package com.example.simpledrone.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {
    private final String username;
    private final String password;

    public UserRealm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        if (!username.equals(userToken.getUsername())) {
            throw new AuthenticationException("Bad username or password");
        }
        if (!password.equals(new String(userToken.getPassword()))) {
            throw new AuthenticationException("Bad username or password");
        }
        return new SimpleAuthenticationInfo(username, userToken.getPassword(), getName());
    }
}
