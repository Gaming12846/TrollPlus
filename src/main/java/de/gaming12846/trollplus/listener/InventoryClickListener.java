/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.ConfigUtil;
import de.gaming12846.trollplus.utils.ControlUtil;
import de.gaming12846.trollplus.utils.GUIUtil;
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

import static de.gaming12846.trollplus.utils.Constants.PLUGIN_PREFIX;

// Listener for handling inventory click events
public class InventoryClickListener implements Listener {
    private final TrollPlus plugin;
    public ControlUtil controlUtil;
    private GUIUtil languageGUI;

    // Constructor for the InventoryClickListener
    public InventoryClickListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Event handler for the InventoryClickEvent
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ConfigUtil langConfig = plugin.getLanguageConfig();
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
        if (isMatchingInventory(clickedInventory, plugin.getTrollCommand().trollGUI)) {
            handleTrollGUI(event, player, langConfig);
        } else if (isMatchingInventory(clickedInventory, plugin.getTrollBowsCommand().trollBowsGUI)) {
            handleTrollBowsGUI(event, player, langConfig);
        } else if (isMatchingInventory(clickedInventory, plugin.getTrollPlusCommand().settingsGUI)) {
            handleSettingsGUI(event, player, langConfig);
        } else if (isMatchingInventory(clickedInventory, languageGUI))
            handleLanguageChangeGUI(event, player, langConfig);
    }

    // Helper method to check if the clicked inventory matches any of the GUIs
    private boolean isMatchingInventory(Inventory inventory, GUIUtil gui) {
        return gui != null && Objects.equals(inventory, gui.getGUI());
    }

    // Handles inventory click events in the Troll GUI
    private void handleTrollGUI(InventoryClickEvent event, Player player, ConfigUtil langConfig) {
        int slot = event.getSlot();
        GUIUtil trollGUI = plugin.getTrollCommand().trollGUI.getGUIUtil();
        Player target = trollGUI.getTarget();

        // Cancel the event to prevent any unwanted interactions
        event.setCancelled(true);

        // If the target player is no longer online, close the inventory and notify the player
        if (!target.isOnline()) {
            player.closeInventory();
            player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.player-quit"));
            return;
        }

        // Random slot selection
        if (slot == 49) {
            int[] slots = {10, 12, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44};
            slot = slots[RandomUtils.nextInt(0, slots.length)];
        }

        // Determine the action based on the clicked slot
        switch (slot) {
            case 45:
                handleVanishFeature(player, target, trollGUI, langConfig);
                break;
            case 46:
                handleTeleportFeature(player, target, langConfig);
                break;
            case 47:
                target.setMetadata("TROLLPLUS_KILL", new FixedMetadataValue(plugin, target.getName()));
                target.setHealth(0.0);
                break;
            case 51:
                player.openInventory(Objects.requireNonNull(target.getPlayer()).getInventory());
                break;
            case 52:
                player.openInventory(Objects.requireNonNull(target.getPlayer()).getEnderChest());
                break;
            case 53:
                player.closeInventory();
                break;
            case 10:
                handleFreezeFeature(target, trollGUI, langConfig);
                break;
            case 12:
                handleHandItemDropFeature(target, trollGUI, langConfig);
                break;
            case 14:
                handleControlFeature(player, target, trollGUI, langConfig);
                break;
            case 16:
                handleFlipBehindFeature(target, trollGUI, langConfig);
                break;
            case 18:
                handleSpankFeature(target, trollGUI, langConfig);
                break;
            case 20:
                handleSpamMessagesFeature(target, trollGUI, langConfig);
                break;
            case 22:
                handleSpamSoundsFeature(target, trollGUI, langConfig);
                break;
            case 24:
                handleSemiBanFeature(target, trollGUI, langConfig);
                break;
            case 26:
                handleFallingAnvilsFeature(target, player, trollGUI, langConfig);
                break;
            case 28:
                handleTntTrackFeature(target, trollGUI, langConfig);
                break;
            case 30:
                handleMobSpawnerFeature(target, trollGUI, langConfig);
                break;
            case 32:
                handleSlowlyKillFeature(target, player, trollGUI, langConfig);
                break;
            case 34:
                handleRandomScarySoundFeature(target);
                break;
            case 36:
                handleInventoryDropFeature(target);
                break;
            case 38:
                handleRocketFeature(player, target, langConfig);
                break;
            case 40:
                handleFakeBanFeature(target, langConfig);
                break;
            case 42:
                handleFakeOpFeature(target, langConfig);
                break;
            case 44:
                handleFreefallFeature(target, player, langConfig);
                break;
            default:
                // No action for other slots
                break;
        }
    }

    // Handles the vanish feature, allowing the player to vanish or reappear
    private void handleVanishFeature(Player player, Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (target == player) {
            player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.vanish-not-available"));
            return;
        }

        if (!target.hasMetadata("TROLLPLUS_VANISH")) {
            target.setMetadata("TROLLPLUS_VANISH", new FixedMetadataValue(plugin, target.getName()));
            target.hidePlayer(plugin, player);
            if (plugin.getConfig().getBoolean("vanish.join-message-enabled", true) && langConfig.getString("vanish.quit-message") != null)
                player.sendMessage(langConfig.getString("vanish.quit-message").replace("[player]", player.getName()));
        } else {
            target.removeMetadata("TROLLPLUS_VANISH", plugin);
            target.showPlayer(plugin, player);
            if (plugin.getConfig().getBoolean("vanish.join-message-enabled", true) && langConfig.getString("vanish.join-message") != null)
                player.sendMessage(langConfig.getString("vanish.join-message").replace("[player]", player.getName()));
        }

        trollGUI.addItem(45, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + langConfig.getString("troll-gui.vanish") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_VANISH"), langConfig.getString("troll-gui.vanish-description")));
    }

    // Handles the teleport Feature, teleporting the player to the target
    private void handleTeleportFeature(Player player, Player target, ConfigUtil langConfig) {
        if (target == player) {
            player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.teleport-not-available"));
            return;
        }
        player.teleport(target);
    }

    // Handles the freeze feature, freezing or unfreezing the target player
    private void handleFreezeFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_FREEZE")) {
            target.setMetadata("TROLLPLUS_FREEZE", new FixedMetadataValue(plugin, target.getName()));
            if (plugin.getServerVersion() > 1.19)
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 6));
        } else {
            target.removeMetadata("TROLLPLUS_FREEZE", plugin);
            if (plugin.getServerVersion() > 1.19) target.removePotionEffect(PotionEffectType.SLOWNESS);
        }

        trollGUI.addItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + langConfig.getString("troll-gui.freeze") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_FREEZE"), langConfig.getString("troll-gui.freeze-description")));
    }

    // Handles the hand item drop feature, continuously drops the item held in the target player's main hand
    private void handleHandItemDropFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
            target.setMetadata("TROLLPLUS_HAND_ITEM_DROP", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_HAND_ITEM_DROP", plugin);

        trollGUI.addItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-gui.hand-item-drop") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_HAND_ITEM_DROP"), langConfig.getString("troll-gui.hand-item-drop-description")));
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
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("hand-item-drop-period"));
    }

    // Handles the control feature, control the player
    private void handleControlFeature(Player player, Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        // Check if the player is trying to control themselves
        if (target.equals(player)) {
            player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.control-not-available"));
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

        trollGUI.addItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-gui.control") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_CONTROL_TARGET"), langConfig.getString("troll-gui.control-description")));
    }

    // Handles the flip behind feature, flip the player 180° on interaction
    private void handleFlipBehindFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
            target.setMetadata("TROLLPLUS_FLIP_BEHIND", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_FLIP_BEHIND", plugin);

        trollGUI.addItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-gui.flip-backwards") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_FLIP_BEHIND"), langConfig.getString("troll-gui.flip-backwards-description")));
    }

    // Handles the spank feature, continuously applies a random knockback ("spank") effect to the target
    private void handleSpankFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_SPANK")) {
            target.setMetadata("TROLLPLUS_SPANK", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SPANK", plugin);

        trollGUI.addItem(18, ItemBuilder.createItemWithLore(Material.SNOWBALL, ChatColor.WHITE + langConfig.getString("troll-gui.spank") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_SPANK"), langConfig.getString("troll-gui.spank-description")));

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
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("spank-period"));
    }

    // Handles the spam messages feature, continuously spams the target player with randomly colored messages and titles
    private void handleSpamMessagesFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
            target.setMetadata("TROLLPLUS_SPAM_MESSAGES", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SPAM_MESSAGES", plugin);

        trollGUI.addItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-messages") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_SPAM_MESSAGES"), langConfig.getString("troll-gui.spam-messages-description")));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                    cancel();
                    return;
                }

                List<String> spamMessages = langConfig.getStringList("spam-messages");
                String chatMessage = getRandomColoredMessage(spamMessages);
                String titleMessage = getRandomColoredMessage(spamMessages);
                String subtitleMessage = getRandomColoredMessage(spamMessages);

                target.sendMessage(chatMessage);
                target.sendTitle(titleMessage, subtitleMessage, 3, 10, 3);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("spam-messages-period"));
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
    private void handleSpamSoundsFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
            target.setMetadata("TROLLPLUS_SPAM_SOUNDS", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SPAM_SOUNDS", plugin);

        trollGUI.addItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-gui.spam-sounds") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_SPAM_SOUNDS"), langConfig.getString("troll-gui.spam-sounds-description")));

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
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("spam-sounds-period"));
    }

    // Handles the semi ban feature, prevents the target from interacting and chatting
    private void handleSemiBanFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            target.setMetadata("TROLLPLUS_SEMI_BAN", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SEMI_BAN", plugin);

        trollGUI.addItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-gui.semi-ban") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_SEMI_BAN"), langConfig.getString("troll-gui.semi-ban-description")));
    }

    // Handles the falling anvils feature, continuously spawns falling anvils above the target player
    private void handleFallingAnvilsFeature(Player target, Player player, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_FALLING_ANVILS")) {
            Location loc = target.getLocation();  // Clone the location to avoid modifying the original

            for (int i = 0; i < 4; i++) {
                if (loc.getBlock().getType() == Material.AIR) {
                    loc.add(0, 1, 0);
                } else {
                    player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.falling-anvils-not-available"));
                    return;
                }
            }

            target.setMetadata("TROLLPLUS_FALLING_ANVILS", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_FALLING_ANVILS", plugin);

        trollGUI.addItem(26, ItemBuilder.createItemWithLore(Material.ANVIL, ChatColor.WHITE + langConfig.getString("troll-gui.falling-anvils") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_FALLING_ANVILS"), langConfig.getString("troll-gui.falling-anvils-description")));

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
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("falling-anvils-period"));
    }

    // Handles the tnt track feature, continuously spawns primed TNT at the target player's location
    private void handleTntTrackFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
            target.setMetadata("TROLLPLUS_TNT_TRACK", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_TNT_TRACK", plugin);

        trollGUI.addItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + langConfig.getString("troll-gui.tnt-track") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_TNT_TRACK"), langConfig.getString("troll-gui.tnt-track-description")));

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
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("tnt-track-period"));
    }

    // Handles the mob spawner feature, continuously spawns random mobs near the target player
    private void handleMobSpawnerFeature(Player target, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
            target.setMetadata("TROLLPLUS_MOB_SPAWNER", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_MOB_SPAWNER", plugin);

        trollGUI.addItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-gui.mob-spawner") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_MOB_SPAWNER"), langConfig.getString("troll-gui.mob-spawner-description")));

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
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("mob-spawner-period"));
    }

    // Handles the slowly kill feature, slowly damages the target player over time
    private void handleSlowlyKillFeature(Player target, Player player, GUIUtil trollGUI, ConfigUtil langConfig) {
        if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
            target.setMetadata("TROLLPLUS_SLOWLY_KILL", new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);

        trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-gui.slowly-kill-description")));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
                    cancel();
                    return;
                }

                if (target.getGameMode() == GameMode.CREATIVE || target.getGameMode() == GameMode.SPECTATOR) {
                    player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.slowly-kill-not-available"));
                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    GUIUtil trollGUI = plugin.getTrollCommand().trollGUI.getGUIUtil();
                    trollGUI.addItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-gui.slowly-kill") + " " + trollGUI.getStatusTrollGUI("TROLLPLUS_SLOWLY_KILL"), langConfig.getString("troll-gui.slowly-kill-description")));
                    cancel();
                    return;
                }

                target.damage(1);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfig().getInt("slowly-kill-period"));
    }

    // Handles the random scary sound feature, plays a random scary sound near the target player
    private void handleRandomScarySoundFeature(Player target) {
        List<Sound> sounds;
        if (plugin.getServerVersion() < 1.16) {
            sounds = Arrays.asList(Sound.AMBIENT_CAVE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE);
        } else
            sounds = Arrays.asList(Sound.AMBIENT_CAVE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, Sound.AMBIENT_CRIMSON_FOREST_MOOD, Sound.AMBIENT_CRIMSON_FOREST_ADDITIONS, Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_NETHER_WASTES_ADDITIONS, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, Sound.AMBIENT_WARPED_FOREST_MOOD, Sound.AMBIENT_WARPED_FOREST_ADDITIONS);
        Sound randomSound = sounds.get(RandomUtils.nextInt(0, sounds.size()));
        target.playSound(target.getLocation(), randomSound, plugin.getConfig().getInt("random-scary-sound-volume"), 1);
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

    // Handles the rocket feature, launching the target player into the air if conditions are met
    private void handleRocketFeature(Player player, Player target, ConfigUtil langConfig) {
        if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
            player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.rocket-cannot-launch"));
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
            private final int maxRocketCharges = plugin.getConfig().getInt("rocket.charges");
            private int rocketCharges = 0;

            @Override
            public void run() {
                // Stop the rocket if the maximum charges are exceeded or if the player is below the highest block
                if (rocketCharges >= maxRocketCharges || target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                    // Restore flight status if it was not allowed initially
                    if (!targetInitiallyAllowedFlight) target.setAllowFlight(false);

                    // Inform the initiating player that the rocket launch was stopped
                    player.sendMessage(PLUGIN_PREFIX + plugin.getLanguageConfig().getString("troll.rocket-launch-stopped"));
                    cancel();
                    return;
                }

                // Propel the target upward
                target.setVelocity(target.getVelocity().setY(2));

                // Disable flying if the player was flying
                if (target.isFlying()) target.setFlying(false);

                rocketCharges++;
            }
        }.runTaskTimer(plugin, 0, plugin.getConfig().getLong("rocket.period"));
    }

    // Handles the fake ban feature, simulates banning a player by kicking them from the server with a custom message
    private void handleFakeBanFeature(Player target, ConfigUtil langConfig) {
        // Retrieve the fake ban message to be sent to the player
        String fakeBanMessagePlayer = langConfig.getString("fake-ban.message-player");

        // Kick the player with the fake ban message
        target.kickPlayer(fakeBanMessagePlayer);

        // Check if fake ban broadcast is enabled in the configuration
        if (plugin.getConfig().getBoolean("fake-ban-message-broadcast-enabled", true)) {
            String fakeBanMessageBroadcast = langConfig.getString("fake-ban.message-broadcast");

            // Replace the placeholder with the target player's name
            String formattedBroadcastMessage = fakeBanMessageBroadcast.replace("[player]", target.getName());

            // Broadcast the fake ban message to all players
            Bukkit.broadcastMessage(formattedBroadcastMessage);
        }
    }

    // Handles the fake op feature, simulates giving a player operator status by sending them a fake message
    private void handleFakeOpFeature(Player target, ConfigUtil langConfig) {
        // Retrieve the fake op message from the configuration
        String fakeOpMessage = langConfig.getString("fake-op-message");

        // Replace the placeholder with the target player's name
        String formattedOpMessage = fakeOpMessage.replace("[player]", target.getName());

        // Check if fake op message broadcast is enabled in the configuration
        if (plugin.getConfig().getBoolean("fake-op-message-broadcast-enabled", true)) {
            // Broadcast the fake op message to all players
            Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + formattedOpMessage);
        } else
            // Send the fake op message only to the target player
            target.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + formattedOpMessage);
    }

    // Handles the freefall feature, simulates a freefall by teleporting the target player to a specified height above their current position
    private void handleFreefallFeature(Player target, Player player, ConfigUtil langConfig) {
        Location loc = target.getLocation().clone();
        loc.setY(loc.getY() + 1);

        // Calculate the highest possible freefall height based on the configuration
        int freefallHeight = plugin.getConfig().getInt("freefall-height");
        boolean canFall = true;

        // Check if there is enough air above the player for the freefall
        for (int i = 0; i < freefallHeight; i++) {
            if (loc.getBlock().getType() != Material.AIR) {
                player.sendMessage(PLUGIN_PREFIX + langConfig.getString("troll.freefall-cannot-fall"));
                canFall = false;
                break;
            }
            loc.setY(loc.getY() + 1);
        }

        // If freefall is possible, disable flying and teleport the target player
        if (canFall) {
            if (target.isFlying()) target.setFlying(false);
            target.teleport(loc);
        }
    }

    // Handles inventory click events in the TrollBows GUI
    private void handleTrollBowsGUI(InventoryClickEvent event, Player player, ConfigUtil langConfig) {
        // Cancel the event to prevent default inventory behavior
        event.setCancelled(true);

        // Determine the action based on the clicked slot
        int slot = event.getSlot();

        if (slot == 8) {
            player.closeInventory();
            return;
        }

        // Determine which bow to give based on the clicked slot
        String bowName = null;
        String bowDescription = null;

        // Determine the action based on the clicked slot
        switch (slot) {
            case 0:
                bowName = langConfig.getString("trollbows.explosion-bow");
                bowDescription = langConfig.getString("trollbows.explosion-bow-description");
                break;
            case 1:
                bowName = langConfig.getString("trollbows.tnt-bow");
                bowDescription = langConfig.getString("trollbows.tnt-bow-description");
                break;
            case 2:
                bowName = langConfig.getString("trollbows.lightning-bolt-bow");
                bowDescription = langConfig.getString("trollbows.lightning-bolt-bow-description");
                break;
            case 3:
                bowName = langConfig.getString("trollbows.silverfish-bow");
                bowDescription = langConfig.getString("trollbows.silverfish-bow-description");
                break;
            default:
                // No action for other slots
                return;
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
    private void handleSettingsGUI(InventoryClickEvent event, Player player, ConfigUtil langConfig) {
        GUIUtil settingsGUI = plugin.getTrollPlusCommand().settingsGUI.getGUIUtil();

        // Cancel the event to prevent default behavior
        event.setCancelled(true);

        // Determine the action based on the clicked slot
        switch (event.getSlot()) {
            case 26:
                player.closeInventory();
                break;
            // Handle language change
            case 10:
                player.closeInventory();
                languageGUI = new GUIUtil(langConfig.getString("trollsettings.title"), 27, plugin);

                // Add the available settings to the GUI
                languageGUI.addItem(4, ItemBuilder.createItem(Material.PAPER, ChatColor.WHITE + langConfig.getString("trollsettings.lang.current-language") + " " + ChatColor.GRAY + plugin.getConfig().getString("language")));
                languageGUI.addItem(26, ItemBuilder.createItemWithLore(Material.BARRIER, ChatColor.RED + langConfig.getString("guis.close"), langConfig.getString("guis.close-description")));
                languageGUI.addItem(9, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.custom")));
                languageGUI.addItem(10, ItemBuilder.createItem(Material.BLACK_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.de")));
                languageGUI.addItem(11, ItemBuilder.createItem(Material.BLUE_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.en")));
                languageGUI.addItem(12, ItemBuilder.createItem(Material.YELLOW_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.es")));
                languageGUI.addItem(13, ItemBuilder.createItem(Material.LIGHT_BLUE_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.fr")));
                languageGUI.addItem(14, ItemBuilder.createItem(Material.WHITE_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.nl")));
                languageGUI.addItem(15, ItemBuilder.createItem(Material.RED_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.zh-cn")));
                languageGUI.addItem(16, ItemBuilder.createItem(Material.RED_STAINED_GLASS, ChatColor.WHITE + langConfig.getString("trollsettings.lang.zh-tw")));
                languageGUI.addItemWithLore(17, Material.CYAN_STAINED_GLASS, " ", langConfig.getString("guis.placeholder.description"));

                // Add placeholders
                byte[] placeholderArray = {0, 1, 2, 3, 5, 6, 7, 8, 18, 19, 20, 21, 22, 23, 24, 25};
                for (int slot : placeholderArray) {
                    languageGUI.addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", langConfig.getString("guis.placeholder.description")));
                }

                player.openInventory(languageGUI.getGUI());
                break;
            // Toggle metrics
            case 11:
                toggleSetting(event, "metrics-enabled", Material.BOOK, settingsGUI, langConfig);
                break;
            // Toggle update check
            case 12:
                toggleSetting(event, "check-for-updates", Material.GLOWSTONE, settingsGUI, langConfig);
                break;
            // Toggle feature deactivation on quit
            case 13:
                toggleSetting(event, "deactivate-features-on-quit", Material.REDSTONE_LAMP, settingsGUI, langConfig);
                break;
            // Toggle teleport control
            case 14:
                toggleSetting(event, "control-teleport-back", Material.ENDER_PEARL, settingsGUI, langConfig);
                break;
            // Toggle fire setting
            case 15:
                toggleSetting(event, "set-fire", Material.FIRE_CHARGE, settingsGUI, langConfig);
                break;
            // Toggle block breaking
            case 16:
                toggleSetting(event, "break-blocks", Material.DIAMOND_PICKAXE, settingsGUI, langConfig);
                break;
            default:
                // No action for other slots
                break;
        }
    }

    // Handles the language change setting
    private void handleLanguageChangeGUI(InventoryClickEvent event, Player player, ConfigUtil langConfig) {
        // Cancel the event to prevent any unwanted interactions
        event.setCancelled(true);

        switch (event.getSlot()) {
            case 26:
                player.closeInventory();
                break;
            case 9:
                plugin.getConfig().set("language", "custom");
                saveLanguageChange(player, langConfig);
                break;
            case 10:
                plugin.getConfig().set("language", "de");
                saveLanguageChange(player, langConfig);
                break;
            case 11:
                plugin.getConfig().set("language", "en");
                saveLanguageChange(player, langConfig);
                break;
            case 12:
                plugin.getConfig().set("language", "es");
                saveLanguageChange(player, langConfig);
                break;
            case 13:
                plugin.getConfig().set("language", "fr");
                saveLanguageChange(player, langConfig);
                break;
            case 14:
                plugin.getConfig().set("language", "nl");
                saveLanguageChange(player, langConfig);
                break;
            case 15:
                plugin.getConfig().set("language", "zh-cn");
                saveLanguageChange(player, langConfig);
                break;
            case 16:
                plugin.getConfig().set("language", "zh-tw");
                saveLanguageChange(player, langConfig);
                break;
            default:
                // No action for other slots
                break;
        }
    }

    // Saves the language change to the config ans send a success message
    private void saveLanguageChange(Player player, ConfigUtil langConfig) {
        // Save configuration and send the player a success message
        plugin.saveConfig();
        player.closeInventory();
        player.sendMessage(PLUGIN_PREFIX + ChatColor.GREEN + langConfig.getString("trollsettings.lang.successfully-changed"));
    }

    // Toggles a boolean setting and updates the corresponding item in the Settings GUI
    private void toggleSetting(InventoryClickEvent event, String key, Material material, GUIUtil settingsGUI, ConfigUtil langConfig) {
        boolean currentValue = plugin.getConfig().getBoolean(key);
        boolean newValue = !currentValue;

        plugin.getConfig().set(key, newValue);
        settingsGUI.addItem(event.getSlot(), ItemBuilder.createItemWithLore(material, ChatColor.WHITE + langConfig.getString("trollsettings." + key) + ChatColor.DARK_GRAY + " " + settingsGUI.getStatusSettingsGUI(newValue), langConfig.getString("trollsettings." + key + "-description")));
        plugin.saveConfig();
    }

    // Retrieves the ControlUtil instance
    public ControlUtil getControlUtil() {
        return controlUtil;
    }
}