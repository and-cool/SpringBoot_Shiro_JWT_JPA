package com.jeffries.springbootshirojwtjpa.dao;

import com.jeffries.springbootshirojwtjpa.entity.RolePermission;
import com.jeffries.springbootshirojwtjpa.entity.User;
import com.jeffries.springbootshirojwtjpa.entity.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DaoTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  void testRepository() {
//    User user = userRepository.findById(2).get();
//    System.out.println(user.getUserRoles().get(0).getRole().getName());

    User user = userRepository.findUserByUsername("jeffries");

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

    System.out.println(stringRoleList.toString());
    System.out.println(stringPermissionList.toString());
  }
}
