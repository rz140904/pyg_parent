package cn.itcast.core.listener;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.service.SolrManagerService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class ItemSearchListener implements MessageListener {

    @Autowired
    private SolrManagerService solrManagerService;

    /*
    自定义监听器
    监听来自消息服务器发送的消息,在这里接受消息后对商品进行 上架操作
    * */
    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage activeMQTextMessage=(ActiveMQTextMessage)message;
        try {
            //1.接受消息
            String goodsId= activeMQTextMessage.getText();
            //2.根据商品id到数据库中查询商品的详细数据,放入solr索引库中供portal系统使用
            solrManagerService.importItemToSolr(Long.parseLong(goodsId));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
