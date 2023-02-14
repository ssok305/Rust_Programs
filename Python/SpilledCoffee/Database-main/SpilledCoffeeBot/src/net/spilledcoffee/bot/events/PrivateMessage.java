package net.spilledcoffee.bot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.spilledcoffee.bot.CoffeeBot;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class PrivateMessage extends ListenerAdapter {

    @Override
    //When a new user joins the Spilled Coffee server channel
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        List<GatewayIntent> gatewayIntents = new ArrayList<>();

        // Add member
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        JDABuilder jdaBuilder = JDABuilder.createDefault(CoffeeBot.TOKEN);
        jdaBuilder.enableIntents(gatewayIntents);

        // Send a direct message to user
        event.getMember().getUser().openPrivateChannel().queue(channel -> {
            // Message is an embed, is the embed details
            EmbedBuilder welcome = new EmbedBuilder();
            welcome.setTitle("☕Spilled Coffee: Bot Information");
            welcome.setDescription("Welcome, to Spilled Coffee's Server. Here you will be able " +
                    "to input different commands for your coffee needs, and our bot will be at your service!" +
                    "\nIf you need further help or command explanations, please enter '-help' in your chat.");
            welcome.addField("Commands", "-help\n-recommend\n-productsavailable\n-pastorders\n-allproducts\n", false);
            welcome.setColor(0xeff542);
            welcome.setFooter("Visit us at 'spilledcoffee.net'");
            //Send embed message to the user
            channel.sendMessage(welcome.build()).queue();
        });
    }
}
