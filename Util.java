import java.util.Random;

public class Util {
    /**
     * 生成一个随机填充的int数组，随机数从0到10000
     * 
     * @param length 数组长度
     * @return 随机数组
     */
    public static int[] generateRandomArray(int length) {
        int[] arr = new int[length];

        for (int i = 0; i < length; i++) {
            arr[i] = new Random().nextInt(10000);
        }

        return arr;
    }
}
