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

    @PostMapping("/profile")
    Profile save(@RequestBody Profile profile)
    {
        return service.save(profile);
    }

    @PostMapping("/following/{offset}")
    List<Follower> findFollowing(@RequestBody List<Long> following, @PathVariable int offset)
    {
        return service.findFollowing(following,offset);
    }

    @GetMapping("/followers/{offset}")
    List<Follower> findFollowers(@RequestParam Long userId, @PathVariable int offset)
    {
        return service.findFollowers(userId,offset);
    }

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
