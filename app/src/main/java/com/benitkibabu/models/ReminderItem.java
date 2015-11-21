package com.benitkibabu.models;

/**
 * Created by Ben on 01/10/2015.
 */
public class ReminderItem {
    String id, moduleName, description, dueDate;

    public ReminderItem(String moduleName, String description, String dueDate){
        super();
        this.moduleName = moduleName;
        this.description = description;
        this.dueDate = dueDate;
    }

    public ReminderItem(String id, String moduleName, String description, String dueDate) {
        this.id = id;
        this.moduleName = moduleName;
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getId() {
        return id;
    }
}
