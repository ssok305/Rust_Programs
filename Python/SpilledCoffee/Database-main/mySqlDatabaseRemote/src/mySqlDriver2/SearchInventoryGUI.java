package mySqlDriver2;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

// A Swing GUI application inherits from top-level container javax.swing.JFrame
public class SearchInventoryGUI extends JFrame {   // JFrame instead of Frame
    private JRadioButton pid, title, sid;
    private JTextField searchPid, searchTitle, searchSid;  // Use Swing's JTextField instead of AWT's TextField
    private JButton submitButton;    // Using Swing's JButton instead of AWT's Button
    private int fieldselection = 0;
    private String record;
 
    // Constructor to setup the GUI components and event handlers
    public SearchInventoryGUI() {
      // Retrieve the content-pane of the top-level container JFrame
      // All operations done on the content-pane
      
      Container cp = getContentPane();
      cp.setLayout(new FlowLayout());
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
            setFieldselection(1);
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
            setFieldselection(2);
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
            setFieldselection(5);
            searchPid.setEditable(false);
            searchSid.setEditable(true);
            searchTitle.setEditable(false);
        }
     });
      cp.add(sid);
      cp.add(panel);

      ButtonGroup btnGp = new ButtonGroup();
      btnGp.add(pid);
      btnGp.add(title);
      btnGp.add(sid);

      

 



      submitButton = new JButton("Submit");
      submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           if(fieldselection == 1 && searchPid.getText() != null){
               record = searchPid.getText();
               
           }
           else if(fieldselection == 2 && searchTitle.getText() != null){
               record = searchTitle.getText();
           }
           else if(fieldselection == 5 && searchSid.getText() != null){
               record = searchSid.getText();
            }
            //System.out.println(record + " " + fieldselection);
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
   }
 
   public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

public int getFieldselection() {
        return fieldselection;
    }

    public void setFieldselection(int fieldselection) {
        this.fieldselection = fieldselection;
    }

// The entry main() method
   public static void main(String[] args) {
      // Run the GUI construction in the Event-Dispatching thread for thread-safety
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new SearchInventoryGUI(); // Let the constructor do the job
         }
      });
   }
}
