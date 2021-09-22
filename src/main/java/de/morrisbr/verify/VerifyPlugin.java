package main.java.de.morrisbr.verify;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import main.java.de.morrisbr.verify.commands.VerifyExecutor;
import main.java.de.morrisbr.verify.mongodb.MongoManager;
import main.java.de.morrisbr.verify.network.events.BankPayEvent;
import main.java.de.morrisbr.verify.network.events.VerifyEvent;
import main.java.de.morrisbr.verify.network.objects.EventPlayer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class VerifyPlugin extends JavaPlugin {

    private final Client client = new Client();
    private final MongoManager database = new MongoManager();

    @Override
    public void onEnable() {
        getCommand("verify").setExecutor(new VerifyExecutor(this));

        Kryo kryo = client.getKryo();
        kryo.register(BankPayEvent.class);
        kryo.register(VerifyEvent.class);
        kryo.register(EventPlayer.class);

        client.start();
        try {
            client.connect(5000, "localhost", 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.addListener(new Listener() {
            public void received(Connection connection, Object object) {

                if (object instanceof VerifyEvent) {
                    VerifyEvent event = ((VerifyEvent) object);

                    if (event.sucess) {
                        Bukkit.broadcastMessage("§aDer Spieler §a" + event.getPlayer().getName() + " §ahat sich erfolgreich registriert!");
                    }
                }

            }

        });
    }

    @Override
    public void onDisable() {

    }

    public Client getClient() {
        return client;
    }

    public MongoManager getDatabase() {
        return database;
    }


//    public static void main(String[] args) {
//    	
//        Kryo kryo = client.getKryo();
//        kryo.register(BankPayEvent.class);
//        kryo.register(VerifyEvent.class);
//        kryo.register(EventPlayer.class);
//       
//        client.start();
//        try {
//			client.connect(5000, "localhost", 54555, 54777);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    	
//    	
//       	 
//        
//   	    VerifyEvent verifyEvent = new VerifyEvent();
//   	    verifyEvent.setPlayer(e);
//   	
//   
//        verifyEvent.generateRandomCode();
//        
//
////        if(verifyEvent.getCode() <= 0) {
////            System.out.println("Es wurde kein Code generiert!");
////            return;
////        }
//
//        
//        getClient().sendTCP(verifyEvent);
//        System.out.println(e.getName() + "`s Verify event wurde gesendet!");
//        
//        while(true) {
//        	
//        }
//	}
}
