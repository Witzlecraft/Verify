package main.java.de.morrisbr.verify.mongodb;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DatabaseUtil {

	public Document getDocument(String jsonKey, String name) {
		Bson filter = Filters.eq(jsonKey, name);
		Document document = MongoManager.getInstance().getDatabase().getCollection("MoneySystem").find(filter)
				.first();
		return document;
	}

	public boolean documentExists(String filterByKey, String name) {
		Bson filter = Filters.eq(filterByKey, name);
		Document document = MongoManager.getInstance().getDatabase().getCollection("MoneySystem").find(filter)
				.first();
		return document != null;
	}

	public DatabaseUtil addObjectToDocument(String documentKey, String documentValue, String key, String value) {
		MongoManager.getInstance().getDatabase().getCollection("MoneySystem").updateOne(
				Filters.eq(documentKey, documentValue), Updates.combine(Updates.set(key, value)));
		return this;
	}

	public DatabaseUtil changeObjectFromDocument(String documentKey, String documentValue, String key, Object value) {
		MongoManager.getInstance().getDatabase().getCollection("MoneySystem").updateOne(
				Filters.eq(documentKey, documentValue), Updates.combine(Updates.set(key, value)));
		return this;
	}

	public DatabaseUtil removeObjectFromDocument(String documentKey, String documentValue, String key) {
		MongoManager.getInstance().getDatabase().getCollection("MoneySystem")
				.updateOne(Filters.eq(documentKey, documentValue), Updates.combine(Updates.unset(key)));
		return this;
	}

}
