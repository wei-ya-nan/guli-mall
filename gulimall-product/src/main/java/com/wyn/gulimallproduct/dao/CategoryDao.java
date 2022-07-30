package com.wyn.gulimallproduct.dao;

import com.wyn.gulimallproduct.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-06 16:52:23
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
