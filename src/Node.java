public class Node{
	Node parentNode;
	String value;
	int depth;
	int[] gridAtNode;
	
	int g;
	int h;

	public int getDepth() {
		return depth;
	}

	public String getValue() {
		return value;
	}

	public Node getParentNode() {
		return parentNode;
	}
	
	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public int[] getGridAtNode() {
		return gridAtNode;
	}

	public void setGridAtNode(int[] gridAtNode) {
		this.gridAtNode = gridAtNode;
	}

	public Node(String value, Node parentNode, int depth){
		this.value = value;
		this.parentNode = parentNode;
		this.depth = depth;
	}
	
	// g and h are only used for the a star search
	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public int getF(){
		return h+g;
	}
}
