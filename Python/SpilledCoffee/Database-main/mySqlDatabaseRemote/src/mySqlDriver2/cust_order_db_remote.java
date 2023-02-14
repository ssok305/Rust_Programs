package mySqlDriver2;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;
import java.io.*;

public class cust_order_db_remote {
	
	//login creditials for database
	private static String url = "jdbc:mysql://192.254.233.63:3306/fbacon_spilledcoffee_main";
	private static String username = "";
	private static String password = "";
	//file for loadfile method
	private static String FILE_NAME = "test_cust_order.csv";

	private Scanner in = new Scanner(System.in);

	public static void main(String[] args) {

		cust_order_db_remote drive = new cust_order_db_remote();

		Scanner log = new Scanner(System.in);

		//simple login for database
		System.out.println("Enter your Username: ");
		username = log.next();

		System.out.println("Enter your Password: ");
		password = log.next();

		drive.displayMenu();
	}
	//displayMenu shows all options for program
	public void displayMenu(){
		Scanner input = new Scanner(System.in);
		System.out.println("Select a prompt: ");
		System.out.println("a. Search for orders\n"
			+ "b. Add a new order\n"
			+ "c. Update an order\n"
			+ "d. Delete an order\n"
			+ "e. Upload CSV\n"
			+ "f. To quit\n");

		String prompt = input.next();
		
		if(prompt.contains("a")) {
			searchOrder();
		}
		if(prompt.contains("b")) {
			addOrder();
		}
		if(prompt.contains("c")) {
			updateOrder();
		}
		if(prompt.contains("d")) {
			deleteOrder();
		}
		if(prompt.contains("e")) {
			try{
				loadFile();
			}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(prompt.contains("f")) {
			System.exit(1);
		}
		else{
			System.out.println("Please enter a valid selection.\n");
			displayMenu();
		}
	}

	public void loadFile() throws FileNotFoundException {

		Scanner scanner = new Scanner(System.in);
		Connection connection = null;
		Statement myStmt = null;
		
			try{
				Scanner in = new Scanner(new FileInputStream(FILE_NAME));
				String titles = in.nextLine();
				System.out.println(titles);
				//while loop goes through file and pulls information to be put into database
				while(in.hasNextLine()){
					String line = in.nextLine();
					int end = line.indexOf(",", 0);
					String date = line.substring(0, end);

					int start = end + 1;
					end = line.indexOf(",", start);
					String user_id = line.substring(start, end);

					start = end + 1;
					end = line.indexOf(",", start);
					String shipping_street = line.substring(start, end);

					start = end + 1;
					end = line.indexOf(",", start);
					String shipping_city = line.substring(start, end);

					start = end + 1;
					end = line.indexOf(",", start);
					String shipping_state = line.substring(start, end);

					start = end + 1;
					end = line.indexOf(",", start);
					String shipping_zipcode = line.substring(start, end);


					start = end + 1;
					end = line.indexOf(",", start);
					String tempQuantity = line.substring(start, end);
					int order_quantity = Integer.parseInt(tempQuantity);
					

					String tempProductId = line.substring(end + 1);
					int product_id = Integer.parseInt(tempProductId);

			try {
				//establishes connection to database
				connection = DriverManager.getConnection(url, username, password);

				//These two queries are made for inserting the new data into the database
				String query = " INSERT INTO new_customer_orders (ordered_at, user_id, shipping_street, shipping_city, shipping_state, shipping_zipcode)" +
				" VALUES (?, ?, ?, ?, ?, ?)";

				String iquery = " INSERT INTO new_order_items (order_id, user_id, product_id, quantity, sale_price, item_total) VALUES (?, ?, ?, ?, ?, ?)";

				//prepared statement that sets new data into the database
				PreparedStatement prepStmt = connection.prepareStatement(query);
				prepStmt.setString(1, date);
				prepStmt.setString(2, user_id);
				prepStmt.setString(3, shipping_street);
				prepStmt.setString(4, shipping_city);
				prepStmt.setString(5, shipping_state);
				prepStmt.setString(6, shipping_zipcode);

				prepStmt.execute();

				//finds sale price from inventory that matches product id in csv order
				PreparedStatement myStmt3 = connection.prepareStatement("Select sale_price FROM new_inventory WHERE product_id = '" + product_id + "'");
				ResultSet myRs = myStmt3.executeQuery();
				
				//finds order id from last order made in the new_customer_orders table
				PreparedStatement prepStmt2 = connection.prepareStatement("SELECT order_id FROM new_customer_orders ORDER BY order_id DESC LIMIT 1");
				ResultSet myRs1 = prepStmt2.executeQuery();
				
				if (myRs1.next()){
					//sets that order id to oid
					int oid = myRs1.getInt("order_id");
					//sets the sale price to price
					if(myRs.next()){
						double price = myRs.getDouble("sale_price");
						double total = order_quantity * price; 

						//sets the information into the new_order_items table
						PreparedStatement prepStmt1 = connection.prepareStatement(iquery);
						prepStmt1.setInt(1, oid);
						prepStmt1.setString (2, user_id);
						prepStmt1.setInt(3, product_id);
						prepStmt1.setInt (4, order_quantity);
						prepStmt1.setDouble(5, price);
						prepStmt1.setDouble(6, total);
					
						prepStmt1.execute();
					
						//finds item total from the same order we just put into the table
						PreparedStatement myStmt6 = connection.prepareStatement("SELECT item_total FROM new_order_items WHERE order_id = '" + oid + "'");
						ResultSet myRs2 = myStmt6.executeQuery();

						if(myRs2.next()){
							//sets item total to variable
							int item_total = myRs2.getInt("item_total");

							//finds quantity from same order id
							PreparedStatement myStmt7 = connection.prepareStatement("SELECT quantity FROM new_order_items WHERE order_id = '" + oid + "'");
							ResultSet myRs3 = myStmt7.executeQuery();

							if(myRs3.next()){
								//creates new quantity to update into new_customer_order table
								double new_quantity = myRs3.getDouble("quantity");

								PreparedStatement prepStmt3 = connection.prepareStatement("UPDATE new_customer_orders SET order_total = '"+ item_total +"' WHERE order_id = '"+ oid +"'");
								prepStmt3.execute();

								PreparedStatement prepStmt4 = connection.prepareStatement("UPDATE new_customer_orders SET order_quantity = '"+ new_quantity +"' WHERE order_id = '"+ oid +"'");
								prepStmt4.execute();

					}}}}
				
			} catch (SQLException se) {

				// TODO Auto-generated catch block
				System.out.println("oops, error!");
				se.printStackTrace();
			} catch (InputMismatchException e) {
				System.out.print(e.getMessage()); //try to find out specific reason.
			}

			try {
				if(connection!=null)
					connection.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}

		};

		in.close();
		displayMenu();
			
		}catch (FileNotFoundException exception){}
	}

		
	public void searchEmail(String email){

		try {
			//establishes connection to database
			Connection connection = DriverManager.getConnection(url, username, password);
			//selects all information from with the same user_id as was searched for
			//type_scroll_sensitve and concur_updatable allow for moving of pointer
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_customer_orders WHERE user_id = '" + email + "'", ResultSet.TYPE_SCROLL_SENSITIVE, 
			ResultSet.CONCUR_UPDATABLE);
		
			ResultSet myRs = myStmt.executeQuery();

			if (myRs.next()) {
				//sets point to beginning of query
				myRs.beforeFirst();
				//prints out all contents for specific email
				while (myRs.next()) {
					System.out.println();
					System.out.println("Order ID: " + myRs.getInt("order_id"));
					System.out.println("Date: " + myRs.getString("ordered_at"));
					System.out.println("Status: " + myRs.getString("order_status"));
					System.out.println("Email: " + myRs.getString("user_id"));
					System.out.println("Quantity: " + myRs.getInt("order_quantity"));
					System.out.println("Order Total: " + myRs.getDouble("order_total"));
					System.out.println("Street: " + myRs.getString("shipping_street"));
					System.out.println("City: " + myRs.getString("shipping_city"));
					System.out.println("Street: " + myRs.getString("shipping_state"));
					System.out.println("Zip Code: " + myRs.getString("shipping_zipcode"));
					System.out.println();
				}
			}
			//error if email is not found
		else{
			System.out.println();
            System.out.println("Sorry, email was not found.");
            System.out.println();
		}


		}catch (SQLException e) {	
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			e.printStackTrace();
			}

	}
	public void searchZip(String zip){
		try {
			//establishes connection to database
			Connection connection = DriverManager.getConnection(url, username, password);
			//selects all information from with the same zipcode as was searched for
			//type_scroll_sensitve and concur_updatable allow for moving of pointer
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_customer_orders WHERE shipping_zipcode = '" + zip + "'", ResultSet.TYPE_SCROLL_SENSITIVE, 
			ResultSet.CONCUR_UPDATABLE);
		
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {
				//sets pointer to beginning of query
				myRs.beforeFirst();
				//prints all results of query with same zip
				while (myRs.next()) {
					System.out.println();
					System.out.println("Order ID: " + myRs.getInt("order_id"));
					System.out.println("Date: " + myRs.getString("ordered_at"));
					System.out.println("Status: " + myRs.getString("order_status"));
					System.out.println("Email: " + myRs.getString("user_id"));
					System.out.println("Quantity: " + myRs.getInt("order_quantity"));
					System.out.println("Order total: " + myRs.getDouble("order_total"));
					System.out.println("Street: " + myRs.getString("shipping_street"));
					System.out.println("City: " + myRs.getString("shipping_city"));
					System.out.println("Street: " + myRs.getString("shipping_state"));
					System.out.println("Zip Code: " + myRs.getString("shipping_zipcode"));
					System.out.println();
				}
			}
			//error if zipcode was not found
			else{
				System.out.println("Sorry no orders with that zipcode was found");
				System.out.println();
				}
		}catch (SQLException e) {
			
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			e.printStackTrace();
			}
		
	}
	public void searchProcuctID(String pid){
		try {
			//establishes connection
			Connection connection = DriverManager.getConnection(url, username, password);
			//selects all information from with the same product id as was searched for
			//type_scroll_sensitve and concur_updatable allow for moving of pointer
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_order_items WHERE product_id = '" + pid + "'", ResultSet.TYPE_SCROLL_SENSITIVE, 
			ResultSet.CONCUR_UPDATABLE);

			ResultSet myRs = myStmt.executeQuery();
				
			if (myRs.next()) {
				//sets pointer to beginning of query
				myRs.beforeFirst();
				//prints all results of query with product id
				while (myRs.next()) {
					System.out.println();
					System.out.println("Order ID: " + myRs.getInt("order_id"));
					System.out.println("Email: " + myRs.getString("user_id"));
					System.out.println("Email: " + myRs.getString("product_id"));
					System.out.println("Quantity: " + myRs.getInt("Quantity"));
					System.out.println("Sale Price: " + myRs.getDouble("sale_price"));
					System.out.println("Item total: " + myRs.getDouble("item_total"));
					System.out.println("Item Sale ID: " + myRs.getString("id"));

					System.out.println();
				}
			}
			//error if product id is not found
		else{
            System.out.println("Sorry, product ID was not found.");
            System.out.println();
		}
		}catch (SQLException e) {
			
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			e.printStackTrace();
			}
		
	}
	public void searchDate(String date){
		try {
			//establish connection to database
			Connection connection = DriverManager.getConnection(url, username, password);
			//selects all information from with the same date as was searched for
			//type_scroll_sensitve and concur_updatable allow for moving of pointer
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_customer_orders WHERE ordered_at Like('%" + date + "%')", ResultSet.TYPE_SCROLL_SENSITIVE, 
			ResultSet.CONCUR_UPDATABLE);
	
			ResultSet myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				//sets pointer to beginning of query
				myRs.beforeFirst();
				//prints all results of query with date
				while (myRs.next()) {
					System.out.println();
					System.out.println("Order ID: " + myRs.getInt("order_id"));
					System.out.println("Date: " + myRs.getString("ordered_at"));
					System.out.println("Status: " + myRs.getString("order_status"));
					System.out.println("Email: " + myRs.getString("user_id"));
					System.out.println("Quantity: " + myRs.getInt("order_quantity"));
					System.out.println("Order total: " + myRs.getDouble("order_total"));
					System.out.println("Street: " + myRs.getString("shipping_street"));
					System.out.println("City: " + myRs.getString("shipping_city"));
					System.out.println("Street: " + myRs.getString("shipping_state"));
					System.out.println("Zip Code: " + myRs.getString("shipping_zipcode"));
					System.out.println();
				}
				
			}
			//error if date is not found or format is incorrect
		else{
			System.out.println();
            System.out.println("Sorry, no orders with that date was found.");
			System.out.println("Make sure that the format is correct, or search wont work!");
            System.out.println();
		}
		}catch (SQLException e) {
			
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			e.printStackTrace();
			}
		
	}
	public void searchOrderID(int oid){
		try {
			//establish connection to database
			Connection connection = DriverManager.getConnection(url, username, password);
			//selects all information from with the same order id as was searched for
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_customer_orders WHERE order_id = '" + oid + "'");
		
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {	
				//No while loop cuz cust_id is unique
				System.out.println();
				System.out.println("Order ID: " + myRs.getInt("order_id"));
				System.out.println("Date: " + myRs.getString("ordered_at"));
				System.out.println("Status: " + myRs.getString("order_status"));
				System.out.println("Email: " + myRs.getString("user_id"));
				System.out.println("Quantity: " + myRs.getInt("order_quantity"));
				System.out.println("Order Total: " + myRs.getDouble("order_total"));
				System.out.println("Street: " + myRs.getString("shipping_street"));
				System.out.println("City: " + myRs.getString("shipping_city"));
				System.out.println("Street: " + myRs.getString("shipping_state"));
				System.out.println("Zip Code: " + myRs.getString("shipping_zipcode"));
				System.out.println();
			
			}
		else{
			//error if order id is not found
			System.out.println();
            System.out.println("Sorry, no order with that ID was found.");
            System.out.println();
		}
		}catch (SQLException e) {	
		// TODO Auto-generated catch block
		System.out.println("oops, error!");
		e.printStackTrace();
		}
	}

	public void searchOrder() {
		
		Scanner scanner = new Scanner(System.in);
			//menu for which field you'd like to search by
			System.out.println();
			System.out.println("What field would you like to search by: ");
			System.out.println("a. Customer email.");
			System.out.println("b. Customer Address.");
			System.out.println("c. Product ID");
			System.out.println("d. Date of Order.");
			System.out.println("e. Order ID");
			System.out.println();
			String select = scanner.next();
			

			if(select.contains("a")) {
				
				System.out.println("Enter Customer Email: ");
				String email = scanner.next();
				searchEmail(email);			
			}
			
			if(select.contains("b")) {

				System.out.println("Enter 5 digit ZIP code: ");
				String zip = scanner.next();

				searchZip(zip);
			}
			
			if(select.contains("c")) {
					
				System.out.println("Enter product ID: ");
				String pid = scanner.next();
				searchProcuctID(pid);		
			}
			
			if(select.contains("d")) {

				System.out.println("Enter Date(ex: 2021-01-01): ");
				String date = scanner.next();
				searchDate(date);

			}
			
			if(select.contains("e")) {
				
				System.out.println("Enter Order ID: ");
				int oid = scanner.nextInt();
				searchOrderID(oid);
				
			}
			//asks if you would like to see more details on order which shows new_order_items fields
		System.out.println("Would you like to see more details of the order?\n" 
			+ "Yes or no?\n");
		String answer1 = in.nextLine();
		if(answer1.contains("y") || answer1.contains("Y")){
			System.out.println("Enter the order id of the order you would like to view: ");
			int oid2 = scanner.nextInt();
			searchProducts(oid2);
		}
		else{
			displayMenu();
		}
		//asks if you'd like to search again
		System.out.println("Would you like to make another search?\n" 
			+ "Yes or no?\n");
		String answer = in.nextLine();
		if(answer.contains("y") || answer.contains("Y")){
			searchOrder();
		}
		else{
			displayMenu();
		}
	
	}

		public void searchProducts(int oid){
			try {
				//establish connection to database
				Connection connection = DriverManager.getConnection(url, username, password);
				//searches new_order_items table for order id and prints query
				//type_scroll_sensitve and concur_updatable allow for moving of pointer
				PreparedStatement myStmt = connection.prepareStatement("SELECT * FROM new_order_items WHERE order_id = '" + oid + "'", ResultSet.TYPE_SCROLL_SENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);
			
				ResultSet myRs = myStmt.executeQuery();
				if (myRs.next()) {	
					//sets pointer to beginning of query
					myRs.beforeFirst();
					
					System.out.println();
					System.out.println("ITEMS ORDERED");
					System.out.println("----------------");
					System.out.println();
					//while loops prints all fields for new_order_items
					while (myRs.next()) {
						System.out.println();
						System.out.println("Order ID: " + myRs.getInt("order_id"));
						System.out.println("Email: " + myRs.getString("user_id"));
						System.out.println("Product ID: " + myRs.getString("product_id"));
						System.out.println("Quantity: " + myRs.getInt("quantity"));
						System.out.println("Sale Price: " + myRs.getDouble("sale_price"));
						System.out.println("Item Total: " + myRs.getDouble("item_total"));
						System.out.println("Item ID: " + myRs.getInt("id"));
						System.out.println();
					}
				
				
				}
			else{
				//error if order id not found
				System.out.println();
				System.out.println("Sorry, no order with that ID was found.");
				System.out.println();
			}
			}catch (SQLException e) {	
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			e.printStackTrace();
			}
		}
		public void addOrder() {

			Scanner scanner = new Scanner(System.in);
			Connection connection = null;
			Statement myStmt = null;
			
			try {
				//establishes connection to database
				connection = DriverManager.getConnection(url, username, password);
				
				//both queries create inserts for fields collected by user
				String query = " INSERT INTO new_customer_orders (ordered_at, user_id, shipping_street, shipping_city, shipping_state, shipping_zipcode)" +
						" VALUES (?, ?, ?, ?, ?, ?)";

				String iquery = " INSERT INTO new_order_items (order_id, user_id, product_id, quantity, sale_price, item_total) VALUES (?, ?, ?, ?, ?, ?)";
				
				//collects fields from user
				System.out.println();
				System.out.println();
				System.out.println("Enter Customer Email or User Id: ");
				String userId = scanner.next();
				System.out.println();
				
				scanner.nextLine();
				System.out.println();
				System.out.println("Enter the customer street address: ");
				String street = scanner.nextLine();
				System.out.println();

				System.out.println();
				System.out.println("Enter the customer City: ");
				String city = scanner.nextLine();
				System.out.println();

				System.out.println();
				System.out.println("Enter the customer State(abbreviation only ex: CO for Colorado): ");
				String state = scanner.next();
				System.out.println();

				System.out.println();
				System.out.println("Enter customer zip code: ");
				String custZip = scanner.next();
				//catches error for zip code being longer than 5
				while(custZip.length() != 5){
					System.out.println("Invalid zip, try again: ");
					custZip = scanner.next();
				}

				System.out.println();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
				//sets all fields and inserts into table
				PreparedStatement prepStmt = connection.prepareStatement(query);
				prepStmt.setTimestamp(1, timestamp);
				prepStmt.setString (2, userId);
				prepStmt.setString(3, street);
				prepStmt.setString(4, city);
				prepStmt.setString(5, state);
				prepStmt.setString(6, custZip);

				prepStmt.execute();


				//loop to keep asking for products being purchased and their quantities
				int cont = 0;
				while(cont == 0){
					System.out.println();
					System.out.println();
					System.out.println("Enter the product id: ");
					int productId = scanner.nextInt();
					System.out.println();

					System.out.println();
					System.out.println();
					System.out.println("Enter the quantity: ");
					int quantity = scanner.nextInt();
					System.out.println();
					
					//prepared statement finds sale price of product id entered
					PreparedStatement myStmt3 = connection.prepareStatement("Select sale_price FROM new_inventory WHERE product_id = '" + productId + "'");

					ResultSet myRs = myStmt3.executeQuery();
					//prepared statement finds last order id entered which is order last inserted
					PreparedStatement prepStmt2 = connection.prepareStatement("SELECT order_id FROM new_customer_orders ORDER BY order_id DESC LIMIT 1");

					ResultSet myRs1 = prepStmt2.executeQuery();

					
					
					
					if (myRs1.next()){
						//sets oid to the order id found
						int oid = myRs1.getInt("order_id");

						if(myRs.next()){
							//sets price to sale price found
							double price = myRs.getDouble("sale_price");
							//creates new variable total by multiplying quantity and price
							double total = quantity * price; 
	
						//inserts fields into table
						PreparedStatement prepStmt1 = connection.prepareStatement(iquery);
						prepStmt1.setInt(1, oid);
						prepStmt1.setString (2, userId);
						prepStmt1.setInt(3, productId);
						prepStmt1.setInt (4, quantity);
						prepStmt1.setDouble(5, price);
						prepStmt1.setDouble(6, total);
						

						prepStmt1.execute();
						//finds the sum of all quantities with that order id
						PreparedStatement myStmt6 = connection.prepareStatement("SELECT SUM(quantity) total_quantity FROM new_order_items WHERE order_id = '" + oid + "'");

						ResultSet myRs2 = myStmt6.executeQuery();
						if(myRs2.next()){

							int item_total = myRs2.getInt("total_quantity");
							//finds the sum of all totals with that order id
							PreparedStatement myStmt7 = connection.prepareStatement("SELECT SUM(item_total) total_cost FROM new_order_items WHERE order_id = '" + oid + "'");
							ResultSet myRs3 = myStmt7.executeQuery();
							if(myRs3.next()){

								
								double order_total = myRs3.getDouble("total_cost");
								//prepared statements update order_total and order_quantity to the sums from all entries with that same order id
								PreparedStatement prepStmt3 = connection.prepareStatement("UPDATE new_customer_orders SET order_total = '"+ order_total +"' WHERE order_id = '"+ oid +"'");
								prepStmt3.execute();
								PreparedStatement prepStmt4 = connection.prepareStatement("UPDATE new_customer_orders SET order_quantity = '"+ item_total +"' WHERE order_id = '"+ oid +"'");
								prepStmt4.execute();

						}}
			
					}
				}

					//starts a new product entry
					System.out.println("Would you like to add another product?");
				String more = scanner.next();
					
				if(more.contains("n")){
					cont = cont +1;
					}
				
				
				
			

				
				}
				
			} catch (SQLException se) {
				
				// TODO Auto-generated catch block
				System.out.println("oops, error!");
				se.printStackTrace();
				
			} 
			try {
				if(connection!=null)
					connection.close();
			}catch(SQLException se) {
				se.printStackTrace();
			}
			System.out.println("Would you like to add another order?\n" 
			+ "Yes or no?\n");
		String answer = in.nextLine();
		if(answer.contains("y") || answer.contains("Y")){
			addOrder();
		}
		else{
			displayMenu();
		}
			
	}
	public void updateOrder() {
			
		Scanner scanner = new Scanner(System.in);
		Connection connection = null;
		Statement myStmt = null;
			
		try {
			//establishes connection to database
			connection = DriverManager.getConnection(url, username, password);
			//asks user if they have the order id
			System.out.println("Do you have the order ID? 'Yes' or 'No'");
			String answer = in.next();
			if(answer.contains("y") || answer.contains("Y")){

			}
			//if no asks for how they would like to search for order
			else{
				System.out.println("How would you like to search for the order to update? ");
				System.out.println("a. Customer email.");
				System.out.println("b. Zip.");
				System.out.println("c. Product ID.");
				String input = in.next();
				boolean search = false;
				while(!search){
					if(input.contains("a")){
						System.out.println("Enter Customer Email: ");
						String email = scanner.next();
						searchEmail(email);
						search = true;
						break;
				}
				if(input.contains("b")){
					System.out.println("Enter zip code: ");
					String zip = scanner.next();
					searchZip(zip);
					search = true;
					break;
				}
				if(input.contains("c")){
					System.out.println("Enter product ID: ");
					String pid = scanner.next();
					searchZip(pid);
					search = true;
					break;
				}
				else{
					System.out.println("Please enter a valid option.");
					input = in.next();
				}
			}
		}
		//if yes enter order id
		System.out.println("Enter the order ID of the order you would like to update: ");
		int oid = scanner.nextInt();
		searchOrderID(oid);
		//choose field user would like to update
		System.out.println("Which field would you like to update? ");
		System.out.println("a. Date");
		System.out.println("b. Shipping Information");
		System.out.println("c. Order Details");
		System.out.println("d. Email");
		System.out.println();
					
		String ans = scanner.next();
					
		if(ans.contains("a")) {
						
			System.out.println();
			System.out.println("Enter the new date and time(ex: 2021-01-01 09:50:22): ");
			System.out.println();
			String date = scanner.nextLine();
			//updates date of order
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_customer_orders SET ordered_at = '" + date +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();
		}
					
		if(ans.contains("b")) {
						
			System.out.println();
			System.out.println("Enter the new zip code: ");
			System.out.println();
			String zip = scanner.next();
			//updates zip of order
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_customer_orders SET shipping_zipcode = '" + zip +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();

			scanner.nextLine();
			System.out.println();
			System.out.println("Enter the new street address: ");
			System.out.println();
			String street = scanner.nextLine();
			//updates street address of order
			PreparedStatement upStmt3 = connection.prepareStatement("UPDATE new_customer_orders SET shipping_street = '" + street + "' WHERE order_id = '"+ oid +"'");
			upStmt3.execute();

			System.out.println();
			System.out.println("Enter the new city: ");
			System.out.println();
			String city = scanner.nextLine();
			//updates city of order
			PreparedStatement upStmt5 = connection.prepareStatement("UPDATE new_customer_orders SET shipping_city = '" + city +"' WHERE order_id = '"+ oid +"'");
			upStmt5.execute();

			System.out.println();
			System.out.println("Enter the new state(example: CO for Colorado): ");
			System.out.println();
			String state = scanner.nextLine();
			//updates state of order
			PreparedStatement upStmt4 = connection.prepareStatement("UPDATE new_customer_orders SET shipping_state = '" + state +"' WHERE order_id = '"+ oid +"'");
			upStmt4.execute();
						
		}
					
							
		if(ans.contains("c")) {
			searchProducts(oid);
			System.out.println();
			System.out.println("Select the item ID you'd like to update: ");
			int iid = scanner.nextInt();
			//selects item id to update
			System.out.println();
			System.out.println("Enter the new Product ID: ");
			System.out.println();
			String pid = scanner.next();
			//updates product id of order
			PreparedStatement upStmt3 = connection.prepareStatement("UPDATE new_order_items SET product_id = '" + pid +"' WHERE id = '"+ iid +"'");
			upStmt3.execute();

			System.out.println();
			System.out.println("Enter the new quantity: ");
			System.out.println();
			int pq = scanner.nextInt();
			//updates quantity of order
			PreparedStatement upStmt4 = connection.prepareStatement("UPDATE new_order_items SET quantity = '" + pq +"' WHERE id = '"+ iid +"'");
			upStmt4.execute();
			//gets sale price of new product id
			PreparedStatement upStmt5 = connection.prepareStatement("Select sale_price FROM new_inventory WHERE product_id = '" + pid + "'");
			ResultSet myRs = upStmt5.executeQuery();

			if(myRs.next()){
				double price = myRs.getDouble("sale_price");
				double total = pq * price; 
				//updates new sale price in new order items
				PreparedStatement upStmt6 = connection.prepareStatement("UPDATE new_order_items SET sale_price = '" + price + "' WHERE id = '"+ iid +"'");
				upStmt6.execute();
				//updates new total in new order items
				PreparedStatement upStmt7 = connection.prepareStatement("UPDATE new_order_items SET item_total = '" + total + "' WHERE id = '"+ iid +"'");
				upStmt7.execute();
				//creates sum of the new quantity
				PreparedStatement myStmt8 = connection.prepareStatement("SELECT SUM(quantity) total_quantity FROM new_order_items WHERE order_id = '" + oid + "'");

				ResultSet myRs2 = myStmt8.executeQuery();
				if(myRs2.next()){

					int item_total = myRs2.getInt("total_quantity");
					//creates sum of item totals
					PreparedStatement myStmt9 = connection.prepareStatement("SELECT SUM(item_total) total_cost FROM new_order_items WHERE order_id = '" + oid + "'");
					ResultSet myRs3 = myStmt9.executeQuery();
					if(myRs3.next()){
						
						double order_total = myRs3.getDouble("total_cost");
						//sets new order total in new_customer_orders
						PreparedStatement prepStmt3 = connection.prepareStatement("UPDATE new_customer_orders SET order_total = '"+ order_total +"' WHERE order_id = '"+ oid +"'");
						prepStmt3.execute();
						//sets new order quantity in new_customer_orders
						PreparedStatement prepStmt4 = connection.prepareStatement("UPDATE new_customer_orders SET order_quantity = '"+ item_total +"' WHERE order_id = '"+ oid +"'");
						prepStmt4.execute();

				}}



			
						
		}
		if(ans.contains("d")) {
						
			System.out.println();
			System.out.println("Enter the new email: ");
			System.out.println();
			String email2 = scanner.next();
			//updates email with new email from user
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_customer_orders SET user_id = '" + email2 +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();
		}				
		} }
		catch (SQLException se) {
				
		// TODO Auto-generated catch block
			System.out.println("oops, error!");
			se.printStackTrace();
		} 
		try {
			if(connection!=null){
			connection.close();}
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		System.out.println("Would you like to update another order?\n" 
			+ "Yes or no?\n");
		String reply = in.next();
		if(reply.contains("y") || reply.contains("Y")){
			updateOrder();
		}
		else{displayMenu();}
			
	}
	
	
	public void deleteOrder() {
		
		Scanner scanner = new Scanner(System.in);
		Connection connection = null;
		Statement myStmt = null;
			
		try {
				
			connection = DriverManager.getConnection(url, username, password);
			//asks user for order id to delete
			System.out.println("Do you have the order ID? Yes or no?");
			String answer = scanner.next();
			if(answer.contains("Y") || answer.contains("y")){
				//if user has order id enter it
				System.out.println("Enter the order ID of the order you would like deleted.");
				int oid = scanner.nextInt();
				searchOrderID(oid);
				//gives user one more check if they want to delete that order		
				System.out.println("Are you sure you want to delete this order? 'Yes' or 'No'");
				String ans1 = scanner.next();
						
				if(ans1.contains("y") || ans1.contains("Y")) {
					//deletes order from both tables user the order id
					String query = "DELETE FROM new_customer_orders WHERE order_id = '" + oid + "'";
					String query2 = "DELETE FROM new_order_items WHERE order_id = '" + oid + "'";
					PreparedStatement delStmt = connection.prepareStatement(query);
					PreparedStatement delStmt2 = connection.prepareStatement(query2);
					delStmt.execute();
					delStmt2.execute();
					System.out.println();
					System.out.println("Order " + oid + " has been deleted.");
					System.out.println();
				}
		
			
				else{
		
					displayMenu();

				}}
			else{
				//search customer email for order id
				System.out.println("Enter customer email: ");
				String email = scanner.next();
	
				searchEmail(email);
				//select order id from orders to delete
				System.out.println("Enter the order ID of the order you would like deleted.");
				int oid = scanner.nextInt();
				searchOrderID(oid);

				//checks with user that they want to delete order
				System.out.println("Are you sure you want to delete this order? 'Yes' or 'No'");
				String ans1 = scanner.next();
						
				if(ans1.contains("y") || ans1.contains("Y")) {
					//deletes order from both tables
					String query = "DELETE FROM new_customer_orders WHERE order_id = '" + oid + "'";
					String query2 = "DELETE FROM new_order_items WHERE order_id = '" + oid + "'";
					PreparedStatement delStmt = connection.prepareStatement(query);
					PreparedStatement delStmt2 = connection.prepareStatement(query2);
					delStmt.execute();
					delStmt2.execute();
					System.out.println();
					System.out.println("Order " + oid + " has been deleted.");
					System.out.println();
				}
				else{
					displayMenu();
				}}}
		 catch (SQLException se) {
				
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			se.printStackTrace();	
		} 
		try {
			if(connection!=null){
			connection.close();
			}
		}catch(SQLException se) {se.printStackTrace();}
		//asks user if they want to delete another order
		System.out.println("Would you like to delete another order?\n" 
			+ "Yes or no?\n");
		String answer = in.nextLine();
		if(answer.contains("y") || answer.contains("Y")){
			deleteOrder();
		}
		else{displayMenu();}		

}}
