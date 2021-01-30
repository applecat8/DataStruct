package com.applecat;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;

public class Sort{
	/**
	 * 堆排序：将待排列数组看成一个完全二叉树，通过构建成堆，将最大值放在最后，然后然后忽略该值后，将剩余数据再调整为大顶堆，如此循环，进行排序
	 */
	public static void heapSort(int[] values){
		int len = values.length;
		//从完全二叉树最后一个非叶子节点开始，自地向上将二叉树构建成为一个大顶堆
		for (int i = values.length / 2- 1;i >= 0 ; --i){
			creatHeap(values,i,len);
		}
		//将最大值放在最后，调整，循环
		for (int i = len - 1;i > 0;--i){
			//最大数据后置
			int temp = values[i];
			values[i] = values[0];
			values[0] = temp;
			//将忽略后数据的二叉树调整
			creatHeap(values,0,i);
		}

	}
	/*
	 * 根据参数将给定数组的一部分构建成大顶堆
	 */
	private static void creatHeap(int[] val,int index,int len){
		int temp = val[index];
		int parent = 0, child = 0;
		//有孩子，则继续比较，否者退出
		for (parent = index ; parent * 2 + 1 < len; parent = child) {
			child = parent * 2 + 1; //左孩子
			if (child < len - 1 && val[child] < val[child + 1]) //判断是否有右孩子，如果有，进行比较取最大孩子
				++child;
			if (temp > val[child]) { //如果当前节点比两个孩子中最大的还大，则可以退出
				break;
			}
			val[parent] = val[child]; //否者将最大子节点向上移
		}
		val[parent] = temp;
	}

	public static void main(String[] args){
		int LENGTH = 8000000;
		int[] values = new int[LENGTH];
		for (int i = 0; i < values.length; i++) {
			values[i] = (int)(Math.random() * LENGTH);
		}
		Date date = new Date();
		System.out.println("date = " + date);
		heapSort(values);
		Date date1;date1 = new Date();
		System.out.println("date1 = " + date1);
		//System.out.println("values = " + Arrays.toString(values));
	}
}
