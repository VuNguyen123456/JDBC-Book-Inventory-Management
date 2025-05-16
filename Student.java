
import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import javax.security.auth.callback.ChoiceCallback;
import org.apache.ibatis.jdbc.ScriptRunner; //
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class Student{
    static Connection con;
    static Statement stmt;
    
    // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter Book ISBN: ");
        // String S_ISBN = scanner.next(); 
        // System.out.println("Enter Book Title: ");
        // String title = scanner.next();
        // System.out.println("Enter Book Category: ");
        // String category = scanner.next();
        // System.out.println("Search Books by:");
        // System.out.println("1. ISBN");
        // System.out.println("2. Title");
        // System.out.println("3. Category");
        // System.out.print("Enter choice (1-3): ");
        // int choice = scanner.nextInt();
        // String S_ISBN;
        // String title;
        // String category;
        // if(choice == 1){
        //     System.out.println("Enter Book ISBN: ");
        //     S_ISBN = scanner.next();
        // }
        // else if(choice == 2){
        //     System.out.println("Enter Book Title: ");
        //    title = scanner.next();
        // }
        // else if(choice == 3){
        //     System.out.println("Enter Book Category: ");
        //     category = scanner.next();
        // }

        // String sql = "SELECT * FROM Books WHERE ISBN = ? OR Title LIKE ? OR Category LIKE ?";
        // PreparedStatement pstmt=connection.prepareStatement(sql); 
        // String Title = "%" + title + "%"; //Substring match
        // String Category = "%" + category + "%";
        // pstmt.setString(1, S_ISBN);
        // pstmt.setString(2, Title);
        // pstmt.setString(3, Category);

    public static void searchBooks(Connection connection) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search Books by:");
        System.out.println("1. ISBN");
        System.out.println("2. Title");
        System.out.println("3. Category");
        System.out.print("Enter choice (1-3): ");
        int choice;
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            return;
        }
        // int choice = scanner.nextInt();
        scanner.nextLine();//clear
        String sql = "";
        PreparedStatement pstmt = null;

        if (choice == 1) {
            System.out.print("Enter Book ISBN: ");
            String S_ISBN = scanner.nextLine();
            sql = "SELECT * FROM Books WHERE ISBN = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, S_ISBN);
        } else if (choice == 2) {
            System.out.print("Enter Book Title: ");
            String title = scanner.nextLine();
            String titlePattern = "%" + title + "%"; // Substring match
            sql = "SELECT * FROM Books WHERE Title LIKE ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, titlePattern);
        } else if (choice == 3) {
            System.out.print("Enter Book Category: ");
            String category = scanner.nextLine();
            String categoryPattern = "%" + category + "%"; // Substring match
            sql = "SELECT * FROM Books WHERE Category LIKE ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, categoryPattern);
        } else {
            System.out.println("Error.");
            return;
        }
        //Then output it?????
        ResultSet rset = pstmt.executeQuery();
        // if(!rset.next()){
        //     System.err.println("Book DNE");
        // }
        // while(rset.next()){
        //     String isbn = rset.getString("ISBN");
        //     String bookTitle = rset.getString("Title");
        //     String categories = rset.getString("Category");
        //     float price = rset.getFloat("Price");

        //     System.out.println("ISBN: " + isbn);
        //     System.out.println("Title: " + bookTitle);
        //     System.out.println("Catego1ry: " + categories);
        //     System.out.println("Price: $" + price);
        // }
        boolean haveSomething = rset.next();
        if (!haveSomething) {
            System.err.println("Book DNE");
        } else {
            do {
                String isbn = rset.getString("ISBN");
                String bookTitle = rset.getString("Title");
                String categories = rset.getString("Category");
                float price = rset.getFloat("Price");

                System.out.println("ISBN: " + isbn);
                System.out.println("Title: " + bookTitle);
                System.out.println("Category: " + categories);
                System.out.println("Price: $" + price);
                System.out.println(); // spacing between entries
            } while (rset.next());
        }
        rset.close();
        pstmt.close();
        // scanner.close();
    }

    public static void NumOfAvailableCopies(Connection connection) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search Books by:");
        System.out.println("1. ISBN");
        System.out.println("2. Title");
        System.out.print("Enter choice (1-2): ");
        int choice;
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            return;
        }
        scanner.nextLine();//clear

        String sql = "";
        PreparedStatement pstmt = null;


        if (choice == 1) {
            System.out.print("Enter Book ISBN: ");
            String S_ISBN = scanner.nextLine();
            sql = "SELECT COUNT(C.Copy#) AS CopyCount FROM Books B, Book_Copies C WHERE B.ISBN = ? AND C.ISBN = B.ISBN AND C.Status = 'Available'";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, S_ISBN);
        } else if (choice == 2) {
            System.out.print("Enter Book Title: ");
            String title = scanner.nextLine();
            String titlePattern = "%" + title + "%"; // Substring match
            sql = "SELECT COUNT(C.Copy#) AS CopyCount FROM Books B, Book_Copies C WHERE Title LIKE ? AND C.ISBN = B.ISBN AND C.Status = 'Available'";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, titlePattern);
        } else {
            System.out.println("Error.");
            return;
        }

        ResultSet rset = pstmt.executeQuery();


        // int copyNum = rset.getInt("Copy#");
        // System.out.println("The number of available copies are: " + copyNum);
        if (rset.next()) {
            int copyCount = rset.getInt("CopyCount");
            System.out.println("The number of available copies: " + copyCount);
        } else {
            System.out.println("The number of available copies: 0");
        }

        rset.close();
        pstmt.close();
        // scanner.close();

    }

    public static void AddNewCopy(Connection connection) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search Books by:");
        System.out.println("1. ISBN");
        System.out.println("2. Title");
        System.out.print("Enter choice (1-2): ");
        int choice;
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            return;
        }
        scanner.nextLine();//clear

        String sql = "";
        PreparedStatement pstmt = null;

        if (choice == 1){
            System.out.print("Enter Book ISBN: ");
            String S_ISBN = scanner.nextLine();
            // System.out.print("Enter new Copy Number: ");
            // int copyNum = scanner.nextInt();
            // scanner.nextLine();
            String sqlCheck = "SELECT 1 FROM Books WHERE ISBN = ?";
            PreparedStatement checkIsbn  = connection.prepareStatement(sqlCheck);
            checkIsbn.setString(1, S_ISBN);
            ResultSet rset0 = checkIsbn.executeQuery();
            if (!rset0.next()) {
                System.out.println("Book DNE.");
                rset0.close();
                checkIsbn.close();
                return;
            }
            rset0.close();
            checkIsbn.close();
            System.out.print("Enter new Copy Number: ");
            int copyNum = scanner.nextInt();
            sql = "INSERT INTO Book_Copies (ISBN, Copy#, Status) VALUES(?,?,'Available')";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, S_ISBN);
            pstmt.setInt(2, copyNum);
        }
        else if(choice == 2){
            System.out.print("Enter Book Title: ");
            String title = scanner.nextLine();
            String titlePattern = "%" + title + "%"; // Substring match
            //sql = "INSERT INTO Book_Copies (ISBN, Copy#, Status)";
            sql = "SELECT B.ISBN FROM Books B, Book_Copies C WHERE B.ISBN = C.ISBN AND B.Title LIKE ?";
            PreparedStatement findIsbn  = connection.prepareStatement(sql);
            findIsbn.setString(1, titlePattern);
            ResultSet rset0 = findIsbn.executeQuery();
            if (!rset0.next()) {
                System.out.println("Book DNE.");
                rset0.close();
                findIsbn.close();
                return;
            }
            int count = 0;
            while(rset0.next()){
                count ++;
            }
            if(count != 1){
                System.out.println("Multiple books found with the same title please re enter.");
                rset0.close();
                findIsbn.close();
                return;
            }
            String isbn = rset0.getString("ISBN");
            rset0.close();
            findIsbn.close();

            System.out.print("Enter new Copy Number: ");
            int copyNum;
            if (scanner.hasNextInt()) {
                copyNum = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                return;
            }
            scanner.nextLine();
            sql = "INSERT INTO Book_Copies (ISBN, Copy#, Status) VALUES(?,?,'Available')";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, isbn);
            pstmt.setInt(2, copyNum);
        }
        else{
            System.out.println("Error: Enter invalid choice or book title/isbn not found");
            return;
        }

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("New copy inserted successfully.");
        } else {
            System.out.println("Insert failed.");
        }


        pstmt.close();
        // scanner.close();
    }

    public static void updateBookCopyStatus(Connection connection) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Book copy ISBN: ");
        String C_ISBN = scanner.next(); 
        System.out.println("Enter Book copy number: ");
        // int copyNum = scanner.nextInt();
        int copyNum;
        if (scanner.hasNextInt()) {
            copyNum = scanner.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            return;
        }
        scanner.nextLine(); // clear
        System.out.print("Enter new status (Available / Checked out / Damaged): ");
        String newStatus = scanner.nextLine();
        String currentStatus;

        String sqlcheck = "SELECT Status FROM Book_Copies WHERE ISBN = ? AND Copy# = ?";
        PreparedStatement checkStatement = connection.prepareStatement(sqlcheck);
        checkStatement.setString(1, C_ISBN);
        checkStatement.setInt(2, copyNum);
        ResultSet rset0 = checkStatement.executeQuery();

        if (!rset0.next()) {
            System.out.println("Book copy DNE.");
            rset0.close();
            checkStatement.close();
            return;
        }
        else{
            //Check current status of copy
            currentStatus = rset0.getString("Status");
            rset0.close();
            checkStatement.close();
            if("Damaged".equals(currentStatus)){
                System.out.println("The copy is damaged and cannot be update");
                return;
            }
        }

        String sqlupdate = "UPDATE Book_Copies SET Status = ? WHERE ISBN = ? AND Copy# = ?";
        PreparedStatement pstmt = connection.prepareStatement(sqlupdate);
        pstmt.setString(1, newStatus);
        pstmt.setString(2, C_ISBN);
        pstmt.setInt(3, copyNum);

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Book copy status updated successfully.");
        } else {
            System.out.println("Failed to update the status.");
        }
        pstmt.close();
    }

    public static void main(String argv[]) throws SQLException, FileNotFoundException
    {
	connectToDatabase();
    }

    public static void connectToDatabase() throws SQLException, FileNotFoundException
    {
	String driverPrefixURL="jdbc:oracle:thin:@";
	String jdbc_url="artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";
	
        // IMPORTANT: DO NOT PUT YOUR LOGIN INFORMATION HERE. INSTEAD, PROMPT USER FOR HIS/HER LOGIN/PASSWD
        // String username="vnguy7";
        // String password="wuceewys";

        // String user;
        // String password;
        // user = readEntry("username: ");
        // password = readEntry("password: ");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username here:");
        String username = scanner.next();
        System.out.println("Enter password here:");
        String password = scanner.next();
        scanner.nextLine();

        // username="vnguy7";
        // password="wuceewys";
        // C:\\Users\\VuNguyen\\OneDrive\\Documents\\GMU\\gmu s6\\CS450\\p2-4\\BookCopies-1.sql

        try{
	    //Register Oracle driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (Exception e) {
            System.out.println("Failed to load JDBC/ODBC driver.");
            return;
        }

       try{
            System.out.println(driverPrefixURL+jdbc_url);
            con=DriverManager.getConnection(driverPrefixURL+jdbc_url, username, password);
            DatabaseMetaData dbmd=con.getMetaData();
            stmt=con.createStatement();

            System.out.println("Connected.");

            if(dbmd==null){
                System.out.println("No database meta data");
            }
            else {
                System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
                System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
                System.out.println("Database Driver Name: "+dbmd.getDriverName());
                System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
            }
        }catch( Exception e) {e.printStackTrace();}
        
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object
        //Reader reader;
        // Reader reader = new BufferedReader(new FileReader("C:\\Users\\VuNguyen\\OneDrive\\Documents\\GMU\\gmu s6\\CS450\\p2-4\\BookCopies-1.sql"));
        // Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the full path to the BookCopies.sql file: ");
        String filePath = scanner.nextLine();

        Reader reader = new BufferedReader(new FileReader(filePath));

        //Running the script
        sr.runScript(reader);
        



        while(true){
            System.out.println("\n1. Search Books\n");
            System.out.println("2. Show the Number of Available Copies\n");
            System.out.println("3. Add a New Copy\n");
            System.out.println("4. Update Book Copy Status (if valid)\n");
            System.out.println("5. Exit\n");

            if(!scanner.hasNextInt()){
                System.out.println("Please re-enter the correct int value\n");
                scanner.nextLine();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if(choice == 1){
                System.out.println("\nEnter a search keyword (ISBN, Title, or Category).");
                searchBooks(con);
            }
            else if(choice == 2){
                System.out.println("Enter an ISBN or Title to display the number of available copies.\n");
                NumOfAvailableCopies(con);
            }
            else if(choice == 3){
                System.out.println("Enter an ISBN or Title, then enter the new book copy information to add a copy.\n");
                AddNewCopy(con);
            }
            else if(choice == 4){
                System.out.println("Enter an ISBN and Copy#, followed by the new status, to update the book copy status (if valid).\n");
                updateBookCopyStatus(con);
            }
            else if(choice == 5){
                System.out.println("Exit the program.\n\n");
                break;
            }
            else{
                System.out.println("Please re-enter the correct value in range 1 - 5\n\n");
                continue;
            }
        }

        scanner.close();
        stmt.close();
        con.close();
    }// End of connectToDatabase()
}

