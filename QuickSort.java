import java.util.Arrays;
import java.util.Random;

class QuickSort {
    /*
     * 快速排序
     * 
     * 核心思想：以分区的方式，针对每个分区进行排序，直到分区大小小于（包含）1个元素。
     * 排序方式：在数组中确定一个原点（pivot），遍历整个分区，将小于原点的元素放到原点左侧，将大于原点的元素放到右侧。
     * 原点确定方式：可以是第一个元素，可以是最后一个元素，可以是数组中间（middle）的一个元素，可以是数组中任何一个元素。
     * 快速排序可能出现最差性能的情况：
     * 1. 数组已经被正向排序
     * 2. 数组已经被逆向排序
     * 3. 所有元素都是一样的
     */

    public static void main(String[] args) {
        int[] randomArray = Util.generateRandomArray(10);
        // int[] randomArray = { 4, 3, 5, 7, 5, 1, 9, 2, 0, 4, 6 };
        System.out.println("未排序的数组： " + Arrays.toString(randomArray));

        QuickSort self = new QuickSort();
        int[] unsorted;

        unsorted = randomArray.clone();
        self.quickSortLomuto(unsorted, 0, unsorted.length - 1);
        System.out.println("已排序的数组【Lomuto】： " + Arrays.toString(unsorted));

        unsorted = randomArray.clone();
        self.quickSortHoare(unsorted, 0, unsorted.length - 1);
        System.out.println("已排序的数组【Hoare】： " + Arrays.toString(unsorted));

        unsorted = randomArray.clone();
        self.quickSortRandom(unsorted, 0, unsorted.length - 1);
        System.out.println("已排序的数组【Random】： " + Arrays.toString(unsorted));

        unsorted = randomArray.clone();
        self.quickSortThreeWay(unsorted, 0, unsorted.length - 1);
        System.out.println("已排序的数组【Three Way】： " + Arrays.toString(unsorted));

        unsorted = randomArray.clone();
        self.quickSortThreeWayBentleyMcIlroy(unsorted, 0, unsorted.length - 1);
        System.out.println("已排序的数组【Three Way - Bentley McIlroy】： " + Arrays.toString(unsorted));
    }

    /**
     * Lomuto方式快排
     * 
     * @param unsortedArray 未排序数组
     * @param start         数组开始下标
     * @param end           数组结束下标
     */
    private void quickSortLomuto(int[] unsortedArray, int start, int end) {
        // 1. 以数组最后一个元素作为pivot
        // 2. 从左向右遍历数组
        // 3. 如果遇到小于当前pivot的元素，则将其放到开头，并记录下一个较小数所存放的下标
        // 4. 遍历完整个数组后，将pivot移动到当前的较小数下标后一个位置，此时可以确认小于pivot的数都在左边
        // 5. 根据pivot的位置，将其分成两部分（分区），第一部分为pivot以左的所有小于pivot的数据，\
        // 第二部分为以pivot以右的所有大于pivot的数据
        // 6. 将两个分区递归排序，直到传入递归的数组元素只有一个或零个（空数组）

        // 如果数组范围小于1，则视为数组已经没有元素了
        if (start >= end) {
            return;
        }

        int pivot = unsortedArray[end];
        int smallerEI = start;

        for (int i = start; i < end; i++) {
            if (unsortedArray[i] < pivot) {
                int temp = unsortedArray[smallerEI];
                unsortedArray[smallerEI] = unsortedArray[i];
                unsortedArray[i] = temp;

                smallerEI++;
            }
        }

        int temp = unsortedArray[smallerEI];
        unsortedArray[smallerEI] = pivot;
        unsortedArray[end] = temp;

        this.quickSortLomuto(unsortedArray, start, smallerEI - 1);
        this.quickSortLomuto(unsortedArray, smallerEI + 1, end);
    }

