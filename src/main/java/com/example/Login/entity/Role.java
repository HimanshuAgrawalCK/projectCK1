package com.example.Login.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import com.example.Login.entity.DashboardPermission;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
@Entity
@Table(name="role")
@Data
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private ERole role;

    @ManyToMany
    @JoinTable(
            name = "role_dashboard",
            joinColumns =@JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name="dashboard_id")
    )
    private List<DashboardPermission> permissions;

}
