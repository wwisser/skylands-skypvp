package me.skylands.skypvp.task.pve;

import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Rewards;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SpawnChestTask extends BukkitRunnable {

    private Location chestLoc;
    private final Location totemCenterLoc;
    private int timePassed = 0;
    private final Particles_1_8 particleHandle = SkyLands.particleAPI.getParticles_1_8();
    private boolean chestSpawned = false;
    private final List<Block> blockList = new ArrayList<>();
    private final Rewards rewards = new Rewards();

    public SpawnChestTask(int bossID) {
        BossTracker bossTracker = new BossTracker();
        this.totemCenterLoc = bossTracker.getTotemCenterLocation(bossID);
        this.chestLoc = totemCenterLoc.clone();
        this.chestLoc.setY(this.chestLoc.getY() + 17);
        this.chestLoc.setZ(this.chestLoc.getZ() + 5);
    }

    @Override
    public void run() {

        if (!chestSpawned) {

            Location centerLoc = totemCenterLoc.clone();
            centerLoc.setY(totemCenterLoc.getY() + 10);
            centerLoc.setZ(totemCenterLoc.getZ() + 5);

            switch (this.timePassed) {
                case 1:
                    setBlockAt(centerLoc, Material.IRON_BLOCK);
                    break;
                case 3:
                    Location aLoc = centerLoc.clone();
                    aLoc.setZ(centerLoc.getZ() + 1);
                    setBlockAt(aLoc, Material.SMOOTH_STAIRS);

                    break;
                case 4:
                    Location bLoc = centerLoc.clone();
                    bLoc.setZ(centerLoc.getZ() - 1);
                    setBlockAt(bLoc, Material.SMOOTH_STAIRS);

                    Block block1 = Bukkit.getWorld(bLoc.getWorld().getName()).getBlockAt(bLoc);
                    setDirection(block1, BlockFace.SOUTH);
                    break;
                case 5:
                    Location cLoc = centerLoc.clone();
                    cLoc.setX(centerLoc.getX() + 1);
                    setBlockAt(cLoc, Material.SMOOTH_STAIRS);

                    Block block2 = Bukkit.getWorld(cLoc.getWorld().getName()).getBlockAt(cLoc);
                    setDirection(block2, BlockFace.WEST);
                    break;
                case 6:
                    Location dLoc = centerLoc.clone();
                    dLoc.setX(centerLoc.getX() - 1);
                    setBlockAt(dLoc, Material.SMOOTH_STAIRS);

                    Block block3 = Bukkit.getWorld(dLoc.getWorld().getName()).getBlockAt(dLoc);
                    setDirection(block3, BlockFace.EAST);
                    break;
                case 7:
                    setBlockAt(chestLoc, Material.CHEST);
                    break;
            }

            if (timePassed >= 12 && timePassed <= 17) {
                descendChest();

                if (timePassed == 17) {
                    chestSpawned = true;
                    spawnLadders();
                    fillChest();
                }
            }

        } else {
            Object object = particleHandle.SPELL_MOB().packetColored(true, this.chestLoc, Color.BLUE);
            for (int i = 0; i < 10; i++) {
                particleHandle.sendPacket(this.chestLoc, 30, object);
            }
        }

        if (timePassed == 90) {
            clearChest();
            deleteBlocks();
            this.cancel();
        }

        this.timePassed++;
    }

    private void setBlockAt(Location location, Material material) {
        location.getWorld().getBlockAt(location).setType(material, true);
        blockList.add(location.getWorld().getBlockAt(location));
    }

    private void descendChest() {
        setBlockAt(chestLoc, Material.AIR);

        Location caLoc = chestLoc.clone();
        caLoc.setY(caLoc.getY() - 1);
        this.chestLoc = caLoc;

        setBlockAt(chestLoc, Material.CHEST);
    }

    private void setDirection(Block block, BlockFace blockFace) {
        BlockState blockState = block.getState();
        MaterialData materialData = blockState.getData();

        if (materialData instanceof Directional) {
            ((Directional) materialData).setFacingDirection(blockFace);
        }

        blockState.setData(materialData);
        blockState.update();
        block.setData(blockState.getRawData());
    }

    private void deleteBlocks() {
        blockList.forEach(
                block -> SkyLands.WORLD_SKYPVP.getBlockAt(block.getLocation()).setType(Material.AIR)
        );
    }

    private void spawnLadders() {
        Location loc = this.totemCenterLoc.clone();
        World world = loc.getWorld();
        loc.setZ(loc.getZ() + 3);
        loc.setY(loc.getY() + 10);

        for (int i = 0; i <= 10; i++) {
            Location ladderLoc = loc.clone();
            ladderLoc.setY(ladderLoc.getY() - i);
            world.getBlockAt(ladderLoc).setType(Material.LADDER, false);
            blockList.add(world.getBlockAt(ladderLoc));
        }
    }

    private void fillChest() {
        Block cBlock = SkyLands.WORLD_SKYPVP.getBlockAt(chestLoc);
        if (cBlock.getState() instanceof Chest) {
            Chest chest = (Chest) cBlock.getState();
            Inventory cInv = chest.getInventory();

            List<ItemStack> itemList = rewards.fetchItems(7);
            itemList.forEach(
                    itemStack -> chest.getInventory().setItem(ThreadLocalRandom.current().nextInt(0, cInv.getSize()), itemStack)
            );
        }
    }

    private void clearChest() {
        Block cBlock = SkyLands.WORLD_SKYPVP.getBlockAt(chestLoc);
        if(cBlock.getState() instanceof Chest) {
            ((Chest) cBlock.getState()).getInventory().clear();
        }
    }
}