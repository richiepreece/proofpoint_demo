package com.richiepreece.proofpoint.filesystem.entity;


/**
 * Created by Richie on 9/29/2016.
 */
public abstract class Entity {
    public static enum Type {
        DRIVE,
        FOLDER,
        TEXT_FILE,
        ZIP_FILE
    };

    public abstract String getName();
    public abstract String getPath();
    public abstract int getSize();
    public abstract Entity getChild(String name);
    public abstract void add(Entity entity);
    public abstract void delete();
    public abstract void move(Entity destination);
    public abstract Entity remove(String name);
    public abstract void printEntity(String spaces);
}
