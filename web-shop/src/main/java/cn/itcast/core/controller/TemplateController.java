package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {


    @Reference
    private TemplateService templateService;


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

    @RequestMapping("findBySpecList")
    public List<Map> findBySpecList(Long id){
        List<Map> specList = templateService.findBySpecList(id);
        return specList;

    }
}
