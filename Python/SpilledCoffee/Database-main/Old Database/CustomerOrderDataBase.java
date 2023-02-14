/**
 * The finance, marketing, and sales departments want to understand customers better but need the historical data to do so.
 * They want to have a database/pseudo-DB that stores all of the customer order
 * information along with the time and date of their orders.
 * This database will grow each simulated day.
 *
 * This will program will function as a database that the employee is able to:
 *
 * **View the inventory of the product**
 *   -- This will allow the user to look product inventory
 *
 * **Adding the order of the product
 *   -- This will add in a new entry into the database.
 *
 * **Updating the order
 *   -- Will update the entry within the database.
 *
 * **Deleting the order of the product
 *   -- Will be able to delete an entry in the database.
 *
 *
 * **Viewing the orders made within the database
 *   -- Will search the database by finding the product ID and user_email
 *   -- View the current order
 *
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CustomerOrderDataBase {


    private Scanner console;
    private ArrayList<OrderItem> orderInfo;
    private static String FILE_NAME = "customer_orders_team1.csv";


    public CustomerOrderDataBase() {
        orderInfo = new ArrayList<>(4000000);
        console = new Scanner(System.in);
        try {
            loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //This new constuctor will be used in InventoryUpdater to allow a user to use the load method but with thier own file name
    public CustomerOrderDataBase(String file) {
        this.FILE_NAME = file;
        orderInfo = new ArrayList<>(4000000);
        console = new Scanner(System.in);
        try {
            loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //A getter for the array list
    public ArrayList<OrderItem> getOrderArray(){return orderInfo;}

    public void loadFile() throws FileNotFoundException {
        try {
            Scanner in = new Scanner(new FileInputStream(FILE_NAME));
            //This will get past the first line that is just the titles of the collums and not data
            String titles = in.nextLine();
            //Test System.out.println(titles);

            //This loop will extrapolate the data from each line and create the entryItem with the line's data
            while (in.hasNextLine()) {
                String[] parsedLine = in.nextLine().split(",");
                String date = parsedLine[0];
                String cust_email = parsedLine[1];
                String cust_location = parsedLine[2];
                String product_id = parsedLine[3];
                int product_quantity = Integer.parseInt(parsedLine[4]);

                OrderItem orderItem = new OrderItem(date, cust_email, cust_location, product_id, product_quantity);
                orderInfo.add(orderItem);
                //TEST System.out.println(orderItem.toString());
            }
                in.close();


        } catch (FileNotFoundException e) {
        }
        //TEST System.out.print(orderInfo.size());

    }

    //This will take all the elemnts in the array and save them back onto the CSV file
    public void saveFile() throws FileNotFoundException {
        try {
            PrintWriter out = new PrintWriter(FILE_NAME);
            //This puts back the labels that the loadFile removed
            out.println("date,cust_email,cust_location,product_id,product_quantity");
            int i = 0;

            while (i < orderInfo.size()) {
                String saved = orderInfo.get(i).toString();
                out.println(saved);
                i++;
            }
            out.close();
        } catch (FileNotFoundException e) {
        }

    }

    public static void main(String[] args) throws IOException {

        //Initializing the constructor
        CustomerOrderDataBase custOrder = new CustomerOrderDataBase();
        custOrder.displayMenue(); // Calls the Display Menu Method
        custOrder.saveFile(); // Saves changes to the file at the end- when program ends


        /*If the load isn't working try running the saveFile method.
        The save file will create a new file has the proper name and location in your computer to run.
        Then just copy and past the data into the new file that it creates.
        custOrder.saveFile();


         */
    }



    public void displayMenue(){
        boolean quit = false;
        System.out.println("Welcome to the Customer Order Data Base!");
        System.out.println("----------------------------------------");
        System.out.println();
        System.out.println("Please type in the corresponding letter to proceed.");
        System.out.println();
        while (!quit) {
            // Printing out prompts to the user
            System.out.print("a.    Create a new order\n" +
                    "b.    View an order\n" +
                    "c.    Update an order\n" +
                    "d.    Delete an order\n" +
                    "f.    Quit\n");


            //This will receive the user input and process the correct char to
            //the correct if statement to proceed to the methods
            String input = console.next();
            if (input.contains("a")) {
                addOrder();
            }
            else if (input.contains("b")) {
                viewOrder();
            }
            else if (input.contains("c")) {
                updateOrder();
            }
            else if (input.contains("d")) {
                deleteOrder();
            }
            else if(input.contains("f")){ ;
                quit = true;
            }
            else{
                System.out.println("Invalid selection");
                System.out.println("Please type the corresponding letter next to the option you want.");
            }
        }
    }//FIN


    //This method will have the employee add in customer data into the database
    private void addOrder() {
        // Variables
        String date = null, cust_email = null, cust_location = null, product_id = null;
        int quantity = 0;

        // Date-Time Format "YYYY-MM-DD"
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        boolean date_Valid = false;

        // Separator for user readability
        String s = "----------------------------------------"; // separator

        boolean user_confirmed = false;
        while (!user_confirmed) {

            // module header
            System.out.println(s);
            System.out.println("Adding Order: ");
            System.out.println(s);

            // Getting the user date for entry
            System.out.print("Enter Date(YYYY-MM-DD): ");
            date = console.next();
            //This while loop will check if the date is valid
            while (!date_Valid) {
                try {
                    LocalDate.parse(date, dateFormatter);
                    System.out.println("Validated Date");
                    date_Valid = true;
                } catch (DateTimeParseException e) {
                    date_Valid =  false;
                    System.out.println("Invalid Date");
                    System.out.print("Enter valid date(YYYY-MM-DD):");
                    date = console.next();
                }
            }

            // Getting user email
            System.out.print("Enter customer email: ");
            cust_email = console.next();
            boolean flag = false;
            int countr = 0, countd = 0;
            while(!flag) {
                //This loop will check if the email is valid
                for (int i = 0; i < cust_email.length(); i++) {
                    if (cust_email.charAt(i) == '@') {
                        countr++;
                        if (countr > 1) {
                            flag = false;
                            break;
                        }
                        if (i >= 1) flag = true;
                        else {
                            flag = false;
                            break;
                        }

                    }
                    if (cust_email.charAt(i) == '.') {
                        countd++;
                        if (countd > 1) {
                            flag = false;
                            break;
                        }
                        if (i >= 3) flag = true;
                        else {
                            flag = false;
                            break;
                        }
                    }
                    if (cust_email.indexOf(".") - cust_email.indexOf("@") >= 2) {
                        flag = true;
                    }
                    if (!flag) break;
                }
                if (flag && cust_email.length() >= 5) {
                    System.out.println("Validated Email");
                    break;
                } else {
                    System.out.println("Invalid Email");
                }
            }

            //Validate the customer ZIP code
            System.out.print("Enter ZIP Code: ");
            cust_location = console.next();
            while((cust_location.length()) != 5){
                System.out.println("ZIP Code must be 5 characters long");
                System.out.print("Enter ZIP Code: ");
                cust_location = console.next();
            }

            // Validate product id
            System.out.print("Enter Product ID: ");
            product_id = console.next();
            while ((product_id.length()) != 12) {
                System.out.println("Product ID must be 12 characters long!");
                System.out.print("Enter Product ID: ");
                product_id = console.next();
            }

            // Validate quantity
            System.out.print("Enter Quantity: ");
            while (!console.hasNextInt()) {
                System.out.println("Quantity must be a whole number!");
                System.out.print("Enter Quantity: ");
                console.next();
            }
            quantity = console.nextInt();

            //Confirming Entries
            System.out.println(s);
            System.out.println("You entered the following values:");
            System.out.println(s);
            System.out.printf("%-11s %-20s %-20s  %-18s %-11s\n", "|DATE:|", "|CUSTOMER EMAIL:|", "|CUSTOMER LOCATION:|", "|PRODUCT ID:|", "|QUANTITY:|");
            System.out.printf("%-11s %-20s %-20s  %-18s %11s\n", date, cust_email, cust_location, product_id, quantity);
            System.out.println(s);
            System.out.println("Is this correct?");
            System.out.print("Type 'yes' to add this record, type 'no' to start over: ");
            String inp = console.next();
            boolean validated = false;
            while (validated == false) {
                if (inp.toLowerCase().equals("yes")) {
                    validated = true;
                    user_confirmed = true;
                }
                else if (inp.toLowerCase().equals("no")) {
                    validated = true;

                }
                else {
                    System.out.print("Invalid response. Please type 'yes' or 'no': ");
                    inp = console.next();
                }
            }
        }
        OrderItem newItem = new OrderItem(date, cust_email, cust_location, product_id, quantity);
        orderInfo.add(newItem);

        // alert user and get next step
        System.out.println(s);
        System.out.println("Entry added to Data Base!");
        System.out.println(s);
        System.out.println("Do you want to add another entry?");
        System.out.print("Type 'yes' to add another entry, or 'no' to exit to main menu: ");
        String inp = console.next();
        boolean valid = false;
        while (valid == false) {
            if (inp.toLowerCase().equals("yes")) {
                valid = true;
                addOrder();
            } else if (inp.toLowerCase().equals("no")) {
                valid = true;                                       // possibly direct to main menu later
            } else {
                System.out.print("Invalid response. Please type 'yes' or 'no': ");
                inp = console.next();
            }
        }
    }
    //This method will update the current entry within the database
    private void updateOrder() {
        System.out.println("----------------------------------------");
        System.out.println("          Update Order");
        System.out.println("----------------------------------------");

        //Prompt email address for the order to update.
        System.out.println("Enter the email address for the order you want to update: ");
        String customerEmail = console.next();

        //Searching OrderInfo for orders belonging to the email address.
        ArrayList<OrderItem> potentialOrderUpdate = new ArrayList<OrderItem>();
        for (int i=0; i<orderInfo.size(); i++ ){
            if (customerEmail.equalsIgnoreCase(orderInfo.get(i).getCustomerEmail())) {
                potentialOrderUpdate.add(orderInfo.get(i));
            }
        }

        //If no records for the given email address are found in orderInfo, return.
        if (potentialOrderUpdate.isEmpty()) {
            System.out.println("No orders found for email: " + customerEmail);
            return;
        }

        //Prompt the order to update.
        int recordIndex = -1;
        boolean validOrderNumber = false;
        while (!validOrderNumber){
            try{
                //Display all of the options and ask the user to select one to update.
                for(int i=0; i<potentialOrderUpdate.size(); i++){
                    System.out.println((i+1) + ". Product ID: " + potentialOrderUpdate.get(i).getProductId()+ "     "
                            + "Quantity: " + potentialOrderUpdate.get(i).getQuantity()+ "     "
                            + "Customer Email: " + potentialOrderUpdate.get(i).getCustomerEmail()+ "     "
                            + "Customer Zip Code: " + potentialOrderUpdate.get(i).getCustomerLocation()+ "     "
                            + "Order Date: " + potentialOrderUpdate.get(i).getOrderDate());
                }

                System.out.println("Enter order number you would like to update: ");
                recordIndex = Integer.parseInt(console.next())-1;

                if (potentialOrderUpdate.get(recordIndex) != null){
                    validOrderNumber = true;
                }
            }
            catch (Exception exception){
                System.out.println("Error! Order number is invalid.");
            }
        }

        //Display chosen order.
        System.out.println();
        System.out.println("You are updating order:");
        System.out.println("----------------------------------------");
        System.out.println( "Product ID: " + potentialOrderUpdate.get(recordIndex).getProductId()+ "     "
                + "Quantity: " + potentialOrderUpdate.get(recordIndex).getQuantity()+ "     "
                + "Customer Email: " + potentialOrderUpdate.get(recordIndex).getCustomerEmail()+ "     "
                + "Customer Zip Code: " + potentialOrderUpdate.get(recordIndex).getCustomerLocation()+ "     "
                + "Order Date: " + potentialOrderUpdate.get(recordIndex).getOrderDate());
        System.out.println("----------------------------------------");

        //Prompt the user for the attribute to update.
        System.out.println("Enter the attribute to update: ");
        System.out.println(" a.   Product ID \n b.   Quantity \n c.   Customer Email \n d.   Customer Zip Code \n e.   Order Date ");
        String attribute = console.next();

        //Prompt for new attribute value and update.
        if (attribute.equals("a")){
            System.out.println("Enter the new Product ID: ");
            String newProductId = console.next();
            potentialOrderUpdate.get(recordIndex).setProductId(newProductId);
        }
        else if(attribute.equals("b")) {
            System.out.println("Enter the new Quantity: ");
            int newQuantity = Integer.parseInt(console.next());
            potentialOrderUpdate.get(recordIndex).setQuantity(newQuantity);
        }
        else if(attribute.equals("c")) {
            System.out.println("Enter the new Customer Email: ");
            String newCustomerEmail = console.next();
            potentialOrderUpdate.get(recordIndex).setCustomerEmail(newCustomerEmail);
        }
        else if(attribute.equals("d")) {
            System.out.println("Enter the new Customer Zip Code: ");
            String newCustomerLocation = console.next();
            potentialOrderUpdate.get(recordIndex).setCustomerLocation(newCustomerLocation);
        }
        else if (attribute.equals("e")){
            System.out.println("Enter the new Order Date: ");
            String newOrderDate = console.next();
            potentialOrderUpdate.get(recordIndex).setOrderDate(newOrderDate);
        }
        else{
            System.out.println("Invalid attribute");
            return;
        }

        //Display updated order.
        System.out.println();
        System.out.println("Updated order values");
        System.out.println("----------------------------------------");
        System.out.println( "Product ID: " + potentialOrderUpdate.get(recordIndex).getProductId()+ "     "
                + "Quantity: " + potentialOrderUpdate.get(recordIndex).getQuantity()+ "     "
                + "Customer Email: " + potentialOrderUpdate.get(recordIndex).getCustomerEmail()+ "     "
                + "Customer Zip Code: " + potentialOrderUpdate.get(recordIndex).getCustomerLocation()+ "     "
                + "Order Date: " + potentialOrderUpdate.get(recordIndex).getOrderDate());
        System.out.println("----------------------------------------");
    }

    //This method will delete a specific order, specified by the user
    private void deleteOrder() {
        // Scanners for individual variables
        Scanner sc = new Scanner(System.in);
        Scanner productIdScanner = new Scanner(System.in);
        Scanner dateScanner = new Scanner(System.in);

        // Mirrors back to the user their chosen menu option (in this case to delete a specific order)
        System.out.println("\n" + "----------------------------------------------");
        System.out.println("        DELETE ORDER");
        System.out.println("----------------------------------------------");

        // Prompts user for E-mail on order
        System.out.println("Enter the E-mail associated with the Order you would like to Delete: ");
        String entered_email = sc.next(); // Reads user's input

        // If user's email does not contain "@" and ".com" then it's invalid,
        // notify user and ask for a valid e-mail address
        if (!entered_email.contains("@") && (!entered_email.contains(".com"))) {
            System.out.println("\nSorry you entered an invalid E-mail!");

            while(!entered_email.contains("@") || (!entered_email.contains(".com"))){
                System.out.println("Please enter a correct E-mail address: ");
                entered_email = sc.next(); // Reads user's input
            }
        }

        System.out.println("\nE-mail Entered: " + entered_email); // Mirrors back entered E-mail
        System.out.println("----------------------------------------------");

        // Create variables that will later hold requested specified information
        OrderItem itemSearchingFor = null;
        OrderItem combinationSearchingFor = null;

        System.out.println("* This is a list of all the orders associated with the E-Mail" +" (" + entered_email+ ")\n");

        // Search the array; if entered E-mail is found/exists, print appropriate list of all orders pertaining to that entered E-mail
        // Its purpose is to return the list of orders pertaining to the user's entered E-mail
        // iterate through "orderInfo" array
        for (int i = 0; i < orderInfo.size(); i++) {
            if (entered_email.equalsIgnoreCase(orderInfo.get(i).getCustomerEmail())) { // if E-mail is found, retrieve info
                itemSearchingFor = orderInfo.get(i); // itemSearchingFor is set to values

                // Prints appropriate values
                System.out.println("Date: " + itemSearchingFor.getOrderDate()
                        + ", Customer E-mail: " + itemSearchingFor.getCustomerEmail()
                        + ", Customer Location: " + itemSearchingFor.getCustomerLocation()
                        + ", Product ID: " + itemSearchingFor.getProductId()
                        + ", Product Quantity: " + itemSearchingFor.getQuantity());

                System.out.println("----------------------------------------------");

            }
        }

        // If no orders were found/returned based on the user's entered email,
        // then notify user and redirect to Main Menu Selection Screen
        if (itemSearchingFor == null) {
            System.out.println("        ! NOTHING FOUND/EMPTY !");
            System.out.println("\n* Please enter 'x' to be redirected to the Main Menu Selection Screen");
            System.out.println("--------------------------------------------");
            String exitAttribute = sc.next();
            if(exitAttribute.equalsIgnoreCase("x")) {
                return;
            }
            else{
                while(!exitAttribute.equalsIgnoreCase("x")) {
                    System.out.println("Sorry please enter 'x' to be redirected! ");
                    exitAttribute = sc.next();
                    if(exitAttribute.equalsIgnoreCase("x")) {
                        return;
                    }
                }
            }
        }

            // Method will now prompt user for more specific data from the order wanting to delete
            System.out.println("\nNow enter the date of the order you would like to delete"); // Prompts date from order
            System.out.println("Note: *** Date must be in this format: (yyyy-mm-dd) Ex: (2020-01-14) ***"); // Note to user regarding date format
            System.out.println("Please enter date now:");
            String entered_date = dateScanner.next(); // Reads in user's entered date

            // If entered date is less than or greater than 10 characters it's invalid so notify user,
            // also if it doesn't contain "-" (dashes) it's invalid
            if (!entered_date.contains("-") && entered_date.length() != 10){
                System.out.println("Invalid Date!"); // Error Message

                // Keep on asking user for a valid date, repeats until user provides a valid input
                while(!entered_date.contains("-") || entered_date.length() != 10) {
                    System.out.println("\n Please enter a valid Date (yyyy-mm-dd) Ex:(2020-01-14): "); // Prompts user for a valid date
                    entered_date = sc.next(); // Reads user's date
                }
            }

            System.out.println("Date Entered: " + entered_date); // Mirrors back entered date

            System.out.println("\nNow please enter the product id associated with the order you would like to delete: ");// Prompts product ID from order
            String entered_productId = productIdScanner.next(); // Reads in user's entered product ID

            // If product id is less than or greater than 12 characters it's invalid so notify user
            if (entered_productId.length() < 12 || entered_productId.length() > 12){
                System.out.println("Invalid entry: Must be 12 characters long."); // Error Message

                // Keep on asking user for a valid 12 character long product id, repeats until user provides a valid input
                while(entered_productId.length() != 12) {
                    System.out.println("\n Please enter a valid Product ID (12 character long): "); // Prompts user for a valid (12 character long) product id
                    entered_productId = sc.next(); // Reads user's product id
                }
            }

            System.out.println("Product ID Entered: " + entered_productId); // Mirrors back entered product ID

            // newly iterate through "orderInfo" array, again
            for (int j = 0; j < orderInfo.size(); j++) {
                if (entered_productId.equalsIgnoreCase(orderInfo.get(j).getProductId()) // if appropriate product ID, E-mail, and date matches an order, retrieve info
                        && entered_email.equalsIgnoreCase(orderInfo.get(j).getCustomerEmail())
                        && entered_date.equalsIgnoreCase(orderInfo.get(j).getOrderDate())) {
                    combinationSearchingFor = orderInfo.get(j); // combinationSearchingFor is set to the value

                    // Prints appropriate values
                    System.out.println("\nRequested Order to Delete: "+ "\n" + "----------------------------------------------");
                    System.out.println("Date: " + combinationSearchingFor.getOrderDate()
                            + ", Customer E-mail: " + combinationSearchingFor.getCustomerEmail()
                            + ", Customer Location: " + combinationSearchingFor.getCustomerLocation()
                            + ", Product ID: " + combinationSearchingFor.getProductId()
                            + ", Product Quantity: " + combinationSearchingFor.getQuantity());

                        System.out.println("----------------------------------------------");
                        // break;
                }
            }

            // If no information was found/exists based on order information entered from user, then notify user
            if (combinationSearchingFor == null) {
                System.out.println("\n" + "----------------------------------------------");
                System.out.println("Sorry, the order you are looking for was not found!");
                System.out.println("----------------------------------------------" + "\n");
            }

            // If correct information was found, then ask user if they would like to delete the order
            if(combinationSearchingFor != null) {
                System.out.println("\nAre you sure you want to delete this record?: 'yes' or 'no'");
                String deleteAttribute = sc.next(); // Read in user's answer

                //if user enters yes then the item is deleted
                if (deleteAttribute.equalsIgnoreCase("yes")) {
                    orderInfo.remove(combinationSearchingFor); // Removes order from array
                    System.out.println("\nRecord has been SUCCESSFULLY deleted."); // Message to let user know of deletion
                    System.out.println("");
                }
                //if user enters 'no' or anything else, then order will not be deleted
                else {
                    System.out.println("\nRecord was NOT deleted.\n");
                }
            }

            // Asks user if they would like to delete another order
            System.out.println("Would you like to search for another order to delete? ('yes'/'no')");
            String searchAttribute = sc.next();

            // if 'yes' then restarts deleteOrder() method again
            if (searchAttribute.equalsIgnoreCase("yes")) {
                System.out.println("\n");
                deleteOrder();
            }
            // Else if 'no',then exits the deleteOrder() method
            else if (searchAttribute.equalsIgnoreCase("no")) {
                System.out.println("\n");
                return;
            }
            // Else if other answer, shows an invalid response message and exits to main menu
            else if (searchAttribute != "no" || searchAttribute != "yes") {
                    System.out.println("Invalid Response! Now Exiting to Menu!\n");
            }
    } // END OF DELETE METHOD

    //This method will view the order specifically with the
    // date, customer_email, customer_location, productID
    private void viewOrder() {

        System.out.println();
        System.out.println("     View Orders");
        System.out.println();


        //asks user to select which variable they would like to search by
        Scanner scanner = new Scanner(System.in);

        System.out.println("What would you like to search by: ");
        System.out.println(" a.   Product ID \n b.   Customer Email \n c.   Customer Location \n d.   Order Date \n");
        String attribute = console.next();


        //if a is selected, enter the product id to search for
        if (attribute.equals("a")){
            System.out.println("Enter the product id you would like to search for: ");
            String searchProductId = console.next();
            OrderItem pIdSearchingFor = null;
            int count1 = 0;
            for (int i=0; i<orderInfo.size(); i++ ){
                if (searchProductId.equalsIgnoreCase(orderInfo.get(i).getProductId())) { // if product id is found print data
                	pIdSearchingFor = orderInfo.get(i); 
                    
                    
                    System.out.println();

                    System.out.println("Product ID: " + pIdSearchingFor.getProductId()+ "     "
                            + "Quantity: " + pIdSearchingFor.getQuantity()+ "     "
                            + "Customer Email: " + pIdSearchingFor.getCustomerEmail()+ "     "
                            + "Customer Zip Code: " + pIdSearchingFor.getCustomerLocation()+ "     "
                            + "Order Date: " + pIdSearchingFor.getOrderDate());
                    
        
        }
    
                else{
                    count1++;   //if product id is not found, add to count and compare to array size. If greater than or equal display product id not found
                    if(count1 >= orderInfo.size()){
                        System.out.println();
                        System.out.println("Product ID Not Found.");
                        System.out.println();
        }
}
            }
            //prompt to continue viewing orders or exit program
            System.out.println();
            System.out.println("Would you like to continue viewing orders? Enter yes or no: ");
            String cont = console.next();
            if(cont.equalsIgnoreCase("yes")){
                viewOrder();
            }

        }
        //if b is selected enter the customer email
        else if(attribute.equals("b")) {
            System.out.println("Enter the Customer Email you would like to search for: ");
            String searchCustomerEmail = console.next();
            OrderItem custSearchingFor = null;
            int count2 = 0;
            for (int j=0; j<orderInfo.size(); j++ ){
                if (searchCustomerEmail.equalsIgnoreCase(orderInfo.get(j).getCustomerEmail())) { // if customer email is found print data
                    custSearchingFor = orderInfo.get(j); 

        
                    System.out.println();

                    System.out.println("Product ID: " + custSearchingFor.getProductId()+ "     "
                            + "Quantity: " + custSearchingFor.getQuantity()+ "     "
                            + "Customer Email: " + custSearchingFor.getCustomerEmail()+ "     "
                            + "Customer Zip Code: " + custSearchingFor.getCustomerLocation()+ "     "
                            + "Order Date: " + custSearchingFor.getOrderDate());
                    
                 
            }       
                else{
                    count2++;   //if email is not found, add to count and compare to array size. If greater than or equal display email not found
                    if(count2 >= orderInfo.size()){
                        System.out.println();
                        System.out.println("Email Not Found.");
                        System.out.println();
                }
                        
            }
        }
            //prompt to continue viewing orders or exit program
            System.out.println();
            System.out.println("Would you like to continue viewing orders? Enter yes or no: ");
            String cont = console.next();
            if(cont.equalsIgnoreCase("yes")){
                viewOrder();
            }

        }
         //if c is selected enter the customer zip code
        else if(attribute.equals("c")) {
            System.out.println("Enter the Customer ZIP code you would like to search for: ");
            String searchCustomerLoc = console.next();
            OrderItem locSearchingFor = null;
            int count3 = 0;
            for (int j=0; j<orderInfo.size(); j++ ){
                if (searchCustomerLoc.equalsIgnoreCase(orderInfo.get(j).getCustomerLocation())) { // if zip code is found display data
                	locSearchingFor = orderInfo.get(j); 

                    
                    System.out.println();

                    System.out.println("Product ID: " + locSearchingFor.getProductId() + "     "
                            + "Quantity: " + locSearchingFor.getQuantity() + "     "
                            + "Customer Email: " + locSearchingFor.getCustomerEmail() + "     "
                            + "Customer Zip Code: " + locSearchingFor.getCustomerLocation() + "     "
                            + "Order Date: " + locSearchingFor.getOrderDate());

                }
                
            
                else{
                    count3++;   //if zip code is not found, add to count and compare to array size. If greater than or equal display zip code not found
                    if(count3 >= orderInfo.size()){
                        System.out.println();
                        System.out.println("Zip Code Not Found.");
                        System.out.println();
                }
        }
            }
            //prompt to continue viewing orders or exit program
            System.out.println();
            System.out.println("Would you like to continue viewing orders? Enter yes or no: ");
            String cont = console.next();
            if(cont.equalsIgnoreCase("yes")){
                viewOrder();
            }

    }

        //if d is selected enter the customer order date
        else if (attribute.equals("d")){
            System.out.println("Enter the date of the order you would like to search for in the format(Year-month-day e.g. 2020-01-13): ");
            String searchOrderDate = console.next();
            OrderItem dateSearchingFor = null;
            int count4 = 0;
            for (int k=0; k<orderInfo.size(); k++ ){
                if (searchOrderDate.equalsIgnoreCase(orderInfo.get(k).getOrderDate())) { // if date is found display data
                	dateSearchingFor = orderInfo.get(k); 

                    
                    System.out.println();

                    System.out.println("Product ID:     " + dateSearchingFor.getProductId()+ "     "
                            + "Quantity:        " + dateSearchingFor.getQuantity() + "     "
                            + "Customer Email:   " + dateSearchingFor.getCustomerEmail() + "     "
                            + "Customer Zip Code:       " + dateSearchingFor.getCustomerLocation() + "     "
                            + "Order Date:       " + dateSearchingFor.getOrderDate());}
                    
                
                
                else{
                    count4++;   //if date is not found, add to count and compare to array size. If greater than or equal display date not found
                    if(count4 >= orderInfo.size()){
                        System.out.println();
                        System.out.println("Order Date Not Found.");
                        System.out.println();
                    }
                
        }
            }
            //prompt to continue viewing orders or exit program
            System.out.println();
            System.out.println("Would you like to continue viewing orders? Enter yes or no: ");
            String cont = console.next();
            if(cont.equalsIgnoreCase("yes")){
                viewOrder();
            }
        }
    }
}
