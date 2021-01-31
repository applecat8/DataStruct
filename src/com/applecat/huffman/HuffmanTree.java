package com.applecat.huffman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * 哈夫曼树
 */




public class HuffmanTree {
    Node root;

    public HuffmanTree() {
    }


    public HuffmanTree(int[] values) {
        root = creatHfTree(values);
    }

    private static class Node implements Comparable<Node> { //实现Comparable接口来实现排序
        int val;
        Node left;
        Node right;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node o) {
            //升序排列
            return this.val - o.val;
        }

    }

    /**
     * 根据给定的数组来创建一个哈夫曼树，方法每次从数据中选择两个最小值的节点，创建一个新的节点，这最小两个节点作为该节点的孩子节点，该节点的值为这两个最小值的和，然后将新节点放入集合中循环执行，直到全部构成一棵树为止。
     *
     * @param values 给定的数组
     */
    private Node creatHfTree(int[] values) {
        if (values.length == 0) return null;
	//将每个元素作为一个节点放在集合中,形成节点集
        List<Node> nodes = new ArrayList<Node>();
        for (int value : values) {
            nodes.add(new Node(value));
        }

        Node node1 = null, node2 = null;

	    //循环构建，直到集合中只有一个元素为止
        while (nodes.size() > 1) {
            Collections.sort(nodes); //将集合升序排列
	    //取得最小，和次小节点
            node1 = nodes.remove(0);
            node2 = nodes.remove(0);

	    //向集合中添加新的节点，上面两个节点作为子节点，和为该节点的和
            nodes.add(new Node((node1.val + node2.val), node1, node2));
        }
        return nodes.remove(0);
    }

    public static void main(String[] args) {
        int[] values = new int[]{2,4,3,6,7,8,5,1};
        HuffmanTree huffmanTree = new HuffmanTree(values);
    }
}
