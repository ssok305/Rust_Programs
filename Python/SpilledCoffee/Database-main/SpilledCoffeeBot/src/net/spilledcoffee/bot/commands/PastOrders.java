package net.spilledcoffee.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.spilledcoffee.bot.CoffeeBot;
import org.jetbrains.annotations.NotNull;
import java.sql.*;

public class PastOrders extends ListenerAdapter {

    // When a private message is received
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        // Put user's input into a list
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // If user enters -pastorders, bot should output user's pastorders
        if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "pastorders")){
            event.getChannel().sendTyping().queue();
            // Delay bot Response
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Send general messages notifying user of entered email and orders.
            event.getChannel().sendMessage("Email: " + args[1]).queue();
            event.getChannel().sendMessage("These are your previous orders: \n").queue();

            try {
                // Establish a connection to SQL database, and prepare a SQL statement
                Connection connection = DriverManager.getConnection(CoffeeBot.url, CoffeeBot.username, CoffeeBot.password);//Statement myStmt = connection.createStatement();
                // Select all products from customer orders database pertaining to given email of customer.
                PreparedStatement myStmt = connection.prepareStatement("Select * FROM customer_orders2 WHERE cust_email = '" + args[1] + "'");
                //Execute statement
                ResultSet myRs = myStmt.executeQuery();

                //Build an embed
                EmbedBuilder pastOs = new EmbedBuilder();

                if (myRs.next()) {

                    // Apply previous orders details into embeds
                    pastOs.setTitle("Order: " + "\n");
                    pastOs.setDescription(
                            "Date: " + myRs.getString("date")+"\n"+
                    "Email: " + myRs.getString("cust_email")+"\n"+
                    "Zip Code: " + myRs.getString("cust_location")+"\n"+
                    "Product ID: " + myRs.getString("product_id")+"\n"+
                    "Quantity: " + myRs.getString("product_quantity")+"\n"+
                    "Order ID: " + myRs.getString("order_id")+"\n"
                    );
                    pastOs.setColor(0xfc3a64);
                    pastOs.setFooter("Visit us: Spilledcoffee.net");

                    //Send the embed to the user
                    event.getChannel().sendMessage(pastOs.build()).queue();
                    pastOs.clear();

                    while (myRs.next()) {

                        // Apply previous orders details into embeds
                        pastOs.setTitle("Order: " + "\n");
                        pastOs.setDescription(
                                "Date: " + myRs.getString("date")+"\n"+
                                        "Email: " + myRs.getString("cust_email")+"\n"+
                                        "Zip Code: " + myRs.getString("cust_location")+"\n"+
                                        "Product ID: " + myRs.getString("product_id")+"\n"+
                                        "Quantity: " + myRs.getString("product_quantity")+"\n"+
                                        "Order ID: " + myRs.getString("order_id")+"\n"
                        );
                        pastOs.setColor(0xfc3a64);
                        pastOs.setFooter("Visit us: Spilledcoffee.net");

                        //Send the embed to the user
                        event.getChannel().sendMessage(pastOs.build()).queue();
                        pastOs.clear();
                    }
                    // Notify user when orders are done loading
                    event.getChannel().sendMessage("===========================\n").queue();
                    event.getChannel().sendMessage("*** DONE LOADING PREVIOUS ORDERS ***").queue();
                }
                else{
                    //Error Message
                    event.getChannel().sendMessage("Sorry, Email was not found.\n").queue();
                }
            }catch (SQLException e) {
                // TODO Auto-generated catch block
                event.getChannel().sendMessage("Oops, Error!\n").queue();
                e.printStackTrace();
            }
        }
    }
}
