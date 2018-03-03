import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class DepthFirstSearch extends Main{
	int nodesExpanded = 0;
	
	/**
	 * Depth first search
	 */
	public DepthFirstSearch(Node root) {

		// Store current node
		resetGrid();
		Node node = root;
		int loopCounter = 0;
		
		while (!isFinished()) {

			// expand current node
			Node[] childNodes = assignRandomChildNodes(node);

			// Move to the leftmost Node that is possible
			int i = 0;
			while (moveNode(childNodes[i]) != true) {
				i++;
			}

			// current node has changed
			node = childNodes[i];
			loopCounter++;
		}
//		System.out.println("Depth First Search solution was found by expanding " + loopCounter + " nodes");
		nodesExpanded = loopCounter;
		try {
			dfsWriter = new PrintWriter(new File("DFSOutputs.txt"));
		} catch (FileNotFoundException e) {
		}
		dfsWriter.println("Running Depth First Search");
		backtrack(node, root, dfsWriter);
		dfsWriter.close();
	}

	public int getNodesExpanded() {
		return nodesExpanded;
	}
}
