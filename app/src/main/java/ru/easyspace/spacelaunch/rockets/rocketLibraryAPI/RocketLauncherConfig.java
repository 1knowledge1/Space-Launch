package ru.easyspace.spacelaunch.rockets.rocketLibraryAPI;

public class RocketLauncherConfig {

    public RocketLauncherConfig(RocketsDB Rocket) {
        id = null;
        launch_library_id = null;
        url = null;
        name = Rocket.Rname;
        family = Rocket.Family;
        full_name = null;
        variant = null;
    }

    public RocketLauncherConfig() {}

    public Integer id;

    public Integer launch_library_id;

    public String url;

    public String name;

    public String family;

    public String full_name;

    public String variant;
}
