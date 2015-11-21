package com.benitkibabu.models;

/**
 * Created by Ben on 04/10/2015.
 */
public class ModuleItem {
    String name;
    String credit;

    public ModuleItem(String name, String credit) {
        this.name = name;
        this.credit = credit;
    }

    public String getName() {
        return name;
    }

    public String getCredit() {
        return credit;
    }
}
