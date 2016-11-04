import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.ArrayList;

public class PercolationStats {
    private double[] measures;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        Percolation board;
        ArrayList<Integer> closed;
        int rndInt, rndSpot;
        
        measures = new double[T];

        for (int i = 0; i < T; i++) {
            board = new Percolation(N);
            closed = new ArrayList<Integer>(N * N);
            for (int j = 0; j < N * N; j++)
                closed.add(j);

            while (!board.percolates()) {
                rndInt = StdRandom.uniform(closed.size());
                rndSpot = closed.get(rndInt);
                closed.remove(rndInt);
                board.open(1 + rndSpot / N, 1 + rndSpot % N);
            }
            measures[i] = (double) (N * N - closed.size()) / (N * N);
        }
    }

    // // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(measures);
    }

    // // sample standard deviation of percolation thresphold
    public double stddev() {
        return StdStats.stddev(measures);
    }

    // // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.95 * mean();
    }

    // // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 1.05 * mean();
    }

    // // test client (described below)
    public static void main(String[] args) {
        PercolationStats test;
        int N = 200;
        int T = 100;
        
        if (args.length == 2) {
            N = Integer.parseInt(args[0]);
            T = Integer.parseInt(args[1]);
        }
        
        if (N < 1 || T < 1)
            throw new IllegalArgumentException("Both N and T must be greater than 0");  

        test = new PercolationStats(N, T);
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = " + test.confidenceLo() + ", " + test.confidenceHi());
    }
}
