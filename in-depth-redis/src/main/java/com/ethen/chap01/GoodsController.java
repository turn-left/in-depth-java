package com.ethen.chap01;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {
    @PostMapping
    public Object releaseGoods() {
        return null;
    }
}
