package com.example.homesecurityautomation;

public class User
{
    private Boolean lights;
    private Boolean alarm;
    private Boolean call;
    private Boolean camera;
    private Boolean mode;
    private String username;
    private String password;

    public User()
    {

    }

    public User(String name, String pass)
    {
        username = name;
        password = pass;
        lights = false;
        alarm = false;
        call = false;
        camera = false;
        mode = false;
    }

    public User(String name, String pass, Boolean l, Boolean a, Boolean c, Boolean cam, Boolean m)
    {
        username = name;
        password = pass;
        lights = l;
        alarm = a;
        call = c;
        camera = cam;
        mode = m;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public Boolean getLights()
    {
        return lights;
    }

    public Boolean getAlarm() {
        return alarm;
    }

    public Boolean getCall() {
        return call;
    }

    public Boolean getCamera() {
        return camera;
    }

    public Boolean getMode() {
        return mode;
    }

    public Boolean setLights(Boolean l)
    {
        lights = l;
        return lights;
    }

    public Boolean setAlarm(Boolean a)
    {
        alarm = a;
        return alarm;
    }

    public Boolean setCall(Boolean c)
    {
        call = c;
        return call;
    }

    public Boolean setCamera(Boolean c)
    {
        camera = c;
        return camera;
    }

    public Boolean setMode(Boolean m)
    {
        mode = m;
        return mode;
    }

}

