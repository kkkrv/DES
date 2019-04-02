package com.company;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class FirstTest {

    @Test
    public void test(){
        int[][] req = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] expected = {{3,4,5},{6,7,8},{9,1,2}};

        int[][] actual = Main.shiftLeft(req, 3);
        assertArrayEquals(expected, actual);
    }
}
