package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.combat.CombatService
import me.skylands.skypvp.config.PeaceConfig
import me.skylands.skypvp.nms.ActionBar
import me.skylands.skypvp.pve.Helper
import me.skylands.skypvp.stats.LastHitCache
import me.skylands.skypvp.user.UserService
import org.bukkit.Bukkit
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EntityDamageByEntityListener : Listener {

    private val peaceConfig: PeaceConfig = SkyLands.peaceConfig
    private val userService: UserService = SkyLands.userService
    private val helper: Helper = Helper()

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.entity.world == SkyLands.WORLD_SKYPVP || event.entity.world == SkyLands.WORLD_SKYBLOCK) {
            val damager = event.damager
            val user = if (damager is Player) userService.getUser(damager) else null
            val entity = event.entity

            if (event.entity !is Player) {
                if (damager is Player) {
                    when (user?.increasedMobDamageLevel) {
                        1 -> event.damage = event.damage + (event.damage * 0.1)
                        2 -> event.damage = event.damage + (event.damage * 0.2)
                        3 -> event.damage = event.damage + (event.damage * 0.3)
                    }

                    if (entity is LivingEntity) {
                        if (event.finalDamage >= entity.health) {
                            if (entity.type == EntityType.SPIDER) {
                                user?.spidersKilled = user?.spidersKilled!! + 1

                                when (user.spidersKilled) {

                                    10 -> {
                                        damager.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eSpinnenphobie I§7' erfolgreich abgeschlossen und §c5 Blutpunkte§7 erhalten.")
                                        if (helper.hasConverterPotion(damager.name)) {
                                            damager.level = damager.level + 5
                                            ActionBar.send("§aUmgewandelt§7! Du hast §a5 Level§7 erhalten", damager)
                                            return
                                        }
                                        user.bloodPoints = user.bloodPoints + 5
                                    }

                                    30 -> {
                                        damager.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eSpinnenphobie II§7' erfolgreich abgeschlossen und §c10 Blutpunkte§7 erhalten.")
                                        if (helper.hasConverterPotion(damager.name)) {
                                            damager.level = damager.level + 10
                                            ActionBar.send("§aUmgewandelt§7! Du hast §a10 Level§7 erhalten", damager)
                                            return
                                        }
                                        user.bloodPoints = user.bloodPoints + 10
                                    }

                                    50 -> {
                                        damager.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eSpinnenphobie III§7' erfolgreich abgeschlossen und §c20 Blutpunkte§7 erhalten.")
                                        if (helper.hasConverterPotion(damager.name)) {
                                            damager.level = damager.level + 20
                                            ActionBar.send("§aUmgewandelt§7! Du hast §a20 Level§7 erhalten", damager)
                                            return
                                        }
                                        user.bloodPoints = user.bloodPoints + 20
                                    }

                                }
                            } else if (entity is Monster) {
                                user?.mobsKilled = user?.mobsKilled!! + 1

                                when (user.mobsKilled) {

                                    50 -> {
                                        damager.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eMonsterjagd I§7' erfolgreich abgeschlossen und §c10 Blutpunkte§7 erhalten.")

                                        if (helper.hasConverterPotion(damager.name)) {
                                            damager.level = damager.level + 5
                                            ActionBar.send("§aUmgewandelt§7! Du hast §a5 Level§7 erhalten", damager)
                                            return
                                        }

                                        user.bloodPoints = user.bloodPoints + 5
                                    }

                                    100 -> {
                                        damager.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eMonsterjagd II§7' erfolgreich abgeschlossen und §c30 Blutpunkte§7 erhalten.")

                                        if (helper.hasConverterPotion(damager.name)) {
                                            damager.level = damager.level + 10
                                            ActionBar.send("§aUmgewandelt§7! Du hast §a10 Level§7 erhalten", damager)
                                            return
                                        }

                                        user.bloodPoints = user.bloodPoints + 10
                                    }

                                    500 -> {
                                        damager.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eMonsterjagd III§7' erfolgreich abgeschlossen und §c100 Blutpunkte§7 erhalten.")

                                        if (helper.hasConverterPotion(damager.name)) {
                                            damager.level = damager.level + 100
                                            ActionBar.send("§aUmgewandelt§7! Du hast §a100 Level§7 erhalten", damager)
                                            return
                                        }

                                        user.bloodPoints = user.bloodPoints + 100
                                    }

                                }
                            }
                        }
                        return
                    }

                    val victim = event.entity as Player
                    val vUser = userService.getUser(victim)

                    when (vUser.damageReductionLevel) {
                        1 -> event.damage = event.damage - (event.damage * 0.03)
                        2 -> event.damage = event.damage - (event.damage * 0.06)
                        3 -> event.damage = event.damage - (event.damage * 0.1)
                    }

                    if (damager is Player &&
                            peaceConfig.hasPeace(victim.uniqueId.toString(), damager.getUniqueId().toString())
                    ) {
                        event.isCancelled = true
                        ActionBar.send("§cDu hast mit " + victim.name + " Frieden geschlossen.", damager)
                    }
                    if (damager !is Projectile) {
                        return
                    }
                    if (damager.shooter is Player) {
                        val shooter = damager.shooter as Player
                        if (shooter !== victim &&
                                peaceConfig.hasPeace(victim.uniqueId.toString(), shooter.uniqueId.toString())
                        ) {
                            event.isCancelled = true
                            ActionBar.send("§cDu hast mit " + victim.name + " Frieden geschlossen.", shooter)
                        }
                    }
                }
            }
        }
    }

    /*@EventHandler
    fun onEntityDamageByEntityBlood(event: EntityDamageByEntityEvent) {
        if (event.entity is Player && event.damager is Player) {
            val player = event.entity as Player
            player.world.playEffect<Int>(event.damager.location, Effect.STEP_SOUND, 152)
        }
    }*/

    @EventHandler
    fun onEntityDamageByEntityStats(event: EntityDamageByEntityEvent) {
        if (event.entity.world != SkyLands.WORLD_SKYPVP || event.entity !is Player) {
            return
        }
        val damager = event.damager
        val victim = event.entity as Player
        if (damager is Player && damager !== victim) {
            LastHitCache.lastHits.put(victim, damager)
        }
        if (damager !is Projectile) {
            return
        }
        val projectile = damager
        if (projectile.shooter is Player) {
            val shooter = projectile.shooter as Player

            if (shooter !== victim) {
                LastHitCache.lastHits.put(victim, shooter)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onEntityDamageByEntityCombatLog(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) {
            return
        }
        val attacker = event.damager
        val victim = event.entity as Player
        if (attacker is Player) {
            CombatService.setFighting(attacker)
            CombatService.setFighting(victim)
        }
        if (attacker !is Projectile) {
            return
        }
        if (attacker.shooter is Player && attacker.shooter !== victim) {
            CombatService.setFighting(attacker.shooter as Player)
            CombatService.setFighting(victim)
        }
    }
}
