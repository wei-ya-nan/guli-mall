package com.wyn.gulimallorder.dao;

import com.wyn.gulimallorder.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 11:22:10
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
