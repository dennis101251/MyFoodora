package fr.ecp.IS1220.group5.project.util;

import java.util.GregorianCalendar;

/**
 * Created by dennis101251 on 2017/1/6.
 */
public class Date {
    static public GregorianCalendar date(String string){
        String[] commands = string.split(",");
        return new GregorianCalendar(Integer.parseInt(commands[0]),Integer.parseInt(commands[1]) - 1, Integer.parseInt(commands[2]));
    }
}
