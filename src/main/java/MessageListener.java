import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Includes basic Methods around Message handling.
 */
public class MessageListener extends ListenerAdapter {

    /**
     * onMessageReceived just evaluates if the Message was sent by a bot and decides
     * which actions to perform. These are in this Version:
     * - delete Invalid Comments
     *
     * @param event Decim registered, that a message was sent
     */
    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        } else {
            deleteInvalidCommands(event);
        }
    }

    /**
     * Removes Messages, that aren't valid commands in the listen Channel.
     *
     * @param event Decim registered, that a message was sent
     */
    public void deleteInvalidCommands(final MessageReceivedEvent event) {
        ArrayList<String> commands = getCmdList(event.getJDA());
        String[] messageParts = event.getMessage().getContentRaw().split(" ");
        for (String messagePart : messageParts) {
            if (!commands.contains(messagePart)) {
                event.getMessage().delete().queue();
            }
        }
    }

    /**
     * Parses a command list from the Last Message in a pre-defined Channel.
     *
     * @param jda current JDA
     * @return List of commands
     */
    private ArrayList<String> getCmdList(final JDA jda) {
        List<Message> messages = jda.getTextChannelById(getCommandChannelID(jda)).getHistory().getRetrievedHistory();
        TextChannel test = jda.getTextChannelById(getCommandChannelID(jda));
        String[] commandMessage = test.getMessageById(test.getLatestMessageId()).complete().getContentRaw().split(" ");
        if (commandMessage.length == 0) {
            return new ArrayList<>();
        }
        ArrayList<String> commands = new ArrayList<>();

        String pattern = "!";
        Pattern p = Pattern.compile(pattern);
        for (String word : commandMessage) {
            Matcher m = p.matcher(word);
            if (m.find()) {
                commands.add(word);
            }
        }
        return commands;
    }

    /**
     * Gets the channel ID for the predefined Command Channel.
     * @param jda
     * @return
     */
    private long getCommandChannelID(final JDA jda) {
        List<TextChannel> channels = jda.getTextChannels();
        for (TextChannel channel : channels) {
            if (channel.getName().equals("commands")) {
                return channel.getIdLong();
            }
        }
        return -1;
    }
}

