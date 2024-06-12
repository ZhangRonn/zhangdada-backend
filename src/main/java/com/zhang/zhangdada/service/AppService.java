package com.zhang.zhangdada.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.zhangdada.model.entity.App;
import com.zhang.zhangdada.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author 张
* @description 针对表【app(应用)】的数据库操作Service
* @createDate 2024-06-09 14:58:20
*/
public interface AppService extends IService<App> {


    void validApp(App app, boolean b);
}
