package me.skylands.skypvp.container.template.impl.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.bloodpoints.BloodPointsShopContainerTemplate;
import me.skylands.skypvp.container.template.impl.pve.potions.BloodpointPotionContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MelisandreContainerTemplate extends ContainerTemplate {

    private final List<String> melisandreMsgs = Arrays.asList("§7Sei gegrüßt, §e[NAME]§7!", "§7Wie geht es dir heute, §e[NAME]§7?", "§7Ich hoffe, meine Auswahl gefällt dir!", "§7Ich verwende nur die besten Zutaten!");

    public MelisandreContainerTemplate(ContainerManager containerManager) {
        super(containerManager);
    }

    @Override
    public void openContainer(Player player) {
        final BloodpointPotionContainerTemplate bloodpointPotionContainerTemplate = new BloodpointPotionContainerTemplate(SkyLands.containerManager);

        ItemStack Melisandre = new ItemBuilder(Material.SKULL_ITEM).name("§8[§dMelisandre§8]").modifyLore().add(" ").add(melisandreMsgs.get(ThreadLocalRandom.current().nextInt(0, melisandreMsgs.size())).replace("[NAME]", player.getName())).finish().build();
        SkullMeta skullMeta = (SkullMeta) Melisandre.getItemMeta();
        skullMeta.setOwner("Ossel");
        Melisandre.setItemMeta(skullMeta);

        ItemStack bpPotion = new ItemBuilder(Material.POTION).data((short) 2).name("§7§lTrank der §e§lUmwandlung").build();
        ItemMeta itemMeta = bpPotion.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(Arrays.asList(" ", "§7Oh, das ist ein ganz", "§7besonderer §eTrank§7!", " ", "§7Trinkst du ihn, erhältst du", "§aLevel§7 statt §cBlutpunkte§7!", " ", "§eDauer§7: §710 Min."));
        bpPotion.setItemMeta(itemMeta);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lMelisandre").setStorageLevel(ContainerStorageLevel.NEW);

        builder.addItem(26, Melisandre);
        builder.addAction(13, bpPotion, bloodpointPotionContainerTemplate::openContainer);

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
}
