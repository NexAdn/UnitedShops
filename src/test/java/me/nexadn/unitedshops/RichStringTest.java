package me.nexadn.unitedshops;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RichStringTest {

    @Test
    public void test() {
        RichString string = new RichString("Hello, ${username}! I am ${myname}");
        string.arg("username", "User").arg("myname", "computer");
        assertEquals("Hello, User! I am computer", string.str());
    }

}