/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class SpamMessages {

	public static void SpamMessages() {

		if (Vars.Lists.spamMessagesList.contains(Vars.target.getName())) {

			new BukkitRunnable() {

				@Override
				public void run() {

					if (Vars.Lists.spamMessagesList.contains(Vars.target.getName())) {

						StringBuilder stringBuilder1 = new StringBuilder();
						for (Character character : Vars.Lists.spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(Vars.Lists.spamMessages.size()))
								.toCharArray()) {
							stringBuilder1.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
							stringBuilder1.append(character);
						}
						StringBuilder stringBuilder2 = new StringBuilder();
						for (Character character : Vars.Lists.spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(Vars.Lists.spamMessages.size()))
								.toCharArray()) {
							stringBuilder2.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
							stringBuilder2.append(character);
						}
						StringBuilder stringBuilder3 = new StringBuilder();
						for (Character character : Vars.Lists.spamMessages.get(RandomUtils.JVM_RANDOM.nextInt(Vars.Lists.spamMessages.size()))
								.toCharArray()) {
							stringBuilder3.append(ChatColor.getByChar(Integer.toHexString(RandomUtils.JVM_RANDOM.nextInt(16))));
							stringBuilder3.append(character);
						}
						Vars.target.sendTitle(stringBuilder1.toString(), stringBuilder2.toString(), 3, 10, 3);
						Vars.target.sendMessage(stringBuilder3.toString());
					} else
						cancel();
				}
			}.runTaskTimer(Main.getPlugin(), 10, 10);
		}
	}
}
