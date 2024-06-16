package com.zhang.zhangdada.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.zhang.zhangdada.common.BaseResponse;
import com.zhang.zhangdada.common.ErrorCode;
import com.zhang.zhangdada.common.ResultUtils;
import com.zhang.zhangdada.exception.BusinessException;
import com.zhang.zhangdada.exception.ThrowUtils;
import com.zhang.zhangdada.model.dto.userAnswer.UserAnswerAddRequest;
import com.zhang.zhangdada.model.entity.App;
import com.zhang.zhangdada.model.entity.User;
import com.zhang.zhangdada.model.entity.UserAnswer;
import com.zhang.zhangdada.model.enums.ReviewStatusEnum;
import com.zhang.zhangdada.service.AppService;
import com.zhang.zhangdada.service.UserAnswerService;
import com.zhang.zhangdada.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController("/userAnswer")
@Slf4j
public class UserAnswerController {

    @Autowired
    private UserAnswerService userAnswerService;

    @Resource
    private UserService userService;

    @Resource
    private AppService appService;
    @PostMapping("/addUserAnswer")
    public BaseResponse<Boolean> addUserAnswer(@RequestBody UserAnswerAddRequest userAnswerAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userAnswerAddRequest == null, ErrorCode.PARAMS_ERROR,"请求参数不能为空");

        UserAnswer userAnswer = new UserAnswer();
        BeanUtil.copyProperties(userAnswerAddRequest,userAnswer);
        List<String> choices = userAnswerAddRequest.getChoices();
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));

//        验证用户回答是否已经存在
        UserAnswer byId = userAnswerService.getById(userAnswer.getId());
        ThrowUtils.throwIf(!BeanUtil.isEmpty(byId),ErrorCode.SYSTEM_ERROR,"用户回答已经存在");

        userAnswerService.validUserAnswer(userAnswer);
        User loginUser = userService.getLoginUser(request);
        userAnswer.setUserId(loginUser.getId());

        App app = appService.getById(userAnswer.getAppId());
        if (!ReviewStatusEnum.PASS.equals(ReviewStatusEnum.getEnumByValue(app.getReviewStatus()))){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "应用未通过审核，操作失败");
        }

        try {
          Boolean  save = userAnswerService.save(userAnswer);
          ThrowUtils.throwIf(!save,ErrorCode.SYSTEM_ERROR,"添加失败");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        todo 调用评分模块

        return ResultUtils.success(true);
    }

}
