package com.zhang.zhangdada.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhang.zhangdada.mapper.AppMapper;
import com.zhang.zhangdada.model.entity.App;
import com.zhang.zhangdada.service.AppService;
import org.springframework.stereotype.Service;

/**
* @author 张
* @description 针对表【app(应用)】的数据库操作Service实现
* @createDate 2024-06-09 14:58:20
*/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService {

}




