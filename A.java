
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
//Node class defines a node with path cost as G and heuristic function as H
//and state as a two dimension array 
class Node {

	int h;
	int g;
	int[][] childnode = new int[3][3];

	Node(int[][] childnode) {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.childnode[i][j] = childnode[i][j];
			}
		}

		this.g = 0;
		this.h = 0;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int[][] getChildnode() {
		return childnode;
	}

	public void setChildnode(int[][] childnode) {
		this.childnode = childnode;
	}

}

public class A {

	static ArrayList<Node> visited = new ArrayList<Node>(); // visited Array
															// List Stores all
															// the nodes that
															// have been
															// generated as
															// successor node
	static ArrayList pair = new ArrayList();

	static int ini[][] = { { 5, 4, 0 }, { 6, 1, 8 }, { 7, 3, 2 } }; // initial
																	// state
	static int fin[][] = { { 1, 2, 3 }, { 4, 0, 5 }, { 6, 7, 8 } }; // final
																	// state
	static int found = 0;
	static int count = 0;
	static int expanded = 0;
	static int GoalStateReached = 0;
	static PriorityQueue<Node> queue;

	public static void main(String[] args) {
		queue = new PriorityQueue<Node>(fComparator); // defines a priority
														// queue based on f(n)
														// value which is G(n) +
														// H(n)

		// Printing the initial state
		System.out.println("Initial state");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(ini[i][j] + " ");
			}
			System.out.println();
		}

		Node root = new Node(ini);
		// queue.offer(root);
		// Printing the final state(Goal state)
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		System.out.println("Final state");
		// printing Final 2D array
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(fin[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		visited.add(root);
		GenerateStates(root, fin);
		expanded++; // Counts the No. of expanded nodes
		// queue.poll();
		while (GoalStateReached == 0) {
			Node nodeTest = queue.poll();
			expanded++;
			//System.out.println("nodeTest that is expanded::::::" + nodeTest);
			//System.out.println("queue =" + queue);
			GenerateStates(nodeTest, fin);
		}
		System.out.println();
	}

	// Generating States method generates valid Successor states
	public static void GenerateStates(Node node, int[][] fin) {

		// .clear();
		count++;
		//System.out.println("level::::::::::::::::::::::::: " + count);
		pair.clear();
		for (int i1 = 0; i1 < 3; i1++) {
			for (int j1 = 0; j1 < 3; j1++) {

				if (node.childnode[i1][j1] == 0) {
					validState(i1, j1);

					// System.out.println("valid states::::::::::::::::"+pair);

					// System.out.println(i1+" "+j1);

					int x = 1;

					for (int ip = 0; ip < pair.size(); ip++) {

						int key = (int) pair.get(ip);
						int value = (int) pair.get(ip + 1);
						ip++;
						int Childnode[][] = new int[3][3];

						for (int i = 0; i < 3; i++) {
							for (int j = 0; j < 3; j++) {
								Childnode[i][j] = node.childnode[i][j];
							}
						}

						Childnode[i1][j1] = Childnode[key][value];
						Childnode[key][value] = 0;
						Node Childnode1 = new Node(Childnode);
						Childnode1.g = node.g + 1;
						Childnode1.h = ManhattenDistance(Childnode1.childnode, fin);

						// System.out.println("Child Node::::::"+x);
						// x++;
						isGoalState(Childnode1, fin);
						boolean isNodeRepeted = isNoderepeted(Childnode1.childnode);
						if (isNodeRepeted) {

						} else {
							visited.add(Childnode1);
							queue.offer(Childnode1);
							// System.out.println("queue ="+queue);
						}
					}

				}

			}
		}
	}

	// This Method displays the checks the Child Nodes and checks whether the
	// Goal state is reached or not
	// if the Goal state is found it displays the no. of nodes expanded and
	// depth at which the Goal state was found
	private static void isGoalState(Node childnode, int[][] fin) {

		System.out.println("Displaying Generated Child node");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(childnode.childnode[i][j] + " ");
			}
			System.out.println();
		}
		found = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (childnode.childnode[i][j] == fin[i][j]) {

				} else {
					found = 0;
				}
			}
		}

		if (found == 1) {
			GoalStateReached = 1;
			System.out.println("Congratualtions! Goal state Reached");
			System.out.println("Goal Reached at Depth d =  " + childnode.g);
			System.out.println("Number of Nodes expanded = " + expanded);

			for (int l = 0; l < 3; l++) {
				for (int m = 0; m < 3; m++) {
					System.out.print(childnode.childnode[l][m] + " ");
				}
				System.out.println();
			}
			System.exit(0);
		} else {
			System.out.println("Goal state not Reached");

		}

	}

	// This Method calculates the possible valid states for a current state
	static void validState(int i, int j) {

		if (i + 1 < 3) {
			pair.add(i + 1);
			pair.add(j);
		}
		if (i - 1 >= 0) {
			pair.add(i - 1);
			pair.add(j);
		}
		if (j + 1 < 3) {
			pair.add(i);
			pair.add(j + 1);
		}
		if (j - 1 >= 0) {
			pair.add(i);
			pair.add(j - 1);
		}

		//System.out.println("pair list ::::::::::::::::::::::::::" + pair);
	}

	// This Method calculates the Heuristic function values for corresponding
	// node
	// Heuristic function for above problem is Manhattan distance
	public static int ManhattenDistance(int[][] childnode, int[][] fin) {

		int distance = 0;
		int a, b;
		// System.out.println("Calculate Manhatten Distance");
		for (int i1 = 0; i1 < 3; i1++) {
			for (int j1 = 0; j1 < 3; j1++) {
				for (int i2 = 0; i2 < 3; i2++) {
					for (int j2 = 0; j2 < 3; j2++) {

						if (childnode[i1][j1] != 0) {
							if (childnode[i1][j1] == fin[i2][j2]) {
								a = i1 - i2;
								b = j1 - j2;
								distance = distance + Math.abs(a) + Math.abs(b);
							}
						}

					}
				}
			}
		}

		return distance;

	}

	// This Method checks whether the states has been already visited by
	// comparing it with visited List
	public static boolean isNoderepeted(int[][] childnode) {

		boolean flag = true;
		for (int i = 0; i < visited.size(); i++) {
			flag = true;
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					if (childnode[j][k] != visited.get(i).getChildnode()[j][k]) {
						flag = false;
					}

				}
			}
			if (flag == true) {
				return flag;
			}

		}
		return false;
	}

	// Defines a comparator for priority queue bsed on f(n) = g(n) + h(n)

	public static Comparator<Node> fComparator = new Comparator<Node>() {

		@Override
		public int compare(Node n1, Node n2) {
			return (int) ((n1.getG() + n1.getH()) - (n2.getG() + n2.getH()));
		}
	};

}
