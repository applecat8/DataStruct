package com.applecat;

/**
 * 最大堆
 */
public class MaxHeap {
    int[] dates;
    int size;
    int capacity;

    public MaxHeap(int capacity) {
        this.dates = new int[capacity + 1];
        this.capacity = capacity;
        size = 0;
    }

    /*
     * 判断堆是否满
     */
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * 判断堆是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     * 打印
     */
    public void print() {
        if (isEmpty()) System.out.println("堆为空");
        for (int i = 1; i <= size; ++i) {
            System.out.print(dates[i] + "-");
        }
    }

    /**
     * 插入数据
     *
     * @param value 待插入数据
     */
    public void insert(int value) {
        //判断是否为满
        if (isFull()) {
            System.out.println("堆已满");
            return;
        }

        //找到待插入位置，并与父节点进行比较，如果比父节点大进行向上调整
        int index = ++size;
        while (index != 1 && dates[index / 2] < value) {
            dates[index] = dates[index / 2];
            index /= 2;
        }
        //找到合适位置
        dates[index] = value;
    }

    /**
     * 删除堆中最大元素
     */
    public void deleteMax() {
        if (isEmpty()) { //判断堆是否为空
            System.out.println("堆为空");
            return;
        }
        //删除元素的方法是将堆中最后一个元素覆盖堆顶元素，然后进行向下调整
        int temp = dates[size--];
        int parent = 0, child = 0;
        for (parent = 1; parent * 2 <= size; parent = child) {
            child = parent * 2; //左孩子
            if (child < size && dates[child] < dates[child + 1]) //判断是否有右孩子，如果有，进行比较取最大孩子
                ++child;
            if (temp > dates[child]) { //如果当前节点比两个孩子中最大的还大，则可以退出
                break;
            }
            dates[parent] = dates[child]; //否者将最大子节点向上移
        }
        dates[parent] = temp;
        System.out.println("删除成功");
    }


    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(20);
        maxHeap.insert(5);
        maxHeap.insert(4);

        maxHeap.print();
        maxHeap.deleteMax();
        maxHeap.print();
    }
}
