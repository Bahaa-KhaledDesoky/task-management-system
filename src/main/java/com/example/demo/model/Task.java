package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String title;
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime deadLine;
    private String priority;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignto_id",referencedColumnName = "id")
    private AppUser assignTo ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    private Project project ;

}
