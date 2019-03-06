package cn.itcast.core.service;

import cn.itcast.core.common.Constants;
import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {


    @Autowired
    private ItemCatDao itemCatDao;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<ItemCat> findByParentId(Long parentId) {

        /*
        * 查询
        * */
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCatList = itemCatDao.selectByExample(query);

        /*
        * 缓存所有数据到redis中 供portal系统使用
        * */
        List<ItemCat> catList = itemCatDao.selectByExample(null);
        if (catList!=null){
            for (ItemCat itemCat : catList) {
                redisTemplate.boundHashOps(Constants.REDIS_CATEGORY_LIST).put(itemCat.getName(), itemCat.getTypeId());
            }
        }
        return itemCatList;
    }

    @Override
    public ItemCat findOne(Long id) {
        ItemCat itemCat = itemCatDao.selectByPrimaryKey(id);

        return itemCat;
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }
}
