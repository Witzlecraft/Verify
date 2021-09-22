package main.java.de.morrisbr.verify.commands;

import main.java.de.morrisbr.verify.VerifyPlugin;
import main.java.de.morrisbr.verify.network.events.VerifyEvent;
import main.java.de.morrisbr.verify.network.objects.EventPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerifyExecutor implements CommandExecutor {

    private final VerifyPlugin verifyPlugin;

    public VerifyExecutor(VerifyPlugin verifyPlugin) {
        this.verifyPlugin = verifyPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            EventPlayer ePlayer = new EventPlayer();
            ePlayer.setName(player.getName());
            ePlayer.setUuid(player.getUniqueId().toString());

            VerifyEvent event = new VerifyEvent();
            event.setPlayer(ePlayer);
            event.generateRandomCode();
            event.setCode(event.getCode());

            verifyPlugin.getClient().sendTCP(event);

            player.sendMessage("§aVerify: http://127.0.0.1/verify/?key=" + event.getCode());

        }


        return false;
    }
}
