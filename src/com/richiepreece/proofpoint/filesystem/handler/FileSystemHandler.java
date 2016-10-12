package com.richiepreece.proofpoint.filesystem.handler;

import com.richiepreece.proofpoint.filesystem.entity.Entity;
import com.richiepreece.proofpoint.filesystem.entity.Entity.*;
import com.richiepreece.proofpoint.filesystem.entity.drive.Drive;
import com.richiepreece.proofpoint.filesystem.entity.folder.Folder;
import com.richiepreece.proofpoint.filesystem.entity.textfile.TextFile;
import com.richiepreece.proofpoint.filesystem.entity.zipfile.ZipFile;
import com.richiepreece.proofpoint.filesystem.exceptions.IllegalFileSystemOperationException;
import com.richiepreece.proofpoint.filesystem.exceptions.NotATextFileException;
import com.richiepreece.proofpoint.filesystem.exceptions.PathAlreadyExistsException;
import com.richiepreece.proofpoint.filesystem.exceptions.PathNotFoundException;

import java.util.HashMap;

/**
 * Created by Richie on 9/29/2016.
 */
public class FileSystemHandler {
    private HashMap<String, Entity> drives;

    public FileSystemHandler () {
        drives = new HashMap<>();
    }

    public void create (Type type, String name, String pathOfParent) throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
        Entity entity;

        switch (type) {
            case DRIVE:
                if(pathOfParent.length() > 0) {
                    throw new IllegalFileSystemOperationException("Drive cannot have a parent");
                }

                entity = getEntity(name);

                if(entity != null) {
                    throw new PathAlreadyExistsException("Drive already exists");
                } else {
                    drives.put(name, new Drive(name, drives));
                }

                break;
            case FOLDER:
                entity = getEntity(pathOfParent);

                if(entity == null) {
                    throw new PathNotFoundException("Parent entity doesn't exist");
                } else if(entity.getChild(name) != null) {
                    throw new PathAlreadyExistsException("Entity already exists");
                } else {
                    entity.add(new Folder(name, entity));
                }

                break;
            case TEXT_FILE:
                entity = getEntity(pathOfParent);

                if(entity == null) {
                    throw new PathNotFoundException("Parent entity doesn't exist");
                } else if(entity.getChild(name) != null) {
                    throw new PathAlreadyExistsException("Entity already exists");
                } else {
                    entity.add(new TextFile(name, entity));
                }

                break;
            case ZIP_FILE:
                entity = getEntity(pathOfParent);

                if(entity == null) {
                    throw new PathNotFoundException("Parent entity doesn't exist");
                } else if(entity.getChild(name) != null) {
                    throw new PathAlreadyExistsException("Entity already exists");
                } else {
                    entity.add(new ZipFile(name, entity));
                }

                break;
        }
    }

    public void delete (String path) throws PathNotFoundException {
        Entity entity = getEntity(path);

        if(entity == null) {
            throw new PathNotFoundException("Entity doesn't exist");
        } else {
            entity.delete();
        }
    }

    public void move (String sourcePath, String destinationPath) throws PathNotFoundException, PathAlreadyExistsException, IllegalFileSystemOperationException {
        Entity entity = getEntity(sourcePath);
        if(entity instanceof Drive) {
            throw new IllegalFileSystemOperationException("Cannot move a drive");
        }

        if(entity == null) {
            throw new PathNotFoundException("Entity doesn't exist");
        } else {
            Entity destination = getEntity(destinationPath);

            if(destination == null) {
                throw new PathNotFoundException("Destination doesn't exist");
            } else if (destination.getChild(entity.getName()) != null) {
                throw new PathAlreadyExistsException("Entity already exists at destination");
            } else {
                entity.move(destination);
            }
        }
    }

    public void writeToFile (String path, String content) throws PathNotFoundException, NotATextFileException {
        Entity entity = getEntity(path);

        if(entity == null) {
            throw new PathNotFoundException("Text File doesn't exist");
        } else if(!(entity instanceof TextFile)) {
            throw new NotATextFileException("Entity is not a text file");
        } else {
            ((TextFile)entity).setContent(content);
        }
    }

    public void printContents (String path) throws PathNotFoundException {
        Entity entity = getEntity(path);

        if(entity == null) {
            throw new PathNotFoundException("Entity doesn't exist");
        }

        entity.printEntity("");
    }

    public void printSize (String path) throws PathNotFoundException {
        Entity entity = getEntity(path);

        if(entity == null) {
            throw new PathNotFoundException("Entity doesn't exist");
        }

        System.out.println(entity.getSize());
    }

    private Entity getEntity (String path) {
        String [] paths = path.split("\\\\");

        Entity currEntity = drives.get(paths[0]);

        for(int i = 1; i < paths.length; ++i) {
            if(currEntity == null) return null;

            currEntity = currEntity.getChild(paths[i]);
        }

        return currEntity;
    }
}
