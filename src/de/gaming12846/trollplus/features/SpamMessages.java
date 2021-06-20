/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class SpamMessages {

	public static void SpamMessages() {

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {

				if (Vars.Lists.spammessagesList.contains(Vars.target.getName())) {
					Random random = new Random();
					String randomElement = Vars.Lists.spammessages.get(random.nextInt(Vars.Lists.spammessages.size()));
					StringBuilder sb1 = new StringBuilder();
					for (Character character : randomElement.toCharArray()) {
						sb1.append(ChatColor.getByChar(Integer.toHexString(random.nextInt(16))));
						sb1.append(character);
					}
					Random random2 = new Random();
					String randomElement2 = Vars.Lists.spammessages.get(random2.nextInt(Vars.Lists.spammessages.size()));
					StringBuilder sb2 = new StringBuilder();
					for (Character character : randomElement2.toCharArray()) {
						sb2.append(ChatColor.getByChar(Integer.toHexString(random2.nextInt(16))));
						sb2.append(character);
					}
					Random random3 = new Random();
					String randomElement3 = Vars.Lists.spammessages.get(random3.nextInt(Vars.Lists.spammessages.size()));
					StringBuilder sb3 = new StringBuilder();
					for (Character character : randomElement3.toCharArray()) {
						sb3.append(ChatColor.getByChar(Integer.toHexString(random3.nextInt(16))));
						sb3.append(character);
					}
					Vars.target.sendTitle(sb1.toString(), sb2.toString(), 0, 20, 0);
					Vars.target.sendMessage(sb3.toString());
				}
			}
		}, 20, 20);
	}
}
