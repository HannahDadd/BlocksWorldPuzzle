
public class BinarySearchTree {
	Node root;

	public Node getRoot() {
		return root;
	}

	public BinarySearchTree(int[] grid){
		root = new Node("na", null, 0);
		root.setGridAtNode(grid);
	}
}
