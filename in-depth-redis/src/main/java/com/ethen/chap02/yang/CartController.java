package com.ethen.chap02.yang;

import com.ethen.chap02.yang.util.JedisUtils;

import static com.ethen.common.ResConstant.SUCCESS;

import com.ethen.model.GoodsReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * 购物车
 * fixme 商品加车时间排序如何解决？?
 */
@Api("购物车逻辑")
@RequestMapping("/cart")
@RestController
public class CartController {
    @ApiOperation("查看购物车")
    @GetMapping("/user/{id}")
    public Object queryUserCartInfo(@PathVariable("id") String userId) {
        Jedis redis = JedisUtils.getClient();
        Map<String, String> cartMap = redis.hgetAll(cartKey(userId));
        return cartMap;
    }

    @ApiOperation("商品加车&商品数量更新")
    @PostMapping("/goods/add")
    public Object addGoods(@RequestBody GoodsReq req) {
        Jedis client = JedisUtils.getClient();
        Long result = client.hincrBy(cartKey(req.getUserId()), req.getGoodsId(), req.getAmount());
        System.out.println("addGoods result:" + result);
        return SUCCESS;
    }

    @ApiOperation("从购物车移除商品")
    @DeleteMapping("/user/{uid}/goods/{gid}")
    public Object removeGoods(@PathVariable("uid") String userId, @PathVariable("gid") String goodsId) {
        Jedis client = JedisUtils.getClient();
        Long result = client.hdel(cartKey(userId), goodsId);
        System.out.println("removeGoods result:" + result);
        return SUCCESS;
    }

    private String cartKey(String userId) {
        return "cart:" + userId;
    }
}
