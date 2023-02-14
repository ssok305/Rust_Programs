/*
-This GUI will take in user input, but it will not store that input unless it a valid input
-It will send out messages to inform user that their input is invalid
-It also has a 'success' message for when all the input is valid


The constuctor is th emain frame, each method is a seperate container that the main frame displays.
To call a new method you must empty the current container, usually labeled cp by removeALL() and then calling the method. 
*/


package mySqlDriver2;
///load file imports
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//MySQL imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
//GUI imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 
@SuppressWarnings("serial")
public class InventoryDataBase extends JFrame {

    //This is credentials required to connect to MySQL
   private String url, username, password;

   private static String FILE_NAME = "test.csv";
    //These are unchaing integers that will assist with choosing options
    private boolean PID_SELCTION = false;
    private boolean TITLE_SELECTION =  false;
    private boolean SID_SELECTION = false;

    String menuSelection;//Used in displayMenue()
    String fieldSelection; // Used in updateRecord()

 
    // Constructor to setup the GUI components and event handlers
    public InventoryDataBase() {  
        getLogin();//will start from the begining

        //CAN CALL JUST ONE METHOD IF YOU WANT TO TEST IT ALONE
        //displayMenue();
        //searchRecords();
        //createRecord();
        //updateRecord();
        //getLogin();
        //deleteRecord();

        /*()
        try {
            loadFile();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        
   }

   public void displayMenue(){
    
    Container cp = getContentPane();
    cp.setLayout(new FlowLayout());

    // Create JComboBox for getting the prompt to run
    cp.add(new JLabel("Select a prompt:"));

    final String[] prompts = {"Create a new record", "Look up a record", "Update a record", "Delete an existing record", "Load a CSV file"};
    menuSelection = prompts[0]; // Setting initial selection to first item

    final JComboBox<String> selections = new JComboBox<String>(prompts);
    selections.setPreferredSize(new Dimension(300, 80));
    cp.add(selections);

    // Update menu selection when something is selected in drop down
    selections.addItemListener(new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                menuSelection = (String)selections.getSelectedItem();
            }
        }
    });

        //Jbutton  
    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(menuSelection);
            if(menuSelection.equals("Create a new record")){
                cp.removeAll();
                createRecord();   

            }
            else if( menuSelection.equals("Look up a record")){
                cp.removeAll();
                searchRecords();
            }
            else if(menuSelection.equals("Update a record")){
                cp.removeAll();
                updateRecord();
            }
            else if(menuSelection.equals("Delete an existing record")){
                cp.removeAll();
                deleteRecord();

            }
            else if(menuSelection.equals("Load a CSV file")){
                cp.removeAll();
                try {
                    loadFile();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
        }

     });
      cp.add(submitButton);

    // Allocate an anonymous instance of an anonymous inner class that
    // implements ActionListener as ActionEvent listener

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
    setTitle("Menu:"); // "super" JFrame sets title
    setSize(350, 225);         // "super" JFrame sets initial size
    setVisible(true);          // "super" JFrame shows
    

   }
   
   private void deleteRecord() {
      // Retrieve the content-pane of the top-level container JFrame
      // All operations done on the content-pane

      JTextField searchPid;  // Use Swing's JTextField instead of AWT's TextField
      JButton submitButton;    // Using Swing's JButton instead of AWT's Button
      
      Container cp = getContentPane();
      cp.setLayout(new FlowLayout());
      //This panel will make so it will make a new row for the textFields
      JPanel panelOne = new JPanel();
      panelOne.setLayout(new FlowLayout());
      panelOne.setVisible(true);

      JLabel text = new JLabel("Enter the Product ID of the item to be deleted.");
      panelOne.add(text);

      JPanel panelTwo = new JPanel();
      searchPid = new JTextField(10);
      panelTwo.add(searchPid);

      //Once the record is found the data will be displayed in this area
      //This panel wont be visible until the search for the product id is made
      JPanel panelThree = new JPanel();
      panelThree.setVisible(false);
      JPanel panelFour = new JPanel();
      panelFour.setVisible(false);

      //This is what the data will be displayed on 
      JTextArea textPane = new JTextArea();
      textPane.setEditable(false);
      textPane.setLineWrap(true);
      textPane.setSize(200,100);
      panelThree.add(textPane);

      //This is the confirmation that they want to delete a record, action is brought up later
      JButton yes = new JButton("Yes");
      yes.setForeground(Color.RED);
      panelFour.add(yes);
      //Will take th user back to the main menu if they do not want to delete a record after all
      JButton noButton = new JButton("No!");
      noButton.setForeground(Color.green);
      noButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           System.out.println("The record was NOT deleted");
           cp.removeAll();
           displayMenue();
        }
     });
    
      panelFour.add(noButton);
      
     //This button is used to make a query to MySQL if user enters a valid product id
      submitButton = new JButton("Submit");
      submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fieldselection = 0;
            //Wont go make a search until there is a valid product id
            if(checkInteger(searchPid.getText())){
                fieldselection = 1;
            }
           if(fieldselection == 1 && searchPid.getText() != null){
               String record = searchPid.getText();
               //clearing first two panels in order to give attention the the third/last one
               panelOne.setVisible(false);
               panelTwo.setVisible(false);
               panelThree.setVisible(true);
               panelFour.setVisible(true);

                try {
                
                    Connection connection = DriverManager.getConnection(url, username, password);
                    //Changed the size here because the discription was cut off on a smaller window
                    setSize(400, 250);

                    PreparedStatement myStmt2 = connection.prepareStatement("Select * FROM new_inventory WHERE product_id = '" + record + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
                    ResultSet myRs = myStmt2.executeQuery();
                    //If there isn't a recprd with the product id entered they have the option 
                     // 1: go to main menu, code already in noButton so no need to rewrite
                    // 2: try again, a new button is called instead of yes button to redo delete method

                    if (myRs.next() == false) {
                        yes.setVisible(false);
                        noButton.setText("Return to Menu");
                        textPane.setText("Record was not found\n");
                        JButton retryButton = new JButton("Try again");
                        panelFour.add(retryButton);
                        retryButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                               cp.removeAll();
                               deleteRecord();
                            }
                         });
                         return;
                    }
                    myRs.beforeFirst();//This will move the pointer back to the first result so it will print results
                    
                
                    //this how the data is displayed on the panel
                    
                        while (myRs.next()) {
                        //System.out.print(myRs.);
                        textPane.setText("Product ID: " + myRs.getString("product_id") + "\n"
                            + "Product Title: " + myRs.getString("product_title") + "\n"
                            + "Product Description: " + myRs.getString("product_description") + "\n"
                            + "Quantity: " + myRs.getInt("quantity") + "\n"
                            + "Wholesale: " + myRs.getString("wholesale_price") + "\n"
                            + "Sale Price: " + myRs.getString("sale_price") + "\n"
                            + "Supplier ID: " + myRs.getString("supplier_id") + "\n"  
                            );
                        }
                    
                    //This action needs to be here so you can use the local varibles in the try/catch 
                    yes.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //another try/catch cuz the code got mad
                            try{
                            Connection deleteConnection = DriverManager.getConnection(url, username, password);
                            String query = "DELETE FROM new_inventory WHERE product_id = '" + record + "'";
                            PreparedStatement delStmt = deleteConnection.prepareStatement(query);
                            delStmt.execute();

                           System.out.println("The record was Sucessfully deleted");
                            }catch (SQLException se) {
                    
                                System.out.println("oops, error!");
                                se.printStackTrace();
                            } catch (InputMismatchException exception) {
                                System.out.print(exception.getMessage()); //try to find out specific reason.
                            }
                           cp.removeAll();
                           displayMenue();
                        }
                     });   
                } catch (SQLException se) {
                    
                    // TODO Auto-generated catch block
                    System.out.println("oops, error!");
                    se.printStackTrace();
                } catch (InputMismatchException exception) {
                    System.out.print(exception.getMessage()); //try to find out specific reason.
                }
               
               //printResults(fieldselection, record);
           }
           
        }
     });
     panelTwo.add(submitButton);
     cp.add(panelOne);
     cp.add(panelTwo);
     cp.add(panelThree);
     cp.add(panelFour);
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
      setTitle("Delete Record"); // "super" JFrame sets title
      setSize(400, 200);         // "super" JFrame sets initial size
      setVisible(true);          // "super" JFrame shows
    }//END Delete Record







   public void updateRecord(){    
        JTextField searchPid;  // Use Swing's JTextField instead of AWT's TextField
        JButton submitButton;    // Using Swing's JButton instead of AWT's Button
        
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        //This panel will make so it will make a new row for the textFields
        JPanel panelOne = new JPanel();
        panelOne.setLayout(new FlowLayout());
        panelOne.setVisible(true);

        JLabel text = new JLabel("Enter the Product ID of the item to be updated.");
        panelOne.add(text);

        JPanel panelTwo = new JPanel();
        searchPid = new JTextField(10);
        panelTwo.add(searchPid);

        //Once the record is found the data will be displayed in this area
        //This panel wont be visible until the search for the product id is made
        
        JPanel panelFour = new JPanel();
        panelFour.setVisible(false);

        //This is what the data will be displayed on panel 3
        JPanel panelThree = new JPanel(new GridLayout(6, 2, 3, 2));//6 rows and 2 columns
        panelThree.setVisible(false);
        JTextField titleText, discriptionText, integerText, wholesale_priceText, sales_priceText, sidText;

        //(Row 1)
        panelThree.add(new JLabel("Product Title"));
        titleText = new JTextField(20);
        panelThree.add(titleText);

        //(Row 2)
        panelThree.add(new JLabel("Product Discription"));
        discriptionText = new JTextField(30);
        panelThree.add(discriptionText);

        //(Row 3)
        panelThree.add(new JLabel("Quantity"));
        integerText = new JTextField(8);
        panelThree.add(integerText);

        //(Row 4)
        panelThree.add(new JLabel("Wholesale Price"));
        wholesale_priceText = new JTextField(10);
        panelThree.add(wholesale_priceText);

        //(Row 5)
        panelThree.add(new JLabel("Sales Price"));
        sales_priceText = new JTextField(8);
        panelThree.add(sales_priceText);

        //(Row 6)
        panelThree.add(new JLabel("Supplier's ID"));
        sidText = new JTextField(8);
        panelThree.add(sidText);


        //This is the confirmation that they want to delete a record, action is brought up later
        panelFour.add(new JLabel("Change record with new information"));
        JButton yes = new JButton("Yes");
        yes.setForeground(Color.RED);
        panelFour.add(yes);
        //Will take th user back to the main menu if they do not want to delete a record after all
        JButton noButton = new JButton("No!");
        noButton.setForeground(Color.green);
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                System.out.println("The record was NOT updated");
                cp.removeAll();
                displayMenue();
                }
            });
        panelFour.add(noButton);
        JLabel label = new JLabel();
        panelFour.add(label);
        
        //This button is used to make a query to MySQL if user enters a valid product id
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fieldselection = 0;
                //Wont go make a search until there is a valid product id
                if(checkInteger(searchPid.getText())){
                    fieldselection = 1;
                }
            if(fieldselection == 1 && searchPid.getText() != null){
                String record = searchPid.getText();
                //clearing first two panels in order to give attention the the third/last one
                panelOne.setVisible(false);
                panelTwo.setVisible(false);
                panelThree.setVisible(true);
                panelFour.setVisible(true);
                    try {
                    
                        Connection connection = DriverManager.getConnection(url, username, password);
                        //Changed the size here because the discription was cut off on a smaller window
                        setSize(500, 300);

                        PreparedStatement myStmt2 = connection.prepareStatement("Select * FROM new_inventory WHERE product_id = '" + record + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                
                        ResultSet myRs = myStmt2.executeQuery();
                        //If there isn't a recprd with the product id entered they have the option 
                        // 1: go to main menu, code already in noButton so no need to rewrite
                        // 2: try again, a new button is called instead of yes button to redo delete method

                        if (myRs.next() == false) {
                            yes.setVisible(false);
                            noButton.setText("Return to Menu");
                            //textPane.setText("Record was not found\n");
                            JButton retryButton = new JButton("Try again");
                            panelFour.add(retryButton);
                            retryButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                cp.removeAll();
                                deleteRecord();
                                }
                            });
                            return;
                        }
                        myRs.beforeFirst();//This will move the pointer back to the first result so it will print results

                        //this how the data is displayed
                        
                        while (myRs.next()) {
                            //System.out.print(myRs.);
                            titleText.setText( myRs.getString("product_title"));
                            discriptionText.setText(myRs.getString("product_description"));
                            integerText.setText(myRs.getString("quantity"));
                            wholesale_priceText.setText(myRs.getString("wholesale_price"));
                            sales_priceText.setText(myRs.getString("sale_price"));
                            sidText.setText(myRs.getString("supplier_id"));

                        }
                    }catch (SQLException se) {
                                
                        System.out.println("oops, error!");
                        se.printStackTrace();
                    } catch (InputMismatchException exception) {
                        System.out.print(exception.getMessage()); //try to find out specific reason.
                    }

                        //This action needs to be here so you can use the local varibles in the try/catch 
                    yes.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            //This will reset the label every time that the button is pressed
                            label.setForeground(Color.red);
                            label.setText("");

                            //This will check if the Integer ID is a number
                            if(titleText.getText().isEmpty()){   
                                label.setText("Enter a Product Title!!!");
                            }
                            //These will make sure that the string Text Fields are not blank
                            else if(discriptionText.getText().isEmpty()){   
                                label.setText("Enter a Product Discription!!!");
                            }
                            else if(!checkInteger(integerText.getText())){
                                label.setText("Invalid Integer!");
                            }
                            else if(integerText.getText().length() > 8){
                                label.setText("Integer is too Big");
                            }
                            else if(integerText.getText().isEmpty()){
                                label.setText("Enter a Integer");
                            }
                            else if(wholesale_priceText.getText().isEmpty()){
                                label.setText("Enter Wholesales Price");
                            }
                            else if(checkDouble(wholesale_priceText.getText()) == false){
                                label.setText("Invalid Wholesale Price");
                            }
                            else if(sales_priceText.getText().isEmpty()){
                                label.setText("Enter a Sales Price");
                            }
                            else if(!checkDouble(sales_priceText.getText())){
                                label.setText("Invalid Sales Price"); 
                            }
                            else if(sidText.getText().isEmpty()){   
                                label.setText("Enter a Supplier's ID");
                            }
                            else{
                                String title = titleText.getText();
                                String discription = discriptionText.getText();
                                int quantity = Integer.parseInt(integerText.getText());
                                double wholesale_price = Double.parseDouble(wholesale_priceText.getText());
                                double sales_price = Double.parseDouble(sales_priceText.getText());
                                String sid = sidText.getText();
                                label.setForeground(Color.green);
                                label.setText("Success!"); 
                                //another try/catch cuz the code got mad
                                try{
                                    Connection connection = DriverManager.getConnection(url, username, password);
                                    PreparedStatement upStmt1 = connection.prepareStatement("UPDATE new_inventory SET product_title = '" + title +"' WHERE product_id = '"+ record +"'");
                                    upStmt1.execute();
                                    PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET product_description = '" + discription +"' WHERE product_id = '"+ record +"'");
                                    upStmt2.execute();
                                    PreparedStatement upStmt3 = connection.prepareStatement("UPDATE new_inventory SET quantity = '" + quantity +"' WHERE product_id = '"+ record +"'");
                                    upStmt3.execute();
                                    PreparedStatement upStmt4 = connection.prepareStatement("UPDATE new_inventory SET wholesale_price = '" + wholesale_price +"' WHERE product_id = '"+ record +"'");
                                    upStmt4.execute();
                                    PreparedStatement upStmt5 = connection.prepareStatement("UPDATE new_inventory SET sale_price = '" + sales_price +"' WHERE product_id = '"+ record +"'");
                                    upStmt5.execute();
                                    PreparedStatement upStmt6 = connection.prepareStatement("UPDATE new_inventory SET supplier_id = '" + sid +"' WHERE product_id = '"+ record +"'");
                                    upStmt6.execute();
                                    
                                }catch (SQLException se) {
                            
                                    System.out.println("oops, error!");
                                    se.printStackTrace();
                                } catch (InputMismatchException exception) {
                                    System.out.print(exception.getMessage()); //try to find out specific reason.
                                }
                                //THe record should have no erros at this point and user will go back to menu
                                System.out.println("Record has been successfully updated!");
                                cp.removeAll();
                                displayMenue();
                            }
                        
                        }
                    });//end of the yes listener
            }
            }
        });

        panelTwo.add(submitButton);
        cp.add(panelOne);
        cp.add(panelTwo);
        cp.add(panelThree);
        cp.add(panelFour);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
        setTitle("Update Record"); // "super" JFrame sets title
        setSize(300, 175);         // "super" JFrame sets initial size
        setVisible(true);          // "super" JFrame shows

    }//END Update Record






   public void getLogin(){
       JTextField urlText, usernameText, passwordText;
       JLabel label = new JLabel();

    // Reference for container: https://www3.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
    // Reference for dropdown: https://www3.ntu.edu.sg/home/ehchua/programming/java/J4a_GUI_2.html
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        cp.add(new JLabel("Enter MySql Credentials:"));

        JPanel tfPanel = new JPanel(new GridLayout(3, 2));
        tfPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        cp.add(tfPanel);

        tfPanel.add(new JLabel("Url"));
        urlText = new JTextField(10);
        tfPanel.add(urlText);
        tfPanel.add(new JLabel("Username"));
        usernameText = new JTextField(10);
        tfPanel.add(usernameText);
        tfPanel.add(new JLabel("Password"));
        passwordText = new JPasswordField(10);
        tfPanel.add(passwordText);

        final JButton submitSelection= new JButton("Submit");
        cp.add(submitSelection);

        label.setFont(new Font("Serif", Font.BOLD, 13));
        label.setForeground(Color.red);
        label.setOpaque(true);
        cp.add(label);

        // Allocate an anonymous instance of an anonymous inner class that
        // implements ActionListener as ActionEvent listener
        submitSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            //This will reset the label every time that the button is pressed
            label.setText("");

            // Getting url, username and password from text fields


            if (urlText.getText() == null || urlText.getText().length() < 1){
                label.setText("Please enter a valid url!");
            }
            else if (usernameText.getText()== null || usernameText.getText().length() < 1){
                label.setText("Please enter a valid username!");
            }
            else if (passwordText.getText() == null || passwordText.getText().length() < 1){
                label.setText("Please enter a valid password!");
            }
            else{
                url = urlText.getText();
                username = usernameText.getText();
                password = passwordText.getText();
                //System.out.println(url + "\n" + username + "\n" + password);
                }
                cp.removeAll();
                displayMenue();
            }
        });


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
        setTitle("Credentials:"); // "super" JFrame sets title
        setSize(300, 225);         // "super" JFrame sets initial size
        setVisible(true);          // "super" JFrame shows

        // Wait until a selection is made before returning
        // reference https://stackoverflow.com/questions/20069374/java-swing-main-class-wait-until-jframe-is-closed

    }//END GET LOGIN


    public void searchRecords(){
        // Retrieve the content-pane of the top-level container JFrame
        // All operations done on the content-pane
        JRadioButton pid, title, sid;
        JTextField searchPid, searchTitle, searchSid;  // Use Swing's JTextField instead of AWT's TextField
        JButton submitButton;    // Using Swing's JButton instead of AWT's Button
  
        //DISCRIPTION_SELECTION = 3;
        //WHOLESALE_SELECTION = 4;
        //SALES_SELECTION = 5;
  
  
  
  
        
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        //This panel will make so it will make a new row for the textFields
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setVisible(true);
  
        searchPid = new JTextField(10);
        searchPid.setEditable(false);
        searchTitle = new JTextField(10);
        searchTitle.setEditable(false);
        searchSid = new JTextField(10);
        searchSid.setEditable(false);
        panel.add(searchPid);
        panel.add(searchTitle);
        panel.add(searchSid);
      
        
        pid = new JRadioButton("Product ID");
        pid.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              PID_SELCTION = true;
              TITLE_SELECTION =  false;
              SID_SELECTION = false;
              searchPid.setEditable(true);
              searchSid.setEditable(false);
              searchTitle.setEditable(false);
          }
       });
        cp.add(pid);
  
        title = new JRadioButton("Product Title");
        title.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              PID_SELCTION = false;
              TITLE_SELECTION = true;
              SID_SELECTION = false;
              searchPid.setEditable(false);
              searchSid.setEditable(false);
              searchTitle.setEditable(true);
          }
       });
        cp.add(title);
  
        sid = new JRadioButton("Supplier's ID");
        sid.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              PID_SELCTION = false;
              TITLE_SELECTION =  false;
              SID_SELECTION = true;
              searchPid.setEditable(false);
              searchSid.setEditable(true);
              searchTitle.setEditable(false);
          }
       });
        cp.add(sid);
        cp.add(panel);
       //This makes it so only one radio button can be slected at a time
        ButtonGroup btnGp = new ButtonGroup();
        btnGp.add(pid);
        btnGp.add(title);
        btnGp.add(sid);
  
          //Jbutton  
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              String record = null;
             if(PID_SELCTION && searchPid.getText() != null){
                 record = searchPid.getText();
                 printResults(1, record);
                 
             }
             else if(TITLE_SELECTION && searchTitle.getText() != null){
                 record = searchTitle.getText();
                 printResults(2, record);
             }
             else if(SID_SELECTION && searchSid.getText() != null){
                 record = searchSid.getText();
                 printResults(5, record);
              }
  
              cp.removeAll();
              displayMenue();
  
              //System.out.println(record + " " + PID_SELCTION + TITLE_SELECTION + SID_SELECTION);
          }
       });
        cp.add(submitButton);
   
        // Allocate an anonymous instance of an anonymous inner class that
        //  implements ActionListener as ActionEvent listener
        submitButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt) {
  
           }
        });
   
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
        setTitle("Search Records"); // "super" JFrame sets title
        setSize(400, 150);         // "super" JFrame sets initial size
        setVisible(true);          // "super" JFrame shows
  
      }//END SEARCH RECORDS
  
      public void printResults(int fieldtype, String itemLookup){
  
          String query = "product_id";//default
  
          switch(fieldtype){
              case 1: query = "product_id";
                      break;
              case 2: query = "product_title";
                      break;
              case 3: query = "product_description";
                      break;
              case 4: query = "quantity";
                      break;
              case 5: query = "supplier_id";
                      break;
              case 6: query = "wholesale_price";
                      break;
              case 7: query = "sale_price";
                      break;
              default: query = "product_id";
                      break;
  
          }
          try {
              System.out.println("sreaching for a " + query +" that has " + itemLookup);
              Connection connection = DriverManager.getConnection(url, username, password);
              PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_inventory WHERE " + query + "= '" + itemLookup + "'", ResultSet.TYPE_SCROLL_SENSITIVE, 
                      ResultSet.CONCUR_UPDATABLE);
      
          
              ResultSet myRs = myStmt.executeQuery();
              
              if (myRs.next()) {
                  
                  myRs.beforeFirst();
              
              
                  while (myRs.next()) {
                      
                      System.out.println();
                      System.out.println("Product ID: " + myRs.getString("product_id"));
                      System.out.println("Product Title: " + myRs.getString("product_title"));
                      System.out.println("Product Description: " + myRs.getString("product_description"));
                      System.out.println("Quantity: " + myRs.getInt("quantity"));
                      System.out.println("Wholesale: " + myRs.getString("wholesale_price"));
                      System.out.println("Sale Price: " + myRs.getString("sale_price"));
                      System.out.println("Supplier ID: " + myRs.getString("supplier_id"));
                      System.out.println();
                  }
              }
              else {
                  System.out.println("No record found");
                  System.out.println();
              }
              
          } catch (SQLException e) {
              System.out.println("oops, error!");
              e.printStackTrace();
          }
      }//END PRINT RESULTS

    public void createRecord() {
        // Private variables of the GUI components
        JTextField titleText, discriptionText, IntegerText, wholesale_priceText, sales_priceText, sidText;
        JLabel label = new JLabel();

        //These also have getter methods that we can extrate the values from the GUI

        
        JPanel tfPanel = new JPanel(new GridLayout(6, 2, 10, 2));//6 rows and 2 columns
        tfPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        JButton button = new JButton("Add Record");

        //(Row 1)
        tfPanel.add(new JLabel("Product Title"));
        titleText = new JTextField(20);
        tfPanel.add(titleText);

        //(Row 2)
        tfPanel.add(new JLabel("Product Discription"));
        discriptionText = new JTextField(30);
        tfPanel.add(discriptionText);

        //(Row 3)
        tfPanel.add(new JLabel("Quantity"));
        IntegerText = new JTextField(8);
        tfPanel.add(IntegerText);

        //(Row 4)
        tfPanel.add(new JLabel("Wholesale Price"));
        wholesale_priceText = new JTextField(8);
        tfPanel.add(wholesale_priceText);

        //(Row 5)
        tfPanel.add(new JLabel("Sales Price"));
        sales_priceText = new JTextField(8);
        tfPanel.add(sales_priceText);

        //(Row 6)
        tfPanel.add(new JLabel("Supplier's ID"));
        sidText = new JTextField(8);
        tfPanel.add(sidText);

        //This is another pannel just for the button that gets out of the grid layout
        JPanel btnPanel = new JPanel();
        btnPanel.setLocation(100, 200);
        btnPanel.add(button);
        label.setFont(new Font("Serif", Font.BOLD, 13));
        label.setForeground(Color.red);
        label.setOpaque(true);
        btnPanel.add(label);

        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout(5, 5));


        button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //This will reset the label every time that the button is pressed
            label.setForeground(Color.red);
            label.setText("");


            //This will check if the Integer ID is a number
            if(titleText.getText().isEmpty()){   
                label.setText("Enter a Product Title!!!");
            }
            else if(discriptionText.getText().isEmpty()){   
                label.setText("Enter a Product Discription!!!");
            }
            else if(!checkInteger(IntegerText.getText())){
                label.setText("Invalid Integer!");
            }
            else if(IntegerText.getText().length() > 8){
                label.setText("Integer is too Big");
            }
            else if(IntegerText.getText().isEmpty()){
                label.setText("Enter a Quantity");
            }
            else if(wholesale_priceText.getText().isEmpty()){
                label.setText("Enter Wholesales Price");
            }
            else if(checkDouble(wholesale_priceText.getText()) == false){
                label.setText("Invalid Wholesale Price");
            }
            else if(sales_priceText.getText().isEmpty()){
                label.setText("Enter a Sales Price");
            }
            else if(!checkDouble(sales_priceText.getText())){
                label.setText("Invalid Sales Price"); 
            }
            //These will make sure that the string Text Fields are not blank
            else if(sidText.getText().isEmpty()){   
                label.setText("Invalid Supplier ID!!!");
            }
            else{
                String title = titleText.getText();
                String discription = discriptionText.getText();
                int quantity = Integer.parseInt(IntegerText.getText());
                double wholesale_price = Double.parseDouble(wholesale_priceText.getText());
                double sales_price = Double.parseDouble(sales_priceText.getText());
                String sid = sidText.getText();
                
                Connection connection = null;

                try {
                    connection = DriverManager.getConnection(url, username, password);
                    String query = " INSERT INTO new_inventory (product_title, product_description, quantity, sale_price, wholesale_price, supplier_id)" +
                            " VALUES (?, ?, ?, ?, ?, ?)";
        
                    PreparedStatement prepStmt = connection.prepareStatement(query);
                    prepStmt.setString (1, title);
                    prepStmt.setString (2, discription);
                    prepStmt.setInt (3, quantity);
                    prepStmt.setDouble (4, sales_price);
                    prepStmt.setDouble (5, wholesale_price);
                    prepStmt.setString (6, sid);
                    prepStmt.execute();
                    System.out.println("Success!");
                
                    /* For testing purposes
                    System.out.println(title);
                    System.out.println(discription);
                    System.out.println(quantity);
                    System.out.println(sales_price);
                    System.out.println(wholesale_price);
                    System.out.println(sid);
                    */
        
                } catch (SQLException se) {
        
                    // TODO Auto-generated catch block
                    System.out.println("oops, error!");
                    se.printStackTrace();
                } catch (InputMismatchException ex) {
                    System.out.print(ex.getMessage()); //try to find out specific reason.
                }
        
        
                try {
                    if(connection!=null)
                        connection.close();
                }catch(SQLException se) {
                    se.printStackTrace();
                }
                cp.removeAll();
                displayMenue();
        }

          /* For TESTING: this is the data that the GUI is getting
          
          System.out.println(title);
          System.out.println(discription);
          System.out.println(Integer);
          System.out.println(wholesale_price);
          System.out.println(sales_price);
          System.out.println(sid);
          */
        }
   });
   

        // Setup the content-pane of JFrame in BorderLayout
        cp.add(tfPanel, BorderLayout.NORTH);
        cp.add(btnPanel, BorderLayout.PAGE_END);
        //cp.add(tAreaScrollPane, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Create Record");
        setSize(350, 275);
        setVisible(true);

    }//END CREATE RECORD



   public static void main(String[] args) {
      // Run the GUI construction in the Event-Dispatching thread for thread-safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new InventoryDataBase(); // Let the constructor do the job
         }
      });
   }





   /**************************************
    * These methods checks to make sure that we are only getting integers and doubles
    * The parsing wont work otherwise
    ************************************/

   public boolean checkInteger(String string){
    boolean valid = true;
    for(int i = 0; i<string.length(); i++){
        char a = string.charAt(i);
        if(!Character.isDigit(a)){
            valid = false;
            break;
        }
        
    }
return valid;
}//END CHECK INT

