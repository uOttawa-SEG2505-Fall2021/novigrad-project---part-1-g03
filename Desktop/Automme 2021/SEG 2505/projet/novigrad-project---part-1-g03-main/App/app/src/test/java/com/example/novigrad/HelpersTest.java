package com.example.novigrad;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * HelpersTest
 */
public class HelpersTest {
    @Test
    // test that the time methods work properly
    public void testTimeGetters() {
        assertEquals(Helpers.getMinutes(90),30);
        assertEquals(Helpers.getHours(90),1);
        assertEquals(Helpers.getMinutes(121),1);
        assertEquals(Helpers.getHours(121),2);

        //test that the default values for the succ times are 9-17/9am-5pm, as expected
        assertEquals(Helpers.getMinutes(540),0);
        assertEquals(Helpers.getHours(540),9);
        assertEquals(Helpers.getMinutes(1020),0);
        assertEquals(Helpers.getHours(1020),17);
    }

    @Test
    public void testTimeApprox() {
        assertEquals(Helpers.approximateTime(59), 60);
        assertEquals(Helpers.approximateTime(50),45);
        assertEquals(Helpers.approximateTime(39),45);
        assertEquals(Helpers.approximateTime(32),30);
        assertEquals(Helpers.approximateTime(24),30);
        assertEquals(Helpers.approximateTime(22),15);
        assertEquals(Helpers.approximateTime(9),15);
        assertEquals(Helpers.approximateTime(7),0);
    }

    @Test
    public void testFormatMethods() {
        assertEquals(Helpers.formatHHmm(0),"00:00");
        assertEquals(Helpers.formatHHmm(121),"02:01");
        assertEquals(Helpers.formatHHmm(599),"09:59");
        assertEquals(Helpers.formatHHmm(1234),"20:34");
        assertEquals(Helpers.formatHHmm(754),"12:34");
        assertEquals(Helpers.formatHHmm(540),"09:00");
        assertEquals(Helpers.formatHHmm(1020),"17:00");
    }
}
