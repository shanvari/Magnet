package com.company;

import java.lang.management.GarbageCollectorMXBean;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	// get inputs
        Scanner scanner = new Scanner(System.in);
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        int [][]g = new int[r+2][c+2];
        int i,j,pCount=0,nCount=0;
        for(i = 2 ; i < c+2 ; i ++) {
            g[0][i] = scanner.nextInt();
            pCount += g[0][i];
        }
        for(i = 2 ; i < c+2 ; i ++) {
            g[1][i] = scanner.nextInt();
            nCount += g[1][i];
        }
        for(i = 2 ; i < r+2 ; i ++) {
            g[i][0] = scanner.nextInt();
            pCount -= g[i][0];
        }
        for(i = 2 ; i < r+2 ; i ++) {
            g[i][1] = scanner.nextInt();
            nCount -= g[i][1];
        }
        g [0][0] = 0;
        g [0][1] = 0;
        g[1][0] = 0;
        g [1][1] = 0;
        for(i = 2 ; i < r+2 ; i ++)
            for(j = 2 ; j < c+2 ; j ++)
               g[i][j] = 0;
    //create objects
        Game game = new Game(r,c,g);
        if (pCount != 0 || nCount != 0)
            System.out.println("No Answer!!");
        game.play();
    }
}
