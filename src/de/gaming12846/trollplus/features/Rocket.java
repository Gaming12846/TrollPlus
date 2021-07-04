/**
 * TrollPlus
 * 
 * @author Gaming12846
 */

package de.gaming12846.trollplus.features;

import java.util.ArrayList;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.gaming12846.trollplus.main.Main;
import de.gaming12846.trollplus.utilitys.Vars;

public class Rocket implements Listener {
	static ArrayList<Player> noFallDamage = new ArrayList<Player>();
	static int i = 0;

	public static void rocket(Player target) {
		Vars.target.setAllowFlight(true);
		noFallDamage.add(Vars.target);

		Vars.target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, Vars.target.getLocation(), 1);
		Particle[] particles = new Particle[] { Particle.FIREWORKS_SPARK, Particle.LAVA, Particle.FLAME, };
		for (Particle particle : particles) {
			Vars.target.getWorld().spawnParticle(particle, Vars.target.getLocation(), 25);
		}
		Vars.target.getWorld().playSound(Vars.target.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 20, 1);

		new BukkitRunnable() {

			@Override
			public void run() {

				if (i < 50) {
					Vars.target.setVelocity(Vars.target.getVelocity().setY(20));

					i++;
				} else {
					Vars.target.setAllowFlight(false);
					i = 0;

					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 5);
	}

	@EventHandler
	public void onEntityDamag(EntityDamageEvent e) {
		Entity entity = e.getEntity();

		if (entity instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
			Player p = (Player) entity;

			if (noFallDamage.contains(p)) {
				e.setCancelled(true);
				noFallDamage.remove(p);
			}
		}
	}
}
