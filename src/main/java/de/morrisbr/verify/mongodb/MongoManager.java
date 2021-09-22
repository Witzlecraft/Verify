package main.java.de.morrisbr.verify.mongodb;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.lang.reflect.Type;
import java.util.Map;

public class MongoManager {

	private final DatabaseUtil databaseUtil = new DatabaseUtil();

	public static MongoManager getInstance() {
		return new MongoManager();
	}

	public MongoDatabase getDatabase() {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(Connection.getConnectionString()));
		System.out.println("Connected to: " + mongoClient.getDatabase("Witzlecraft").getName());
		return mongoClient.getDatabase("Witzlecraft");
	}

	@SuppressWarnings("unchecked")
	public void addObjectToCollection(Object objectOne, Type type, String collectionName, Map<String, Object> toJson) {
		String json = (new Gson()).toJson(objectOne, type);
		@SuppressWarnings("unused")
		Map<String, Object> document = (Map<String, Object>) (new Gson()).fromJson(json, Map.class);
		getInstance().getDatabase().getCollection(collectionName).insertOne(new Document(toJson));
	}

	public DatabaseUtil getDatabaseUtil() {
		return databaseUtil;
	}
}
