/*
 * This file is part of TrollPlus.
 * Copyright (C) 2026 Gaming12846
 */

package de.gaming12846.trollplus.constants;

import org.bukkit.ChatColor;

public class LangConstants {
    /**
     * The plugin prefix shown before messages
     */
    public static final String PLUGIN_PREFIX =
            ChatColor.DARK_GRAY + "[" + ChatColor.BOLD + ChatColor.DARK_RED + "Troll" + ChatColor.RED + "Plus" + ChatColor.RESET +
                    ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " ";

    /**
     * Configuration file version
     */
    public static final String LANGUAGE_CONFIG_VERSION = "version";

    private LangConstants() {
        // Prevent instantiation
    }

    /**
     * Global system messages and errors
     */
    public static final class Global {
        public static final String METRICS_ENABLED = "global.metrics-enabled";
        public static final String SERVER_VERSION_ONLY_PARTLY_SUPPORTED = "global.server-version-only-partly-supported";
        public static final String CHECKING_FOR_UPDATES = "global.checking-for-updates";
        public static final String UPDATE_AVAILABLE = "global.update-available";
        public static final String NO_UPDATE_AVAILABLE = "global.no-update-available";
        public static final String UNABLE_CHECK_FOR_UPDATES = "global.unable-check-for-updates";
        public static final String CONFIG_OUTDATED = "global.config-outdated";
        public static final String LANGUAGE_CONFIG_OUTDATED = "global.language-config-outdated";
        public static final String FAILED_TO_SAVE_CONFIG = "global.failed-to-save-config";
        public static final String NO_PERMISSION = "global.no-permission";
        public static final String NO_CONSOLE = "global.no-console";
        public static final String INVALID_SYNTAX = "global.invalid-syntax";
        public static final String INVALID_SYNTAX_USAGE = "global.invalid-syntax-usage";

        private Global() {
        }
    }

    /**
     * Messages related to the /trollplus command
     */
    public static final class TrollPlusCommand {
        public static final String VERSION = "trollplus.version";
        public static final String DEVELOPER = "trollplus.developer";
        public static final String PLUGIN_WEBSITE = "trollplus.plugin-website";
        public static final String REPORT_BUGS = "trollplus.report-bugs";
        public static final String RELOAD = "trollplus.reload";

        private TrollPlusCommand() {
        }

        public static final class Blocklist {
            public static final String ALREADY_IN = "trollplus.blocklist.already-in";
            public static final String ADDED = "trollplus.blocklist.added";
            public static final String NOT_IN = "trollplus.blocklist.not-in";
            public static final String REMOVED = "trollplus.blocklist.removed";

            private Blocklist() {
            }
        }
    }

    /**
     * Messages related to the /troll command
     */
    public static final class TrollCommand {
        public static final String KILL_MESSAGE = "troll.kill-message";
        public static final String SPAM_MESSAGES = "troll.spam-messages";
        public static final String FAKE_OP_MESSAGE = "troll.fake-op-message";

        private TrollCommand() {
        }

        public static final class Player {
            public static final String NOT_ONLINE = "troll.player.not-online";
            public static final String IS_IMMUNE = "troll.player.is-immune";
            public static final String QUIT = "troll.player.quit";

            private Player() {
            }
        }

        public static final class NotAvailable {
            public static final String VANISH = "troll.not-available.vanish";
            public static final String TELEPORT = "troll.not-available.teleport";
            public static final String CONTROL = "troll.not-available.control";
            public static final String FALLING_ANVILS = "troll.not-available.falling-anvils";
            public static final String SLOWLY_KILL = "troll.not-available.slowly-kill";
            public static final String FREEFALL = "troll.not-available.freefall";

            private NotAvailable() {
            }
        }

        public static final class Rocket {
            public static final String CANNOT_LAUNCH = "troll.rocket.cannot-launch";
            public static final String LAUNCH_STOPPED = "troll.rocket.launch-stopped";

            private Rocket() {
            }
        }

        public static final class Vanish {
            public static final String QUIT_MESSAGE = "troll.vanish.quit-message";
            public static final String JOIN_MESSAGE = "troll.vanish.join-message";

            private Vanish() {
            }
        }

        public static final class FakeBan {
            public static final String MESSAGE_PLAYER = "troll.fake-ban.message-player";
            public static final String MESSAGE_BROADCAST = "troll.fake-ban.message-broadcast";

            private FakeBan() {
            }
        }
    }

    /**
     * Messages related to the different GUIs
     */
    public static final class GUI {
        public static final String PLACEHOLDER_DESCRIPTION = "gui.placeholder-description";

