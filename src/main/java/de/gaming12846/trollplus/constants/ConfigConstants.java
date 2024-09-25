/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.constants;

public class ConfigConstants {
    public static final String CONFIG_VERSION = "version";
    // Global settings
    public static final String LANGUAGE = "language";
    public static final String METRICS_ENABLED = "metrics-enabled";
    public static final String CHECK_FOR_UPDATES = "check-for-updates";
    public static final String LOG_LEVEL = "log-level";
    // Feature settings
    public static final String DEACTIVATE_FEATURES_ON_QUIT = "deactivate-features-on-quit";
    public static final String SET_FIRE = "set-fire";
    public static final String BREAK_BLOCKS = "break-blocks";
    // Troll settings
    public static final String VANISH_JOIN_MESSAGE_ENABLED = "vanish.join-message-enabled";
    public static final String VANISH_QUIT_MESSAGE_ENABLED = "vanish.quit-message-enabled";
    public static final String HAND_ITEM_DROP_PERIOD = "hand-item-drop-period";
    public static final String CONTROL_TELEPORT_BACK = "control-teleport-back";
    public static final String CONTROL_PERIOD = "control-period";
    public static final String SPANK_PERIOD = "spank-period";
    public static final String SPAM_MESSAGES_PERIOD = "spam-messages-period";
    public static final String SPAM_SOUNDS_PERIOD = "spam-sounds-period";
    public static final String SEMI_BAN_MESSAGE_REPLACE = "semi-ban-message-replace";
    public static final String FALLING_ANVILS_PERIOD = "falling-anvils-period";
    public static final String TNT_TRACK_PERIOD = "tnt-track-period";
    public static final String MOB_SPAWNER_PERIOD = "mob-spawner-period";
    public static final String SLOWLY_KILL_PERIOD = "slowly-kill-period";
    public static final String RANDOM_SCARY_SOUND_VOLUME = "random-scary-sound-volume";
    public static final String ROCKET_CHARGES = "rocket-charges";
    public static final String ROCKET_PERIOD = "rocket.period";
    public static final String FAKE_BAN_MESSAGE_BROADCAST_ENABLED = "fake-ban-message-broadcast-enabled";
    public static final String FAKE_OP_MESSAGE_BROADCAST_ENABLED = "fake-op-message-broadcast-enabled";
    public static final String FREEFALL_HEIGHT = "freefall-height";

    // Prevent instantiation
    private ConfigConstants() {
    }
}