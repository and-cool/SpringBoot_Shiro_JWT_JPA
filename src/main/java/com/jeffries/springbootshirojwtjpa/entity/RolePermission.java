package com.jeffries.springbootshirojwtjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "s_role_permission")
@EqualsAndHashCode(exclude = {"role", "permission"})
public class RolePermission {

  @Id
  private Integer id;

  @ManyToOne(targetEntity = Role.class)
  @JoinColumn(name = "role_id", referencedColumnName = "id")
  @JsonIgnore
  private Role role;

  @ManyToOne(targetEntity = Permission.class)
  @JoinColumn(name = "permission_id", referencedColumnName = "id")
  private Permission permission;

}
