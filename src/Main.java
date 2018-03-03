import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	// The completed positions of each of the blocks
	int blockAFinish = 6;
	int blockBFinish = 10;
	int blockCFinish = 14;

	// The grid size n*n
	int n = 4;

	// The current grid format [agent, blockA, blockB, blockC]
	protected int[] grid = new int[4];

	// Blocked Squares- where the agent cannot go
	ArrayList<Integer> blockedSquares = new ArrayList<Integer>();

	// writers for writing output
	protected PrintWriter dfsWriter = null;
	public PrintWriter bfsWriter = null;
	protected PrintWriter idWriter = null;
	protected PrintWriter asWriter = null;
	protected PrintWriter bestfsWriter = null;

	int iDLoopCounter = 0;

	/**
	 * Reset the grid to starting position
	 */
	public void resetGrid() {
		grid[0] = 16;
		grid[1] = 13;
		grid[2] = 14;
		grid[3] = 15;
	}

	/**
	 * Code run when new Node is created
	 */
	public Node[] addBranchesToNode(Node node, ArrayList<Integer> numbers) {

		// Create nodes based on set
		Node[] addedNodes = new Node[4];
		int loopCounter = 0;
		for (Integer i : numbers) {
			if (i.equals(1)) {
				addedNodes[loopCounter] = new Node("u", node, node.getDepth() + 1);
			}
			if (i.equals(2)) {
				addedNodes[loopCounter] = new Node("d", node, node.getDepth() + 1);
			}
			if (i.equals(3)) {
				addedNodes[loopCounter] = new Node("l", node, node.getDepth() + 1);
			}
			if (i.equals(4)) {
				addedNodes[loopCounter] = new Node("r", node, node.getDepth() + 1);
			}
			loopCounter++;
		}

		return addedNodes;
	}

	/**
	 * Assign node values randomly
	 */
	public Node[] assignRandomChildNodes(Node node) {

		// Generate numbers until arraylist is full with no duplicates
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		Random rand = new Random();
		while (numbers.size() != 4) {
			int num = rand.nextInt(4) + 1;
			if (!numbers.contains(num)) {
				numbers.add(num);
			}
		}

		return addBranchesToNode(node, numbers);
	}

	/**
	 * Assign node values in order
	 */
	public Node[] assignChildNodes(Node node) {

		// Generate numbers until arraylist is full with no duplicates
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i = 1; i < 5; i++) {
			numbers.add(i);
		}

		return addBranchesToNode(node, numbers);
	}

	/**
	 * Move the agent and blocks
	 */
	public boolean moveNode(Node node) {
		
		// Special case- root node
		if (node.getValue() == "na") {
			return true;
		}

		// Set the grid correctly
		grid = node.getParentNode().getGridAtNode().clone();

		// store agent's current position
		int agentPos = grid[0];

		// Move the agent if it is legal (i.e. not moving off the board)
		if (node.getValue() == "u") {
			if ((grid[0] - n) > 0 && !blockedSquares.contains(grid[0] - n)) {
				grid[0] = grid[0] - n;
			} else {
				return false;
			}
		}
		if (node.getValue() == "d") {
			if ((grid[0] + n) <= (n * n) && !blockedSquares.contains(grid[0] + n)) {
				grid[0] = grid[0] + n;
			} else {
				return false;
			}
		}
		if (node.getValue() == "l") {
			if (!((grid[0] - 1) % n == 0) && !blockedSquares.contains(grid[0] - 1)) {
				grid[0] = grid[0] - 1;
			} else {
				return false;
			}
		}
		if (node.getValue() == "r") {
			if (!((grid[0] % n) == 0) && !blockedSquares.contains(grid[0] + 1)) {
				grid[0] = grid[0] + 1;
			} else {
				return false;
			}
		}

		// move blocks where necessary
		for (int i = 1; i < 4; i++) {
			if (grid[0] == grid[i]) {
				grid[i] = agentPos;
			}
		}

		node.setGridAtNode(grid.clone());
		return true;
	}
	
	/**
	 * Once a solution is found backtrack to start node
	 */
	public void backtrack(Node goalNode, Node root, PrintWriter writer){
		
		// Once you have the goal node backtrack back to find solution
		int steps = 0;
		while (goalNode != root) {
			grid = goalNode.getGridAtNode();
			steps++;
			goalNode = goalNode.getParentNode();
			writer.println("Agent: " + grid[0] + " A: " + grid[1] + " B: " + grid[2] + " C: " + grid[3]);
		}
		System.out.println("The agent took " + steps + " number of moves");
	}

	/**
	 * Check if the game has been won
	 */
	public boolean isFinished() {

		if (grid[1] == blockAFinish) {
			if (grid[2] == blockBFinish) {
				if (grid[3] == blockCFinish) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Main method
	 */
	public static void main(String[] args) {

		// make tree and output files
		Main m = new Main();
		m.resetGrid();
		BinarySearchTree t = new BinarySearchTree(m.grid);

		// run algorithms
		DepthFirstSearch d = new DepthFirstSearch(t.getRoot());

		BreadthFirstSearch b = new BreadthFirstSearch(t.getRoot());

		IterativeDeepeningSearch i = new IterativeDeepeningSearch(t.getRoot());

		AStarSearch a = new AStarSearch(m.n);
		a.runAStarSearch(t.getRoot());
	}
}