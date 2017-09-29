

import java.util.Random;

public class PercolationStates {

	private double meanVal;
	private double sigmalVal;
	private double tVal;

	private double[] x_val;
	// Perform t independent experiments
	public PercolationStates(int n, int t) {

		tVal = t;
		x_val = new double[t];


		// t independent experiments
		for (int tt = 0; tt < t; tt++) {
			x_val[tt] = PercolationSingle(n);

		}
		// mean calculate
		double sum = 0;

		for (int i = 0; i < t; i++) {
			sum = sum + x_val[i];
		}

		meanVal = sum / tVal;

		// std calculate

		sum = 0;
		for (int i = 0; i < t; i ++) {
			sum = sum + Math.pow((x_val[i] - meanVal),2);
		}

		sigmalVal = Math.sqrt(sum/(tVal - 1));
	}

	public double PercolationSingle(int n) {

		Percolation perco = new Percolation(n);
		Random random = new Random();
		double sum = 0;

		int i = 0;
		int j = 0;
		while (perco.percolates() == false) {

			while (true) {
				i = random.nextInt(n) + 1;
				j = random.nextInt(n) + 1;

				if (perco.isOpen(i,j) == true)
					continue;
				else
					break;
			}

			perco.open(i,j);
			sum = sum + 1;
			// perco.print();
		}

		return sum/(n * n);
	}

	// sample mean of percolation threshold
	public double mean() {

		return meanVal;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {


		return sigmalVal;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {

		return meanVal - 1.96 * sigmalVal / Math.sqrt(tVal);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return meanVal + 1.96 * sigmalVal / Math.sqrt(tVal);
	}

	// test 
	public static void main(String[] argas) {
		int n = 2;
		int t = 10000;

		PercolationStates percostate = new PercolationStates(n,t);

		System.out.printf("mean\t\t\t\t\t= %f\n",percostate.mean());
		System.out.printf("stddev\t\t\t\t\t= %f\n",percostate.stddev());
	}

}