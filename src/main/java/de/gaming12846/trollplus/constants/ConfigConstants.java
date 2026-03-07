/*
 * This file is part of TrollPlus.
 * Copyright (C) 2026 Gaming12846
 */

package de.gaming12846.trollplus.constants;

/**
 * Constant class for holding constant values that are used for configuration
 */
public class ConfigConstants {
    // Configuration file version
    public static final String CONFIG_VERSION = "version";

    private ConfigConstants() {
        // Prevent instantiation
    }

    /**
     * General configuration
     */
    public static final class General {
        public static final String LANGUAGE = "general.language";
        public static final String METRICS_ENABLED = "general.enable-metrics";
        public static final String CHECK_FOR_UPDATES = "general.check-for-updates";
        public static final String LOG_LEVEL = "general.log-level";

        private General() {
        }
    }

    /**
     * Feature configuration
     */
    public static final class Features {
        public static final String DEACTIVATE_ON_QUIT = "features.deactivate-on-quit";
        public static final String SET_FIRE = "features.set-fire";
        public static final String BREAK_BLOCKS = "features.break-blocks";

        private Features() {
        }
    }

    /**
     * Troll configuration
     */
    public static final class Troll {
        public static final String HAND_ITEM_DROP_PERIOD = "troll.hand-item-drop-period";
        public static final String SPANK_PERIOD = "troll.spank-period";
        public static final String SPAM_MESSAGES_PERIOD = "troll.spam-messages-period";
        public static final String SPAM_SOUNDS_PERIOD = "troll.spam-sounds-period";
        public static final String SEMI_BAN_MESSAGE_REPLACE = "troll.semi-ban-message-replace";
        public static final String FALLING_ANVILS_PERIOD = "troll.falling-anvils-period";
        public static final String TNT_TRACK_PERIOD = "troll.tnt-track-period";
        public static final String MOB_SPAWNER_PERIOD = "troll.mob-spawner-period";
        public static final String SLOWLY_KILL_PERIOD = "trol.slowly-kill-period";
        public static final String RANDOM_TELEPORT_PERIOD = "troll.random-teleport-period";
        public static final String RANDOM_SCARY_SOUND_VOLUME = "troll.random-scary-sound-volume";
        public static final String FAKE_BAN_MESSAGE_BROADCAST_ENABLED = "troll.fake-ban-message-broadcast-enabled";
        public static final String FAKE_OP_MESSAGE_BROADCAST_ENABLED = "troll.fake-op-message-broadcast-enabled";
        public static final String FREEFALL_HEIGHT = "troll.freefall-height";

        private Troll() {
        }

        public static final class Vanish {
            public static final String JOIN_MESSAGE_ENABLED = "troll.vanish.join-message-enabled";
            public static final String QUIT_MESSAGE_ENABLED = "troll.vanish.quit-message-enabled";

            private Vanish() {
            }
        }

        public static final class Control {
            public static final String TELEPORT_BACK = "troll.control.teleport-back";
            public static final String PERIOD = "troll.control.period";

            private Control() {
            }
        }

        public static final class Rocket {
            public static final String CHARGES = "troll.rocket.charges";
            public static final String PERIOD = "troll.rocket.period";

            private Rocket() {
            }
        }
    }
}