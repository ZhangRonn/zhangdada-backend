package com.zhang.zhangdada.service.impl;
import java.util.Date;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhang.zhangdada.common.ErrorCode;
import com.zhang.zhangdada.exception.ThrowUtils;
import com.zhang.zhangdada.mapper.AppMapper;
import com.zhang.zhangdada.model.entity.App;
import com.zhang.zhangdada.model.entity.User;
import com.zhang.zhangdada.model.enums.AppScoringStrategyEnum;
import com.zhang.zhangdada.model.enums.AppTypeEnum;
import com.zhang.zhangdada.service.AppService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
* @author 张
* @description 针对表【app(应用)】的数据库操作Service实现
* @createDate 2024-06-09 14:58:20
*/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
    implements AppService {


    @Override
    public void validApp(App app, boolean b) {
        ThrowUtils.throwIf(app==null, ErrorCode.SYSTEM_ERROR);
        String appName = app.getAppName();
        String appDesc = app.getAppDesc();
        String appIcon = app.getAppIcon();
        Integer appType = app.getAppType();
        Integer scoringStrategy = app.getScoringStrategy();
        Integer reviewStatus = app.getReviewStatus();
        if (b) {
            ThrowUtils.throwIf(StrUtil.isEmpty(appName),ErrorCode.PARAMS_ERROR,"应用名不能为空");
            ThrowUtils.throwIf(StrUtil.isEmpty(appDesc),ErrorCode.PARAMS_ERROR,"应用描述不能为空");
            ThrowUtils.throwIf(StrUtil.isEmpty(appIcon),ErrorCode.PARAMS_ERROR,"应用图标不能为空");
            ThrowUtils.throwIf(AppTypeEnum.getEnumByValue(appType)==null,
                    ErrorCode.PARAMS_ERROR, "应用类型枚举不能为空");
            ThrowUtils.throwIf(AppScoringStrategyEnum.getEnumByValue(scoringStrategy)==null,
                    ErrorCode.PARAMS_ERROR,"应用评分标准不能为空");
//            ThrowUtils.throwIf(reviewStatus!=1,
//                    ErrorCode.OPERATION_ERROR,"应用审核未通过");

        }
    }

}




