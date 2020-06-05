package com.woniuxy.springboot01.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("认证");
        String uname = "zhangsan";
        String pwd = new SimpleHash("MD5", "123", uname, 1024).toString();
        System.out.println("realm=" + pwd);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(uname, pwd, this.getName());
        return info;
    }
}
