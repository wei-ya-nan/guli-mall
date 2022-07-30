package com.wyn.gulimallcoupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyn.common.utils.PageUtils;
import com.wyn.gulimallcoupon.entity.CouponEntity;

import java.util.Map;

/**
 * 优惠券信息
 *
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 10:32:02
 */
public interface CouponService extends IService<CouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

