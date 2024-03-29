package com.wyn.gulimallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyn.common.utils.PageUtils;
import com.wyn.gulimallproduct.entity.AttrEntity;
import com.wyn.gulimallproduct.vo.AttrGroupRelationVo;
import com.wyn.gulimallproduct.vo.AttrRespVo;
import com.wyn.gulimallproduct.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-06 16:52:23
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrpPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNorelationAttr(Map<String, Object> params, Long attrgroupId);
}

