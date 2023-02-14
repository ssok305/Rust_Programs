package net.spilledcoffee.bot;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.spilledcoffee.bot.commands.*;
import net.spilledcoffee.bot.events.PrivateMessage;
import javax.security.auth.login.LoginException;

// Main Class Driver
public class CoffeeBot extends ListenerAdapter {
    public static JDA jda;
    public static String prefix = "-"; // Command prefix (indicating that it's a command)

    // TOKEN-REMOVE
    public static final String TOKEN = "Token here";

    // Database Credentials-REMOVE
    public static String url = "Credentials Here";
    public static String username = "Credentials Here";
    public static String password = "Credentials Here";

    //Main method
    public static void main(String[] args)throws LoginException{
        // JDA Builder
        JDA jda = JDABuilder.createDefault(TOKEN).build();

        // Set status and Activity
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.getPresence().setActivity(Activity.watching("Humans Do Stuff."));


        // Communicating with classes to run them.
        // jda object listening to appropriate events
        jda.addEventListener(new PrivateMessage());
        jda.addEventListener(new Help());
        jda.addEventListener(new Recommend());
        jda.addEventListener(new PastOrders());
        jda.addEventListener(new ProductsAvailable());
        jda.addEventListener(new AllProducts());

    }
}
