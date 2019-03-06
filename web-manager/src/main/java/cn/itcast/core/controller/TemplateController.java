package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {


    @Reference
    private TemplateService templateService;

    /**
     *
     * @param page 当前页数
     * @param rows 每页显示条数
     * @param typeTemplate
     * @return
     */
    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, @RequestBody TypeTemplate typeTemplate){
        PageResult search = templateService.search(page, rows, typeTemplate);
        return search;
    }

    /**
     * 添加
     * @param template 传入添加的模板对象
     * @return 返回结果状态
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate template){
        try {
            templateService.add(template);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    /**
     * 修改回显
     * @param id 传入ID根据ID查询回显数据
     * @return 返回模板对象
     */
    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id){
        TypeTemplate one = templateService.findOne(id);
        return one;
    }

    /**
     *
     * @param template 传入修改的模板对象
     * @return 返回结果状态
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TypeTemplate template){
        try {
            templateService.update(template);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    /**
     *
     * @param ids 传入需要删除的ID集合
     * @return 返回结果状态
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            templateService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

}
