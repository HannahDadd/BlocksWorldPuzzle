import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarSearch extends HeuristicAlgorithm {

	public AStarSearch(double n) {
		super(n);
	}

	/**
	 * A* Search g= number of squares passed by blocks from the start to get to
	 * + agent position h= number of squares until complete f = g+h
	 */
	public void runAStarSearch(Node root) {

		// Create open (Nodes not yet evaluated) and closed (Nodes already
		// evaluated) priority queues
		resetGrid();
		Queue<Node> openQueue = new PriorityQueue<Node>(new NodeComparator());
		openQueue.add(root);
		Node goalNode = null;
		int loopCounter = 0;

		// Calculate the number of blocks until complete for A, B and C for root
		// node
		int h = calculateHeuristic(blockAFinish, grid[1]) + calculateHeuristic(blockBFinish, grid[2])
				+ calculateHeuristic(blockCFinish, grid[3]);
		root.setG(0);
		root.setH(h);

		while (goalNode == null) {

			// Get the value on the open queue with the lowest f
			Node lowestfNode = openQueue.remove();

			// Move to node
			moveNode(lowestfNode);

			// add lowest f to closed list and generate children
			Node[] childNodes = assignChildNodes(lowestfNode);
			loopCounter++;

			// store the positions of A,B and C and the agent
			int posA = grid[1];
			int posB = grid[2];
			int posC = grid[3];
			int posAgent = grid[0];

			// Loop through each child node
			for (Node childNode : childNodes) {

				// move to the child nodes if possible
				if (moveNode(childNode)) {

					// if it's finished stop the search
					if (isFinished()) {
						goalNode = childNode;
					} else {

						// set g - distance between child and parent node + cost
						childNode.setG(childNode.getDepth() + calculateHeuristic(posA, grid[1])
								+ calculateHeuristic(posB, grid[2]) + calculateHeuristic(posC, grid[3])
								+ calculateHeuristic(posAgent, grid[0]));

						// set h- distance from goal to node
						childNode.setH(calculateHeuristic(blockAFinish, grid[1])
								+ calculateHeuristic(blockBFinish, grid[2]) + calculateHeuristic(blockCFinish, grid[3]));

						openQueue.add(childNode);
					}
				}
			}
		}
		try {
			asWriter = new PrintWriter(new File("ASOutputs.txt"));
		} catch (FileNotFoundException e) {
		}
		asWriter.println("Running A* Search");
		System.out.println("A Star Search solution was found by expanding " + loopCounter + " nodes");
		backtrack(goalNode, root, asWriter);
		asWriter.close();
	}
}
