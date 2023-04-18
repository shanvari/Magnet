package com.company;
import java.util.Stack;
public class Game{
    //each cell is a variable & 0,-1,1 is domain
    //0 = empty, default . 1 = + . -1 = - . 2 = 0|+ . -2 = 0|- . 3 = just can be empty
    //constraints is number of + & - in each col & row
    public final int  m, n;
    private int sCount = 1;
    private static int[][]playground;
    private static int [][] fGround;
    Stack<State> states;
    Game(int a, int b,int[][] g){
        n = a; //rows
        m = b; //cols
        states = new Stack<State>();
        playground = g;
        //first forward_checking = playGround
        int i, j;
        fGround = new int[n+2][m+2];
        for (i = 0; i < n; i++)
            for (j = 0; j < m; j++)
                fGround[i][j] = 0;
        for (i = 0; i < m; i++){
            fGround[n][i] = playground[1][i+2];
            fGround[n+1][i] = playground[0][i+2];
        }
        for (i = 0; i < n; i++){
            fGround[i][m] = playground[i+2][1];
            fGround[i][m+1] = playground[i+2][0];
        }
        System.out.println("Start");
        for(i = 0 ; i < n+2 ; i ++) {
            for (j = 0; j < m+2; j++)
                System.out.format("%2d ",fGround[i][j]);
            System.out.println();
        }
    }
    private class Move{
        int x,y,v,p;
        Move(int a,int b,int c,int d){
            x = a;
            y = b;
            v = c;
            p = d;
        }
    }
    private class State{
        int [][] ground;
        int num;
        float move = 0;
        State(int nu){
            System.out.println("state " + nu);
            ground = new int[n+2][m+2];
            num = nu;
            int i,j;
            for(i = 0 ; i < n ; i ++)
                for (j = 0; j < m; j++)
                    ground[i][j] = fGround[i][j];

        }
        boolean setGround(int [][] g){
            ground = g;
            return true;
        }
        boolean add(Move move){
            System.out.println("add");
            if(move.v == 1){
                //update number of + & - in cols
                //ground[n][move.y+1]++;
                //ground[n+1][move.y]++;
                if(move.p == 1){
                    //+ 0
                    //- 0
                    //put magnet in playground
                    ground[move.x][move.y] = 1;
                    ground[move.x+1][move.y] = -1;
                    //update number of + & - in rows
                    //ground[move.x+1][m]++;
                    //ground[move.x][m+1]++;
                }
                else{
                    //- 0
                    //+ 0
                    ground[move.x][move.y] = -1;
                    ground[move.x+1][move.y] = 1;
                    //ground[move.x][m]++;
                    //ground[move.x+1][m+1]++;
                }
            }else {
                //ground[move.x][m+1]++;
                //ground[move.x][m]++;
                if(move.p == 1){
                    //+ -
                    //0 0
                    ground[move.x][move.y] = 1;
                    ground[move.x][move.y+1] = -1;
                    //ground[n][move.y+1]++;
                    //ground[n+1][move.y]++;
                }
                else{
                    //- +
                    //0 0
                    ground[move.x][move.y] = -1;
                    ground[move.x][move.y+1] = 1;
                    //ground[n][move.y]++;
                   // ground[n+1][move.y+1]++;
                }
            }
            forward_checking(move,1);
            return true;
        }
        boolean remove(Move move){
            System.out.println("remove");
            //exactly like add
            if(move.v == 1){
                ground[move.x][move.y] = 0;
                ground[move.x+1][move.y] = 0;
                //ground[n][y]--;
                //ground[n+1][y]--;
                if(move.p == 1){
                    //+ 0
                    //- 0
                    //update number of + & - in row & col
                   // ground[x+1][m]--;
                  //  ground[x][m+1]--;
                }
                else{
                    //- 0
                    //+ 0
                  //  ground[x][m]--;
                  //  ground[x+1][m+1]--;
                }
            }
            else {
                ground[move.x][move.y] = 0;
                ground[move.x][move.y+1] = 0;
              //  ground[x][y+1]--;
              //  ground[x][y]--;
                if(move.p == 1){
                    //+ -
                    //0 0
                  //  ground[n][m+1]--;
                 //   ground[n+1][m]--;
                }
                else{
                    //- +
                    //0 0
                 //   ground[n+1][m+1]--;
                 //   ground[n][m]--;
                }
            }
            forward_checking(move,-1);
            return true;
        }
}

