package fr.ecp.IS1220.group5.project;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dennis101251 on 2016/11/27.
 */
public class UserFactoryTest {
    @Test
    public void foundRepeatUserame() throws Exception {
        String username = "dennis101251";
        String username2 = "mk";
        UserFactory userFactory = new UserFactory();

        Assert.assertTrue(userFactory.foundRepeatUserame(username));
        Assert.assertTrue(!userFactory.foundRepeatUserame(username2));
    }

}