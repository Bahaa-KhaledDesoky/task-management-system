package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String username;
    private String password;
    private String phone;
    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns =@JoinColumn(name = "userId") ,
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private List<Role> roles;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns =@JoinColumn(name = "userId") ,
            inverseJoinColumns = @JoinColumn(name = "projectId")
    )
    private List<Project> workOn;
}

