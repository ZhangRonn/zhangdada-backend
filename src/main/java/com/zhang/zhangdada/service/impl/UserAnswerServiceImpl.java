package com.zhang.zhangdada.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhang.zhangdada.mapper.UserAnswerMapper;
import com.zhang.zhangdada.model.entity.UserAnswer;
import com.zhang.zhangdada.service.UserAnswerService;
import org.springframework.stereotype.Service;

/**
* @author 张
* @description 针对表【user_answer(用户答题记录)】的数据库操作Service实现
* @createDate 2024-06-09 14:58:21
*/
@Service
public class UserAnswerServiceImpl extends ServiceImpl<UserAnswerMapper, UserAnswer>
    implements UserAnswerService {

    @Override
    public void validUserAnswer(UserAnswer userAnswer) {
     Long appId = userAnswer.getAppId();
     Integer appType = userAnswer.getAppType();
     Integer scoringStrategy = userAnswer.getScoringStrategy();
     String choices = userAnswer.getChoices();
     Long resultId = userAnswer.getResultId();
     String resultName = userAnswer.getResultName();
     String resultDesc = userAnswer.getResultDesc();
     String resultPicture = userAnswer.getResultPicture();
     Integer resultScore = userAnswer.getResultScore();
     Long userId = userAnswer.getUserId();


    }
}




