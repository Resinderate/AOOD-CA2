package ca2;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                System.out.println("Database created!");
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

                    useOpenMenu(db, sc);
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
                System.out.println("Database deleted!");
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
    
    public void printOpenMenu()
    {
        System.out.println(":::::::::::::::::::::::::::::::");
        System.out.println("Press 1 to Add a record to the database");
        System.out.println("Press 2 to Edit an existing record, based on student ID");
        System.out.println("Press 3 to Search by Regex");
        System.out.println("Press 4 to Search by a numeric Range");
        System.out.println("Press 5 to Delete a record, based on student ID");
        System.out.println("Press 6 to Delete all records in the database");
        System.out.println("Press 7 to Show all records in the database");
        System.out.println("Press 8 to Show the number of records in the databse");
        System.out.println("Press 9 to quit to main menu");
    }
    
    public void useOpenMenu(DatabaseContainer db, MyScanner sc)
    {
        
        int choice = 0;
        while(choice != 9)
        {
            printOpenMenu();
            choice = sc.nextInt("Enter your choice: ", 1, 9);

            if(choice == 1)
                addRecord(db, sc);
            else if(choice == 2)
                editRecord(db, sc);
            else if(choice == 3)
                searchByRegex(db, sc);
            else if(choice == 4)
                searchByRange(db, sc);
            else if(choice == 5)
                deleteRecord(db, sc);
            else if(choice == 6)
                deleteAll(db);
            else if(choice == 7)
                showAll(db);
            else if(choice == 8)
                showCount(db);  
        }            
    }
    
    public void addRecord(DatabaseContainer db, MyScanner sc)
    {
        int id = sc.nextInt("Please enter student ID (1000000000 - 1999999999): ", 1000000000, 1999999999);
        String fName = sc.nextLine("Please enter student First Name: ", 1, 50);
        String lName = sc.nextLine("Please enter student Last Name: ", 1, 50);
        int age = sc.nextInt("Please enter student age: ");
        boolean sex;
        String sexStr =  sc.validate("Please enter the student sex (male / female)", "male|female");
        if(sexStr.equals("male"))
            sex = true;
        else
            sex = false;
        double GPA = sc.nextDouble("Please enter the student's current GPA (0-100): ", 0.0, 100.0);
            
        Student newStudent = new Student(id, fName, lName, age, sex, GPA);
        db.getFile().addStudent(newStudent);
        System.out.println("Student added!");
    }
    
    public void editRecord(DatabaseContainer db, MyScanner sc)
    {
        int id = sc.nextInt("Please enter the id of the student you wish to edit: ", 1000000000, 1999999999);
        if(db.getFile().getStudent(id) != null)
        {
            Student s = db.getFile().getStudent(id);
            int choice = sc.nextInt("Please enter the number of the field you wish to edit:"
                    + "\n1. First Name"
                    + "\n2. Last Name"
                    + "\n3. Age"
                    + "\n4. Sex"
                    + "\n5. Current GPA", 1, 5);

            if(choice == 1)
            {
                s.setFirstName(sc.nextLine("Please enter student First Name: ", 1, 50));
            }
            else if(choice == 2)
            {
                s.setLastName(sc.nextLine("Please enter student Last Name: ", 1, 50));
            }
            else if(choice == 3)
            {
                s.setAge(sc.nextInt("Please enter student age: "));
            }   
            else if(choice == 4)
            {
                boolean sex;
                String sexStr =  sc.validate("Please enter the student sex (male / female)", "male|female");
                if(sexStr.equals("male"))
                    sex = true;
                else
                    sex = false;
                s.setSex(sex);
            }   
            else if(choice == 5)
            {
                s.setCurrentGPA(sc.nextDouble("Please enter the student's current GPA (0-100): ", 0.0, 100.0));
            }
            System.out.println("Record Edited!");
        }
        else
            System.out.println("Student not found!");
    }
    
    public void searchByRegex(DatabaseContainer db, MyScanner sc)
    {
        String regex = sc.nextLine("Please enter the regex you want to search with: ", 1, 100);
        ArrayList<Student> results;
        try {
            results = db.getFile().searchByRegex(regex);
        
        
        if(!results.isEmpty())
        {
            System.out.println("Results found: ");
            for(Student s : results)
            {
                System.out.println("**********");
                System.out.println(s);
            }
        }
        else
            System.out.println("No results found!");
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void searchByRange(DatabaseContainer db, MyScanner sc)
    {
        String typeStr = sc.validate("Please enter which field you want to search (age / gpa): ", "age|gpa");
        boolean type;
        if(typeStr.equals("age"))
            type = db.getFile().AGE_TYPE;
        else
            type = db.getFile().GPA_TYPE;
        
        int lo = sc.nextInt("What is the lower range of your search?");
        int hi = sc.nextInt("What is the higher ranger of your search?");
        
        ArrayList<Student> results;
        try {
            results = db.getFile().searchByRange(hi, lo, type);
        
        
        if(!results.isEmpty())
        {
            System.out.println("Results found: ");
            for(Student s : results)
            {
                System.out.println("**********");
                System.out.println(s);
            }
        }
        else
            System.out.println("No results found!");
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteRecord(DatabaseContainer db, MyScanner sc)
    {
        int id = sc.nextInt("Please enter the id of the sudent you wish to delete: ", 1000000000, 1999999999);
        if(db.getFile().getStudent(id) != null)
        {
            if(db.getFile().deleteStudent(db.getFile().getStudent(id)))
                System.out.println("Delete successful!");
            else
                System.out.println("That student appears to be gone already!");
        }
        else
            System.out.println("That student appears to be gone already!");
    }
    
    public void deleteAll(DatabaseContainer db)
    {
        if(db.getFile().deleteAll())
            System.out.println("Delete successful!");
        else
            System.out.println("Something seems to have gone wrong."); //IOException
    }
    
    public void showAll(DatabaseContainer db)
    {
        ArrayList<Student> results = db.getFile().getStudents();
        
        if(!results.isEmpty())
        {
            System.err.println("All the entries: ");
            for(Student s : results)
            {
                System.out.println("**********");
                System.out.println(s);
            }
        }
        else
            System.out.println("No entries found!");
    }
    
    public void showCount(DatabaseContainer db)
    {
            System.out.println("There are " + db.getFile().getRecordCount() + " total records in the database.");   
    }
    
}