public boolean checkDouble(String string){
   /*This will check to see if there is only numbers and/or 1 period */
    boolean valid = true;
    char chr;
    //This is needed to because characters don't like being compared nicely
    String dot = ".";
    int dotcounter = 0;
    for(int i = 0; i< string.length(); i++){  
        chr = string.charAt(i);
        String str = string.substring(i,i+1);
        if(!Character.isDigit(chr)){
            if(!str.equals(dot)){
                valid = false;
                break;
            }
            else{
                dotcounter++;
                if(dotcounter > 1){
                    valid = false;
                    break;
                }
                continue;
            }   
        }
    }
    return valid;
}//END CHECK DOUBLE


public void loadFile() throws FileNotFoundException {
    Container cp = new Container();
    cp.setLayout(new GridLayout(2,1));

    JPanel panelOne = new JPanel();
    panelOne.setLayout(new FlowLayout());
    panelOne.setBorder(BorderFactory.createEmptyBorder(10, 2, 0, 2));
    JPanel panelTwo = new JPanel();
    panelTwo.setLayout(new FlowLayout());
    panelTwo.setBorder(BorderFactory.createEmptyBorder(0, 2, 5, 2));
    JLabel label = new JLabel("Enter the file's name");
    panelOne.add(label);
    //What is on the secound panel
    JTextField userFile = new JTextField(8);
    panelTwo.add(userFile);
    JButton submit = new JButton("Submit");
    submit.setVisible(true);
    panelTwo.add(submit);

    cp.add(panelOne);
    cp.add(panelTwo);
    add(cp); //adding container to the super frame
    
    submit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {

            System.out.println(FILE_NAME);
        try {
            Scanner in = new Scanner(new FileInputStream(FILE_NAME));
            //This will get past the first line that is just the titles of the collums and not data
            String titiles = in.nextLine();
            System.out.println(titiles);
            //This loop will extrapulate the data fron each line and create the entryItem with the line's data
            while(in.hasNextLine()) {
                String line = in.nextLine();
                int end = line.indexOf(",", 0);
                String product_title = line.substring(0, end);

                int start = end + 1;
                end = line.indexOf(",", start);
                String product_discription = line.substring(start, end);

                start = end + 1;
                end = line.indexOf(",", start);
                String tempQuantity = line.substring(start, end);
                int quantity = Integer.parseInt(tempQuantity);

                start = end + 1;
                end = line.indexOf(",", start);
                String tempWholesale_cost = line.substring(start, end);
                double wholesale_cost = Double.parseDouble(tempWholesale_cost);

                start = end + 1;
                end = line.indexOf(",", start);
                String tempSale_price = line.substring(start, end);
                double sale_price = Double.parseDouble(tempSale_price);

                String supplier_id = line.substring(end + 1);
                
                Connection connect = null;
                try {

                    connect = DriverManager.getConnection(url, username, password);

                    String query = " INSERT INTO new_inventory (product_title, product_description, quantity, sale_price, wholesale_price, supplier_id)" +
                            " VALUES (?, ?, ?, ?, ?, ?)";
        
                    PreparedStatement prepStmt = connect.prepareStatement(query);
                    prepStmt.setString (1, product_title);
                    prepStmt.setString (2, product_discription);
                    prepStmt.setInt (3, quantity);
                    prepStmt.setDouble (4, sale_price);
                    prepStmt.setDouble (5, wholesale_cost);
                    prepStmt.setString (6, supplier_id);
        
                    //Test
                    System.out.println(product_title);
                    System.out.println(product_discription);
                    System.out.println(quantity);
                    System.out.println(sale_price);
                    System.out.println(wholesale_cost);
                    System.out.println(supplier_id);
        
                    prepStmt.execute();
        
                } catch (SQLException se) {
        
                    // TODO Auto-generated catch block
                    System.out.println("oops, error!");
                    se.printStackTrace();
                } catch (InputMismatchException e) {
                    System.out.print(e.getMessage()); //try to find out specific reason.
                }
                try {
                    if(connect!=null)
                        connect.close();
                }catch(SQLException se) {
                    se.printStackTrace();
                }

        }
        in.close();
        cp.removeAll();
        displayMenue();

         }catch (FileNotFoundException exception) { }
    }
    });
    
    setSize(250,130);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Load File");

}//END loadFile


}//FIN!!!
