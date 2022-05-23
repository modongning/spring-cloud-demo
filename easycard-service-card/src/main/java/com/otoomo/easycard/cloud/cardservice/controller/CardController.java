package com.otoomo.easycard.cloud.cardservice.controller;

import com.google.common.collect.ImmutableList;
import com.otoomo.easycard.common.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class CardController {

    @GetMapping("/getUserCardList/{userId}")
    public Result getUserCardList(@PathVariable Long userId) {
        Map<String, Object> cardInfo = new HashMap<>();
        cardInfo.put("cardNo", "2134567896543231");
        cardInfo.put("status", 1);
        return Result.success(ImmutableList.builder().add(cardInfo).build());
    }
}
