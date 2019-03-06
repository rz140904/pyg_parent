package cn.itcast.core.controller;


import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.GoodsService;
import cn.itcast.core.service.SolrManagerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

/*    @Reference
    private SolrManagerService solrManagerService;

    @Reference
    private CmsService cmsService;*/

    @RequestMapping("search")
    public PageResult search(Integer page, Integer rows, @RequestBody Goods goods){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(userName);
        PageResult pageResult = goodsService.findPage(page, rows, goods);
        return pageResult;
    }


    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids,String status){
        try {
            //1.根据商品id,修改数据库中商品的上架状态
            goodsService.updateStatus(ids,status );
/*            //判断审核通过则执行商品上架操作
            if ("1".equals(status)){
                if (ids!=null){
                    for (Long goodsId : ids) {
                        //2.根据商品id 将商品的库存数据放入solr索引库中供前台搜索使用
                        solrManagerService.importItemToSolr(goodsId);
                        //3.根据商品id,获取商品详情,商品,库存集合等数据,然后根据模板生成静态化页面,
                        Map<String, Object> goods = cmsService.findGoods(goodsId);
                        //生成
                        cmsService.createStaticPage(goodsId,goods);
                    }
                }
            }*/
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    @RequestMapping("/delete")
    public Result dele(Long[] ids){
        try {
/*            //1.根据商品ID到数据库中逻辑删除商品
            goodsService.delete(ids);
            //2.根据商品id到solr索引库中删除对应的数据
            if (ids!=null){
                for (Long goodsId : ids) {
                    solrManagerService.deleteSolrByGoodsId(goodsId);
                }
            }*/
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
}
