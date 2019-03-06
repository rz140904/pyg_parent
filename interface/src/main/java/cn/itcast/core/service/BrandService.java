package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    public List<Brand> findAll();

    //分页查询
    public PageResult findPage(Brand brand, Integer page, Integer rows);

    //添加品牌
    public void add(Brand brand);

    //修改回显
    public Brand findOne(Long id);
    //修改
    public void update(Brand brand);
    //删除
    public void delete(Long[] ids);
    //查询品牌所有数据,供select2下拉框使用
    public List<Map> selectOptionList();

}
