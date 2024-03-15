package com.votingApp.server.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.votingApp.server.models.PasswordEntity;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBPasswordRepository implements IVotingSessionPasswordRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    private final MongoClient client;

    private MongoCollection<PasswordEntity> votingSessionPasswordCollection;

    public MongoDBPasswordRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        votingSessionPasswordCollection = client.getDatabase("VotingApp").getCollection("passwords", PasswordEntity.class);
    }

    @Override
    public boolean checkPassword(String sessionID, String password) {
        PasswordEntity tmp = votingSessionPasswordCollection.find(and(eq("sessionID", sessionID), eq("password", password))).first();
        if(tmp != null)
            return true;
        return false;
    }

    @Override
    public void addEntry(String sessionID, String password) {
        PasswordEntity tm = new PasswordEntity(new ObjectId(), sessionID, password);
        votingSessionPasswordCollection.insertOne(tm);
    }

    @Override
    public void updateSessionID(String old, String neew) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        PasswordEntity pe = votingSessionPasswordCollection.find(eq("sessionID", old)).first();
        pe.setSessionID(neew);
        votingSessionPasswordCollection.findOneAndReplace(eq("sessionID", old), pe, options);

    }
}
