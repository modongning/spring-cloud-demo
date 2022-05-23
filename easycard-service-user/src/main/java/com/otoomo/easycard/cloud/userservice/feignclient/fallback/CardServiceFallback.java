package com.otoomo.easycard.cloud.userservice.feignclient.fallback;

import com.otoomo.easycard.cloud.userservice.feignclient.CardServiceFeignClient;
import com.otoomo.easycard.common.pojo.Result;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 自定义的Fallback逻辑
 */
//这里需要添加到容器管理
@Component
public class CardServiceFallback implements CardServiceFeignClient {
    @Override
    public Result getUserCardList(Long userId) {
        return Result.success(new ArrayList<>());
    }
}
