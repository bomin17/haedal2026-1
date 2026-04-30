package com.example.haedal.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor //생성자 생략
public class Post {
    @Id //auto로 자동생성
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne //user 하나, 게시물 여러 개
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //user entity와의 관계 나타내는 field

    @Column(name = "image_url")
    private String imageUrl;

    @Column(length = 2000)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public Post(User user, String content, String imageUrl) {
        this.user = user;
        this.imageUrl = imageUrl;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}