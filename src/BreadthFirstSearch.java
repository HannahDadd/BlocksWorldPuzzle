import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends Main{
	
	/**
	 * Breadth firth search
	 */
	public BreadthFirstSearch(Node root) {

		// create queue containing just the root node
		resetGrid();
		Queue<Node> nodeQueue = new LinkedList<Node>();
		nodeQueue.add(root);
		
		// Loop counter and goal node
		int loopCounter = 0;
		Node goalNode = null;

		while (goalNode == null) {

			// take node off queue
			Node node = nodeQueue.remove();
			loopCounter++;
			
			// add child nodes
			Node[] childNodes = assignChildNodes(node);

			// Move to each child node
			for (int i = 0; i < 4; i++) {

				// Move to node
				if (moveNode(childNodes[i])) {

					// If finished you're done
					if (isFinished()) {
						goalNode = childNodes[i];
					} else {
						nodeQueue.add(childNodes[i]);
					}
				}
			}
		}

		System.out.println("Breadth First Search solution was found by expanding " + loopCounter + " nodes");
		try {
			bfsWriter = new PrintWriter(new File("BFSOutputs.txt"));
		} catch (FileNotFoundException e) {	}
		bfsWriter.println("Running Breadth first search");
		backtrack(goalNode, root, bfsWriter);
		bfsWriter.close();
	}
}
