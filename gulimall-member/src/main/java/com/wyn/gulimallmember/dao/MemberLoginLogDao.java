package com.wyn.gulimallmember.dao;

import com.wyn.gulimallmember.entity.MemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-07 11:00:08
 */
@Mapper
public interface MemberLoginLogDao extends BaseMapper<MemberLoginLogEntity> {
	
}
