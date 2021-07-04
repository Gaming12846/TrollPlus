package com.github.gaming12846.trollplus.features;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus.features SoundsFeature.java
 *
 * @author Gaming12846
 */
public class SoundsFeature {

	public static void spamSounds(Player target) {
		new BukkitRunnable() {
			@Override
			public void run() {

				if (Vars.Lists.spamSoundsList.contains(target.getName())) {
					List<Sound> sounds = Arrays.asList(Sound.ENTITY_FOX_BITE, Sound.ENTITY_VILLAGER_YES, Sound.ENTITY_PLAYER_HURT, Sound.ENTITY_CHICKEN_DEATH,
							Sound.ENTITY_WOLF_GROWL, Sound.BLOCK_BELL_USE, Sound.BLOCK_ANVIL_FALL, Sound.ENTITY_WITHER_DEATH, Sound.ENTITY_WOLF_DEATH,
							Sound.BLOCK_IRON_DOOR_CLOSE, Sound.BLOCK_CHEST_OPEN, Sound.ENTITY_PIG_HURT, Sound.BLOCK_GRAVEL_BREAK,
							Sound.ENTITY_SHULKER_BULLET_HIT, Sound.ENTITY_ILLUSIONER_DEATH, Sound.BLOCK_PORTAL_AMBIENT, Sound.BLOCK_CANDLE_BREAK,
							Sound.ENTITY_BAT_HURT, Sound.ITEM_AXE_WAX_OFF);

					target.playSound(target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())), RandomUtils.JVM_RANDOM.nextInt(),
							RandomUtils.JVM_RANDOM.nextInt());
				} else
					cancel();
			}
		}.runTaskTimer(TrollPlus.getPlugin(), 0, 5);
	}

	public static void randomScarySound(Player target) {
		List<Sound> sounds = Arrays.asList(Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_CAVE, Sound.AMBIENT_CRIMSON_FOREST_MOOD,
				Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_WARPED_FOREST_MOOD,
				Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);

		target.playSound(target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())), 200, 1);
	}
}
