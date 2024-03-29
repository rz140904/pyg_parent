package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BransServiceImpl implements BrandService {



    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> findAll() {
        return brandDao.selectByExample(null);
    }

    //分页查询
    @Override
    public PageResult findPage(Brand brand, Integer page, Integer rows) {

         //创建查询对象
        BrandQuery query = new BrandQuery();

        //组装条件
        if (brand!=null){
            //创建SQL语句中的when条件
            BrandQuery.Criteria criteria = query.createCriteria();
            if (brand.getFirstChar()!=null && !"".equals(brand.getFirstChar())){
                criteria.andFirstCharEqualTo(brand.getFirstChar());
            }
            if (brand.getName()!=null &&!"".equals(brand.getName())){
                criteria.andNameLike("%"+brand.getName()+"%");
            }
        }


        PageHelper.startPage(page,rows);
        //查询
        Page<Brand> brandList=(Page<Brand>)brandDao.selectByExample(query);
        return new PageResult(brandList.getTotal(),brandList.getResult());
    }

    //添加
    @Override
    public void add(Brand brand) {
        //添加的时候传入brand对象中的所有属性必须有值,
        //brandDao.insert();
        //添加的时候,判断传入brand的对象中是否有值,有值添加
        brandDao.insertSelective(brand);
    }

    //修改回显
    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void update(Brand brand) {
        //根据条件查询更新,这个条件不包括主键条件,判断传入的brand中是否有NULL的属性,如果为null,不参与更新
        //brandDao.updateByExampleSelective(, );
        //根据条件查询更新,这个条件不包括主键条件
        //brandDao.updateByExample(, );
        //根据主键作为条件更新
        //brandDao.updateByPrimaryKey();
        //跟句主键作为条件更新并判断传入的brand参数中的属性是否为null,如果有则不参与更新
        brandDao.updateByPrimaryKeySelective(brand);
    }

    //
    @Override
    public void delete(Long[] ids) {
        if (ids!=null){
            for (Long id : ids) {
                brandDao.deleteByPrimaryKey(id);
            }
        }
    }

    //查询品牌所有数据,供select2下拉框使用
    @Override
    public List<Map> selectOptionList() {
        List<Map> mapList = brandDao.selectOptionList();
        return mapList;
    }
}
