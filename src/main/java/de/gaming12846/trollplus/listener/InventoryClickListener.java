/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.constants.ConfigConstants;
import de.gaming12846.trollplus.constants.LangConstants;
import de.gaming12846.trollplus.constants.MetadataConstants;
import de.gaming12846.trollplus.utils.ConfigHelper;
import de.gaming12846.trollplus.utils.ControlHelper;
import de.gaming12846.trollplus.utils.GUIHelper;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
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
import java.util.concurrent.ThreadLocalRandom;

// Listener for handling inventory click events
public class InventoryClickListener implements Listener {
    private final TrollPlus plugin;
    public ControlHelper controlHelper;
    private GUIHelper guiHelperLanguageSettings;

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
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_FREEZE)) event.setCancelled(true);

        //  Cancel damage if the player has the "TROLLPLUS_CONTROL_TARGET" metadata
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET)) event.setCancelled(true);

        //  Cancel damage if the player has the "TROLLPLUS_SEMI_BAN" metadata
        if (player.hasMetadata(MetadataConstants.TROLLPLUS_SEMI_BAN)) event.setCancelled(true);

        // Retrieve the clicked inventory
        Inventory clickedInventory = event.getClickedInventory();

        // Check which GUI the clicked inventory matches
        if (isMatchingInventory(clickedInventory, plugin.getTrollCommand().getGUIHelperTroll())) {
            handleTrollGUI(event, player, configHelperLanguage);
        } else if (isMatchingInventory(clickedInventory, plugin.getTrollBowsCommand().getGUIHelperTrollBows())) {
            handleTrollBowsGUI(event, player, configHelperLanguage);
        } else if (isMatchingInventory(clickedInventory, plugin.getTrollPlusCommand().getGuiHelperSettings())) {
            handleSettingsGUI(event, player, configHelperLanguage);
        } else if (isMatchingInventory(clickedInventory, getGuiHelperLanguageSettings()))
            handleLanguageChangeGUI(event, player, configHelperLanguage);
    }

    // Helper method to check if the clicked inventory matches any of the GUIs
    private boolean isMatchingInventory(Inventory inventory, GUIHelper gui) {
        return gui != null && Objects.equals(inventory, gui.getGUI());
    }

    // Handles inventory click events in the Troll GUI
    private void handleTrollGUI(InventoryClickEvent event, Player player, ConfigHelper configHelperLanguage) {
        int slot = event.getSlot();
        GUIHelper trollGUI = plugin.getTrollCommand().getGUIHelperTroll().getGUIUtil();
        Player target = trollGUI.getTarget();

        // Cancel the event to prevent any unwanted interactions
        event.setCancelled(true);

        // If the target player is no longer online, close the inventory and notify the player
        if (!target.isOnline()) {
            player.closeInventory();
            player.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_PLAYER_QUIT));
            return;
        }

        // Random slot selection
        if (slot == 50) {
            int[] slots = {10, 12, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44};
            slot = slots[ThreadLocalRandom.current().nextInt(0, slots.length)];
        }

        // Determine the action based on the clicked slot
        switch (slot) {
            case 11 -> handleFreezeFeature(target, configHelperLanguage);
            case 12 -> handleHandItemDropFeature(target, configHelperLanguage);
            case 13 -> handleControlFeature(player, target, configHelperLanguage);
            case 14 -> handleFlipBehindFeature(target, configHelperLanguage);
            case 15 -> handleSpankFeature(target, configHelperLanguage);
            case 19 -> handleSpamMessagesFeature(target, configHelperLanguage);
            case 20 -> handleSpamSoundsFeature(target, configHelperLanguage);
            case 21 -> handleSemiBanFeature(target, configHelperLanguage);
            case 22 -> handleFallingAnvilsFeature(target, player, configHelperLanguage);
            case 23 -> handleTntTrackFeature(target, configHelperLanguage);
            case 24 -> handleMobSpawnerFeature(target, configHelperLanguage);
            case 25 -> handleSlowlyKillFeature(target, player, configHelperLanguage);
            case 28 -> handleRandomTeleportFeature(target, configHelperLanguage);
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
                target.setMetadata(MetadataConstants.TROLLPLUS_KILL, new FixedMetadataValue(plugin, target.getName()));
                target.setHealth(0.0);
            }
            case 35 ->
                player.openInventory(Objects.requireNonNull(target.getPlayer()).getEnderChest());
            case 48 -> handleVanishFeature(player, target, configHelperLanguage);
        }
    }

    // Handles the freeze feature, freezing or unfreezing the target player
    private void handleFreezeFeature(Player target, ConfigHelper configHelperLanguage) {
        double serverVersion = plugin.getServerVersion();
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_FREEZE)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_FREEZE, new FixedMetadataValue(plugin, target.getName()));
            if (serverVersion > 1.19)
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, Integer.MAX_VALUE, 6));
        } else {
            target.removeMetadata(MetadataConstants.TROLLPLUS_FREEZE, plugin);
            if (serverVersion > 1.19) target.removePotionEffect(PotionEffectType.SLOWNESS);
        }

        updateGUIHelperTroll(11, Material.BLUE_ICE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FREEZE), MetadataConstants.TROLLPLUS_FREEZE, configHelperLanguage.getString(LangConstants.TROLL_GUI_FREEZE_DESCRIPTION));
    }

    // Handles the hand item drop feature, continuously drops the item held in the target player's main hand
    private void handleHandItemDropFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_HAND_ITEM_DROP)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_HAND_ITEM_DROP, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_HAND_ITEM_DROP, plugin);

        // Update the GUI with the hand item drop status
        updateGUIHelperTroll(12, Material.SHEARS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_HAND_ITEM_DROP), MetadataConstants.TROLLPLUS_HAND_ITEM_DROP, configHelperLanguage.getString(LangConstants.TROLL_GUI_HAND_ITEM_DROP_DESCRIPTION));

        // Schedule the hand item drop task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the player no longer has the "TROLLPLUS_HAND_ITEM_DROP" metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_HAND_ITEM_DROP)) {
                    cancel();
                    return;
                }

                // Get the item in the player's main hand. If it's air (empty), do nothing
                ItemStack itemInHand = target.getInventory().getItemInMainHand();

                // If the hand is empty, do nothing
                if (itemInHand.getType() == Material.AIR) {
                    cancel(); // Optional: cancel the task if hand is empty
                    return;
                }

                // Drop a single item from the stack in the player's main hand
                ItemStack dropItem = new ItemStack(itemInHand.getType(), 1);
                dropItem.setItemMeta(itemInHand.getItemMeta());
                Item itemDrop = target.getWorld().dropItemNaturally(target.getLocation(), dropItem);
                itemDrop.setPickupDelay(40);

                // Decrease the amount of the item in the player's hand by one
                int newAmount = itemInHand.getAmount() - 1;
                itemInHand.setAmount(newAmount);

                // Update the player's inventory
                target.getInventory().setItemInMainHand(itemInHand);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.HAND_ITEM_DROP_PERIOD));
    }

    // Handles the control feature, control the player
    private void handleControlFeature(Player controller, Player target, ConfigHelper configHelperLanguage) {
        // Check if the player is trying to control themselves
        if (target.equals(controller)) {
            controller.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_CONTROL_NOT_AVAILABLE));
            return;
        }

        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET, new FixedMetadataValue(plugin, target.getName()));
            controller.setMetadata(MetadataConstants.TROLLPLUS_CONTROL_PLAYER, new FixedMetadataValue(plugin, controller.getName()));

            // Initialize the control utility and start controlling
            controlHelper = new ControlHelper(plugin, target, controller);
            controlHelper.control();
        } else {
            // Stop controlling the target player
            target.removeMetadata(MetadataConstants.TROLLPLUS_CONTROL_TARGET, plugin);
            controller.removeMetadata(MetadataConstants.TROLLPLUS_CONTROL_PLAYER, plugin);
        }

        // Update the GUI with the control status
        updateGUIHelperTroll(13, Material.LEAD, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_CONTROL), MetadataConstants.TROLLPLUS_CONTROL_TARGET, configHelperLanguage.getString(LangConstants.TROLL_GUI_CONTROL_DESCRIPTION));
    }

    // Retrieves the ControlHelper instance
    public ControlHelper getControlHelper() {
        return controlHelper;
    }

    // Handles the flip behind feature, flip the player 180° on interaction
    private void handleFlipBehindFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_FLIP_BEHIND)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_FLIP_BEHIND, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_FLIP_BEHIND, plugin);

        // Update the GUI with the flip behind status
        updateGUIHelperTroll(14, Material.COMPASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FLIP_BACKWARDS), MetadataConstants.TROLLPLUS_FLIP_BEHIND, configHelperLanguage.getString(LangConstants.TROLL_GUI_FLIP_BACKWARDS_DESCRIPTION));
    }

    // Handles the spank feature, continuously applies a random knockback ("spank") effect to the target
    private void handleSpankFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SPANK)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_SPANK, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_SPANK, plugin);

        // Update the GUI with the spank status
        updateGUIHelperTroll(15, Material.SLIME_BALL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SPANK), MetadataConstants.TROLLPLUS_SPANK, configHelperLanguage.getString(LangConstants.TROLL_GUI_SPANK_DESCRIPTION));

        // Schedule the spank task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the "TROLLPLUS_SPANK" metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SPANK)) {
                    cancel();
                    return;
                }

                // Generate random knockback vector components
                double x = ThreadLocalRandom.current().nextDouble(0.1, 1) - ThreadLocalRandom.current().nextDouble(0.1, 1);
                double y = ThreadLocalRandom.current().nextDouble(0.33, 1);
                double z = ThreadLocalRandom.current().nextDouble(0.1, 1) - ThreadLocalRandom.current().nextDouble(0.1, 1);

                // Apply the knockback to the target
                target.setVelocity(new Vector(x, y, z));
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.SPANK_PERIOD));
    }

    // Handles the spam messages feature, continuously spams the target player with randomly colored messages and titles
    private void handleSpamMessagesFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SPAM_MESSAGES)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_SPAM_MESSAGES, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_SPAM_MESSAGES, plugin);

        // Update the GUI with the spam messages status
        updateGUIHelperTroll(19, Material.WRITABLE_BOOK, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_MESSAGES), MetadataConstants.TROLLPLUS_SPAM_MESSAGES, configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_MESSAGES_DESCRIPTION));

        // List of spam messages
        List<String> spamMessages = configHelperLanguage.getStringList(LangConstants.SPAM_MESSAGES);

        // Schedule the spam messages task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the "TROLLPLUS_SPAM_MESSAGES" metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SPAM_MESSAGES)) {
                    cancel();
                    return;
                }

                // Get random messages for chat and title
                String chatMessage = getRandomColoredMessage(spamMessages);
                String titleMessage = getRandomColoredMessage(spamMessages);
                String subtitleMessage = getRandomColoredMessage(spamMessages);

                // Send chat and title messages to the target
                target.sendMessage(chatMessage);
                target.sendTitle(titleMessage, subtitleMessage, 3, 10, 3);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.SPAM_MESSAGES_PERIOD));
    }

    // Generates a randomly colored message from a list of message
    private String getRandomColoredMessage(List<String> messages) {
        // Select a random message from the provided list
        String randomMessage = messages.get(ThreadLocalRandom.current().nextInt(0, messages.size()));
        StringBuilder messageBuilder = new StringBuilder();

        // Append a random color to each character in the selected message
        for (Character character : randomMessage.toCharArray()) {
            // Get a random index between 0 and 15 (inclusive) for ChatColor
            ChatColor randomColor = ChatColor.getByChar(Integer.toHexString(ThreadLocalRandom.current().nextInt(0, 16)));
            messageBuilder.append(randomColor).append(character);
        }

        return messageBuilder.toString();
    }

    // Handles the spam sounds feature, continuously plays random sounds to the target player
    private void handleSpamSoundsFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SPAM_SOUNDS)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_SPAM_SOUNDS, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_SPAM_SOUNDS, plugin);

        // Update the GUI with the spam sounds status
        updateGUIHelperTroll(20, Material.NOTE_BLOCK, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_SOUNDS), MetadataConstants.TROLLPLUS_SPAM_SOUNDS, configHelperLanguage.getString(LangConstants.TROLL_GUI_SPAM_SOUNDS_DESCRIPTION));

        // List of annoying sounds
        List<Sound> annoyingSounds = Arrays.asList(Sound.ENTITY_PHANTOM_AMBIENT, Sound.ENTITY_PHANTOM_HURT, Sound.ENTITY_PHANTOM_DEATH, Sound.ENTITY_ENDERMAN_SCREAM, Sound.ENTITY_GHAST_SCREAM, Sound.ENTITY_GHAST_HURT, Sound.BLOCK_NOTE_BLOCK_HARP, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, Sound.BLOCK_NOTE_BLOCK_BELL, Sound.BLOCK_NOTE_BLOCK_BASS, Sound.BLOCK_NOTE_BLOCK_SNARE, Sound.BLOCK_ANVIL_USE, Sound.BLOCK_ANVIL_FALL, Sound.BLOCK_BELL_USE, Sound.BLOCK_BELL_RESONATE, Sound.BLOCK_IRON_DOOR_OPEN, Sound.BLOCK_IRON_DOOR_CLOSE, Sound.ENTITY_VILLAGER_NO, Sound.ENTITY_VILLAGER_HURT, Sound.ENTITY_VILLAGER_TRADE, Sound.ENTITY_VILLAGER_YES, Sound.BLOCK_PORTAL_AMBIENT, Sound.BLOCK_PORTAL_TRAVEL, Sound.BLOCK_LAVA_POP, Sound.BLOCK_LAVA_AMBIENT, Sound.BLOCK_END_PORTAL_SPAWN, Sound.BLOCK_FIRE_AMBIENT, Sound.BLOCK_FIRE_EXTINGUISH, Sound.ENTITY_SILVERFISH_AMBIENT, Sound.ENTITY_SILVERFISH_HURT, Sound.ENTITY_SILVERFISH_DEATH, Sound.ENTITY_CAT_HISS, Sound.ENTITY_ZOMBIE_CONVERTED_TO_DROWNED, Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED);

        // Schedule the spam sounds task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the spam sounds metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SPAM_SOUNDS)) {
                    cancel();
                    return;
                }

                // Play a random sound from the list
                Sound randomSound = annoyingSounds.get(ThreadLocalRandom.current().nextInt(annoyingSounds.size()));
                float volume = ThreadLocalRandom.current().nextFloat() * 2; // Volume range between 0 and 2
                float pitch = ThreadLocalRandom.current().nextFloat() * 2;  // Pitch range between 0 and 2
                target.playSound(target.getLocation(), randomSound, volume, pitch);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.SPAM_SOUNDS_PERIOD));
    }

    // Handles the semi ban feature, prevents the target from interacting and chatting
    private void handleSemiBanFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SEMI_BAN)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_SEMI_BAN, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_SEMI_BAN, plugin);

        // Update the GUI with the semi ban status
        updateGUIHelperTroll(21, Material.TRIPWIRE_HOOK, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SEMI_BAN), MetadataConstants.TROLLPLUS_SEMI_BAN, configHelperLanguage.getString(LangConstants.TROLL_GUI_SEMI_BAN_DESCRIPTION));
    }

    // Handles the falling anvils feature, continuously spawns falling anvils above the target player
    private void handleFallingAnvilsFeature(Player target, Player player, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_FALLING_ANVILS)) {
            // Clone the target's location
            Location loc = target.getLocation();

            // Check if the space above the target is clear for anvil placement
            for (int i = 0; i < 4; i++) {
                if (loc.getBlock().getType() == Material.AIR) {
                    loc.add(0, 1, 0);
                } else {
                    player.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_FALLING_ANVILS_NOT_AVAILABLE));
                    return;
                }
            }

            // Set metadata to indicate falling anvils feature is active
            target.setMetadata(MetadataConstants.TROLLPLUS_FALLING_ANVILS, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_FALLING_ANVILS, plugin);

        // Update the GUI with the falling anvils status
        updateGUIHelperTroll(22, Material.ANVIL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_FALLING_ANVILS), MetadataConstants.TROLLPLUS_FALLING_ANVILS, configHelperLanguage.getString(LangConstants.TROLL_GUI_FALLING_ANVILS_DESCRIPTION));

        // Schedule the falling anvils task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the falling anvils metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_FALLING_ANVILS)) {
                    cancel();
                    return;
                }

                // Get the location directly above the target player
                Location loc = target.getLocation().add(0, 3, 0);

                // Check if the location above is clear and set an anvil block
                if (loc.getBlock().getType() == Material.AIR) loc.getBlock().setType(Material.DAMAGED_ANVIL);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.FALLING_ANVILS_PERIOD));
    }

    // Handles the tnt track feature, continuously spawns primed TNT at the target player's location
    private void handleTntTrackFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_TNT_TRACK)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_TNT_TRACK, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_TNT_TRACK, plugin);

        // Update the GUI with the TNT spawning status
        updateGUIHelperTroll(23, Material.TNT, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_TNT_TRACK), MetadataConstants.TROLLPLUS_TNT_TRACK, configHelperLanguage.getString(LangConstants.TROLL_GUI_TNT_TRACK_DESCRIPTION));

        // Schedule the TNT track task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the TNT track metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_TNT_TRACK)) {
                    cancel();
                    return;
                }

                // Spawn the primed TNT and set its properties
                TNTPrimed tnt = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(80);
                tnt.setMetadata(MetadataConstants.TROLLPLUS_TNT, new FixedMetadataValue(plugin, tnt));
                tnt.getWorld().playSound(target.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.TNT_TRACK_PERIOD));
    }

    // Handles the mob spawner feature, continuously spawns random mobs near the target player
    private void handleMobSpawnerFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_MOB_SPAWNER)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_MOB_SPAWNER, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_MOB_SPAWNER, plugin);

        // Update the GUI with the mob spawner status
        updateGUIHelperTroll(24, Material.SPAWNER, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_MOB_SPAWNER), MetadataConstants.TROLLPLUS_MOB_SPAWNER, configHelperLanguage.getString(LangConstants.TROLL_GUI_MOB_SPAWNER_DESCRIPTION));

        // Define a list of hostile mobs
        List<EntityType> hostileMobs = Arrays.asList(EntityType.CREEPER, EntityType.SKELETON, EntityType.SPIDER, EntityType.ZOMBIE, EntityType.HUSK, EntityType.DROWNED, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.SILVERFISH, EntityType.BLAZE, EntityType.MAGMA_CUBE, EntityType.WITHER_SKELETON, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN, EntityType.PILLAGER, EntityType.VINDICATOR, EntityType.EVOKER, EntityType.ILLUSIONER, EntityType.WITCH, EntityType.PHANTOM, EntityType.SLIME, EntityType.VEX, EntityType.RAVAGER, EntityType.PIGLIN, EntityType.ZOGLIN, EntityType.STRAY, EntityType.CAVE_SPIDER);

        // Schedule the mob spawning task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the mob spawner metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_MOB_SPAWNER)) {
                    cancel();
                    return;
                }

                // Spawn a random hostile mob near the target player
                EntityType randomMob = hostileMobs.get(ThreadLocalRandom.current().nextInt(0, hostileMobs.size()));
                target.getWorld().spawnEntity(target.getLocation(), randomMob).setGlowing(true);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.MOB_SPAWNER_PERIOD));
    }

    // Handles the slowly kill feature, slowly damages the target player over time
    private void handleSlowlyKillFeature(Player target, Player player, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SLOWLY_KILL)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_SLOWLY_KILL, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_SLOWLY_KILL, plugin);

        // Update the GUI with the slowly kill status
        updateGUIHelperTroll(25, Material.SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SLOWLY_KILL), MetadataConstants.TROLLPLUS_SLOWLY_KILL, configHelperLanguage.getString(LangConstants.TROLL_GUI_SLOWLY_KILL_DESCRIPTION));

        // Schedule the slowly kill task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the slowly kill metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_SLOWLY_KILL)) {
                    cancel();
                    return;
                }

                // Handle game mode restrictions
                GameMode gameMode = target.getGameMode();
                if (gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR) {
                    player.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_SLOWLY_KILL_NOT_AVAILABLE));
                    target.removeMetadata(MetadataConstants.TROLLPLUS_SLOWLY_KILL, plugin);
                    updateGUIHelperTroll(25, Material.SKELETON_SKULL, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_SLOWLY_KILL), MetadataConstants.TROLLPLUS_SLOWLY_KILL, configHelperLanguage.getString(LangConstants.TROLL_GUI_SLOWLY_KILL_DESCRIPTION));
                    cancel();
                    return;
                }

                // Damage the target player
                target.damage(1);
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.SLOWLY_KILL_PERIOD));
    }

    // Handles the random teleport feature
    private void handleRandomTeleportFeature(Player target, ConfigHelper configHelperLanguage) {
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_RANDOM_TELEPORT)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_RANDOM_TELEPORT, new FixedMetadataValue(plugin, target.getName()));
        } else target.removeMetadata(MetadataConstants.TROLLPLUS_RANDOM_TELEPORT, plugin);

        // Update the GUI with the random teleport status
        updateGUIHelperTroll(28, Material.ENDER_EYE, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_TELEPORT), MetadataConstants.TROLLPLUS_RANDOM_TELEPORT, configHelperLanguage.getString(LangConstants.TROLL_GUI_RANDOM_TELEPORT_DESCRIPTION));

        // Schedule the random teleport task
        new BukkitRunnable() {
            @Override
            public void run() {
                // Cancel if the target no longer has the random teleport metadata
                if (!target.hasMetadata(MetadataConstants.TROLLPLUS_RANDOM_TELEPORT)) {
                    cancel();
                    return;
                }

                Location loc = target.getLocation();
                World world = target.getWorld();

                // Try to find a safe location
                for (int i = 0; i < 100; i++) {
                    double randomX = loc.getX() + (ThreadLocalRandom.current().nextDouble() * (16 * 2)) - 16;
                    double randomZ = loc.getZ() + (ThreadLocalRandom.current().nextDouble() * (16 * 2)) - 16;

                    int highestY = world.getHighestBlockYAt((int) randomX, (int) randomZ);
                    Location location = new Location(world, randomX, highestY, randomZ);

                    if (isSafeLocation(location)) {
                        target.teleport(location);
                        break;
                    }
                }
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getInt(ConfigConstants.RANDOM_TELEPORT_PERIOD));
    }

    private boolean isSafeLocation(Location loc) {
        World world = loc.getWorld();

        if (world != null) {
            // Check the block the player will stand on (below feet)
            Block blockBelow = world.getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ());

            // Check the space at feet and head level
            Block blockAtFeet = world.getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            Block blockAtHead = world.getBlockAt(loc.getBlockX(), loc.getBlockY() + 1, loc.getBlockZ());

            // Ensure block below is solid, and the space at feet and head are air or passable blocks
            return blockBelow.getType().isSolid() && (blockAtFeet.getType().isAir() || blockAtFeet.isPassable()) && (blockAtHead.getType().isAir() || blockAtHead.isPassable());
        }
        return false;
    }

    // Handles the inventory drop feature, drops all items from the target player's inventory at their current location
    private void handleInventoryDropFeature(Player target) {
        // Drop items from inventory
        for (ItemStack item : target.getInventory().getContents()) {
            if (item != null) {
                Item droppedItem = target.getWorld().dropItemNaturally(target.getLocation(), item);
                // Set pickup delay to prevent immediate pickup
                droppedItem.setPickupDelay(40);
            }
        }

        // Clear the inventory after dropping items
        target.getInventory().clear();
    }

    // Handles the inventory shuffle feature, shuffles the order of items in a player's inventory
    private void handleInventoryShuffleFeature(Player target) {
        Inventory inventory = target.getInventory();
        ItemStack[] items = inventory.getContents();

        // Shuffles the items in the inventory
        for (int i = 0; i < items.length / 2; i++) {
            ItemStack temp = items[i];
            items[i] = items[items.length - 1 - i];
            items[items.length - 1 - i] = temp;
        }

        // Update the player's inventory with the shuffled items
        inventory.setContents(items);
    }

    // Handles the random scary sound feature, plays a random scary sound near the target player
    private void handleRandomScarySoundFeature(Player target) {
        // List of scary ambient sounds
        List<Sound> scaryAmbientSounds = Arrays.asList(Sound.AMBIENT_CAVE, Sound.AMBIENT_BASALT_DELTAS_ADDITIONS, Sound.AMBIENT_BASALT_DELTAS_LOOP, Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_NETHER_WASTES_ADDITIONS, Sound.AMBIENT_NETHER_WASTES_LOOP, Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, Sound.AMBIENT_SOUL_SAND_VALLEY_LOOP, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_CRIMSON_FOREST_ADDITIONS, Sound.AMBIENT_CRIMSON_FOREST_LOOP, Sound.AMBIENT_CRIMSON_FOREST_MOOD, Sound.AMBIENT_WARPED_FOREST_ADDITIONS, Sound.AMBIENT_WARPED_FOREST_LOOP, Sound.AMBIENT_WARPED_FOREST_MOOD, Sound.BLOCK_PORTAL_AMBIENT, Sound.BLOCK_PORTAL_TRAVEL, Sound.BLOCK_END_PORTAL_SPAWN, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);
        // Select a random sound from the list
        Sound randomSound = scaryAmbientSounds.get(ThreadLocalRandom.current().nextInt(0, scaryAmbientSounds.size()));
        // Play the selected sound near the target player
        target.playSound(target.getLocation(), randomSound, plugin.getConfigHelper().getInt(ConfigConstants.RANDOM_SCARY_SOUND_VOLUME), 1);
    }

    // Handles the rocket feature, launching the target player into the air if conditions are met
    private void handleRocketFeature(Player player, Player target, ConfigHelper configHelperLanguage) {
        // Check if the target can be launched
        if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
            player.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_ROCKET_CANNOT_LAUNCH));
            target.removeMetadata(MetadataConstants.TROLLPLUS_ROCKET_NO_FALL_DAMAGE, plugin);
            return;
        }

        // Set metadata to prevent fall damage
        target.setMetadata(MetadataConstants.TROLLPLUS_ROCKET_NO_FALL_DAMAGE, new FixedMetadataValue(plugin, target.getName()));

        // Store the target's current flight status and temporarily allow flight if not allowed
        boolean targetInitiallyAllowedFlight = target.getAllowFlight();
        if (!targetInitiallyAllowedFlight) target.setAllowFlight(true);

        // Spawn initial launch particle at the player's location
        Particle particleType = plugin.getServerVersion() < 1.20 ? Particle.ASH : Particle.EXPLOSION;
        target.getWorld().spawnParticle(particleType, target.getLocation(), 1);

        // Create an array of particles to simulate the rocket launch
        Particle[] particles = {plugin.getServerVersion() < 1.20 ? Particle.FLASH : Particle.FIREWORK, Particle.LAVA, Particle.FLAME};
        for (Particle particle : particles) {
            target.getWorld().spawnParticle(particle, target.getLocation(), 25);
        }

        // Play explosion sound at the player's location
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

        // Schedule the rocket task
        new BukkitRunnable() {
            private final int maxRocketCharges = plugin.getConfigHelper().getInt(ConfigConstants.ROCKET_CHARGES);
            private int rocketCharges = 0;

            @Override
            public void run() {
                // Stop the rocket if the maximum charges are exceeded or if the player is below the highest block
                if (rocketCharges >= maxRocketCharges || target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                    // Restore flight status if it was not allowed initially
                    if (!targetInitiallyAllowedFlight) target.setAllowFlight(false);

                    // Inform the initiating player that the rocket launch was stopped
                    player.sendMessage(LangConstants.PLUGIN_PREFIX + plugin.getConfigHelperLanguage().getString(LangConstants.TROLL_ROCKET_LAUNCH_STOPPED));
                    cancel();
                    return;
                }

                // Propel the target upward
                target.setVelocity(target.getVelocity().setY(2));

                // Disable flying if the player was flying
                if (target.isFlying()) target.setFlying(false);
                rocketCharges++;
            }
        }.runTaskTimer(plugin, 0, plugin.getConfigHelper().getLong(ConfigConstants.ROCKET_PERIOD));
    }

    // Handles the freefall feature, simulates a freefall by teleporting the target player to a specified height above their current position
    private void handleFreefallFeature(Player target, Player player, ConfigHelper configHelperLanguage) {
        Location loc = target.getLocation().clone();
        loc.setY(loc.getY() + 1);

        // Calculate the highest possible freefall height based on the configuration
        int freefallHeight = plugin.getConfigHelper().getInt(ConfigConstants.FREEFALL_HEIGHT);
        boolean canFall = true;

        // Check if there is enough air above the player for the freefall
        for (int i = 0; i < freefallHeight; i++) {
            if (loc.getBlock().getType() != Material.AIR) {
                player.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_FREEFALL_CANNOT_FALL));
                canFall = false;
                // Exit loop early if a non-air block is found
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

    // Handles the fake ban feature, simulates banning a player by kicking them from the server with a custom message
    private void handleFakeBanFeature(Player target, ConfigHelper configHelperLanguage) {
        // Retrieve the fake ban message to be sent to the player
        String fakeBanMessagePlayer = configHelperLanguage.getString(LangConstants.FAKE_BAN_MESSAGE_PLAYER);

        // Kick the player with the fake ban message
        target.kickPlayer(fakeBanMessagePlayer);

        // Check if fake ban broadcast is enabled in the configuration
        if (plugin.getConfigHelper().getBoolean(ConfigConstants.FAKE_BAN_MESSAGE_BROADCAST_ENABLED)) {
            String fakeBanMessageBroadcast = configHelperLanguage.getString(LangConstants.FAKE_BAN_MESSAGE_BROADCAST);
            // Replace the placeholder with the target player's name
            String formattedBroadcastMessage = fakeBanMessageBroadcast.replace("[player]", target.getName());
            // Broadcast the fake ban message to all players
            Bukkit.broadcastMessage(formattedBroadcastMessage);
        }
    }

    // Handles the fake op feature, simulates giving a player operator status by sending them a fake message
    private void handleFakeOpFeature(Player target, ConfigHelper configHelperLanguage) {
        // Retrieve the fake op message from the configuration
        String fakeOpMessage = configHelperLanguage.getString(LangConstants.FAKE_OP_MESSAGE);
        // Replace the placeholder with the target player's name
        String formattedOpMessage = fakeOpMessage.replace("[player]", target.getName());

        // Check if fake op message broadcast is enabled in the configuration
        if (plugin.getConfigHelper().getBoolean(ConfigConstants.FAKE_OP_MESSAGE_BROADCAST_ENABLED)) {
            // Broadcast the fake op message to all players
            Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + formattedOpMessage);
        } else
            // Send the fake op message only to the target player
            target.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + formattedOpMessage);
    }

    // Handles the teleport Feature, teleporting the player to the target
    private void handleTeleportFeature(Player player, Player target, ConfigHelper configHelperLanguage) {
        // Check if the target is the same as the player
        if (target.equals(player)) {
            player.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_TELEPORT_NOT_AVAILABLE));
            return;
        }

        // Teleport the player to the target's location
        player.teleport(target);
    }

    // Handles the vanish feature, allowing the player to vanish or reappear
    private void handleVanishFeature(Player player, Player target, ConfigHelper configHelperLanguage) {
        // Check if the target is the same as the player
        if (target.equals(player)) {
            player.sendMessage(LangConstants.PLUGIN_PREFIX + configHelperLanguage.getString(LangConstants.TROLL_VANISH_NOT_AVAILABLE));
            return;
        }

        // Toggle vanish state
        if (!target.hasMetadata(MetadataConstants.TROLLPLUS_VANISH)) {
            target.setMetadata(MetadataConstants.TROLLPLUS_VANISH, new FixedMetadataValue(plugin, target.getName()));
            // Vanish the player
            target.hidePlayer(plugin, player);

            // Send vanish message if configured
            if (plugin.getConfigHelper().getBoolean(ConfigConstants.VANISH_QUIT_MESSAGE_ENABLED))
                player.sendMessage(configHelperLanguage.getString(LangConstants.VANISH_QUIT_MESSAGE).replace("[player]", player.getName()));
        } else {
            target.removeMetadata(MetadataConstants.TROLLPLUS_VANISH, plugin);
            // Reappear the player
            target.showPlayer(plugin, player);

            // Send reappear message if configured
            if (plugin.getConfigHelper().getBoolean(ConfigConstants.VANISH_JOIN_MESSAGE_ENABLED))
                player.sendMessage(configHelperLanguage.getString(LangConstants.VANISH_JOIN_MESSAGE).replace("[player]", player.getName()));
        }

        // Update the GUI with the vanish status
        updateGUIHelperTroll(48, Material.POTION, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLL_GUI_VANISH), MetadataConstants.TROLLPLUS_VANISH, configHelperLanguage.getString(LangConstants.TROLL_GUI_VANISH_DESCRIPTION));
    }

    // Updates the GUIHelperTroll
    private void updateGUIHelperTroll(Integer index, Material material, String name, String metadata, String lore) {
        plugin.getTrollCommand().getGUIHelperTroll().addItemWithLoreAndStatus(index, material, name, metadata, lore);
    }

    // Handles inventory click events in the TrollBows GUI
    private void handleTrollBowsGUI(InventoryClickEvent event, Player player, ConfigHelper configHelperLanguage) {
        // Cancel the event to prevent default inventory behavior
        event.setCancelled(true);

        // Determine the action based on the clicked slot
        int slot = event.getSlot();

        // Determine which bow to give based on the clicked slot
        String bowName;
        String bowDescription;

        // Determine the action based on the clicked slot
        switch (slot) {
            case 2 -> {
                bowName = configHelperLanguage.getString(LangConstants.TROLLBOWS_EXPLOSION_BOW);
                bowDescription = configHelperLanguage.getString(LangConstants.TROLLBOWS_EXPLOSION_BOW_DESCRIPTION);
            }
            case 3 -> {
                bowName = configHelperLanguage.getString(LangConstants.TROLLBOWS_TNT_BOW);
                bowDescription = configHelperLanguage.getString(LangConstants.TROLLBOWS_TNT_BOW_DESCRIPTION);
            }
            case 4 -> {
                bowName = configHelperLanguage.getString(LangConstants.TROLLBOWS_LIGHTNING_BOLT_BOW);
                bowDescription = configHelperLanguage.getString(LangConstants.TROLLBOWS_LIGHTNING_BOLT_BOW_DESCRIPTION);
            }
            case 5 -> {
                bowName = configHelperLanguage.getString(LangConstants.TROLLBOWS_SILVERFISH_BOW);
                bowDescription = configHelperLanguage.getString(LangConstants.TROLLBOWS_SILVERFISH_BOW_DESCRIPTION);
            }
            case 6 -> {
                bowName = configHelperLanguage.getString(LangConstants.TROLLBOWS_POTION_EFFECT_BOW);
                bowDescription = configHelperLanguage.getString(LangConstants.TROLLBOWS_POTION_EFFECT_BOW_DESCRIPTION);
            }
            default -> {
                return;
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
        // Cancel the event to prevent default behavior
        event.setCancelled(true);

        // Initialize GUI helper
        GUIHelper settingsGUI = plugin.getTrollPlusCommand().getGuiHelperSettings().getGUIUtil();

        // Determine the action based on the clicked slot
        switch (event.getSlot()) {
            // Handle language change
            case 1 -> {
                player.closeInventory();
                guiHelperLanguageSettings = new GUIHelper(ChatColor.BLACK + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_TITLE), 18, plugin);

                // Add the available settings to the GUI
                getGuiHelperLanguageSettings().addItem(4, ItemBuilder.createItem(Material.PAPER, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_CURRENT_LANGUAGE) + " " + ChatColor.GRAY + plugin.getConfigHelper().getString(ConfigConstants.LANGUAGE)));
                getGuiHelperLanguageSettings().addItem(9, ItemBuilder.createItem(Material.GRAY_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_CUSTOM)));
                getGuiHelperLanguageSettings().addItem(10, ItemBuilder.createItem(Material.BLACK_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_DE)));
                getGuiHelperLanguageSettings().addItem(11, ItemBuilder.createItem(Material.BLUE_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_EN)));
                getGuiHelperLanguageSettings().addItem(12, ItemBuilder.createItem(Material.YELLOW_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_ES)));
                getGuiHelperLanguageSettings().addItem(13, ItemBuilder.createItem(Material.LIGHT_BLUE_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_FR)));
                getGuiHelperLanguageSettings().addItem(14, ItemBuilder.createItem(Material.WHITE_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_NL)));
                getGuiHelperLanguageSettings().addItem(15, ItemBuilder.createItem(Material.RED_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_ZH_CN)));
                getGuiHelperLanguageSettings().addItem(16, ItemBuilder.createItem(Material.RED_STAINED_GLASS, ChatColor.WHITE + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_ZH_TW)));
                getGuiHelperLanguageSettings().addItemWithLore(17, Material.CYAN_STAINED_GLASS, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)); //TODO

                // Add placeholders
                byte[] placeholderArray = {0, 1, 2, 6, 7, 8};
                for (int slot : placeholderArray) {
                    getGuiHelperLanguageSettings().addItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)));
                }
                final byte[] placeholderSlots1 = {3, 5};
                for (int slot : placeholderSlots1) {
                    getGuiHelperLanguageSettings().addItem(slot, ItemBuilder.createItemWithLore(Material.WHITE_STAINED_GLASS_PANE, " ", configHelperLanguage.getString(LangConstants.GUI_PLACEHOLDER_DESCRIPTION)));
                }

                player.openInventory(getGuiHelperLanguageSettings().getGUI());
            }
            // Toggle metrics
            case 2 ->
                    toggleSetting(event, ConfigConstants.METRICS_ENABLED, Material.BOOK, settingsGUI, configHelperLanguage);
            // Toggle update check
            case 3 ->
                    toggleSetting(event, ConfigConstants.CHECK_FOR_UPDATES, Material.GLOWSTONE, settingsGUI, configHelperLanguage);
            // Toggle feature deactivation on quit
            case 4 ->
                    toggleSetting(event, ConfigConstants.DEACTIVATE_FEATURES_ON_QUIT, Material.REDSTONE_LAMP, settingsGUI, configHelperLanguage);
            // Toggle teleport control
            case 5 ->
                    toggleSetting(event, ConfigConstants.CONTROL_TELEPORT_BACK, Material.ENDER_PEARL, settingsGUI, configHelperLanguage);
            // Toggle fire setting
            case 6 ->
                    toggleSetting(event, ConfigConstants.SET_FIRE, Material.FIRE_CHARGE, settingsGUI, configHelperLanguage);
            // Toggle block breaking
            case 7 ->
                    toggleSetting(event, ConfigConstants.BREAK_BLOCKS, Material.DIAMOND_PICKAXE, settingsGUI, configHelperLanguage);
        }
    }

    // Toggles a boolean setting and updates the corresponding item in the Settings GUI
    private void toggleSetting(InventoryClickEvent event, String key, Material material, GUIHelper settingsGUI, ConfigHelper configHelperLanguage) {
        boolean currentValue = plugin.getConfigHelper().getBoolean(key);
        boolean newValue = !currentValue;

        plugin.getConfigHelper().set(key, newValue);
        settingsGUI.addItem(event.getSlot(), ItemBuilder.createItemWithLore(material, ChatColor.WHITE + configHelperLanguage.getString("trollsettings." + key) + ChatColor.DARK_GRAY + " " + settingsGUI.getStatus(newValue), configHelperLanguage.getString("trollsettings." + key + "-description")));
        plugin.saveConfig();
    }

    // Retrieves the guiHelperLanguageSettings instance
    public GUIHelper getGuiHelperLanguageSettings() {
        return guiHelperLanguageSettings;
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

    // Saves the language change to the config and send a success message
    private void saveLanguageChange(Player player, ConfigHelper configHelperLanguage) {
        // Save configuration and send the player a success message
        plugin.saveConfig();

        player.closeInventory();
        player.sendMessage(LangConstants.PLUGIN_PREFIX + ChatColor.GREEN + configHelperLanguage.getString(LangConstants.TROLLSETTINGS_LANG_SUCCESSFULLY_CHANGED));
    }
}