package com.wyn.gulimallmember.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyn.common.utils.PageUtils;
import com.wyn.gulimallmember.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 11:00:08
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

