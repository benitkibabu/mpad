package com.benitkibabu.models;

/**
 * Created by Ben on 03/10/2015.
 */
public class GridItem {
    String title;
    int icon;

    public GridItem(int icon,String title){
        super();
        this.title = title;
        this.icon = icon;
    }

    public GridItem(String title){
        super();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
