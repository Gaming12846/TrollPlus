/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.gaming12846.trollplus.TrollPlus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// A utility class for checking updates for the plugin by querying the GitHub API
public class UpdateChecker {
    private final TrollPlus plugin;
    boolean updateAvailable = false;
    private boolean isPreRelease = false;

    // Constructor for the UpdateChecker
    public UpdateChecker(TrollPlus plugin) {
        this.plugin = plugin;
    }

    // Fetches the latest release version from the GitHub API
    public JsonObject getLatestReleaseFromGitHub() throws Exception {
        URL url = new URL("https://api.github.com/repos/Gaming12846/TrollPlus/releases/latest");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return JsonParser.parseString(response.toString()).getAsJsonObject();
        } else
            plugin.getLogger().warning(plugin.getLanguageConfig().getString("unable-check-updates") + " " + responseCode);
        return null;
    }

    // Retrieves if an update is available
    public boolean getUpdateAvailable() {
        return updateAvailable;
    }

    // Retrieves if the latest version is a pre-release
    public boolean getIsPreRelease() {
        return isPreRelease;
    }

    // Checks for updates to the plugin
    public String checkForUpdates() {
        String currentVersion = plugin.getDescription().getVersion();

        try {
            JsonObject latestRelease = getLatestReleaseFromGitHub();
            String latestVersion = latestRelease.get("tag_name").getAsString();
            isPreRelease = latestRelease.get("prerelease").getAsBoolean();

            if (!isPreRelease && !currentVersion.equals(latestVersion)) {
                return plugin.getLanguageConfig().getString("update-available") + " https://github.com/Gaming12846/TrollPlus/releases";
            } else return plugin.getLanguageConfig().getString("no-update-available");
        } catch (Exception exception) {
            plugin.getLogger().warning(plugin.getLanguageConfig().getString("unable-check-updates") + " " + exception.getMessage());
        }
        return null;
    }
}