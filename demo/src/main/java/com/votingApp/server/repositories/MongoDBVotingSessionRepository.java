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
        String sessionID = getNewSessionID();
        while(read(sessionID) != null)
            sessionID = getNewSessionID();
        votingSessionEntity.setSessionID(sessionID);
        votingSessionEntity.getResults().add(new VotingPostEntity(new VoterEntity("Daniel"), new HashMap<String, Integer>() {{
            put("1", 1);
            put("2",2);
            put("3",3);
        }}));
        votingSessionCollection.insertOne(votingSessionEntity);
        return votingSessionEntity;
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
    public Long delete(String id) { return votingSessionCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount(); }

    @Override
    public Long deleteAll() { return votingSessionCollection.deleteMany(new Document()).getDeletedCount(); }


    @Override
    public VotingSessionEntity update(String sessionID, VotingSessionEntity votingSessionEntity, boolean results) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        if(results)
            votingSessionEntity.setSessionID(sessionID);
        else
        {
            String tmp = getNewSessionID();
            while(read(tmp) != null)
                sessionID = getNewSessionID();
            votingSessionEntity.setSessionID(tmp);
        }
        votingSessionEntity.setId(read(sessionID).getId());
        return votingSessionCollection.findOneAndReplace(eq("sessionID", sessionID), votingSessionEntity, options);
    }

    @Override
    public VotingPostDTO postResults(String sessionID, VotingPostDTO votingPostDTO) {
        VotingSessionEntity vse = read(sessionID);
        vse.getResults().add(votingPostDTO.toVotingPostEntity());
        update(sessionID, vse, true);
        return votingPostDTO;
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
