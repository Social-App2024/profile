package com.social.profile.service;

import com.social.profile.dto.Follower;
import com.social.profile.entity.Profile;
import com.social.profile.repo.ProfileRepo;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    /**
     * Find details about the first 50 of the Following List (Username, userid)
     * @param following list of following userids of a specific profile
     * @param offset internally used for scrolling, start at -1
     * @return
     */
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

    /**
     * Find details about the first 50 followers of a userid
     * @param userId
     * @param offset internally used for scrolling
     * @return
     */
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
            profile=repo.save(profile);
            //send notification
//            RabbitTemplate template = new RabbitTemplate(); // using default no-name Exchange
//            template.setRoutingKey("queue.helloWorld"); // but we'll always send to this Queue
//            template.send(new Message("Hello World".getBytes()));
            //send notification 2
            ConnectionFactory connectionFactory = new CachingConnectionFactory();
            AmqpAdmin admin = new RabbitAdmin(connectionFactory);
            admin.declareQueue(new Queue("follow"));
            AmqpTemplate template = new RabbitTemplate(connectionFactory);
            template.convertAndSend("follow", mapProfileToFollower(repo.findByUserId(followId).get()));
            //String foo = (String) template.receiveAndConvert("myqueue");
            return profile;
        }
    }

    private Follower mapProfileToFollower(Profile profile)
    {
        if(profile==null)
            return null;
        return new Follower(profile.getUserId(),profile.getName());
    }
}
