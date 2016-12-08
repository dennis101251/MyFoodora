package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.CommandLine;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by dennis101251 on 2016/12/1.
 */
public class CommandLineTest {

    @Test
    public void CustomerTest() throws UserNotFoundException {
        CommandLine commandLine = new CommandLine();

        String input = "login WDY 501\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        commandLine.run();

//        String input = "login WDY 501\n";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);

        input = "quit\n";
        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        System.out.println();
    }
}