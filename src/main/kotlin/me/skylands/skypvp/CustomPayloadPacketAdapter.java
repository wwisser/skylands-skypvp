package me.skylands.skypvp;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class CustomPayloadPacketAdapter extends PacketAdapter {

    CustomPayloadPacketAdapter(final Plugin plugin) {
        super(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.CUSTOM_PAYLOAD);
    }

    @Override
    public void onPacketReceiving(PacketEvent packetEvent) {
        Player player = packetEvent.getPlayer();
        String packetName = packetEvent.getPacket().getStrings().readSafely(0);

        if (Arrays.asList("MC|BEdit", "MC|BSign").contains(packetName)) {
            if (player.getItemInHand() != null) {
                if (!player.getItemInHand().getType().equals(Material.BOOK_AND_QUILL)) {
                    packetEvent.setCancelled(true);
                }
            } else {
                packetEvent.setCancelled(true);
            }
        }

        if (Arrays.asList("WDL|INIT", "WDL|CONTROL").contains(packetName)) {
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "banip " + player.getName() + " WorldDownloader ist verboten"
            );
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    "ban " + player.getName() + " WorldDownloader ist verboten"
            );
        }
    }

}
