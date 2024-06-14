package com.zhang.zhangdada.controller;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.zhang.zhangdada.common.ResultUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        @Autowired
        private AppService  appService;
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
//        根据用户id获取应用实体
        QueryWrapper<App> appQueryWrapper = new QueryWrapper<>();
        appQueryWrapper.eq("userId", userId);
        App one = appService.getOne(appQueryWrapper);

        question.setAppId(one.getId());


        boolean save = questionService.save(question);
        ThrowUtils.throwIf(!save,ErrorCode.SYSTEM_ERROR,"添加失败");

        return ResultUtils.success(question.getId());
    }

//    todo update delete query
}
