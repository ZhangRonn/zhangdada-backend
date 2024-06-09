package com.zhang.zhangdada.service.impl;

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

}




