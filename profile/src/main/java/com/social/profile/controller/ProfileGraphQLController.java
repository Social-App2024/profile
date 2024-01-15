package com.social.profile.controller;

import com.social.profile.entity.Profile;
import com.social.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileGraphQLController {

    @Autowired
    ProfileService service;

    @QueryMapping
    public Profile profileById(@Argument long userId)
    {
        return service.findProfileById(userId);
    }

    @SchemaMapping
    public int nfollowers(Profile profile)
    {
        return service.countFollowers(profile.getUserId());
    }

    @SchemaMapping
    public int nfollowing(Profile profile)
    {
        return profile.getFollowing().size();
    }
}
