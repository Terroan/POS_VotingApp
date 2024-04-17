package com.votingApp.server.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.models.VoterEntity;
import com.votingApp.server.models.VotingPostEntity;
import com.votingApp.server.models.VotingQuestionEntity;
import com.votingApp.server.models.VotingSessionEntity;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBVotingSessionRepository implements IVotingSessionRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    private final MongoClient client;

    private MongoCollection<VotingSessionEntity> votingSessionCollection;

    public MongoDBVotingSessionRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        votingSessionCollection = client.getDatabase("VotingApp").getCollection("sessions", VotingSessionEntity.class);
    }

    @Override
    public VotingSessionEntity create(VotingSessionEntity votingSessionEntity) {
        votingSessionEntity.setId(new ObjectId());
        votingSessionCollection.insertOne(votingSessionEntity);
        return votingSessionEntity;
    }

    @Override
    public boolean startSession(ObjectId id, VoterEntity creator) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null)
            return false;

        // check if user is correct
        if(!vse.getCreator().checkUser(creator))
            return false;

        // set session id -> "start" session
        String sessionID = getNewSessionID();
        while(read(sessionID) != null)
            sessionID = getNewSessionID();
        vse.setSessionID(sessionID);
        return true;
    }

    @Override
    public boolean endSession(ObjectId id, VoterEntity creator) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null)
            return false;

        // check if user is correct
        if(!vse.getCreator().checkUser(creator))
            return false;

        // remove session id -> "end" session
        vse.setSessionID("");
        return true;
    }

    @Override
    public List<VotingSessionEntity> findAllByUser(VoterEntity creator) {
        List<VotingSessionEntity> vses = new ArrayList<>();
        for (VotingSessionEntity vse : votingSessionCollection.find().into(new ArrayList<>())) {
            if(vse.getCreator().checkUser(creator))
                vses.add(vse);
        }
        return vses;
    }

    @Override
    public VotingSessionEntity read(String sessionID) {
        return votingSessionCollection.find(eq("sessionID", sessionID)).first();
    }

    @Override
    public List<VotingSessionEntity> readAll() {
        return votingSessionCollection.find().into(new ArrayList<>());
    }

    @Override
    public boolean delete(ObjectId id, VoterEntity creator) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null)
            return false;

        // check if user is correct
        if(!vse.getCreator().checkUser(creator))
            return false;

        // delete session
        votingSessionCollection.deleteOne(eq("_id", id));
        return true;
    }

    @Override
    public Long deleteAll() { return votingSessionCollection.deleteMany(new Document()).getDeletedCount(); }


    @Override
    public boolean update(Object id, VotingSessionEntity newVse, VoterEntity creator) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null)
            return false;

        // check if user is correct
        if(!vse.getCreator().checkUser(creator))
            return false;

        newVse.setId(vse.getId());
        votingSessionCollection.findOneAndReplace(eq("id", id), newVse);
        return true;
    }

    @Override
    public boolean postResults(String sessionID, VotingPostDTO votingPostDTO) {
        VotingSessionEntity vse = read(sessionID);
        if(vse == null)
            return false;
        vse.getResults().add(votingPostDTO.toVotingPostEntity());
        votingSessionCollection.findOneAndReplace(eq("sessionId", sessionID), vse);
        return true;
    }

    private String getNewSessionID() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
