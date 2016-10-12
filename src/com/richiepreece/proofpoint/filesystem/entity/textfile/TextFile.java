package com.richiepreece.proofpoint.filesystem.entity.textfile;

import com.richiepreece.proofpoint.filesystem.entity.Entity;

import java.util.ArrayList;

/**
 * Created by Richie on 9/29/2016.
 */
public class TextFile extends Entity {
    private String name;
    private String content;
    private Entity parent;

    public TextFile (String name, Entity parent) {//, String content) {
        this.name = name;
        this.content = "";//content;
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
        return this.getContent().length();
    }

    @Override
    public Entity getChild(String name) {
        return null;
    }

    @Override
    public void add(Entity entity) {
        //Cannot add children
    }

    @Override
    public void delete() {
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
        //Cannot remove children
        return null;
    }

    @Override
    public void printEntity(String spaces) {
        System.out.println(spaces + name + " : text_file : size - " + getSize() + " : content - " + content);
    }

    public String getContent () {
        return this.content;
    }

    public void setContent (String content) {
        this.content = content;
    }
}
