package com.social.profile.service;

import com.social.profile.dto.Follower;
import com.social.profile.entity.Profile;
import com.social.profile.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    ProfileRepo repo;
    @Autowired
    ProfileSequenceGenerator generator;
    public List<Follower> findFollowing(List<Long> following, int offset) {
        Window<Profile> users=null;
        List<Follower> followingList=new ArrayList<>();
        if(offset==-1) {
            users = repo.findFirst50ByUserIdIn(following, ScrollPosition.offset());
        }
        else{
//            users = repo.findFirst1ByUserIdIn(following, (OffsetScrollPosition) users.positionAt(offset));
            users = repo.findFirst50ByUserIdIn(following, ScrollPosition.offset(offset));
        }
//        WindowIterator<Profile> usersIt = WindowIterator.of(position -> repo.findFirst50ByUserIdIn(following, position))
//                .startingAt(OffsetScrollPosition.initial());

        //users.toList().stream().map(profile -> followingList.add(new Follower(profile.getUserId(),profile.getName())));
        followingList=users.get().map(profile -> new Follower(profile.getUserId(),profile.getName())).collect(Collectors.toList());;

        return followingList;
    }

    public Profile save(Profile profile) {
        long userId=generator.generateNextUserId();
        profile.setUserId(userId);
        return repo.save(profile);
    }

    public List<Follower> findFollowers(Long userId, int offset) {
        Window<Profile> users=null;
        List<Follower> followers=new ArrayList<>();
        if(offset==-1)
        {
            users=repo.findFirst50ByFollowing(userId,ScrollPosition.offset());
        }
        else{
//            users=repo.findFirst1ByFollowing(userId,(OffsetScrollPosition) users.positionAt(offset));
            users=repo.findFirst50ByFollowing(userId,ScrollPosition.offset(offset));
        }
        followers=users.get().map(profile -> new Follower(profile.getUserId(),profile.getName())).collect(Collectors.toList());
        return followers;
    }

    public List<Follower> findSuggestions(List<Long> following, long userId, int offset) {
        Window<Profile> users=null;
        List<Follower> followers=new ArrayList<>();
        if(offset==-1)
        {
            users=repo.findFirst5ByFollowingInAndUserIdNot(following,userId,ScrollPosition.offset());
        }
        else{
//            users=repo.findFirst1ByFollowing(userId,(OffsetScrollPosition) users.positionAt(offset));
            users=repo.findFirst5ByFollowingInAndUserIdNot(following,userId,ScrollPosition.offset(offset));
        }
        followers=users.get().map(profile -> new Follower(profile.getUserId(),profile.getName())).collect(Collectors.toList());
        return followers;
    }

    public Profile findProfileById(long userId) {
        Optional<Profile> profile= repo.findByUserId(userId);
        if(profile.isPresent())
            return profile.get();
        else
            return null;
    }

    public int countFollowers(long userId) {
      //  return repo.countProfileForFollowing(userId).intValue();
        return repo.countByFollowing(userId).intValue();
    }

    public Profile addToFollowing(long profileId, long followId) {
        Optional<Profile> profileOp = repo.findByUserId(profileId);
        Profile profile = null;
        if (profileOp.isEmpty()) {
            throw new RuntimeException("An error occured, userId is not found, maybe deleted");
        } else{
            profile=profileOp.get();
            List<Long> following = profile.getFollowing();
            if (following == null) {
                following = new ArrayList<>();
            }
            following.add(followId);
            profile.setFollowing(following);
            return repo.save(profile);
        }
    }
}
