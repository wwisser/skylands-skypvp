package me.skylands.skypvp.pve;

import me.skylands.skypvp.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Rewards {

    private final String[] brewingItemLore = {" ", "§eDieses Item kannst du zum Herstellen", "§evon besonderen Tränken benutzen."};
    private final String[] couponItemLore = {" ", "§eRechtsklick, um Gutschein einzulösen"};

    private final List<ItemStack> commonRewards = Arrays.asList(
            new ItemBuilder(Material.IRON_INGOT).amount(12).build(),
            new ItemBuilder(Material.IRON_INGOT).amount(9).build(),
            new ItemBuilder(Material.IRON_INGOT).amount(4).build(),
            new ItemBuilder(Material.DIAMOND).amount(1).build(),
            new ItemBuilder(Material.EXP_BOTTLE).amount(64).build(),
            new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build(),
            new ItemBuilder(Material.GLOWSTONE_DUST).name("§7Feenstaub").modifyLore().set(brewingItemLore).finish().glow().build(),
            new ItemBuilder(Material.GLOWSTONE_DUST).name("§7Feenstaub").amount(3).modifyLore().set(brewingItemLore).finish().glow().build(),
            new ItemBuilder(Material.ENCHANTED_BOOK).enchant(Enchantment.DAMAGE_ALL, 2).build(),
            new ItemBuilder(Material.ENCHANTED_BOOK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build()

    );

    private final List<ItemStack> uncommonRewards = Arrays.asList(
            new ItemBuilder(Material.DIAMOND).amount(4).build(),
            new ItemBuilder(Material.DIAMOND).amount(9).build(),
            new ItemBuilder(Material.DIAMOND_CHESTPLATE).build(),
            new ItemBuilder(Material.DIAMOND_LEGGINGS).build(),
            new ItemBuilder(Material.DIAMOND_BOOTS).build(),
            new ItemBuilder(Material.DIAMOND_HELMET).build(),
            new ItemBuilder(Material.ENCHANTED_BOOK).enchant(Enchantment.DAMAGE_ALL, 3).build(),
            new ItemBuilder(Material.ENCHANTED_BOOK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build()
    );

    private final List<ItemStack> rareRewards = Arrays.asList(
            new ItemBuilder(Material.BONE).amount(2).name("§cTeufelsknochen").modifyLore().set(brewingItemLore).finish().glow().build(),
            new ItemBuilder(Material.TRIPWIRE_HOOK).name("§3Katalysator").modifyLore().add(" ").add("§7Seltener §eBraugestand").add("§7Bringe diesen §eKatalysator").add("§7zu §eMelisandre.").finish().glow().build(),
            new ItemBuilder(Material.REDSTONE).amount(4).name("§cBlutpunkt").modifyLore().set(couponItemLore).finish().glow().build()
    );

    private final List<ItemStack> legendaryRewards = Arrays.asList(
            new ItemBuilder(Material.ENCHANTED_BOOK).enchant(Enchantment.DAMAGE_ALL, 5).build(),
            new ItemBuilder(Material.ENCHANTED_BOOK).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build(),
            new ItemBuilder(Material.EYE_OF_ENDER).name("§5Auge des §7Obsidiandrachens").modifyLore().set(brewingItemLore).finish().glow().build()
    );

    public List<ItemStack> fetchItems(int amount) {
        List<ItemStack> rewardList = new ArrayList<ItemStack>();

        for (int i = 1; i <= amount; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, 1000 + 1);

            if (randomNumber <= 650) {
                // Common
                rewardList.add(commonRewards.get(ThreadLocalRandom.current().nextInt(0, commonRewards.size())));
            } else if (randomNumber > 650 && randomNumber <= 850) {
                // Uncommon
                rewardList.add(uncommonRewards.get(ThreadLocalRandom.current().nextInt(0, uncommonRewards.size())));
            } else if (randomNumber > 850 && randomNumber <= 950) {
                // Rare
                rewardList.add(rareRewards.get(ThreadLocalRandom.current().nextInt(0, rareRewards.size())));
            } else if (randomNumber > 950) {
                // Legendary
                rewardList.add(legendaryRewards.get(ThreadLocalRandom.current().nextInt(0, legendaryRewards.size())));
            }
        }

        return rewardList;
    }
}
