package mySqlDriver2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class inventory_db_remote {
	//database login credentials
	public static String url = "jdbc:mysql://192.254.233.63:3306/fbacon_spilledcoffee_main";
	public static String username = "";
	public static String password = "";
	
	public static void main(String[] args) {
		
		//scanner for login
		Scanner log = new Scanner(System.in);

		//login for user into database
		System.out.println("Enter your Username: ");
		username = log.next();

		System.out.println("Enter your Password: ");
		password = log.next();


		displayMenu();
	}
	
	//method to display menu
	public static void displayMenu() {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Select a prompt: ");
		System.out.print("a. Create a new record\n"
				+ "b. Look up a record\n"
				+ "c. Update a record\n"
				+ "d. Delete an existing record\n");;

		String prompt = input.next();
		
		if(prompt.contains("a")) {
			createRecord();
		}
		
		if(prompt.contains("b")) {
			lookUpRecord();
		}
		
		if(prompt.contains("c")) {
			updateRecord();
		}
		
		if(prompt.contains("d")) {
			deleteRecord();
		}
		
	}
	//method will look up record in inventory
	public static void lookUpRecord(){
		

		//scanner for lookup
		Scanner scanner = new Scanner(System.in);
		
		try {
			//creates connection to sql database
			Connection connection = DriverManager.getConnection(url, username, password);
			
			System.out.println();
			System.out.println("Search by product id: ");
			System.out.println("Enter Product ID: ");
			String pid = scanner.next();
			
			//queries the product with that specific product id. resultset is made to be able to adjust pointer
			PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_inventory WHERE product_id = '" + pid + "'", ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
	
		
			ResultSet myRs = myStmt.executeQuery();
			
			if (myRs.next()) {
				//this sets pointer to beginning of query
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
					
				
					}}
			else {
				System.out.println("Product ID invalid.");
				System.out.println();
			}

			displayMenu();
			
				
		
				
			
			
			
			} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			System.out.println("oops, error!");
			e.printStackTrace();
		}
		
	}
	public static void createRecord() {
	
		
		
		Scanner scanner = new Scanner(System.in);
		Scanner scanner1 = new Scanner(System.in);
		Connection connection = null;
		Statement myStmt = null;
		
		try {
			//connects to database
			connection = DriverManager.getConnection(url, username, password);
			
			
			System.out.println("");
			System.out.println("Create Record");
			System.out.println("");
			
			
			double wholeCost = 0, salePrice = 0;
			
			//collects all product information for new product
			System.out.println();
			System.out.println("Enter the Product Title: ");
			String product_title = scanner.nextLine();
			System.out.println();
			
			System.out.println();
			System.out.println("Enter Product Description: ");
			String product_des = scanner.nextLine();
			System.out.println();
			
			System.out.println();
			System.out.println("Enter the quantity: ");
			int quantity = scanner.nextInt();
			System.out.println();

			System.out.println();
			System.out.println("Enter the Wholesale Cost: ");
			wholeCost = scanner.nextDouble();
			System.out.println();

			System.out.println();
			System.out.println("Enter the Sale Price: ");
			salePrice = scanner.nextDouble();
			System.out.println();

			System.out.println();
			System.out.println("Enter Supplier ID: ");
			String sid = scanner.next();
			System.out.println();

			//reads out values input by user
            System.out.println("You entered the following values:");
            System.out.println();
            System.out.println("Product Title:        " + product_title
					+ "\nProduct Description:        " + product_des
                    + "\nQuantity:          " + quantity
                    + "\nWhole Sale Cost:   " + wholeCost
                    + "\nSale Price:        " + salePrice
                    + "\nSupplier ID:       " + sid);
            System.out.println();
            System.out.println("Is this correct?");
            System.out.print("Type 'yes' to add this record, type 'no' to start over: ");
            String inp = scanner.next();
            boolean valid = false;
            while (!valid) {
            	if (inp.toLowerCase().contentEquals("yes")) {
            		
    				//inserts new product information into database
        			String query = " INSERT INTO new_inventory (product_title, product_description, quantity, sale_price, wholesale_price, supplier_id)" +
        					" VALUES (?, ?, ?, ?, ?, ?)";
        			
        			PreparedStatement prepStmt = connection.prepareStatement(query);
        			prepStmt.setString (1, product_title);
					prepStmt.setString (2, product_des);
        			prepStmt.setInt (3, quantity);
        			prepStmt.setDouble (4, salePrice);
        			prepStmt.setDouble (5, wholeCost);
        			prepStmt.setString (6, sid);

        			
        			prepStmt.execute();
        			
        			displayMenu();
        		
            	}
            	else if (inp.toLowerCase().equals("no")) {
  
            		displayMenu();
            	}
            	else {
            		System.out.print("\nInvalid response. Please type 'yes' or 'no': ");
            	}
            	
            	   // alert user and get next step
                System.out.println();
                System.out.println("Entry added to inventory!");
                System.out.println();
                System.out.println("Do you want to add another entry?");
                System.out.print("Type 'yes' to add another entry, or 'no' to exit to main menu: ");
                inp = scanner.nextLine();
                valid = false;
                while (!valid) {
                    if (inp.toLowerCase().equals("yes")) {
                        valid = true;
                        createRecord();
                    } else if (inp.toLowerCase().equals("no")) {
                        valid = true;                                      
                        displayMenu();
                        
                    } else {
                        System.out.print("Invalid response. Please type 'yes' or 'no': ");
                        inp = scanner.nextLine();
                    }
                }
            }

			

			
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
	}
	
	//method deletes a product record
	public static void deleteRecord() {
		
		Scanner scanner = new Scanner(System.in);
		Connection connection = null;
		Statement myStmt = null;
		
		try {
			
			connection = DriverManager.getConnection(url, username, password);
			
			
			System.out.println("");
			System.out.println("Delete Record");
			System.out.println("");
			
	        System.out.println("");
	        System.out.println("Enter Product ID of the record you would like to delete: ");
	        System.out.println("");
	        String pid = scanner.next();

	
			//queries the product with that specific product id. resultset is made to be able to adjust pointer
			PreparedStatement myStmt2 = connection.prepareStatement("Select * FROM new_inventory WHERE product_id = '" + pid + "'", ResultSet.TYPE_SCROLL_SENSITIVE, 
			ResultSet.CONCUR_UPDATABLE);
	
			ResultSet myRs = myStmt2.executeQuery();
		
			if (myRs.next() == false) {
				System.out.println("");
				System.out.println("Invalid Product ID. Record does not exist.\n");
				return;
			}
			//sets pointer to beginning of query
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
            //checks with user if they want to delete product
			System.out.println("\nAre you sure you want to delete this record?: 'yes' or 'no'");
	        String attribute = scanner.next();
	        
	        if (attribute.equalsIgnoreCase("yes")) {
	        	//deletes product from database
				String query = "DELETE FROM new_inventory WHERE product_id = '" + pid + "'";
				
				PreparedStatement delStmt = connection.prepareStatement(query);
				delStmt.execute();
				
				System.out.println("\nRecord was deleted.\n");
				displayMenu();
	        }
	        
	        else {
	        	System.out.println("\nRecord was NOT deleted.\n");
	        	
	        	displayMenu();
	        	
	        }

			

			
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
	}
	//this method updates a product already in database
	public static void updateRecord() {
		
		Scanner scanner = new Scanner(System.in);
		Connection connection = null;
		Statement myStmt = null;
		
		try {
			//connects to database
			connection = DriverManager.getConnection(url, username, password);
			
			
			System.out.println("");
			System.out.println("Update Record");
			System.out.println("");
			
	        System.out.println("");
	        System.out.println("Enter Product ID of the record you would like to update: ");
	        System.out.println("");
	        String pid = scanner.next();
			//queries the product with that specific product id.
			PreparedStatement myStmt2 = connection.prepareStatement("Select * FROM new_inventory WHERE product_id = '" + pid + "'");
	
			ResultSet myRs = myStmt2.executeQuery();
		
			if (myRs.next() == false) {
				System.out.println("");
				System.out.println("Invalid Product ID. Record does not exist.\n");
				return;
			}
		
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
            
			System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
	        String attribute = scanner.next();
	        //this will select an individual field to update.
	        if (attribute.equalsIgnoreCase("yes")) {
	        	
	        	System.out.println();
				System.out.println();
				System.out.println("Which field would you like to update? ");
				System.out.println("a. Product ID");
				System.out.println("b. Quantity");
				System.out.println("c. Wholesale Price");
				System.out.println("d. Sale Price");
				System.out.println("e. Supplier ID");
				System.out.println("f. Product Title");
				System.out.println("g. Product Description");
				System.out.println();
				
				String ans = scanner.next();
				
				if(ans.contains("a")) {
					
					System.out.println();
					
					System.out.println("Enter the new Product ID: ");
					System.out.println();
					String pid2 = scanner.next();

					System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
			        attribute = scanner.next();
			        
			        if (attribute.equalsIgnoreCase("yes")) {
			        	//updates selected field with new user input
			        	PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET product_id = '" + pid2 +"' WHERE product_id = '"+ pid +"'");
						upStmt2.execute();
						
						System.out.println("Record has been updated.");
						
						displayMenu();
						
			        }
			        
			        else {
			        	System.out.println("\nRecord was NOT updated.\n");
			        	displayMenu();
			        }
					
					
				}
				
				if(ans.contains("b")) {
					
					System.out.println();
					int quantity = 0;
					System.out.println("Enter the new quantity: ");
					System.out.println();
					while (!scanner.hasNextInt()) {
				          System.out.println("Quantity must be a whole number!");
				          System.out.println("Enter Quantity: ");
				          quantity = scanner.nextInt();
						}
					quantity = scanner.nextInt();
					
					System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
			        attribute = scanner.next();
			        
			        if (attribute.equalsIgnoreCase("yes")) {
			        	//updates selected field with new user input
			        	PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET quantity = '" + quantity +"' WHERE product_id = '"+ pid +"'");
						upStmt2.execute();
						
						System.out.println("Record has been updated.");
						
						displayMenu();
						
			        }
			        
			        else {
			        	System.out.println("\nRecord was NOT updated.\n");
			        	displayMenu();
			        }
				
					
				}
				
				if(ans.contains("c")) {
					
					System.out.println();
					double wholeCost = 0;
					System.out.println("Enter the new Wholesale Price: ");
					System.out.println();
					while (!scanner.hasNextDouble()) {
				          System.out.println("Wholesale Price must be a whole number or decimal!");
				          System.out.println("Enter Wholesale Price: ");
				          wholeCost = scanner.nextDouble();
						}
					wholeCost = scanner.nextDouble();
					
					System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
			        attribute = scanner.next();
			        
			        if (attribute.equalsIgnoreCase("yes")) {
			        	//updates selected field with new user input
			        	PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET wholesale_price = '" + wholeCost +"' WHERE product_id = '"+ pid +"'");
						upStmt2.execute();
						
						System.out.println("Record has been updated.");
						
						displayMenu();
						
			        }
			        
			        else {
			        	System.out.println("\nRecord was NOT updated.\n");
			        	displayMenu();
			        }
					
					
					
					
				}
						
				if(ans.contains("d")) {
					
					System.out.println();
					double salePrice = 0;
					System.out.println("Enter the Sale Price: ");
					while (!scanner.hasNextDouble()) {
			          System.out.println("Sale Price must be a whole number or decimal!");
			          System.out.println("Enter the Sale Price: ");
			          salePrice = scanner.nextDouble();
					}
					salePrice = scanner.nextDouble();
					
					System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
			        attribute = scanner.next();
			        
			        if (attribute.equalsIgnoreCase("yes")) {
			        	//updates selected field with new user input
						PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET sale_price = '" + salePrice +"' WHERE product_id = '"+ pid +"'");
						upStmt2.execute();
						
						System.out.println("Record has been updated.");
						
						displayMenu();
						
			        }
			        
			        else {
			        	System.out.println("\nRecord was NOT updated.\n");
			        	displayMenu();
			        }

				}
				if(ans.contains("e")) {
					
					System.out.println();
					
					System.out.println("Enter the new Supplier ID: ");
					System.out.println();
					String sid = scanner.next();
					while((sid.length() != 8)) {
						System.out.println("Supplier ID must be 8 characters long!");
						System.out.println("Enter the new Supplier ID: ");
						sid = scanner.next();
					}
					
					System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
			        attribute = scanner.next();
			        
			        if (attribute.equalsIgnoreCase("yes")) {
			        	//updates selected field with new user input
			        	PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET supplier_id = '" + sid +"' WHERE product_id = '"+ pid +"'");
						upStmt2.execute();
						
						System.out.println("Record has been updated.");
						
						displayMenu();
						
			        }
			        
			        else {
			        	System.out.println("\nRecord was NOT updated.\n");
			        	displayMenu();
			        }
					
					
				}

				if(ans.contains("f")) {
					scanner.nextLine();
					System.out.println();
					System.out.println("Enter the Product Title: ");
					String product_title = scanner.nextLine();
					System.out.println();
					
					System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
			        attribute = scanner.next();
			        
			        if (attribute.equalsIgnoreCase("yes")) {
			        	//updates selected field with new user input
			        	PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET product_title = '" + product_title +"' WHERE product_id = '"+ pid +"'");
						upStmt2.execute();
						
						System.out.println("Record has been updated.");
						
						displayMenu();
						
			        }
			        
			        else {
			        	System.out.println("\nRecord was NOT updated.\n");
			        	displayMenu();
			        }
					
					
				}

				if(ans.contains("g")) {
					
					scanner.nextLine();
					System.out.println();
					System.out.println("Enter the new Product Description: ");
					System.out.println();
					String prod_des = scanner.nextLine();
					
					System.out.println("\nAre you sure you want to update this record?: 'yes' or 'no'");
			        attribute = scanner.next();
			        
			        if (attribute.equalsIgnoreCase("yes")) {
			        	//updates selected field with new user input
			        	PreparedStatement upStmt2 = connection.prepareStatement("UPDATE new_inventory SET product_description = '" + prod_des +"' WHERE product_id = '"+ pid +"'");
						upStmt2.execute();
						
						System.out.println("Record has been updated.");
						
						displayMenu();
						
			        }
			        
			        else {
			        	System.out.println("\nRecord was NOT updated.\n");
			        	displayMenu();
			        }
					
					
				}
	        }
	        

			

			
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
		
	}
	}

	

