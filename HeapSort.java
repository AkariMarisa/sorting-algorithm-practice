import java.util.Arrays;

class HeapSort {
    /*
     * 堆排序
     * 
     * 核心思想：使用堆的数据格式，避免了像合并排序对空间占用高的问题
     * 排序方式：将数组转化为堆，并使用最大堆或最小堆的方式对数组进行排序
     * 可能出现最差性能的情况：
     * 没有，和合并排序一样，每次排序的时间复杂度都是一样的
     */

    public static void main(String[] args) {
        int[] randomArray = Util.generateRandomArray(10);
        System.out.println("未排序的数组： " + Arrays.toString(randomArray));

        HeapSort self = new HeapSort();
        int[] unsorted;

        unsorted = randomArray.clone();
        self.heapSort(unsorted);
        System.out.println("已排序数组：" + Arrays.toString(unsorted));
    }

    /**
     * 普通堆排序
     * 
     * @param unsortedArray
     */
    private void heapSort(int[] unsortedArray) {
        // 将数组排序成最大堆
        // 根据最大堆的性质，将其反向放入数组中，实现数组排序

        // 确定数组中间位置，因为堆的特性，数组的中间元素为最后一级父级元素，再往后的都是叶子元素（没有子元素的元素）
        // 确定父级元素的子元素，判断子元素是否大于父元素
        // 如果有一个子元素比父元素大，那么将子元素和父元素位置互换，并重新在判断一次

        // 将数组所有父元素都排序一遍，最后的数组就是最大堆了
        // 将数组头尾交换，就变成了排序后的数组了

        int lastParentIndex = unsortedArray.length / 2 - 1;

        // 最大堆化数组
        int length = unsortedArray.length;
        this.heapify(unsortedArray, length, lastParentIndex);

        for (int i = unsortedArray.length - 1; i >= 0; i--) {
            // 每次将第一位与最后一位元素交换，这样最大的就在最后了
            int temp = unsortedArray[0];
            unsortedArray[0] = unsortedArray[i];
            unsortedArray[i] = temp;

            // 对剩下比最大数小的元素进行最大堆化，这样就会把第二大的数放到开头
            this.heapify(unsortedArray, i, 0);
        }

    }

    private void heapify(int[] unsortedArray, int length, int lastParentIndex) {

        for (int i = lastParentIndex; i >= 0; i--) {
            int largerestIndex = i; // 默认认为父元素是最大的
            int firstChildIndex = i * 2 + 1;
            int lastChildIndex = i * 2 + 2;

            if (firstChildIndex < length && unsortedArray[firstChildIndex] > unsortedArray[largerestIndex]) {
                largerestIndex = firstChildIndex;
            }

            if (lastChildIndex < length && unsortedArray[lastChildIndex] > unsortedArray[largerestIndex]) {
                largerestIndex = lastChildIndex;
            }

            if (largerestIndex != i) {
                int temp = unsortedArray[i];
                unsortedArray[i] = unsortedArray[largerestIndex];
                unsortedArray[largerestIndex] = temp;

                this.heapify(unsortedArray, length, largerestIndex);
            }
        }
    }
}