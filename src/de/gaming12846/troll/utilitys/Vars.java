/**
 * Troll
 * 
 * @author Gaming12846
 */

package de.gaming12846.troll.utilitys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.gaming12846.troll.main.Main;

public class Vars {
	public static Player target;
	public static Inventory trollmenu;

	// Plugin initialisation
	public static String prefix = "§f[§dTroll§f] ";

	// Messages
	public static String nopermission = prefix + Main.getPlugin().getConfig().getString("message.nopermission");
	public static String noconsole = "[Troll] " + Main.getPlugin().getConfig().getString("message.noconsole");

	public static String usagetrollmenu = prefix + Main.getPlugin().getConfig().getString("message.usagetrollmenu");
	public static String targetnotonline = prefix + Main.getPlugin().getConfig().getString("message.targetnotonline");

	public static String banmessageplayer = Main.getPlugin().getConfig().getString("banmessage.banmessageplayer");
	public static String banmessagebroadcast = Main.getPlugin().getConfig().getString("banmessage.banmessagebroadcast");

	// Booleans
	public static boolean bankmessagebroadcastonoff = Main.getPlugin().getConfig()
			.getBoolean("banmessage.bankmessagebroadcastonoff");

	// ArrayLists
	public static ArrayList<String> vanishlist = new ArrayList<>();
	public static ArrayList<String> freezelist = new ArrayList<>();
	public static ArrayList<String> handitemdroplist = new ArrayList<>();
	public static ArrayList<String> controllist = new ArrayList<>();
	public static ArrayList<String> spammessageslist = new ArrayList<>();
	public static ArrayList<String> spamsoundslist = new ArrayList<>();
	public static ArrayList<String> flipbehindlist = new ArrayList<>();

	// Lists
	public static List<String> spammessages = Main.getPlugin().getConfig().getStringList("spammessages");

	// HashMaps
	public static HashMap<Player, Player> controller = new HashMap<>();

	// Status
	public static String vanishstatus = "§cOFF";
	public static String freezestatus = "§cOFF";
	public static String handitemdropstatus = "§cOFF";
	public static String controlstatus = "§cOFF";
	public static String spammessagesstatus = "§cOFF";
	public static String spamsoundsstatus = "§cOFF";
	public static String flipbehindstatus = "§cOFF";
}
