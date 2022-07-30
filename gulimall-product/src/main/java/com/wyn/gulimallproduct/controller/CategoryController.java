package com.wyn.gulimallproduct.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wyn.gulimallproduct.entity.CategoryEntity;
import com.wyn.gulimallproduct.service.CategoryService;
import com.wyn.common.utils.PageUtils;
import com.wyn.common.utils.R;



/**
 * 商品三级分类
 *
 * @author wei-ya-nan
 * @email soyongman0@gmail.com
 * @date 2022-06-06 16:52:23
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list/tree")
    public R list(){
        List<CategoryEntity> entities = categoryService.listWithTree();
        return R.ok().put("page", entities);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("gulimallproduct:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("gulimallproduct:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("gulimallproduct:category:update")
    public R update(@RequestBody CategoryEntity category){
        categoryService.updateById(category);

        return R.ok();
    }

    @RequestMapping("/update/sort")
    //@RequiresPermissions("gulimallproduct:category:update")
    public R update(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));

        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("gulimallproduct:category:delete")
    public R delete(@RequestBody Long[] catIds){
		/*categoryService.removeByIds(Arrays.asList(catIds));*/
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

    /*@RequestMapping("/batch/delete")
    //@RequiresPermissions("gulimallproduct:category:delete")
    public R batchDelete(@RequestBody Long[] catIds){
        *//*categoryService.removeByIds(Arrays.asList(catIds));*//*
        categoryService.re
        return R.ok();
    }*/


}
