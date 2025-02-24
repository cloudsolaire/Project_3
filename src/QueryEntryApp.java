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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class QueryEntryApp extends JFrame implements ActionListener{
    private static final int WINDOW_WIDTH = 800; //Window width
    private static final int WINDOW_HEIGHT = 800; //Window heigth
    private static final int USER_DETAILS_FIELD = 50; //User login entry field width
    private static final int QUERY_ENTRY_WIDTH = 30; //Button area width

    //Event buttons
    private JButton connectButton = new JButton(); //Connect to db button
    private JButton disconnectButton = new JButton(); //Disconnect from db button
    private final JTextArea queryEntryField = new JTextArea(); //Query entry field
    private JButton clearButton = new JButton(); //Clear query entry field button
    private JButton executeButton = new JButton(); //Execute query button

    private JLabel connectStatusLabel = new JLabel(); //Connection status label, changes on login

    private final JTextArea queryResultArea = new JTextArea(); //Query results output field
    private JButton resetResultsButton = new JButton(); //Clear query results field button
    private JButton closeButton = new JButton(); //Close application

    public QueryEntryApp(){
        //Creates query window
        setTitle("SQL CLIENT APPLICATION - (JEH - CNT 4714 - SPRING 2025 - PROJECT 3)");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

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
        JLabel urlPropLabel = new JLabel(" D8 URL Properties");
        JComboBox<String> urlDropdownBox = new JComboBox<>(new String[]
            {"project3.properties", "bikedb.properties"});
        JLabel userPropLabel = new JLabel(" User Properties");
        JComboBox<String> userDropdownBox = new JComboBox<>(new String[]
            {"root.properties", "client1.properties", "client2.properties"});
        JLabel usernameLabel = new JLabel(" Username");
        JTextField userEntryField = new JTextField(USER_DETAILS_FIELD);
        JLabel pswdLabel = new JLabel(" Password");
        JPasswordField pswdEntryField = new JPasswordField(USER_DETAILS_FIELD);

        //User selections properties
        urlPropLabel.setBackground(Color.LIGHT_GRAY);
        urlPropLabel.setOpaque(true);
        userPropLabel.setBackground(Color.LIGHT_GRAY);
        userPropLabel.setOpaque(true);
        usernameLabel.setBackground(Color.LIGHT_GRAY);
        usernameLabel.setOpaque(true);
        pswdLabel.setBackground(Color.LIGHT_GRAY);
        pswdLabel.setOpaque(true);
        
        //Connect to db properties
        connectButton = new JButton("Connect to Database");
        connectButton.setFont(new Font("Arial",Font.BOLD, 10));
        connectButton.setForeground(Color.WHITE);
        connectButton.setBackground(Color.BLUE);
        connectButton.setOpaque(true);
        connectButton.setBorderPainted(false); // Remove default border
        connectButton.setFocusPainted(true); // Remove focus border
        connectButton.setContentAreaFilled(true);
        connectButton.addActionListener(this);

        //Disconnect from db properties
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
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(Color.YELLOW);
        middlePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 100));
        connectStatusLabel = new JLabel("NO CONNECTION NOW", SwingConstants.LEFT);
        connectStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        connectStatusLabel.setBackground(Color.GRAY);
        connectStatusLabel.setForeground(Color.RED);
        connectStatusLabel.setOpaque(true);
        
        //Adds sections to middle panel
        middlePanel.add(connectStatusLabel);

        //Bottom panel properties
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.GREEN);
        bottomPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, 350));

        //Bottom panel label properties
        JLabel queryResultLabel = new JLabel("SQL Execution Result Window", SwingConstants.LEFT);
        queryResultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        queryResultLabel.setForeground(Color.BLUE);
        JScrollPane queryScroll = new JScrollPane(queryResultArea);
        queryResultArea.setLineWrap(true);
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
        if (e.getSource() == connectButton){
            System.out.println("Connect button clicked!");

        }
        else if (e.getSource() == disconnectButton){
            System.out.println("Disconnect button clicked!");
        }
        else if (e.getSource() == clearButton){
            System.out.println("Cleared SQL entry!");
        }
        else if (e.getSource() == executeButton){
            System.out.println("Executing SQL command!");
        }
        else if (e.getSource() == resetResultsButton){
            System.out.println("Clearing results...");
            //Call an empty table
        }
        else if (e.getSource() == closeButton){
            System.exit(0); //Exit application
        }
    }
    public static void main(String[] args) {
        // String url = "jdbc:mysql://localhost:3306/project3";
        // String user = "root";
        // String password = "";

        // try (Connection conn = DriverManager.getConnection(url, user, password)) {
        //     System.out.println("Connected to the database!");
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        new QueryEntryApp();
    }
}