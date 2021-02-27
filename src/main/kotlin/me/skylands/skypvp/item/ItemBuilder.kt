package me.skylands.skypvp.item

import net.minecraft.server.v1_8_R3.NBTTagCompound
import net.minecraft.server.v1_8_R3.NBTTagList
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach
import kotlin.collections.set

class ItemBuilder {

    private var material: Material? = null
    private var damage: Short = 0
    private var amount: Int = 1
    private var name: String? = null
    private var owner: String? = null
    private var itemLore: ItemLore = null
    private var enchantments: MutableMap<Enchantment, Int>? = null
    private var enchantmentStorage: MutableMap<Enchantment, Int>? = null
    private var glow = false
    private var color: Color? = null
    
    constructor(item: ItemStack) {
        material = item.type
        damage = item.data.data.toShort()
        amount = item.amount
        if (item.itemMeta.hasDisplayName()) {
            name = item.itemMeta.displayName
        }
        if (item.itemMeta.hasLore()) {
            itemLore = ItemLore(this, item.itemMeta.lore)
        }
        if (item.itemMeta.hasEnchants()) {
            enchantments = item.itemMeta.enchants
        }
    }

    constructor(material: Material) {
        this.material = material
    }

    constructor(id: Int) {
        material = Material.getMaterial(id)
    }

    fun material(material: Material?): ItemBuilder {
        this.material = material
        return this
    }

    fun id(id: Int): ItemBuilder {
        material = Material.getMaterial(id)
        return this
    }

    fun data(data: Short): ItemBuilder {
        damage = data
        return this
    }

    fun amount(amount: Int): ItemBuilder {
        var amount = amount
        if (amount > 64) {
            amount = 64
        }
        this.amount = amount
        return this
    }

    fun name(name: String?): ItemBuilder {
        this.name = name
        return this
    }

    fun modifyLore(): ItemLore {
        return ItemLore(this)
    }

    fun enchant(enchantment: Enchantment, level: Int): ItemBuilder {
        if (enchantments == null) {
            enchantments = HashMap()
        }
        enchantments!![enchantment] = level
        return this
    }

    fun enchantStorage(enchantment: Enchantment, level: Int): ItemBuilder {
        if (enchantmentStorage == null) {
            enchantmentStorage = HashMap()
        }
        enchantmentStorage!![enchantment] = level
        return this
    }

    fun glow(): ItemBuilder {
        glow = true
        return this
    }

    fun color(color: Color?): ItemBuilder {
        this.color = color
        return this
    }

    fun owner(owner: String?): ItemBuilder {
        this.owner = owner
        return this
    }

    fun build(): ItemStack? {
        var item: ItemStack = ItemStack(material!!, amount, damage)
        if (name != null) {
            val meta = item.itemMeta
            meta.displayName = name
            item.itemMeta = meta
        }
        if (itemLore != null) {
            val meta = item.itemMeta
            meta.lore = itemLore!!.toList()
            item.itemMeta = meta
        }
        if (enchantments != null) {
            enchantments!!.forEach { (ench: Enchantment?, level: Int?) ->
                item.addUnsafeEnchantment(
                    ench,
                    level
                )
            }
        } else {
            if (glow) {
                val nmsStack = CraftItemStack.asNMSCopy(item)
                var tag: NBTTagCompound? = null
                if (!nmsStack.hasTag()) {
                    tag = NBTTagCompound()
                    nmsStack.tag = tag
                }
                if (tag == null) {
                    tag = nmsStack.tag
                }
                val ench = NBTTagList()
                tag!!["ench"] = ench
                nmsStack.tag = tag
                item = CraftItemStack.asCraftMirror(nmsStack)
            }
        }
        if (enchantmentStorage != null) {
            if (item.type == Material.ENCHANTED_BOOK) {
                val storageMeta = item.itemMeta as EnchantmentStorageMeta
                enchantmentStorage!!.forEach { (enchantment: Enchantment?, level: Int?) ->
                    storageMeta.addStoredEnchant(
                        enchantment,
                        level, true
                    )
                }
                item.itemMeta = storageMeta
            }
        }
        if (color != null) {
            if (material.toString().startsWith("LEATHER_")) {
                val meta = item.itemMeta as LeatherArmorMeta
                meta.color = color
                item.itemMeta = meta
            }
        }
        if (owner != null) {
            val meta = item.itemMeta as SkullMeta
            meta.owner = owner
            item.itemMeta = meta
        }
        return item
    }

    fun setItemLore(itemLore: ItemLore) {
        this.itemLore = itemLore
    }

    class ItemLore {
        private var itemBuilder: ItemBuilder
        private var lore: MutableList<String>

        constructor(itemBuilder: ItemBuilder) {
            this.itemBuilder = itemBuilder
            lore = ArrayList()
        }

        constructor(itemBuilder: ItemBuilder, lore: MutableList<String>) {
            this.itemBuilder = itemBuilder
            this.lore = lore
        }

        fun clear(): ItemLore {
            lore.clear()
            return this
        }

        fun add(line: String): ItemLore {
            lore.add("§r$line")
            return this
        }

        fun set(lines: Array<String>): ItemLore {
            this.clear()
            for (line in lines) {
                this.add(line)
            }
            return this
        }

        fun add(lines: Array<String>): ItemLore {
            for (line in lines) {
                this.add(line)
            }
            return this
        }

        fun toList(): List<String> {
            return lore
        }

        fun finish(): ItemBuilder {
            itemBuilder.setItemLore(this)
            return itemBuilder
        }
    }
}