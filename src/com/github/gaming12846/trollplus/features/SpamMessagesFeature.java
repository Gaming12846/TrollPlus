package com.github.gaming12846.trollplus.features;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.gaming12846.trollplus.TrollPlus;
import com.github.gaming12846.trollplus.utils.Vars;

/**
 * TrollPlus com.github.gaming12846.trollplus.features SpamMessagesFeature.java
 *
 * @author Gaming12846
 */
public class SpamMessagesFeature {

	public static void spamMessages(Player target) {
		new BukkitRunnable() {
			@Override
			public void run() {

				if (Vars.Lists.spamMessagesList.contains(target.getName())) {
					StringBuilder stringBuilder1 = new StringBuilder();
					for (Character character : Vars.Lists.spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(Vars.Lists.spamMessages.size())).toCharArray()) {
						stringBuilder1.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
						stringBuilder1.append(character);
					}

					StringBuilder stringBuilder2 = new StringBuilder();
					for (Character character : Vars.Lists.spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(Vars.Lists.spamMessages.size())).toCharArray()) {
						stringBuilder2.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
						stringBuilder2.append(character);
					}

					StringBuilder stringBuilder3 = new StringBuilder();
					for (Character character : Vars.Lists.spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(Vars.Lists.spamMessages.size())).toCharArray()) {
						stringBuilder3.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
						stringBuilder3.append(character);
					}

					target.sendTitle(stringBuilder1.toString(), stringBuilder2.toString(), 3, 10, 3);
					target.sendMessage(stringBuilder3.toString());
				} else
					cancel();
			}
		}.runTaskTimer(TrollPlus.getPlugin(), 0, 15);
	}
}