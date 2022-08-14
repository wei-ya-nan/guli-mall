package com.wyn.gulimallproduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyn.common.constant.ProductConstant;
import com.wyn.common.utils.PageUtils;
import com.wyn.common.utils.Query;
import com.wyn.gulimallproduct.dao.AttrAttrgroupRelationDao;
import com.wyn.gulimallproduct.dao.AttrDao;
import com.wyn.gulimallproduct.dao.AttrGroupDao;
import com.wyn.gulimallproduct.dao.CategoryDao;
import com.wyn.gulimallproduct.entity.AttrAttrgroupRelationEntity;
import com.wyn.gulimallproduct.entity.AttrEntity;
import com.wyn.gulimallproduct.entity.AttrGroupEntity;
import com.wyn.gulimallproduct.entity.CategoryEntity;
import com.wyn.gulimallproduct.service.AttrService;
import com.wyn.gulimallproduct.service.CategoryService;
import com.wyn.gulimallproduct.vo.AttrGroupRelationVo;
import com.wyn.gulimallproduct.vo.AttrRespVo;
import com.wyn.gulimallproduct.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The type Attr service.
 */
@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    /**
     * The Relation dao.
     */
    @Resource
    AttrAttrgroupRelationDao relationDao;

    /**
     * The Attr group dao.
     */
    @Resource
    AttrGroupDao attrGroupDao;

    /**
     * The Category dao.
     */
    @Resource
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // 保存基本数据
        this.save(attrEntity);
        // 保存关联关系
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }


    }

    @Override
    public PageUtils queryBaseAttrpPage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        if (catelogId != 0) {
            queryWrapper.eq("catelog_id", catelogId).eq("attr_id", "base".equalsIgnoreCase(type) ? 1 : 0);
        }
        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and((wrapper -> {
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            }));
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            // 设置分类和分组的名字。
            if ("base".equalsIgnoreCase(type)) {
                AttrAttrgroupRelationEntity attrId =
                        relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",
                                attrEntity.getAttrId()));

                if (attrId != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getAttrId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo attrRespVo = new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, attrRespVo);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 设置分组信息
            AttrAttrgroupRelationEntity attrgroupRelationEntity =
                    relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            attrRespVo.setAttrGroupId(attrgroupRelationEntity.getAttrGroupId());
            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupRelationEntity.getAttrGroupId());
        }


        // 设置分类信息
        Long catelogId = attrEntity.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        attrRespVo.setCatelogPath(path);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        attrRespVo.setCatelogName(categoryEntity.getName());

        return null;
    }

    @Override
    @Transactional
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // 修改分组关联
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());

            Integer count = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_d",
                    attr.getAttrId()));
            if (count > 0) {

                relationDao.update(relationEntity, new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",
                        attr.getAttrId()));
            } else {
                relationDao.insert(relationEntity);
            }
        }

    }

    /**
     * 根据分组id查询所有的基本属性
     *
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> entities =
                relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id",
                        attrgroupId));
        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());
        return null;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        //relationDao.delete(new QueryWrapper<AttrGroupRelationVo>().eq("attr_id", 1L).eq("attr_group_id", 1L))
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(entities);
    }

    /**
     * 获取当前分组 没有关联的所有属性
     *
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNorelationAttr(Map<String, Object> params, Long attrgroupId) {
        //  当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        // 当前分组只能关联别的分组没哟引用的属性
        // a:当前分类下的其他分类。b:这些分组关联的属性。c:从当前的分类所有的属性中移除这些属性
        // 当前分类下的其他分组
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq(
                "catelog_id", catelogId).ne("attr_group_id", attrgroupId));
        List<Long> groupIds = groupEntities.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        // 这些分组关联的属性
        List<AttrAttrgroupRelationEntity> entities =
                relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id",
                        groupIds));
        List<Long> attrIds = entities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        // 从当前分类的所有属性的中移除这些属性
        QueryWrapper<AttrEntity> queryWrapper =
                new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).notIn("attr_id", attrIds);
        IPage<AttrEntity> iPage = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        PageUtils page = new PageUtils(iPage);

        return page;
    }

}