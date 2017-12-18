import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {

	private int[][] currentPic;
	private double[][] currentEnergy;
	private int currentHeight;
	private int currentWidth;
	
	private boolean isTranspose;
	private boolean isHorizontal;
	
	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		
		if (picture == null) {
			throw new java.lang.IllegalArgumentException();
		}
		
		this.currentWidth = picture.width();
		this.currentHeight = picture.height();
		this.currentPic = new int[this.currentHeight][this.currentWidth];
		this.currentEnergy = new double[this.currentHeight][this.currentWidth];
		
		this.isTranspose = false;
		
		// initialize energy for each pixel
		for (int i = 0; i < this.currentHeight; i++) {
			for (int j = 0; j < this.currentWidth; j++) {
				currentPic[i][j] = picture.get(j, i).getRGB();
			}
		}
		
		for (int i = 0; i < this.currentHeight; i++) {
			for (int j = 0; j < this.currentWidth; j++) {
				this.currentEnergy[i][j] = this.energy(j, i);
			}
		}
		
		// create
	}
	
	// current picture
	public Picture picture() { 
		if (this.isTranspose) {
			this.transpose();
		}
		
		Picture pic = new Picture(this.currentWidth, this.currentHeight);
		for (int i = 0; i < this.currentHeight; i++) {
			for (int j = 0; j < this.currentWidth; j++) {
//				System.out.println(this.currentPic[i][j]);
				pic.set(j, i, new Color(this.currentPic[i][j]));
			}
		}
		
		return pic;
	}
	
	// width of current picture
	public int width() {
		return (this.isTranspose) ? this.currentHeight : this.currentWidth;
	}
	
	// height of current picture
	public int height() {
		return (this.isTranspose) ? this.currentWidth : this.currentHeight;
	}
	
	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		if (this.isTranspose && !this.isHorizontal) {
			int temp = x;
			x = y;
			y = temp;
		}
		
		if (x < 0 || x >= this.currentWidth || y < 0 || y > this.currentHeight - 1) {
			throw new java.lang.IllegalArgumentException();
		}
		
		if (x == 0 || x == this.currentWidth - 1 || y == 0 || y == this.currentHeight - 1) {
			return 1000;
		}
		
		Color rgb = new Color(this.currentPic[y][x]);
		Color rgbym1 = new Color(this.currentPic[y - 1][x]);
		Color rgbyp1 = new Color(this.currentPic[y + 1][x]);
		Color rgbxm1 = new Color(this.currentPic[y][x - 1]);
		Color rgbxp1 = new Color(this.currentPic[y][x + 1]);
		
		double dxSq = (rgbxm1.getRed() - rgbxp1.getRed()) * (rgbxm1.getRed() - rgbxp1.getRed()) 
				+ (rgbxm1.getGreen() - rgbxp1.getGreen()) * (rgbxm1.getGreen() - rgbxp1.getGreen()) 
				+ (rgbxm1.getBlue() - rgbxp1.getBlue()) * (rgbxm1.getBlue() - rgbxp1.getBlue());
		double dySq = (rgbym1.getRed() - rgbyp1.getRed()) * (rgbym1.getRed() - rgbyp1.getRed()) 
				+ (rgbym1.getGreen() - rgbyp1.getGreen()) * (rgbym1.getGreen() - rgbyp1.getGreen()) 
				+ (rgbym1.getBlue() - rgbyp1.getBlue()) * (rgbym1.getBlue() - rgbyp1.getBlue());
		
		return Math.sqrt(dxSq + dySq);
	}
	
	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {
		
		if (!this.isTranspose) {
			this.transpose();
		}
		
		this.isHorizontal = true;
		int[] seam =  this.findVerticalSeam();
		this.isHorizontal = false;
		
		return seam;
	}
	
	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		if (!this.isHorizontal && this.isTranspose) {
			this.transpose();
		}
		
		if (this.currentWidth == 0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		int[] seam = new int[this.currentHeight];
		int[][] dir = new int[this.currentHeight][this.currentWidth];
		
		if (this.currentWidth == 1) {
			return seam;
		}
		
//		double[][] minE = new double[this.currentHeight][this.currentWidth];
		double[] minEOld = new double[this.currentWidth];
		double[] minENew = new double[this.currentWidth];
		
		for (int j = 0; j < this.currentWidth; j++) {
			minEOld[j] = this.currentEnergy[0][j];
		}
		
		for (int i = 1; i < this.currentHeight; i++) {
			
			if (minEOld[0] <= minEOld[1]) {
				minENew[0] = minEOld[0] + this.currentEnergy[i][0];
				dir[i][0] = 0;
			}
			else {
				minENew[0] = minEOld[1] + this.currentEnergy[i][0];
				dir[i][0] = 1;
			}
			
			if (minEOld[this.currentWidth - 2] <= minEOld[this.currentWidth - 1]) {
				minENew[this.currentWidth - 1] = minEOld[this.currentWidth - 2] + this.currentEnergy[i][this.currentWidth - 1];
				dir[i][this.currentWidth - 1] = -1;
			}
			else {
				minENew[this.currentWidth - 1] = minEOld[this.currentWidth - 1] + this.currentEnergy[i][this.currentWidth - 1];
				dir[i][this.currentWidth - 1] = 0;
			}
			
			for (int j = 1; j < this.currentWidth - 1; j++) {
				dir[i][j] = this.min3(minEOld[j - 1], minEOld[j], minEOld[j + 1]);
				minENew[j] = minEOld[j + dir[i][j]] + this.currentEnergy[i][j];
			}
			
			System.arraycopy(minENew, 0, minEOld, 0, minENew.length);
		}
		
		int seamEnd = 0;
		double minEnergy = Double.MAX_VALUE;
		for (int j = 0; j < this.currentWidth; j++) {
			if (minENew[j] <= minEnergy) {
				seamEnd = j;
				minEnergy = minENew[j];
			}
		}
		
		
//		for (int i = 1; i < this.currentHeight; i++) {
//			
//			if (minE[i - 1][0] <= minE[i - 1][1]) {
//				minE[i][0] = minE[i - 1][0] + this.currentEnergy[i][0];
//				dir[i][0] = 0;
//			}
//			else {
//				minE[i][0] = minE[i - 1][1] + this.currentEnergy[i][0];
//				dir[i][0] = 1;
//			}
//			
//			if (minE[i - 1][this.currentWidth - 2] <= minE[i - 1][this.currentWidth - 1]) {
//				minE[i][this.currentWidth - 1] = minE[i - 1][this.currentWidth - 2] + this.currentEnergy[i][this.currentWidth - 1];
//				dir[i][this.currentWidth - 1] = -1;
//			}
//			else {
//				minE[i][this.currentWidth - 1] = minE[i - 1][this.currentWidth - 1] + this.currentEnergy[i][this.currentWidth - 1];
//				dir[i][this.currentWidth - 1] = 0;
//			}
//			
//			for (int j = 1; j < this.currentWidth - 1; j++) {
////				minE[i][j] = Math.min(Math.min(minE[i-1][j-1], minE[i-1][j]), minE[i-1][j+1]) + this.currentEnergy[i][j];
//				dir[i][j] = this.min3(minE[i - 1][j - 1], minE[i - 1][j], minE[i - 1][j + 1]);
//				minE[i][j] = minE[i-1][j + dir[i][j]] + this.currentEnergy[i][j];
//			}
//		}
//		
//		int seamEnd = 0;
//		double minEnergy = minE[this.currentHeight - 1][0];
//		for (int j = 0; j < this.currentWidth; j++) {
//			if (minE[this.currentHeight - 1][j] <= minEnergy) {
//				seamEnd = j;
//				minEnergy = minE[this.currentHeight - 1][j];
//			}
//		}
		
		seam[this.currentHeight - 1] = seamEnd;
		for (int i = this.currentHeight - 1; i > 0; i--) {
			seam[i - 1] = seam[i] + dir[i][seam[i]];
		}
		
		return seam;
		
	}
	
	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		if (seam == null) {
			throw new java.lang.IllegalArgumentException();
		}
		
		if (!this.isTranspose) {
			this.transpose();
		}
		
		this.isHorizontal = true;
		this.removeVerticalSeam(seam);
		this.isHorizontal = false;
		
	}
	
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		
		if (seam == null) {
			throw new java.lang.IllegalArgumentException();
		}
		
		if (!this.isHorizontal && this.isTranspose) {
			this.transpose();
		}
		
		isSeamValid(seam);
		
		// shift and recalculate energy array;
		for (int i = 0; i < this.currentHeight; i++) {
			if (seam[i] != this.currentWidth - 1) {
				System.arraycopy(this.currentPic[i], seam[i] + 1, this.currentPic[i], seam[i], this.currentWidth - seam[i] - 1);
				System.arraycopy(this.currentEnergy[i], seam[i] + 1, this.currentEnergy[i], seam[i], this.currentWidth - seam[i] - 1);
			}
			
		}
		
		this.currentWidth -= 1;
		this.recalculateEnergy(seam);
		
	}
	
	private void isSeamValid(int[] seam) {
		// illegal seam inputs
		if (seam.length != this.currentHeight || this.currentWidth <= 1) {
			throw new java.lang.IllegalArgumentException();
		}
		
		for (int i = 0; i < this.currentHeight; i++) {
			if (seam[i] < 0 || seam[i] > this.currentWidth - 1) {
				throw new java.lang.IllegalArgumentException();
			}
		}
		
		for (int i = 1; i < this.currentHeight; i++) {
			if (seam[i] - seam[i - 1] < - 1 || seam[i] - seam[i - 1] > 1){
				throw new java.lang.IllegalArgumentException();
			}
		}
	}
	
	private int min3(double a, double b, double c) {
		if (a <= b) {
			return (a <= c) ? -1 : 1;
		}
		else {
			return (b <= c) ? 0 : 1;
		}
	}
	
	private void recalculateEnergy(int[] seam) {
		for (int i = 0; i < seam.length; i++) {
			if (seam[i] != 0) {
				this.currentEnergy[i][seam[i] - 1] = this.energy(seam[i] - 1, i);
			}
			
			if (seam[i] != this.currentWidth) {
				this.currentEnergy[i][seam[i]] = this.energy(seam[i], i);
			}
		}
	}
	
	private void transpose() {
		
		this.isTranspose = !this.isTranspose;
		
		// transpose picture and enery array
		int[][] transPic = new int[this.currentWidth][this.currentHeight];
		double[][] transEnergy = new double[this.currentWidth][this.currentHeight];

		for (int i = 0; i < this.currentHeight; i++) {
			for (int j = 0; j < this.currentWidth; j++) {
				transPic[j][i] = this.currentPic[i][j];
				transEnergy[j][i] = this.currentEnergy[i][j];
			}
		}
		
		int forSwap = this.currentHeight;
		this.currentHeight = this.currentWidth;
		this.currentWidth = forSwap;
		
		this.currentEnergy = transEnergy;
		this.currentPic = transPic;
	}	
	
	public static void main(String[] args) {
		String pictureFile = "../seam/3x4.png";
//		Picture picture = new Picture(pictureFile);
		
	}
}
