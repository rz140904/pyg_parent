package cn.itcast.test;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/application*.xml"})
public class TestDao {

    @Autowired
    private BrandDao brandDao;

    @Test
    public void testFindAll() {
        List<Brand> brands = brandDao.selectByExample(null);
        System.out.println("========="+brands);
    }

    @Test
    public void testFindOne() {
        Brand brand = brandDao.selectByPrimaryKey(1L);
        System.out.println(brand);
    }

    @Test
    public void testFindBrandByQuery() {
        //创建查询对象
        BrandQuery query = new BrandQuery();
        //设置去重 不写默认是false
        query.setDistinct(true);
        //设置排序
        query.setOrderByClause("id desc");
        //设置查询字段,不写默认查询所有字段,写指定字段
        query.setFields("id,name");
        //创建when查询条件对象
        BrandQuery.Criteria criteria = query.createCriteria();
        //根据名称模糊查询
        criteria.andNameLike("%联想%");
        //根据首字母精确查询
        criteria.andFirstCharEqualTo("L");
        //根据条件查询返回结果
        List<Brand> brands = brandDao.selectByExample(query);
        System.out.println(brands);
    }
}
