import java.util.*;
import java.io.*;

public class Maze {
  private char[][] maze;
  private boolean animate;
  private int moves;
  private int[][] move;

  public Maze(String filename) throws FileNotFoundException{
    int[][] solver  = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    move = solver;
    moves = 0;
    animate = false;
    int row = 0;
    int col = 0;

    File f = new File(filename);
    Scanner s = new Scanner(f);
    while (s.hasNextLine()){
      row++;
      String result = s.nextLine();
      col = result.length();
    }
    maze = new char[row][col];
    s = new Scanner(f);
    int scount = 0;
    int ecount = 0;
    for (int i = 0; i < row; i++){
      String result = s.nextLine();
      for (int x = 0; x < result.length(); x++){
        maze[i][x] = result.charAt(x);
        if (maze[i][x] == 'S'){
          scount++;
        }
        if (maze[i][x] == 'E') {
          ecount++;
        }
      }
    }
        if (ecount + scount != 2){
          throw new IllegalStateException();
        }
  }

  public void setAnimate(boolean b){
    animate = b;
  }

    public void clearTerminal(){
      //erase terminal, go to top left of screen.
      System.out.println("\033[2J\033[1;1H");
    }

//change the textfile that constructor received and change the characters.
public String toString(){
  String result = "";
  for (int i = 0; i < maze.length; i++){
    result += "\n";
    for (int x = 0; x < maze[i].length; x++){
      result += maze[i][x];
    }
  }
  return result;
}

    public int solve(){
      int r = 0;
      int c = 0;
      for (int i = 0; i < maze.length; i++){
        for (int x = 0; x < maze[i].length; x++){
          if (maze[i][x] == 'S'){
            r = i;
            c = x;
            maze[i][x] = ' ';
            break;
          }
        }
      }
          return solve(r, c);
        }


    /*
      Recursive Solve function:

      A solved maze has a path marked with '@' from S to E.

      Returns the number of @ symbols from S to E when the maze is solved,
      Returns -1 when the maze has no solution.

      Postcondition:
        The S is replaced with '@' but the 'E' is not.
        All visited spots that were not part of the solution are changed to '.'
        All visited spots that are part of the solution are changed to '@'
    */
    //thinking of a way for a way to count the moves so when you trace back it helps you.
    /*
    up = maze[i - 1][x]
    down = maze[i + 1][x]
    left = maze [i][x - 1]
    right = maze[i][x + 1]
    how can you use the helper method and also the third parameter to help solve the maze?
    */
    private int solve(int row, int col){
        if(animate){
            clearTerminal();
            System.out.println(this);
            wait(20);
        }
        if (maze[row][col] == 'E') {
          return 1;
        }
        if (maze[row][col] == ' '){
          maze[row][col] = '@';
          moves++;
          for (int i = 0; i < 4; i++){
            if (solve(row + move[i][0], col + move[i][1]) != -1){
              return moves;
            }
          }
          maze[row][col] = '.';
          moves--;
        }
        return -1;
    }

    private void wait(int millis){
      try {
        Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
      }

      public static void main(String args[]) {
   try {
     Maze test = new Maze("Maze1.txt");
     System.out.print(test);
     Maze test1 = new Maze("data1.dat");
      System.out.print(test1);
     Maze test2 = new Maze("data2.dat");
      System.out.print(test2);
     Maze test3 = new Maze("data3.dat");
 //    test2.setAnimate(true);
     System.out.println(test.solve());
     System.out.println(test1.solve());
     System.out.println(test2.solve());
     System.out.println(test3.solve());
     System.out.println(test);
     System.out.println(test1);
     System.out.println(test2);
     System.out.println(test3);
   }
   catch (FileNotFoundException e) {
     System.out.println("File not found");
   }
 }
}
