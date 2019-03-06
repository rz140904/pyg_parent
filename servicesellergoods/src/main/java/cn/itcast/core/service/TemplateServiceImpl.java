package cn.itcast.core.service;

import cn.itcast.core.common.Constants;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService {


    @Autowired
    private TypeTemplateDao templateDao;


    @Autowired
    private SpecificationOptionDao optionDao;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate) {


        /*
        * 缓存品牌集合和规格集合到redis中供portal使用
        * */
        //查询所有模板数据
        List<TypeTemplate> templates = templateDao.selectByExample(null);
        if (templates!=null){
            for (TypeTemplate template : templates) {
                //获取品牌json字符串
                String brandJsonStr = template.getBrandIds();
                List<Map> maps= JSON.parseArray(brandJsonStr, Map.class);
                //缓存品牌集合到redis数据库
                redisTemplate.boundHashOps(Constants.REDIS_BRAND_LIST).put(template.getId(),maps );
                //根据模板ID获取规格和规格选项集合
                List<Map> specList = findBySpecList(template.getId());
                redisTemplate.boundHashOps(Constants.REDIS_SPEC_LIST).put(template.getId(),specList );
            }
        }


        /*
        * 分页查询
        * */
        //创建查询对象
        TypeTemplateQuery query = new TypeTemplateQuery();
        //创建when查询条件
        TypeTemplateQuery.Criteria criteria = query.createCriteria();
        if (typeTemplate!=null){
            if (typeTemplate.getName()!=null && !"".equals(typeTemplate.getName())){
                criteria.andNameLike("%"+typeTemplate.getName()+"%");
            }
        }
        PageHelper.startPage(page,rows );
        Page<TypeTemplate> templateList = (Page<TypeTemplate>) templateDao.selectByExample(query);

        return new PageResult(templateList.getTotal(),templateList.getResult()) ;
    }

    @Override
    public void add(TypeTemplate template) {
        templateDao.insertSelective(template);
    }

    @Override
    public TypeTemplate findOne(Long id) {
        TypeTemplate typeTemplate = templateDao.selectByPrimaryKey(id);
        return typeTemplate;
    }

    @Override
    public void update(TypeTemplate template) {
        templateDao.updateByPrimaryKeySelective(template);
    }

    @Override
    public void delete(Long[] ids) {
        if (ids!=null){
            for (Long id : ids) {
                templateDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<Map> findBySpecList(Long id) {

        //1.根据模板ID查询模板对象
        TypeTemplate typeTemplate = templateDao.selectByPrimaryKey(id);
        //2.从模板对象中获取规格集合数据
        String specJsonStr = typeTemplate.getSpecIds();
        //3.解析规格集合数据
        List<Map> specList = JSON.parseArray(specJsonStr, Map.class);

        //4.遍历解析后的规格集合数据
        if (specList!=null){

            for (Map map : specList) {

                //5.在遍历过程中,根据规格ID查询规格选项集合数据
                //结果经历两次转换,现将object转换为string 在转换为long型
                Long specId = Long.parseLong(String.valueOf(map.get("id")));
                //创建查询对象
                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(specId);
                List<SpecificationOption> optionList = optionDao.selectByExample(query);
                //6.将规格集合数据封装到规格集合中
                map.put("options",optionList );
            }

        }


        return specList;
    }
}
