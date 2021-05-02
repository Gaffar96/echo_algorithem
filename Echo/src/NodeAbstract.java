import java.util.HashSet;
import java.util.Set;


public abstract class NodeAbstract extends Thread implements Node {

	protected final String name;
	protected final boolean initiator;
	protected final Set<Node> neighbours = new HashSet<Node>();
	protected Monitor m;

	public NodeAbstract(String name, boolean initiator, Monitor m) {
		super(name);
		this.name = name;
		this.initiator = initiator;
		this.m = m;
	}

	public abstract void setupNeighbours(Node... neighbours);
	
	

	@Override
	public String toString() {
		return name;
	}

}
