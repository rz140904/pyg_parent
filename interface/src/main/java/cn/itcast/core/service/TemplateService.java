package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


public interface TemplateService {
    //分页查询
    public PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate);
    //增加
    public void add( TypeTemplate template);
    //修改回显
    public TypeTemplate findOne(Long id);
    //修改
    public void update(TypeTemplate template);
    //删除
    public void delete(Long[] ids);

    public List<Map> findBySpecList(Long id);
}
