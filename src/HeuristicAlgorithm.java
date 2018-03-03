import java.util.Comparator;

public abstract class HeuristicAlgorithm extends Main{
	Double n;
	
	/**
	 * Set n in constructor
	 */
	public HeuristicAlgorithm(double n){
		this.n = n;
	}
	

	/**
	 * Heuristic calculation- number of moves from one square to another Factors
	 * in the grid size
	 */
	public int calculateHeuristic(int locationOne, int locationTwo) {

		// Get the location over of the two locations
		double locationOneOverN = locationOne / n;
		double locationTwoOverN = locationTwo / n;

		// Get the remainder of the locations over n
		double locationOneOverNRemainder = locationOne / n - (long) (locationOne / n);
		double locationTwoOverNRemainder = locationTwo / n - (long) (locationTwo / n);

		// work out the row and column of locationOne and locationTwo
		int rowDifference = Math.abs(calculateRowNumber(locationOneOverN) - calculateRowNumber(locationTwoOverN));
		int columnDifference = Math.abs(
				calculateColumnNumber(locationOneOverNRemainder) - calculateColumnNumber(locationTwoOverNRemainder));

		// The total squares away will be addition of row number and column
		// numberwill not work for diagonals which are not possible currently
		return rowDifference + columnDifference;
	}

	/**
	 * Calculate the column number of the value over n
	 */
	public int calculateColumnNumber(double locationOverNRemainder) {

		// if the locationOverNRemainder is 0 return the last column
		if (locationOverNRemainder == 0.0) {
			return n.intValue();
		}

		// Each column will have the same remainder which increases in
		// increments of 1/n
		Double increment = 1 / n;

		// The column number will be the number of increments the remainder
		// contains
		return (int) (locationOverNRemainder / increment);
	}

	/**
	 * Calculate the row of a value over n
	 */
	public int calculateRowNumber(double locationOverN) {
		
		// If it's 0 it must be at row 1
		if(locationOverN == 0){
			return 1;
		}

		// Loop through each row
		for (int i = 1; i < n + 1; i++) {
			if (locationOverN > i - 1 && locationOverN <= i) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Class to compare nodes for priority queue
	 */
	public class NodeComparator implements Comparator<Node> {
		/**
		 * Compare f values of 2 nodes
		 */
		@Override
		public int compare(Node nodeOne, Node nodeTwo) {

			if (nodeOne.getF() > nodeTwo.getF()) {
				return 1;
			}
			if (nodeTwo.getF() > nodeOne.getF()) {
				return -1;
			}
			return 0;
		}
	}
}
