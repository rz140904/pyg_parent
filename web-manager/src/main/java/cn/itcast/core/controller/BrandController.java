package cn.itcast.core.controller;


import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<Brand> findAll() {
        List<Brand> brands = brandService.findAll();
        return brands;
    }


    /*
     * 分页查询
     * page 当前页
     * rows 每页显示条数
     * */
    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer rows) {
        PageResult pageResult = brandService.findPage(null, page, rows);
        return pageResult;
    }


    /*
     * 添加
     *
     * */

    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");

        }
    }


    /*
     * 修改回显数据
     * */
    @RequestMapping("/findOne")
    public Brand findOne(Long id) {
        Brand one = brandService.findOne(id);
        return one;
    }

    /*修改
     * */
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /*
     * 删除
     * */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            brandService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");

        }
    }

    /*
    * 高级查询(分页,查询)
    * page 当前页
     rows 每页显示条数
     brand 需要查询的条件品牌对象
    * */
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody Brand brand) {
        PageResult pageResult = brandService.findPage(brand, page, rows);
        return pageResult;
    }


    //查询品牌的所有数据,返回给模板select2下拉框使用,数据格式是select2下拉框规定的
    @RequestMapping("selectOptionList")
    public List<Map> selectOptionList() {
        return brandService.selectOptionList();

    }
}
