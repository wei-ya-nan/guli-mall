package com.wyn.gulimallorder.dao;

import com.wyn.gulimallorder.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 11:22:10
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