    /**
     * Hoare方式快排
     * 
     * @param unsortedArray 未排序数组
     * @param start         数组开始下标
     * @param end           数组结束下标
     */
    private void quickSortHoare(int[] unsortedArray, int start, int end) {
        // 1. 以数组第一个元素作为pivot，初始化数组开始与结束的下标位置
        // 2. 开始移动两个下标，直到两个下标相遇（开始下标大于结束下标）
        // 3. 首先移动开始下标，一直向右移动直到找到比pivot大或与pivot相同的元素
        // 4. 接着移动结束下标，一直向左移动直到找到比pivot小或与pivot相同的元素
        // 5. 将两元素互换位置
        // 6. 继续第4-5步，直到两个下标相遇
        // 7. 现在数组左侧都是比pivot小的数，右侧都是比pivot大的数，所以可以根据两个下标，确定pivot的位置
        // 8. 根据pivot的位置，将数组分区为两个部分，第一部分为pivot以左的所有小于pivot的数据，\
        // 第二部分为以pivot以右的所有大于pivot的数据
        // 9. 将两个分区递归排序，直到传入递归的数组元素只有一个或零个（空数组）

        // 如果数组范围小于1，则视为数组已经没有元素了
        if (start >= end) {
            return;
        }

        int pivot = unsortedArray[start];
        int left = start - 1;
        int right = end + 1;

        while (true) {
            // 关键：及时数组中有相同的元素，每次也必定会向前（向后）移动下标，避免进入死循环
            do {
                left++;
            } while (unsortedArray[left] < pivot);

            do {
                right--;
            } while (unsortedArray[right] > pivot);

            if (left >= right) {
                break;
            }

            // 两头都是pivot就没必要再交换了
            if (pivot == unsortedArray[left] && pivot == unsortedArray[right]) {
                continue;
            }

            int temp = unsortedArray[left];
            unsortedArray[left] = unsortedArray[right];
            unsortedArray[right] = temp;
        }

        quickSortHoare(unsortedArray, start, right);
        quickSortHoare(unsortedArray, right + 1, end);
    }

    /**
     * 随机pivot快排
     * 很简单，随机取一个在数组中的元素，并将其放到开始或结束位置，其他流程与普通Lomuto或Hoare快排一样
     * 
     * @param unsortedArray 未排序数组
     * @param start         数组开始下标
     * @param end           数组结束下标
     */
    private void quickSortRandom(int[] unsortedArray, int start, int end) {
        // 随机确定一个pivot，并将其放到数组开始或结束位置
        // 这里放在结束位置，因为这里使用Lomuto方式实现（偷懒😁）

        Random rng = new Random();
        int randomIndex = rng.nextInt(end + 1);
        int randomPivot = unsortedArray[randomIndex];
        unsortedArray[randomIndex] = unsortedArray[end];
        unsortedArray[end] = randomPivot;

        this.quickSortLomuto(unsortedArray, start, end);
    }

    /**
     * 荷兰国旗版三路快排（适合大量重复数据）
     * 
     * @param unsortedArray 未排序数组
     * @param start         数组开始下标
     * @param end           数组结束下标
     */
    private void quickSortThreeWay(int[] unsortedArray, int start, int end) {
        // 1. 将数组划分成三个区域，小于pivot的、等于pivot的、大于pivot的
        // 2. 小于pivot的放在数组开始，等于pivot的放在数组中间，大于pivot的放在数组最末
        // 3. 首先以数组的第一位元素作为pivot，然后从左向右开始遍历数组
        // 4. 针对最小区和最大区，划分并维护两个下标
        // 5. 如果当前元素小于pivot，则将当前元素放到最小区中，并且最小区下标增加，当前所在序列增加
        // 6. 如果当前元素大于pivot，则将当前元素放到最大区中，并且最大区下标减小
        // 7. 如果当前元素等于pivot，则当前所在序列增加
        // 8. 当数组全部遍历完成后，将最小区和最大区分别再递归排序

        // 如果数组范围小于1，则视为数组已经没有元素了
        if (start >= end) {
            return;
        }

        int pivot = unsortedArray[start];
        int smallerIndex = start;
        int biggerIndex = end;
        int i = start;

        // 关键：这里的i作为pivot同类区右边界下标存在，不能让pivot同类区超过最大区，否则会导致递归排序时下标越界
        while (i <= biggerIndex) {
            int item = unsortedArray[i];
            if (item < pivot) {
                unsortedArray[i] = unsortedArray[smallerIndex];
                unsortedArray[smallerIndex] = item;
                smallerIndex++;
                i++;
            } else if (item > pivot) {
                unsortedArray[i] = unsortedArray[biggerIndex];
                unsortedArray[biggerIndex] = item;
                biggerIndex--;
            } else {
                i++;
            }
        }

        this.quickSortThreeWay(unsortedArray, start, smallerIndex - 1);
        this.quickSortThreeWay(unsortedArray, biggerIndex + 1, end);
    }

