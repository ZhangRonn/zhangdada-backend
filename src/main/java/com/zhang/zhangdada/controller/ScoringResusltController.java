package com.zhang.zhangdada.controller;

import com.zhang.zhangdada.common.BaseResponse;
import com.zhang.zhangdada.common.ResultUtils;
import com.zhang.zhangdada.model.dto.scoringResult.ScoringResultAddRequest;
import com.zhang.zhangdada.model.entity.ScoringResult;
import com.zhang.zhangdada.service.ScoringResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/scoringResult")
@Slf4j
public class ScoringResusltController {


    @Autowired
    private ScoringResultService scoringResultService;

    @PostMapping("/addScoringresult")
    public BaseResponse<Boolean> addScoringResult(@RequestBody ScoringResultAddRequest scoringResultAddRequest) {


        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultAddRequest, scoringResult);

        scoringResultService.save(scoringResult);


        return ResultUtils.success(true);
    }


}
