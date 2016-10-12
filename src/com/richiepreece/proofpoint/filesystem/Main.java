package com.richiepreece.proofpoint.filesystem;

import com.richiepreece.proofpoint.filesystem.entity.Entity;
import com.richiepreece.proofpoint.filesystem.exceptions.IllegalFileSystemOperationException;
import com.richiepreece.proofpoint.filesystem.exceptions.NotATextFileException;
import com.richiepreece.proofpoint.filesystem.exceptions.PathAlreadyExistsException;
import com.richiepreece.proofpoint.filesystem.exceptions.PathNotFoundException;
import com.richiepreece.proofpoint.filesystem.handler.FileSystemHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Richie on 9/29/2016.
 */
public class Main {
    public static void main (String [] args) {
        FileSystemHandler fs = new FileSystemHandler();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        System.out.println("Type 'help' for help");

        while(!input.equals("exit")) {
            try {
                System.out.print(":> ");
                input = br.readLine();

                String [] inputs = input.split("\\ ");

                switch (inputs[0]) {
                    case "create":
                        if(inputs.length != 3 && inputs.length != 4) {
                            printHelp();
                            break;
                        }

                        fs.create(textToType(inputs[1]), inputs[2], inputs.length == 3 ? "" : inputs[3]);

                        break;
                    case "delete":
                        if(inputs.length != 2) {
                            printHelp();
                            break;
                        }
                        fs.delete(inputs[1]);

                        break;
                    case "move":
                        if(inputs.length != 3) {
                            printHelp();
                            break;
                        }

                        fs.move(inputs[1], inputs[2]);

                        break;
                    case "writetofile":
                        if(inputs.length < 3) {
                            printHelp();
                            break;
                        }

                        String text = inputs[2];
                        for(int i = 3; i < inputs.length; ++i) text += " " + inputs[i];

                        fs.writeToFile(inputs[1], text);

                        break;
                    case "ls":
                        if(inputs.length != 2) {
                            printHelp();
                            break;
                        }

                        fs.printContents(inputs[1]);

                        break;
                    case "size":
                        if(inputs.length != 2) {
                            printHelp();
                            break;
                        }

                        fs.printSize(inputs[1]);

                        break;
                    case "exit":
                        break;
                    case "help":
                    default:
                        printHelp();
                        break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (IllegalFileSystemOperationException e) {
                System.out.println(e.getMessage());
            } catch (NotATextFileException e) {
                System.out.println(e.getMessage());
            } catch (PathAlreadyExistsException e) {
                System.out.println(e.getMessage());
            } catch (PathNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Entity.Type textToType (String type) {
        switch (type) {
            case "drive":
                return Entity.Type.DRIVE;
            case "folder":
                return Entity.Type.FOLDER;
            case "text_file":
                return Entity.Type.TEXT_FILE;
            case "zip_file":
                return Entity.Type.ZIP_FILE;
            default:
                return null;
        }
    }

    public static void printHelp () {
        System.out.println("******** Help ********");
        System.out.println("create [type] [name] [destination?]");
        System.out.println("  Examples:");
        System.out.println("    create drive a");
        System.out.println("    create folder b a");
        System.out.println("    create zip_file c a\\b");
        System.out.println("    create text_file d a\\b\\c");
        System.out.println("delete [path + name]");
        System.out.println("  Examples:");
        System.out.println("    delete a\\b\\c\\d");
        System.out.println("move [path + name] [destination]");
        System.out.println("  Examples:");
        System.out.println("    move a\\b\\c a\\b");
        System.out.println("writetofile [path + name] [Content For File]");
        System.out.println("  Examples:");
        System.out.println("    writetofile a\\b\\c\\d Test content for file");
        System.out.println("ls [path + name]");
        System.out.println("  Examples:");
        System.out.println("    ls a\\b");
        System.out.println("size [path + name]");
        System.out.println("  Examples:");
        System.out.println("    size a\\b\\c");
        System.out.println("exit");
        System.out.println("  Exits the application");
    }
}
