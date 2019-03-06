package cn.itcast.core.service;

import cn.itcast.core.pojo.ad.ContentCategory;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;

import java.util.List;

public interface ContentCategoryService {

    public PageResult search (Integer page, Integer rows,  ContentCategory contentCategory);

    public void add( ContentCategory contentCategory);

    public  ContentCategory findOne(Long id);

    public void update( ContentCategory contentCategory);

    public void delete(Long[] ids);

    public List<ContentCategory> findAll();
}
