package ca2;

import java.io.*;

/**
 *
 * @author Ronan
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       MainApp theApp = new MainApp();
       theApp.run();
    }
    
    public void run()
    {
        DatabaseContainer db = new DatabaseContainer();
        MyScanner sc = new MyScanner();
        
        System.out.println(":::::::::::::::::::::::::::::::");
        System.out.println("Welcome to the database system!");
        System.out.println(":::::::::::::::::::::::::::::::");
        
        printMainMenu();
        
    }
    
    public void printMainMenu()
    {
        System.out.println("Press 1 to Create a new database");
        System.out.println("Press 2 to Open an existing database");
        System.out.println("Press 3 to Delete an existing database");
        System.out.println("Press 4 to quit");
    }
}
