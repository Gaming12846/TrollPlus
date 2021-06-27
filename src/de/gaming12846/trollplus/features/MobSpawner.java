/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class MobSpawner {

	public static void MobSpawner() {

		new BukkitRunnable() {

			@Override
			public void run() {

				if (Vars.Lists.mobSpawnerList.contains(Vars.target.getName())) {

					List<EntityType> entitys = Arrays.asList(EntityType.DROWNED, EntityType.HUSK, EntityType.PILLAGER, EntityType.SKELETON,
							EntityType.SPIDER, EntityType.STRAY, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOMBIE,
							EntityType.ZOMBIE_VILLAGER, EntityType.PIGLIN_BRUTE, EntityType.SILVERFISH, EntityType.VINDICATOR);

					Vars.target.getWorld().spawnEntity(Vars.target.getLocation(), entitys.get(RandomUtils.JVM_RANDOM.nextInt(entitys.size())));

				} else
					cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 15, 15);
	}
}
