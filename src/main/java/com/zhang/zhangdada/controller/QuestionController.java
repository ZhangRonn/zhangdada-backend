package com.zhang.zhangdada.controller;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.zhang.zhangdada.annotation.AuthCheck;
import com.zhang.zhangdada.common.ResultUtils;
import com.zhang.zhangdada.constant.UserConstant;
import com.zhang.zhangdada.model.dto.question.QuestionContentDTO;

import com.zhang.zhangdada.common.BaseResponse;
import com.zhang.zhangdada.common.ErrorCode;
import com.zhang.zhangdada.exception.ThrowUtils;
import com.zhang.zhangdada.model.dto.question.QuestionAddRequest;
import com.zhang.zhangdada.model.entity.App;
import com.zhang.zhangdada.model.entity.Question;
import com.zhang.zhangdada.model.entity.User;
import com.zhang.zhangdada.service.AppService;
import com.zhang.zhangdada.service.QuestionService;
import com.zhang.zhangdada.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {
        @Resource
        private QuestionService questionService;

        @Resource
        private UserService userService;

    @PostMapping("/addQuestion")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request){

        ThrowUtils.throwIf(questionAddRequest == null,
                ErrorCode.PARAMS_ERROR,"请求参数不能为空");
            //        校验登录用户
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        ThrowUtils.throwIf(loginUser == null,ErrorCode.NOT_LOGIN_ERROR);
        //赋值题目内容
        List<QuestionContentDTO> questionContent = questionAddRequest.getQuestionContent();
        Question question = new Question();
        question.setQuestionContent(JSONUtil.toJsonStr(questionContent));
        question.setAppId(questionAddRequest.getAppId());
        question.setUserId(userId);

        boolean save = questionService.save(question);
        ThrowUtils.throwIf(!save,ErrorCode.SYSTEM_ERROR,"添加失败");

        return ResultUtils.success(question.getId());
    }

    @DeleteMapping("/deleteQuestion")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteQuestion(@RequestParam Long questionId,HttpServletRequest request) {
        ThrowUtils.throwIf(questionId == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);

        Question oldQuestion = questionService.getById(questionId);

        ThrowUtils.throwIf(BeanUtil.isEmpty(oldQuestion)||loginUser.getId()!=oldQuestion.getUserId(),
                ErrorCode.NO_AUTH_ERROR, "无权限删除");

        boolean b = questionService.removeById(questionId);
        ThrowUtils.throwIf(!b, ErrorCode.SYSTEM_ERROR, "删除失败");

        return ResultUtils.success(true);
    }
//    todo update delete query
}
