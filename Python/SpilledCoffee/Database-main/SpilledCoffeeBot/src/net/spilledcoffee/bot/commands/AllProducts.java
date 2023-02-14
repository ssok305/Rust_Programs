package net.spilledcoffee.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.spilledcoffee.bot.CoffeeBot;
import org.jetbrains.annotations.NotNull;
import java.sql.*;


public class AllProducts extends ListenerAdapter {

    // When a private message is received
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        // Separates commands into a list
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // If user enters -allproducts, bot returns all products available at Spilled Coffee.
        if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "allproducts")) {
            event.getChannel().sendTyping().queue();
            // Delay bot Response
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Sends general messages notifying user of products.
            event.getChannel().sendMessage("For a better view at our products please visit our website at (https://spilledcoffee.net/products)").queue();
            event.getChannel().sendMessage("\n**Here is the list of all our products: **").queue();

            try {
                // Establish a connection to SQL database, and prepare a SQL statement
                Connection connection = DriverManager.getConnection(CoffeeBot.url, CoffeeBot.username, CoffeeBot.password);
                // Select all products from our database
                PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_inventory", ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                // Execute statement
                ResultSet myRs = myStmt.executeQuery();

                //Build an embed
                EmbedBuilder allPros = new EmbedBuilder();
                if (myRs.next()) {

                    // Apply product details into embeds
                    allPros.setTitle(myRs.getString("product_title")+"\n");
                    allPros.setDescription(
                   "Product Description: " + myRs.getString("product_description")+"\n"+
                   "Quantity: " + myRs.getString("quantity")+"\n"+
                   "Sale Price: " + myRs.getString("sale_price")+"\n"+
                            "Product ID: " + myRs.getString("product_id")+"\n"
                    );
                    allPros.setColor(0xf558ed);
                    allPros.setFooter("Visit us: Spilledcoffee.net");

                    //Send the embed to the user
                    event.getChannel().sendMessage(allPros.build()).queue();
                    allPros.clear();


                    while (myRs.next()) {

                        allPros.setTitle(myRs.getString("product_title")+"\n");
                        allPros.setDescription(
                                        "Product Description: " + myRs.getString("product_description")+"\n"+
                                        "Quantity: " + myRs.getString("quantity")+"\n"+
                                        "Sale Price: " + myRs.getString("sale_price")+"\n"+
                                "Product ID: " + myRs.getString("product_id")+"\n"
                        );
                        allPros.setColor(0xf558ed);
                        allPros.setFooter("Visit us: Spilledcoffee.net");
                        event.getChannel().sendMessage(allPros.build()).queue();
                        allPros.clear();

                    }
                    // Notify user when done loading products
                    event.getChannel().sendMessage("===========================\n").queue();
                    event.getChannel().sendMessage("*** DONE LOADING LIST OF PRODUCTS ***").queue();
                }
                else {
                    // Error Message
                    event.getChannel().sendMessage("There was an error somewhere!\n").queue();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                event.getChannel().sendMessage("Oops, Error!\n").queue();
                e.printStackTrace();
            }
        }
    }
}