/*
 * Created by maomao (c)
 * 2021. 1. 4.
 * at Xi'an jiaotong university.
 */

package maomao.leetcode.T509;

public class Solution1 {
    public static int fib(int n) {
        if (n <= 1){
            return n;
        }
        int f0 = 0, f1 = 1;
        for (int i = 2; i <= n; i++) {
            int temp = f1;
            f1 = f0 + f1;
            f0 = temp;
        }
        return f1;
    }

    public static void main(String[] args) {
        assert fib(2) == 1;
        assert fib(3) == 2;
        assert fib(4) == 3;
        System.out.println("所有测试用例通过");
    }
}
