package ca2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class DatabaseContainer 
{
    private final String fileDir = "DBFiles";
    
    private ArrayList<String> fileNames = null;
    public StudentRandomFile currentStudentFile = null;
    
    
    
    public DatabaseContainer()
    {
        fileNames = getFileNames();
        File dir = new File(fileDir);
        if(!dir.exists())
            dir.mkdirs();
    }
    
    public ArrayList<String> getFileNames()
    {
        ArrayList<String> existingFiles = new ArrayList<String>();
        File dir = new File(fileDir);
        
        if(dir.exists() && dir.isDirectory())
        {
            String[] files = dir.list();
            existingFiles.addAll(Arrays.asList(files));
        }
        return existingFiles;
    }
    
    public boolean create(String fileName)
    {
        if(!fileExists(fileName))
        {
            File f = new File(fileDir + "/" + fileName);
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.out.println("IOException in DatabaseContainer::Create");
            }
            return true;
        }
        else
            return false;
        //if so get another
    }
    
    public void open(String fileName)
    {
        if(fileExists(fileName))
        {
            currentStudentFile =  new StudentRandomFile(fileDir + "/" +fileName);
        }
    }
    
    public boolean delete(String fileName)
    {
        if(fileExists(fileName))
        {
            File f = new File(fileDir + "/" + fileName);
            f.delete();
            fileNames.remove(fileName);
            return true;
        }
        else
            return false;
    }
    
    public boolean fileExists(String fileName)
    {
        if(fileNames.contains(fileName))
            return true;
        else
            return false;
    }  
}
