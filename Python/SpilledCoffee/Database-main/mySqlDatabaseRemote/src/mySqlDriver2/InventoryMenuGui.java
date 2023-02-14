package mySqlDatabaseRemote.src.mySqlDriver2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InventoryMenuGui extends JFrame {
    private String menuSelection;

    public InventoryMenuGui(){

    }

    public void displayGuiMenu() {
        final InventoryMenuGui frame = this;

        // Reference for container: https://www3.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html
        // Reference for dropdown: https://www3.ntu.edu.sg/home/ehchua/programming/java/J4a_GUI_2.html

        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());

        // Create JComboBox for getting the prompt to run
        cp.add(new JLabel("Select a prompt:"));

        final String[] prompts = {"Create a new record", "Look up a record", "Update a record", "Delete an existing record"};
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

        final JButton submitSelection= new JButton("Submit");
        cp.add(submitSelection);

        // Allocate an anonymous instance of an anonymous inner class that
        // implements ActionListener as ActionEvent listener
        submitSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // stop waiting and let method return
                synchronized(frame){
                    frame.notify();
                }

                // When submit button is clicked, close the menu
                frame.setVisible(false);
                frame.dispose(); // Close the menu after selection
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
        setTitle("Menu:"); // "super" JFrame sets title
        setSize(350, 225);         // "super" JFrame sets initial size
        setVisible(true);          // "super" JFrame shows

        // Wait until a selection is made before returning
        // reference https://stackoverflow.com/questions/20069374/java-swing-main-class-wait-until-jframe-is-closed
        synchronized(frame){
            try{
                frame.wait();
            }
            catch(InterruptedException ex)
            {

            }
        }
    }

    public String getMenuSelection(){
        return menuSelection;
    }

}
