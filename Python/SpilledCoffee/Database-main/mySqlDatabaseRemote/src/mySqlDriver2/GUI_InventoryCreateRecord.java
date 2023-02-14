/*
-This GUI will take in user input, but it will not store that input unless it a valid input
-It will send out messages to inform user that their input is invalid
-It also has a 'success' message for when all the input is valid
*/


package mySqlDriver2;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
 
@SuppressWarnings("serial")
public class GUI_InventoryCreateRecord extends JFrame {
 
   // Private variables of the GUI components
   JTextField titleText, discriptionText, quantityText, wholesale_priceText, sales_priceText, sidText;
   JFormattedTextField formattedField;
   private JLabel label = new JLabel();

   //These also have getter methods that we can extrate the values from the GUI
   String title, discription, sid;
   int quantity = -1;
   double wholesale_price = -1;
   double sales_price = -1.0;
   boolean valid = false;
   
 
   /* Constructor to set up all the GUI components */
   public GUI_InventoryCreateRecord() {
      
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
      quantityText = new JTextField(8);
      tfPanel.add(quantityText);

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


      button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //This will reset the label every time that the button is pressed
            label.setForeground(Color.red);
            label.setText("");

            //This will check if the quantity ID is a number
            if(titleText.getText().isEmpty()){   
                label.setText("Enter a Product Title!!!");
            }
            else if(discriptionText.getText().isEmpty()){   
                label.setText("Enter a Product Discription!!!");
            }
            else if(!checkQuantity(quantityText.getText())){
                label.setText("Invalid Quantity!");
            }
            else if(quantityText.getText().length() > 8){
                label.setText("Quantity is too Big");
            }
            else if(quantityText == null){
                label.setText("Enter a Quantity");
            }
            else if(wholesale_priceText == null){
                label.setText("Enter Wholesales Price");
            }
            else if(checkDouble(wholesale_priceText.getText()) == false){
                label.setText("Invalid Wholesale Price");
            }
            else if(sales_priceText == null){
                label.setText("Enter a Sales Price");
            }
            else if(!checkDouble(sales_priceText.getText())){
                label.setText("Invalid Sales Price"); 
            }
            //These will make sure that the string Text Fields are not blank
            else if(sidText.getText() == null){   
                label.setText("Invalid Supplier ID!!!");
            }
            else{
            title = titleText.getText();
            discription = discriptionText.getText();
            quantity = Integer.parseInt(quantityText.getText());
            wholesale_price = Double.parseDouble(sales_priceText.getText());
            sales_price = Double.parseDouble(sales_priceText.getText());
            sid = sidText.getText();
            label.setForeground(Color.green);
            label.setText("Success!");
            valid = true;
            dispose();
            }

            /* For TESTING */
            System.out.println(title);
            System.out.println(discription);
            System.out.println(quantity);
            System.out.println(wholesale_price);
            System.out.println(sales_price);
            System.out.println(sid);
            
        }
     });
     
 
      // Setup the content-pane of JFrame in BorderLayout
      Container cp = this.getContentPane();
      cp.setLayout(new BorderLayout(5, 5));
      cp.add(tfPanel, BorderLayout.NORTH);
      cp.add(btnPanel, BorderLayout.PAGE_END);
      //cp.add(tAreaScrollPane, BorderLayout.CENTER);
 
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("Create Record");
      setSize(350, 275);
      setVisible(true);
      //setForeground(new ColorUIResource(1, 2, 3));
   }
   //This will get the vlaues that the GUI has stored so we can use the inventory class to add those 
   //into MySQL
   public String getTitle(){return this.title;}
   public String getDiscription(){return this.discription;}
   public int getQuantity(){return this.quantity;}
   public double  getWholesale_price(){return this.wholesale_price;}
   public double getSales_price(){return this.sales_price;}
   public String getSid(){return this.sid;}
   public boolean getValid(){return this.valid;}
 
   
   public static void main(String[] args) {
      // Run GUI codes in Event-Dispatching thread for thread safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new GUI_InventoryCreateRecord();// Let the constructor do the job
         }
      });  
   }
   /**************************************
    * I don't know why but it wont go through the loop to check other characters
    * 
    ************************************/

   public boolean checkQuantity(String string){
        valid = true;
        for(int i = 0; i<string.length(); i++){
            char a = string.charAt(i);
            if(!Character.isDigit(a)){
                valid = false;
                break;
            }
            
        }
    return valid;
   }

   public boolean checkDouble(String string){
        valid = true;
        char chr;
        String dot = ".";
        int dotcounter = 0;
        for(int i = 0; i< string.length(); i++){  
            chr = string.charAt(i);
            //System.out.println(dot);
            String str = string.substring(i,i+1);
            System.out.println(str);
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
    }
}

   
