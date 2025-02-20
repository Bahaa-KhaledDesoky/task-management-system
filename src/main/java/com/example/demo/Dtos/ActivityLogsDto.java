package com.example.demo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ActivityLogsDto {
    private String action;
    private String entity;
    private Integer idEntity;
    private LocalDateTime createdAt;
    private Integer logOwner ;
}
