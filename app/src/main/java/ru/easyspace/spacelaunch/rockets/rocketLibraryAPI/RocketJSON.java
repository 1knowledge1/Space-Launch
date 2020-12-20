package ru.easyspace.spacelaunch.rockets.rocketLibraryAPI;


public class RocketJSON {
    public RocketJSON(RocketsDB Rocket){
         id=Rocket.id;
         url=Rocket.url;
         flight_proven=Rocket.flightProven;
         serial_number=Rocket.serialNumber;
         status=Rocket.status;
         details=Rocket.details;
         launcher_config=new RocketLauncherConfig(Rocket);
         image_url=Rocket.imageUrl;
         flights=Rocket.flights;
         last_launch_date=Rocket.lastLaunchDate;
         first_launch_date=Rocket.firstLaunchDate;
    }
    public RocketJSON(){

    }
    public Integer id;

    public String url;

    public Boolean flight_proven;

    public String serial_number;

    public String status;

    public String details;

    public RocketLauncherConfig launcher_config;

    public String image_url;

    public Integer flights;

    public String last_launch_date;

    public String first_launch_date;
}
