package com.sai.designPatterns.segmentTrees;

import java.util.LinkedList;
import java.util.Queue;

public class SegmentTree {
    private TreeNode root;
    private int[] nums;

    public SegmentTree(int[] nums) {
        this.nums = nums;
        this.root = buildTree(0, nums.length - 1, 0);
    }

    private TreeNode buildTree(int start, int end, int depth) {
        if (start == end) {
            return new TreeNode(start, end, nums[start], depth);
        } else {
            int mid = start + (end - start) / 2;
            TreeNode leftChild = buildTree(start, mid, depth + 1);
            TreeNode rightChild = buildTree(mid + 1, end, depth + 1);
            int sum = leftChild.sum + rightChild.sum;
            return new TreeNode(start, end, sum, leftChild, rightChild);
        }
    }

    public int query(int queryStart, int queryEnd) {
        return query(root, queryStart, queryEnd);
    }

    private int query(TreeNode node, int queryStart, int queryEnd) {
        if (node.start > queryEnd || node.end < queryStart) {
            return 0; // Out of range
        } else if (node.start >= queryStart && node.end <= queryEnd) {
            return node.sum; // Current segment is fully within the query range
        } else {
            return query(node.left, queryStart, queryEnd) + query(node.right, queryStart, queryEnd);
        }
    }

    public void update(int i, int newValue) {
        update(root, i, newValue);
    }

    private void update(TreeNode node, int i, int newValue) {
        if (node.start == node.end && node.start == i) {
            node.sum = newValue;
        } else {
            int mid = node.start + (node.end - node.start) / 2;
            if (i <= mid) {
                update(node.left, i, newValue);
            } else {
                update(node.right, i, newValue);
            }
            node.sum = node.left.sum + node.right.sum;
        }
    }

    public void printTree() {
        if (root == null) return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            StringBuilder levelString = new StringBuilder();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                // Add node value to the level string
                levelString.append(getIndentedValue(node));

                // Enqueue left and right children if they exist
                if (node.left != null) {
                    node.left.depth = node.depth + 1;
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    node.right.depth = node.depth + 1;
                    queue.offer(node.right);
                }
            }

            // Print the level string with appropriate indentation
            System.out.println(levelString);
        }
    }

    private String getIndentedValue(TreeNode node) {
        StringBuilder indentedValue = new StringBuilder();

        // Add indentation based on the depth of the node
        for (int j = 0; j < node.depth; j++) {
            indentedValue.append("   ");
        }

        // Add node value with spacing
        indentedValue.append(String.format("%3d", node.sum));

        return indentedValue.toString();
    }

    static class TreeNode {
        int start;
        int end;
        int sum;
        TreeNode left;
        TreeNode right;
        int depth;

        public TreeNode(int sum) {
            this.sum = sum;
        }

        public TreeNode(int start, int end, int sum, int depth) {
            this.start = start;
            this.end = end;
            this.sum = sum;
            this.depth = depth;
        }

        public TreeNode(int start, int end, int sum, TreeNode left, TreeNode right) {
            this.start = start;
            this.end = end;
            this.sum = sum;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9, 11};
        SegmentTree segmentTreeArr = new SegmentTree(nums);

        // Query sum of elements in range [1, 3]
        int sum = segmentTreeArr.query(1, 3);
        System.out.println("Sum of elements in range [1, 3]: " + sum);

        segmentTreeArr.printTree();

        // Update value at index 2 to 6
        segmentTreeArr.update(2, 6);

//        TreeNode t = new TreeNode(36);
//        t.left = new TreeNode(9);
//        t.left.left = new TreeNode(4);
//        t.left.left.left = new TreeNode(1);
//        t.left.left.right = new TreeNode(3);
//        t.left.right = new TreeNode(5);
//        t.right = new TreeNode(27);
//        t.right.right.left = new TreeNode(16);
//        t.right.right.left.left = new TreeNode(7);
//        t.right.right.left.right = new TreeNode(9);
//        t.right.right.right = new TreeNode(11);

        // Query sum of elements in range [1, 3] after update
        sum = segmentTreeArr.query(1, 3);
        System.out.println("Sum of elements in range [1, 3] after update: " + sum);
    }
}

