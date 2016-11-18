package fr.ecp.IS1220.group5.project;

import java.io.IOException;

/**
 * Created by dennis101251 on 2016/11/18.
 */
public abstract class UserFactory {
    public abstract User createUser() throws IOException;

    /**
     * My idea is that putting the registering process into the UserFactory
     * because different kinds of user have different parameter to setup
     *
     * We have four kinds of factory
     *
     * When we create a new user, then we pass the user to system to save;
     * */
}
