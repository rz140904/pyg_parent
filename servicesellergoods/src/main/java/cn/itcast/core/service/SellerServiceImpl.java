package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {


    @Autowired
    private SellerDao sellerDao;

    @Override
    public void add(Seller seller) {
        //刚创建卖家状态默认为0
        seller.setStatus("0");
        //创建的时间
        seller.setCreateTime(new Date());
        //调用dao层的添加方法
        sellerDao.insertSelective(seller);
    }

    //分页查询
    @Override
    public PageResult findPage(Integer page, Integer rows, Seller seller) {


        //创建查询对象
        SellerQuery query = new SellerQuery();
        //创建when查询条件对象
        SellerQuery.Criteria criteria = query.createCriteria();
        if (seller!=null){
            if (seller.getStatus()!=null && !"".equals(seller.getStatus())){
                criteria.andStatusEqualTo(seller.getStatus());
            }
            if (seller.getName()!=null && !"".equals(seller.getName())){
                criteria.andNameLike("%"+seller.getName()+"%");
            }
            if (seller.getNickName()!=null && !"".equals(seller.getNickName())){
                criteria.andNickNameLike("%"+seller.getNickName()+"%");
            }
        }

        PageHelper.startPage(page,rows );
        //返回seller对象的page集合进行强制装换
       Page<Seller> sellerList = (Page<Seller>) sellerDao.selectByExample(query);

        return new PageResult(sellerList.getTotal(),sellerList.getResult());
    }

    //分页
    @Override
    public Seller findOne(String id) {
        Seller seller = sellerDao.selectByPrimaryKey(id);
        return seller;
    }

    //商家审核
    @Override
    public void updateStatus(String sellerId, String status) {
        //创建seller对象
        Seller seller = new Seller();
        seller.setSellerId(sellerId);
        seller.setStatus(status);
        //调用dao层更新seller
        sellerDao.updateByPrimaryKeySelective(seller);

    }


}
