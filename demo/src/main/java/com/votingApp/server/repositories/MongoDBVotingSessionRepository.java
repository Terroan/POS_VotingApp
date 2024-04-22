package com.votingApp.server.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.votingApp.server.dtos.VotingPostDTO;
import com.votingApp.server.dtos.VotingSessionIngressDTO;
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
import static com.mongodb.client.model.Filters.in;
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
    public ObjectId create(VotingSessionEntity votingSessionEntity) {
        votingSessionEntity.setId(new ObjectId());
        votingSessionCollection.insertOne(votingSessionEntity);
        return votingSessionEntity.getId();
    }

    @Override
    public String startSession(ObjectId id) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null || vse.getSessionID() != null)
            return null;

        // set session id -> "start" session
        String sessionID = getNewSessionID();
        while(read(sessionID) != null)
            sessionID = getNewSessionID();
        vse.setSessionID(sessionID);
        votingSessionCollection.findOneAndReplace(eq("_id", id), vse);
        return sessionID;
    }

    @Override
    public boolean endSession(ObjectId id) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null || vse.getSessionID() == null)
            return false;

        // remove session id -> "end" session
        vse.setSessionID(null);
        votingSessionCollection.findOneAndReplace(eq("_id", id), vse);
        return true;
    }

    @Override
    public List<VotingSessionEntity> findAllById(List<ObjectId> ids) {
        return votingSessionCollection.find(in("_id", ids)).into(new ArrayList<>());
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
    public boolean delete(ObjectId id) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null)
            return false;

        // delete session
        return votingSessionCollection.deleteOne(eq("_id", id)).wasAcknowledged();
    }

    @Override
    public Long deleteAll() { return votingSessionCollection.deleteMany(new Document()).getDeletedCount(); }


    @Override
    public boolean update(Object id, VotingSessionEntity newVse) {
        // find session by id
        VotingSessionEntity vse = votingSessionCollection.find(eq("_id", id)).first();
        if(vse == null)
            return false;

        newVse.setId(vse.getId());
        votingSessionCollection.findOneAndReplace(eq("id", id), newVse);
        return true;
    }

    @Override
    public VotingPostEntity postResults(String sessionID, VotingPostEntity votingPostEntity) {
        VotingSessionEntity vse = read(sessionID);
        vse.getResults().add(votingPostEntity);
        votingSessionCollection.findOneAndReplace(eq("sessionId", sessionID), vse);
        return votingPostEntity;
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
