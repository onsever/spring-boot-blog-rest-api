package com.onurcansever.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "body", nullable = false)
    private String body;

    // Many comments can belong to one post. (Bidirectional relationship)
    @ManyToOne(fetch = FetchType.LAZY)

    // Join column is the foreign key column in the comments table.
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
