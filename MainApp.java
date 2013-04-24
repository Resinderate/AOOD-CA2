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
        
        
        int choice = 0;
        while(choice != 4)
        {        
            printMainMenu();
            choice = sc.nextInt("\nEnter your choice: ", 1, 4);

            if(choice == 1)
            {
                
                String fileName = sc.nextLine("Enter a name for your new database file (eg. something.db)", 3, 50);
                boolean valid = db.create(fileName);
                while(!valid)
                {
                    fileName = sc.nextLine("That file already exists, please enter another", 3, 50);
                    valid = db.create(fileName);
                }
            }
            else if(choice == 2)
            {
                if(!db.isEmpty())
                {
                    System.out.println("Here is a list of existing files: ");
                    db.printFileNames();
                    String fileName = sc.nextLine("\nEnter the name of a valid database to open: ", 3, 50);
                    boolean valid = db.open(fileName);
                    while(!valid)
                    {
                        fileName = sc.nextLine("That filename does not exist, please try another.", 3, 50);
                        valid = db.create(fileName);
                    }

                    //openFileMenu                                                                                                                  <<<<<<<<<<<<<<<<<<<<<<<<
                }
                else
                {
                    System.out.println("There is currently no existing database files.");
                }
            }
            else if(choice == 3)
            {
                if(!db.isEmpty())
                {
                    System.out.println("Here is a list of existing files: ");
                    db.printFileNames();
                    String fileName = sc.nextLine("\nEnter the name of a valid database to delete: ", 3, 50);
                    boolean valid = db.delete(fileName);
                    while(!valid)
                    {
                        fileName = sc.nextLine("That filename does not exist, please try another.", 3, 50);
                        valid = db.delete(fileName);
                    }
                }
                else
                {
                    System.out.println("There is currently no existing database files.");
                }
            }
            
        }
    }
    
    public void printMainMenu()
    {
        System.out.println(":::::::::::::::::::::::::::::::");
        System.out.println("Press 1 to Create a new database");
        System.out.println("Press 2 to Open an existing database");
        System.out.println("Press 3 to Delete an existing database");
        System.out.println("Press 4 to quit");
    }
}
