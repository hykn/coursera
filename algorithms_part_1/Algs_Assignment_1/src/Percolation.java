


public class Percolation {
    
    private int[] grid;
    private int size;
    private int volume;

    private int[] treeSize;
    private boolean[] gridOpen;

    private int virtualTopValue;
    private int virtualBottomValue;

    // create N-by-N grid, with all sits blocked
    public Percolation(int n) {
        
        // catch exception if N < 0
        if (n < 1) throw new java.lang.IllegalArgumentException();
        
        size = n;
        volume = size  * size;
        grid = new int[volume + 2];
        gridOpen = new boolean[volume + 2];
        treeSize = new int[volume + 2];

        for (int i = 0; i < volume + 2; i++) {
            grid[i] = i;
            gridOpen[i] = false;
            treeSize[i] = 1;
        }

        virtualTopValue = volume;
        virtualBottomValue = volume + 1;
    }
    
    // open site(i,j), if it is not open already
    public void open(int i, int j) {

        if (i < 1 || i > size || j < 1 || j > size)
            throw new java.lang.IndexOutOfBoundsException();

        int index = getindex(i, j);
        if (gridOpen[index]) return;

        int upindex = getindex(i - 1, j);
        int downindex = getindex(i + 1, j);
        int leftindex = getindex(i, j - 1);
        int rightindex = getindex(i, j + 1);

        gridOpen[index] = true;

        if (i == 1) {
            connect(index, virtualTopValue);
        }
        else if (isOpen(i - 1, j))
            connect(index, upindex);

        if (i != size && isOpen(i + 1, j))
            connect(index, downindex);

        if (j != 1 && isOpen(i, j - 1))
            connect(index, leftindex);

        if (j != size && isOpen(i, j + 1))
            connect(index, rightindex);
    }
    
    // is open?
    public boolean isOpen(int i, int j) {

        if (i < 1 || i > size || j < 1 || j > size)
            throw new java.lang.IndexOutOfBoundsException();

        int index = 0;

        index = getindex(i, j);

        return gridOpen[index];
    }
    
    // is full?
    public boolean isFull(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size)
            throw new java.lang.IndexOutOfBoundsException();

        int index = getindex(i, j);
        return getroot(virtualTopValue) == getroot(index);
    }
    
    // does the system percolate?
    public boolean percolates() {

        for (int i = volume - size; i < volume; i++)
            if (getroot(grid[i]) == getroot(virtualTopValue))
                return true;
        return false;

    }

    private int getroot(int index) {

        int root = index;
        
        while (grid[root] != root)
            root = grid[root];

        return root;
    }

    private void connect(int index1, int index2) {
        int root1 = getroot(index1);
        int root2 = getroot(index2);
        if (root1 == root2)
            return;
        if (treeSize[root1] < treeSize[root2]) {
            grid[root1] = root2;
            treeSize[root2] += treeSize[root1];
        }
        else {
            grid[root2] = root1;
            treeSize[root1] += treeSize[root2];
        }

    }

    private int getindex(int i, int j) {

        return (i - 1) * size + (j - 1);
    }

    private void print() {

        int index = 0;
        System.out.print("\n****************** root index *********************\n");
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {

                index = getindex(i, j);

                System.out.printf("%d\t", grid[index]);

            }
            System.out.print("\n");
        }

    System.out.printf("\nvirtual top node:\t%d\n", grid[virtualTopValue]);
    System.out.printf("\nvirtual bottom node:\t%d\n", grid[virtualBottomValue]);
    }
    
    public static void main(String[] args) {


        int n = 10;
        Percolation perco = new Percolation(n);

        perco.print();

        perco.open(n, n);
        perco.print();

        perco.open(n - 1, n);
        perco.print();

        perco.open(n - 1,n - 1);
        perco.print();


    }
}
