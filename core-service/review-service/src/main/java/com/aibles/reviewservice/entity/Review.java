package com.aibles.reviewservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "review")
@EntityListeners(AuditingEntityListener.class)
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "user_uid", nullable = false)
    private String userUid;

    @Column(name = "product_uid", nullable = false)
    private String productUid;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
