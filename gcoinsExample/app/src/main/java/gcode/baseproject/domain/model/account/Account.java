package gcode.baseproject.domain.model.account;

import gcode.baseproject.view.utils.API;

public class Account {

    private String mId;
    private String mName;
    private String mRole;
    private String mImage;


    public void setId(String id) {
        mId = id;
    }

    public String getId(){
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getRole() {
        return mRole;
    }

    public void setImage(String url) {
        mImage = url;
    }

    public String getImage() {
        return API.IMAGE_URL + mImage;
    }

}
