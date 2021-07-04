package com.github.gaming12846.trollplus.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.github.gaming12846.trollplus.TrollPlus;

/**
 * TrollPlus com.github.gaming12846.trollplus.utils Vars.java
 *
 * @author Gaming12846
 */
public class Vars {
	public static String prefix = "§8[§c§lTrollPlus§8]§f ";
	public static String consolePrefix = "[TrollPlus] ";

	public static FileConfiguration blacklist = null;

	public static Player target = null;
	public static Player executer = null;
	public static Inventory trollmenu = null;

	// Status
	public static class Status {
		public static String vanishStatus = "§c§lOFF";
		public static String freezeStatus = "§c§lOFF";
		public static String handItemDropStatus = "§c§lOFF";
		public static String controlStatus = "§c§lOFF";
		public static String spamMessagesStatus = "§c§lOFF";
		public static String spamSoundsStatus = "§c§lOFF";
		public static String flipBehindStatus = "§c§lOFF";
		public static String semiBanStatus = "§c§lOFF";
		public static String tntTrackStatus = "§c§lOFF";
		public static String mobSpawnerStatus = "§c§lOFF";
	}

	// ArrayLists and Lists
	public static class Lists {
		public static ArrayList<String> vanishList = new ArrayList<>();
		public static ArrayList<String> freezeList = new ArrayList<>();
		public static ArrayList<String> handItemDropList = new ArrayList<>();
		public static HashMap<Player, Player> controlList = new HashMap<>();
		public static ArrayList<String> spamMessagesList = new ArrayList<>();
		public static ArrayList<String> spamSoundsList = new ArrayList<>();
		public static ArrayList<String> flipBehindList = new ArrayList<>();
		public static ArrayList<String> semiBanList = new ArrayList<>();
		public static ArrayList<String> tntTrackList = new ArrayList<>();
		public static ArrayList<String> mobSpawnerList = new ArrayList<>();

		public static List<String> spamMessages = TrollPlus.getPlugin().getConfig().getStringList("SpamMessages");
	}

	// Booleans
	public static class Booleans {
		public static boolean checkForUpdates = TrollPlus.getPlugin().getConfig().getBoolean("CheckForUpdates");
		public static boolean metricsEnabled = TrollPlus.getPlugin().getConfig().getBoolean("MetricsEnabled");
		public static boolean banMessageBroadcastSwitch = TrollPlus.getPlugin().getConfig().getBoolean("Features.BanMessageBroadcastSwitch");
		public static boolean fakeOpMessageBroadcastSwitch = TrollPlus.getPlugin().getConfig().getBoolean("Features.FakeOpMessageBroadcastSwitch");
	}

	// Messages
	public static class Messages {
		public static String noPermission = TrollPlus.getPlugin().getConfig().getString("Messages.NoPermission");
		public static String noConsole = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.NoConsole");

		public static String usageTrollmenu = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.UsageTrollmenu");
		public static String usageBlacklist = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.UsageBlacklist");;

		public static String targetNotOnline = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.TargetNotOnline");
		public static String immune = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.Immune");
		public static String notAllowedControl = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.NotAllowedControl");

		public static String addedBlacklist = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.AddedBlacklist");
		public static String removedBlacklist = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.RemovedBlacklist");
		public static String allreadyInBlacklist = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.AllreadyInBlacklist");
		public static String notInBlacklist = prefix + TrollPlus.getPlugin().getConfig().getString("Messages.NotInBlacklist");

		public static String banMessagePlayer = TrollPlus.getPlugin().getConfig().getString("Messages.BanMessagePlayer");
		public static String banMessageBroadcast = TrollPlus.getPlugin().getConfig().getString("Messages.BanMessageBroadcast");
		public static String semiBanMessage = TrollPlus.getPlugin().getConfig().getString("Messages.SemiBanMessage");
		public static String fakeOpMessageBroadcast = TrollPlus.getPlugin().getConfig().getString("Messages.FakeOpMessageBroadcast");
	}
}