package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    //分页查询
    public PageResult finPage(Integer page,Integer rows, Specification spec);

    //添加规格个规格选项
    public void add(SpecEntity specEntity);
    //回显
    public SpecEntity findOne(Long id);
    //修改
    public void update(SpecEntity specEntity);
    //删除
    public void delete (Long[] ids);
    //查询规格所有用于select2下拉框使用
    public List<Map> selectOptionList();
}
