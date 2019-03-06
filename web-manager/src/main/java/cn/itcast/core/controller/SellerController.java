package cn.itcast.core.controller;


import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {


   @Reference
    private SellerService sellerService;

   //分页
    @RequestMapping("/search")
    public PageResult findPage(Integer page, Integer rows, @RequestBody Seller seller) {
        PageResult pageResult = sellerService.findPage(page, rows, seller);
        return pageResult;
    }

    //回显
    @RequestMapping("findOne")
    public Seller findOne(String id){

        Seller one = sellerService.findOne(id);
        return one;
    }
    //审核商家
    @RequestMapping("updateStatus")
    public Result updateStatus(String sellerId,String status ){
        try {
            sellerService.updateStatus(sellerId,status );
            return new Result(true,"状态修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"状态修改失败");
        }
    }


}

