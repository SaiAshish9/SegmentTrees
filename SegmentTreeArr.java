package com.sai.designPatterns.segmentTrees;

// Segment trees are commonly used in various algorithmic problems,
// such as queries like finding the minimum, maximum, sum, or other aggregate values over a range,
// of elements in an array. They are also used in problems involving range updates and queries,
// such as range addition, range assignment, etc.

// buildTree
// query
// update

public class SegmentTreeArr {
    private int[] tree;
    private int[] nums;
    private int size;

    public SegmentTreeArr(int[] nums) {
        this.nums = nums;
        this.size = nums.length;
        this.tree = new int[4 * size];
        buildTree(1, 0, size - 1);
    }

    private void buildTree(int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            buildTree(leftChild, start, mid);
            buildTree(rightChild, mid + 1, end);
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    // Query the sum of elements in range [queryStart, queryEnd]
    public int query(int queryStart, int queryEnd) {
        return query(1, 0, size - 1, queryStart, queryEnd);
    }

    private int query(int node, int start, int end, int queryStart, int queryEnd) {
        if (queryStart > end || queryEnd < start) {
            return 0;
        } else if (queryStart <= start && queryEnd >= end) {
            return tree[node];
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            int leftSum = query(leftChild, start, mid, queryStart, queryEnd);
            int rightSum = query(rightChild, mid + 1, end, queryStart, queryEnd);
            return leftSum + rightSum;
        }
    }

 // Update the value at index i to newValue
    public void update(int i, int newValue) {
        update(1, 0, size - 1, i, newValue);
    }

    private void update(int node, int start, int end, int i, int newValue) {
        if (start == end) {
            tree[node] = newValue;
            nums[i] = newValue;
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            if (i >= start && i <= mid) {
                update(leftChild, start, mid, i, newValue);
            } else {
                update(rightChild, mid + 1, end, i, newValue);
            }
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    public void printTree() {
        printTree(1, 0, size - 1, 0);
    }

    private void printTree(int node, int start, int end, int level) {
        if (start == end) {
//            System.out.println(getIndent(level) + "[" + start + ", " + end + "]: " + tree[node]);
            System.out.println(getIndent(level) + tree[node]);
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            printTree(leftChild, start, mid, level + 1);
//            System.out.println(getIndent(level) + "[" + start + ", " + end + "]: " + tree[node]);
            System.out.println(getIndent(level) + tree[node]);
            printTree(rightChild, mid + 1, end, level + 1);
        }
    }

    private String getIndent(int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("\t");
        }
        return indent.toString();
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9, 11};
        SegmentTreeArr segmentTreeArr = new SegmentTreeArr(nums);

        // Query sum of elements in range [1, 3]
        int sum = segmentTreeArr.query(1, 3);
        System.out.println("Sum of elements in range [1, 3]: " + sum);

        segmentTreeArr.printTree();

        // Update value at index 2 to 6
        segmentTreeArr.update(2, 6);

        // Query sum of elements in range [1, 3] after update
        sum = segmentTreeArr.query(1, 3);
        System.out.println("Sum of elements in range [1, 3] after update: " + sum);
    }
}

// So, for each query or update operation, the time complexity is
//�
//(
//log
//⁡
//�
//)
//O(logn), where
//�
//n is the number of elements in the array. The space complexity is
//�
//(
//�
//)
//O(n) for storing the segment tree.
