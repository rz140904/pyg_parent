package cn.itcast.core.controller;


import cn.itcast.core.pojo.ad.ContentCategory;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.ContentCategoryService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("contentCategory")
public class ContentCategoryController {


    @Reference
    private ContentCategoryService categoryService;


    @RequestMapping("/search")
    public PageResult search (Integer page, Integer rows, @RequestBody ContentCategory contentCategory){
        return categoryService.search(page,rows ,contentCategory );
    }


    @RequestMapping("/add")
    public Result add(@RequestBody ContentCategory contentCategory){
        try {
            categoryService.add(contentCategory);
            return new Result(true,"添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败!");
        }
    }

    @RequestMapping("/findOne")
    public  ContentCategory findOne(Long id){

        ContentCategory one = categoryService.findOne(id);
        return one;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody ContentCategory contentCategory){
        try {
            categoryService.update(contentCategory);
            return new Result(true,"修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败!");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            categoryService.delete(ids);
            return new Result(true,"删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败!");
        }
    }

    @RequestMapping("/findAll")
    public List<ContentCategory> findAll(){
        return categoryService.findAll();
    }

}
