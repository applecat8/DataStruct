package com.applecat;

import java.util.Stack;

public class SearchToSumTree {
    //	int sum = 0;
//	public TreeNode bstToGst(TreeNode root) {
//		if (root == null) return null;
//		bstToGst(root.right);
//		sum += root.val;
//		root.val = sum;
//		bstToGst(root.left);
//		return root;
//	}
    public TreeNode bstToGst(TreeNode root) {
        if (root == null) return null;
        TreeNode temp = root;
        int sum = 0;
        Stack<TreeNode> stack = new Stack<TreeNode>();

        while (!stack.isEmpty() || temp != null) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.right;
            }

            if (!stack.isEmpty()) {
                temp = stack.pop();
                sum += temp.val;
                temp.val = sum;
                temp = temp.left;
            }
        }
        return root;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

