package com.applecat.huffman;

import com.applecat.BinTree;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;

import javax.swing.filechooser.FileSystemView;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * 利用哈夫曼编码来完成对数据的压缩
 */
public class HuffmanCode {

    Map<Byte,String> coding = new HashMap<>();

    public HuffmanCode(String s){
        Node node =creatCodeTree(s.getBytes());
        HFCoding(node);
    }

    private static class Node implements Comparable<Node> {

        int weight;
        Byte data;
        Node left;
        Node right;

        public Node(){}

        public Node(int weight,Byte data){
            this.weight = weight;
            this.data = data;
        }

        public Node(int weight, Byte data, Node left, Node right) {
            this.weight = weight;
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int compareTo(Node o){
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "weight=" + weight +
                    ", data=" + data +
                    '}';
        }
    }


    public Node creatCodeTree(byte[] dates) {

        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : dates) {
            Integer count = counts.get(b);
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, ++count);
            }
        }
        Set<Byte> bytes = counts.keySet();
        List<Node> nodes = new ArrayList<>();
        for (Byte b : bytes) {
            nodes.add(new Node(counts.get(b), b));
        }

        Node node1 = null, node2 = null;

        while (nodes.size() > 1){
            Collections.sort(nodes);
            node1 = nodes.remove(0);
            node2 = nodes.remove(0);
            nodes.add(new Node((node1.weight + node2.weight),null,node1,node2));
        }

        return nodes.remove(0);
    }

    public void levelOrder(Node root){

        if (root == null) return;

        Queue<Node> queue = new LinkedList<Node>();

        queue.add(root);
        while (!queue.isEmpty()){
            Node remove = queue.remove();

            System.out.println(remove);

            if (remove.left != null) queue.add(remove.left);
            if (remove.right!= null) queue.add(remove.right);
        }
    }

    public void HFCoding(Node root){
        HFCoding(root,"",new StringBuilder());
    }
    public void HFCoding(Node root,String s,StringBuilder builder){
        if (root == null) return;
        StringBuilder builder1 = new StringBuilder(builder);
        builder1.append(s);
        if (root.data == null){
            HFCoding(root.right,"0",builder1);
            HFCoding(root.left,"1",builder1);
        }else {
            coding.put(root.data,builder1.toString());
        }
    }

    public byte[] zip(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes){
            sb.append(coding.get(b));
        }

        int len = (sb.length() + 7) / 8;
        byte[] hfByte = new byte[len];

        for (int i = 0,index = 0;i < sb.length() ; i += 8,index++){
            String strByte;
            if (i + 8 > sb.length()) {
                strByte = sb.substring(i);
            }else {
                strByte = sb.substring(i,i + 8);
            }
            hfByte[index] = (byte) Integer.parseInt(strByte,2);
        }

        return hfByte;
    }

    public String unzip(byte[] hfBytes){

        return null;
    }

    private String byteToBit(byte b,boolean flag){

        int temp = b;
        temp |= 256;
        String s = Integer.toBinaryString(b);
        return s.substring(s.length() - 8);
    }

    public static void main(String[] args) {
        String txt = "见风使舵和法国哈哈发货计划发单号发";

        HuffmanCode huffmanCode = new HuffmanCode(txt);

        byte[] bytes = txt.getBytes();
        System.out.println("bytes = " + Arrays.toString(bytes));
        System.out.println("bytes = " + new String(bytes));
        byte[] zip = huffmanCode.zip(bytes);
        System.out.println("zip = " + Arrays.toString(zip));

    }
}


