import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class IterativeDeepeningSearch extends Main {
	
	/**
	 * Depth first search over a set group of nodes- used for iterative
	 * deepening
	 */
	public Node depthLimitedSearch(Node node, int limit) {
		Node goalNode = null;

		iDLoopCounter++;

		// If there are no more nodes to expand this is not the correct path
		if (limit == 0) {

			// if you can move to the node move to it
			moveNode(node);
			
			// check if final position has been reached
			if (isFinished()) {
				return node;
			} else {
				return null;
			}

		} else {

			// If you can move to that node move to it
			if (moveNode(node)) {

				// Expand Node
				Node[] childNodes = assignChildNodes(node);

				// push the child nodes onto the stack
				// (stack is FIFO)
				for (Node childNode : childNodes) {
					goalNode = depthLimitedSearch(childNode, limit - 1);

					if (goalNode != null) {
						return goalNode;
					}
				}
			}
			return null;
		}
	}

	/**
	 * Iterative Deepening Search
	 */
	public IterativeDeepeningSearch(Node root) {

		int limit = 0;
		resetGrid();
		Node goalNode = null;
		while (goalNode == null) {
			resetGrid();
			goalNode = depthLimitedSearch(root, limit);
			limit++;
		}
		System.out.println("Iterative Deepening solution was found by expanding " + iDLoopCounter + " nodes");
		try {
			idWriter = new PrintWriter(new File("IDOutputs.txt"));
		} catch (FileNotFoundException e) {
		}
		idWriter.println("Running Iterative Deepening Search");
		backtrack(goalNode, root, idWriter);
		idWriter.close();
	}
}
