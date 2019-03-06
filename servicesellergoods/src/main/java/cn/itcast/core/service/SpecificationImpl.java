package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.specification.SpecificationQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpecificationImpl implements SpecificationService {

    @Autowired
    private SpecificationDao specDao;

    @Autowired
    private SpecificationOptionDao optionDao;


   //分页
    @Override
    public PageResult finPage(Integer page,Integer rows, Specification spec) {
        //创建查询对象
        SpecificationQuery query=new SpecificationQuery();
        //创建when查询条件
        SpecificationQuery.Criteria criteria = query.createCriteria();
        //判断条件是否为空
        if (spec!=null){
            if(spec.getSpecName()!=null && !"".equals(spec.getSpecName())){
                criteria.andSpecNameLike("%"+spec.getSpecName()+"%");
            }

        }


        //使用分页助手
        PageHelper.startPage(page,rows );
        //调用dao层方法执行查询sql语句  返回强转的page集合的specification对象
        Page<Specification> specList= (Page<Specification>) specDao.selectByExample(query);
        //返回总条数和商品参数
        return new PageResult(specList.getTotal(),specList.getResult());
    }

    @Override
    public void add(SpecEntity specEntity) {
        //1. 保存规格对象
        specDao.insertSelective(specEntity.getSpecification());
        //2. 遍历规格选项集合
        if (specEntity.getSpecificationOptionList()!= null) {
            for (SpecificationOption option : specEntity.getSpecificationOptionList()) {
                //设置规格选项对象中的规格对象的主键id
                option.setSpecId(specEntity.getSpecification().getId());
                //3. 遍历过程中保存规格选项对象
                optionDao.insertSelective(option);
            }
        }


    }

    //回显
    @Override
    public SpecEntity findOne(Long id) {
        //1.根据规格ID查询规格表数据
        Specification specification = specDao.selectByPrimaryKey(id);
        //2.根据规格ID查询规格选项表数据
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = query.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<SpecificationOption> OptionList = optionDao.selectByExample(query);
        //讲规格表和规格选项包封装到entity实体类中
        SpecEntity entity = new SpecEntity();
        entity.setSpecification(specification);
        entity.setSpecificationOptionList(OptionList);
        return entity;
    }


    //修改
    @Override
    public void update(SpecEntity specEntity) {
        //1.更新规格名称
        specDao.updateByPrimaryKeySelective(specEntity.getSpecification());
        //2.根据规格ID删除规格选项
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = query.createCriteria();
        criteria.andSpecIdEqualTo(specEntity.getSpecification().getId());
        optionDao.deleteByExample(query);
        //3.遍历页面传递的新的规格选项集合
        if (specEntity.getSpecificationOptionList()!=null){

            for (SpecificationOption option : specEntity.getSpecificationOptionList()) {

                //设置规格选项表的外键
                option.setSpecId(specEntity.getSpecification().getId());
                //4.保存新的规格选项对象
                optionDao.insertSelective(option);
            }
        }

    }

    //删除
    @Override
    public void delete(Long[] ids) {
        //判断
        if (ids!=null){
            for (Long id : ids) {
                //1.根据规格ID删除规格表的数据
                specDao.deleteByPrimaryKey(id);
                //2.根据规格ID删除规格选项表的数据
                //创建查询对象
                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(id);
                optionDao.deleteByExample(query);
            }
        }
    }
    //查询规格所有用于select2下拉框使用
    @Override
    public List<Map> selectOptionList() {
        List<Map> mapList = specDao.selectOptionList();
        return mapList;
    }


}
