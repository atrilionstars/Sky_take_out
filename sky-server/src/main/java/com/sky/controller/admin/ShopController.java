package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminShopController") //一般无参数，该参数用来命名Bean,以与用户端的Bean做区分
@Api(tags = "店铺相关接口")
@RequestMapping("admin/shop")
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    //设置店铺的营业状态
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺的营业状态为：{}", status==1? "营业中":"已打烊");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }
    //获取店铺的营业状态
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("当前店铺的营业状态为：{}",status==1? "营业中":"已打烊");
        return Result.success(status);
    }
}
