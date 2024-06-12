package com.zhang.zhangdada.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zhang.zhangdada.common.BaseResponse;
import com.zhang.zhangdada.common.ErrorCode;
import com.zhang.zhangdada.common.ResultUtils;
import com.zhang.zhangdada.exception.ThrowUtils;
import com.zhang.zhangdada.model.dto.app.AppAddRequest;
import com.zhang.zhangdada.model.entity.App;
import com.zhang.zhangdada.model.entity.User;
import com.zhang.zhangdada.model.enums.ReviewStatusEnum;
import com.zhang.zhangdada.model.vo.AppVO;
import com.zhang.zhangdada.service.AppService;
import com.zhang.zhangdada.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/app")
@Slf4j
public class AppController {

    @Resource
    private AppService appService;

    @Resource
    UserService userService;

    @PostMapping("addApp")
    public BaseResponse<Long> addApp(@RequestBody AppAddRequest appAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(appAddRequest==null, ErrorCode.PARAMS_ERROR);
        App app = new App();
        BeanUtil.copyProperties(appAddRequest,app);

        appService.validApp(app,true);


        User loginUser = userService.getLoginUser(request);
        app.setId(loginUser.getId());
        app.setReviewStatus(ReviewStatusEnum.PASS.getValue());

        boolean result = appService.save(app);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(app.getId());

    }
}
