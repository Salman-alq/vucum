/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoo;

import java.util.*;

public class Board {
  
     List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);
    int[][] board = new int[4][4];

    public Board() {
    }

    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }
    //check if X have won represented by 1
    public boolean hasXWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == board[3][3] && board[0][0] == 1) || 
                (board[0][3] == board[1][2] && board[0][3] == board[2][1] && board[0][3] == board[3][1] && board[0][3] == 1))  {
            //System.out.println("O Diagonal Win");
            return true;
        }
        for (int i = 0; i < 4; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == board[i][3] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == board[3][i] && board[0][i] == 1))) {
                // System.out.println("O Row or Column win");
                return true;
            }
        }
        return false;
    }
    
    //check if O has won represented by 2
    public boolean hasOWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == board[3][3] && board[0][0] == 2) ||
                (board[0][3] == board[1][2] && board[0][3] == board[2][1] && board[0][3] == board[3][0] && board[0][3] == 2)) {
            // System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 4; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] ==board[i][3] && board[i][0] ==2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == board[3][i] && board[0][i] == 2)) {
                //  System.out.println("X Row or Column win");
                return true;
            }
        }

        return false;
    }
    
    //check available states in the board
    public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    //put player move in the board
    public void placeAMove(Point point, int player) {
        board[point.x][point.y] = player;   //player = 1 for O, 2 for X..
    }

    //get best movement according to the board state
    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) { 
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScores.get(best).point;
    }

    //accepts input from user
    void takeHumanInput() {
        System.out.println("Your move: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        Point point = new Point(x, y);
        placeAMove(point, 3); 
    }

    //display current board
    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();

        }
    }

    //get min value
    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    //get max value
    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }
    
    //declares a list for scores
    List<PointsAndScores> rootsChildrenScores;
 
    //excutes minimax algorithm
    public void callMinimax(int depth, int turn){
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn);
    }
    
    //minimax algorithm
    public int minimax(int depth, int turn) {

        if (hasXWon()) return +1;
        if (hasOWon()) return -1;

        //get available states from the board
        List<Point> pointsAvailable = getAvailableStates();
        if (pointsAvailable.isEmpty()) return 0; 
        
        //stores scores
        List<Integer> scores = new ArrayList<>(); 
        
        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);  

            if (turn == 1) { //O's turn select the highest from below minimax() call
                placeAMove(point, 1); 
                int currentScore = minimax(depth + 1, 2);
                scores.add(currentScore);//add scores to the list

                if (depth == 0) 
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
                
            } else if (turn == 2) {//X's turn select the lowest from below minimax() call
                placeAMove(point, 2); 
                scores.add(minimax(depth + 1, 1));
            }
            board[point.x][point.y] = 0; //Reset this point
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }
}
