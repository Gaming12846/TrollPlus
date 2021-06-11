/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import de.gaming12846.troll.main.Main;
import de.gaming12846.troll.utilitys.Vars;

public class SpamSounds {

	public static void SpamSounds() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {

				if (Vars.spamsoundslist.contains(Vars.target.getName())) {
					List<Sound> sounds = new ArrayList<>();
					sounds.add(Sound.ENTITY_EXPERIENCE_BOTTLE_THROW);
					sounds.add(Sound.ENTITY_PLAYER_HURT);
					sounds.add(Sound.ENTITY_CHICKEN_DEATH);
					sounds.add(Sound.ENTITY_WOLF_GROWL);
					sounds.add(Sound.BLOCK_BELL_USE);
					sounds.add(Sound.BLOCK_ANVIL_FALL);
					sounds.add(Sound.ENTITY_WITHER_DEATH);
					sounds.add(Sound.ENTITY_WOLF_DEATH);
					sounds.add(Sound.BLOCK_IRON_DOOR_CLOSE);
					sounds.add(Sound.BLOCK_CHEST_OPEN);
					sounds.add(Sound.ENTITY_PIG_HURT);
					sounds.add(Sound.BLOCK_GRAVEL_BREAK);
					sounds.add(Sound.ENTITY_IRON_GOLEM_ATTACK);
					sounds.add(Sound.ENTITY_FOX_BITE);
					sounds.add(Sound.ENTITY_SKELETON_DEATH);
					sounds.add(Sound.ENTITY_VILLAGER_YES);
					sounds.add(Sound.ENTITY_WITCH_DRINK);
					sounds.add(Sound.BLOCK_PORTAL_AMBIENT);
					sounds.add(Sound.ENTITY_ILLUSIONER_DEATH);
					sounds.add(Sound.ENTITY_SHULKER_BULLET_HIT);

					Random random = new Random();
					Sound sound = sounds.get(random.nextInt(sounds.size()));
					int rand = 0;
					while (true) {
						rand = random.nextInt(11);
						if (rand != 0)
							break;
					}
					Vars.target.playSound(Vars.target.getLocation(), sound, 30, rand);

				}

			}

		}, 5, 5);

	}

}
