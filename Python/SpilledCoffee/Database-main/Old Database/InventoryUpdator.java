/*
    This class will be able to update the inventory csv by order input. It will e able to do this in two ways
        1. reading input from an order file
        2. getting individual orders from user input

        It will will call on the DataBase class and use it's methods to help update records
 */

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;

public class InventoryUpdator {
    private DataBase dataBase;
    private CustomerOrderDataBase orderDB;
    private String FILE;
    private Scanner in;
    //Theses might be useful if we wanted to make a
    //receipt of all the orders that went through and all the orders that failed
    private ArrayList<OrderItem> validOrders, failedOrders;
    //These two arrays are dublicates of the data bases' arrays
    private ArrayList<OrderItem> currentOrders, orders;
    private ArrayList<EntryItem> inventory;
    private boolean product_idOK, stockOK;
    EntryItem item;

    public InventoryUpdator(){
        in = new Scanner(System.in);
        validOrders = new ArrayList<>(40000);
        failedOrders = new ArrayList<>(40000);

        //This loads the inventory database
        dataBase = new DataBase();
        inventory = dataBase.getRecordsArray();

        //this loads the Customer Order data base
        orderDB = new CustomerOrderDataBase();
        orders = orderDB.getOrderArray();
    }

    // Main method to call other methods
    public static void  main(String [] args) throws IOException{
        InventoryUpdator inventoryUpdator = new InventoryUpdator();
        inventoryUpdator.displayMenu();
    }

    //This will make a menu giving the user options to use either a file or place a single order
    public void displayMenu() throws FileNotFoundException {
        boolean quit = false;
        while (!quit) {
            // Printing out prompts to the user
            System.out.print("\na.    Place individual order\n" +
                    "b.    Place mass order\n" +
                    "c.    Quit\n" +
                    "Please enter an letter prompt to proceed. ");


            //This will receive the user input and process the correct char to
            //the correct if statement to proceed to the methods
            String input = in.next();
            if (input.contains("a")) {
                individualOrdering();
            }
            if (input.contains("b")) {
                massOrdering();
            }
            if (input.contains("c")) {
                quit = true;
            }

        }


    }

    /* This will load a file from the user
          -It calls on the CustomerOrderDataBase's load method.
          -The file name can be changed
          -There are no user prompts! :) (this keeps the code more flexible)
     */
    public void loadUserFile(String file){
        CustomerOrderDataBase temp = new CustomerOrderDataBase(file);
        currentOrders = temp.getOrderArray();
        //TESTING
        //System.out.println(currentOrders.size());
        //System.out.println(currentOrders.toString());
    }

    private void processOrder(OrderItem order) throws FileNotFoundException {
        // Validating product id
        if (!validateProductID(order.getProductId())){
            System.out.println("Product does not exist in inventory.");

            // Product id is invalid, adding order to failed orders
            failedOrders.add(order);

            return;
        }

        // Validating stock
        if (!validateStock(order.getQuantity())){
            if (item.getQuantity() == 0){
                System.out.println("The product: "+ order.getProductId() + "is out of stock.");

            }
            else{
                System.out.println("Insufficient stock for product: " + order.getProductId() + " Only " + item.getQuantity() + " remaining");
            }

            // Not enough stock for product, adding order to failed orders
            failedOrders.add(order);
            return;
        }

        // Order is valid, updating quantity
        int currentQuantity = item.getQuantity();
        item.setQuantity(currentQuantity - order.getQuantity());
        validOrders.add(order);
        orders.add(order);
        /*
        System.out.println();
        System.out.println("Order completed successfully. New inventory value for product: ");
        System.out.println("Product ID:        " + item.getProduct_id()
                + "\nQuantity:          " + item.getQuantity()
                + "\nWhole Sale Cost:   " + item.getWholesale_cost()
                + "\nSale Price:        " + item.getSale_price()
                + "\nSupplier ID:       " + item.getSupplier_id());

        */

    }


    private OrderItem GetOrder(){
        // Variables
        String date = null, cust_email = null, cust_location = null, product_id = null;
        int quantity = 0;
        LocalDate now = LocalDate.now();
        date = now + "";

        // Date-Time Format "YYYY-MM-DD"
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        boolean date_Valid = false;

        // Separator for user readability
        String s = "----------------------------------------"; // separator

        boolean user_confirmed = false;
        while (!user_confirmed) {
            //get date

            // Getting user email
            System.out.print("Enter customer email: ");
            cust_email = in.next();
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
                    cust_email = in.nextLine();
                }
            }

            //Validate the customer ZIP code
            System.out.print("Enter ZIP Code: ");
            cust_location = in.next();
            while((cust_location.length()) != 5){
                System.out.println("ZIP Code must be 5 characters long");
                System.out.print("Enter ZIP Code: ");
                cust_location = in.next();
            }

            // Validate product id
            System.out.print("Enter Product ID: ");
            product_id = in.next();
            while ((product_id.length()) != 12) {
                System.out.println("Product ID must be 12 characters long!");
                System.out.print("Enter Product ID: ");
                product_id = in.next();
            }

