package net.spilledcoffee.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.spilledcoffee.bot.CoffeeBot;
import org.jetbrains.annotations.NotNull;

public class Help extends ListenerAdapter {

    // All when a private message is received
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        // Separates user's input into a list
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        // If user enters -help, bot outputs an Embed highlighting helpful information about different commands
        if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "help") && (args.length ==1)) {
            // Creates an embed with information regarding the help command.
            EmbedBuilder info = new EmbedBuilder();
            info.setTitle("☕Spilled Coffee: Bot Help Desk");
            info.setDescription("Joining this server allows you to enter known commands into your chat box, then our bot will help you " +
                    "execute some of those commands. This server is dedicated to fulfill some of our customers needs at the convenience of Discord." +
                    "\nBelow are accepted commands: ");
            info.addField("Commands", "-help\n-recommend\n-pastorders\n-productsavailable\n-allproducts\n", false);
            info.addField("Joining", "When you join the server our bot will immediately send you a private direct message, that is where you will enter your desired commands!\n", false);
            info.addField("Extra Notes: ", "For the bot to recognize a command you must enter a '-' in front of the command!\n\nIf you need specific information on how to use a specific command, " +
                    "enter in your chat the help command along with the desired command. \nFor example: '-help [-Command]' (without the brackets) ", false);
            info.setColor(0x5afc03);
            info.setFooter("Helping");

            // send typing icon
            event.getChannel().sendTyping().queue();
            // Delay bot Response
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // send embed message
            event.getChannel().sendMessage(info.build()).queue();
            // embed clear
            info.clear();
        }

        // If user enters -help, along with the command productsavailable,
        // it will output information regarding that specific command.
        else if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                && args[1].equalsIgnoreCase(CoffeeBot.prefix + "productsavailable") ||
                args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                        && args[1].equalsIgnoreCase( "productsavailable")) {
            // Create an embed with information regarding the productsavailable command.
            EmbedBuilder helpAvailable = new EmbedBuilder();
            helpAvailable.setTitle("Products Available Command Information:");
            helpAvailable.setDescription("The products available command allows the user to see a list of our products, " +
                    "filtered by a specific price (specified by the user)." +
                    "To apply the command, the user must enter the productsavailable command and the desired price to filter the products by.");
            helpAvailable.addField("Format:", "-productsavailable [Price to filter products by]\n(Without brackets)", false);
            helpAvailable.addField("Note:", "Price are in U.S. dollars.", false);
            helpAvailable.setColor(0xffa640);
            //send embed message
            event.getChannel().sendMessage(helpAvailable.build()).queue();
            // embed clear
            helpAvailable.clear();
        }

        // If user enters -help, along with the command pastorders,
        // it will output information on how to use that specific command
        else if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                && args[1].equalsIgnoreCase(CoffeeBot.prefix + "pastorders") ||
                args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                        && args[1].equalsIgnoreCase( "pastorders")) {
            // Create an embed with information regarding the pastorders command.
            EmbedBuilder helpPastOrders = new EmbedBuilder();
            helpPastOrders.setTitle("Past Orders Command Information:");
            helpPastOrders.setDescription("The pastorders command allows users to retrieve a list of their past submitted orders" +
                    " from Spilled Coffee. " +
                    "To apply the command, simply enter the pastorders command, along with the email associated with your account (the customer).");
            helpPastOrders.addField("Format:", "-pastorders [customer's email]\n(Without brackets)", false);
            helpPastOrders.setColor(0xffa640);
            // send embed message
            event.getChannel().sendMessage(helpPastOrders.build()).queue();
            // embed clear
            helpPastOrders.clear();
        }

        // If user enters -help, along with the recommend command,
        // it will output information on how to use that specific command
        else if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                && args[1].equalsIgnoreCase(CoffeeBot.prefix + "recommend") ||
                args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                        && args[1].equalsIgnoreCase( "recommend")) {
            // Create an embed with information regarding the recommend command.
            EmbedBuilder helpRecommend = new EmbedBuilder();
            helpRecommend.setTitle("Recommend Command Information:");
            helpRecommend.setDescription("The recommend command simply asks the Spilled coffee bot " +
                    "to recommend a few Spilled Coffee products to the user." +
                    "To apply the command, simply enter the recommend command.");
            helpRecommend.addField("Format:", "-recommend", false);
            helpRecommend.addField("Note:", "As of right now, the bot recommends 4 products.", false);
            helpRecommend.setColor(0xffa640);
            // send embed message
            event.getChannel().sendMessage(helpRecommend.build()).queue();
            // embed clear
            helpRecommend.clear();
        }

        // If user enters -help, along with the allproducts command,
        // it will output information on how to use that specific command
        else if (args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                && args[1].equalsIgnoreCase(CoffeeBot.prefix + "allproducts") ||
                args[0].equalsIgnoreCase(CoffeeBot.prefix + "help")
                && args[1].equalsIgnoreCase( "allproducts")) {
            // Create an embed with information regarding the recommend command.
            EmbedBuilder helpAllPros = new EmbedBuilder();
            helpAllPros.setTitle("All Products Command Information:");
            helpAllPros.setDescription("The allproducts command simply asks the Spilled coffee bot " +
                    "to return all of the current products available at Spilled Coffee." +
                    "To apply the command, simply enter the allproducts command.");
            helpAllPros.addField("Format:", "-allproducts", false);
            helpAllPros.addField("Note:", "For a better view at our products please visit our website:\nSpilledcoffee.net", false);
            helpAllPros.setColor(0xffa640);
            // send embed message
            event.getChannel().sendMessage(helpAllPros.build()).queue();
            // embed clear
            helpAllPros.clear();
        }
    }
}
