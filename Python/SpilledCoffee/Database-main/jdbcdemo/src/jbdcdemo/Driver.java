package jbdcdemo;
import java.util.*;


import java.sql.*;

public class Driver {

	public static String url = "";
	public static String username = "";
	public static String password = "";

	private Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		Driver drive = new Driver();
		drive.displayMenu();
		
	}
	public void displayMenu(){
		Scanner input = new Scanner(System.in);
		System.out.println("Select a prompt: ");
		System.out.println("a. Search for orders\n"
			+ "b. Add a new order\n"
			+ "c. Update an order\n"
			+ "d. Delete an order\n"
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
		if(prompt.contains("f")) {
			System.exit(1);
		}
		else{
			System.out.println("Please enter a valid selection.\n");
			displayMenu();
		}
	}
	public void searchEmail(String email){

		try {
			Connection connection = DriverManager.getConnection(url, username, password);//Statement myStmt = connection.createStatement();
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM customer_orders2 WHERE cust_email = '" + email + "'");
		
			
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {

				
				//The first order was not printing, so I added this print section to get first item
				System.out.println();
				System.out.println("Date: " + myRs.getString("date"));
				System.out.println("Email: " + myRs.getString("cust_email"));
				System.out.println("Zip Code: " + myRs.getString("cust_location"));
				System.out.println("Product ID: " + myRs.getString("product_id"));
				System.out.println("Quantity: " + myRs.getInt("product_quantity"));
				System.out.println("Order ID: " + myRs.getString("order_id"));
				System.out.println(); 

				while (myRs.next()) {
					System.out.println();
					System.out.println("Date: " + myRs.getString("date"));
					System.out.println("Email: " + myRs.getString("cust_email"));
					System.out.println("Zip Code: " + myRs.getString("cust_location"));
					System.out.println("Product ID: " + myRs.getString("product_id"));
					System.out.println("Quantity: " + myRs.getInt("product_quantity"));
					System.out.println("Order ID: " + myRs.getString("order_id"));
					System.out.println();
				}
			}
		else{
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
			Connection connection = DriverManager.getConnection(url, username, password);//Statement myStmt = connection.createStatement();
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM customer_orders2 WHERE cust_location = '" + zip + "'");
		
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {	
				System.out.println();
				System.out.println("Date: " + myRs.getString("date"));
				System.out.println("Email: " + myRs.getString("cust_email"));
				System.out.println("Zip Code: " + myRs.getString("cust_location"));
				System.out.println("Product ID: " + myRs.getString("product_id"));
				System.out.println("Quantity: " + myRs.getInt("product_quantity"));
				System.out.println("Order ID: " + myRs.getString("order_id"));
				System.out.println();
				while (myRs.next()) {
					System.out.println();
					System.out.println("Date: " + myRs.getString("date"));
					System.out.println("Email: " + myRs.getString("cust_email"));
					System.out.println("Zip Code: " + myRs.getString("cust_location"));
					System.out.println("Product ID: " + myRs.getString("product_id"));
					System.out.println("Quantity: " + myRs.getInt("product_quantity"));
					System.out.println("Order ID: " + myRs.getString("order_id"));
					System.out.println();
				}
			}
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
			Connection connection = DriverManager.getConnection(url, username, password);//Statement myStmt = connection.createStatement();
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM customer_orders2 WHERE product_id = '" + pid + "'");

			ResultSet myRs = myStmt.executeQuery();
				
			if (myRs.next()) {
				System.out.println();
				System.out.println("Date: " + myRs.getString("date"));
				System.out.println("Email: " + myRs.getString("cust_email"));
				System.out.println("Zip Code: " + myRs.getString("cust_location"));
				System.out.println("Product ID: " + myRs.getString("product_id"));
				System.out.println("Quantity: " + myRs.getInt("product_quantity"));
				System.out.println("Order ID: " + myRs.getString("order_id"));
				System.out.println();

				while (myRs.next()) {
					System.out.println();
					System.out.println("Date: " + myRs.getString("date"));
					System.out.println("Email: " + myRs.getString("cust_email"));
					System.out.println("Zip Code: " + myRs.getString("cust_location"));
					System.out.println("Product ID: " + myRs.getString("product_id"));
					System.out.println("Quantity: " + myRs.getInt("product_quantity"));
					System.out.println("Order ID: " + myRs.getString("order_id"));
					System.out.println();
				}
			}
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
			Connection connection = DriverManager.getConnection(url, username, password);
			
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM customer_orders2 WHERE date = '" + date + "'");
	
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {	
					System.out.println();
					System.out.println("Date: " + myRs.getString("date"));
					System.out.println("Email: " + myRs.getString("cust_email"));
					System.out.println("Zip Code: " + myRs.getString("cust_location"));
					System.out.println("Product ID: " + myRs.getString("product_id"));
					System.out.println("Quantity: " + myRs.getInt("product_quantity"));
					System.out.println("Order ID: " + myRs.getString("order_id"));
					System.out.println();
				while(myRs.next()){
					System.out.println();
					System.out.println("Date: " + myRs.getString("date"));
					System.out.println("Email: " + myRs.getString("cust_email"));
					System.out.println("Zip Code: " + myRs.getString("cust_location"));
					System.out.println("Product ID: " + myRs.getString("product_id"));
					System.out.println("Quantity: " + myRs.getInt("product_quantity"));
					System.out.println("Order ID: " + myRs.getString("order_id"));
					System.out.println();
				}
				
			}
		else{
            System.out.println("Sorry,no orders with that date was found.");
			System.out.println("Make sure that the format is correct, or search wont work!");
            System.out.println();
		}
		}catch (SQLException e) {
			
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			e.printStackTrace();
			}
		
	}
	public void searchOrderID(String oid){
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			//Statement myStmt = connection.createStatement();
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM customer_orders2 WHERE order_id = '" + oid + "'");
		
			ResultSet myRs = myStmt.executeQuery();
			if (myRs.next()) {	
				//No while loop cuz cust_id is unique
					System.out.println();
					System.out.println("Date: " + myRs.getString("date"));
					System.out.println("Email: " + myRs.getString("cust_email"));
					System.out.println("Zip Code: " + myRs.getString("cust_location"));
					System.out.println("Product ID: " + myRs.getString("product_id"));
					System.out.println("Quantity: " + myRs.getInt("product_quantity"));
					System.out.println("Order ID: " + myRs.getString("order_id"));
					System.out.println();
			
			}
		else{
            System.out.println("Sorry, no oder with that ID was found.");
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

			System.out.println();
			System.out.println("What field would you like to search by: ");
			System.out.println("a. Customer email.");
			System.out.println("b. Zip code of customer.");
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

				System.out.println("Enter Date(ex: 1-1-2021): ");
				String date = scanner.next();
				searchDate(date);

			}
			
			if(select.contains("e")) {
				
				System.out.println("Enter Order ID: ");
				String oid = scanner.next();
				searchOrderID(oid);
				
			}
			
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
		public void addOrder() {

			Scanner scanner = new Scanner(System.in);
			Connection connection = null;
			Statement myStmt = null;
			
			try {
				
				connection = DriverManager.getConnection(url, username, password);
				
				String query = " INSERT INTO customer_orders2 (date, cust_email, cust_location, product_id, product_quantity)" +
						" VALUES (?, ?, ?, ?, ?)";
						
				System.out.println();
				System.out.println("Enter the date(format 01/01/2021): ");
				String date = scanner.next();
				while(!date.contains("/")){
					System.out.println("Please enter the date in correct format: ");
					date = scanner.next();
				}
				System.out.println();
				
				System.out.println();
				System.out.println("Enter customer email: ");
				String custEmail = scanner.next();
				System.out.println();
				
				System.out.println();
				System.out.println("Enter customer zip code: ");
				String custZip = scanner.next();
				while(custZip.length() != 5){
					System.out.println("Invalid zip, try again: ");
					custZip = scanner.next();
				}
				System.out.println();
				
				System.out.println();
				System.out.println("Enter the product id: ");
				String productId = scanner.next();
				System.out.println();
				
				System.out.println();
				System.out.println("Enter the quantity: ");
				int quantity = scanner.nextInt();
				System.out.println();
							
				

				PreparedStatement prepStmt = connection.prepareStatement(query);
				prepStmt.setString (1, date);
				prepStmt.setString (2, custEmail);
				prepStmt.setString (3, custZip);
				prepStmt.setString (4, productId);
				prepStmt.setInt (5, quantity);
	
				
				prepStmt.execute();
				

				
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
				
			connection = DriverManager.getConnection(url, username, password);

			System.out.println("Do you have the order ID? 'Yes' or 'No'");
			String answer = in.next();
			if(answer.contains("y") || answer.contains("Y")){

			}
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

		System.out.println("Enter the order ID of the order you would like to update: ");
		String oid = scanner.next();
		searchOrderID(oid);

		System.out.println("Which field would you like to update? ");
		System.out.println("a. Date");
		System.out.println("b. Zip Code");
		System.out.println("c. Product ID");
		System.out.println("d. Quantity");
		System.out.println("e. Email");
		System.out.println();
					
		String ans = scanner.next();
					
		if(ans.contains("a")) {
						
			System.out.println();
			System.out.println("Enter the new date(ex:1/1/2020): ");
			System.out.println();
			String date = scanner.next();
						
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE customer_orders2 SET date = '" + date +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();
		}
					
		if(ans.contains("b")) {
						
			System.out.println();
			System.out.println("Enter the new zip code: ");
			System.out.println();
			String zip = scanner.next();
					
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE customer_orders2 SET cust_location = '" + zip +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();
						
		}
					
		if(ans.contains("c")) {
						
			System.out.println();
			System.out.println("Enter the new Product ID: ");
			System.out.println();
			String pid = scanner.next();
						
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE customer_orders2 SET product_id = '" + pid +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();
						
		}
							
		if(ans.contains("d")) {
						
			System.out.println();
			System.out.println("Enter the new quantity: ");
			System.out.println();
			String pq = scanner.next();
						
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE customer_orders2 SET product_quantity = '" + pq +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();
						
		}
		if(ans.contains("e")) {
						
			System.out.println();
			System.out.println("Enter the new email: ");
			System.out.println();
			String email2 = scanner.next();
						
			PreparedStatement upStmt2 = connection.prepareStatement("UPDATE customer_orders2 SET date = '" + email2 +"' WHERE order_id = '"+ oid +"'");
			upStmt2.execute();
		}				
		} 
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
					
			System.out.println("Do you have the order ID? Yes or no?");
			String answer = scanner.next();
			if(answer.contains("Y") || answer.contains("y")){
						
				System.out.println("Enter the order ID of the order you would like deleted.");
				String oid = scanner.next();
				searchOrderID(oid);
						
				System.out.println("Are you sure you want to delete this order? 'Yes' or 'No'");
				String ans = scanner.next();
						
				if(ans.contains("y") || ans.contains("Y")) {
					String query = "DELETE FROM customer_orders2 WHERE order_id = '" + oid + "'";
						
					PreparedStatement delStmt = connection.prepareStatement(query);
					delStmt.execute();
				}
			}
			else{
				System.out.println("Enter customer email: ");
				String email = scanner.next();
	
				searchEmail(email);
							
				System.out.println("Enter the order ID of the order you would like deleted.");
				int oid = scanner.nextInt();
				System.out.println();
						
				System.out.println("Are you sure you want to delete this order? 'Yes' or 'No'");
				String ans = scanner.next();
						
				if(ans.contains("y") || ans.contains("Y")) {
					String query = "DELETE FROM customer_orders2 WHERE order_id = '" + oid + "'";
						
					PreparedStatement delStmt = connection.prepareStatement(query);
					delStmt.execute();
				}

			}
		} catch (SQLException se) {
				
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			se.printStackTrace();	
		} 
		try {
			if(connection!=null){
			connection.close();
			}
		}catch(SQLException se) {se.printStackTrace();}
		
		System.out.println("Would you like to delete another order?\n" 
			+ "Yes or no?\n");
		String answer = in.nextLine();
		if(answer.contains("y") || answer.contains("Y")){
			deleteOrder();
		}
		else{displayMenu();}		
		
	}
}
