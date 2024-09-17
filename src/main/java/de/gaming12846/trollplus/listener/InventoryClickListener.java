/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.ControlUtil;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static de.gaming12846.trollplus.TrollPlus.PLUGIN_PREFIX;

// Listener for handling inventory click events
public class InventoryClickListener implements Listener {
    private final TrollPlus plugin;
    public ControlUtil controlUtil;
    private GUIHelper GUIHelperLanguage;

    // Constructor for the InventoryClickListener
    public InventoryClickListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the InventoryClickEvent
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ConfigHelper configHelperLanguage = plugin.getConfigHelperLanguage();
        Player player = (Player) event.getWhoClicked();

        //  Cancel damage if the player has the "TROLLPLUS_FREEZE" metadata
        if (player.hasMetadata("TROLLPLUS_FREEZE")) event.setCancelled(true);

        //  Cancel damage if the player has the "TROLLPLUS_CONTROL_TARGET" metadata
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) event.setCancelled(true);

        //  Cancel damage if the player has the "TROLLPLUS_SEMI_BAN" metadata
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) event.setCancelled(true);

        // Retrieve the clicked inventory
        Inventory clickedInventory = event.getClickedInventory();

        // Check which GUI the clicked inventory matches
        if (isMatchingInventory(clickedInventory, plugin.getTrollCommand().GUIHelperTroll)) {
            handleTrollGUI(event, player, configHelperLanguage);
        } else if (isMatchingInventory(clickedInventory, plugin.getTrollBowsCommand().GUIHelperTrollBows)) {
            handleTrollBowsGUI(event, player, configHelperLanguage);
        } else if (isMatchingInventory(clickedInventory, plugin.getTrollPlusCommand().GUIHelperSettings)) {
            handleSettingsGUI(event, player, configHelperLanguage);
        } else if (isMatchingInventory(clickedInventory, GUIHelperLanguage))
            handleLanguageChangeGUI(event, player, configHelperLanguage);
    }

    // Helper method to check if the clicked inventory matches any of the GUIs
    private boolean isMatchingInventory(Inventory inventory, GUIHelper gui) {
        return gui != null && Objects.equals(inventory, gui.getGUI());
    }

    // Handles inventory click events in the Troll GUI
    private void handleTrollGUI(InventoryClickEvent event, Player player, ConfigHelper configHelperLanguage) {
        int slot = event.getSlot();
        GUIHelper trollGUI = plugin.getTrollCommand().GUIHelperTroll.getGUIUtil();
        Player target = trollGUI.getTarget();

        // Cancel the event to prevent any unwanted interactions
        event.setCancelled(true);

        // If the target player is no longer online, close the inventory and notify the player
        if (!target.isOnline()) {
            player.closeInventory();
            player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.player-quit"));
            return;
        }

        // Random slot selection
        if (slot == 50) {
            int[] slots = {10, 12, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44};
            slot = slots[RandomUtils.nextInt(0, slots.length)];
        }

        // Determine the action based on the clicked slot
        switch (slot) {
            case 11 -> handleFreezeFeature(target, trollGUI, configHelperLanguage);

            case 12 -> handleHandItemDropFeature(target, trollGUI, configHelperLanguage);

            case 13 -> handleControlFeature(player, target, trollGUI, configHelperLanguage);

            case 14 -> handleFlipBehindFeature(target, trollGUI, configHelperLanguage);

            case 15 -> handleSpankFeature(target, trollGUI, configHelperLanguage);

            case 19 -> handleSpamMessagesFeature(target, trollGUI, configHelperLanguage);

            case 20 -> handleSpamSoundsFeature(target, trollGUI, configHelperLanguage);

            case 21 -> handleSemiBanFeature(target, trollGUI, configHelperLanguage);

            case 22 -> handleFallingAnvilsFeature(target, player, trollGUI, configHelperLanguage);

            case 23 -> handleTntTrackFeature(target, trollGUI, configHelperLanguage);

            case 24 -> handleMobSpawnerFeature(target, trollGUI, configHelperLanguage);

            case 25 -> handleSlowlyKillFeature(target, player, trollGUI, configHelperLanguage);

            case 28 -> {
                //TODO
            }

            case 29 -> {
                //TODO
            }

            case 30 -> {
                //TODO
            }

            case 31 -> {
                //TODO
            }

            case 32 -> {
                //TODO
            }

            case 33 ->
                handleInventoryDropFeature(target);

            case 34 ->
                handleInventoryShuffleFeature(target);

            case 38 ->
                handleRandomScarySoundFeature(target);

            case 39 -> handleRocketFeature(player, target, configHelperLanguage);

            case 40 -> handleFreefallFeature(target, player, configHelperLanguage);

            case 41 -> handleFakeBanFeature(target, configHelperLanguage);

            case 42 -> handleFakeOpFeature(target, configHelperLanguage);

            case 18 -> handleTeleportFeature(player, target, configHelperLanguage);

            case 26 ->
                player.openInventory(Objects.requireNonNull(target.getPlayer()).getInventory());

            case 27 -> {
                target.setMetadata("TROLLPLUS_KILL", new FixedMetadataValue(plugin, target.getName()));
                target.setHealth(0.0);
            }

            case 35 ->
                player.openInventory(Objects.requireNonNull(target.getPlayer()).getEnderChest());

            case 48 -> handleVanishFeature(player, target, trollGUI, configHelperLanguage);
        }
    }

    // Handles the freeze feature, freezing or unfreezing the target player
    private void handleFreezeFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_FREEZE")) {
            target.setMetadata("TROLLPLUS_FREEZE", new FixedMetadataValue(plugin, target.getName()));
            if (plugin.getServerVersion() > 1.19)
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 6));
        } else {
            target.removeMetadata("TROLLPLUS_FREEZE", plugin);
            if (plugin.getServerVersion() > 1.19) target.removePotionEffect(PotionEffectType.SLOWNESS);
        }

        trollGUI.addItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.freeze") + " " + trollGUI.getStatus("TROLLPLUS_FREEZE"), configHelperLanguage.getString("troll-gui.freeze-description")));
    }

    // Handles the hand item drop feature, continuously drops the item held in the target player's main hand
    private void handleHandItemDropFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
            target.setMetadata("TROLLPLUS_HAND_ITEM_DROP", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_HAND_ITEM_DROP", plugin);

        trollGUI.addItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.hand-item-drop") + " " + trollGUI.getStatus("TROLLPLUS_HAND_ITEM_DROP"), configHelperLanguage.getString("troll-gui.hand-item-drop-description")));
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel the task if the player no longer has the "TROLLPLUS_HAND_ITEM_DROP" metadata
                if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
                    cancel();
                    return;
                }

                // Get the item in the player's main hand. If it's air (empty), do nothing
                ItemStack itemInHand = target.getInventory().getItemInMainHand();
                if (itemInHand.getType() == Material.AIR) return;

                // Drop a single item from the stack in the player's main hand
                ItemStack dropItem = new ItemStack(itemInHand.getType(), 1);
                dropItem.setItemMeta(itemInHand.getItemMeta());
                Item itemDrop = target.getWorld().dropItemNaturally(target.getLocation(), dropItem);
                itemDrop.setPickupDelay(40);

                // Decrease the amount of the item in the player's hand by one
                int newAmount = itemInHand.getAmount() - 1;
                itemInHand.setAmount(newAmount);
                target.getInventory().setItemInMainHand(itemInHand);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("hand-item-drop-period"));
    }

    // Handles the control feature, control the player
    private void handleControlFeature(Player player, Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        // Check if the player is trying to control themselves
        if (target.equals(player)) {
            player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.control-not-available"));
            return;
        }

        if (!target.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            target.setMetadata("TROLLPLUS_CONTROL_TARGET", new FixedMetadataValue(plugin, target.getName()));
            player.setMetadata("TROLLPLUS_CONTROL_PLAYER", new FixedMetadataValue(plugin, player.getName()));

            // Initialize the control utility and start controlling
            controlUtil = new ControlUtil(plugin, target, player);
            controlUtil.control();
        } else {
            target.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);
            player.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
        }

        trollGUI.addItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.control") + " " + trollGUI.getStatus("TROLLPLUS_CONTROL_TARGET"), configHelperLanguage.getString("troll-gui.control-description")));
    }

    // Handles the flip behind feature, flip the player 180° on interaction
    private void handleFlipBehindFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
            target.setMetadata("TROLLPLUS_FLIP_BEHIND", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_FLIP_BEHIND", plugin);

        trollGUI.addItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.flip-backwards") + " " + trollGUI.getStatus("TROLLPLUS_FLIP_BEHIND"), configHelperLanguage.getString("troll-gui.flip-backwards-description")));
    }

    // Handles the spank feature, continuously applies a random knockback ("spank") effect to the target
    private void handleSpankFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_SPANK")) {
            target.setMetadata("TROLLPLUS_SPANK", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SPANK", plugin);

        trollGUI.addItem(18, ItemBuilder.createItemWithLore(Material.SNOWBALL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.spank") + " " + trollGUI.getStatus("TROLLPLUS_SPANK"), configHelperLanguage.getString("troll-gui.spank-description")));

        new BukkitRunnable() {

            @Override
            public void run() {
                // Check if the target still has the "TROLLPLUS_SPANK" metadata, cancel if not
                if (!target.hasMetadata("TROLLPLUS_SPANK")) {
                    cancel();
                    return;
                }

                // Generate random knockback vector components within specified ranges
                double x = RandomUtils.nextDouble(0.1, 1) - RandomUtils.nextDouble(0.1, 1);
                double y = RandomUtils.nextDouble(0.33, 1);
                double z = RandomUtils.nextDouble(0.1, 1) - RandomUtils.nextDouble(0.1, 1);

                // Apply the knockback to the target
                target.setVelocity(new Vector(x, y, z));
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("spank-period"));
    }

    // Handles the spam messages feature, continuously spams the target player with randomly colored messages and titles
    private void handleSpamMessagesFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
            target.setMetadata("TROLLPLUS_SPAM_MESSAGES", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SPAM_MESSAGES", plugin);

        trollGUI.addItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.spam-messages") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_MESSAGES"), configHelperLanguage.getString("troll-gui.spam-messages-description")));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                    cancel();
                    return;
                }

                List<String> spamMessages = configHelperLanguage.getStringList("spam-messages");
                String chatMessage = getRandomColoredMessage(spamMessages);
                String titleMessage = getRandomColoredMessage(spamMessages);
                String subtitleMessage = getRandomColoredMessage(spamMessages);

                target.sendMessage(chatMessage);
                target.sendTitle(titleMessage, subtitleMessage, 3, 10, 3);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("spam-messages-period"));
    }

    // Generates a randomly colored message from a list of message
    private String getRandomColoredMessage(List<String> messages) {
        StringBuilder messageBuilder = new StringBuilder();
        String randomMessage = messages.get(RandomUtils.nextInt(0, messages.size()));

        for (Character character : randomMessage.toCharArray()) {
            ChatColor randomColor = ChatColor.getByChar(Integer.toHexString(RandomUtils.nextInt(0, 16)));
            messageBuilder.append(randomColor).append(character);
        }

        return messageBuilder.toString();
    }

    // Handles the spam sounds feature, continuously plays random sounds to the target player
    private void handleSpamSoundsFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
            target.setMetadata("TROLLPLUS_SPAM_SOUNDS", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SPAM_SOUNDS", plugin);

        trollGUI.addItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.spam-sounds") + " " + trollGUI.getStatus("TROLLPLUS_SPAM_SOUNDS"), configHelperLanguage.getString("troll-gui.spam-sounds-description")));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
                    cancel();
                    return;
                }

                List<Sound> sounds;
                if (plugin.getServerVersion() < 1.14) {
                    sounds = Arrays.asList(Sound.ENTITY_VILLAGER_YES, Sound.ENTITY_PLAYER_HURT, Sound.ENTITY_CHICKEN_DEATH, Sound.ENTITY_WOLF_GROWL, Sound.BLOCK_ANVIL_FALL, Sound.ENTITY_WITHER_DEATH, Sound.ENTITY_WOLF_DEATH, Sound.BLOCK_IRON_DOOR_CLOSE, Sound.BLOCK_CHEST_OPEN, Sound.ENTITY_PIG_HURT, Sound.BLOCK_GRAVEL_BREAK, Sound.ENTITY_SHULKER_BULLET_HIT, Sound.ENTITY_ILLUSIONER_DEATH, Sound.BLOCK_PORTAL_AMBIENT, Sound.ENTITY_BAT_HURT);
                } else
                    sounds = Arrays.asList(Sound.ENTITY_FOX_BITE, Sound.ENTITY_VILLAGER_YES, Sound.ENTITY_PLAYER_HURT, Sound.ENTITY_CHICKEN_DEATH, Sound.ENTITY_WOLF_GROWL, Sound.BLOCK_BELL_USE, Sound.BLOCK_ANVIL_FALL, Sound.ENTITY_WITHER_DEATH, Sound.ENTITY_WOLF_DEATH, Sound.BLOCK_IRON_DOOR_CLOSE, Sound.BLOCK_CHEST_OPEN, Sound.ENTITY_PIG_HURT, Sound.BLOCK_GRAVEL_BREAK, Sound.ENTITY_SHULKER_BULLET_HIT, Sound.ENTITY_ILLUSIONER_DEATH, Sound.BLOCK_PORTAL_AMBIENT, Sound.ENTITY_BAT_HURT);
                Sound randomSound = sounds.get(RandomUtils.nextInt(0, sounds.size()));

                target.playSound(target.getLocation(), randomSound, RandomUtils.nextInt(1, 100), RandomUtils.nextInt(1, 100));
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("spam-sounds-period"));
    }

    // Handles the semi ban feature, prevents the target from interacting and chatting
    private void handleSemiBanFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            target.setMetadata("TROLLPLUS_SEMI_BAN", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SEMI_BAN", plugin);

        trollGUI.addItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.semi-ban") + " " + trollGUI.getStatus("TROLLPLUS_SEMI_BAN"), configHelperLanguage.getString("troll-gui.semi-ban-description")));
    }

    // Handles the falling anvils feature, continuously spawns falling anvils above the target player
    private void handleFallingAnvilsFeature(Player target, Player player, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_FALLING_ANVILS")) {
            Location loc = target.getLocation();  // Clone the location to avoid modifying the original

            for (int i = 0; i < 4; i++) {
                if (loc.getBlock().getType() == Material.AIR) {
                    loc.add(0, 1, 0);
                } else {
                    player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.falling-anvils-not-available"));
                    return;
                }
            }

            target.setMetadata("TROLLPLUS_FALLING_ANVILS", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_FALLING_ANVILS", plugin);

        trollGUI.addItem(26, ItemBuilder.createItemWithLore(Material.ANVIL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.falling-anvils") + " " + trollGUI.getStatus("TROLLPLUS_FALLING_ANVILS"), configHelperLanguage.getString("troll-gui.falling-anvils-description")));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_FALLING_ANVILS")) {
                    cancel();
                    return;
                }

                Location loc = target.getLocation().add(0, 3, 0);

                if (loc.getBlock().getType() == Material.AIR) loc.getBlock().setType(Material.DAMAGED_ANVIL);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("falling-anvils-period"));
    }

    // Handles the tnt track feature, continuously spawns primed TNT at the target player's location
    private void handleTntTrackFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
            target.setMetadata("TROLLPLUS_TNT_TRACK", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_TNT_TRACK", plugin);

        trollGUI.addItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.tnt-track") + " " + trollGUI.getStatus("TROLLPLUS_TNT_TRACK"), configHelperLanguage.getString("troll-gui.tnt-track-description")));

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
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("tnt-track-period"));
    }

    // Handles the mob spawner feature, continuously spawns random mobs near the target player
    private void handleMobSpawnerFeature(Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
            target.setMetadata("TROLLPLUS_MOB_SPAWNER", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_MOB_SPAWNER", plugin);

        trollGUI.addItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.mob-spawner") + " " + trollGUI.getStatus("TROLLPLUS_MOB_SPAWNER"), configHelperLanguage.getString("troll-gui.mob-spawner-description")));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
                    cancel();
                    return;
                }

                List<EntityType> mobs;
                if (plugin.getServerVersion() < 1.14) {
                    mobs = Arrays.asList(EntityType.DROWNED, EntityType.ENDERMITE, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.STRAY, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ILLUSIONER);
                } else if (plugin.getServerVersion() < 1.16) {
                    mobs = Arrays.asList(EntityType.DROWNED, EntityType.ENDERMITE, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PILLAGER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.STRAY, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ILLUSIONER);
                } else
                    mobs = Arrays.asList(EntityType.DROWNED, EntityType.ENDERMITE, EntityType.HOGLIN, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.STRAY, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ILLUSIONER);
                EntityType randomMob = mobs.get(RandomUtils.nextInt(0, mobs.size()));
                target.getWorld().spawnEntity(target.getLocation(), randomMob).setGlowing(true);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("mob-spawner-period"));
    }

    // Handles the slowly kill feature, slowly damages the target player over time
    private void handleSlowlyKillFeature(Player target, Player player, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
            target.setMetadata("TROLLPLUS_SLOWLY_KILL", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);

        trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), configHelperLanguage.getString("troll-gui.slowly-kill-description")));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
                    cancel();
                    return;
                }

                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) {
                    player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.slowly-kill-not-available"));
                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    GUIHelper trollGUI = plugin.getTrollCommand().GUIHelperTroll.getGUIUtil();
                    trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatus("TROLLPLUS_SLOWLY_KILL"), configHelperLanguage.getString("troll-gui.slowly-kill-description")));
                    cancel();
                    return;
                }

                target.damage(1);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt("slowly-kill-period"));
    }

    // Handles the inventory drop feature, drops all items from the target player's inventory at their current location
    private void handleInventoryDropFeature(Player target) {
        for (ItemStack item : target.getInventory().getContents()) {
            if (item != null) {
                Item droppedItem = target.getWorld().dropItemNaturally(target.getLocation(), item);
                droppedItem.setPickupDelay(40);
            }
        }

        target.getInventory().clear();
    }

    // Handles the inventory shuffle feature, shuffles the order of items in a player's inventory
    private void handleInventoryShuffleFeature(Player target) {
        Inventory inventory = target.getInventory();
        ItemStack[] items = inventory.getContents();

        // Reverse the order of items in the inventory
        for (int i = 0; i < items.length / 2; i++) {
            ItemStack temp = items[i];
            items[i] = items[items.length - 1 - i];
            items[items.length - 1 - i] = temp;
        }

        // Update the player's inventory with the reversed items
        inventory.setContents(items);
    }

    // Handles the random scary sound feature, plays a random scary sound near the target player
    private void handleRandomScarySoundFeature(Player target) {
        List<Sound> sounds;
        if (plugin.getServerVersion() < 1.16) {
            sounds = Arrays.asList(Sound.AMBIENT_CAVE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE);
        } else
            sounds = Arrays.asList(Sound.AMBIENT_CAVE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, Sound.AMBIENT_CRIMSON_FOREST_MOOD, Sound.AMBIENT_CRIMSON_FOREST_ADDITIONS, Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_NETHER_WASTES_ADDITIONS, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, Sound.AMBIENT_WARPED_FOREST_MOOD, Sound.AMBIENT_WARPED_FOREST_ADDITIONS);
        Sound randomSound = sounds.get(RandomUtils.nextInt(0, sounds.size()));
        target.playSound(target.getLocation(), randomSound, plugin.getConfigHelper().getInt("random-scary-sound-volume"), 1);
    }

    // Handles the rocket feature, launching the target player into the air if conditions are met
    private void handleRocketFeature(Player player, Player target, ConfigHelper configHelperLanguage) {
        if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
            player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.rocket-cannot-launch"));
            target.removeMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", plugin);
            return;
        }

        // Set metadata to prevent fall damage
        target.setMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", new FixedMetadataValue(plugin, target.getName()));

        // Store the target's current flight status and temporarily allow flight if not allowed
        boolean targetInitiallyAllowedFlight = target.getAllowFlight();
        if (!targetInitiallyAllowedFlight) target.setAllowFlight(true);

        // Spawn initial explosion particle at the player's location
        Particle particleType = plugin.getServerVersion() < 1.20 ? Particle.ASH : Particle.EXPLOSION;
        target.getWorld().spawnParticle(particleType, target.getLocation(), 1);

        // Create an array of particles to simulate the rocket launch
        Particle particlesType = plugin.getServerVersion() < 1.20 ? Particle.FLASH : Particle.FIREWORK;
        Particle[] particles = {particlesType, Particle.LAVA, Particle.FLAME};
        for (Particle particle : particles) {
            target.getWorld().spawnParticle(particle, target.getLocation(), 25);
        }

        // Play explosion sound at the player's location
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

        // Schedule a repeating task to simulate the rocket's upward movement
        new BukkitRunnable() {
            private final int maxRocketCharges = plugin.getConfigHelper().getInt("rocket.charges");
            private int rocketCharges = 0;

            @Override
            public void run() {
                // Stop the rocket if the maximum charges are exceeded or if the player is below the highest block
                if (rocketCharges >= maxRocketCharges || target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                    // Restore flight status if it was not allowed initially
                    if (!targetInitiallyAllowedFlight) target.setAllowFlight(false);

                    // Inform the initiating player that the rocket launch was stopped
                    player.sendMessage(PLUGIN_PREFIX + plugin.getConfigHelperLanguage().getString("troll.rocket-launch-stopped"));
                    cancel();
                    return;
                }

                // Propel the target upward
                target.setVelocity(target.getVelocity().setY(2));

                // Disable flying if the player was flying
                if (target.isFlying()) target.setFlying(false);

                rocketCharges++;
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getLong("rocket.period"));
    }

    // Handles the freefall feature, simulates a freefall by teleporting the target player to a specified height above their current position
    private void handleFreefallFeature(Player target, Player player, ConfigHelper configHelperLanguage) {
        Location loc = target.getLocation().clone();
        loc.setY(loc.getY() + 1);

        // Calculate the highest possible freefall height based on the configuration
        int freefallHeight = plugin.getConfigHelper().getInt("freefall-height");
        boolean canFall = true;

        // Check if there is enough air above the player for the freefall
        for (int i = 0; i < freefallHeight; i++) {
            if (loc.getBlock().getType() != Material.AIR) {
                player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.freefall-cannot-fall"));
                canFall = false;

            }
            loc.setY(loc.getY() + 1);
        }

        // If freefall is possible, disable flying and teleport the target player
        if (canFall) {
            if (target.isFlying()) target.setFlying(false);
            target.teleport(loc);
        }
    }

    // Handles the fake ban feature, simulates banning a player by kicking them from the server with a custom message
    private void handleFakeBanFeature(Player target, ConfigHelper configHelperLanguage) {
        // Retrieve the fake ban message to be sent to the player
        String fakeBanMessagePlayer = configHelperLanguage.getString("fake-ban.message-player");

        // Kick the player with the fake ban message
        target.kickPlayer(fakeBanMessagePlayer);

        // Check if fake ban broadcast is enabled in the configuration
        if (plugin.getConfigHelper().getBoolean("fake-ban-message-broadcast-enabled")) {
            String fakeBanMessageBroadcast = configHelperLanguage.getString("fake-ban.message-broadcast");

            // Replace the placeholder with the target player's name
            String formattedBroadcastMessage = fakeBanMessageBroadcast.replace("[player]", target.getName());

            // Broadcast the fake ban message to all players
            Bukkit.broadcastMessage(formattedBroadcastMessage);
        }
    }

    // Handles the fake op feature, simulates giving a player operator status by sending them a fake message
    private void handleFakeOpFeature(Player target, ConfigHelper configHelperLanguage) {
        // Retrieve the fake op message from the configuration
        String fakeOpMessage = configHelperLanguage.getString("fake-op-message");

        // Replace the placeholder with the target player's name
        String formattedOpMessage = fakeOpMessage.replace("[player]", target.getName());

        // Check if fake op message broadcast is enabled in the configuration
        if (plugin.getConfigHelper().getBoolean("fake-op-message-broadcast-enabled")) {
            // Broadcast the fake op message to all players
            Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + formattedOpMessage);
        } else
            // Send the fake op message only to the target player
            target.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + formattedOpMessage);
    }

    // Handles the teleport Feature, teleporting the player to the target
    private void handleTeleportFeature(Player player, Player target, ConfigHelper configHelperLanguage) {
        if (target == player) {
            player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.teleport-not-available"));
            return;
        }
        player.teleport(target);
    }

    // Handles the vanish feature, allowing the player to vanish or reappear
    private void handleVanishFeature(Player player, Player target, GUIHelper trollGUI, ConfigHelper configHelperLanguage) {
        if (target == player) {
            player.sendMessage(PLUGIN_PREFIX + configHelperLanguage.getString("troll.vanish-not-available"));
            return;
        }

        if (!target.hasMetadata("TROLLPLUS_VANISH")) {
            target.setMetadata("TROLLPLUS_VANISH", new FixedMetadataValue(plugin, target.getName()));
            target.hidePlayer(plugin, player);
            if (plugin.getConfigHelper().getBoolean("vanish.join-message-enabled") && configHelperLanguage.getString("vanish.quit-message") != null)
                player.sendMessage(configHelperLanguage.getString("vanish.quit-message").replace("[player]", player.getName()));
        } else {
            target.removeMetadata("TROLLPLUS_VANISH", plugin);
            target.showPlayer(plugin, player);
            if (plugin.getConfigHelper().getBoolean("vanish.join-message-enabled"))
                if (configHelperLanguage.getString("vanish.join-message") != null) {
                    player.sendMessage(configHelperLanguage.getString("vanish.join-message").replace("[player]", player.getName()));
                }
        }

        trollGUI.addItem(45, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + configHelperLanguage.getString("troll-gui.vanish") + " " + trollGUI.getStatus("TROLLPLUS_VANISH"), configHelperLanguage.getString("troll-gui.vanish-description")));
    }

    // Handles inventory click events in the TrollBows GUI
    private void handleTrollBowsGUI(InventoryClickEvent event, Player player, ConfigHelper configHelperLanguage) {
        // Cancel the event to prevent default inventory behavior
        event.setCancelled(true);

        // Determine the action based on the clicked slot
        int slot = event.getSlot();

        // Determine which bow to give based on the clicked slot
        String bowName = "";
        String bowDescription = "";

        // Determine the action based on the clicked slot
        switch (slot) {
            case 2 -> {
                bowName = configHelperLanguage.getString("trollbows.explosion-bow");
                bowDescription = configHelperLanguage.getString("trollbows.explosion-bow-description");
            }

            case 3 -> {
                bowName = configHelperLanguage.getString("trollbows.tnt-bow");
                bowDescription = configHelperLanguage.getString("trollbows.tnt-bow-description");
            }

            case 4 -> {
                bowName = configHelperLanguage.getString("trollbows.lightning-bolt-bow");
                bowDescription = configHelperLanguage.getString("trollbows.lightning-bolt-bow-description");
            }

            case 5 -> {
                bowName = configHelperLanguage.getString("trollbows.silverfish-bow");
                bowDescription = configHelperLanguage.getString("trollbows.silverfish-bow-description");
            }

            case 6 -> {
                bowName = configHelperLanguage.getString("trollbows.potion-effect-bow");
                bowDescription = configHelperLanguage.getString("trollbows.potion-effect-bow-description");
            }
        }

        // Give the player the selected bow, and an arrow if needed
        givePlayerBow(player, bowName, bowDescription);
    }

    // Gives the player a trollbow
    private void givePlayerBow(Player player, String bowName, String bowDescription) {
        ItemStack bow = ItemBuilder.createBow(plugin, bowName, bowDescription);

        if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(Material.ARROW))
            player.getInventory().addItem(new ItemStack(Material.ARROW));

        player.getInventory().addItem(bow);
    }

    // Handles inventory click events in the Settings GUI
    private void handleSettingsGUI(InventoryClickEvent event, Player player, ConfigHelper configHelperLanguage) {
        GUIHelper settingsGUI = plugin.getTrollPlusCommand().GUIHelperSettings.getGUIUtil();

        // Cancel the event to prevent default behavior
        event.setCancelled(true);

        // Determine the action based on the clicked slot
        switch (event.getSlot()) {
            // Handle language change
            case 1:
                player.closeInventory();
                GUIHelperLanguage = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString("trollsettings.title"), 18, plugin);

                // Add the available settings to the GUI
                GUIHelperLanguage.addItem(4, ItemBuilder.createItem(Material.PAPER, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.current-language") + " " + ChatColor.GRAY + plugin.getConfigHelper().getString("language")));
                GUIHelperLanguage.addItem(9, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.custom")));
                GUIHelperLanguage.addItem(10, ItemBuilder.createItem(Material.BLACK_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.de")));
                GUIHelperLanguage.addItem(11, ItemBuilder.createItem(Material.BLUE_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.en")));
                GUIHelperLanguage.addItem(12, ItemBuilder.createItem(Material.YELLOW_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.es")));
                GUIHelperLanguage.addItem(13, ItemBuilder.createItem(Material.LIGHT_BLUE_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.fr")));
                GUIHelperLanguage.addItem(14, ItemBuilder.createItem(Material.WHITE_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.nl")));
                GUIHelperLanguage.addItem(15, ItemBuilder.createItem(Material.RED_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.zh-cn")));
                GUIHelperLanguage.addItem(16, ItemBuilder.createItem(Material.RED_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString("trollsettings.lang.zh-tw")));
                GUIHelperLanguage.addItemWithLore(17, Material.CYAN_STAINED_GLASS, " ", configHelperLanguage.getString("guis.placeholder.description")); //TODO

                // Add placeholders
                byte[] placeholderArray = {0, 1, 2, 6, 7, 8};
                for (int slot : placeholderArray) {
                    GUIHelperLanguage.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString("guis.placeholder.description")));
                }
                final byte[] placeholderSlots1 = {3, 5};
                for (int slot : placeholderSlots1) {
                    GUIHelperLanguage.addItem(slot, ItemBuilder.createItemWithLore(Material.WHITE_STAINED_GLASS_PANE, " ", configHelperLanguage.getString("guis.placeholder.description")));
                }

                player.openInventory(GUIHelperLanguage.getGUI());

                // Toggle metrics
            case 2:
                toggleSetting(event, "metrics-enabled", Material.BOOK, settingsGUI, configHelperLanguage);

                // Toggle update check
            case 3:
                toggleSetting(event, "check-for-updates", Material.GLOWSTONE, settingsGUI, configHelperLanguage);

                // Toggle feature deactivation on quit
            case 4:
                toggleSetting(event, "deactivate-features-on-quit", Material.REDSTONE_LAMP, settingsGUI, configHelperLanguage);

                // Toggle teleport control
            case 5:
                toggleSetting(event, "control-teleport-back", Material.ENDER_PEARL, settingsGUI, configHelperLanguage);

                // Toggle fire setting
            case 6:
                toggleSetting(event, "set-fire", Material.FIRE_CHARGE, settingsGUI, configHelperLanguage);

                // Toggle block breaking
            case 7:
                toggleSetting(event, "break-blocks", Material.DIAMOND_PICKAXE, settingsGUI, configHelperLanguage);

            default:
                // No action for other slots

        }
    }

    // Handles the language change setting
    private void handleLanguageChangeGUI(InventoryClickEvent event, Player player, ConfigHelper configHelperLanguage) {
        // Cancel the event to prevent any unwanted interactions
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 9 -> {
                plugin.getConfigHelper().set("language", "custom");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 10 -> {
                plugin.getConfigHelper().set("language", "de");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 11 -> {
                plugin.getConfigHelper().set("language", "en");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 12 -> {
                plugin.getConfigHelper().set("language", "es");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 13 -> {
                plugin.getConfigHelper().set("language", "fr");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 14 -> {
                plugin.getConfigHelper().set("language", "nl");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 15 -> {
                plugin.getConfigHelper().set("language", "zh-cn");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 16 -> {
                plugin.getConfigHelper().set("language", "zh-tw");
                saveLanguageChange(player, configHelperLanguage);
            }
            case 17 -> {
                //TODO
            }
        }
    }

    // Saves the language change to the config ans send a success message
    private void saveLanguageChange(Player player, ConfigHelper configHelperLanguage) {
        // Save configuration and send the player a success message
        plugin.saveConfig();
        player.closeInventory();
        player.sendMessage(PLUGIN_PREFIX + ChatColor.GREEN + configHelperLanguage.getString("trollsettings.lang.successfully-changed"));
    }

    // Toggles a boolean setting and updates the corresponding item in the Settings GUI
    private void toggleSetting(InventoryClickEvent event, String key, Material material, GUIHelper settingsGUI, ConfigHelper configHelperLanguage) {
        boolean currentValue = plugin.getConfigHelper().getBoolean(key);
        boolean newValue = !currentValue;

        plugin.getConfigHelper().set(key, newValue);
        settingsGUI.addItem(event.getSlot(), ItemBuilder.createItemWithLore(material, ChatColor.WHITE + configHelperLanguage.getString("trollsettings." + key) + ChatColor.DARK_GRAY + " " + settingsGUI.getStatus(newValue), configHelperLanguage.getString("trollsettings." + key + "-description")));
        plugin.saveConfig();
    }

    // Retrieves the ControlUtil instance
    public ControlUtil getControlUtil() {
        return controlUtil;
    }
}