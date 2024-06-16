package com.zhang.zhangdada.controller;

import cn.hutool.core.bean.BeanUtil;
import com.zhang.zhangdada.common.BaseResponse;
import com.zhang.zhangdada.common.ErrorCode;
import com.zhang.zhangdada.common.ResultUtils;
import com.zhang.zhangdada.exception.ThrowUtils;
import com.zhang.zhangdada.model.dto.userAnswer.UserAnswerAddRequest;
import com.zhang.zhangdada.model.entity.User;
import com.zhang.zhangdada.model.entity.UserAnswer;
import com.zhang.zhangdada.service.UserAnswerService;
import com.zhang.zhangdada.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("/userAnswer")
@Slf4j
public class UserAnswerController {

    @Autowired
    private UserAnswerService userAnswerService;

    @Resource
    private UserService userService;

    @PostMapping("/addUserAnswer")
    public BaseResponse<Boolean> addUserAnswer(@RequestBody UserAnswerAddRequest userAnswerAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userAnswerAddRequest == null, ErrorCode.PARAMS_ERROR,"请求参数不能为空");

        User loginUser = userService.getLoginUser(request);
        ThrowUtils.throwIf(BeanUtil.isEmpty(loginUser),ErrorCode.NO_AUTH_ERROR);

        UserAnswer userAnswer = new UserAnswer();
        BeanUtil.copyProperties(userAnswerAddRequest,userAnswer);
        userAnswerService.validUserAnswer(userAnswer);

        boolean save = userAnswerService.save(userAnswer);
        ThrowUtils.throwIf(!save,ErrorCode.OPERATION_ERROR,"添加失败");

        return ResultUtils.success(true);

    }

}
