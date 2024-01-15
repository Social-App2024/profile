package com.social.profile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profileseq")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSequence {
    private long userid;
}
