package com.wyn.gulimallproduct.dao;

import com.wyn.gulimallproduct.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 * 
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-06 16:52:23
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void udpateCategory(@Param("catId") Long catId, @Param("name") String name);
}
