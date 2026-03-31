package com.example.haedal.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = false, nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String bio;

    @Column(name = "joined_at", nullable = false) //여기는 snake case
    private LocalDateTime joinedAt; //code는 camel case로 작성

    @Column(name = "image_url")
    private String imageUrl;



    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.bio = null;
        this.joinedAt = LocalDateTime.now();
        this.imageUrl = null;
    }
}
