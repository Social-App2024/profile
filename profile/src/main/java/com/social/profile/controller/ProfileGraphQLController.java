package com.social.profile.controller;

import com.social.profile.entity.Profile;
import com.social.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

/**
 * The main class for receiving GraphQL API calls
 */
@RestController
@CrossOrigin
public class ProfileGraphQLController {

    @Autowired
    ProfileService service;

    /**
     * Get profile details, including no. of following and no. of followers
     * @param userId
     * @return
     */
    @QueryMapping
    public Profile profileById(@Argument long userId)
    {
        return service.findProfileById(userId);
    }

    /**
     * Used by above method to get the no. of followers
     * @param profile
     * @return
     */
    @SchemaMapping
    public int nfollowers(Profile profile)
    {
        return service.countFollowers(profile.getUserId());
    }

    /**
     * Internally used to get the no. of following
     * @param profile
     * @return
     */
    @SchemaMapping
    public int nfollowing(Profile profile)
    {
        return profile.getFollowing().size();
    }
}
