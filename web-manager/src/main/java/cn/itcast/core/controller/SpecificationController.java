package cn.itcast.core.controller;


import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {


    @Reference
    private SpecificationService specificationService;


    /**
     * 分页查询
     * @param page 当前页
     * @param rows 每页显示条数
     * @param spec 查询条件对象
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows,@RequestBody Specification spec){

        PageResult pageResult = specificationService.finPage(page, rows, spec);

        return pageResult;

    }

    /**
     * 增加
     * @param specEntity 规格对象个规格选项集合的实体类
     * @return 返回成功或者失败的状态
     */
    @RequestMapping("/add")
    public Result add(@RequestBody SpecEntity specEntity) {
        try {
            specificationService.add(specEntity);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败!");
        }
    }
    //回显
    @RequestMapping("/findOne")
    public SpecEntity findOne(Long id){
        SpecEntity one = specificationService.findOne(id);
        return one;
    }

    //修改
    @RequestMapping("/update")
    public Result update(@RequestBody SpecEntity specEntity){
        //调用业务层方法
        try {
            specificationService.update(specEntity);
            return new Result(true,"修改成功!!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败!!");

        }
    }

    //删除
    @RequestMapping("delete")
    public Result delete(Long[] ids){
        //调用业务层删除的方法
        try {
            specificationService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
    //查询规格所有用于select2下拉框使用
    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList(){
       return specificationService.selectOptionList();
    }
}
