package com.richiepreece.proofpoint.filesystem.entity.drive;

import com.richiepreece.proofpoint.filesystem.entity.Entity;

import java.util.HashMap;

/**
 * Created by Richie on 9/29/2016.
 */
public class Drive extends Entity {
    private String name;
    private HashMap<String, Entity> children;
    private HashMap<String, Entity> parent;

    public Drive (String name, HashMap parent) {
        this.name = name;
        this.children = new HashMap<>();
        this.parent = parent;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.name;
    }

    @Override
    public int getSize() {
        int size = 0;

        for(Entity child : this.children.values()) {
            size += child.getSize();
        }

        return size;
    }

    @Override
    public Entity getChild(String name) {
        return this.children.get(name);
    }

    @Override
    public void add(Entity entity) {
        this.children.put(entity.getName(), entity);
    }

    @Override
    public void delete() {
        Object [] children = this.children.values().toArray();
        for(Object child : children) {
            ((Entity)child).delete();
        }

        this.children.clear();

        parent.remove(this.name);
    }

    @Override
    public void move(Entity destination) {
        //Don't allow moves
    }

    @Override
    public Entity remove(String name) {
        return this.children.remove(name);
    }

    @Override
    public void printEntity(String spaces) {
        System.out.println(spaces + name + " : drive : size - " + getSize());

        for(Entity child : this.children.values()) {
            child.printEntity(spaces + "  ");
        }
    }
}
