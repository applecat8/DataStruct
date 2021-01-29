package com.applecat;

/**
 * 最大堆
 */
public class MaxHeap {
    int[] dates;
    int size;
    int capacity;

    public MaxHeap(int capacity){
        this.dates = new int[capacity + 1];
        this.capacity = capacity;
        size = 0;
    }

    public boolean isFull(){
        return size == capacity;
    }

    /**
     * 插入数据
     * @param value 待插入数据
     */
    public void insert(int value){
        //判断是否为空
        if (isFull()){
            System.out.println("堆已满");
            return;
        }

        //找到待插入位置，并与父节点进行比较，如果比父节点大进行向上调整
        int index = ++size;
        while(index != 1&& dates[index / 2] < value){
            dates[index] = dates[index / 2];
            index /= 2;
        }
        //找到合适位置
        dates[index] = value;
    }
}