        private GUI() {
        }

        public static final class Status {
            public static final String ON = "gui.status-on";
            public static final String OFF = "gui.status-off";

            private Status() {
            }
        }

        public static final class Troll {
            public static final String TITLE = "gui.troll.title";

            private Troll() {
            }

            public static final class Features {
                public static final String FREEZE = "gui.troll.features.freeze";
                public static final String FREEZE_DESCRIPTION = "gui.troll.features.freeze-description";
                public static final String HAND_ITEM_DROP = "gui.troll.features.hand-item-drop";
                public static final String HAND_ITEM_DROP_DESCRIPTION = "gui.troll.features.hand-item-drop-description";
                public static final String CONTROL = "gui.troll.features.control";
                public static final String CONTROL_DESCRIPTION = "gui.troll.features.control-description";
                public static final String FLIP_BACKWARDS = "gui.troll.features.flip-backwards";
                public static final String FLIP_BACKWARDS_DESCRIPTION = "gui.troll.features.flip-backwards-description";
                public static final String SPANK = "gui.troll.features.spank";
                public static final String SPANK_DESCRIPTION = "gui.troll.features.spank-description";
                public static final String SPAM_MESSAGES = "gui.troll.features.spam-messages";
                public static final String SPAM_MESSAGES_DESCRIPTION = "gui.troll.features.spam-messages-description";
                public static final String SPAM_SOUNDS = "gui.troll.features.spam-sounds";
                public static final String SPAM_SOUNDS_DESCRIPTION = "gui.troll.features.spam-sounds-description";
                public static final String SEMI_BAN = "gui.troll.features.semi-ban";
                public static final String SEMI_BAN_DESCRIPTION = "gui.troll.features.semi-ban-description";
                public static final String FALLING_ANVILS = "gui.troll.features.falling-anvils";
                public static final String FALLING_ANVILS_DESCRIPTION = "gui.troll.features.falling-anvils-description";
                public static final String TNT_TRACK = "gui.troll.features.tnt-track";
                public static final String TNT_TRACK_DESCRIPTION = "gui.troll.features.tnt-track-description";
                public static final String MOB_SPAWNER = "gui.troll.features.mob-spawner";
                public static final String MOB_SPAWNER_DESCRIPTION = "gui.troll.features.mob-spawner-description";
                public static final String SLOWLY_KILL = "gui.troll.features.slowly-kill";
                public static final String SLOWLY_KILL_DESCRIPTION = "gui.troll.features.slowly-kill-description";
                public static final String RANDOM_TELEPORT = "gui.troll.features.random-teleport";
                public static final String RANDOM_TELEPORT_DESCRIPTION = "gui.troll.features.random-teleport-description";
                public static final String INVENTORY_DROP = "gui.troll.features.inventory-drop";
                public static final String INVENTORY_DROP_DESCRIPTION = "gui.troll.features.inventory-drop-description";
                public static final String INVENTORY_SHUFFLE = "gui.troll.features.inventory-shuffle";
                public static final String INVENTORY_SHUFFLE_DESCRIPTION = "gui.troll.features.inventory-shuffle-description";
                public static final String RANDOM_SCARY_SOUND = "gui.troll.features.random-scary-sound";
                public static final String RANDOM_SCARY_SOUND_DESCRIPTION = "gui.troll.features.random-scary-sound-description";
                public static final String ROCKET = "gui.troll.features.rocket";
                public static final String ROCKET_DESCRIPTION = "gui.troll.features.rocket-description";
                public static final String FREEFALL = "gui.troll.features.freefall";
                public static final String FREEFALL_DESCRIPTION = "gui.troll.features.freefall-description";
                public static final String FAKE_BAN = "gui.troll.features.fake-ban";
                public static final String FAKE_BAN_DESCRIPTION = "gui.troll.features.fake-ban-description";
                public static final String FAKE_OP = "gui.troll.features.fake-op";
                public static final String FAKE_OP_DESCRIPTION = "gui.troll.features.fake-op-description";
                public static final String TELEPORT = "gui.troll.features.teleport";
                public static final String TELEPORT_DESCRIPTION = "gui.troll.features.teleport-description";
                public static final String INVSEE = "gui.troll.features.invsee";
                public static final String INVSEE_DESCRIPTION = "gui.troll.features.invsee-description";
                public static final String KILL = "gui.troll.features.kill";
                public static final String KILL_DESCRIPTION = "gui.troll.features.kill-description";
                public static final String INVSEE_ENDER_CHEST = "gui.troll.features.invsee-ender-chest";
                public static final String INVSEE_ENDER_CHEST_DESCRIPTION = "gui.troll.features.invsee-ender-chest-description";
                public static final String VANISH = "gui.troll.features.vanish";
                public static final String VANISH_DESCRIPTION = "gui.troll.features.vanish-description";
                public static final String RANDOM_TROLL = "gui.troll.features.random-troll";
                public static final String RANDOM_TROLL_DESCRIPTION = "gui.troll.features.random-troll-description";

