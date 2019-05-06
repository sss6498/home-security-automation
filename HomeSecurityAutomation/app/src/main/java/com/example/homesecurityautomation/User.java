//Written, Tested, and Debugged By: Nikunj Jhaveri, Miraj Patel, Nirav Patel
package com.example.homesecurityautomation;

//This class represents the user object and helps the app store the current user of the app. The user objects stores the user's email/username, password, privileges, and if they are an admin or not.
public class User
{
    public static int numUsers = 0;
    private Boolean lights;
    private Boolean alarm;
    private Boolean call;
    private Boolean camera;
    private Boolean mode;
    private String username;
    private String password;
    private Boolean admin;

    //empty contructor, needed for database
    public User()
    {

    }

    //constructor taking in username and user password
    public User(String name, String pass)
    {
        username = name;
        password = pass;
        lights = false;
        alarm = false;
        call = false;
        camera = false;
        mode = false;
        admin = false;
    }

    //constructor takes in parameters for all attributes of the user objects.
    public User(String name, String pass, Boolean l, Boolean a, Boolean c, Boolean cam, Boolean m, Boolean ad)
    {
        username = name;
        password = pass;
        lights = l;
        alarm = a;
        call = c;
        camera = cam;
        mode = m;
        admin = ad;
    }

    //Sets the admin status of the user
    public Boolean setAdmin(Boolean a)
    {
        admin = a;
        return admin;
    }

    //Retrieves boolean signifying if user is admin or not.
    public Boolean getAdmin() {
        return admin;
    }

    //Gets the user's username
    public String getUsername() {
        return username;
    }

    //Gets the user's password
    public String getPassword()
    {
        return password;
    }

    //Gets the value of the user's light privilege
    public Boolean getLights()
    {
        return lights;
    }

    //Gets the value of the user's alarm privilege
    public Boolean getAlarm() {
        return alarm;
    }

    //Gets the value of the user's call privilege
    public Boolean getCall() {
        return call;
    }

    //Gets the value of the user's camera privilege
    public Boolean getCamera() {
        return camera;
    }

    //Gets the value of the user's mode privilege
    public Boolean getMode() {
        return mode;
    }

    //Sets the value of the user's light privilege
    public Boolean setLights(Boolean l)
    {
        lights = l;
        return lights;
    }

    //Sets the value of the user's alarm privilege
    public Boolean setAlarm(Boolean a)
    {
        alarm = a;
        return alarm;
    }

    //Sets the value of the user's call privilege
    public Boolean setCall(Boolean c)
    {
        call = c;
        return call;
    }

    //Sets the value of the user's camera privilege
    public Boolean setCamera(Boolean c)
    {
        camera = c;
        return camera;
    }

    //Sets the value of the user's mode privilege
    public Boolean setMode(Boolean m)
    {
        mode = m;
        return mode;
    }

    //used to display the user in text
    public String toString()
    {
        return username;
    }

}

