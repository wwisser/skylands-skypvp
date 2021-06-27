package me.skylands.skypvp.container.template.impl.bloodpoints;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.container.Container;
import me.skylands.skypvp.container.ContainerManager;
import me.skylands.skypvp.container.ContainerStorageLevel;
import me.skylands.skypvp.container.action.ContainerAction;
import me.skylands.skypvp.container.template.ContainerTemplate;
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate;
import me.skylands.skypvp.item.ItemBuilder;
import me.skylands.skypvp.user.User;
import me.skylands.skypvp.user.UserService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ChallengesShopContainerTemplate extends ContainerTemplate {

    private final ShopContainerTemplate shopContainerTemplate;
    private final ASkyBlockAPI aSkyBlockApi = ASkyBlockAPI.getInstance();

    public ChallengesShopContainerTemplate(ContainerManager containerManager, final ShopContainerTemplate shopContainerTemplate) {
        super(containerManager);
        this.shopContainerTemplate = shopContainerTemplate;
    }

    @Override
    public void openContainer(Player player) {
        UserService userService = SkyLands.userService;
        User user = userService.getUser(player);

        String blocksPlacedChallengeName = user.getBlocksPlaced() < 500 ? "§eBaumeister I" : (user.getBlocksPlaced() >= 500 && user.getBlocksPlaced() <= 1000) ? "§eBaumeister II" : (user.getBlocksPlaced() > 1000 && user.getBlocksPlaced() < 10000) ? "§eBaumeister III" : "§eBaumeister III";
        int blocksPlacedChallengeDesc = user.getBlocksPlaced() < 500 ? 500 : (user.getBlocksPlaced() >= 500 && user.getBlocksPlaced() <= 1000) ? 1000 : (user.getBlocksPlaced() > 1000 && user.getBlocksPlaced() < 10000) ? 10000 : -1;
        int blocksPlacedChallengeReward = user.getBlocksPlaced() < 500 ? 10 : (user.getBlocksPlaced() >= 500 && user.getBlocksPlaced() <= 1000) ? 50 : (user.getBlocksPlaced() > 1000 && user.getBlocksPlaced() < 10000) ? 100 : -1;
        ItemStack BlocksPlacedChallenge = new ItemBuilder(Material.REDSTONE_TORCH_ON).name(blocksPlacedChallengeName).modifyLore().add("").add(blocksPlacedChallengeDesc == -1 ? "§7-> §cMaximalstufe erreicht §7<-" : "§7Setze §e" + blocksPlacedChallengeDesc + " §7Blöcke.").add(blocksPlacedChallengeDesc == -1 ? " " : "§7Fortschritt: §e" + user.getBlocksPlaced() + "§7/" + "§e" + blocksPlacedChallengeDesc).add("").add(blocksPlacedChallengeReward == -1 ? " " : "§eBelohnung§7:§c " + blocksPlacedChallengeReward + " Blutpunkte").finish().build();

        String woodChopperChallengeName = user.getWoodChopped() < 100 ? "§eHolzfäller I" : (user.getWoodChopped() >= 100 && user.getWoodChopped() <= 250) ? "§eHolzfäller II" : (user.getWoodChopped() > 250 && user.getWoodChopped() < 500) ? "§eHolzfäller III" : "§eHolzfäller III";
        int woodChopperChallengeDesc = user.getWoodChopped() < 100 ? 100 : (user.getWoodChopped() >= 100 && user.getWoodChopped() <= 250) ? 250 : (user.getWoodChopped() > 250 && user.getWoodChopped() < 500) ? 500 : -1;
        int woodChopperChallengeReward = user.getWoodChopped() < 100 ? 15 : (user.getWoodChopped() >= 100 && user.getWoodChopped() <= 250) ? 30 : (user.getWoodChopped() > 250 && user.getWoodChopped() < 500) ? 50 : -1;
        ItemStack WoodChopperChallenge = new ItemBuilder(Material.WOOD_AXE).name(woodChopperChallengeName).modifyLore().add("").add(woodChopperChallengeDesc == -1 ? "§7-> §cMaximalstufe erreicht §7<-" : "§7Baue §e" + woodChopperChallengeDesc + " §7Holz ab.").add(woodChopperChallengeDesc == -1 ? " " : "§7Fortschritt: §e" + user.getWoodChopped() + "§7/" + "§e" + woodChopperChallengeDesc).add("").add(woodChopperChallengeReward == -1 ? " " : "§eBelohnung§7:§c " + woodChopperChallengeReward + " Blutpunkte").finish().build();

        ItemMeta itemMeta = WoodChopperChallenge.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        WoodChopperChallenge.setItemMeta(itemMeta);

        String spiderKillingChallengeName = user.getSpidersKilled() < 10 ? "§eSpinnenphobie I" : (user.getSpidersKilled() >= 10 && user.getSpidersKilled() <= 30) ? "§eSpinnenphobie II" : (user.getSpidersKilled() > 30 && user.getSpidersKilled() < 50) ? "§eSpinnenphobie III" : "§eSpinnenphobie III";
        int spiderKillingChallengeDesc = user.getSpidersKilled() < 10 ? 10 : (user.getSpidersKilled() >= 10 && user.getSpidersKilled() <= 30) ? 30 : (user.getSpidersKilled() > 30 && user.getSpidersKilled() < 50) ? 50 : -1;
        int spiderKillingChallengeReward = user.getSpidersKilled() < 10 ? 5 : (user.getSpidersKilled() >= 10 && user.getSpidersKilled() <= 30) ? 10 : (user.getSpidersKilled() > 30 && user.getSpidersKilled() < 50) ? 20 : -1;
        ItemStack SpiderKillingChallenge = new ItemBuilder(Material.STRING).name(spiderKillingChallengeName).modifyLore().add("").add(spiderKillingChallengeDesc == -1 ? "§7-> §cMaximalstufe erreicht §7<-" : "§7Töte §e" + spiderKillingChallengeDesc + " §7Spinnen.").add(spiderKillingChallengeDesc == -1 ? " " : "§7Fortschritt: §e" + user.getSpidersKilled() + "§7/" + "§e" + spiderKillingChallengeDesc).add("").add(spiderKillingChallengeReward == -1 ? " " : "§eBelohnung§7:§c " + spiderKillingChallengeReward + " Blutpunkte").finish().build();

        String mobKillingChallengeName = user.getMobsKilled() < 50 ? "§eMonsterjagd I" : (user.getMobsKilled() >= 50 && user.getMobsKilled() <= 100) ? "§eMonsterjagd II" : (user.getWoodChopped() > 100 && user.getWoodChopped() < 500) ? "§eMonsterjagd III" : "§eMonsterjagd III";
        int mobKillingChallengeDesc = user.getMobsKilled() < 50 ? 50 : (user.getMobsKilled() >= 50 && user.getMobsKilled() <= 100) ? 100 : (user.getMobsKilled() > 100 && user.getMobsKilled() < 500) ? 500 : -1;
        int mobKillingChallengeReward = user.getMobsKilled() < 50 ? 10 : (user.getMobsKilled() >= 50 && user.getMobsKilled() <= 100) ? 30 : (user.getWoodChopped() > 100 && user.getWoodChopped() < 500) ? 100 : -1;
        ItemStack MobKillingChallenge = new ItemBuilder(Material.ROTTEN_FLESH).name(mobKillingChallengeName).modifyLore().add("").add(mobKillingChallengeDesc == -1 ? "§7-> §cMaximalstufe erreicht §7<-" : "§7Töte §e" + mobKillingChallengeDesc + " §7Monster.").add(mobKillingChallengeDesc == -1 ? " " : "§7Fortschritt: §e" + user.getMobsKilled() + "§7/" + "§e" + mobKillingChallengeDesc).add("").add(mobKillingChallengeReward == -1 ? " " : "§eBelohnung§7:§c " + mobKillingChallengeReward + " Blutpunkte").finish().build();

        ItemStack IslandLevelChallenge = new ItemBuilder(Material.NETHER_STAR).name("§eUnfassbar!").modifyLore().add("").add(user.getIslandLevelChallengeCompleted() ? "§7-> §cBereits abgeschlossen §7<-" : "§7Erreiche ein Insellevel von §e1000").add(user.getIslandLevelChallengeCompleted() ? " " : "§7Fortschritt: §e" + this.aSkyBlockApi.getLongIslandLevel(player.getUniqueId()) + "§7/" + "§e1000").add("").add(user.getIslandLevelChallengeCompleted() ? " " : "§eBelohnung§7:§c 250 Blutpunkte").finish().build();

        ItemStack TeamworkChallenge = new ItemBuilder(Material.SKULL_ITEM).name("§eTeamwork").modifyLore().add("").add(user.getTeamWorkChallengeCompleted() ? "§7-> §cBereits abgeschlossen §7<-" : "§7Erreiche §e3§7 Inselmitglieder").add(user.getTeamWorkChallengeCompleted() ? " " : "§7Fortschritt: §e" + this.aSkyBlockApi.getIslandOwnedBy(player.getUniqueId()).getMembers().size() + "§7/" + "§e3").add("").add(user.getTeamWorkChallengeCompleted() ? " " : "§eBelohnung§7:§c 20 Blutpunkte").finish().build();
        SkullMeta skullMeta = (SkullMeta) TeamworkChallenge.getItemMeta();
        TeamworkChallenge.setDurability((short)3);
        skullMeta.setOwner(player.getName());
        TeamworkChallenge.setItemMeta(skullMeta);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lChallenges")
                .setStorageLevel(ContainerStorageLevel.NEW);

        builder.addAction(2, TeamworkChallenge, ContainerAction.NONE);
        builder.addAction(6, IslandLevelChallenge, ContainerAction.NONE);
        builder.addAction(19, SpiderKillingChallenge, ContainerAction.NONE);
        builder.addAction(21, MobKillingChallenge, ContainerAction.NONE);
        builder.addAction(23, BlocksPlacedChallenge, ContainerAction.NONE);
        builder.addAction(25, WoodChopperChallenge, ContainerAction.NONE);

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
    }
}