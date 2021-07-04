package com.github.gaming12846.trollplus.features;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus.features MobSpawnerFeature.java
 *
 * @author Gaming12846
 */
public class MobSpawnerFeature {

	public static void mobSpawner(Player target) {
		new BukkitRunnable() {
			@Override
			public void run() {

				if (Vars.Lists.mobSpawnerList.contains(target.getName())) {
					List<EntityType> entitys = Arrays.asList(EntityType.DROWNED, EntityType.HUSK, EntityType.PILLAGER, EntityType.SKELETON, EntityType.SPIDER,
							EntityType.STRAY, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER,
							EntityType.PIGLIN_BRUTE, EntityType.SILVERFISH, EntityType.VINDICATOR);

					target.getWorld().spawnEntity(target.getLocation(), entitys.get(RandomUtils.JVM_RANDOM.nextInt(entitys.size())));
				} else
					cancel();
			}
		}.runTaskTimer(TrollPlus.getPlugin(), 0, 15);
	}
}
