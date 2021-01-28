package com.applecat;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinTree {

    private final Node tree;

    private static class Node{
        char value;
        Node leftNode;
        Node rightNode;
    }

    public BinTree(char[] values){
        tree = linkNode(values, 1);
    }

    public Node linkNode(char[] values,int start){
        if (start - 1 >= values.length || values[start - 1] == '0') return null;

        Node node = new Node();
        node.value = values[start - 1];
        node.leftNode = linkNode(values,start * 2);
        node.rightNode = linkNode(values,start * 2 + 1);

        return node;
    }
    //非递归方式实现先序遍历
    public void preorderTraversal(){
        if (tree == null) return;

        Stack<Node> stack = new Stack<>();

        Node temp = tree;
        stack.push(temp);
        while (!stack.isEmpty()){
            temp = stack.pop();
            System.out.print(temp.value);
            if (temp.rightNode != null) stack.push(temp.rightNode);
            if (temp.leftNode != null) stack.push(temp.leftNode);
        }
    }


    //非递归形式中序遍历
    public void inorderTraversal(){
        if (tree == null) return;

        Stack<Node> stack = new Stack<>();
        Node temp = tree;

        while (temp != null || !stack.isEmpty()){
            while (temp != null){
                stack.push(temp);
                temp = temp.leftNode;
            }

            if (!stack.isEmpty()) {
                temp = stack.pop();
                System.out.print(temp.value);
                temp = temp.rightNode;
            }
        }
    }

    //非递归后序遍历
    public void postorderTraversal(){
        if (tree == null) return;

        Stack<Node> stack = new Stack<>();
        Stack<Node> stack1 = new Stack<>();
        Node temp;

        stack.push(tree);
        while (!stack.empty()){
            temp = stack.pop();
            stack1.push(temp);

            if (temp.leftNode != null) stack.push(temp.leftNode);
            if (temp.rightNode != null) stack.push(temp.rightNode);

        }

        while (!stack1.empty()){
            System.out.print(stack1.pop().value);
        }
    }

    //层序遍历
    public void levelOrder(){

        if (tree == null) return;

        Queue<Node> queue = new LinkedList<Node>();
        Node temp = tree;
        queue.add(tree);
        while (!queue.isEmpty()){
            Node remove = queue.remove();
            System.out.print(remove.value);
            if (remove.leftNode != null) queue.add(remove.leftNode);
            if (remove.rightNode != null) queue.add(remove.rightNode);
        }
    }

    public static void main(String[] args) {
        char[] values = {'A', 'B', 'C', 'D', 'E', 'F', 'G', '0', '0', 'H', '0', '0', 'I'};
        BinTree binTree = new BinTree(values);
        binTree.inorderTraversal();
        System.out.println();
        binTree.preorderTraversal();
        System.out.println();
        binTree.postorderTraversal();
        System.out.println();
        binTree.levelOrder();

    }
}
