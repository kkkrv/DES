package com.company;

public class ArrayConverter {
    static public int[] to1D(int[][] array) {

        int[] newArray = new int[array.length * array[0].length];

        for (int i = 0; i < array.length; ++i)
            for (int j = 0; j < array[i].length; ++j) {
                newArray[i * array[0].length + j] = array[i][j];
            }

        return newArray;
    }

    static public int[][] to2D(int[] array, int width) {

        int[][] newArray = new int[array.length / width][width];

        for (int i = 0; i < array.length; ++i) {
            newArray[i / width][i % width] = array[i];
        }

        return newArray;
    }

    public static void shiftLeft(int[] array, int amount) {
        for (int j = 0; j < amount; j++) {
            int a = array[0];
            int i;
            for (i = 0; i < array.length - 1; i++)
                array[i] = array[i + 1];
            array[i] = a;
        }
    }

    public static void shiftRight(int[] array, int amount) {
        for (int j = 0; j < amount; j++) {
            int a = array[array.length - 1];
            int i;
            for (i = array.length - 1; i > 0; i--)
                array[i] = array[i - 1];
            array[i] = a;
        }
    }
}
