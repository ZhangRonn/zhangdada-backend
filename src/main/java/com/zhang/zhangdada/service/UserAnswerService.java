package com.zhang.zhangdada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.zhangdada.model.entity.UserAnswer;

/**
* @author 张
* @description 针对表【user_answer(用户答题记录)】的数据库操作Service
* @createDate 2024-06-09 14:58:21
*/
public interface UserAnswerService extends IService<UserAnswer> {

    void validUserAnswer(UserAnswer userAnswer);
}
