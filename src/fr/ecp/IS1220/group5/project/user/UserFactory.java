package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.user.User;
import fr.ecp.IS1220.group5.project.user.Userlist;

import java.io.IOException;

/**
 * Created by dennis101251 on 2016/11/18.
 */
public class UserFactory {
    public User createUser() throws IOException {
        return null;
    }

    /**
     * Common method
     */

    public boolean foundRepeatUserame(String username) {
        Userlist tempUserlist = new Userlist();
        tempUserlist.retrieveUsers();
        boolean repeatFound = false;

        for (User user : tempUserlist.getUsers()
                ) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                repeatFound = true;
            }
        }
        return repeatFound;
    }

    /**
     * My idea is that putting the registering process into the UserFactory
     * because different kinds of user have different parameter to setup
     *
     * We have four kinds of factory
     *
     * When we create a new user, then we pass the user to system to save;
     * */
}
