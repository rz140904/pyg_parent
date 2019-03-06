package cn.itcast.core.service;


import cn.itcast.core.pojo.seller.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/*
* 自定义权限管理实现类
* */
public class UserDetailServiceImpl implements UserDetailsService {

    //使用get set 方法注入
    private SellerService sellerService;
    //set方法注入
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    /**
     *
     * @param username 用户名为seller表的主键ID
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //创建权限集合
        List<GrantedAuthority> authorityList=new ArrayList<GrantedAuthority>();
        //给权限集合加入对应的访问权限
        authorityList.add(new SimpleGrantedAuthority("ROLE_SELLER"));


        //1.根据用户名到数据库查询对应的商家对象
        Seller seller = sellerService.findOne(username);
        //2.如果商家对象能找到,则判断商家审核状态是否已审核
        if (seller!=null){
            //3.如果已审核则返回springsecurity的user对象
            if ("1".equals(seller.getStatus())){
                //user内传入三个参数,用户名,密码,和权限集合
                return new User(username,seller.getPassword(),authorityList);
            }
        }
            return null;
    }
}
