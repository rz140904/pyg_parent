package cn.itcast.core.service;


import cn.itcast.core.dao.ad.ContentCategoryDao;
import cn.itcast.core.pojo.ad.ContentCategory;
import cn.itcast.core.pojo.ad.ContentCategoryQuery;
import cn.itcast.core.pojo.entity.PageResult;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryDao contentCategoryDao;


    @Override
    public PageResult search(Integer page, Integer rows, ContentCategory contentCategory) {
        PageHelper.startPage(page,rows );
        ContentCategoryQuery query=new ContentCategoryQuery();
        ContentCategoryQuery.Criteria criteria = query.createCriteria();
        if (contentCategory!=null){
            if (contentCategory.getName()!=null && !"".equals(contentCategory.getName())){
                criteria.andNameLike("%"+contentCategory.getName()+"%");
            }
        }
        Page<ContentCategory>contentCategoriesList = (Page<ContentCategory>) contentCategoryDao.selectByExample(query);
        return new PageResult(contentCategoriesList.getTotal(),contentCategoriesList.getResult());
    }

    @Override
    public void add(ContentCategory contentCategory) {
        contentCategoryDao.insertSelective(contentCategory);
    }

    @Override
    public ContentCategory findOne(Long id) {
        return contentCategoryDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(ContentCategory contentCategory) {
        contentCategoryDao.updateByPrimaryKeySelective(contentCategory);
    }

    @Override
    public void delete(Long[] ids) {
        if (ids!=null){
            for (Long id : ids) {
                contentCategoryDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<ContentCategory> findAll() {

        return contentCategoryDao.selectByExample(null);
    }
}
