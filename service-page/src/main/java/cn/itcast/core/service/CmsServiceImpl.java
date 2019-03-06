package cn.itcast.core.service;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.good.GoodsDescDao;
import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemQuery;
import com.alibaba.dubbo.config.annotation.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmsServiceImpl implements CmsService,ServletContextAware {

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsDescDao goodsDescDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemCatDao catDao;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    private ServletContext servletContext;

    @Override
    public void createStaticPage(Long goodsId, Map<String, Object> rootMap)throws Exception {
        //1.获取freemarker初始化对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        //2.获取模板对象,加载指定模板名称
        Template template = configuration.getTemplate("item.ftl");
        //3.定义生成后的静态化页面存放位置,使用商品id+.html组成商品页面名称;
        String path=goodsId+".html";
        //相对路径转成绝对路径  例如:goodsId.html 转为 http://localhost:8085/goodsId.html
        String url = getRealPath(path);
        //4.定义输出流
        Writer out=new OutputStreamWriter(new FileOutputStream(new File(url)),"utf-8");
        //5.生成
        template.process(rootMap,out );
        //6.关闭流
        out.close();

    }

    //获取当前项目的绝对路径
    private String getRealPath(String path){
        String realPath = servletContext.getRealPath(path);
        return realPath;
    }

    @Override
    public Map<String, Object> findGoods(Long goodsId) {

        Map<String, Object> rootMap = new HashMap<>();

        //1.根据商品id获取商品对象;
        Goods goods = goodsDao.selectByPrimaryKey(goodsId);
        //2.根据商品id获取商品详情对象;
        GoodsDesc goodsDesc = goodsDescDao.selectByPrimaryKey(goodsId);
        //3.根据商品id获取库存集合对象;
        ItemQuery query = new ItemQuery();
        ItemQuery.Criteria criteria = query.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId);
        List<Item> items = itemDao.selectByExample(query);
        //4.根据分类id获取对应三级分类对象;
        if (goods != null) {
            ItemCat itemCat1 = catDao.selectByPrimaryKey(goods.getCategory1Id());
            ItemCat itemCat2 = catDao.selectByPrimaryKey(goods.getCategory2Id());
            ItemCat itemCat3 = catDao.selectByPrimaryKey(goods.getCategory3Id());
            rootMap.put("itemCat1", itemCat1);
            rootMap.put("itemCat2", itemCat2);
            rootMap.put("itemCat3", itemCat3);
        }
        //5.将以上获取的对象封装到Map中返回;
        rootMap.put("goods", goods);
        rootMap.put("goodsDesc", goodsDesc);
        rootMap.put("itemList", items);
        return rootMap;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
