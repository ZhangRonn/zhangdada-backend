package com.zhang.zhangdada.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhang.zhangdada.annotation.AuthCheck;
import com.zhang.zhangdada.common.BaseResponse;
import com.zhang.zhangdada.common.ErrorCode;
import com.zhang.zhangdada.common.ResultUtils;
import com.zhang.zhangdada.constant.UserConstant;
import com.zhang.zhangdada.exception.ThrowUtils;
import com.zhang.zhangdada.model.dto.app.AppAddRequest;
import com.zhang.zhangdada.model.dto.app.AppQueryRequest;
import com.zhang.zhangdada.model.dto.app.AppUpdateRequest;
import com.zhang.zhangdada.model.entity.App;
import com.zhang.zhangdada.model.entity.User;
import com.zhang.zhangdada.model.enums.ReviewStatusEnum;
import com.zhang.zhangdada.service.AppService;
import com.zhang.zhangdada.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addApp")
    public BaseResponse<Long> addApp(@RequestBody AppAddRequest appAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(appAddRequest==null, ErrorCode.PARAMS_ERROR);
        App app = new App();
        BeanUtil.copyProperties(appAddRequest,app);
        app.setReviewStatus(1);
        appService.validApp(app,true);

        User loginUser = userService.getLoginUser(request);
        app.setUserId(loginUser.getId());
        app.setReviewStatus(ReviewStatusEnum.PASS.getValue());

        boolean result = appService.save(app);
        System.out.println("11111");
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);

        return ResultUtils.success(app.getId());

    }
    @PostMapping("/updateApp")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateApp(@RequestBody AppUpdateRequest appUpdateRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(appUpdateRequest ==null, ErrorCode.PARAMS_ERROR);

        App app = new App();
        BeanUtil.copyProperties(appUpdateRequest,app);

        appService.validApp(app,true);

        Long id = appUpdateRequest.getId();
        App byId = appService.getById(id);
        ThrowUtils.throwIf(byId==null,ErrorCode.NOT_FOUND_ERROR);

        String appName = appUpdateRequest.getAppName();
        String appDesc = appUpdateRequest.getAppDesc();
        String appIcon = appUpdateRequest.getAppIcon();
        Integer appType = appUpdateRequest.getAppType();
        Integer scoringStrategy = appUpdateRequest.getScoringStrategy();
        Integer reviewStatus = appUpdateRequest.getReviewStatus();

        UpdateWrapper<App> appUpdateWrapper = new UpdateWrapper<>();
        appUpdateWrapper.eq("id", id);
        appUpdateWrapper.eq("appDesc", appDesc);
        appUpdateWrapper.eq("appIcon", appIcon);
        appUpdateWrapper.eq("appType", appType);
        appUpdateWrapper.eq("scoringStrategy", scoringStrategy);
        appUpdateWrapper.eq("reviewStatus", reviewStatus);

        boolean update = appService.update(appUpdateWrapper);
        ThrowUtils.throwIf(!update,ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);

    }
    @GetMapping("/queryApp")
    public BaseResponse<Boolean> queryApp(@RequestBody AppQueryRequest appQueryRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(appQueryRequest ==null, ErrorCode.PARAMS_ERROR);
        App app = new App();
        BeanUtil.copyProperties(appQueryRequest,app);
        app.setReviewStatus(1);
        appService.validApp(app,true);

        User loginUser = userService.getLoginUser(request);
        app.setId(loginUser.getId());
        app.setReviewStatus(ReviewStatusEnum.PASS.getValue());
        QueryWrapper<App> appQueryWrapper = new QueryWrapper<>();
        appQueryWrapper.eq("id", appQueryRequest.getId());
        App one = appService.getOne(appQueryWrapper);

        ThrowUtils.throwIf(BeanUtil.isEmpty(one),ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);


    }

    //todo  delete query
}
