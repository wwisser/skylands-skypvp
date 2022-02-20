package me.skylands.skypvp.container.template.impl.pve.potions;

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate;
import me.skylands.skypvp.container.template.impl.pve.MelisandreContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class BloodpointPotionContainerTemplate extends ContainerTemplate {

    private final String[] brewingItemLore = {" ", "§eDieses Item kannst du zum Herstellen", "§evon besonderen Tränken benutzen."};

    public BloodpointPotionContainerTemplate(ContainerManager containerManager) {
        super(containerManager);
    }

    @Override
    public void openContainer(Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lMelisandre > Tränke").setStorageLevel(ContainerStorageLevel.NEW);
        final MelisandreContainerTemplate melisandreContainerTemplate = new MelisandreContainerTemplate(SkyLands.containerManager);
        final UserService userService = SkyLands.userService;

        ItemStack Melisandre = new ItemBuilder(Material.SKULL_ITEM).name("§8[§dMelisandre§8]").modifyLore().add(" ").add("§7Bitte bringe mir folgende").add("§eZutaten.").finish().build();
        ItemStack fairyDust = new ItemBuilder(Material.GLOWSTONE_DUST).name("§7Feenstaub").modifyLore().set(brewingItemLore).finish().glow().build();
        ItemStack devilsBones = new ItemBuilder(Material.BONE).name("§cTeufelsknochen").modifyLore().set(brewingItemLore).finish().glow().build();

        ItemStack fairyDustDesc = new ItemBuilder(Material.GLOWSTONE_DUST).name("§7Feenstaub §8(§ex3§8)").glow().build();
        ItemStack devilsBonesDesc = new ItemBuilder(Material.BONE).name("§cTeufelsknochen §8(§ex1§8)").glow().build();


        SkullMeta skullMeta = (SkullMeta) Melisandre.getItemMeta();
        skullMeta.setOwner("Ossel");
        Melisandre.setItemMeta(skullMeta);

        builder.addItem(4, Melisandre);

        if(hasItem(player, fairyDust) >= 3) {
            ItemMeta itemMeta = fairyDustDesc.getItemMeta();
            itemMeta.setLore(Arrays.asList(" ", "§l§a✔", "§7Du besitzt §aalle", "§7notwendigen §eZutaten§7."));
            fairyDustDesc.setItemMeta(itemMeta);

            builder.addItem(11, fairyDustDesc);
        } else {
            ItemMeta itemMeta = fairyDustDesc.getItemMeta();
            itemMeta.setLore(Arrays.asList(" ", "§l§c✖", "§7Du besitzt §c" + String.valueOf(hasItem(player, fairyDust)) + "§7 der", "§7notwendigen §eZutaten§7."));
            fairyDustDesc.setItemMeta(itemMeta);

            builder.addItem(11, fairyDustDesc);
        }

        if(hasItem(player, devilsBones) >= 1) {
            ItemMeta itemMeta = devilsBonesDesc.getItemMeta();
            itemMeta.setLore(Arrays.asList(" ", "§l§a✔", "§7Du besitzt §aalle", "§7notwendigen §eZutaten§7."));
            devilsBonesDesc.setItemMeta(itemMeta);

            builder.addItem(15, devilsBonesDesc);
        } else {
            ItemMeta itemMeta = devilsBonesDesc.getItemMeta();
            itemMeta.setLore(Arrays.asList(" ", "§l§c✖", "§7Du besitzt §c" + String.valueOf(hasItem(player, devilsBones)) + "§7 der", "§7notwendigen §eZutaten§7."));            devilsBonesDesc.setItemMeta(itemMeta);

            builder.addItem(15, devilsBonesDesc);
        }

        ItemStack confirmTrade = new ItemBuilder(Material.POTION).data((short) 2).name("§eJetzt eintauschen!").modifyLore().add(" ").add("§7Tausche deine §eZutaten §7ein.").add("§7Kosten: §c1 Blutpunkt").finish().build();
        ItemMeta itemMeta = confirmTrade.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
        confirmTrade.setItemMeta(itemMeta);

        builder.addAction(22, confirmTrade, clicker -> {
            if(hasItem(player, devilsBones) >= 1 && hasItem(player, fairyDust) >= 3) {
                if(userService.getUser(clicker).getBloodPoints() >= 1) {
                    if(!(player.getInventory().firstEmpty() == -1)) {
                        userService.getUser(clicker).setBloodPoints(userService.getUser(clicker).getBloodPoints() - 1);

                        ItemStack fairyDustRemoval = fairyDust.clone();
                        fairyDustRemoval.setAmount(3);

                        ItemStack devilsBonesRemoval = devilsBones.clone();
                        devilsBonesRemoval.setAmount(1);

                        ItemStack bpPotion = new ItemBuilder(Material.POTION).name("§7Trank der §eUmwandlung").data((short) 2).modifyLore().add(" ").add("§7Für §e10 Minuten §7erhältst du").add("§aLevel§7 anstelle von §cBlutpunkten§7.").finish().build();
                        PotionMeta potionMeta = (PotionMeta) bpPotion.getItemMeta();
                        potionMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS);
                        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 10 * (60 * 20), 0, true, true), true);
                        bpPotion.setItemMeta(potionMeta);

                        clicker.getInventory().removeItem(fairyDustRemoval);
                        clicker.getInventory().removeItem(devilsBonesRemoval);

                        clicker.getInventory().addItem(bpPotion);
                        clicker.sendMessage(Messages.PREFIX + "§eTausch §aerfolgreich§7!");
                    } else {
                        player.sendMessage(Messages.PREFIX + "§7Dein Inventar ist §evoll§7!");
                    }
                } else {
                    clicker.sendMessage(Messages.PREFIX + "§7Du hast nicht genug §cBlutpunkte§7!");
                }
            } else {
                clicker.sendMessage(Messages.PREFIX + "§7Du besitzt §enicht§7 die erforderlichen §eZutaten§7!");
            }
        });

        builder.addAction(26, new ItemBuilder(Material.BARRIER).name("§c§lZurück").build(), melisandreContainerTemplate::openContainer);

        final Container builtContainer = builder.build();

        for(int i = 0; i < builtContainer.getSize(); i++) {
            if(builtContainer.getInventory().getItem(i) == null) {
                builder.addItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE).build());
            }
        }

        final Container newContainer = builder.build();
        super.containerService.registerContainer(newContainer);

        player.openInventory(newContainer.getInventory());
    }

    private int hasItem(Player player, ItemStack query) {
        int count = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if(itemStack != null) {
                if(itemStack.getType().equals(query.getType()) && itemStack.getItemMeta().equals(query.getItemMeta())) {
                    count += itemStack.getAmount();
                }
            }
        }

        return count;
    }
}
