import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private WeightedQuickUnionUF board;
    private WeightedQuickUnionUF boardFull;
    private boolean[] opened;
    private int upNode;
    private int downNode;

    // return the 1D index from a 2D array
    private int realIndex(int i, int j) {
        return (i - 1) * N + (j - 1);
    }

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        board = new WeightedQuickUnionUF(N * N + 2);
	boardFull = new WeightedQuickUnionUF(N * N + 1);
        opened = new boolean[N * N];
        this.N = N;
        upNode = N * N;
        downNode = upNode + 1;

        if (N <= 0)
            throw new IllegalArgumentException("constructor index is not greater than 0");  
    }
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        int actual = realIndex(i, j);

        if (i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException("indexes are not between 1 and N");

        if (opened[actual])
            return;

        opened[actual] = true;

        if (i == 1) {
            board.union(actual, upNode);
	    boardFull.union(actual, upNode);
	}
        if (i == N)
            board.union(actual, downNode);

        if (i > 1)              // up
            if (isOpen(i - 1, j)) {
                board.union(actual, realIndex(i - 1, j));
		boardFull.union(actual, realIndex(i - 1, j));
	    }
        if (i < N)              // down
            if (isOpen(i + 1, j)) {
                board.union(actual, realIndex(i + 1, j));
		boardFull.union(actual, realIndex(i + 1, j));
	    }
        if (j > 1)              // left
            if (isOpen(i, j - 1)) {
                board.union(actual, realIndex(i, j - 1));
		boardFull.union(actual, realIndex(i, j - 1));
	    }
        if (j < N)              // right
            if (isOpen(i, j + 1)) {
                board.union(actual, realIndex(i, j + 1));
		boardFull.union(actual, realIndex(i, j + 1));
	    }
    }

    private void validate(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException("indexes are not between 0 and N");  
    }
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return opened[realIndex(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validate(i, j);
        return boardFull.connected(upNode, realIndex(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return board.connected(upNode, downNode);
    }

    public static void main(String[] args) { // test client (optional)
        Percolation test = new Percolation(8);

         test.open(1, 1);
         test.open(8, 1);
        // test.open(2, 1);
        System.out.println(test.isOpen(8, 1));
        System.out.println(test.isFull(8, 1));
    }
}
