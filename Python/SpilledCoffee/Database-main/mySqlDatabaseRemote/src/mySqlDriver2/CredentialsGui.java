package mySqlDatabaseRemote.src.mySqlDriver2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CredentialsGui extends JFrame {
    private JTextField urlText, usernameText, passwordText;
    private String url, username, password;

    private JLabel label = new JLabel();

    public CredentialsGui(){

    }

    public void displayCredentialsGui() {
        final CredentialsGui frame = this;

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
                url = urlText.getText();
                username = usernameText.getText();
                password = passwordText.getText();

                if (url == null || url.length() < 1)
                {
                    label.setText("Please enter a valid url!");
                }
                else if (username == null || username.length() < 1)
                {
                    label.setText("Please enter a valid username!");
                }
                else if (password == null || password.length() < 1)
                {
                    label.setText("Please enter a valid password!");
                }
                else{
                    // stop waiting and let method return
                    synchronized(frame){
                        frame.notify();
                    }

                    // When submit button is clicked, close the menu
                    frame.setVisible(false);
                    frame.dispose(); // Close the menu after selection
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program if close-window button clicked
        setTitle("Credentials:"); // "super" JFrame sets title
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

    public String getUrl(){
        return url;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}

