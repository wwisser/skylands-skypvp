package me.skylands.skypvp.container.template.impl.pve;

import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.data.StatsData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class StatsContainerTemplate extends ContainerTemplate {

    private Container container;
    private final Helper helper = new Helper();

    //todo level coupon use data tag of item builder
    public StatsContainerTemplate(ContainerManager containerManager, HashMap<String, StatsData> data) {
        super(containerManager);
        ItemStack slimeBoss = new ItemBuilder(Material.SLIME_BLOCK).name("§aSlimekönig").modifyLore().add(" ").add("§7Der §eSlimekönig§7 wurde besiegt!").finish().glow().build();
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lOutlands §0> §0§lStats").setStorageLevel(ContainerStorageLevel.NEW);
        List<ItemStack> amountOfPlayers = new LinkedList<ItemStack>();
        builder.addItem(4, slimeBoss);

        for (Map.Entry<String, StatsData> entry : data.entrySet()) {
            String key = entry.getKey();
            StatsData value = entry.getValue();

            ItemStack builtItem = new ItemBuilder(Material.SKULL_ITEM).name("§e" + key).data((short) 3).modifyLore().add(" ").add("§7Ausgeteilter Schaden: §e" + value.getFormattedDamage() + "❤").add("§7Anteil: §e" + value.getPercentage() + "%").finish().build();
            SkullMeta skullMeta = (SkullMeta) builtItem.getItemMeta();
            skullMeta.setOwner(key);
            builtItem.setItemMeta(skullMeta);

            amountOfPlayers.add(builtItem);
        }

        if(amountOfPlayers.size() >= 10) {
            helper.setDataStatus(0);
            return;
        } else {
            if (amountOfPlayers.size() <= 3) {
                for (int i = 0; i < amountOfPlayers.size(); i++) {
                    builder.addItem(12 + i, amountOfPlayers.get(i));
                }
            } else {
                for (int i = 0; i < amountOfPlayers.size(); i++) {
                    builder.addItem(9 + i, amountOfPlayers.get(i));
                }
            }
        }

        final Container builtContainer = builder.build();

        for(int i = 0; i < builtContainer.getSize(); i++) {
            if(builtContainer.getInventory().getItem(i) == null) {
                builder.addItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).build());
            }
        }

        final Container newContainer = builder.build();

        this.container = newContainer;
        super.containerService.registerContainer(newContainer);
    }

    @Override
    public void openContainer(Player player) {
        player.openInventory(this.container.getInventory());
    }
}


