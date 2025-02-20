package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "projects")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("workOn")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String projectname;
    private String description;
    private Date createdAt;
    private Date deadLine;
    @ManyToOne
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private AppUser projectOwner ;


}
