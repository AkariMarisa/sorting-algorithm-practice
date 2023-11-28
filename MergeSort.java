import java.util.Arrays;

class MergeSort {
    /*
     * 合并排序
     * 
     * 核心思想：分化、排序、合并
     * 排序方式：将一个数组递归对半切分，直到每个子数组中只有一个元素为止。然后针对每个子数组进行排序，并最终合并回原数组中。
     * 合并排序可能出现最差性能的情况：
     * 没有，因为合并排序每一次的时间都是一样的，但是可能也出现所有元素全部需要排序一遍的情况。
     */

    public static void main(String[] args) {
        int[] randomArray = Util.generateRandomArray(10);
        System.out.println("未排序的数组： " + Arrays.toString(randomArray));

        MergeSort self = new MergeSort();
        int[] unsorted;

        unsorted = randomArray.clone();
        self.mergeSortNormal(unsorted, 0, unsorted.length - 1);
        System.out.println("已排序数组【Normal】：" + Arrays.toString(unsorted));

        unsorted = randomArray.clone();
        self.mergeSortInPlace(unsorted, 0, unsorted.length - 1);
        System.out.println("已排序数组【In place】：" + Arrays.toString(unsorted));

    }

    /**
     * 普通对半分化
     * 
     * @param unsortedArray 未排序数组
     * @param start         数组开始下标
     * @param end           数组结束下标
     */
    private void mergeSortNormal(int[] unsortedArray, int start, int end) {
        // 1. 获取中心点
        // 2. 根据中心点分割成两个数组
        // 3. 递归分割，直到子数组中只有一个元素
        // 4. 对子数组进行排序
        // 5. 将排序后的子数组合并到原来的数组中

        // 如果数组范围小于1，则视为数组已经没有元素了
        if (start >= end) {
            return;
        }

        int mid = (end - start) / 2 + start;

        mergeSortNormal(unsortedArray, start, mid);
        mergeSortNormal(unsortedArray, mid + 1, end);

        // 针对子数组进行排序
        // 创建两个子数组
        int[] leftArray = Arrays.copyOfRange(unsortedArray, start, mid + 1);
        int[] rightArray = Arrays.copyOfRange(unsortedArray, mid + 1, end + 1);

        int leftIndex = 0; // 左边子数组元素遍历下标
        int rightIndex = 0; // 右边子数组元素遍历下标
        int subIndex = start; // 访问原数组的控制下标

        int leftSize = mid - start + 1;
        int rightSize = end - mid;

        while (leftIndex < leftSize && rightIndex < rightSize) {
            // 排序的概念就是，同时遍历左右数组，从小到大合并回原先的数组中，然后从哪边数组拿到的元素，就更新对应数组的遍历下标
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                unsortedArray[subIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                unsortedArray[subIndex] = rightArray[rightIndex];
                rightIndex++;
            }

            subIndex++;
        }

        // 大部分时候，完成上面的排序后，左右数组总有一个没有遍历完的，这时候需要将其合并到原先数组中
        while (leftIndex < leftSize) {
            unsortedArray[subIndex] = leftArray[leftIndex];
            leftIndex++;
            subIndex++;
        }

        while (rightIndex < rightSize) {
            unsortedArray[subIndex] = rightArray[rightIndex];
            rightIndex++;
            subIndex++;
        }
    }

    /**
     * 就地合并
     * 
     * @param unsortedArray 未排序数组
     * @param start         数组开始下标
     * @param end           数组结束下标
     */
    private void mergeSortInPlace(int[] unsortedArray, int start, int end) {
        // 1. 获取中心点
        // 2. 根据中心点分割成两个数组
        // 3. 递归分割，直到子数组中只有一个元素
        // 4. 对子数组进行排序

        // 如果数组范围小于1，则视为数组已经没有元素了
        if (start >= end) {
            return;
        }

        int mid = (end - start) / 2 + start;

        mergeSortNormal(unsortedArray, start, mid);
        mergeSortNormal(unsortedArray, mid + 1, end);

        // 针对子数组进行排序

        int leftIndex = start; // 左边子数组元素遍历下标
        int rightIndex = mid + 1; // 右边子数组元素遍历下标

        while (leftIndex <= mid && rightIndex <= end) {
            // 就地排序，即直接操作原数组，左边是小的元素就不要动，右边有小的就移动过来
            // 相当于左侧数组起始是1个元素长度，通过不断移动右侧数组中小的值达到缩小右侧需要探索的区域
            if (unsortedArray[leftIndex] <= unsortedArray[rightIndex]) {
                leftIndex++;
            } else {
                int temp = unsortedArray[rightIndex];
                int index = rightIndex;

                // 将右侧小的数一直换到左边
                // eg. leftIndex = 0, mid = 5, rightIndex = 6,
                // { 4, 2, 5, 1, 8, 0, 3, 6, 7, 9 } => { 3, 4, 2, 5, 1, 8, 0, 6, 7, 9 }
                while (index != leftIndex) {
                    unsortedArray[index] = unsortedArray[index - 1];
                    index--;
                }
                unsortedArray[leftIndex] = temp;

                // 因为右侧数组向左侧移动过来了一个元素，相当于右侧数组变小了，所以需要更新三个下标
                leftIndex++;
                mid++;
                rightIndex++;
            }
        }
    }
}