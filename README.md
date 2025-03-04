## Getting Started

Project 3 assignment
- All files included in Assignment_Files

## Planning
INSTALL MYSQL AND WORKBENCH

Building a two-tier, 2 different Java based applications
1. Allows end-users to issue SQL commands against various databases
    - Create a Java GUI based front end that will accept any MySQL DDL or DML command
    -   Then pass that command through a JDBC connection to the MySQL db server

> Note too, that the only DML command that uses the executeQuery() method of JDBC is the Select command, 
>   all other DML and DDL commands utilize executeUpdate().

> Default number of connections is 151

    - A background (business logic) transaction logging operation will occur which 
    -   keeps a running total of the number of queries 
    -   and the number of updates that have occurred via each different user’s interactions with your application 
    -   (aggregate over all separate connections per user). 
    - This is a separate database (i.e., a completely different database than any to which a client user can connect), 
    -    to which the application will connect, using special application-level privileges in a separate properties file. 
    - This separate properties file is not accessible by any end user. 
    - Each user operation will cause the application to make this connection and update the operational logging database table.

2. Based on first application but restricted to specialized accountant client
    - Permissions are restricted to viewing(querying) this transaction logging database

Both applications
    - The project3app "client user" commands must be issued using the PreparedStatement interface
    - Non-query commands must display a message to the user with status of executed command

## Process

Install bikedb from notes

First step
1. Login to MySQL workbench as root user and execute scripts to populate backend databases
2. Use project3dbscript.sql - creates project3 db
3. Use project3operations.sql - creates operationslog
    > only used and accessed by project3app and theaccountant
    >   not intended for normal end-users

Second step
1. Create client-level users
2. NAMES: client1, client2, project3app, theaccountant
3. Use clientCreationScriptProject3.sql
    > Sign in as root user
    > Script only needs to be executed once

Third step
1. Assign correct permissions to each users
2. Use clientPermissionsScriptProject3.sql
    > Assignments
    >   client1 - select project3 and bikedb
    >   client2 - select, update project 3 and bikedb
    >   project3app - select, insert, update operationslog
    >   theaccountant - select operationslog
    > Script only needs to be executed once

## Output and Screenshots

NOTE:  Be  sure  to  label  each  screenshot.
    Use  the  convention:  RootCommand1, RootCommand2A,  RootCommand2B,  and  so  on.
    Similarly  for  Client1Command1, Client1Command2A, and so on. 

1. All .java files from project 
    > Place into a folder named Source Code
2. All 17 screenshots from project3rootscript.sql commands
    > Place into a folder named Root Commands Screenshots
3. All 11 screenshots from project3client1script.sql commands
    > Place into a folder named Client1 Commands Screenshots
4. All 11 screenshots from project3client2script.sql commands
    > Place into a folder named Client2 Commands Screenshots
5. All 3 screenshots for account specific interface application page 4
    > Page 16 num 12 and page 18 number 13 and page 19 num 14
    > First 2
    >       (1) the results of the query –  
    >           “select num_queries from operationscount where login_username = “root@localhost”;  
    >       (2) select * from operationscount ; 
    > Last
    >       (3) select * from operationscount;
    > Place into a folder named Accountant-OperationsLog Screenshots

6. A screenshot showing mismatch between user-entered credentials 
    and the selected properties file resulting in no connection.
    > Page 15
    > Place into a folder named Credentials Mismatch Screenshot

## To Do v 1

1. Make sure Java project can access external driver (.jar to use JDBC) DONE
2. Create a JFrame window DONE
3. Draw outline map for GUI DONE
4. Create JPanel sections DONE
5. Create leftTopSection, rightTopSection, middlePanel, bottomPanel DONE

Left Top Section
1. URL Drop down sections DONE
2. User Dropdown sections DONE
3. Username and pswd background color DONE
4. Connect button actions DONE
5. Disconnect button actions DONE

Right Top Section
1. Figure out how query string works DONE
2. Clear button actions DONE
3. Execute button actions DONE
    > Running total on number of queries
    > Running total on number of updates from queries

Middle Panel
1. Needs default status DONE
2. Needs status update after successful login DONE

Bottom
1. Needs Title DONE
2. Needs empty table IN PROG
3. Needs clear table button DONE
4. Needs close app button DONE

## To Do v2

Top-Left Panel
1. Find out how to check from valid list of passwords DONE
2. Find out how to check from user properties DONE
3. How to connect to db DONE
4. How to disconnect from db DONE

Top-Right Panel
1. Find out how to execute a query and read correctly DONE
2. Find out how to read from specific db DONE

Middle Panel
1. DEPENDENT ON VALID LOGIN
    Change login status based on connection DONE

Bottom Panel
1. Find out how to make an empty table DONE
2. Find out how to make a table based on query DONE

## To Do v3

No Section
1. Change username and password fields after connectToDB = true DONE
2. Left Align connectionStatusLabel DONE
3. Align top labels IGNORE
4. Begin accountant app DONE

Accountant APP
>CREATE CLASS TO STORE NUM_QUERIES AND NUM_UPDATES
1. Create new GUI window for special user DONE
2. Access num of updates and num queries DONE

## To Do v4

1. Find out how to store num_queries and num_updates DONE
2. Store login_username during each query DONE
3. Take screenshots
4. Add comments to both apps