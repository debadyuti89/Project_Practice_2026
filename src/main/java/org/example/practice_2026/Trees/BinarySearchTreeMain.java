package Trees;

class BinarySearchTree {
    public BinarySearchTree() {
    }

    public class Node {
        private int value;
        private int height;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public int getValue() {
            return value;
        }
    }

    private Node root;

    public Node getRoot() {
        return root;
    }

    public int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.getValue()) {
            node.left = insertRec(node.left, value);
        } else if (value > node.getValue()) {
            node.right = insertRec(node.right, value);
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        return node;
    }

    public void populate(int[] nums) {
        for (int num : nums) {
            insert(num);
        }
    }

    public void populateSorted(int[] nums) {
        populateSorted(nums, 0, nums.length);
    }

    private void populateSorted(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        insert(nums[mid]);
        populateSorted(nums, start, mid);
        populateSorted(nums, mid + 1, end);
    }

    public boolean balanced(Node node) {
        return isBalanced(node);
    }

    private boolean isBalanced(Node node) {
        if (node == null) {
            return true;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return (Math.abs(leftHeight - rightHeight) <= 1)
                && isBalanced(node.left)
                && isBalanced(node.right);
    }

    public void display() {
        prettyDisplay(root, "Root Node: ");
    }

    private void prettyDisplay(Node node, String details) {
        if (node == null) {
            return;
        }
        System.out.println(details + node.getValue());
        prettyDisplay(node.left, "Left Child of " + node.getValue() + ": ");
        prettyDisplay(node.right, "Right Child of " + node.getValue() + ": ");
    }

    public void preOrderTraversal() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.getValue() + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrderTraversal() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.getValue() + " ");
        inOrder(node.right);
    }

    public void postOrderTraversal() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.getValue() + " ");
    }
}

public class BinarySearchTreeMain {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        // int[] nums = { 10, 5, 15, 3, 7, 12, 18 };
        // bst.populate(nums);
        // bst.display();
        // System.out.println("\nIs the tree balanced? " + bst.balanced(bst.getRoot()));

        System.out.println("\n");
        int[] nums2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        bst.populateSorted(nums2);
        bst.display();
        System.out.println("\nIs the tree balanced? " + bst.balanced(bst.getRoot()));
        System.out.println("\nPre-order Traversal:");
        bst.preOrderTraversal();
        System.out.println("\nIn-order Traversal:");
        bst.inOrderTraversal();
        System.out.println("\nPost-order Traversal:");
        bst.postOrderTraversal();

    }
}
