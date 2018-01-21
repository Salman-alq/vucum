/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoo;

/**
 *
 * @author labuser
 */
public class Point {
     int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
    
    
}
