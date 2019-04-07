package com.company;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class FirstTest {

    private Main main = new Main();

    @Test
    public void test(){
        int[][] req = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] expected = {{3,4,5},{6,7,8},{9,1,2}};

        int[][] actual = Main.shiftLeft(req, 1);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test2(){
        int[][] arg1 = {{1,0,1,1},{0,0,1,1}};
        int[][] arg2 = {{0,0,0,1},{1,0,1,0}};
        int[][] expected = {{1,0,1,0},{1,0,0,1}};

        int[][] actual = main.xorForEachElement(arg1, arg2);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test3(){
        int[][] req = {{1,0,1,1,0,0},
                {1,0,0,0,1,1},
                {0,1,1,1,1,0},
                {0,0,1,1,1,0},
                {1,0,0,1,0,0},
                {0,1,1,1,0,1},
                {1,0,1,0,0,1},
                {1,0,0,1,0,1}};
        int[][] expected = {{0,0,1,0},
                {1,0,1,0},
                {0,1,1,0},
                {1,0,1,0},
                {0,1,1,0},
                {0,0,1,1},
                {0,1,1,1},
                {0,0,0,0}};

        int[][] actual = main.resultDivide(req);
        assertArrayEquals(expected, actual);
    }
}
