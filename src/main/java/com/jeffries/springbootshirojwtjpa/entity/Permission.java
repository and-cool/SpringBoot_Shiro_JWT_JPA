package com.jeffries.springbootshirojwtjpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "s_permission")
@EqualsAndHashCode(exclude = "rolePermissions")
public class Permission {

  @Id
  private Integer id;

  private String name;

  private String url;

  @OneToMany(targetEntity = RolePermission.class, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "permission_id", referencedColumnName = "id")
  @JsonProperty(access = Access.READ_ONLY)
  private List<RolePermission> rolePermissions = new ArrayList<>();

}
