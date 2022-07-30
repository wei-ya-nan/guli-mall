package com.wyn.gulimallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyn.common.utils.PageUtils;
import com.wyn.gulimallware.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 11:29:08
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

