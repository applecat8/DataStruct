package com.applecat;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉排序数
 */
public class BinarySortTree {

    private Node root;

    private static class Node {
        int value;
        Node leftNode;
        Node rightNode;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 通过将二叉数补全为完全二叉树的数组表示形式来构建二叉树
     *
     * @param values 完全二叉树数组 #-1表示为空
     */
    public BinarySortTree(int[] values) {
        root = linkNode(values, 1);
    }

    private Node linkNode(int[] values, int start) {
        if (start - 1 >= values.length || values[start - 1] == -1) return null;

        Node node = new Node();
        node.value = values[start - 1];
        node.leftNode = linkNode(values, start * 2);
        node.rightNode = linkNode(values, start * 2 + 1);

        return node;
    }

    /**
     * 根据给定的值来查找节点
     */
    public Node findNode(int number) {
        Node temp = root;
        while (temp != null) {
            if (temp.value == number) {
                return temp;
            } else if (temp.value < number) {
                temp = temp.rightNode;
            } else {
                temp = temp.leftNode;
            }
        }
        return null;
    }

    /**
     * 查找最大节点
     */
    public Node findMaxNode() {
        if (root == null) return null;
        Node temp = root;
        while (temp.rightNode != null) {
            temp = temp.rightNode;
        }
        return temp;
    }

    /**
     * 查找最小节点
     */
    public Node findMinNode() {
        Node temp = root;
        if (temp == null) return null;
        while (temp.leftNode != null) {
            temp = temp.leftNode;
        }
        return temp;
    }

    /**
     * 查找最小节点
     */
    public Node findMinNode(Node root) {

        Node temp = root;
        if (temp == null) return null;
        while (temp.leftNode != null) {
            temp = temp.leftNode;
        }
        return temp;
    }

    /**
     * 插入一个节点
     *
     * @param value 插入结点的值
     */
    public void insertNode(int value) {
        if (root == null) {
            root = new Node(value);
        }
        Node temp = root, pre = root;
        while (temp != null) {
            pre = temp;
            if (value > temp.value) {
                temp = temp.rightNode;
            } else {
                temp = temp.leftNode;
            }
        }

        if (value > pre.value) pre.rightNode = new Node(value);
        else pre.leftNode = new Node(value);
    }

    /**
     * 删除节点
     *
     * @param value 待删除结点的值
     */
    public Node deleteNode(int value) {
        Node temp = root, pre = root;
        //进行遍历直到找到待删除节点或遍历结束没有待删除节点
        while (temp != null) {
            if (temp.value == value) {
                break;
            } else if (temp.value < value) {
                pre = temp;
                temp = temp.rightNode;
            } else {
                pre = temp;
                temp = temp.leftNode;
            }
        }
        if (temp == null) { //没有待删除节点返回null
            return null;
        } else if (temp.leftNode == null || temp.rightNode == null) { //待删除结点有只有一个或无子节点
            if (temp.rightNode == null) { //有左子节点或无子节点
                if (pre.value > temp.value) { //待删除结点是其父节点左节点
                    pre.leftNode = temp.leftNode;
                } else { //待删除结点是其父节点右节点
                    pre.rightNode = temp.leftNode;
                }
            } else { //由右子节点或无子节点
                if (pre.value > temp.value) {
                    pre.leftNode = temp.rightNode;
                } else {
                    pre.rightNode = temp.rightNode;
                }
            }
            return temp;
        } else { //待删除结点有两个子节点

            //找到待删除节点左子树中最大的节点
            Node p1 = temp.leftNode;
            Node p2 = p1;
            while (p1.rightNode != null) {
                p2 = p1;
                p1 = p1.rightNode;
            }

            //将最大值赋给待删除节点
            int result = temp.value;
            temp.value = p1.value;

            p2.rightNode = p1.leftNode;
            return new Node(result);
        }
    }

    /**
     * 递归方式删除节点
     */
    public Node deleteNodeRe(int value, Node root) {
        Node temp;
        if (root == null) //为空，说明递归到最底层，没有找到节点
            System.out.println("待删除结点没有找到");
        else if (value < root.value) //如果当前树的根节点的值大于待删除结点的值，返回结果为当前树从左子树删除待删除结点
            root.leftNode = deleteNodeRe(value, root.leftNode);
        else if (value > root.value) //如果当前树的根节点的值小于待删除结点的值，返回结果为当前树从右子树删除待删除结点
            root.rightNode = deleteNodeRe(value, root.rightNode);
        else { //如果当前树的根为待删除结点
            if (root.leftNode != null && root.rightNode != null) { //当前树有两个子树
                temp = findMinNode(root.rightNode); //找到右子树的最小值，赋值给根节点（即待删除结点）
                root.value = temp.value;
                //由子树删除最小值
                temp.rightNode = deleteNodeRe(root.value, root.rightNode);
            } else { //当前树只有一个或没有子树
                if (root.rightNode == null) //有左子树或没有子树
                    root = root.leftNode;
                else //有右子树
                    root = root.rightNode;
            }
        }

        return root;
    }

    /**
     * 先序遍历
     */
    public void preorderTraversal() {
        if (root == null) return;

        Stack<Node> stack = new Stack<>();

        Node temp = root;
        stack.push(temp);
        while (!stack.isEmpty()) {
            temp = stack.pop();
            if (temp.rightNode != null) stack.push(temp.rightNode);
            if (temp.leftNode != null) stack.push(temp.leftNode);
            if (!stack.isEmpty()) {
                System.out.print(temp.value + "->");
            } else {
                System.out.println(temp.value);
            }
        }
    }

    public void levelOrder() {

        if (root == null) return;

        Queue<Node> queue = new LinkedList<Node>();
        Node temp = root;
        queue.add(root);
        while (!queue.isEmpty()) {
            Node remove = queue.remove();
            System.out.print(remove.value);
            if (remove.leftNode != null) queue.add(remove.leftNode);
            if (remove.rightNode != null) queue.add(remove.rightNode);
        }
    }

    public static void main(String[] args) {

        int[] terrNodeArray = new int[]{30, 15, 41, -1, -1, 33, 50};
        BinarySortTree sortTree = new BinarySortTree(terrNodeArray);
        sortTree.preorderTraversal();

        Node node = sortTree.findNode(33);
        System.out.println("node.value = " + node.value);

        Node maxNode = sortTree.findMaxNode();
        System.out.println("maxNode = " + maxNode.value);
        sortTree.insertNode(20);
        sortTree.preorderTraversal();
        sortTree.insertNode(16);
        sortTree.preorderTraversal();
        Node minNode = sortTree.findMinNode();
        System.out.println("mixNode = " + minNode.value);

        Node deleteNode = sortTree.deleteNodeRe(15, sortTree.root);
        System.out.println("deleteNode = " + deleteNode);
        sortTree.preorderTraversal();


    }
}
