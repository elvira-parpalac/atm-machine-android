package com.example.lira.atm.utils;

import com.example.lira.atm.models.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {

    public static void saveObject(Account p){
        try
        {
            System.out.println("Start save");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/sdcard/save_object.bin"))); //Select where you wish to save the file...
            oos.writeObject(p); // write the class as an 'object'
            oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
            oos.close();// close the stream
            System.out.println("End save");

        }
        catch(Exception ex)
        {
            System.out.println("Serialization Save Error : "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static Object loadSerializedObject(String filename){
        File f = new File(filename);
        try
        {
            System.out.println("Start Deserialization");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object o = ois.readObject();

            System.out.println("loadSerializedObject: " + ois);
            System.out.println("End Deserialization");

            return o;
        }
        catch(Exception ex)
        {
            System.out.println("Serialization Read Error : " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }
}
