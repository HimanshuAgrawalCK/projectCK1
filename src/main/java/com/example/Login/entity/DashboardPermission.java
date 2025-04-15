package com.example.Login.entity;


import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "dashboard")
public class DashboardPermission
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dashboards;

    private String path;



}
