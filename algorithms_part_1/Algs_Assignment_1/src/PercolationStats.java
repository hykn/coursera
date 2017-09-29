

import java.util.Random;

public class PercolationStats {

	private double meanVal;
	private double sigmalVal;
	private double tVal;

	private double[] xVal;
	// Perform t independent experiments
	public PercolationStats(int n, int t) {

		if (n <= 0 || t <= 0) throw new java.lang.IllegalArgumentException();
		
		tVal = t;
		xVal = new double[t];


		// t independent experiments

		for (int tt = 0; tt < t; tt++) {
			xVal[tt] = percolationSingle(n);

		}
		// mean calculate
		double sum = 0;

		for (int i = 0; i < t; i++) {
			sum = sum + xVal[i];
		}

		meanVal = sum / tVal;

		// std calculate

		sum = 0;
		for (int i = 0; i < t; i++) {
			sum = sum + Math.pow((xVal[i] - meanVal), 2);
		}

		sigmalVal = Math.sqrt(sum / (tVal - 1));
	}

	private double percolationSingle(int n) {

		Percolation perco = new Percolation(n);
		Random random = new Random();
		double sum = 0;

		int i = 0;
		int j = 0;
		while (!perco.percolates()) {

			while (true) {
				i = random.nextInt(n) + 1;
				j = random.nextInt(n) + 1;

				if (perco.isOpen(i, j))
					continue;
				else
					break;
			}

			perco.open(i, j);
			sum = sum + 1;
			// perco.print();
		}

		return sum / (n * n);
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

		PercolationStats percostate = new PercolationStats(n, t);

		System.out.printf("mean\t\t\t\t\t= %f\n", percostate.mean());
		System.out.printf("stddev\t\t\t\t\t= %f\n", percostate.stddev());
	}

}