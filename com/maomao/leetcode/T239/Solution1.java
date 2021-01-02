/*
 * Created by maomao (c)
 * 2021. 1. 2.
 * at Xi'an jiaotong university.
 */

package maomao.leetcode.T239;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 优先队列的法子
 */
public class Solution1 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n < k){
            return new int[]{};
        }
        int[] ans = new int[n-k+1];
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] != o2[0] ? o2[0] - o1[0] : o2[1] - o2[1];
            }
        });
        for (int i = 0; i < k; i++) {
            priorityQueue.offer(new int[]{nums[i], i});
        }
        ans[0] = priorityQueue.peek()[0];
        for (int i = k; i < n; i++) {
            priorityQueue.offer(new int[]{nums[i], i});
            while (priorityQueue.peek()[0] <= i-k){
                priorityQueue.poll();
            }
            ans[i-k+1] = priorityQueue.peek()[0];
        }
        return ans;
    }
}
