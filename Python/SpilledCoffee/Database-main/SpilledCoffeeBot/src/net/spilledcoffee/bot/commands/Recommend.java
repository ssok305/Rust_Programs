package net.spilledcoffee.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.spilledcoffee.bot.CoffeeBot;
import org.jetbrains.annotations.NotNull;
import java.sql.*;

public class Recommend extends ListenerAdapter {

    // When a private message is received
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        // Separates commands into a list
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // If user enters -recommend, bot outputs recommended items
        if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "recommend")){
            event.getChannel().sendTyping().queue();
            // Delay bot Response
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // General message to user
            event.getChannel().sendMessage("Here is a list of my recommended products:").queue();

            try {
                // Establish a connection to SQL database, and prepare a SQL statement
                Connection connection = DriverManager.getConnection(CoffeeBot.url, CoffeeBot.username, CoffeeBot.password);

                // Select 4 random products from our database inventory, then execute the statement
                PreparedStatement myStmt = connection.prepareStatement("Select * FROM new_inventory ORDER BY RAND() LIMIT 4;");
                ResultSet myRs = myStmt.executeQuery();

                //Build an embed
                EmbedBuilder rec = new EmbedBuilder();

                if (myRs.next()) {

                    // Apply product details into embeds
                    rec.setTitle(myRs.getString("product_title")+"\n");
                    rec.setDescription(
                            "Product Description: " + myRs.getString("product_description")+"\n"+
                                    "Quantity: " + myRs.getString("quantity")+"\n"+
                                    "Sale Price: $" + myRs.getString("sale_price")+"\n"+
                                    "Product ID: " + myRs.getString("product_id")+"\n"
                    );
                    rec.setColor(0xfae934);
                    rec.setFooter("Visit us: Spilledcoffee.net");

                    //Send the embed to the user
                    event.getChannel().sendMessage(rec.build()).queue();
                    rec.clear();

                    while (myRs.next()) {

                        rec.setTitle(myRs.getString("product_title")+"\n");
                        rec.setDescription(
                                "Product Description: " + myRs.getString("product_description")+"\n"+
                                        "Quantity: " + myRs.getString("quantity")+"\n"+
                                        "Sale Price: $" + myRs.getString("sale_price")+"\n"+
                                        "Product ID: " + myRs.getString("product_id")+"\n"
                        );
                        rec.setColor(0xfae934);
                        rec.setFooter("Visit us: Spilledcoffee.net");
                        event.getChannel().sendMessage(rec.build()).queue();
                        rec.clear();

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
