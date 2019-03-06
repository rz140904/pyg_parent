package cn.itcast.core.listener;

import cn.itcast.core.service.SolrManagerService;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/*
* 自定义监听器
* 监听来自消息服务器发送的消息,也就是商品id,根据商品id删除solr索引库中的数据
* */
public class ItemDeleteListener implements MessageListener {

    @Autowired
    private SolrManagerService solrManagerService;

    @Override
    public void onMessage(Message message) {
        ActiveMQTextMessage activeMQTextMessage=(ActiveMQTextMessage)message;
        try {
            //1.接受消息
            String goodsId = activeMQTextMessage.getText();
            //2.根据商品id删除solr索引库中的数据
            solrManagerService.deleteSolrByGoodsId(Long.parseLong(goodsId));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
