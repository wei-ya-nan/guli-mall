package com.wyn.gulimallorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyn.common.utils.PageUtils;
import com.wyn.gulimallorder.entity.OrderOperateHistoryEntity;

import java.util.Map;

/**
 * 订单操作历史记录
 *
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 11:22:10
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

