package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

    //下面的方法需要根据用户名查到一个admin对象
    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    //登录时，SpringSecurity会自动调用该方法，并将用户名传入该方法中
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin =adminService.getAdminByUsername(username);
        if (admin==null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        //调用permissionService通过adminId来查询codes
        List<String> permissionCodes =permissionService.getCodesByAdminId(admin.getId());
        //新建一个授权对象的list
        List<GrantedAuthority>  grantedAuthorities=new ArrayList<>();
        //遍历codeList
        for (String permissionCode : permissionCodes) {
            //新建一个GrantedAuthority对象
            SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(permissionCode);
            //将SimpleGrantedAuthority添加到list中
            grantedAuthorities.add(simpleGrantedAuthority);
        }
      /*
        给用户授权
                1.权限有两种标识方式：
                 1.通过角色的方式表示：例如：ROLE_ADMIN
                 2.直接设置权限：例如:Delete,Query,Update
       */
        return new User(username,admin.getPassword(),grantedAuthorities);
//        return new User(username,admin.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
    }




}
