package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.opensaml.xml.signature.Q;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;

import java.util.List;
import java.util.Map;

@Service
public class SolrManagerServiceImpl implements SolrManagerService {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private ItemDao itemDao;

    @Override
    public void importItemToSolr(Long goodsId) {
        //1.查询库存表所有static为1审核通过的数据
        ItemQuery query=new ItemQuery();
        ItemQuery.Criteria criteria = query.createCriteria();
        //审核通过的
        criteria.andStatusEqualTo("1");
        //根据商品呢id查询
        criteria.andGoodsIdEqualTo(goodsId);
        List<Item> items = itemDao.selectByExample(query);

        //解析规格
        if (items!=null){
            for (Item item : items) {
                //获取规格json字符串
                String specJsonStr = item.getSpec();
                Map<String,String> map = JSON.parseObject(specJsonStr, Map.class);
                item.setSpecMap(map);

            }
        }

        //2.将查询到的数据导入save到solr索引库
        solrTemplate.saveBeans(items);
        //提交
        solrTemplate.commit();
    }

    @Override
    public void deleteSolrByGoodsId(Long goodsId) {
        //创建查询对象
        Query query = new SimpleQuery();
        //创建查询条件
        Criteria criteria =new Criteria("item_goodsid").is(goodsId);
        //将查询条件添加到查询对象中
       query.addCriteria(criteria);
       //根据条件删除
        solrTemplate.delete(query);
        solrTemplate.commit();

    }
}
