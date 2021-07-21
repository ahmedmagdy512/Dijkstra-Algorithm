import java.util.*;

public class Solver {
	Map<Integer, Integer> distanceMap;
	Map<Integer, Node> nodesMap;

	public Map<Integer, Integer> getDistanceMap() {
		return distanceMap;
	}

	public Map<Integer, Node> getNodesMap() {
		return nodesMap;
	}

	static class Node {
		public int u;
		public String path;

		@Override
		public String toString() {
			return "Node [ u = " + u + ", path = " + path + " ]";
		}

		protected Node(int u, String path) {
			super();
			this.u = u;
			this.path = path;
		}
	}

	static class Edge {
		public int u;
		public int v;
		public int cost;

		public Edge(int u, int v, int cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}

		@Override
		public String toString() {
			return "Edge [u=" + u + ", v=" + v + ", cost=" + cost + "]";
		}
	}

	static class Graph {
		public List<Edge> edges = new ArrayList<>();
		public int nodesNumber;

		public Graph(List<Edge> edges) {
			this.edges = edges;
		}
	}

	public static List<Edge> makeEdges(int u, int v, int cost) {
		return Arrays.asList(new Edge(u, v, cost), new Edge(v, u, cost));
	}

	public static Graph initializeGraph() {
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(makeEdges(1, 2, 1));
		edges.addAll(makeEdges(1, 3, 2));
		edges.addAll(makeEdges(1, 4, 3));
		edges.addAll(makeEdges(1, 5, 4));
		edges.addAll(makeEdges(2, 3, 5));
		edges.addAll(makeEdges(2, 5, 6));
		edges.addAll(makeEdges(3, 4, 5));
		edges.addAll(makeEdges(4, 5, 4));
		edges.addAll(makeEdges(4, 6, 3));
		edges.addAll(makeEdges(5, 6, 2));
		edges.addAll(makeEdges(5, 7, 1));
		edges.addAll(makeEdges(6, 7, 5));
		edges.addAll(makeEdges(7, 8, 1));
		return new Graph(edges);
	}

	public static Map<Integer, Integer> initializeCostMap(int nodesNumber) {
		Map<Integer, Integer> mp = new HashMap<>();
		for (int i = 1; i <= nodesNumber; i++) {
			mp.put(i, Integer.MAX_VALUE);
		}
		return mp;
	}

	public static Map<Integer, Node> initializeNodes(int nodesNumber) {
		Map<Integer, Node> nodes = new HashMap<>();
		for (int i = 1; i <= nodesNumber; i++) {
			nodes.put(i, new Node(i, String.valueOf(i)));
		}
		return nodes;
	}

	public void solveGraph(Graph graph, int nodesNumber, int source) {
		if (source < 1 || source > nodesNumber)
			return;
		Map<Integer, Integer> mp = initializeCostMap(nodesNumber);
		Map<Integer, Node> nodes = initializeNodes(nodesNumber);
		mp.put(source, 0);
		Queue<Integer> queue = new PriorityQueue<>();
		queue.add(source);
		Set<Integer> setOfCompletedNodes = new HashSet<>();
		setOfCompletedNodes.add(source);
		while (!queue.isEmpty()) {
			int u = queue.poll();
			setOfCompletedNodes.add(u);
			graph.edges.stream().filter(edge -> {
				return edge.u == u && !setOfCompletedNodes.contains(edge.v);
			}).forEach(edge -> {
				int v = edge.v;
				int uCost = mp.get(u);
				int vCost = mp.get(v);
				int distance = edge.cost;
				if (uCost + distance < vCost) {
					vCost = uCost + distance;
					mp.put(v, vCost);
					queue.add(v);
					String newPath = nodes.get(u).path + " -> " + v;
					nodes.get(v).path = newPath;
				}
			});

		}
		distanceMap = mp;
		nodesMap = nodes;
	}

	public static int sum(Collection<Integer> collection) {
		return collection.stream().reduce(0, (a,b)-> a+b);
	}

	public static void main(String[] args) {
		Graph graph = initializeGraph();
		Solver solver = new Solver();
		int numberOfNodes = 8;
		solver.solveGraph(graph, numberOfNodes, 5);
		System.out.println(solver.distanceMap);
		solver.nodesMap.values().forEach(System.out::println);

	}

}