                private Features() {
                }
            }
        }

        public static final class TrollBows {
            public static final String TITLE = "gui.trollbows.title";

            private TrollBows() {
            }

            public static final class Bows {
                public static final String EXPLOSION_BOW = "gui.trollbows.bows.explosion-bow";
                public static final String EXPLOSION_BOW_DESCRIPTION = "gui.trollbows.bows.explosion-bow-description";
                public static final String TNT_BOW = "gui.trollbows.bows.tnt-bow";
                public static final String TNT_BOW_DESCRIPTION = "gui.trollbows.bows.tnt-bow-description";
                public static final String LIGHTNING_BOLT_BOW = "gui.trollbows.bows.lightning-bolt-bow";
                public static final String LIGHTNING_BOLT_BOW_DESCRIPTION = "gui.trollbows.bows.lightning-bolt-bow-description";
                public static final String SILVERFISH_BOW = "gui.trollbows.bows.silverfish-bow";
                public static final String SILVERFISH_BOW_DESCRIPTION = "gui.trollbows.bows.silverfish-bow-description";
                public static final String POTION_EFFECT_BOW = "gui.trollbows.bows.potion-effect-bow";
                public static final String POTION_EFFECT_BOW_DESCRIPTION = "gui.trollbows.bows.potion-effect-bow-description";

                private Bows() {
                }
            }
        }

        public static final class TrollSettings {
            public static final String TITLE = "gui.trollsettings.title";

            private TrollSettings() {
            }

            public static final class Settings {
                public static final String LANGUAGE = "gui.trollsettings.settings.language";
                public static final String LANGUAGE_DESCRIPTION = "gui.trollsettings.settings.language-description";
                public static final String METRICS_ENABLED = "gui.trollsettings.settings.metrics-enabled";
                public static final String METRICS_ENABLED_DESCRIPTION = "gui.trollsettings.settings.metrics-enabled-description";
                public static final String CHECK_FOR_UPDATES = "gui.trollsettings.settings.check-for-updates";
                public static final String CHECK_FOR_UPDATES_DESCRIPTION = "gui.trollsettings.settings.check-for-updates-description";
                public static final String DEACTIVATE_FEATURES_ON_QUIT = "gui.trollsettings.settings.deactivate-features-on-quit";
                public static final String DEACTIVATE_FEATURES_ON_QUIT_DESCRIPTION = "gui.trollsettings.settings.deactivate-features-on-quit-description";
                public static final String CONTROL_TELEPORT_BACK = "gui.trollsettings.settings.control-teleport-back";
                public static final String CONTROL_TELEPORT_BACK_DESCRIPTION = "gui.trollsettings.settings.control-teleport-back-description";
                public static final String SET_FIRE = "gui.trollsettings.settings.set-fire";
                public static final String SET_FIRE_DESCRIPTION = "gui.trollsettings.settings.set-fire-description";
                public static final String BREAK_BLOCKS = "gui.trollsettings.settings.break-blocks";
                public static final String BREAK_BLOCKS_DESCRIPTION = "gui.trollsettings.settings.break-blocks-description";

                private Settings() {
                }
            }

            public static final class Locale {
                public static final String TITLE = "gui.trollsettings.locale.title";
                public static final String CURRENT_LANGUAGE = "gui.trollsettings.locale.current-language";
                public static final String SUCCESSFULLY_CHANGED = "gui.trollsettings.locale.successfully-changed";

                public static final String CUSTOM = "gui.trollsettings.locale.custom";
                public static final String DE = "gui.trollsettings.locale.de";
                public static final String EN = "gui.trollsettings.locale.en";
                public static final String ES = "gui.trollsettings.locale.es";
                public static final String FR = "gui.trollsettings.locale.fr";
                public static final String NL = "gui.trollsettings.locale.nl";
                public static final String ZH_CN = "gui.trollsettings.locale.zh-cn";
                public static final String ZH_TW = "gui.trollsettings.locale.zh-tw";

                private Locale() {
                }
            }
        }
    }
}