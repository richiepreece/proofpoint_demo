package com.richiepreece.proofpoint.filesystem.entity.folder;

import com.richiepreece.proofpoint.filesystem.entity.Entity;

import java.util.HashMap;

/**
 * Created by Richie on 9/29/2016.
 */
public class Folder extends Entity {
    private String name;
    private Entity parent;
    private HashMap<String, Entity> children;

    public Folder (String name, Entity parent) {
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
        return this.parent.getPath() + "\\" + this.name;
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

        this.parent.remove(this.name);
    }

    @Override
    public void move(Entity destination) {
        this.parent.remove(this.name);
        this.parent = destination;
        destination.add(this);
    }

    @Override
    public Entity remove(String name) {
        return this.children.remove(name);
    }

    @Override
    public void printEntity(String spaces) {
        System.out.println(spaces + name + " : folder : size - " + getSize());

        for(Entity child : this.children.values()) {
            child.printEntity(spaces + "  ");
        }
    }
}
