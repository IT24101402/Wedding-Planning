import java.util.Scanner;

public class Node {
    int empNumber;
    String empName;
    Node left, right;

    public Node(int empNumber, String empName) {
        this.empNumber = empNumber;
        this.empName = empName;
        left = right = null;
    }

    public void displayNode() {
        System.out.println("Employee Number: " + empNumber + ", Name: " + empName);
    }
}
class Tree {
    private Node root;

    public void insert(int empNumber, String empName) {
        root = insertRec(root, empNumber, empName);
    }

    private Node insertRec(Node root, int empNumber, String empName) {
        if (root == null) return new Node(empNumber, empName);
        if (empNumber < root.empNumber)
            root.left = insertRec(root.left, empNumber, empName);
        else
            root.right = insertRec(root.right, empNumber, empName);
        return root;
    }

    public Node findRecursive(int empNumber) {
        return findRecursive(root, empNumber);
    }

    private Node findRecursive(Node root, int empNumber) {
        if (root == null || root.empNumber == empNumber) return root;
        return (empNumber < root.empNumber)
                ? findRecursive(root.left, empNumber)
                : findRecursive(root.right, empNumber);
    }

    public void deleteAll() {
        root = null; // Allow garbage collector to remove all nodes
        System.out.println("All nodes deleted.");
    }

    public void inorder() {
        System.out.println("Inorder Traversal:");
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            root.displayNode();
            inorderRec(root.right);
        }
    }

    public void preorder() {
        System.out.println("Preorder Traversal:");
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(Node root) {
        if (root != null) {
            root.displayNode();
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    public void postorder() {
        System.out.println("Postorder Traversal:");
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            root.displayNode();
        }
    }
}

class TreeApp {
    public static void main(String[] args) {
        Tree employeeTree = new Tree();
        Scanner scanner = new Scanner(System.in);


        employeeTree.insert(1001, "Alice");
        employeeTree.insert(1005, "Bob");
        employeeTree.insert(1003, "Charlie");
        employeeTree.insert(1002, "David");
        employeeTree.insert(1006, "Eve");
        employeeTree.insert(1007, "Frank");
        employeeTree.insert(1008, "Grace");
        employeeTree.insert(1009, "Heidi");
        employeeTree.insert(1010, "Ivy");
        employeeTree.insert(1011, "Jack");


        employeeTree.inorder();
        employeeTree.preorder();
        employeeTree.postorder();


        System.out.print("Enter employee number to search: ");
        int empNum = scanner.nextInt();
        Node found = employeeTree.findRecursive(empNum);
        if (found != null) {
            System.out.print("Employee found: ");
            found.displayNode();
        } else {
            System.out.println("Employee not found.");
        }


        employeeTree.deleteAll();


        employeeTree.inorder();
    }
}
