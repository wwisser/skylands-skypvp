package me.skylands.skypvp;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
@ToString
public enum PremiumRank {

    GOLD("gold", 10000, Material.GOLD_INGOT, "§e§l", "§8► §eKit Gold", "§8► §eBis zu 5 Inselmitglieder", "§8► §e/repair", "§8► §e/feed", "§8► §e/heal", "§8► §e/tpa", "§8► §e/tpahere"),

    DIAMOND("diamond", 20000, Material.DIAMOND, "§b§l", "§8► §7Rechte von §eGold", "§8► §bBis zu 6 Inselmitglieder", "§8► §bKit Diamond", "§8► §bFarbig auf Schildern schreiben", "§8► §b/stack", "§8► §b/workbench", "§8► §b/anvil", "§8► §b/cook"),

    EMERALD("emerald", 30000, Material.EMERALD, "§a§l", "§8► §7Rechte von §eGold§7, §bDiamond", "§8► §aBis zu 7 Inselmitglieder", "§8► §aKit Emerald", "§8► §a/invsee", "§8► §e/fly", "§8► §a/enderchest", "§8► §a/bottle", "§8► §a/fill", "§8► §a/top");
 
    public static final String[] PERMISSIONS_DEFAULT = {
        "§8► §7Prefix im Chat und Tab",
    };

    private String groupName;
    private int costs;
    private Material material;
    private String chatColor;
    private String[] permissions;

    PremiumRank(String groupName, int costs, Material material, String chatColor, String... permissions) {
        this.groupName = groupName;
        this.costs = costs;
        this.material = material;
        this.chatColor = chatColor;
        this.permissions = permissions;
    }

    public String getName() {
        return StringUtils.capitalize(this.groupName);
    }

    public String getPermission() {
        return "skylands." + this.groupName;
    }

    public static PremiumRank getRank(final Player player) {
        for (int i = PremiumRank.values().length - 1; i >= 0; i--) {
            final PremiumRank rank = PremiumRank.values()[i];

            if (player.hasPermission(rank.getPermission())) {
                return rank;
            }
        }

        return null;
    }

    public static int getCurrentCosts(final Player player, final PremiumRank premiumRank) {
        if (player.hasPermission(PremiumRank.values()[PremiumRank.values().length - 1].getPermission())) {
            return 0;
        }

        final PremiumRank rank = PremiumRank.getRank(player);

        if (rank == null) {
            return premiumRank.costs;
        }

        if (premiumRank.getCosts() <= rank.getCosts()) {
            return 0;
        }

        return premiumRank.getCosts() - rank.getCosts();
    }

}
