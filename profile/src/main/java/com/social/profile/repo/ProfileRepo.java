package com.social.profile.repo;

import com.social.profile.entity.Profile;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The main interface for handling mongodb DB queries
 */
@Repository
public interface ProfileRepo extends MongoRepository<Profile,Long>{

    //Profile is following
    Window<Profile> findFirst50ByUserIdIn(List<Long> following,OffsetScrollPosition position);
    //followers of the Profile
    Window<Profile> findFirst50ByFollowing(Long userId, OffsetScrollPosition position);

    //friends suggestion, common following
    Window<Profile> findFirst5ByFollowingInAndUserIdNot(List<Long> following, Long userId, OffsetScrollPosition position);

    Optional<Profile> findByUserId(long userId);

    //countFollowers?0 }", count = true)
//    public Long countProfileForFollowing(Long userI
////    @Query(value = "'social.profile':{ following: d);
    //countFollowers

    public Long countByFollowing(Long userId);
}
