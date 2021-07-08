package com.github.gaming12846.trollplus.listener;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.ItemBuilder;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
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

/**
 * TrollPlus com.github.gaming12846.trollplus.listener InventoryClickListener.java
 *
 * @author Gaming12846
 */
public final class InventoryClickListener implements Listener {

    private final TrollPlus plugin;

    public InventoryClickListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            event.setCancelled(true);
        }

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            event.setCancelled(true);
        }


        Player target = VMConstants.TARGET;

        if (target == null || !event.getView().getTitle().equals("Trollmenu " + ChatColor.RED + ChatColor.BOLD + target.getName())) {
            return;
        }

        event.setCancelled(true);

        switch (event.getSlot()) {
            default:
                return;

            // Features
            case 53:
                player.closeInventory();

                break;
            case 51:
                player.openInventory(target.getPlayer().getInventory());

                break;
            case 50:
                target.setHealth(0.0);

                break;
            case 48:
                player.teleport(target);

                break;
            case 47:
                if (!target.hasMetadata("TROLLPLUS_VANISH")) {
                    target.setMetadata("TROLLPLUS_VANISH", new FixedMetadataValue(plugin, target.getName()));
                    target.hidePlayer(player);
                    VMConstants.STATUS_VANISH = "§a§lTarget";
                    VMConstants.TROLLMENU.setItem(47, ItemBuilder.createItem(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH));
                    return;
                }

                if (VMConstants.STATUS_VANISH == "§a§lTarget") {
                    target.showPlayer(player);
                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        online.hidePlayer(player);
                    }
                    VMConstants.STATUS_VANISH = "§b§lAll";
                    VMConstants.TROLLMENU.setItem(47, ItemBuilder.createItem(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH));
                    return;
                }

                target.removeMetadata("TROLLPLUS_VANISH", plugin);
                for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                    online.showPlayer(player);
                }
                VMConstants.STATUS_VANISH = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(47, ItemBuilder.createItem(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH));

                break;
            case 10:
                if (!target.hasMetadata("TROLLPLUS_FREEZE")) {
                    target.setMetadata("TROLLPLUS_FREEZE", new FixedMetadataValue(plugin, target.getName()));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 6));
                    VMConstants.STATUS_FREEZE = "§a§lON";
                    VMConstants.TROLLMENU.setItem(10, ItemBuilder.createItem(Material.ICE, 1, 0, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE));
                    return;
                }

                target.removeMetadata("TROLLPLUS_FREEZE", plugin);
                target.removePotionEffect(PotionEffectType.SLOW);
                VMConstants.STATUS_FREEZE = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(10, ItemBuilder.createItem(Material.ICE, 1, 0, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE));

                break;
            case 12:
                if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
                    target.setMetadata("TROLLPLUS_HAND_ITEM_DROP", new FixedMetadataValue(plugin, target.getName()));
                    VMConstants.STATUS_HAND_ITEM_DROP = "§a§lON";
                    VMConstants.TROLLMENU.setItem(12, ItemBuilder.createItem(Material.SHEARS, 1, 0, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP));
                    handItemDrop(target);
                    return;
                }

                target.removeMetadata("TROLLPLUS_HAND_ITEM_DROP", plugin);
                VMConstants.STATUS_HAND_ITEM_DROP = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(12, ItemBuilder.createItem(Material.SHEARS, 1, 0, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP));

                break;
            case 14:
                if (target == player) {
                    player.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Your cannot control yourself");
                    return;
                }

                if (!target.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
                    target.setMetadata("TROLLPLUS_CONTROL_TARGET", new FixedMetadataValue(plugin, target.getName()));
                    player.setMetadata("TROLLPLUS_CONTROL_PLAYER", new FixedMetadataValue(plugin, player.getName()));
                    VMConstants.STATUS_CONTROL = "§a§lON";
                    VMConstants.TROLLMENU.setItem(14, ItemBuilder.createItem(Material.LEAD, 1, 0, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL));
                    control(target, player);
                    return;
                }

                target.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);
                player.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
                VMConstants.STATUS_CONTROL = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(14, ItemBuilder.createItem(Material.LEAD, 1, 0, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL));

                break;
            case 16:
                if (!target.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
                    target.setMetadata("TROLLPLUS_FLIP_BEHIND", new FixedMetadataValue(plugin, target.getName()));
                    VMConstants.STATUS_FLIP_BEHIND = "§a§lON";
                    VMConstants.TROLLMENU.setItem(16, ItemBuilder.createItem(Material.COMPASS, 1, 0, ChatColor.WHITE + "Flip behind " + VMConstants.STATUS_FLIP_BEHIND));
                    return;
                }

                target.removeMetadata("TROLLPLUS_FLIP_BEHIND", plugin);
                VMConstants.STATUS_FLIP_BEHIND = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(16, ItemBuilder.createItem(Material.COMPASS, 1, 0, ChatColor.WHITE + "Flip behind " + VMConstants.STATUS_FLIP_BEHIND));

                break;
            case 20:
                if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                    target.setMetadata("TROLLPLUS_SPAM_MESSAGES", new FixedMetadataValue(plugin, target.getName()));
                    VMConstants.STATUS_SPAM_MESSAGES = "§a§lON";
                    VMConstants.TROLLMENU.setItem(20, ItemBuilder.createItem(Material.LEGACY_BOOK_AND_QUILL, 1, 0,
                            ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES));
                    spamMessages(target);
                    return;
                }

                target.removeMetadata("TROLLPLUS_SPAM_MESSAGES", plugin);
                VMConstants.STATUS_SPAM_MESSAGES = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(20, ItemBuilder.createItem(Material.LEGACY_BOOK_AND_QUILL, 1, 0,
                        ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES));

                break;
            case 22:
                if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
                    target.setMetadata("TROLLPLUS_SPAM_SOUNDS", new FixedMetadataValue(plugin, target.getName()));
                    VMConstants.STATUS_SPAM_SOUNDS = "§a§lON";
                    VMConstants.TROLLMENU.setItem(22, ItemBuilder.createItem(Material.NOTE_BLOCK, 1, 0, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS));
                    spamSounds(target);
                    return;
                }

                target.removeMetadata("TROLLPLUS_SPAM_SOUNDS", plugin);
                VMConstants.STATUS_SPAM_SOUNDS = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(22, ItemBuilder.createItem(Material.NOTE_BLOCK, 1, 0, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS));

                break;
            case 24:
                if (!target.hasMetadata("TROLLPLUS_SEMI_BAN")) {
                    target.setMetadata("TROLLPLUS_SEMI_BAN", new FixedMetadataValue(plugin, target.getName()));
                    VMConstants.STATUS_SEMI_BAN = "§a§lON";
                    VMConstants.TROLLMENU.setItem(24, ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 1, 0, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN));
                    spamSounds(target);
                    return;
                }

                target.removeMetadata("TROLLPLUS_SEMI_BAN", plugin);
                VMConstants.STATUS_SEMI_BAN = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(24, ItemBuilder.createItem(Material.TRIPWIRE_HOOK, 1, 0, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN));

                break;
            case 28:
                if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
                    target.setMetadata("TROLLPLUS_TNT_TRACK", new FixedMetadataValue(plugin, target.getName()));
                    VMConstants.STATUS_TNT_TRACK = "§a§lON";
                    VMConstants.TROLLMENU.setItem(28, ItemBuilder.createItem(Material.TNT, 1, 0, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK));
                    tntTrack(target);
                    return;
                }

                target.removeMetadata("TROLLPLUS_TNT_TRACK", plugin);
                VMConstants.STATUS_TNT_TRACK = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(28, ItemBuilder.createItem(Material.TNT, 1, 0, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK));

                break;
            case 30:
                if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
                    target.setMetadata("TROLLPLUS_MOB_SPAWNER", new FixedMetadataValue(plugin, target.getName()));
                    VMConstants.STATUS_MOB_SPAWNER = "§a§lON";
                    VMConstants.TROLLMENU.setItem(30, ItemBuilder.createItem(Material.SPAWNER, 1, 0, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER));
                    mobSpawner(target);
                    return;
                }

                target.removeMetadata("TROLLPLUS_MOB_SPAWNER", plugin);
                VMConstants.STATUS_MOB_SPAWNER = "§c§lOFF";
                VMConstants.TROLLMENU.setItem(30, ItemBuilder.createItem(Material.SPAWNER, 1, 0, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER));

                break;
            case 34:
                List<Sound> sounds = Arrays.asList(Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_CAVE, Sound.AMBIENT_CRIMSON_FOREST_MOOD, Sound.AMBIENT_NETHER_WASTES_MOOD,
                        Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_WARPED_FOREST_MOOD, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE,
                        Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);

                target.playSound(target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())), 200, 1);

                break;
            case 38:
                rocket(target, player);

                break;
            case 40:
                String fakeBanMessagePlayer = plugin.getConfig().getString(VMConstants.CONFIG_FAKE_BAN_MESSAGE_PLAYER, "");
                if (fakeBanMessagePlayer == null) {
                    fakeBanMessagePlayer = "";
                }

                if (!fakeBanMessagePlayer.isEmpty()) {
                    target.kickPlayer(fakeBanMessagePlayer);
                }

                if (plugin.getConfig().getBoolean(VMConstants.CONFIG_FAKE_BAN_MESSAGE_BROADCAST_ENABLED, true)) {
                    String fakeBanMessageBroadcast = plugin.getConfig().getString(VMConstants.CONFIG_FAKE_BAN_MESSAGE_BROADCAST, "");
                    if (fakeBanMessageBroadcast == null) {
                        fakeBanMessageBroadcast = "";
                    }

                    if (!fakeBanMessageBroadcast.isEmpty()) {
                        String fakeBanMessageBroadcastReplace = fakeBanMessageBroadcast.replace("[PLAYER]", target.getName());
                        Bukkit.broadcastMessage(fakeBanMessageBroadcastReplace);
                    }
                }

                break;
            case 42:
                if (!plugin.getConfig().getBoolean(VMConstants.CONFIG_FAKE_OP_MESSAGE_BROADCAST_ENABLED, true)) {
                    String fakeOpMessage = plugin.getConfig().getString(VMConstants.CONFIG_FAKE_OP_MESSAGE, "");
                    if (fakeOpMessage == null) {
                        fakeOpMessage = "";
                    }

                    if (!fakeOpMessage.isEmpty()) {
                        String fakeOpMessageReplace = fakeOpMessage.replace("[PLAYER]", target.getName());
                        target.sendMessage(ChatColor.GRAY + fakeOpMessageReplace);
                    }
                    return;
                }

                String fakeOpMessage = plugin.getConfig().getString(VMConstants.CONFIG_FAKE_OP_MESSAGE, "");
                if (fakeOpMessage == null) {
                    fakeOpMessage = "";
                }

                if (!fakeOpMessage.isEmpty()) {
                    String fakeOpMessageReplace = fakeOpMessage.replace("[PLAYER]", target.getName());
                    Bukkit.broadcastMessage(ChatColor.GRAY + fakeOpMessageReplace);
                }

                break;
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

                if (target.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    return;
                }

                ItemStack item = target.getInventory().getItemInHand();
                ItemStack dropItem = new ItemStack(item.getType(), 1);
                dropItem.setAmount(1);
                dropItem.setItemMeta(item.getItemMeta());
                Item itemDrop = target.getWorld().dropItemNaturally(target.getLocation(), dropItem);
                itemDrop.setPickupDelay(20);
                int amount = item.getAmount();
                amount--;
                item.setAmount(amount);
                target.getInventory().setItemInHand(item);
            }

        }.runTaskTimer(plugin, 0, 10);
    }

    // Feature control
    private void control(Player target, Player player) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            online.hidePlayer(player);
        }

        player.hidePlayer(target);

        VMConstants.CONTROL_PLAYER_LOCATION = player.getLocation();
        VMConstants.CONTROL_PLAYER_INVENTORY = player.getInventory().getContents();
        VMConstants.CONTROL_PLAYER_ARMOR = player.getInventory().getArmorContents();
        VMConstants.CONTROL_PLAYER_OFF_HAND_ITEM = player.getInventory().getItemInOffHand();

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

                    player.getInventory().setContents(VMConstants.CONTROL_PLAYER_INVENTORY);
                    player.getInventory().setArmorContents(VMConstants.CONTROL_PLAYER_ARMOR);
                    player.getInventory().setItemInOffHand(VMConstants.CONTROL_PLAYER_OFF_HAND_ITEM);
                    player.teleport(VMConstants.CONTROL_PLAYER_LOCATION);

                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        online.showPlayer(player);
                    }

                    player.showPlayer(target);

                    cancel();
                    return;
                }

                if (VMConstants.CONTROL_MESSAGE_BOOLEAN == true) {
                    target.chat(VMConstants.CONTROL_MESSAGE);
                    VMConstants.CONTROL_MESSAGE_BOOLEAN = false;
                }

                if (target.getLocation() != player.getLocation()) {
                    target.teleport(player);
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

                List<String> spamMessages = plugin.getConfig().getStringList(VMConstants.CONFIG_SPAM_MESSAGES);

                StringBuilder stringBuilderChat = new StringBuilder();
                StringBuilder stringBuilderTitle = new StringBuilder();
                StringBuilder stringBuilderTitle2 = new StringBuilder();

                for (Character character : spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(spamMessages.size())).toCharArray()) {
                    stringBuilderChat.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
                    stringBuilderChat.append(character);
                }

                for (Character character : spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(spamMessages.size())).toCharArray()) {
                    stringBuilderTitle.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
                    stringBuilderTitle.append(character);
                }

                for (Character character : spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(spamMessages.size())).toCharArray()) {
                    stringBuilderTitle2.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
                    stringBuilderTitle2.append(character);
                }

                target.sendMessage(stringBuilderChat.toString());
                target.sendTitle(stringBuilderTitle.toString(), stringBuilderTitle2.toString(), 3, 10, 3);
            }

        }.runTaskTimer(plugin, 0, 15);
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

                List<Sound> sounds = Arrays.asList(Sound.ENTITY_FOX_BITE, Sound.ENTITY_VILLAGER_YES, Sound.ENTITY_PLAYER_HURT, Sound.ENTITY_CHICKEN_DEATH,
                        Sound.ENTITY_WOLF_GROWL, Sound.BLOCK_BELL_USE, Sound.BLOCK_ANVIL_FALL, Sound.ENTITY_WITHER_DEATH, Sound.ENTITY_WOLF_DEATH, Sound.BLOCK_IRON_DOOR_CLOSE,
                        Sound.BLOCK_CHEST_OPEN, Sound.ENTITY_PIG_HURT, Sound.BLOCK_GRAVEL_BREAK, Sound.ENTITY_SHULKER_BULLET_HIT, Sound.ENTITY_ILLUSIONER_DEATH,
                        Sound.BLOCK_PORTAL_AMBIENT, Sound.BLOCK_CANDLE_BREAK, Sound.ENTITY_BAT_HURT, Sound.ITEM_AXE_WAX_OFF);

                target.playSound(target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())), RandomUtils.JVM_RANDOM.nextInt(),
                        RandomUtils.JVM_RANDOM.nextInt());
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

                Entity tnt = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
                ((TNTPrimed) tnt).setFuseTicks(100);
                tnt.setCustomName("TROLLPLUSS_TNT_TRACK_TNT");
                tnt.getWorld().playSound(target.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
            }

        }.runTaskTimer(plugin, 0, 15);
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

                List<EntityType> mobs = Arrays.asList(EntityType.DROWNED, EntityType.HUSK, EntityType.PILLAGER, EntityType.SKELETON, EntityType.SPIDER, EntityType.STRAY,
                        EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.PIGLIN_BRUTE, EntityType.SILVERFISH,
                        EntityType.VINDICATOR);

                target.getWorld().spawnEntity(target.getLocation(), mobs.get(RandomUtils.JVM_RANDOM.nextInt(mobs.size())));
            }

        }.runTaskTimer(plugin, 0, 15);
    }

    // Feature rocket
    private void rocket(Player target, Player player) {
        if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
            player.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Cannot launch because the player " + ChatColor.BOLD + target.getName() + ChatColor.RESET + ChatColor.RED + " is not under the open sky");
            return;
        }

        Boolean targetAllowToFlight = false;
        if (target.getAllowFlight() == true) {
            target.setAllowFlight(false);
            target.setAllowFlight(true);
            targetAllowToFlight = true;
        } else {
            target.setAllowFlight(true);
        }

        target.setMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", new FixedMetadataValue(plugin, target.getName()));

        target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, target.getLocation(), 1);
        Particle[] particles = new Particle[]{
                Particle.FIREWORKS_SPARK, Particle.LAVA, Particle.FLAME
        };

        for (Particle particle : particles) {
            target.getWorld().spawnParticle(particle, target.getLocation(), 25);
        }

        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);

        Boolean finalTargetAllowToFlight = targetAllowToFlight;
        new BukkitRunnable() {

            int rocket = 0;

            @Override
            public void run() {
                if (rocket < 50) {
                    if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                        player.sendMessage(VMConstants.PLUGIN_PREFIX + ChatColor.RED + "Launch stopped because the player " + ChatColor.BOLD + target.getName() + ChatColor.RESET + ChatColor.RED + " is no longer under the open sky");
                        if (!finalTargetAllowToFlight) {
                            target.setAllowFlight(false);
                        }
                        rocket = 0;
                        cancel();
                        return;
                    }

                    target.setVelocity(target.getVelocity().setY(20));
                    rocket++;
                } else {
                    if (!finalTargetAllowToFlight) {
                        target.setAllowFlight(false);
                    }
                    rocket = 0;

                }
            }

        }.runTaskTimer(plugin, 0, 5);
    }

}