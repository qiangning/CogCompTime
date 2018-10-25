package edu.illinois.cs.cogcomp.temporal.utils.IO;

import java.io.*;

public class mySerialization {
    private boolean verbose;

    public mySerialization() {
        verbose = true;
    }

    public mySerialization(boolean verbose) {
        this.verbose = verbose;
    }

    public void serialize(Object obj, String path) throws Exception{
        File serializedFile = new File(path);
        FileOutputStream fileOut = new FileOutputStream(serializedFile.getPath());
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(obj);
        out.close();
        fileOut.close();
        if(verbose)
            System.out.println("Serialization of object has been saved to "+serializedFile.getPath());
    }
    public Object deserialize(String path) throws Exception{
        File serializedFile = new File(path);
        Object obj = null;
        if(serializedFile.exists()){
            if(verbose)
                System.out.println("Serialization exists. Loading from "+serializedFile.getPath());
            FileInputStream fileIn = new FileInputStream(serializedFile.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = in.readObject();
            in.close();
            fileIn.close();
        }
        else{
            if(verbose)
                System.out.println("Serialization doesn't exist. Return null. ");
        }
        return obj;
    }
}
