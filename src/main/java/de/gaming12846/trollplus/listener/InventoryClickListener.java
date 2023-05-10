/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.ItemBuilder;
import de.gaming12846.trollplus.utils.TrollGUIBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InventoryClickListener implements Listener {
    private final TrollPlus plugin;

    public InventoryClickListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);

        FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

        TrollGUIBuilder trollGUI = plugin.getTrollCommand().trollGUI.getGUIBuilder();

        assert trollGUI != null;

        Player target = trollGUI.getTarget();

        if (Objects.equals(event.getClickedInventory(), trollGUI.getGUI())) {
            event.setCancelled(true);

            switch (event.getSlot()) {
                default:
                    return;
                // Features
                case 47:
                    if (target == player) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll-vanish-not-available"));
                        return;
                    }

                    if (!target.hasMetadata("TROLLPLUS_VANISH")) {
                        target.setMetadata("TROLLPLUS_VANISH", new FixedMetadataValue(plugin, target.getName()));
                        target.hidePlayer(plugin, player);
                        if (plugin.getConfig().getBoolean("vanish-join-quit-message-enabled", true)) {
                            String vanishQuitMessage = plugin.getConfig().getString("vanish-quit-message");
                            assert vanishQuitMessage != null;
                            target.sendMessage(vanishQuitMessage.replace("[player]", player.getName()));
                        }
                        trollGUI.addItem(47, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + langConfig.getString("troll-vanish") + " " + trollGUI.getStatus("TROLLPLUS_VANISH"), langConfig.getString("troll-vanish-description")));
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_VANISH", plugin);
                    target.showPlayer(plugin, player);
                    if (plugin.getConfig().getBoolean("vanish-join-quit-message-enabled", true)) {
                        String vanishJoinMessage = plugin.getConfig().getString("vanish-join-message");
                        assert vanishJoinMessage != null;
                        target.sendMessage(vanishJoinMessage.replace("[player]", player.getName()));
                    }
                    trollGUI.addItem(47, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + langConfig.getString("troll-vanish") + " " + trollGUI.getStatus("TROLLPLUS_VANISH"), langConfig.getString("troll-vanish-description")));

                    break;
                case 48:
                    if (target == player) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll-teleport-not-available"));
                        return;
                    }

                    player.teleport(target);

                    break;
                case 49:
                    target.setHealth(0.0);

                    break;
                case 50:
                    player.openInventory(Objects.requireNonNull(target.getPlayer()).getInventory());

                    break;
                case 51:
                    player.openInventory(Objects.requireNonNull(target.getPlayer()).getEnderChest());

                    break;
                case 53:
                    player.closeInventory();

                    break;

                case 10:
                    if (!target.hasMetadata("TROLLPLUS_FREEZE")) {
                        target.setMetadata("TROLLPLUS_FREEZE", new FixedMetadataValue(plugin, target.getName()));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 6));
                        trollGUI.addItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + langConfig.getString("troll-freeze") + " " + trollGUI.getStatus("TROLLPLUS_FREEZE"), langConfig.getString("troll-freeze-description")));
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_FREEZE", plugin);
                    target.removePotionEffect(PotionEffectType.SLOW);
                    trollGUI.addItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + langConfig.getString("troll-freeze") + " " + trollGUI.getStatus("TROLLPLUS_FREEZE"), langConfig.getString("troll-freeze-description")));

                    break;
                case 12:
                    if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
                        target.setMetadata("TROLLPLUS_HAND_ITEM_DROP", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-hand-item-drop") + " " + trollGUI.getStatus("TROLLPLUS_HAND_ITEM_DROP"), langConfig.getString("troll-hand-item-drop-description")));
                        handItemDrop(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_HAND_ITEM_DROP", plugin);
                    trollGUI.addItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-hand-item-drop") + " " + trollGUI.getStatus("TROLLPLUS_HAND_ITEM_DROP"), langConfig.getString("troll-hand-item-drop-description")));

                    break;
                case 14:
                    if (target == player) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll-control-not-available"));
                        return;
                    }

                    if (!target.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
                        target.setMetadata("TROLLPLUS_CONTROL_TARGET", new FixedMetadataValue(plugin, target.getName()));
                        player.setMetadata("TROLLPLUS_CONTROL_PLAYER", new FixedMetadataValue(plugin, player.getName()));
                        trollGUI.addItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-control") + " " + trollGUI.getStatus("TROLLPLUS_CONTROL_TARGET"), langConfig.getString("troll-control-description")));
                        control(target, player);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);
                    player.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
                    trollGUI.addItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-control") + " " + trollGUI.getStatus("TROLLPLUS_CONTROL_TARGET"), langConfig.getString("troll-control-description")));

                    break;
                case 16:
                    if (!target.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
                        target.setMetadata("TROLLPLUS_FLIP_BEHIND", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-flip-backwards") + " " + trollGUI.getStatus("TROLLPLUS_FLIP_BEHIND"), langConfig.getString("troll-flip-backwards-description")));
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_FLIP_BEHIND", plugin);
                    trollGUI.addItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-flip-backwards") + " " + trollGUI.getStatus("TROLLPLUS_FLIP_BEHIND"), langConfig.getString("troll-flip-backwards-description")));

                    break;
                case 20:
                    if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                        target.setMetadata("TROLLPLUS_SPAM_MESSAGES", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-spam-messages") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_MESSAGES"), langConfig.getString("troll-spam-messages-description")));
                        spamMessages(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SPAM_MESSAGES", plugin);
                    trollGUI.addItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-spam-messages") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_MESSAGES"), langConfig.getString("troll-spam-messages-description")));

                    break;
                case 22:
                    if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
                        target.setMetadata("TROLLPLUS_SPAM_SOUNDS", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-spam-sounds") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_SOUNDS"), langConfig.getString("troll-spam-sounds-description")));
                        spamSounds(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SPAM_SOUNDS", plugin);
                    trollGUI.addItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-spam-sounds") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_SOUNDS"), langConfig.getString("troll-spam-sounds-description")));

                    break;
                case 24:
                    if (!target.hasMetadata("TROLLPLUS_SEMI_BAN")) {
                        target.setMetadata("TROLLPLUS_SEMI_BAN", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-semi-ban") + " " + trollGUI.getStatus("TROLLPLUS_SEMI_BAN"), langConfig.getString("troll-semi-ban-description")));
                        spamSounds(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SEMI_BAN", plugin);
                    trollGUI.addItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-semi-ban") + " " + trollGUI.getStatus("TROLLPLUS_SEMI_BAN"), langConfig.getString("troll-semi-ban-description")));

                    break;
                case 28:
                    if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
                        target.setMetadata("TROLLPLUS_TNT_TRACK", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + langConfig.getString("troll-tnt-track") + " " + trollGUI.getStatus("TROLLPLUS_TNT_TRACK"), langConfig.getString("troll-tnt-track-description")));
                        tntTrack(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_TNT_TRACK", plugin);
                    trollGUI.addItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + langConfig.getString("troll-tnt-track") + " " + trollGUI.getStatus("TROLLPLUS_TNT_TRACK"), langConfig.getString("troll-tnt-track-description")));

                    break;
                case 30:
                    if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
                        target.setMetadata("TROLLPLUS_MOB_SPAWNER", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-mob-spawner") + " " + trollGUI.getStatus("TROLLPLUS_MOB_SPAWNER"), langConfig.getString("troll-mob-spawner-description")));
                        mobSpawner(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_MOB_SPAWNER", plugin);
                    trollGUI.addItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-mob-spawner") + " " + trollGUI.getStatus("TROLLPLUS_MOB_SPAWNER"), langConfig.getString("troll-mob-spawner-description")));

                    break;
                case 32:
                    if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
                        target.setMetadata("TROLLPLUS_SLOWLY_KILL", new FixedMetadataValue(plugin, target.getName()));
                        trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-slowly-kill-description")));
                        slowlyKill(target, player);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-slowly-kill-description")));

                    break;
                case 34:
                    randomScarySound(target);

                    break;
                case 36:
                    inventoryDrop(target);


                    break;
                case 38:
                    if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                        player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll-rocket-cannot-launch"));
                        target.removeMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", plugin);
                        break;
                    }
                    rocket(target, player);

                    break;
                case 40:
                    fakeBan(target);

                    break;
                case 42:
                    fakeOp(target);

                    break;
            }

        } else if (Objects.equals(event.getClickedInventory(), Constants.TROLLBOWS_GUI)) {
            event.setCancelled(true);

            ItemStack arrow = new ItemStack(Material.ARROW, 1);

            switch (event.getSlot()) {
                default:
                    return;
                // Features
                case 8:
                    player.closeInventory();

                    break;
                case 0:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-explosion-bow"), langConfig.getString("trollbows-explosion-bow-description")));
                        player.getInventory().addItem(arrow);
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-explosion-bow"), langConfig.getString("trollbows-explosion-bow-description")));

                    break;
                case 1:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-tnt-bow"), langConfig.getString("trollbows-tnt-bow-description")));
                        player.getInventory().addItem(arrow);
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-tnt-bow"), langConfig.getString("trollbows-tnt-bow-description")));

                    break;
                case 2:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-lighting-bolt-bow"), langConfig.getString("trollbows-lighting-bolt-bow-description")));
                        player.getInventory().addItem(arrow);
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-lighting-bolt-bow"), langConfig.getString("trollbows-lighting-bolt-bow-description")));

                    break;
                case 3:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW)) {
                        player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-silverfish-bow"), langConfig.getString("trollbows-silverfish-bow-description")));
                        player.getInventory().addItem(arrow);
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(langConfig.getString("trollbows-silverfish-bow"), langConfig.getString("trollbows-silverfish-bow-description")));

                    break;
            }
        }
    }

    // Feature hand item drop
    private void handItemDrop(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
                    cancel();
                    return;
                }

                if (target.getInventory().getItemInMainHand().getType() == Material.AIR) return;

                ItemStack item = target.getInventory().getItemInMainHand();
                ItemStack dropItem = new ItemStack(item.getType(), 1);
                dropItem.setItemMeta(item.getItemMeta());
                Item itemDrop = target.getWorld().dropItemNaturally(target.getLocation(), dropItem);
                itemDrop.setPickupDelay(40);
                int amount = item.getAmount();
                amount--;
                item.setAmount(amount);
                target.getInventory().setItemInMainHand(item);
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // Feature control
    private void control(Player target, Player player) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(plugin, player);
        }

        player.hidePlayer(plugin, target);

        Location player_location = player.getLocation();
        ItemStack[] player_inventory = player.getInventory().getContents();
        ItemStack[] player_armor = player.getInventory().getArmorContents();
        ItemStack player_off_hand_item = player.getInventory().getItemInOffHand();

        player.teleport(target);
        player.getInventory().setContents(target.getInventory().getContents());
        player.getInventory().setArmorContents(target.getInventory().getArmorContents());
        player.getInventory().setItemInOffHand(target.getInventory().getItemInOffHand());

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
                    target.getInventory().setContents(player.getInventory().getContents());
                    target.getInventory().setArmorContents(player.getInventory().getArmorContents());
                    target.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());

                    player.getInventory().setContents(player_inventory);
                    player.getInventory().setArmorContents(player_armor);
                    player.getInventory().setItemInOffHand(player_off_hand_item);
                    if (plugin.getConfig().getBoolean("control-teleport-back", true)) player.teleport(player_location);

                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        online.showPlayer(plugin, player);
                    }

                    player.showPlayer(plugin, target);

                    cancel();
                    return;
                }

                if (Constants.CONTROL_MESSAGE_BOOLEAN) {
                    target.chat(Constants.CONTROL_MESSAGE);
                    Constants.CONTROL_MESSAGE_BOOLEAN = false;
                }

                if (target.getLocation() != player.getLocation()) target.teleport(player);
                if (!player.getInventory().equals(target.getInventory()) || !Arrays.equals(player.getInventory().getArmorContents(), target.getInventory().getArmorContents()) || !player.getInventory().getItemInOffHand().equals(target.getInventory().getItemInOffHand())) {
                    target.getInventory().setContents(player.getInventory().getContents());
                    target.getInventory().setArmorContents(player.getInventory().getArmorContents());
                    target.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    // Feature spam messages
    private void spamMessages(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                    cancel();
                    return;
                }

                List<String> spamMessages = plugin.getConfig().getStringList("spam-messages");

                StringBuilder stringBuilderChat = new StringBuilder();
                StringBuilder stringBuilderTitle = new StringBuilder();
                StringBuilder stringBuilderTitle2 = new StringBuilder();

                for (Character character : spamMessages.get(RandomUtils.nextInt(0, spamMessages.size())).toCharArray()) {
                    stringBuilderChat.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.nextInt(0, 16))));
                    stringBuilderChat.append(character);
                }

                for (Character character : spamMessages.get(RandomUtils.nextInt(0, spamMessages.size())).toCharArray()) {
                    stringBuilderTitle.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.nextInt(0, 16))));
                    stringBuilderTitle.append(character);
                }

                for (Character character : spamMessages.get(RandomUtils.nextInt(0, spamMessages.size())).toCharArray()) {
                    stringBuilderTitle2.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.nextInt(0, 16))));
                    stringBuilderTitle2.append(character);
                }

                target.sendMessage(stringBuilderChat.toString());
                target.sendTitle(stringBuilderTitle.toString(), stringBuilderTitle2.toString(), 3, 10, 3);
            }
        }.runTaskTimer(plugin, 0, 10);
    }

    // Feature spam sounds
    private void spamSounds(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
                    cancel();
                    return;
                }

                List<Sound> sounds = Arrays.asList(Sound.ENTITY_FOX_BITE, Sound.ENTITY_VILLAGER_YES, Sound.ENTITY_PLAYER_HURT, Sound.ENTITY_CHICKEN_DEATH, Sound.ENTITY_WOLF_GROWL, Sound.BLOCK_BELL_USE, Sound.BLOCK_ANVIL_FALL, Sound.ENTITY_WITHER_DEATH, Sound.ENTITY_WOLF_DEATH, Sound.BLOCK_IRON_DOOR_CLOSE, Sound.BLOCK_CHEST_OPEN, Sound.ENTITY_PIG_HURT, Sound.BLOCK_GRAVEL_BREAK, Sound.ENTITY_SHULKER_BULLET_HIT, Sound.ENTITY_ILLUSIONER_DEATH, Sound.BLOCK_PORTAL_AMBIENT, Sound.BLOCK_CANDLE_BREAK, Sound.ENTITY_BAT_HURT, Sound.ITEM_AXE_WAX_OFF);

                target.playSound(target.getLocation(), sounds.get(RandomUtils.nextInt(0, sounds.size())), RandomUtils.nextInt(), RandomUtils.nextInt());
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    // Feature tnt track
    private void tntTrack(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
                    cancel();
                    return;
                }

                TNTPrimed tnt = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(80);
                tnt.setMetadata("TROLLPLUS_TNT", new FixedMetadataValue(plugin, tnt));
                tnt.getWorld().playSound(target.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // Feature mob spawner
    private void mobSpawner(Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
                    cancel();
                    return;
                }

                List<EntityType> mobs = Arrays.asList(EntityType.DROWNED, EntityType.HUSK, EntityType.PILLAGER, EntityType.SKELETON, EntityType.SPIDER, EntityType.STRAY, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.PIGLIN_BRUTE, EntityType.SILVERFISH, EntityType.VINDICATOR);

                target.getWorld().spawnEntity(target.getLocation(), mobs.get(RandomUtils.nextInt(0, mobs.size())));
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    // Feature slowly kill
    private void slowlyKill(Player target, Player player) {
        new BukkitRunnable() {
            @Override

            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
                    cancel();
                    return;
                }

                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) {
                    FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

                    player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll-slowly-kill-not-available"));
                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    TrollGUIBuilder trollGUI = plugin.getTrollCommand().trollGUI.getGUIBuilder();
                    trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-slowly-kill-description")));
                    cancel();
                    return;
                }

                target.damage(1);
            }
        }.runTaskTimer(plugin, 0, 60);
    }

    // Feature random scary sound
    private void randomScarySound(Player target) {
        List<Sound> sounds = Arrays.asList(Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_CAVE, Sound.AMBIENT_CRIMSON_FOREST_MOOD, Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_WARPED_FOREST_MOOD, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);

        target.playSound(target.getLocation(), sounds.get(RandomUtils.nextInt(0, sounds.size())), 200, 1);
    }

    // Inventory Drop
    private void inventoryDrop(Player target) {
        for (ItemStack inventoryItems : target.getInventory().getContents()) {
            if (inventoryItems != null) {
                Item inventoryItemsDrop = target.getWorld().dropItemNaturally(target.getLocation(), inventoryItems);
                inventoryItemsDrop.setPickupDelay(40);
            }
        }

        target.getInventory().clear();
        target.updateInventory();
    }

    // Feature rocket
    private void rocket(Player target, Player player) {
        target.setMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", new FixedMetadataValue(plugin, target.getName()));

        boolean targetAllowedToFlight = false;
        if (!target.getAllowFlight()) {
            target.setAllowFlight(true);
        } else {
            target.setAllowFlight(false);
            target.setAllowFlight(true);
            targetAllowedToFlight = true;
        }

        target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.getLocation(), 1);
        Particle[] particles = new Particle[]{Particle.FIREWORKS_SPARK, Particle.LAVA, Particle.FLAME};
        for (Particle particle : particles) {
            target.getWorld().spawnParticle(particle, target.getLocation(), 25);
        }

        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);

        Boolean finalTargetAllowToFlight = targetAllowedToFlight;
        new BukkitRunnable() {
            byte rocket = 0;

            @Override
            public void run() {
                if (rocket < 22) {
                    if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                        if (!finalTargetAllowToFlight) target.setAllowFlight(false);

                        player.sendMessage(Constants.PLUGIN_PREFIX + plugin.getLanguageConfig().getConfig().getString("troll-rocket-launch-stopped"));

                        cancel();
                        rocket = 0;
                        return;
                    }

                    target.setVelocity(target.getVelocity().setY(20));
                    rocket++;
                } else {
                    if (!finalTargetAllowToFlight) target.setAllowFlight(false);

                    rocket = 0;
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    // Feature fake ban
    private void fakeBan(Player target) {
        String fakeBanMessagePlayer = plugin.getConfig().getString("fake-ban-message-player", "");

        if (!fakeBanMessagePlayer.isEmpty()) target.kickPlayer(fakeBanMessagePlayer);

        if (plugin.getConfig().getBoolean("fake-ban-message-broadcast-enabled", true)) {
            String fakeBanMessageBroadcast = plugin.getConfig().getString("fake-ban-message-broadcast", "");

            if (!fakeBanMessageBroadcast.isEmpty()) {
                String fakeBanMessageBroadcastReplace = fakeBanMessageBroadcast.replace("[player]", target.getName());
                Bukkit.broadcastMessage(fakeBanMessageBroadcastReplace);
            }
        }
    }

    // Feature fake op
    private void fakeOp(Player target) {
        String fakeOpMessage = plugin.getConfig().getString("fake-op-message", "");

        if (!plugin.getConfig().getBoolean("fake-op-message-broadcast-enabled", true)) {

            if (!fakeOpMessage.isEmpty()) {
                String fakeOpMessageReplace = fakeOpMessage.replace("[player]", target.getName());
                target.sendMessage(ChatColor.GRAY + fakeOpMessageReplace);
            }
            return;
        }

        if (!fakeOpMessage.isEmpty()) {
            String fakeOpMessageReplace = fakeOpMessage.replace("[player]", target.getName());
            Bukkit.broadcastMessage(ChatColor.GRAY + fakeOpMessageReplace);
        }
    }
}