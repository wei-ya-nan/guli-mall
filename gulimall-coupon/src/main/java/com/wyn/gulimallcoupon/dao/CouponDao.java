package com.wyn.gulimallcoupon.dao;

import com.wyn.gulimallcoupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 10:32:02
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
