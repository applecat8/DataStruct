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

    //存储哈夫曼编码标
    Map<Byte, String> coding = new HashMap<>();

    /**
     * 通过字符串来给定构建编码标
     */
    public HuffmanCode(String s) {
        //构建哈夫曼树
        Node node = creatCodeTree(s.getBytes());
        //生成哈夫曼编码表
        HFCoding(node);
    }

    /*
     * 通过比特数组来构建编码表
     */
    public HuffmanCode(byte[] bytes) {
        Node node = creatCodeTree(bytes);
        HFCoding(node);
    }

    /**
     * 树节点，实现Comparable接口来用于排序，以便用于构建哈夫曼树
     */
    private static class Node implements Comparable<Node> {

        int weight;
        Byte data;
        Node left;
        Node right;

        public Node() {
        }

        public Node(int weight, Byte data) {
            this.weight = weight;
            this.data = data;
        }

        public Node(int weight, Byte data, Node left, Node right) {
            this.weight = weight;
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public int compareTo(Node o) {
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


    /**
     * 通过字节数组来创建哈夫曼树，每个节点的值是每种字节，节点的权值是该字节出现的次数
     */
    public Node creatCodeTree(byte[] dates) {

        Map<Byte, Integer> counts = new HashMap<>();
        //遍历，将每种字节和该字节出现的次数（即权值）组成一个键值对
        for (byte b : dates) {
            Integer count = counts.get(b);
            if (count == null) { //第一次出现
                counts.put(b, 1);
            } else {
                counts.put(b, ++count);
            }
        }
        //将每一个键值对构建成一个节点，放在nodes中
        Set<Byte> bytes = counts.keySet();
        List<Node> nodes = new ArrayList<>();
        for (Byte b : bytes) {
            nodes.add(new Node(counts.get(b), b));
        }

        //形成哈夫曼树：见HuffmanTree -> private Node creatHfTree(int[] values);
        Node node1 = null, node2 = null;

        while (nodes.size() > 1) {
            Collections.sort(nodes);
            node1 = nodes.remove(0);
            node2 = nodes.remove(0);
            nodes.add(new Node((node1.weight + node2.weight), null, node1, node2));
        }

        return nodes.remove(0);
    }

    public void levelOrder(Node root) {

        if (root == null) return;

        Queue<Node> queue = new LinkedList<Node>();

        queue.add(root);
        while (!queue.isEmpty()) {
            Node remove = queue.remove();

            System.out.println(remove);

            if (remove.left != null) queue.add(remove.left);
            if (remove.right != null) queue.add(remove.right);
        }
    }

    /*
     * 根据哈夫曼树形成对应的哈夫曼编码
     */
    public void HFCoding(Node root) {
        HFCoding(root, "", new StringBuilder());
    }

    /*
     * 根据哈夫曼树形成对应的哈夫曼编码表
     * 递归方式，向右编码 + ‘0’
     * 		 向左编码 + '1'
     */
    public void HFCoding(Node root, String s, StringBuilder builder) {
        if (root == null) return;
        //不能使用传进来的的stringBuilder，因为在其他递归分支中也需要用，用传进来的stringBuilder构建一个新的
        StringBuilder builder1 = new StringBuilder(builder);
        builder1.append(s);
        if (root.data == null) {
            HFCoding(root.right, "0", builder1);//向右+‘0’
            HFCoding(root.left, "1", builder1);//向左+‘1’
        } else {
            //到叶子节点，将对应的数据放入编码表中
            coding.put(root.data, builder1.toString());
        }
    }

    /**
     * 将对应数据的字节数组通过对应的哈夫曼编码表进行压缩
     * 返回压缩后的字节数组
     */
    public byte[] zip(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        //通过哈夫曼编码表将每个直接映射为哈夫曼编码
        //存入一个字符串中
        for (byte b : bytes) {
            sb.append(coding.get(b));
        }

        //System.out.println("sb = " + sb.toString());
        int length = sb.length();
        int len = (length + 7) / 8;
        //创建用于存放压缩后数据的字节数组
        //由于哈夫曼编码并非8的整数倍，多1个空位放置最后一个字节的位数
        byte[] hfByte = new byte[len + 1];
        byte endLen = 8;

        //每次读取8位放入结果数组中
        for (int i = 0, index = 0; i < length; i += 8, index++) {
            String strByte;
            if (i + 8 > length) { //最后一位
                strByte = sb.substring(i);//直接取到最后
                endLen = (byte) strByte.length();//记录最后一位的位数
            } else {
                strByte = sb.substring(i, i + 8);//非最后一位，取8位
            }
            hfByte[index] = (byte) Integer.parseInt(strByte, 2);
        }

        hfByte[len] = endLen;//将最后一位的长度存入

        return hfByte;
    }

    /**
     * 通过哈夫曼编码表，将对应的压缩编码还原
     */
    public byte[] unzip(byte[] hfBytes) {
        StringBuilder builder = new StringBuilder();
        //将字节数组变为二进制的字符串
        for (int i = 0; i < hfBytes.length - 1; ++i) {
            //判断是不是最后一个
            if (i == hfBytes.length - 2) {
                builder.append(byteToBit(hfBytes[i], hfBytes[i + 1]));
            } else {
                builder.append(byteToBit(hfBytes[i], 8));
            }
        }

        List<Byte> resultList = new ArrayList<>();

        //将编码表反转,得到解码表
        Map<String, Byte> encoding = new HashMap<>();
        for (Map.Entry<Byte, String> entry : coding.entrySet()) {
            encoding.put(entry.getValue(), entry.getKey());
        }
        StringBuilder temp = new StringBuilder();
        //通过逐个读取二进制字符串，每轮循环添加一位，当从解码表中找到对应的数据后进行记录，并清空stringBuilder，循环解码得到原来数据的字节数组
        for (int i = 0; i < builder.length(); i++) {
            temp.append(builder.charAt(i));
            Byte s = encoding.get(temp.toString());
            if (s != null) {
                resultList.add(s);
                temp.delete(0, temp.length());
            }
        }

        byte[] result = new byte[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    /**
     * 将一个byte数据的二进制形式返回
     *
     * @param cut 用于处理最后一位，非最后一位截取8位，最后一位根据压缩后字节数组的最后一位决定
     */
    private String byteToBit(byte b, int cut) {

        int temp = b;
        temp |= 256;//按位与将第九位变为一，方便后面截取
        String s = Integer.toBinaryString(temp);
        return s.substring(s.length() - cut);
    }

    public static void main(String[] args) {
        String txt = "gughjgh杠杆原理比较好v";

        HuffmanCode huffmanCode = new HuffmanCode(txt);

        byte[] bytes = txt.getBytes();
        System.out.println("bytes = " + Arrays.toString(bytes));
        System.out.println("bytes = " + new String(bytes));
        byte[] zip = huffmanCode.zip(bytes);
        System.out.println("zip = " + Arrays.toString(zip));
        byte[] unzip = huffmanCode.unzip(zip);
        System.out.println("unzip = " + new String(unzip));

    }
}


