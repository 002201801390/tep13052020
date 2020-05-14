package br.edu.usf;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class DBConnection {
    private static final DBConnection instance = new DBConnection();

    private static final MongoClient mongoClient = new MongoClient();
    private static final MongoDatabase database = mongoClient.getDatabase("tep13052020");
    private static final MongoCollection<Document> collection = database.getCollection("Collection");

    public static DBConnection gi() {
        return instance;
    }

    private DBConnection() {
        super();
    }

    public void insert(String name, String address) {
        final Document document = new Document("name", name).append("address", address);

        collection.insertOne(document);
    }

    public List<Document> findAll() {
        final List<Document> items = new ArrayList<>();

        try (final MongoCursor<Document> cursor = collection.find().iterator()) {

            while (cursor.hasNext()) {
                final Document document = cursor.next();
                items.add(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
