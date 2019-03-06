package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seller.Seller;
import org.springframework.web.bind.annotation.RequestBody;

public interface SellerService {
    public void add( Seller seller);

    //分页查询
    public PageResult findPage(Integer page, Integer rows, Seller seller);

    //回显
    public Seller findOne(String id);
    //商家审核
    public void updateStatus(String sellerId,String status );
}
