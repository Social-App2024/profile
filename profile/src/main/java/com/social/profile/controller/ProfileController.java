package com.social.profile.controller;

import com.social.profile.dto.Follower;
import com.social.profile.entity.Profile;
import com.social.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService service;

    /**
     * Create new profile
     * @param profile
     * @return
     */
    @PostMapping("/profile")
    Profile save(@RequestBody Profile profile)
    {
        return service.save(profile);
    }

    /**
     * Find details about the first 50 of the Following List (Username, userid)
     * @param following list of following userids of a specific profile
     * @param offset internally used for scrolling, start at -1
     * @return
     */
    @PostMapping("/following/{offset}")
    List<Follower> findFollowing(@RequestBody List<Long> following, @PathVariable int offset)
    {
        return service.findFollowing(following,offset);
    }

    /**
     * Find details about the first 50 followers of a userid
     * @param userId
     * @param offset internally used for scrolling, start at -1
     * @return
     */
    @GetMapping("/followers/{offset}")
    List<Follower> findFollowers(@RequestParam Long userId, @PathVariable int offset)
    {
        return service.findFollowers(userId,offset);
    }

    /**
     * Add a user to following list
     * @param profileId the follower userid
     * @param followId the following id
     * @return
     */
    @GetMapping("/follow/{followId}")
    Profile follow(@RequestParam long profileId,@PathVariable long followId)
    {
        return service.addToFollowing(profileId,followId);
    }

    @PostMapping("/suggest/{userId}")
    List<Follower> findSuggestion(@RequestBody List<Long> following,@PathVariable long userId)
    {
        return service.findSuggestions(following,userId,-1);
    }
}
