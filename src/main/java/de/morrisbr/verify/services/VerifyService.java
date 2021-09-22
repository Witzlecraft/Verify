package main.java.de.morrisbr.verify.services;

import com.esotericsoftware.kryonet.Client;
import main.java.de.morrisbr.verify.mongodb.MongoManager;
import main.java.de.morrisbr.verify.network.events.VerifyEvent;
import main.java.de.morrisbr.verify.network.objects.Account;
import main.java.de.morrisbr.verify.network.objects.EventPlayer;
import org.bson.Document;

public class VerifyService {

    private final MongoManager mongodbManager;
    private Client client;

    public VerifyService(MongoManager mongodbManager, Client client) {
        this.mongodbManager = mongodbManager;
        this.client = client;
    }

    public VerifyService(MongoManager mongodbManager) {
        this.mongodbManager = mongodbManager;
    }


    public void postVerify(EventPlayer player) {
        VerifyEvent verifyEvent = new VerifyEvent();
        verifyEvent.setPlayer(player);

        verifyEvent.generateRandomCode();

        if (verifyEvent.getCode() == null || verifyEvent.getCode().equals("")) {
            System.out.println("Es wurde kein Code generiert!");
            return;
        }

        client.sendTCP(verifyEvent);
        System.out.println(player.getName() + "`s Verify event wurde gesendet!");

    }

    public void registerAccount(EventPlayer eventPlayer, String password) {

        Document account = new Document("_id", eventPlayer.getName())
                .append("mcname", eventPlayer.getName())
                .append("mcuuid", eventPlayer.getUuid())
                .append("password", password);

        mongodbManager.getDatabase().getCollection("VerifyAccounts").insertOne(account);
    }

    public void registerAccount(Account account) {

        Document acc = new Document("_id", account.getUsername())
                .append("mcname", account.getUsername())
                .append("mcuuid", account.getUuid())
                .append("password", account.getPassword());
        mongodbManager.getDatabase().getCollection("VerifyAccounts").insertOne(acc);
    }


    public Document getAccountDocument(String username) {
        if (isAccountExist(username)) {
            return mongodbManager.getDatabaseUtil().getDocument("_id", username);
        }
        return null;
    }

    public Account getAccount(String username) {
        if (isAccountExist(username)) {
            Document accDoc = getAccountDocument(username);

            Account account = new Account();
            account.setUsername(accDoc.getString("mcname"));
            account.setUuid(accDoc.getString("mcuuid"));
            account.setPassword(accDoc.getString("password"));

            return account;
        }
        return null;
    }

    public boolean isAccountExist(String username) {
        return mongodbManager.getDatabaseUtil().documentExists("_id", username);
    }

    public boolean isLoginValid(String username, String password) {
        if (isAccountExist(username)) {
            String pass = (String) mongodbManager.getDatabaseUtil().getDocument("_id", username).get("password");

            return password.equals(pass);
        } else {
            return false;
        }
    }


}
