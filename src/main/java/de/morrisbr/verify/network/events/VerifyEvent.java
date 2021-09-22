package main.java.de.morrisbr.verify.network.events;

import main.java.de.morrisbr.verify.network.objects.EventPlayer;
import main.java.de.morrisbr.verify.utils.RandomString;

public class VerifyEvent {

    public EventPlayer player;

    public String code;
    public boolean sucess;


    public void generateRandomCode() {
//        long min = 5000000000000L;
//        long max = 100000000000000L;
//
//        this.code = (long)Math.floor(Math.random()*(max-min+1)+min);

        this.code = RandomString.getAlphaNumericString(20);

        System.out.println("Code generation sucess! (Code: " + code + ")");
    }


    public EventPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EventPlayer player) {
        this.player = player;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
