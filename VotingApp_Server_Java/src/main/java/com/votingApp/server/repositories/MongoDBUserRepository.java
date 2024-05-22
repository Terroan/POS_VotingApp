package com.votingApp.server.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import com.votingApp.server.models.VoterEntity;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

@Repository
public class MongoDBUserRepository implements IUserRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    private final MongoClient client;

    private MongoCollection<VoterEntity> userRepository;

    public MongoDBUserRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userRepository = client.getDatabase("VotingApp").getCollection("users", VoterEntity.class);
    }

    @Override
    public boolean addUser(VoterEntity user) {
        VoterEntity tmp = userRepository.find(eq("name", user.getName())).first();
        if(tmp != null)
            return false;
        if(user.getSessions() == null)
            user.setSessions(new ArrayList<>());
        userRepository.insertOne(user);
        return true;
    }

    @Override
    public VoterEntity checkUser(VoterEntity user) {
        // Find user by username
        VoterEntity tmp = userRepository.find(eq("name", user.getName())).first();
        if (tmp != null && tmp.getPassword().equals(user.getPassword())) //check user password
            return tmp;
        return null;
    }

    @Override
    public VoterEntity update(VoterEntity user) {
        return userRepository.findOneAndReplace(and(eq("name",user.getName()),eq("password",user.getPassword())), user);
    }

    @Override
    public VoterEntity findBySessionId(ObjectId id) {
        for(VoterEntity ve : userRepository.find()) {
            if(ve.getSessions().contains(id))
                return ve;
        }
        return null;
    }
}
