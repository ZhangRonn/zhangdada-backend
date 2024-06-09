package com.zhang.zhangdada.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhang.zhangdada.mapper.QuestionMapper;
import com.zhang.zhangdada.model.entity.Question;
import com.zhang.zhangdada.service.QuestionService;
import org.springframework.stereotype.Service;

/**
* @author 张
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2024-06-09 14:58:20
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

}




