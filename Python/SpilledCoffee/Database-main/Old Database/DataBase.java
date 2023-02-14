/*
        * This program will load the CSV File and the program will prompt commands to the user
        * allowing the user to head to different methods to process CSV File.
        * It will get data from the CSV File and process the data depending on the user
        * With 'createRecord' it will create a new entry with the data file
        * 'lookUpRecord' will proceed to search for a specific record/entry
        * 'updateRecord' will update the current entry with the data file
'deleteRecord' will remove the current data entry from the data file
*/

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class DataBase {
    private Scanner console;   // This will receive data input from the keyboard of the user
    private ArrayList<EntryItem> records;
    private static String FILE_NAME = "inventory_team1.csv";
    // seperator for user readibility
    private String s = "----------------------------------------"; // separator

    public DataBase(){
        records = new ArrayList<>(400000);
        console = new Scanner(System.in);
        try {
            loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //A getter method for the array list
    public ArrayList<EntryItem> getRecordsArray(){return records;}
    
    /* This method will read the CSV file and break up the data contained in each line.
    Then it will create an entryItem with the data contained in each line and push that entryItem into the array - JS
    */
    public void loadFile() throws FileNotFoundException {
        try {
            Scanner in = new Scanner(new FileInputStream(FILE_NAME));
            //This will get past the first line that is just the titles of the collums and not data
            String titiles = in.nextLine();
            //This loop will extrapulate the data fron each line and create the entryItem with the line's data
            while(in.hasNextLine()) {
                String line = in.nextLine();
                int end = line.indexOf(",", 0);
                String product_id = line.substring(0, end);
                int start = end + 1;
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

                EntryItem entryItem = new EntryItem(product_id, quantity, wholesale_cost, sale_price, supplier_id);
                records.add(entryItem);
            }
            in.close();

        }catch (FileNotFoundException e) { }
        //TESTING PURPOSES
        //System.out.println(records.size());
        //System.out.println(records.get(75).toString());
    }
    //This will take all the elemnts in the array and save them back onto the CSV file
    public void saveFile() throws FileNotFoundException {
        try {
            PrintWriter out = new PrintWriter(FILE_NAME);
            //This puts back the lables that the loadFile removed
            out.println("product_id,quantity,wholesale_cost,sale_price,supplier_id");
            int i = 0;

            while(i < records.size()){
                String saved = records.get(i).toString();
                out.println(saved);
                i++;
            }
            out.close();
        } catch(FileNotFoundException e) {}

    }

    // Main method to call other methods
    public static void  main(String [] args) throws IOException{
        boolean quit = false;
        DataBase dataBase = new DataBase();
        while (!quit) {
            // Printing out prompts to the user
            System.out.print("a.    Create a new record\n" +
                    "b.    Look up a record\n" +
                    "c.    Update a record\n" +
                    "d.    Delete an existing record\n" +
                    "f.    Quit\n" +
                    "Please enter an letter prompt to proceed. ");


            //This will receive the user input and process the correct char to
            //the correct if statement to proceed to the methods
            String input = dataBase.console.next();
            if (input.contains("a")) {
                dataBase.createRecord();
            }
            if (input.contains("b")) {
                dataBase.lookUpRecord();
            }
            if (input.contains("c")) {
                dataBase.updateRecord();
            }
            if (input.contains("d")) {
                dataBase.deleteRecord();
            }
            if(input.contains("f")){
                dataBase.saveFile();
                quit = true;
            }
        }


    }


    // Method will create a new entry
    public void createRecord(){
        // module header
        System.out.println(s);
        System.out.println("Create Record");
        System.out.println(s);

        // user-defined variables
        String product_id = null, supplier_id = null;
        int quantity = 0;
        double wholesale_cost = 0, sale_price = 0;


        // loop for user inputs and validation, exits when user confirms entries
        boolean user_confirmed = false;
        while (!user_confirmed) {

            // get user inputs for all variables

            // validate product id
            System.out.print("Enter Product ID: ");
            product_id = console.next();
            while ((product_id.length()) != 12) {
                System.out.println("Product ID must be 12 characters long!");
                System.out.print("Enter Product ID: ");
                product_id = console.next();
            }

            // validate quantity
            System.out.print("Enter Quantity: ");
            while (!console.hasNextInt()) {
                System.out.println("Quantity must be a whole number!");
                System.out.print("Enter Quantity: ");
                console.next();
            }
            quantity = console.nextInt();

            // validate wholesale cost
            System.out.print("Enter Wholesale Cost: ");
            while (!console.hasNextDouble()) {
                System.out.println("Wholesale cost must be whole number or decimal!");
                System.out.print("Enter Wholesale Cost: ");
                console.next();
            }
            wholesale_cost = console.nextDouble();

            // validate sale price
            System.out.print("Enter Sale Price: ");
            while (!console.hasNextDouble()) {
                System.out.println("Sale price must be whole number or decimal!");
                System.out.print("Enter Sale Price: ");
                console.next();
            }
            sale_price = console.nextDouble();

            // validate supplier id
            System.out.print("Enter Supplier ID: ");
            supplier_id = console.next();
            while ((supplier_id.length()) != 8) {
                System.out.println("Supplier ID must be 8 characters long!");
                System.out.print("Enter Supplier ID: ");
                supplier_id = console.next();
            }

            // confirm entries with user
            System.out.println("You entered the following values:");
            System.out.println(s);
            System.out.println("Product ID:        " + product_id
                    + "\nQuantity:          " + quantity
                    + "\nWhole Sale Cost:   " + wholesale_cost
                    + "\nSale Price:        " + sale_price
                    + "\nSupplier ID:       " + supplier_id);
            System.out.println(s);
            System.out.println("Is this correct?");
            System.out.print("Type 'yes' to add this record, type 'no' to start over: ");
            boolean valid = false;
            while (!valid) {
                String inp = console.nextLine();
                if (inp.toLowerCase().equals("yes")) {
                    valid = true;
                    user_confirmed = true;
                    // create EntryItem object with user inputs
                    EntryItem newItem = new EntryItem(product_id, quantity, wholesale_cost, sale_price, supplier_id);
                    records.add(newItem);

                } else if (inp.toLowerCase().equals("no")) {
                    valid = true;
                } else {
                    System.out.print("\nInvalid response. Please type 'yes' or 'no': ");
                }
            }
        }


        // alert user and get next step
        System.out.println(s);
        System.out.println("Entry added to inventory!");
        System.out.println(s);
        System.out.println("Do you want to add another entry?");
        System.out.print("Type 'yes' to add another entry, or 'no' to exit to main menu: ");
        String inp = console.nextLine();
        boolean valid = false;
        while (!valid) {
            if (inp.toLowerCase().equals("yes")) {
                valid = true;
                createRecord();
            } else if (inp.toLowerCase().equals("no")) {
                valid = true;                                       // possibly direct to main menu later
            } else {
                System.out.print("Invalid response. Please type 'yes' or 'no': ");
                inp = console.nextLine();
            }
        }
    }


    // Method will search the database and look for a specific record
    public void lookUpRecord(){

        // user product id scanner
        Scanner sc = new Scanner(System.in);

        // Mirrors back to the user their chosen menu option (in this case to look-up a record)
        System.out.println(s);
        System.out.println("     Look Up Record");
        System.out.println(s);

        // Prompts user for product id
        System.out.println("Enter the Product ID of the Record you would like to view: ");
        String entered_product_id = sc.next(); // Reads user's input

        // If product id is less than or greater than 12 characters it's invalid so notify user
        if (entered_product_id.length() < 12 || entered_product_id.length() > 12){
            System.out.println("Invalid entry: Must be 12 characters long."); // Error Message

            // Keep on asking user for a valid 12 character long product id, repeats until user provides a valid input
            while(entered_product_id.length() != 12) {
                System.out.println("\n Please enter a valid Product ID (12 character long): "); // Prompts user for a valid (12 character long) product id
                entered_product_id = sc.next(); // Reads user's product id
            }
        }

        // Else if product id is correct, mirror back entered product id
        else {
            System.out.println("You Entered: " + entered_product_id);
            System.out.println(s);
        }

        // Search the array; if entered product id is found/exists, print appropriate row of information pertaining to that entered product id
        EntryItem itemSearchingFor = null;
        // iterate through "records"
        for (int i=0; i<records.size(); i++ ){
            if (entered_product_id.equalsIgnoreCase(records.get(i).getProduct_id())) { // if product id is found, retrieve info
                itemSearchingFor = records.get(i); // itemSearchingFor is set to item
                System.out.println("Product ID:        " + itemSearchingFor.getProduct_id()
                        + "\nQuantity:          " + itemSearchingFor.getQuantity()
                        + "\nWhole Sale Cost:   " + itemSearchingFor.getWholesale_cost()
                        + "\nSale Price:        " + itemSearchingFor.getSale_price()
                        + "\nSupplier ID:       " + itemSearchingFor.getSupplier_id());
                System.out.println(s);
                break;
            }
        }

        // If product id was not found, notify user that it was not found/does not exist
        if (itemSearchingFor == null){
            System.out.println("Sorry, the record you are looking for was not found.");
            System.out.println(s);
            return;
        }
    }


    //Method will update entry
    public void updateRecord(){
        //Prompt product Id for the product to update.
        System.out.println(s);
        System.out.println("          Update Record");
        System.out.println(s);

        System.out.println("Enter Product ID of the record you want to update: ");
        String productId = console.next();

        //Searching records for record with the product Id entered above.
        EntryItem itemToUpdate = null;
        for (int i=0; i<records.size(); i++ ){
            if (productId.equals(records.get(i).getProduct_id())) {
                itemToUpdate = records.get(i);
            }
        }

        //If product Id is not found in records, return.
        if (itemToUpdate == null){
            System.out.println(" Invalid Product ID. ");
            return;
        }

        System.out.println("Current item values: product id: ");
        System.out.println(s);
        System.out.println("Product ID:        " + itemToUpdate.getProduct_id()
                + "\nQuantity:          " + itemToUpdate.getQuantity()
                + "\nWhole Sale Cost:   " + itemToUpdate.getWholesale_cost()
                + "\nSale Price:        " + itemToUpdate.getSale_price()
                + "\nSupplier ID:       " + itemToUpdate.getSupplier_id());
        System.out.println(s);

        //Prompt the attribute to update.
        System.out.println("Enter the attribute to update: ");
        System.out.println(" a.   Product ID \n b.   Quantity \n c.   Wholesale Cost \n d.   Sale Price \n e.   Supplier ID ");
        String attribute = console.next();

        //Prompt for new attribute value and update.
        if (attribute.equals("a")){
            System.out.println("Enter the new Product ID: ");
            String newProductId = console.next();
            itemToUpdate.setProductId(newProductId);
        }
        else if(attribute.equals("b")) {
            System.out.println("Enter the new Quantity: ");
            int newQuantity = Integer.parseInt(console.next());
            itemToUpdate.setQuantity(newQuantity);
        }
        else if(attribute.equals("c")) {
            System.out.println("Enter the new Wholesale Cost: ");
            double newWholesaleCost = Double.parseDouble(console.next());
            itemToUpdate.setWholesaleCost(newWholesaleCost);
        }
        else if(attribute.equals("d")) {
            System.out.println("Enter the new Sale Price: ");
            double newSalePrice = Double.parseDouble(console.next());
            itemToUpdate.setSalePrice(newSalePrice);
        }
        else if (attribute.equals("e")){
            System.out.println("Enter the new Supplier ID: ");
            String newSupplierId = console.next();
            itemToUpdate.setSupplierId(newSupplierId);
        }
        else{
            System.out.println("Invalid attribute");
            return;
        }

        System.out.println("Updated record values");
        System.out.println(s);
        System.out.println("Product ID:        " + itemToUpdate.getProduct_id()
                + "\nQuantity:          " + itemToUpdate.getQuantity()
                + "\nWhole Sale Cost:   " + itemToUpdate.getWholesale_cost()
                + "\nSale Price:        " + itemToUpdate.getSale_price()
                + "\nSupplier ID:       " + itemToUpdate.getSupplier_id());
        System.out.println(s);
    }

    //Method will delete entry record
    public void deleteRecord(){
        System.out.println(s);
        System.out.println("     Delete Record");
        System.out.println(s);

        //asks user to enter product id
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("Enter Product ID of the record you would like to delete: ");
        System.out.println("");
        String productId = scanner.next();

        //searches entryitem for product to delete
        EntryItem itemToDeleItem = null;
        for (int i=0; i<records.size(); i++ ){
            if (productId.equals(records.get(i).getProduct_id())) {
                itemToDeleItem = records.get(i);
            }
        }
        //if item doesn't exists gives error
        if (itemToDeleItem == null){
            System.out.println("");
            System.out.println(" Invalid Product ID. Record does not exist.\n");
            return;
        }

        //shows all fields of entry to be deleted
        System.out.println("");
        System.out.println("Current item values");
        System.out.println(s);
        System.out.println("Product ID:        " + itemToDeleItem.getProduct_id()
                + "\nQuantity:          " + itemToDeleItem.getQuantity()
                + "\nWhole Sale Cost:   " + itemToDeleItem.getWholesale_cost()
                + "\nSale Price:        " + itemToDeleItem.getSale_price()
                + "\nSupplier ID:       " + itemToDeleItem.getSupplier_id());
        System.out.println(s);

        //asks user if they want to delete the item
        System.out.println("\nAre you sure you want to delete this record?: 'yes' or 'no'");
        String attribute = scanner.next();

        //if user selects yes then the item is deleted using the index
        if (attribute.equalsIgnoreCase("yes")){

            records.remove(itemToDeleItem);
            System.out.println("\nRecord has been deleted.");
            System.out.println("");
        }
        //if user selects anything else they are told the item was not deleted and are brought back to the menu
        else{
            System.out.println("\nRecord was NOT deleted.\n");
        }
    }
      /*Adding a method that can will give back the EntryItem we are looking for.
    -Will interact with Inventory updator;
    -is a mostly duplicate of lookUpRecord w/o the user prompts
    -Takes in a product ID and returns an EntryItem
     */
    public EntryItem viewRecord(String product_id){

        // user product id scanner
        Scanner sc = new Scanner(System.in);
        EntryItem itemSearchingFor = null;


        // If product id is less than or greater than 12 characters it's invalid so notify user
        if (product_id.length() < 12 || product_id.length() > 12){
            System.out.println( product_id + "is an invalid entry: Product ID must be 12 characters long."); // Error Message
            return itemSearchingFor;
        }


        // Search the array; if entered product id is found/exists, it will send the EntryItem object back
        // iterate through "records"
        for (int i=0; i<records.size(); i++ ){
            if (product_id.equalsIgnoreCase(records.get(i).getProduct_id())) { // if product id is found, retrieve info
                itemSearchingFor = records.get(i); // itemSearchingFor is set to item
                break;
            }
        }

        // If product id was not found, notify user that it was not found/does not exist
        if (itemSearchingFor == null){
            System.out.println("Sorry, the record you are looking for was not found.");
        }
        return itemSearchingFor;
    } 
}
