/*
    Name: Jessica Hernandez
    Course: CNT 4714 Spring 2025
    Assignment title: Project 3 – A Two-tier Client-Server Application
    Date: March 14, 2025
    Class: Enterprise Computing
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class QueryEntryApp extends JFrame implements ActionListener{
    //Window variables
    private static final int WINDOW_WIDTH = 800; //Window width
    private static final int WINDOW_HEIGHT = 800; //Window heigth
    private static final int USER_DETAILS_FIELD = 50; //User login entry field width
    private static final int QUERY_ENTRY_WIDTH = 30; //Button area width

    //Event buttons variables
    private JButton connectButton = new JButton(); //Connect to db button
    private JButton disconnectButton = new JButton(); //Disconnect from db button
    private final JTextArea queryEntryField = new JTextArea(); //Query entry field
    private JButton clearButton = new JButton(); //Clear query entry field button
    private JButton executeButton = new JButton(); //Execute query button

    //Status label variables
    private JLabel connectStatusLabel = new JLabel(); //Connection status label, changes on login
    private boolean connectToDB = false; //Boolean Connection status
    private Connection connection; //Connection Driver object
    private Statement statement; //SQL Statement object
    private ResultSet resultSet; //Result Set object
    private ResultSetMetaData metaData; //Result Set Metadata object
    private int resultRows; //Number of rows in table
    
    //Output variables
    private final JTable queryResultTable = new JTable(); //Query results output field
    private final JScrollPane queryScroll = new JScrollPane(queryResultTable);
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JButton resetResultsButton = new JButton(); //Clear query results field button
    private JButton closeButton = new JButton(); //Close application
    
    //User login variables
    private final JComboBox<String> urlDropdownBox = new JComboBox<>(new String[]
    {"" ,"project3.properties", "bikedb.properties"}); //URL dropdown menu
    private final JComboBox<String> userDropdownBox = new JComboBox<>(new String[]
    {"" ,"root.properties", "client1.properties", "client2.properties"}); //User properties dropdown menu
    private final JTextField userEntryField = new JTextField(USER_DETAILS_FIELD); //User entry field
    private final JPasswordField pswdEntryField = new JPasswordField(USER_DETAILS_FIELD); //Password entry field

    

    public QueryEntryApp(){
        //Creates query window
        setTitle("SQL CLIENT APPLICATION - (JEH - CNT 4714 - SPRING 2025 - PROJECT 3)"); //Window Title
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT); //Set window size
        setResizable(false); //Fixed window
        setLocationRelativeTo(null); //Center window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close window x button
        setLayout(new BorderLayout(10,10)); //Window layout

        //Creates top panel with left and right sections
        JPanel topContainer = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JPanel leftTopSection = new JPanel(new GridLayout(6,2,5,5));
        JPanel rightTopSection = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); //Padding between components
        gbc.fill = GridBagConstraints.BOTH;  //To resize

        //Left top section properties
        JLabel leftTopTitle = new JLabel("Connection Details", SwingConstants.LEFT);
        leftTopTitle.setFont(new Font("Arial", Font.BOLD, 18));
        leftTopTitle.setForeground(Color.BLUE);
        leftTopSection.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));
        
        //Left top subpanels
        JLabel urlPropLabel = new JLabel(" DB URL Properties"); //Database label
        JLabel userPropLabel = new JLabel(" User Properties"); //User properties label
        JLabel usernameLabel = new JLabel(" Username"); //Username label
        JLabel pswdLabel = new JLabel(" Password"); //Password label

        //User selections properties
        urlPropLabel.setBackground(Color.LIGHT_GRAY);
        urlPropLabel.setOpaque(true);
        userPropLabel.setBackground(Color.LIGHT_GRAY);
        userPropLabel.setOpaque(true);
        usernameLabel.setBackground(Color.LIGHT_GRAY);
        usernameLabel.setOpaque(true);
        pswdLabel.setBackground(Color.LIGHT_GRAY);
        pswdLabel.setOpaque(true);
        
        //Connect Button to db properties
        connectButton = new JButton("Connect to Database");
        connectButton.setFont(new Font("Arial",Font.BOLD, 10));
        connectButton.setForeground(Color.WHITE);
        connectButton.setBackground(Color.BLUE);
        connectButton.setOpaque(true);
        connectButton.setBorderPainted(false);
        connectButton.setContentAreaFilled(true);
        connectButton.addActionListener(this);

        //Disconnect Button from db properties
        disconnectButton = new JButton("Disconnect from Database");
        disconnectButton.setFont(new Font("Arial", Font.BOLD, 10));
        disconnectButton.setForeground(Color.WHITE);
        disconnectButton.setBackground(Color.RED);
        disconnectButton.setOpaque(true);
        disconnectButton.setBorderPainted(false);
        disconnectButton.setFocusPainted(true);
        disconnectButton.setContentAreaFilled(true);
        disconnectButton.addActionListener(this);

        //Left top section grid
        leftTopSection.add(leftTopTitle);
        leftTopSection.add(new JLabel());
        leftTopSection.add(urlPropLabel);
        leftTopSection.add(urlDropdownBox);
        leftTopSection.add(userPropLabel);
        leftTopSection.add(userDropdownBox);
        leftTopSection.add(usernameLabel);
        leftTopSection.add(userEntryField);
        leftTopSection.add(pswdLabel);
        leftTopSection.add(pswdEntryField);
        leftTopSection.add(connectButton);
        leftTopSection.add(disconnectButton);

        //Right top section properties
        JLabel rightTopTitle = new JLabel("Enter An SQL Command", SwingConstants.LEFT);
        rightTopTitle.setFont(new Font("Arial", Font.BOLD, 18));
        rightTopTitle.setForeground(Color.BLUE);

        //Clear entry button properties
        clearButton = new JButton("Clear SQL Command");
        clearButton.setFont(new Font("Arial", Font.BOLD, 10));
        clearButton.setBackground(Color.WHITE);
        clearButton.setForeground(Color.RED);
        clearButton.setOpaque(true);
        clearButton.setBorderPainted(false);
        clearButton.setFocusPainted(true);
        clearButton.setContentAreaFilled(true);
        clearButton.addActionListener(this);
        
        //Execute Button properties
        executeButton = new JButton("Execute SQL Command");
        executeButton.setFont(new Font("Arial", Font.BOLD, 10));
        executeButton.setBackground(Color.GREEN);
        executeButton.setForeground(Color.BLACK);
        executeButton.setOpaque(true);
        executeButton.setBorderPainted(false);
        executeButton.setFocusPainted(true);
        executeButton.setContentAreaFilled(true);
        executeButton.addActionListener(this);

        // Row 0 - Title (spans 2 columns)
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.weightx = 1;  // Allow horizontal expansion
        rightTopSection.add(rightTopTitle, gbc);

        // Row 1 - Query Entry Field (spans 2 columns)
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2; // Make it span both columns
        gbc.weighty = 0;  // Allow vertical expansion
        queryEntryField.setPreferredSize(new Dimension(QUERY_ENTRY_WIDTH, 190)); // Taller input field
        queryEntryField.setRows(5);
        queryEntryField.setLineWrap(true);
        queryEntryField.setWrapStyleWord(true);
        queryEntryField.setMargin(new Insets(5, 5, 5, 5));
        rightTopSection.add(queryEntryField, gbc);

        // Row 2 - Buttons (each takes one column)
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1; // Reset span
        gbc.weighty = 1;  // Normal weight
        rightTopSection.add(clearButton, gbc);
        gbc.gridx = 1; // Move to next column
        rightTopSection.add(executeButton, gbc);

        //Adds sections to top panel
        topPanel.add(leftTopSection);
        topPanel.add(rightTopSection);
        topContainer.add(topPanel, BorderLayout.CENTER);
        topContainer.setPreferredSize(new Dimension(WINDOW_WIDTH,300));

        //Middle panel properties
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 50));
        connectStatusLabel = new JLabel("NO CONNECTION NOW", SwingConstants.LEFT);
        connectStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        connectStatusLabel.setBackground(Color.GRAY);
        middlePanel.setBackground(Color.GRAY);
        connectStatusLabel.setForeground(Color.RED);
        connectStatusLabel.setOpaque(true);
        
        //Adds sections to middle panel
        middlePanel.add(connectStatusLabel, BorderLayout.WEST);

        //Bottom panel properties
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        bottomPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 350));

        //Bottom panel label properties
        JLabel queryResultLabel = new JLabel("SQL Execution Result Window", SwingConstants.LEFT);
        queryResultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        queryResultLabel.setForeground(Color.BLUE);

        queryEntryField.setWrapStyleWord(true);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        //Clear Results Button properties
        resetResultsButton = new JButton("Clear Result Window");
        resetResultsButton.setFont(new Font("Arial", Font.BOLD, 10));
        resetResultsButton.setBackground(Color.YELLOW);
        resetResultsButton.setForeground(Color.BLACK);
        resetResultsButton.setOpaque(true);
        resetResultsButton.setBorderPainted(false);
        resetResultsButton.setFocusPainted(true);
        resetResultsButton.setContentAreaFilled(true);
        resetResultsButton.addActionListener(this);

        //Close App Button properties
        closeButton = new JButton("Close Application");
        closeButton.setFont(new Font("Arial", Font.BOLD, 10));
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.BLACK);
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(true);
        closeButton.setContentAreaFilled(true);
        closeButton.addActionListener(this);

        //Adds sections to bottom panel
        bottomPanel.add(queryResultLabel, BorderLayout.NORTH);
        bottomPanel.add(queryScroll, BorderLayout.CENTER);
        buttonPanel.add(resetResultsButton);
        buttonPanel.add(closeButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        //Add panels to window
        add(topContainer, BorderLayout.NORTH);
        add(middlePanel,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);

        //Display query window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //Connect button -> Connects to database
        if (e.getSource() == connectButton){
            String url = (String) urlDropdownBox.getSelectedItem();
            String properties = (String) userDropdownBox.getSelectedItem();

            //If no database is selected
            if (urlDropdownBox.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(this, "PLEASE SELECT A DATABASE");
            }
            //If no user properties are selected
            if (userDropdownBox.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(this, "PLEASE SELECT USER PROPERTIES");
            }

            String propFile = "src/User_Properties/" + properties; //Used if properties outside of src file

            //Creates properites object to selected specific login credentials
            Properties props = new Properties();
            try(FileInputStream fileInput = new FileInputStream(propFile)){
                props.load(fileInput);
            } catch(IOException exception){
                JOptionPane.showMessageDialog(this, "ERROR LOADING USER PROPERTIES FILE!");
                exception.printStackTrace();
                return;
            }
            //Assigns login credentials strings based on properties file
            String username = props.getProperty("MYSQL_DB_USERNAME");
            String password = props.getProperty("MYSQL_DB_PASSWORD");
            String newURL = createURLStr(url, "");

            //Attempts to connect to database using external driver
            try{
                connection = DriverManager.getConnection(newURL, username, password);
                connectToDB = true;
                connectStatusLabel.setText("CONNECTED TO: " + newURL);
                connectStatusLabel.setForeground(Color.BLACK);
                userEntryField.setText(username);
                userEntryField.setBackground(Color.BLACK);
                userEntryField.setForeground(Color.WHITE);
                pswdEntryField.setText(password);
                pswdEntryField.setBackground(Color.BLACK);
                pswdEntryField.setForeground(Color.WHITE);
            } catch(SQLException ex){
                connectToDB = false;
                connectStatusLabel.setText("NOT CONNECTED - User Credentials Do Not Match Properties File!");
                connectStatusLabel.setForeground(Color.RED);
                ex.printStackTrace();
            }
                        
        }
        //Disconnect button -> Disconnects from database
        else if (e.getSource() == disconnectButton){
            try {
                if (connection != null && !connection.isClosed()){
                    connection.close();
                    connectToDB = false;
                    userEntryField.setText("");
                    userEntryField.setBackground(Color.WHITE);
                    userEntryField.setForeground(Color.BLACK);
                    pswdEntryField.setText("");
                    pswdEntryField.setBackground(Color.WHITE);
                    pswdEntryField.setForeground(Color.BLACK);
                    connectStatusLabel.setText("NO CONNECTION NOW");
                    connectStatusLabel.setForeground(Color.RED);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        //Clear button -> Clears user query field
        else if (e.getSource() == clearButton){
            queryEntryField.setText("");
        }
        //Execute button -> Execute user query button
        else if (e.getSource() == executeButton){
            try{
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                setQuery(queryEntryField.getText());
            } catch(SQLException sqlExc){
                JOptionPane.showMessageDialog(this, sqlExc.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                queryResultTable.setModel(new DefaultTableModel());
            }
        }
        //Reset button -> Reset query results table
        else if (e.getSource() == resetResultsButton){
            queryResultTable.setModel(new DefaultTableModel());
        }
        //Close button -> Close application
        else if (e.getSource() == closeButton){
            System.exit(0); //Exit application
        }
    }

    //Creates a URL string based on user selection
    public String createURLStr(String url, String newURL){
        if (url.contains("project3")){
            newURL = "jdbc:mysql://localhost:3306/project3";
        }
        if (url.contains("bikedb")){
            newURL = "jdbc:mysql://localhost:3306/bikedb";
        }
        return newURL;
    }

    //Creates a query statement based on user input
    //  and creates a table based on results
    public void setQuery(String query) throws SQLException, IllegalStateException{
        //Checks if a connection to a database is found
        if (!connectToDB){
            throw new IllegalStateException("NO CONNECTION FOUND!");
        }

        //NOT USED IN ACCOUNTANT APP
        String username = userEntryField.getText() + "@localhost";
        System.out.println(username);

        //Checks if user query includes SELECT
        if (query.trim().toUpperCase().startsWith("SELECT")){
            resultSet = statement.executeQuery(query);
            updateOperationsLog(username, true); //Updates operationslog database
        }
        //Assumes other queries INSERT,UPDATE,etc.
        else{
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                //Displays number of rows affected by query
                JOptionPane.showMessageDialog(
                    this, 
                    "Successful Update: " + rowsAffected + " rows updated.", 
                    "Successful Update", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } 
            else {
                //Displays update status
                JOptionPane.showMessageDialog(
                    this, 
                    "Update executed but no rows were affected.", 
                    "Update Status", 
                    JOptionPane.WARNING_MESSAGE
                );
               
            updateOperationsLog(username, false);
            }
        }
        metaData = resultSet.getMetaData();

        int resultColumns = metaData.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> data = new Vector<>();

        for (int i = 1; i <= resultColumns; i++){
            columnNames.add(metaData.getColumnName(i));
        }

        while(resultSet.next()){
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= resultColumns; i++){
                row.add(resultSet.getObject(i));
            }
            data.add(row);
        }

        tableModel = new DefaultTableModel(data, columnNames);
        queryResultTable.setModel(tableModel);

        resultSet.last();
        resultRows = resultSet.getRow();
    }

    private void updateOperationsLog(String username, boolean isSelectQuery) {
        String opURL = "jdbc:mysql://localhost:3306/operationslog";
        String opUser = "root";
        String opPswd = "USERPASSWORD"; //Modified for security
        
        // For SELECT queries, increment only num_queries;
        // For DELETE, INSERT, UPDATE, increment both num_queries and num_updates.
        String sql = isSelectQuery 
                ? "UPDATE operationscount SET num_queries = num_queries + 1 WHERE login_username = ?" 
                : "UPDATE operationscount SET num_queries = num_queries + 1, num_updates = num_updates + 1 WHERE login_username = ?";
        
        try (Connection logConnection = DriverManager.getConnection(opURL, opUser, opPswd);
             PreparedStatement ps = logConnection.prepareStatement(sql)) {
             
            ps.setString(1, username);
            int rowsUpdated = ps.executeUpdate();
            
            // If no record exists for the user, insert a new one.
            if (rowsUpdated == 0) {
                String insertSQL = "INSERT INTO operationscount (login_username, num_queries, num_updates) VALUES (?, ?, ?)";
                try (PreparedStatement psInsert = logConnection.prepareStatement(insertSQL)) {
                    psInsert.setString(1, username);
                    if (isSelectQuery) {
                        psInsert.setInt(2, 1);  // 1 query executed
                        psInsert.setInt(3, 0);  // no update executed
                    } else {
                        psInsert.setInt(2, 1);  // 1 query executed
                        psInsert.setInt(3, 1);  // 1 update executed
                    }
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new QueryEntryApp();
    }
}