    /**
     * Bentley-McIlroy版三路快排
     * 娘嘞，这个方式够难写的😵‍💫😵‍💫😵‍💫😵‍💫
     * 
     * @param unsortedArray 未排序数组
     * @param start         数组开始下标
     * @param end           数组结束下标
     */
    private void quickSortThreeWayBentleyMcIlroy(int[] unsortedArray, int start, int end) {
        // 1. 将数组划分成三个区域，小于pivot的、等于pivot的、大于pivot的
        // 2. 小于pivot的放在数组开始，大于pivot的放在数组最末，等于pivot的放在数组两边，最后会合并到数组中间
        // 3. 首先以数组的第一位元素作为pivot，然后用类似Hoare方式开始从两端向内遍历
        // 4. 首先从左向右遍历，寻找当前元素比pivot大的
        // 5. 然后从右向左，寻找当前元素比pivot小的
        // 6. 将两边的元素对调，达到小的在左边，大的在右边
        // 7. 如果找到了和pivot一样的元素，且左边和右边并不是指向的同一个元素，则是哪边找到的放到哪边
        // 8. 如果找到了和pivot一样的元素，并且两边指向的是同一个元素，则将其放到左侧
        // 9. 当两边的下标相遇并经过后，将两边相同的元素放到数组正中间
        // 10. 最后将剩下的最小区和最大去分别再递归排序

        // 如果数组范围小于1，则视为数组已经没有元素了
        if (start >= end) {
            return;
        }

        int pivot = unsortedArray[start];
        int left = start;
        int right = end + 1;
        int leftPivot = start;
        int rightPivot = end + 1;

        while (true) {
            do {
                left++;
                if (left > end) {
                    break;
                }
            } while (unsortedArray[left] < pivot);

            do {
                right--;
                if (right < start) {
                    break;
                }
            } while (unsortedArray[right] > pivot);

            // 两边下标已经相交，数组已经遍历完成
            if (left >= right) {
                break;
            }

            // 先将两边的数进行对换
            int temp = unsortedArray[left];
            unsortedArray[left] = unsortedArray[right];
            unsortedArray[right] = temp;

            if (unsortedArray[left] == pivot) {
                // 左边发现pivot相同元素，放到左边
                leftPivot++;
                temp = unsortedArray[leftPivot];
                unsortedArray[leftPivot] = pivot;
                unsortedArray[left] = temp;
            }

            if (unsortedArray[right] == pivot) {
                // 右边发现pivot相同元素，放到右边
                rightPivot--;
                temp = unsortedArray[rightPivot];
                unsortedArray[rightPivot] = pivot;
                unsortedArray[right] = temp;

            }
        }

        // 将两边pivot相同元素放到中间
        // 取中间值
        left = right + 1;
        // 先将左边相同的放到中间
        for (int i = start; i <= leftPivot; i++) {
            int temp = unsortedArray[i];
            unsortedArray[i] = unsortedArray[right];
            unsortedArray[right] = temp;
            right--;
        }
        // 再将右边相同的放到中间
        for (int i = end; i >= rightPivot; i--) {
            int temp = unsortedArray[i];
            unsortedArray[i] = unsortedArray[left];
            unsortedArray[left] = temp;
            left++;
        }

        this.quickSortThreeWayBentleyMcIlroy(unsortedArray, start, right);
        this.quickSortThreeWayBentleyMcIlroy(unsortedArray, left, end);
    }

}