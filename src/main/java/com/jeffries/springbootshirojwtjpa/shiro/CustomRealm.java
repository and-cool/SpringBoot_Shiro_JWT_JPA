package com.jeffries.springbootshirojwtjpa.shiro;

import com.jeffries.springbootshirojwtjpa.dao.UserRepository;
import com.jeffries.springbootshirojwtjpa.entity.RolePermission;
import com.jeffries.springbootshirojwtjpa.entity.User;
import com.jeffries.springbootshirojwtjpa.entity.UserRole;
import com.jeffries.springbootshirojwtjpa.util.JWTUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomRealm extends AuthorizingRealm {

  @Autowired
  private UserRepository userRepository;

  /**
   * 必须重写此方法，不然会报错
   */
  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JWTToken;
  }

  /**
   * 获取授权信息
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    System.out.println("————权限认证————");
    //从 token 中获得的 username
    String username = JWTUtil.getUsername(principalCollection.toString());

    User user = userRepository.findUserByUsername(username);

    List<String> stringRoleList = new ArrayList<>();
    List<String> stringPermissionList = new ArrayList<>();

    List<UserRole> userRoles = user.getUserRoles();
    userRoles.stream().map(
        userRole -> {
          stringRoleList.add(userRole.getRole().getName());
          List<RolePermission> rolePermissions = userRole.getRole().getRolePermissions();
          rolePermissions.stream().map(
              rolePermission -> {
                stringPermissionList.add(rolePermission.getPermission().getName());
                return null;
              }
          ).collect(Collectors.toList());
          return null;
        }
    ).collect(Collectors.toList());

    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    simpleAuthorizationInfo.addRoles(stringRoleList);
    simpleAuthorizationInfo.addStringPermissions(stringPermissionList);

    System.out.println(stringPermissionList.toString());

    return simpleAuthorizationInfo;
  }

  /**
   * 获取身份验证信息
   * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
   * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
   *
   * @param authenticationToken 用户身份信息 token
   * @return 返回封装了用户信息的 AuthenticationInfo 实例
   *
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    System.out.println("————身份认证方法————");
    String token = (String) authenticationToken.getCredentials();

    String username = JWTUtil.getUsername(token);
    if(username == null || !JWTUtil.verify(token, username)) {
      throw new AuthenticationException("token认证失败");
    }
    User user = userRepository.findUserByUsername(username);

    String password = user.getPassword();
    if(null == password) {
      throw new AuthenticationException("该用户不存在");
    }
    int ban = user.getBan();
    if(ban == 1) {
      throw new AuthenticationException("该用户已被封号");
    }

    return new SimpleAuthenticationInfo(token, token, "MyRealm");

  }
}
