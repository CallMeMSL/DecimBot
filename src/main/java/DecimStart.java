import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

/**
 * Startup for the Bot
 */
public class DecimStart {

    @SuppressWarnings("SpellCheckingInspection")
    final static private String decimToken = "NTMwNDk3ODczNTY2MTA1NjAy.DxAjVg.An2kwJqSMQO8mXmQajsedvhG_Os";

    /**
     * Initialises Decim.
     *
     * @param args Programm parameters. Not in use atm
     * @throws LoginException       thrown, if login was unsuccessful
     * @throws InterruptedException catches various errors
     */
    public static void main(String[] args)
            throws LoginException, InterruptedException {
        JDA jda = new JDABuilder(AccountType.BOT).setToken(decimToken).buildBlocking();
        jda.addEventListener(new MessageListener());
    }
}