            // Validate quantity
            System.out.print("Enter Quantity: ");
            while (!in.hasNextInt()) {
                System.out.println("Quantity must be a whole number!");
                System.out.print("Enter Quantity: ");
                in.next();
            }
            quantity = in.nextInt();

            //Confirming Entries
            System.out.println(s);
            System.out.println("You entered the following values:");
            System.out.println(s);
            System.out.printf("%-11s %-20s %-20s  %-18s %-11s\n", "|DATE:|", "|CUSTOMER EMAIL:|", "|CUSTOMER LOCATION:|", "|PRODUCT ID:|", "|QUANTITY:|");
            System.out.printf("%-11s %-20s %-20s  %-18s %11s\n", date, cust_email, cust_location, product_id, quantity);
            System.out.println(s);
            System.out.println("Is this correct?");
            System.out.print("Type 'yes' to add this record, type 'no' to start over: ");
            String inp = in.next();
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
                    inp = in.next();
                }
            }
        }
        //This will add the order to the CustomerOrderDatabase once the order has been placed
        OrderItem item = new OrderItem(date, cust_email, cust_location, product_id, quantity);
        return item;
    }

    //This will check to see if there is a product with the same ID
    public boolean validateProductID( String product_id){
        item = dataBase.viewRecord(product_id);
        if(item == null){product_idOK = false;}
        else{product_idOK = true;}
        //TEST: System.out.println(item.toString());
        //TEST: System.out.println(product_idOK);
        return product_idOK;
    }

    //This will validate that there is enough items in stock to place the order
    public boolean validateStock(int quantity){
        //automatically making it false reduces the needs for checks
        stockOK = false;
        //If the product doesn't exist then there is not need to continue
        if(product_idOK){
            //this checks how much the EntryItem has;
            int stock = item.getQuantity();
            if(quantity <= stock) {
                stockOK = true;
            }
        }
        return stockOK;
    }
    public String getFILE(){
        Scanner user = new Scanner(System.in);
        System.out.println("Please enter the file's name");
        String file = user.nextLine();
        return file;
    }

    //This will create an interface with the user to upload their file
    public void massOrdering() throws FileNotFoundException {
        String s = "----------------------------------------"; // separator
        String file = getFILE();
        loadUserFile(file);
        for (int i = 0; i < currentOrders.size(); i++) {
            processOrder(currentOrders.get(i));
        }

        if(failedOrders.size() == 0){
            System.out.println(s);
            System.out.println("All orders were able to be placed!");
            System.out.println(s);
            System.out.println();
            System.out.println("Would you like to place these orders?");
        }
        else{
            System.out.println("Sorry. these orders were not able to be processed");
            System.out.println(s);
            System.out.println();
            printArray(failedOrders);
            System.out.println("These are able to be processed:");
            System.out.println(s);
            System.out.println();
            printArray(validOrders);
            System.out.println("Would you like to place these orders? 'yes' or 'no'");
        }
        Boolean good = false;
        String answer = in.nextLine();
        while(!good)

        if(answer.contains("y")){
            saveOrders();
            saveInventory();
            System.out.println(s);
            System.out.println("Orders have been placed.");
            System.out.println(s);
            good = true;
        }
        else if (answer.contains("n")){
            System.out.println("Ok, orders will not be placed. Good bye!");
            //Existing because it will be too difficult to try to delete order items out of array
            System.exit(1);
        }
        else{
            System.out.println("Please enter yes or no");
            answer = in.nextLine();
        }
    }

    //This will create an interface with the user to make a single order
    public void individualOrdering() throws FileNotFoundException {
        // Getting order information from user
        OrderItem order = GetOrder();
        // Processing order
        processOrder(order);
        saveInventory();
        saveOrders();
    }

    //This will print an array's data, each with their own line.
    public void printArray(ArrayList array){
        for (int i = 0; i < array.size() ; i++) {
            System.out.println(array.get(i).toString());
        }
    }

    public void saveInventory() throws FileNotFoundException {

        try {
            PrintWriter out = new PrintWriter("inventory_team1.csv");
            //This puts back the labels that the loadFile removed
            out.println("product_id,quantity,wholesale_cost,sale_price,supplier_id");
            int i = 0;

            while (i < inventory.size()) {
                String saved = inventory.get(i).toString();
                out.println(saved);
                i++;
            }
            out.close();
        } catch (FileNotFoundException e) { }
    }
    public void saveOrders() throws FileNotFoundException {
        try {
            PrintWriter out = new PrintWriter("customer_orders_team1.csv");
            //This puts back the labels that the loadFile removed
            out.println("date,cust_email,cust_location,product_id,product_quantity");
            int i = 0;

            while (i < orders.size()) {
                String saved = orders.get(i).toString();
                out.println(saved);
                i++;
            }
            out.close();
        } catch (FileNotFoundException e) { }
    }
}//FIN
