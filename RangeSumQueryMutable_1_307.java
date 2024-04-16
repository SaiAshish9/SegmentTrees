package com.sai.designPatterns.segmentTrees;

class RangeSumQueryMutable_1_307 {

    private TreeNode root;
    private int[] nums;

    public RangeSumQueryMutable_1_307(int[] nums) {
        this.nums = nums;
        this.root = buildTree(0, nums.length - 1, 0);
    }

    public void update(int index, int val) {
        update(root, index, val);
    }

    public int sumRange(int left, int right) {
        return query(root, left, right);
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

    private int query(TreeNode node, int queryStart, int queryEnd) {
        if (node.start > queryEnd || node.end < queryStart) {
            return 0; // Out of range
        } else if (node.start >= queryStart && node.end <= queryEnd) {
            return node.sum; // Current segment is fully within the query range
        } else {
            return query(node.left, queryStart, queryEnd) + query(node.right, queryStart, queryEnd);
        }
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

}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(index,val);
 * int param_2 = obj.sumRange(left,right);
 */