//Written, Tested, and Debugged By: Nikunj Jhaveri, Miraj Patel, Nirav Patel
package com.example.homesecurityautomation;

//This class is for the photo object for pictures to be viewed
public class Photo
{
    private String photoURL;
    private String name;

    public Photo()
    {

    }

    //Constructor used to create a photo object
    public Photo(String imagename, String imageurl)
    {
        if(imagename.trim().equals(""))
        {
            name = "No name";
        }
        name = imagename;
        photoURL = imageurl;
    }

    //Constructor used to create a photo object

    public Photo(String imageurl)
    {
        name = "";
        photoURL = imageurl;
    }

    //Gets the photo image's url
    public String getPhotoURL()
    {
        return photoURL;
    }

    //sets the photo images url
    public void setUrl(String imageurl)
    {
        photoURL = imageurl;
    }

}
