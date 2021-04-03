package me.skylands.skypvp.listener

import com.google.common.collect.ImmutableList
import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.delay.DelayConfig
import me.skylands.skypvp.delay.DelayService
import me.skylands.skypvp.item.ItemBuilder
import me.skylands.skypvp.item.YoloBootsItemFactory
import me.skylands.skypvp.nms.ActionBar
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Sign
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import java.util.concurrent.TimeUnit

class PlayerInteractListener : Listener {

    companion object {
        val FREE_SIGN_CONFIG: DelayConfig = DelayConfig("§cBitte warte noch 1 Sekunde(n)", TimeUnit.SECONDS.toMillis(1))
        val BOOST_FEATHER_CONFIG: DelayConfig = DelayConfig(time = TimeUnit.SECONDS.toMillis(5), message = null)

        const val PREFIX: String = "§1[Kostenlos]"

        const val CRATE_TITLE = "§0§lVoteCrate §0| /vote"


    }

    private val inAnimation: MutableList<Player> = ArrayList()
    val VOTE_WINNINGS = ImmutableList.of(
        ItemStack(Material.WOOD, 64),
        ItemStack(Material.BREWING_STAND_ITEM, 1),
        ItemStack(Material.POTION, 8),
        ItemStack(Material.POTION, 8, 16453.toShort()),
        ItemStack(Material.POTION, 1, 8265.toShort()),
        ItemStack(Material.NETHER_WARTS, 3),
        ItemStack(Material.GLASS_BOTTLE, 8),
        ItemStack(Material.POTION, 8),
        ItemStack(Material.NETHER_STALK, 3),
        ItemStack(Material.EMERALD, 32),
        ItemStack(Material.GOLDEN_APPLE, 10),
        ItemStack(Material.REDSTONE, 8),
        ItemStack(Material.GLOWSTONE_DUST, 1),
        YoloBootsItemFactory.createYoloBootsItem(),
        ItemBuilder(Material.FEATHER).name("§c§lBoostfeder").glow().build(),
        ItemBuilder(Material.IRON_SWORD)
            .enchant(Enchantment.DURABILITY, 1)
            .enchant(Enchantment.FIRE_ASPECT, 1)
            .enchant(Enchantment.KNOCKBACK, 1)
            .enchant(Enchantment.DAMAGE_ALL, 3)
            .enchant(Enchantment.DAMAGE_UNDEAD, 3)
            .enchant(Enchantment.DAMAGE_ARTHROPODS, 3)
            .enchant(Enchantment.LOOT_BONUS_MOBS, 2).build(),
        ItemBuilder(Material.DIAMOND_SWORD).build(),
        ItemBuilder(Material.DIAMOND_HELMET).build(),
        ItemBuilder(Material.DIAMOND_CHESTPLATE).build(),
        ItemBuilder(Material.DIAMOND_LEGGINGS).build(),
        ItemBuilder(Material.DIAMOND_BOOTS).build(),
        ItemBuilder(Material.GOLDEN_APPLE).amount(6).build(),
        ItemStack(Material.ENDER_PEARL, 5),
        ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).build(),
        ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
        ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
        ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
        ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build()
    )

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val itemStack = player.itemInHand

        if (itemStack != null && itemStack.hasItemMeta() && itemStack.itemMeta.hasDisplayName()
            && itemStack.itemMeta.displayName.equals("§c§lBoostfeder", ignoreCase = true)
        ) {
            DelayService.handleDelay(player, BOOST_FEATHER_CONFIG) {
                player.velocity = player.velocity.setY(1.0)
                player.playSound(player.location, Sound.ENDERDRAGON_WINGS, 1f, 1f)
            }
        }

        if (player.world != SkyLands.WORLD_SKYPVP) {
            return
        }
        if (event.action == Action.RIGHT_CLICK_BLOCK
            && event.clickedBlock.state is Sign
        ) {
            val sign = event.clickedBlock.state as Sign
            if (!sign.getLine(0).equals(PREFIX, ignoreCase = true)) {
                return
            }
            val delayed: Boolean = DelayService.handleDelayInverted(player, FREE_SIGN_CONFIG) { delayedPlayer ->
                event.isCancelled = true
                player.playSound(player.location, Sound.CREEPER_HISS, 1f, 1f)
                ActionBar.send(FREE_SIGN_CONFIG.message!!, delayedPlayer)
            }
            if (delayed) {
                return
            }
            val idRaw: List<String> = ChatColor.stripColor(sign.getLine(1)).split(":")
            val id = idRaw[0].toInt()
            val sh = idRaw[1].toInt()
            var amount: Int = ChatColor.stripColor(sign.getLine(2)).toInt()
            if (amount > 64) {
                amount = 64
            }
            val item = ItemStack(id, amount, sh.toShort())
            val inventory = Bukkit.createInventory(
                null,
                3 * 9,
                sign.getLine(0) + " " + sign.getLine(3)
            )
            inventory.setItem(13, item)
            player.openInventory(inventory)
            player.playSound(player.location, Sound.SUCCESSFUL_HIT, 1f, 1f)
        }


        if (event.action.toString().contains("RIGHT_CLICK")) {
            if (event.clickedBlock != null && event.clickedBlock.type == Material.TRAPPED_CHEST) {
                event.isCancelled = true
                if (player.itemInHand != null && player.itemInHand.type == Material.TRIPWIRE_HOOK
                    && player.itemInHand.itemMeta.displayName.startsWith("§9§lVote")) {
                    if (player.itemInHand.itemMeta.hasEnchants()) {
                        if (inAnimation.contains(player)) {
                            ActionBar.send("§cEs wird bereits eine Crate geöffnet.", player)
                        } else {
                            this.runCaseAnimation(player)
                            if (player.inventory.itemInHand.amount > 1) {
                                player.inventory.itemInHand.amount = player.itemInHand.amount - 1
                            } else {
                                player.itemInHand = null
                            }
                        }
                    }
                } else {
                    player.sendMessage(Messages.PREFIX_LONG)
                    player.sendMessage("  §7Vote, um einen Schlüssel zu erhalten.")
                    val base = TextComponent()
                    base.text = "  §7Klicke §ehier§7, um für SkyLands zu voten."
                    base.hoverEvent = HoverEvent(
                        HoverEvent.Action.SHOW_TEXT, arrayOf(
                            TextComponent("§aKlicke, um zu voten")
                        )
                    )
                    base.clickEvent = ClickEvent(
                        ClickEvent.Action.OPEN_URL,
                        Messages.getVoteUrl(player.name)
                    )
                    player.spigot().sendMessage(base)
                    player.sendMessage(Messages.PREFIX_LONG)
                }
            }
        }
    }

    private fun runCaseAnimation(player: Player) {
       inAnimation.add(player)
        val inventory = Bukkit.createInventory(null, 3 * 9, CRATE_TITLE)
        inventory.setItem(4, ItemBuilder(Material.HOPPER).name("§8▼ §eDein Gewinn §8▼").build())
        for (i in 0 until inventory.size) {
            if (i < 9 || i > 17) {
                if (!(inventory.getItem(i) != null && inventory.getItem(i).type == Material.HOPPER)) {
                    inventory.setItem(
                        i, ItemBuilder(Material.STAINED_GLASS_PANE)
                            .data(15).build()
                    )
                }
            }
        }
        val items: MutableList<ItemStack> = ArrayList()
        player.openInventory(inventory)
        for (i in 0..34) {
            items.add(VOTE_WINNINGS.random()!!)
        }
        val itemQueue = LinkedList(items)
        object : BukkitRunnable() {
            var count = 0
            var ticks = 0
            var execute = 3
            override fun run() {
                if (ticks == execute) {
                    ticks = 0
                    if (player.isOnline) {
                        for (i in 8 + 9 downTo 9) {
                            if (i != 9) {
                                val previous = inventory.getItem(i - 1)
                                inventory.setItem(
                                    i,
                                    previous
                                        ?: VOTE_WINNINGS.random()!!
                                )
                            } else {
                                inventory.setItem(i, itemQueue.poll())
                            }
                        }
                        count++
                        if (player.openInventory != null &&
                            player.openInventory.topInventory.title.contains("§0§lVoteCrate")
                        ) {
                            player.playSound(player.location, Sound.NOTE_SNARE_DRUM, 1f, 1f)
                        }
                        if (count > 35) {
                            super.cancel()
                            val winning = inventory.getItem(14)
                            inventory.clear()
                            inventory.setItem(13, winning)
                            player.playSound(player.location, Sound.FIREWORK_TWINKLE, 1f, 1f)
                            player.inventory.addItem(winning)
                            inAnimation.remove(player)
                        }
                    } else {
                        super.cancel()
                    }
                    if (count > 30) {
                        execute = 15
                    } else if (count > 20) {
                        execute = 7
                    } else if (count > 25) {
                        if (count < 20) {
                            execute = 3
                        }
                    }
                }
                ticks++
            }
        }.runTaskTimer(SkyLands.plugin, 0L, 1L)
    }

}