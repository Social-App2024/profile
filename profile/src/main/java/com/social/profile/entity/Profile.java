package com.social.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("profile")
public class Profile {
    @Id
    String id;
    long userId;
    String name;
    String username;
    String password;
    String token;
    String country;
    String city;
    LocalDate birthday;
    List<Long> following;
}
