package com.json.mulityrecycle.bean;


import com.json.mulityrecycle.Myappplication;

/**
 * Created by JsonQiu on 2017/11/30.
 */

public class HomeCategory {

    private int imageid;
    private String typename;

    public HomeCategory(int imageid, String typename) {

        this.imageid = imageid;
        this.typename = typename;
    }

    public HomeCategory(int imageid, int stringID) {

        this.imageid = imageid;
        typename = Myappplication.mContext.getResources().getString(stringID);
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }
}
