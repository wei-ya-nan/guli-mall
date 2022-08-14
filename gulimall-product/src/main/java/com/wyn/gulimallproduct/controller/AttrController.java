package com.wyn.gulimallproduct.controller;

import com.wyn.common.utils.PageUtils;
import com.wyn.common.utils.R;
import com.wyn.gulimallproduct.service.AttrService;
import com.wyn.gulimallproduct.vo.AttrGroupRelationVo;
import com.wyn.gulimallproduct.vo.AttrRespVo;
import com.wyn.gulimallproduct.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 商品属性
 *
 * @author wei -ya-nan
 * @email soyongman0 @gmail.com
 * @date 2022 -06-06 16:52:23
 */
@RestController
@RequestMapping("gulimallproduct/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * Base attr list r.
     *
     * @param params    the params
     * @param catelogId the catelog id
     * @return the r
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params, @PathVariable Long catelogId,
                          @PathVariable("attrType") String type) {
        PageUtils page = attrService.queryBaseAttrpPage(params, catelogId, type);
        return R.ok().put("page", page);
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * 列表
     *
     * @param params the params
     * @return the r
     */
    @RequestMapping("/list")
    //@RequiresPermissions("gulimallproduct:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     *
     * @param attrId the attr id
     * @return the r
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("gulimallproduct:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        /*AttrEntity attr = attrService.getById(attrId);*/
        AttrRespVo attrRespVo = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     *
     * @param attr the attr
     * @return the r
     */
    @RequestMapping("/save")
    //@RequiresPermissions("gulimallproduct:attr:save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     *
     * @param attr the attr
     * @return the r
     */
    @RequestMapping("/update")
    //@RequiresPermissions("gulimallproduct:attr:update")
    public R update(@RequestBody AttrVo attr) {
        /*attrService.updateById(attr);*/
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 删除
     *
     * @param attrIds the attr ids
     * @return the r
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("gulimallproduct:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
