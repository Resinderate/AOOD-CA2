package ca2;

import java.io.IOException;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Ca2Test {

    @Test
    public void testCreate() {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        boolean created = db.create("testfile.lel");
        assertEquals(created, true);
    }
    
    //testCreate_NotFound()

    @Test
    public void testCreateUnique() {
        DatabaseContainer db = new DatabaseContainer();
        boolean created;
        db.create("testfile.lel");
        created = db.create("testfile.lel");
        assertEquals(created, false);

    }

    @Test
    public void testOpen() {
        DatabaseContainer db = new DatabaseContainer();
        boolean opened = db.open("testfile.lel");
        assertEquals(opened, true);

    }

    @Test
    public void testOpen_NotFound() {
        DatabaseContainer db = new DatabaseContainer();
        boolean opened = db.open("fakefile.lol");
        assertEquals(opened, false);

    }

    @Test
    public void testDelete() {
        DatabaseContainer db = new DatabaseContainer();
        boolean deleted = db.delete("testfile.lel");
        assertEquals(deleted, true);

    }

    @Test
    public void testDelete_NotFound() {
        DatabaseContainer db = new DatabaseContainer();
        boolean deleted = db.delete("fakefile.lol");
        assertEquals(deleted, false);

    }

    @Test
    public void testAdd() throws IOException {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("addingfile.lel");
        db.create("addingfile.lel");
        db.open("addingfile.lel");
        Student s1 = new Student(1000000001, "Aaron", "Conroy", 21, true, 83.2);
        boolean added;
        db.getFile().addStudent(s1);

        
         if(db.getFile().searchByRegex("Aaron") != null) //ie. not empty 
         { 
             added = true; 
         }
         else 
             added = false;
         
        assertEquals(added, true);

    }

    @Test
    public void testAddUnique() {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("addingfile.lel");
        db.create("addingfile.lel");
        db.open("addingfile.lel");
        Student s1 = new Student(1000400101, "Ronan", "Murphy", 19, true, 83.2);
        Student s2 = new Student(1000400101, "Ronan", "Murphy", 19, true, 83.2);

        boolean added;
        db.getFile().addStudent(s1);
        added = db.getFile().addStudent(s2);    //returns false, saying we already have one of that index

        assertEquals(added, false);
    }

    @Test
    public void testEdit() throws IOException {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);

        boolean edited = false;
        db.getFile().addStudent(s1);

        //Makes a new student to replace(edit) the old one with same id
        Student s2 = new Student(1, "Ronan", "Conroy", 21, true, 100);
        if (!db.getFile().updateStudent(s2))//if it didnt update the student then its false.
        {
            edited = false;
        } 
        else 
        {
            if (db.getFile().searchByRegex("Ronan") != null)//Ronan now Exists
            {
                edited = true;
            }
        }

        assertEquals(edited, true);

    }

    @Test
    public void testEdit_NotFound() {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);
        db.getFile().addStudent(s1);
        
        boolean edited;
        
        //Makes a new student to replace(edit) the old one so you don't have to use the menu thing.
        Student s2 = new Student(2, "Ronan", "Murphy", 59, true, 99);
        if (db.getFile().updateStudent(s2)) //if true, it was edited
        {
            edited = true; //it somehow edited something that didnt exist
        } 
        else
        {
            edited = false; //it couldn't find it, correct
        }

        assertEquals(edited, false);
    }

    @Test
    public void testRegex() throws IOException {

        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);

        boolean regex;
        db.getFile().addStudent(s1);


        if (db.getFile().searchByRegex("Aaron") != null) //ie contains something
        {
            regex = true;
        } else {
            regex = false;
        }

        assertEquals(regex, true);
    }

    @Test
    public void testRegex_NotFound() throws IOException {//Its the same thing except your looking for something that dosn't exist.
        DatabaseContainer db = new DatabaseContainer();
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);

        boolean regex;
        db.getFile().addStudent(s1);
        //Makes a new student to replace(edit) the old one so you don't have to use the menu thing.
        ArrayList<Student> results = db.getFile().searchByRegex("Ronan");
        if (results != null) {
            //results = bad
            regex = true;
        } else {
            //no results = good
            regex = false;
        }
        assertEquals(regex, false);
    }
    
    @Test
    public void testRange() throws IOException {        //Its the same thing except your looking for something that dosn't exist.
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);
        Student s2 = new Student(2, "Ronan", "Murphy", 74, true, 99);
        Student s3 = new Student(3, "Billy", "Waters", 12, true, 6);

        int count;
        boolean range = false;
        
        db.getFile().addStudent(s1);
        db.getFile().addStudent(s2);
        db.getFile().addStudent(s3);
        ArrayList<Student> results = db.getFile().searchByRange(80, 10, db.getFile().AGE_TYPE);//Searching for a range of ages and puts it in an arrayList.

        //We added 3 students.
        count = 3;
        if(results != null) //it's empty for some reason
        {
            if (count == results.size())//if count is the same size as the students size than the search found the right students
            {
                range = true;
            }
        }
        assertEquals(range, true);
    }

    @Test
    public void testRange_NotFound() throws IOException {//Its the same thing except your looking for something that dosn't exist.
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);
        Student s2 = new Student(2, "Ronan", "Murphy", 74, true, 99);
        Student s3 = new Student(3, "Billy", "Waters", 12, true, 6);

        boolean range;
        db.getFile().addStudent(s1);
        db.getFile().addStudent(s2);
        db.getFile().addStudent(s3);


        //if the search is empty, good
        //Searching for GPA's between 20-40, which none added have
        ArrayList<Student> results = db.getFile().searchByRange(40, 20, db.getFile().GPA_TYPE);
        if (results == null)//Search should return an empty list.
        {
            range = true;
        } 
        else //Else it found something
        {
            range = false;
        }

        assertEquals(range, true);
    }

    @Test
    public void testDeleteStudent() throws IOException {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);
        boolean deleted;
        db.getFile().addStudent(s1);
        
        System.out.println(":::::::::::::::::::");
        System.out.println("testDeleteStudent::");
        if (db.getFile().searchByRegex("Aaron") != null) {
            System.out.println("Exists");
        } else {
            System.out.println("Dosn't Exist");
        }//First make sure Aaron exists
        System.out.println(":::::::::::::::::::");

        db.getFile().deleteStudent(s1);//Now delete it


        if (db.getFile().searchByRegex("Aaron") != null) {
            deleted = false;
        } else {
            deleted = true;
        }//Check if he still exists.

        assertEquals(deleted, true);



    }

    @Test
    public void testDeleteAll() {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);
        Student s2 = new Student(2, "Ronan", "Murphy", 74, true, 99);
        Student s3 = new Student(3, "Billy", "Waters", 12, true, 6);

        boolean deleted = true;
        db.getFile().addStudent(s1);
        db.getFile().addStudent(s2);
        db.getFile().addStudent(s3);

        if (db.getFile().deleteAll())//if the delete worked
        {
            if (db.getFile().getRecordCount() == 0 && db.fileExists("testFile")) {
                deleted = true;
            }//if the count is 0 and does not exist.
        } else {
            deleted = false;
        }

        assertEquals(deleted, true);


    }

    @Test
    public void testShowAll() {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        Student s1 = new Student(1, "Aaron", "Conroy", 21, true, 100);
        Student s2 = new Student(2, "Ronan", "Murphy", 74, true, 99);
        Student s3 = new Student(3, "Billy", "Waters", 12, true, 6);

        db.getFile().addStudent(s1);
        db.getFile().addStudent(s2);
        db.getFile().addStudent(s3);
        
        System.out.println("\n:::::::::::::::::::");
        System.out.println("testShowAll");
        System.out.println("Should be 3 records");
        db.getFile().printAllStudents();
        System.out.println(":::::::::::::::::::");
    }

    @Test
    public void testShowAll_NotFound() {
        DatabaseContainer db = new DatabaseContainer();
        db.delete("testfile.lel");
        db.create("testfile.lel");
        db.open("testfile.lel");
        db.getFile().deleteAll();
        
        //Trying to print an empty file.
        System.out.println("\n:::::::::::::::::::");
        System.out.println("testShowAll_NotFound");
        System.out.println("Should be 0 records");
        db.getFile().printAllStudents();
        System.out.println(":::::::::::::::::::");
        

    }
}
