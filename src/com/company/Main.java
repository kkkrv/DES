package com.company;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int iteration = 16; //количество раундов

        String[] Blocks; //сами блоки в двоичном формате
    }

    private String increaseStringLength(String input)
    {
        char symbToStringEnd;
        symbToStringEnd = '0';
        int sizeOfChar = 16; //размер одного символа (in Unicode 16 bit)
        int sizeOfBlock = 64; //в DES размер блока 64 бит, но поскольку в unicode символ в два раза длинее, то увеличим блок тоже в два раза
        while (((input.length() * sizeOfChar) % sizeOfBlock) != 0)
            input += symbToStringEnd;
        return input;
    }


    private void BreakIntoBlocks(String input)
    {
        int sizeOfChar = 16; //размер одного символа (in Unicode 16 bit)
        int sizeOfBlockInBits = 64;
        int amountOfBlocks = (input.length() * sizeOfChar) / sizeOfBlockInBits;
        String[] Blocks = new String[amountOfBlocks];
        int sizeOfBlockInChars = input.length() / amountOfBlocks;

        for (int i = 0; i < amountOfBlocks; i++)
        {
            Blocks[i] = input.substring(i * sizeOfBlockInChars, i * sizeOfBlockInChars + sizeOfBlockInChars);
            Blocks[i] = stringToBinary(Blocks[i]);
        }
    }
    public String stringToBinary(String s) {
        StringBuilder binaryString = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            binaryString.append(Integer.toBinaryString(c));
        }
        return binaryString.toString();
    }

    private char[][] matrixP (char[] T) {
        int[][] P = {
                {58, 50, 42, 34, 26, 18, 10, 2},
                {60, 52, 44, 36, 28, 20, 12, 4},
                {62, 54, 46, 38, 30, 22, 14, 6},
                {64, 56, 48, 40, 32, 24, 16, 8},
                {57, 49, 41, 33, 25, 17, 9, 1},
                {59, 51, 43, 35, 27, 19, 11, 3},
                {61, 53, 45, 37, 29, 21, 13, 5},
                {63, 55, 47, 39, 31, 23, 15, 07}
        };

        char[][] changedT = new char[8][8];
        for(int i = 0; i < P.length; i++){
            for(int j = 0; j <  P[0].length; j++){
                changedT[i][j] = T[P[i][j]];
            }
        }
        return changedT;
    }

    private char[][] getL (char[][] T){
        char[][] L = new char[8][4];
        for(int i = 0; i < T.length; i++) {
            for (int j = 0; j < T[0].length/2; j++) {
                L[i][j] = T[i][j];
            }
        }
        return L;
    }

    private char[][] getR (char[][] T){
        char[][] R = new char[8][4];
        for(int i = 0; i < T.length; i++) {
            for (int j = 4; j < T[0].length; j++) {
                R[i][j] = T[i][j];
            }
        }
        return R;
    }

    private char[][] generateKeyInTheBegin (String Key) {
       String binaryKeyString = stringToBinary(Key);
       char[] binaryKeyCharArray = binaryKeyString.toCharArray();
       char[][] transformedKey = transformKeyMatrixG(binaryKeyCharArray);
       char[][] C = getC(transformedKey);
       char[][] D = getD(transformedKey);
       // merge two arrays C and D
       char[][] key = new char[4][14];
       System.arraycopy(C, 0, key, 0, C.length);
       System.arraycopy(D, 0, key, C.length, D.length);
       return key;
    }

    private char[][] transformKeyMatrixG (char[] Key) {
        int[][] G = {
                {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18},
                {10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44,36},
                {63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22},
                {14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4}
        };
        char[][] changedKey = new char[4][14];
        for(int i = 0; i < G.length; i++){
            for(int j = 0; j <  G[0].length; j++){
                changedKey[i][j] = Key[G[i][j]];
            }
        }
        return changedKey;
    }

    private char[][] getC (char[][] Key) {
        char[][] C = new char[2][14];
        for(int i = 0; i < Key.length/2; i++) {
            for (int j = 0; j < Key[0].length; j++) {
                C[i][j] = Key[i][j];
            }
        }
        return C;
    }

    private char[][] getD (char[][] Key) {
        char[][] D = new char[2][14];
        for(int i = 2; i < Key.length; i++) {
            for (int j = 0; j < Key[0].length; j++) {
                D[i][j] = Key[i][j];
            }
        }
        return D;
    }

    private char[][] shiftKey(char[][] C, char[][] D, int iteration) {
        int[] shift = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

        char firstSymbFirstStringC = C[0][0];
        char firstSymbLastStringC = C[1][0];
        char firstSymbFirstStringD = D[0][0];
        char firstSymbLastStringD = D[1][0];
        // in case shift = 2
        char secondSymbFirstStringC = C[0][1];
        char secondSymbLastStringC = C[1][1];
        char secondSymbFirstStringD = D[0][1];
        char secondSymbLastStringD = D[1][1];

        for (int i = 0; i < C[0].length - shift[iteration-1]; i++)
        {
            C [0][i] = C[0][i+shift[iteration-1]];
            D [0][i] = D[0][i+shift[iteration-1]];
            C [1][i] = C[1][i+shift[iteration-1]];
            D [1][i] = D[1][i+shift[iteration-1]];
        }

        if (shift[iteration-1] > 1){
            C[0][C[0].length - 2] = firstSymbLastStringC;
            C[1][C[0].length - 2] = firstSymbFirstStringC;
            D[0][D[0].length - 2] = firstSymbLastStringD;
            D[1][D[0].length - 2] = firstSymbFirstStringD;

            C[0][C[0].length - 1] = secondSymbLastStringC;
            C[1][C[0].length - 1] = secondSymbFirstStringC;
            D[0][D[0].length - 1] = secondSymbLastStringD;
            D[1][D[0].length - 1] = secondSymbFirstStringD;
        }
        else {
            C[0][C[0].length - 1] = firstSymbLastStringC;
            C[1][C[0].length - 1] = firstSymbFirstStringC;
            D[0][D[0].length - 1] = firstSymbLastStringD;
            D[1][D[0].length - 1] = firstSymbFirstStringD;
        }

        // merge two arrays C and D
        char[][] key = new char[4][14];
        System.arraycopy(C, 0, key, 0, C.length);
        System.arraycopy(D, 0, key, C.length, D.length);

        return key;
    }

    private char[][] transformKeyMatrixH (char[] Key) {
        char[][] k = new char[3][16];
        char[][] H = {
                {14, 17, 11, 24, 1,	5,	3, 28, 15, 6, 21, 10, 23, 19, 12, 4},
                {26, 8,	16,	7,	27,	20,	13,	2, 41, 52, 31, 37, 47, 55, 30, 40},
                {51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32},
        };
        char[][] changedKey = new char[4][14];
        for(int i = 0; i < H.length; i++){
            for(int j = 0; j <  H[0].length; j++){
                k[i][j] = Key[H[i][j]];
            }
        }
        return k;
    }
    /*
    private char[][] cycle(char[][] L, char[][] R, int iteration, char[][] key) {

        F = function(R, key);
        R = L ^ F;
    }

    private char[][] function(char[][] R, char[][] key) {

    }
    */

    private char[][] expansionR (char[][] R) {
        char[][] E = {
                {32, 1, 2, 3, 4, 5},
                {4, 5, 6, 7, 8, 9},
                {8, 9, 10, 11, 12, 13},
                {12, 13, 14, 15, 16, 17},
                {16, 17, 18, 19, 20, 21},
                {20, 21, 22, 23, 24, 25},
                {24, 25, 26, 27, 28, 29},
                {28, 29, 30, 31, 32, 1}
        };
        char[][] expandedR = new char[8][6];

        // One string
        char[] inOneStringR = new char[32];
        for (int i=0; i < R.length; i++){
            for (int j = 0; j < R[0].length; j++) {
                inOneStringR[i*R[0].length+j]=R[i][j];
            }
        }

        for(int i = 0; i < E.length; i++){
            for(int j = 0; j <  E[0].length; j++){
                expandedR[i][j] = inOneStringR[E[i][j]];
            }
        }
        return expandedR;
    }

    private char[][] sumKeyAndR(char[][] valueKey, char[][] R) {
        char[][] result = new char[R.length][R[0].length];
        for (int i = 0; i < R.length; i++) {
            for (int j = 0; j < R[0].length; j++) {
                result[i][j] = (char) (R[i][j] ^ valueKey[i][j]);
            }
        }
        return result;
    }



}
