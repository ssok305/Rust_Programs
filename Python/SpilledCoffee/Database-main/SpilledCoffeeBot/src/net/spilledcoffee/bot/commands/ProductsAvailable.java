package net.spilledcoffee.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.spilledcoffee.bot.CoffeeBot;
import org.jetbrains.annotations.NotNull;
import java.sql.*;


public class ProductsAvailable extends ListenerAdapter {

    // When a private message is received
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        // Put user's input into a list
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // If user enters -productsavailable, bot should output filtered products available
        // for less than a given price (provided by the user in the command)
        if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "productsavailable")) {
            event.getChannel().sendTyping().queue();
            // Delay bot Response
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // variable holding the user's filtered price as a float
            float userFilteredPrice = Float.parseFloat(args[1]);

            // Send general messages notifying user of filtered price and products.
            event.getChannel().sendMessage("Filtered Price: $" + userFilteredPrice).queue();
            event.getChannel().sendMessage("Displaying Products less than $" + userFilteredPrice + ": \n").queue();

            try {
                // Establish a connection to SQL database, and prepare a SQL statement
                Connection connection = DriverManager.getConnection(CoffeeBot.url, CoffeeBot.username, CoffeeBot.password);
                // Select all from database in which the product's quantity is greater than zero,
                // and the product's price is less than the user's filtered price.
                PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_inventory WHERE sale_price < '" + userFilteredPrice + "' AND quantity > 0", ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

                // execute statement
                ResultSet myRs = myStmt.executeQuery();

                //Build an embed
                EmbedBuilder ProsAvailable = new EmbedBuilder();

                if (myRs.next()) {

                    // Apply product details into embeds
                    ProsAvailable.setTitle(myRs.getString("product_title")+"\n");
                    ProsAvailable.setDescription(
                            "Product Description: " + myRs.getString("product_description")+"\n"+
                                    "Quantity: " + myRs.getString("quantity")+"\n"+
                                    "Sale Price: $" + myRs.getString("sale_price")+"\n"+
                                    "Product ID: " + myRs.getString("product_id")+"\n"
                    );
                    ProsAvailable.setColor(0x38d2fc);
                    ProsAvailable.setFooter("Visit us: Spilledcoffee.net");

                    //Send the embed to the user
                    event.getChannel().sendMessage(ProsAvailable.build()).queue();
                    ProsAvailable.clear();

                    while (myRs.next()) {

                        // Apply product details into embeds
                        ProsAvailable.setTitle(myRs.getString("product_title")+"\n");
                        ProsAvailable.setDescription(
                                "Product Description: " + myRs.getString("product_description")+"\n"+
                                        "Quantity: " + myRs.getString("quantity")+"\n"+
                                        "Sale Price: $" + myRs.getString("sale_price")+"\n"+
                                        "Product ID: " + myRs.getString("product_id")+"\n"
                        );
                        ProsAvailable.setColor(0x38d2fc);
                        ProsAvailable.setFooter("Visit us: Spilledcoffee.net");

                        //Send the embed to the user
                        event.getChannel().sendMessage(ProsAvailable.build()).queue();
                        ProsAvailable.clear();
                    }
                    // Notify user when done loading products
                    event.getChannel().sendMessage("===========================\n").queue();
                    event.getChannel().sendMessage("*** DONE LOADING LIST OF PRODUCTS ***").queue();
                }
                else {
                    // notify user if an error occurred
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
