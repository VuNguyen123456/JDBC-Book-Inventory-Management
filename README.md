# JDBC-Book-Inventory-Management
Java Database Connectivity (JDBC) through the development of a command-line interface for managing book data stored in an Oracle database.

Connects to Oracle campus database using a secure, user-provided login.

Executes a provided SQL script (BookCopies.sql) to set up and populate two related tables: Books and Book_Copies.

Allows users to perform the following operations:

- Search Books by ISBN, title, or category with support for substring matching, displaying all book details.
  
- Show Available Copies for a given book (by ISBN or partial title).
  
- Add a New Copy of an existing book by ISBN or partial title.
  
- Update Book Copy Status while enforcing logical constraints (e.g., preventing updates from "Damaged" to "Available").
  
- Exit the Program only upon explicit user selection.

The system prompts the user for login credentials and SQL file path at startup, handles invalid input gracefully, formats output cleanly, and ensures the database connection is properly closed after use. An optional Python implementation using oracledb is permitted. For extra credit, students may develop a GUI or web application version and demonstrate its functionality.

Deliverables include source code, script/screenshots showing each function in use, and optionally, a video or live demo for GUI implementations.

Set up:
OJDBC 11: https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
MyBatis:  https://github.com/mybatis/mybatis-3/releases/tag/mybatis-3.5.7
