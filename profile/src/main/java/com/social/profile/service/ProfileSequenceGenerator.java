package com.social.profile.service;

import com.social.profile.entity.ProfileSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ProfileSequenceGenerator {
    @Autowired
    private MongoOperations op;

    public long generateNextUserId()
    {
        //Criteria criteria = new Criteria("_id").is("sequence");
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("profileseq"));

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        options.upsert(true);

        ProfileSequence count=op.findAndModify(
                //new Query(criteria),
                query,
                new Update().inc("userid",1),
                options,
                ProfileSequence.class
        );

        return count.getUserid();
    }
}
