import java.util.ArrayList;
import java.util.Comparator;

/**
 * 
 */

/**
 * @author adrian
 * 
 */
public class Solver {
  ArrayList<Board> solution;
  private int moves;
  
  private class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private int moves;
    public SearchNode(Board board, int moves){
      this.board = board;
      this.moves = moves;
    }
    public int priority(){
      return board.manhattan()+moves;
    }
    public Board board(){
      return this.board;
    }
    @Override
    public int compareTo(SearchNode sn) {
      if(this.priority() > sn.priority())
        return 1;
      else if (this.priority() < sn.priority())
        return -1;
      else
        return 0;
    }
    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append("Move: "+ this.moves);
      sb.append("\n\tpriority = "+ this.priority());
      sb.append("\n\tmanhattan = "+ this.board.manhattan());
      sb.append("\n\thamming = "+ this.board.hamming());
      sb.append("\n\t"+ this.board.toString());
      return sb.toString();
    }
  }

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    solution = new ArrayList<Board>();
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    moves = 0;
    SearchNode sn;
    
    sn = new SearchNode(initial, moves++);
    pq.insert(sn);
//    System.out.println(sn);

    while (true) {
      Board board = pq.delMin().board;
      solution.add(board);
//      System.out.println("--vvv--");
//      System.out.println(board);
//      System.out.println("--^^^--");
      
      if(board.hamming() == 0)
        break;
      for (Board b : board.neighbors()) {
        sn = new SearchNode(b, moves);
        pq.insert(sn);
//        System.out.println(sn);     
      }
      moves++;
    }

  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return false;
  }

  // min number of moves to solve initial board; -1 if no solution
  public int moves() {
    return moves;
  }

  // sequence of boards in a shortest solution; null if no solution
  public Iterable<Board> solution() {
    return solution;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}

