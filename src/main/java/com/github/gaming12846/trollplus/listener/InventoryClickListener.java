package com.github.gaming12846.trollplus.listener;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.ItemBuilder;
import com.github.gaming12846.trollplus.utils.VMConstants;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.*;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

        // Feature freeze
        if (player.hasMetadata("TROLLPLUS_FREEZE")) {
            event.setCancelled(true);
        }

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            event.setCancelled(true);
        }

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            event.setCancelled(true);
        }

        Player target = VMConstants.TARGET;

        if (target != null && event.getView().getTitle().equals("Troll menu " + ChatColor.GOLD + ChatColor.BOLD + target.getName())) {
            event.setCancelled(true);

            switch (event.getSlot()) {
                default:
                    return;

                // Features
                case 53:
                    player.closeInventory();

                    break;
                case 51:
                    player.openInventory(Objects.requireNonNull(target.getPlayer()).getInventory());

                    break;
                case 50:
                    target.setHealth(0.0);

                    break;
                case 48:
                    if (target == player) {
                        player.sendMessage(VMConstants.PLUGIN_PREFIX + "Nice try, but you're already in your own position :)");
                        return;
                    }

                    player.teleport(target);

                    break;
                case 47:
                    if (!target.hasMetadata("TROLLPLUS_VANISH")) {
                        target.setMetadata("TROLLPLUS_VANISH", new FixedMetadataValue(plugin, target.getName()));
                        target.hidePlayer(player);
                        VMConstants.STATUS_VANISH = "§a§lTarget";
                        VMConstants.TROLL_MENU.setItem(47, ItemBuilder.createItemWithLore(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH, Collections.singletonList("Disappear for the target or for all players")));
                        return;
                    }

                    if (Objects.equals(VMConstants.STATUS_VANISH, "§a§lTarget")) {
                        target.showPlayer(player);
                        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                            online.hidePlayer(player);
                        }
                        VMConstants.STATUS_VANISH = "§b§lAll";
                        VMConstants.TROLL_MENU.setItem(47, ItemBuilder.createItemWithLore(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH, Collections.singletonList("Disappear for the target or for all players")));
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_VANISH", plugin);
                    for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                        online.showPlayer(player);
                    }
                    VMConstants.STATUS_VANISH = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(47, ItemBuilder.createItemWithLore(Material.POTION, 1, 0, ChatColor.WHITE + "Vanish " + VMConstants.STATUS_VANISH, Collections.singletonList("Disappear for the target or for all players")));

                    break;
                case 10:
                    if (!target.hasMetadata("TROLLPLUS_FREEZE")) {
                        target.setMetadata("TROLLPLUS_FREEZE", new FixedMetadataValue(plugin, target.getName()));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 6));
                        VMConstants.STATUS_FREEZE = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(10, ItemBuilder.createItemWithLore(Material.ICE, 1, 0, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE, Collections.singletonList("Freeze the target")));
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_FREEZE", plugin);
                    target.removePotionEffect(PotionEffectType.SLOW);
                    VMConstants.STATUS_FREEZE = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(10, ItemBuilder.createItemWithLore(Material.ICE, 1, 0, ChatColor.WHITE + "Freeze " + VMConstants.STATUS_FREEZE, Collections.singletonList("Freeze the target")));

                    break;
                case 12:
                    if (!target.hasMetadata("TROLLPLUS_HAND_ITEM_DROP")) {
                        target.setMetadata("TROLLPLUS_HAND_ITEM_DROP", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_HAND_ITEM_DROP = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(12,
                                ItemBuilder.createItemWithLore(Material.SHEARS, 1, 0, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP, Collections.singletonList("Automatic dropping of the hand item from the target")));
                        handItemDrop(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_HAND_ITEM_DROP", plugin);
                    VMConstants.STATUS_HAND_ITEM_DROP = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(12,
                            ItemBuilder.createItemWithLore(Material.SHEARS, 1, 0, ChatColor.WHITE + "Hand item drop " + VMConstants.STATUS_HAND_ITEM_DROP, Collections.singletonList("Automatic dropping of the hand item from the target")));

                    break;
                case 14:
                    if (target == player) {
                        player.sendMessage(VMConstants.PLUGIN_PREFIX + "Nice try, but you're already in control :)");
                        return;
                    }

                    if (!target.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
                        target.setMetadata("TROLLPLUS_CONTROL_TARGET", new FixedMetadataValue(plugin, target.getName()));
                        player.setMetadata("TROLLPLUS_CONTROL_PLAYER", new FixedMetadataValue(plugin, player.getName()));
                        VMConstants.STATUS_CONTROL = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(14, ItemBuilder.createItemWithLore(Material.LEAD, 1, 0, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL, Collections.singletonList("Completely control the target")));
                        control(target, player);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_CONTROL_TARGET", plugin);
                    player.removeMetadata("TROLLPLUS_CONTROL_PLAYER", plugin);
                    VMConstants.STATUS_CONTROL = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(14, ItemBuilder.createItemWithLore(Material.LEAD, 1, 0, ChatColor.WHITE + "Control " + VMConstants.STATUS_CONTROL, Collections.singletonList("Completely control the target")));

                    break;
                case 16:
                    if (!target.hasMetadata("TROLLPLUS_FLIP_BEHIND")) {
                        target.setMetadata("TROLLPLUS_FLIP_BEHIND", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_FLIP_BEHIND = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(16,
                                ItemBuilder.createItemWithLore(Material.COMPASS, 1, 0, ChatColor.WHITE + "Flip backwards " + VMConstants.STATUS_FLIP_BEHIND, Collections.singletonList("Flip the target backwards when interacting with something")));
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_FLIP_BEHIND", plugin);
                    VMConstants.STATUS_FLIP_BEHIND = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(16,
                            ItemBuilder.createItemWithLore(Material.COMPASS, 1, 0, ChatColor.WHITE + "Flip backwards " + VMConstants.STATUS_FLIP_BEHIND, Collections.singletonList("Flip the target backwards when interacting with something")));

                    break;
                case 20:
                    if (!target.hasMetadata("TROLLPLUS_SPAM_MESSAGES")) {
                        target.setMetadata("TROLLPLUS_SPAM_MESSAGES", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_SPAM_MESSAGES = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, 1, 0,
                                ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES, Collections.singletonList("Spam the target with random custom messages")));
                        spamMessages(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SPAM_MESSAGES", plugin);
                    VMConstants.STATUS_SPAM_MESSAGES = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, 1, 0,
                            ChatColor.WHITE + "Spam messages " + VMConstants.STATUS_SPAM_MESSAGES, Collections.singletonList("Spam the target with random custom messages")));

                    break;
                case 22:
                    if (!target.hasMetadata("TROLLPLUS_SPAM_SOUNDS")) {
                        target.setMetadata("TROLLPLUS_SPAM_SOUNDS", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_SPAM_SOUNDS = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(22,
                                ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, 1, 0, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS, Collections.singletonList("Spam the target with random sounds")));
                        spamSounds(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SPAM_SOUNDS", plugin);
                    VMConstants.STATUS_SPAM_SOUNDS = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(22,
                            ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, 1, 0, ChatColor.WHITE + "Spam sounds " + VMConstants.STATUS_SPAM_SOUNDS, Collections.singletonList("Spam the target with random sounds")));

                    break;
                case 24:
                    if (!target.hasMetadata("TROLLPLUS_SEMI_BAN")) {
                        target.setMetadata("TROLLPLUS_SEMI_BAN", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_SEMI_BAN = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(24,
                                ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, 1, 0, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN, Collections.singletonList("Prevents the target from building, interacting, causing damage and writing")));
                        spamSounds(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SEMI_BAN", plugin);
                    VMConstants.STATUS_SEMI_BAN = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(24,
                            ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, 1, 0, ChatColor.WHITE + "Semi ban " + VMConstants.STATUS_SEMI_BAN, Collections.singletonList("Prevents the target from building, interacting, causing damage and writing")));

                    break;
                case 28:
                    if (!target.hasMetadata("TROLLPLUS_TNT_TRACK")) {
                        target.setMetadata("TROLLPLUS_TNT_TRACK", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_TNT_TRACK = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(28,
                                ItemBuilder.createItemWithLore(Material.TNT, 1, 0, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK, Collections.singletonList("Spawn primed TNT at the target")));
                        tntTrack(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_TNT_TRACK", plugin);
                    VMConstants.STATUS_TNT_TRACK = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(28,
                            ItemBuilder.createItemWithLore(Material.TNT, 1, 0, ChatColor.WHITE + "TNT track " + VMConstants.STATUS_TNT_TRACK, Collections.singletonList("Spawn primed TNT at the target")));

                    break;
                case 30:
                    if (!target.hasMetadata("TROLLPLUS_MOB_SPAWNER")) {
                        target.setMetadata("TROLLPLUS_MOB_SPAWNER", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_MOB_SPAWNER = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(30,
                                ItemBuilder.createItemWithLore(Material.SPAWNER, 1, 0, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER, Collections.singletonList("Spawn random mobs at the target")));
                        mobSpawner(target);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_MOB_SPAWNER", plugin);
                    VMConstants.STATUS_MOB_SPAWNER = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(30,
                            ItemBuilder.createItemWithLore(Material.SPAWNER, 1, 0, ChatColor.WHITE + "Mob spawner " + VMConstants.STATUS_MOB_SPAWNER, Collections.singletonList("Spawn random mobs at the target")));

                    break;
                case 32:
                    if (!target.hasMetadata("TROLLPLUS_SLOWLY_KILL")) {
                        target.setMetadata("TROLLPLUS_SLOWLY_KILL", new FixedMetadataValue(plugin, target.getName()));
                        VMConstants.STATUS_SLOWLY_KILL = "§a§lON";
                        VMConstants.TROLL_MENU.setItem(32,
                                ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, 1, 0, ChatColor.WHITE + "Slowly kill " + VMConstants.STATUS_SLOWLY_KILL, Collections.singletonList("Slowly kills the target")));
                        slowlyKill(target, player);
                        return;
                    }

                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    VMConstants.STATUS_SLOWLY_KILL = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(32,
                            ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, 1, 0, ChatColor.WHITE + "Slowly kill " + VMConstants.STATUS_SLOWLY_KILL, Collections.singletonList("Slowly kills the target")));

                    break;
                case 34:
                    randomScarySound(target);

                    break;
                case 38:
                    rocket(target, player);

                    break;
                case 40:
                    fakeBan(target);

                    break;
                case 42:
                    fakeOp(target);

                    break;
            }

        } else if (event.getView().getTitle().equals("Bows menu")) {
            event.setCancelled(true);

            switch (event.getSlot()) {
                default:
                    return;

                // Features
                case 8:
                    player.closeInventory();

                    break;
                case 0:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(new ItemStack(Material.ARROW))) {
                        player.getInventory().addItem(ItemBuilder.createBow(1, 0, "Explosion bow", Collections.singletonList("Create an explosion on hit")));
                        player.getInventory().addItem(new ItemStack(Material.ARROW));
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(1, 0, "Explosion bow", Collections.singletonList("Create an explosion on hit")));

                    break;
                case 1:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(new ItemStack(Material.ARROW))) {
                        player.getInventory().addItem(ItemBuilder.createBow(1, 0, "TNT bow", Collections.singletonList("Create an primed TNT on hit")));
                        player.getInventory().addItem(new ItemStack(Material.ARROW));
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(1, 0, "TNT bow", Collections.singletonList("Create an primed TNT on hit")));

                    break;
                case 2:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(new ItemStack(Material.ARROW))) {
                        player.getInventory().addItem(ItemBuilder.createBow(1, 0, "Lightning bolt bow", Collections.singletonList("Create an lightning bolt on hit")));
                        player.getInventory().addItem(new ItemStack(Material.ARROW));
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(1, 0, "Lightning bolt bow", Collections.singletonList("Create an lightning bolt on hit")));

                    break;
                case 3:
                    if (player.getGameMode() != GameMode.CREATIVE && !player.getInventory().contains(new ItemStack(Material.ARROW))) {
                        player.getInventory().addItem(ItemBuilder.createBow(1, 0, "Silverfish bow", Collections.singletonList("Spawn silverfishes on hit")));
                        player.getInventory().addItem(new ItemStack(Material.ARROW));
                        return;
                    }
                    player.getInventory().addItem(ItemBuilder.createBow(1, 0, "Silverfish bow", Collections.singletonList("Spawn silverfishes on hit")));

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

                if (target.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    return;
                }

                ItemStack item = target.getInventory().getItemInHand();
                ItemStack dropItem = new ItemStack(item.getType(), 1);
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

                if (VMConstants.CONTROL_MESSAGE_BOOLEAN) {
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

                TNTPrimed tnt = target.getWorld().spawn(target.getLocation(), TNTPrimed.class);
                tnt.setFuseTicks(80);
                tnt.setMetadata("TROLLPLUS_TNT", new FixedMetadataValue(plugin, tnt));
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
                    player.sendMessage(VMConstants.PLUGIN_PREFIX + "Cannot slowly kill because the target " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " is not in survival or adventure mode.");
                    target.removeMetadata("TROLLPLUS_SLOWLY_KILL", plugin);
                    VMConstants.STATUS_SLOWLY_KILL = "§c§lOFF";
                    VMConstants.TROLL_MENU.setItem(32,
                            ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, 1, 0, ChatColor.WHITE + "Slowly kill " + VMConstants.STATUS_SLOWLY_KILL, Collections.singletonList("Slowly kills the target")));
                    cancel();
                    return;
                }

                target.damage(1);
            }

        }.runTaskTimer(plugin, 0, 80);
    }

    // Feature random scary sound
    private void randomScarySound(Player target) {
        List<Sound> sounds = Arrays.asList(Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_CAVE, Sound.AMBIENT_CRIMSON_FOREST_MOOD, Sound.AMBIENT_NETHER_WASTES_MOOD,
                Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_WARPED_FOREST_MOOD, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE,
                Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);

        target.playSound(target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())), 200, 1);
    }

    // Feature rocket
    private void rocket(Player target, Player player) {
        if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
            player.sendMessage(VMConstants.PLUGIN_PREFIX + "Cannot launch because the target " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " is not under the open sky.");
            target.removeMetadata("TROLLPLUS_ROCKET_NO_FALL_DAMAGE", plugin);
            return;
        }

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
        Particle[] particles = new Particle[]{
                Particle.FIREWORKS_SPARK, Particle.LAVA, Particle.FLAME
        };
        for (Particle particle : particles) {
            target.getWorld().spawnParticle(particle, target.getLocation(), 25);
        }

        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);

        Boolean finalTargetAllowToFlight = targetAllowedToFlight;
        new BukkitRunnable() {

            int rocket = 0;

            @Override
            public void run() {
                if (rocket < 22) {
                    if (target.getLocation().getBlockY() < target.getWorld().getHighestBlockYAt(target.getLocation())) {
                        if (!finalTargetAllowToFlight) {
                            target.setAllowFlight(false);
                        }
                        player.sendMessage(VMConstants.PLUGIN_PREFIX + "Launch stopped because the target " + ChatColor.BOLD + target.getName() + ChatColor.RESET + " is no longer under the open sky.");

                        cancel();
                        rocket = 0;
                        return;
                    }

                    target.setVelocity(target.getVelocity().setY(20));
                    rocket++;
                } else {
                    if (!finalTargetAllowToFlight) {
                        target.setAllowFlight(false);
                    }

                    rocket = 0;
                    cancel();
                }
            }

        }.runTaskTimer(plugin, 0, 6);
    }

    // Feature fake ban
    private void fakeBan(Player target) {
        String fakeBanMessagePlayer = plugin.getConfig().getString(VMConstants.CONFIG_FAKE_BAN_MESSAGE_PLAYER, "");

        if (!fakeBanMessagePlayer.isEmpty()) {
            target.kickPlayer(fakeBanMessagePlayer);
        }

        if (plugin.getConfig().getBoolean(VMConstants.CONFIG_FAKE_BAN_MESSAGE_BROADCAST_ENABLED, true)) {
            String fakeBanMessageBroadcast = plugin.getConfig().getString(VMConstants.CONFIG_FAKE_BAN_MESSAGE_BROADCAST, "");

            if (!fakeBanMessageBroadcast.isEmpty()) {
                String fakeBanMessageBroadcastReplace = fakeBanMessageBroadcast.replace("[PLAYER]", target.getName());
                Bukkit.broadcastMessage(fakeBanMessageBroadcastReplace);
            }
        }
    }

    // Feature fake op
    private void fakeOp(Player target) {
        String fakeOpMessage = plugin.getConfig().getString(VMConstants.CONFIG_FAKE_OP_MESSAGE, "");

        if (!plugin.getConfig().getBoolean(VMConstants.CONFIG_FAKE_OP_MESSAGE_BROADCAST_ENABLED, true)) {

            if (!fakeOpMessage.isEmpty()) {
                String fakeOpMessageReplace = fakeOpMessage.replace("[PLAYER]", target.getName());
                target.sendMessage(ChatColor.GRAY + fakeOpMessageReplace);
            }
            return;
        }

        if (!fakeOpMessage.isEmpty()) {
            String fakeOpMessageReplace = fakeOpMessage.replace("[PLAYER]", target.getName());
            Bukkit.broadcastMessage(ChatColor.GRAY + fakeOpMessageReplace);
        }
    }

}