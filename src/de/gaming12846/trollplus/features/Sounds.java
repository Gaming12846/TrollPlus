/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class Sounds {

	public static void SpamSounds() {

		new BukkitRunnable() {

			@Override
			public void run() {

				if (Vars.Lists.spamSoundsList.contains(Vars.target.getName())) {

					List<Sound> sounds = Arrays.asList(Sound.ENTITY_FOX_BITE, Sound.ENTITY_VILLAGER_YES, Sound.ENTITY_PLAYER_HURT,
							Sound.ENTITY_CHICKEN_DEATH, Sound.ENTITY_WOLF_GROWL, Sound.BLOCK_BELL_USE, Sound.BLOCK_ANVIL_FALL,
							Sound.ENTITY_WITHER_DEATH, Sound.ENTITY_WOLF_DEATH, Sound.BLOCK_IRON_DOOR_CLOSE, Sound.BLOCK_CHEST_OPEN,
							Sound.ENTITY_PIG_HURT, Sound.BLOCK_GRAVEL_BREAK, Sound.ENTITY_SHULKER_BULLET_HIT, Sound.ENTITY_ILLUSIONER_DEATH,
							Sound.BLOCK_PORTAL_AMBIENT, Sound.BLOCK_CANDLE_BREAK, Sound.ENTITY_BAT_HURT, Sound.ITEM_AXE_WAX_OFF);
					Vars.target.playSound(Vars.target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())),
							RandomUtils.JVM_RANDOM.nextInt(), RandomUtils.JVM_RANDOM.nextInt());
				} else
					cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 5, 5);
	}

	public static void RandomScarySound() {

		List<Sound> sounds = Arrays.asList(Sound.AMBIENT_BASALT_DELTAS_MOOD, Sound.AMBIENT_CAVE, Sound.AMBIENT_CRIMSON_FOREST_MOOD,
				Sound.AMBIENT_NETHER_WASTES_MOOD, Sound.AMBIENT_SOUL_SAND_VALLEY_MOOD, Sound.AMBIENT_WARPED_FOREST_MOOD,
				Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE);
		Vars.target.playSound(Vars.target.getLocation(), sounds.get(RandomUtils.JVM_RANDOM.nextInt(sounds.size())), 200, 1);
	}
}