    private boolean goalTest(State s){
        System.out.println("goalTest");
        int i,j;
        for(j = 0 ; j < m ; j ++) {
            if(fGround[n][j] != 0 || fGround[n+1][j] != 0 )
                return false;
        }
        for(i = 0 ; i < n ; i ++) {
            if(fGround[i][m] != 0 || fGround[i][m+1] != 0)
                return false;
        }
        return true;
    }
    public void print(int [][] g){
        int i,j;
        System.out.println("End State");
        for(i = 0 ; i < n ; i ++) {
            for (j = 0; j < m; j++)
                System.out.format("%2d ",g[i][j]);
            System.out.println();
        }
    }
    public int play(){
        State root = new State(0);
        states.push(root);
        State s = back(0);
        //no answer
        if(s == null){
            System.out.println("No Answer!!");
            return -1;
        }
        print(s.ground);
        return 0;
    }
    public Move chooseMove(int ch){
        System.out.println("chooseMove");
        int choose = 0;
        int i , j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if(fGround[i][j] == 2){
                    if(fGround[i+1][j] == 0|| fGround[i+1][j] == -2){
                        if(i <n-1 && fGround[i+1][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0){
                            if(choose == ch)     return new Move(i,j,1,1);
                            choose++;
                        }
                    }
                    else if(fGround[i][j+1] == 0 ||fGround[i][j+1] == -2){
                        if(j <m-1 &&fGround[i][m] > 0 && fGround[n][j+1] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0){
                            if(choose == ch)    return new Move(i,j,0,1);
                            choose++;
                        }
                    }
                }
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if(fGround[i][j] == -2){
                    if(fGround[i+1][j] == 0|| fGround[i+1][j] == 2){
                        if(i <n-1 && fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i+1][m+1] > 0 && fGround[n+1][j] > 0){
                            if(choose == ch)    return new Move(i,j,1,0);
                            choose++;
                        }
                    }
                    else if(fGround[i][j+1] == 0 ||fGround[i][j+1] == 2){
                        if(j <m-1 && fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j+1] > 0){
                            if(choose == ch)    return new Move(i,j,0,0);
                            choose++;
                        }
                    }
                }
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if(fGround[i][j] == 0 ){
                    if((fGround[i+1][j] == 0|| fGround[i+1][j] == -2)){
                        if(i <n-1 && fGround[i+1][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0) {
                            if(choose == ch)    return new Move(i, j, 1, 1);
                            choose++;
                        }
                    }
                    if(i <n-1 && fGround[i+1][j] == 2 && fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i+1][m+1] > 0 && fGround[n+1][j] > 0){
                        if(choose == ch)    return new Move(i,j,1,0);
                        choose++;
                    }
                    else if(fGround[i][j+1] == 0 ||fGround[i][j+1] == -2){
                        if(j <m-1 &&fGround[i][m] > 0 && fGround[n][j+1] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0){
                            if(choose == ch)    return new Move(i,j,0,1);
                            choose++;
                        }
                    }
                    if(j <m-1 &&fGround[i][j+1] == 2&&fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j+1] > 0){
                        if(choose == ch)    return new Move(i,j,0,0);
                        choose++;
                    }
                }
            }
        }
        return null;
    }
    public State back(int l){
        Move m = chooseMove(0);
        //Move m = LCV(0);
        //Move m = MRV(0);
        int chCount = 0;
        while(m != null) {
            State s = new State(l);
            s.setGround(states.peek().ground);
            m = chooseMove(chCount);
            //m = LCV(chCount);
            //Move m = MRV(chCount);
            if(m == null)
                return null;
            s.add(m);
            System.out.println("add move : " + m.x + "," + m.y + "  " + m.v + ","+m.p+"ch = " + chCount);
            states.push(s);
            if (goalTest(s))
                return s;
            State b = back(l + 1);
            if (b != null) {
                return b;
            }
            s.remove(m);
            System.out.println("reomve move : " + m.x + "," + m.y + "  " + m.v + ","+m.p+"ch = " + chCount);
            chCount ++;
            System.out.println("backtracking!  " + l +" ");
        }
        return null;
    }
    public Move LCV(int ch){
        System.out.println("LCV");
        int choose = 0;
        float rmn, max = 0;
        Move ans,mov;
        int i , j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if(fGround[i][j] == 2){
                    if(fGround[i+1][j] == 0|| fGround[i+1][j] == -2){
                        if(i <n-1 && fGround[i+1][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0){
                            mov = new Move(i,j,1,1);
                            rmn =0; //remain(mov);
                           // if(choose == ch)     return
                            if(max < rmn){
                                max = rmn;
                                ans = mov;
                            }
                            choose++;
                        }
                    }
                    else if(fGround[i][j+1] == 0 ||fGround[i][j+1] == -2){
                        if(j <m-1 &&fGround[i][m] > 0 && fGround[n][j+1] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0){
                            mov = new Move(i,j,0,1);
                            rmn = 0;//remain(mov);
                            // if(choose == ch)     return
                            if(max < rmn){
                                max = rmn;
                                ans = mov;
                            }
                            choose++;
                        }
                    }
                }
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if(fGround[i][j] == -2){
                    if(fGround[i+1][j] == 0|| fGround[i+1][j] == 2){
                        if(i <n-1 && fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i+1][m+1] > 0 && fGround[n+1][j] > 0){
                            mov = new Move(i,j,1,0);
                            rmn = 0;//remain(mov);
                            // if(choose == ch)     return
                            if(max < rmn){
                                max = rmn;
                                ans = mov;
                            }
                            choose++;
                        }
                    }
                    else if(fGround[i][j+1] == 0 ||fGround[i][j+1] == 2){
                        if(j <m-1 && fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j+1] > 0){
                            mov = new Move(i,j,0,0);
                            rmn = 0;//remain(mov);
                            // if(choose == ch)     return
                            if(max < rmn){
                                max = rmn;
                                ans = mov;
                            }
                            choose++;
                        }
                    }
                }
            }
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if(fGround[i][j] == 0 ){
                    if((fGround[i+1][j] == 0|| fGround[i+1][j] == -2)){
                        if(i <n-1 && fGround[i+1][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0) {
                            if(choose == ch)    return new Move(i, j, 1, 1);
                            choose++;
                            mov = new Move(i,j,0,0);
                            rmn =0;// remain(mov);
                            // if(choose == ch)     return
                            if(max < rmn){
                                max = rmn;
                                ans = mov;
                            }
                            choose++;
                        }
                    }
                    if(i <n-1 && fGround[i+1][j] == 2 && fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i+1][m+1] > 0 && fGround[n+1][j] > 0){ ;
                        mov = new Move(i,j,1,0);
                        rmn = 0;//remain(mov);
                        // if(choose == ch)     return
                        if(max < rmn){
                            max = rmn;
                            ans = mov;
                        }
                        choose++;
                    }
                    else if(fGround[i][j+1] == 0 ||fGround[i][j+1] == -2){
                        if(j <m-1 &&fGround[i][m] > 0 && fGround[n][j+1] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j] > 0){
                            mov = new Move(i,j,0,1);
                            rmn = 0;//remain(mov);
                            // if(choose == ch)     return
                            if(max < rmn){
                                max = rmn;
                                ans = mov;
                            }
                            choose++;
                        }
                    }
                    if(j <m-1 &&fGround[i][j+1] == 2&&fGround[i][m] > 0 && fGround[n][j] > 0 && fGround[i][m+1] > 0 && fGround[n+1][j+1] > 0){
                        mov =  new Move(i,j,0,0);
                        rmn =0;// remain(mov);
                        // if(choose == ch)     return
                        if(max < rmn){
                            max = rmn;
                            ans = mov;
                        }
                        choose++;
                    }
                }
            }
        }

        return null;
    }
    public Move MRV(int ch){
        return null;
    }
    private int forward_checking(Move move,int ar){
        int i , j;
        if(ar == 1) {
            if (move.v == 1) {
                if (move.y + 1 < m + 1) fGround[n][move.y]--;
                fGround[n + 1][move.y]--;
                if (move.p == 1) {
                    //
                    if (move.x + 1 < n + 1) fGround[move.x + 1][m]--;
                    fGround[move.x][m + 1]--;
                    //checking + - pole inside
                    fGround[move.x][move.y] = 1;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 0) fGround[move.x - 1][move.y] = -2;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 2) fGround[move.x - 1][move.y] = 3;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == 0) fGround[move.x][move.y - 1] = -2;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == 2) fGround[move.x][move.y - 1] = 3;
                    if (move.y + 1 < m && fGround[move.x][move.y + 1] == 0) fGround[move.x][move.y + 1] = -2;
                    if (move.y + 1 < m && fGround[move.x][move.y + 1] == 2) fGround[move.x][move.y + 1] = 3;

                    if (move.x + 1 < n) fGround[move.x + 1][move.y] = -1;

                    if (move.x + 2 < n && fGround[move.x + 2][move.y] == 0) fGround[move.x + 2][move.y] = 2;
                    if (move.x + 2 < n && fGround[move.x + 2][move.y] == -2) fGround[move.x + 2][move.y] = 3;
                    if (move.x + 1 < n && move.y > 1 && fGround[move.x + 1][move.y - 1] == 0) fGround[move.x + 1][move.y - 1] = 2;
                    if (move.x + 1 < n && move.y > 1 && fGround[move.x + 1][move.y - 1] == -2) fGround[move.x + 1][move.y - 1] = 3;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 0) fGround[move.x + 1][move.y + 1] = 2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == -2) fGround[move.x + 1][move.y + 1] = 3;

                }
                else {
                    //
                    fGround[move.x][m]--;
                    if (move.x + 1 < n + 1) fGround[move.x + 1][m + 1]--;

                    fGround[move.x][move.y] = -1;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 0) fGround[move.x - 1][move.y] = 2;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == -2) fGround[move.x - 1][move.y] = 3;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == 0) fGround[move.x][move.y - 1] = 2;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == -2) fGround[move.x][move.y - 1] = 3;
                    if (move.y + 1 < m && fGround[move.x][move.y + 1] == 0) fGround[move.x][move.y + 1] = 2;
                    if (move.y + 1 < m && fGround[move.x][move.y + 1] == -2) fGround[move.x][move.y + 1] = 3;

                    if (move.x + 1 < n) fGround[move.x + 1][move.y] = 1;
                    if (move.x + 2 < n && fGround[move.x + 2][move.y] == 0) fGround[move.x + 2][move.y] = -2;
                    if (move.x + 2 < n && fGround[move.x + 2][move.y] == 2) fGround[move.x + 2][move.y] = 3;
                    if (move.x + 1 < n && move.y > 1 && fGround[move.x + 1][move.y - 1] == 0)fGround[move.x + 1][move.y - 1] = -2;
                    if (move.x + 1 < n && move.y > 1 && fGround[move.x + 1][move.y - 1] == 2) fGround[move.x + 1][move.y - 1] = 3;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 0) fGround[move.x + 1][move.y + 1] = -2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 2) fGround[move.x + 1][move.y + 1] = 3;
                }
            }
            else {
                fGround[move.x][m + 1]--;
                fGround[move.x][m]--;
                if (move.p == 1) {
                    if (move.y + 1 < m + 1) fGround[n][move.y + 1]--;
                    fGround[n + 1][move.y]--;
                    //checking + - pole inside
                    fGround[move.x][move.y] = 1;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 0) fGround[move.x - 1][move.y] = -2;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 2) fGround[move.x - 1][move.y] = 3;
                    if (move.x + 1 < n && fGround[move.x + 1][move.y] == 0) fGround[move.x + 1][move.y] = -2;
                    if (move.x + 1 < n && fGround[move.x + 1][move.y] == 2) fGround[move.x + 1][move.y] = 3;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y] == 0) fGround[move.x][move.y - 1] = -2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y] == 2) fGround[move.x][move.y - 1] = 3;

                    if (move.y + 1 < m) fGround[move.x][move.y + 1] = -1;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == 0) fGround[move.x][move.y + 2] = 2;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == -2) fGround[move.x][move.y + 2] = 3;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == 0) fGround[move.x - 1][move.y + 1] = 2;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == -2) fGround[move.x - 1][move.y + 1] = 3;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 0) fGround[move.x + 1][move.y + 1] = 2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == -2) fGround[move.x + 1][move.y + 1] = 3;
                } else {
                    fGround[n][move.y]--;
                    if (move.y + 1 < m + 1) fGround[n + 1][move.y + 1]--;

                    fGround[move.x][move.y] = -1;
                    if (move.x > 1 && fGround[move.x + 2][move.y] == 0) fGround[move.x - 1][move.y] = 2;
                    if (move.x > 1 && fGround[move.x + 2][move.y] == -2) fGround[move.x - 1][move.y] = 3;
                    if (move.x + 1 < n + 1 && fGround[move.x + 1][move.y] == 0) fGround[move.x + 1][move.y] = 2;
                    if (move.x + 1 < n + 1 && fGround[move.x + 1][move.y] == -2) fGround[move.x + 1][move.y] = 3;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == 0) fGround[move.x][move.y - 1] = 2;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == -2) fGround[move.x][move.y - 1] = 3;

                    if (move.y + 1 < m) fGround[move.x][move.y + 1] = 1;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == 0) fGround[move.x][move.y + 2] = -2;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == 2) fGround[move.x][move.y + 2] = 3;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == 0)
                        fGround[move.x - 1][move.y + 1] = -2;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == 2)
                        fGround[move.x - 1][move.y + 1] = 3;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 0)
                        fGround[move.x + 1][move.y + 1] = -2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 2)
                        fGround[move.x + 1][move.y + 1] = 3;
                }
                for (i = 0; i < n; i++) {
                    if (fGround[i][m] == 0) {
                        if (fGround[i][m + 1] == 0)
                            for (j = 0; j < m; j++)
                                if (fGround[i][j] == 0) fGround[i][j] = 3;
                                else
                                    for (j = 0; j < m; j++)
                                        if (fGround[i][j] == 0) fGround[i][j] = 2;
                    } else if (fGround[i][m + 1] == 0)
                        for (j = 0; j < m; j++)
                            if (fGround[i][j] == 0) fGround[i][j] = -2;

                }
                for (i = 0; i < m; i++) {
                    if (fGround[n][i] == 0) {
                        if (fGround[n + 1][i] == 0) {
                            for (j = 0; j < n; j++)
                                if (fGround[j][i] == 0) fGround[j][i] = 3;
                        } else
                            for (j = 0; j < n; j++)
                                if (fGround[j][i] == 0) fGround[j][i] = 2;
                    } else if (fGround[n + 1][i] == 0)
                        for (j = 0; j < n; j++)
                            if (fGround[j][i] == 0) fGround[j][i] = -2;

                }
            }
        }
        else {
            if (move.v == 1) {
                if (move.y + 1 < m + 1)fGround[n][move.y]++;
                fGround[n + 1][move.y]++;
                if (move.p == 1) {
                    //
                    if (move.x + 1 < n + 1)fGround[move.x + 1][m]++;
                    fGround[move.x][m + 1]++;
                    //checking + - pole inside
                    fGround[move.x][move.y] = 0;
                    if (move.x > 1 &&fGround[move.x - 1][move.y] == 3) fGround[move.x - 1][move.y] = -2;
                    if (move.x > 1 &&fGround[move.x - 1][move.y] == -2) fGround[move.x - 1][move.y] = 0;
                    if (move.y > 1 &&fGround[move.x][move.y - 1] == 3) fGround[move.x][move.y - 1] = -2;
                    if (move.y > 1 &&fGround[move.x][move.y - 1] == -2) fGround[move.x][move.y - 1] = 0;
                    if (move.y + 1 < m &&  fGround[move.x][move.y + 1] == 3) fGround[move.x][move.y + 1] = -2;
                    if (move.y + 1 < m &&  fGround[move.x][move.y + 1] == -2) fGround[move.x][move.y + 1] = 0;

                    if (move.x + 1 < n)fGround[move.x + 1][move.y] = 0;
                    if (move.x + 2 < n &&fGround[move.x + 2][move.y] == 3) fGround[move.x + 2][move.y] = 2;
                    if (move.x + 2 < n &&fGround[move.x + 2][move.y] == -2) fGround[move.x + 2][move.y] = 0;
                    if (move.x + 1 < n && move.y > 1 &&fGround[move.x + 1][move.y - 1] == 3)     fGround[move.x + 1][move.y - 1] = 2;
                    if (move.x + 1 < n && move.y > 1 &&fGround[move.x + 1][move.y - 1] == -2)     fGround[move.x + 1][move.y - 1] = 0;
                    if (move.x + 1 < n && move.y + 1 < m &&fGround[move.x + 1][move.y + 1] == 3) fGround[move.x + 1][move.y + 1] = 2;
                    if (move.x + 1 < n && move.y + 1 < m &&fGround[move.x + 1][move.y + 1] == -2) fGround[move.x + 1][move.y + 1] = 0;
                }
                else {
                    //
                    fGround[move.x][m]++;
                    if (move.x + 1 < n + 1)fGround[move.x + 1][m + 1]++;

                    fGround[move.x][move.y] = 0;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 3) fGround[move.x - 1][move.y] = -2;
                    if (move.x > 1 &&fGround[move.x - 1][move.y] == -2) fGround[move.x - 1][move.y] = 0;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == 3) fGround[move.x][move.y - 1] = -2;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == -2) fGround[move.x][move.y - 1] = 0;
                    if (move.y + 1 < m &&fGround[move.x][move.y + 1] == 3) fGround[move.x][move.y + 1] = -2;
                    if (move.y + 1 < m &&fGround[move.x][move.y + 1] == -2) fGround[move.x][move.y + 1] = 0;

                    if (move.x + 1 < n)fGround[move.x + 1][move.y] = 0;
                    if (move.x + 2 < n &&fGround[move.x + 2][move.y] == 3) fGround[move.x + 2][move.y] = -2;
                    if (move.x + 2 < n &&fGround[move.x + 2][move.y] == -2) fGround[move.x + 2][move.y] = 0;
                    if (move.x + 1 < n && move.y > 1 && fGround[move.x + 1][move.y - 1] == 3) fGround[move.x + 1][move.y - 1] = -2;
                    if (move.x + 1 < n && move.y > 1 && fGround[move.x + 1][move.y - 1] == -2) fGround[move.x + 1][move.y - 1] = 0;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 3) fGround[move.x + 1][move.y + 1] = -2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == -2) fGround[move.x + 1][move.y + 1] = 0;
                }
            }
            else {
                fGround[move.x][m + 1]++;
                fGround[move.x][m]++;
                if (move.p == 1) {
                    if (move.y + 1 < m + 1)fGround[n][move.y + 1]++;
                    fGround[n + 1][move.y]++;
                    //checking + - pole inside
                    fGround[move.x][move.y] = 0;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 3) fGround[move.x - 1][move.y] = -2;
                    if (move.x > 1 && fGround[move.x - 1][move.y] == 2) fGround[move.x - 1][move.y] = 0;
                    if (move.x + 1 < n && fGround[move.x + 1][move.y] == 3) fGround[move.x + 1][move.y] = -2;
                    if (move.x + 1 < n && fGround[move.x + 1][move.y] == 2) fGround[move.x + 1][move.y] = 0;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y] == 3) fGround[move.x][move.y - 1] = -2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y] == 2) fGround[move.x][move.y - 1] = 0;

                    if (move.y + 1 < m) fGround[move.x][move.y + 1] = 0;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == 3) fGround[move.x][move.y + 2] = -2;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == -2) fGround[move.x][move.y + 2] = 0;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == 3) fGround[move.x - 1][move.y + 1] = -2;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == -2) fGround[move.x - 1][move.y + 1] = 0;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 3) fGround[move.x + 1][move.y + 1] = -2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == -2) fGround[move.x + 1][move.y + 1] = 0;
                } else {
                    fGround[n][move.y]++;
                    fGround[n + 1][move.y + 1]++;

                    fGround[move.x][move.y] = 0;
                    if (move.x > 1 && fGround[move.x + 2][move.y] == 3) fGround[move.x - 1][move.y] = -2;
                    if (move.x > 1 && fGround[move.x + 2][move.y] == -2) fGround[move.x - 1][move.y] =0;
                    if (move.x + 1 < n + 1 && fGround[move.x + 1][move.y] == 3) fGround[move.x + 1][move.y] = -2;
                    if (move.x + 1 < n + 1 && fGround[move.x + 1][move.y] == -2) fGround[move.x + 1][move.y] = 0;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == 3) fGround[move.x][move.y - 1] = -2;
                    if (move.y > 1 && fGround[move.x][move.y - 1] == -2) fGround[move.x][move.y - 1] =0;

                    if (move.y + 1 < m) fGround[move.x][move.y + 1] = 0;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == 3) fGround[move.x][move.y + 2] = 2;
                    if (move.y + 2 < m && fGround[move.x][move.y + 2] == 2) fGround[move.x][move.y + 2] = 0;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == 3)
                        fGround[move.x - 1][move.y + 1] = 2;
                    if (move.x > 1 && move.y + 1 < m && fGround[move.x - 1][move.y + 1] == 2)
                        fGround[move.x - 1][move.y + 1] = 0;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 3)
                        fGround[move.x + 1][move.y + 1] = 2;
                    if (move.x + 1 < n && move.y + 1 < m && fGround[move.x + 1][move.y + 1] == 2)
                        fGround[move.x + 1][move.y + 1] = 0;
                }
            }
        }
        System.out.println("forwardCheck:");
        for(i = 0 ; i < n+2 ; i ++) {
            for (j = 0; j < m+2; j++)
                System.out.format("%2d ",fGround[i][j]);
            System.out.println();
        }
            return 0;
    }

}