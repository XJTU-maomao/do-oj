/*
 * Created by maomao (c)
 * 2021. 1. 7.
 * at Xi'an jiaotong university.
 */

package maomao.leetcode.T547;

import java.util.HashSet;
import java.util.Set;

public class Solution1 {
    public int findCircleNum(int[][] isConnected) {
        int m = isConnected.length;
        UnionFind unionFind = new UnionFind(m);
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= i; j++) {
                if (isConnected[i][j] == 0){
                    continue;
                }
                if (unionFind.parent[i] != unionFind.parent[j]){
                    unionFind.union(i, j);
                }
            }
        }
        for (int i = 0; i < m; i++) {
            if (unionFind.find(i) == unionFind.parent[i] && !set.contains(unionFind.parent[i])) {
                set.add(i);
            }
        }
        return set.size();
    }

    /**
     * 并查集
     */
    private class UnionFind{

        private int[] parent;

        public UnionFind(int n){
            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public void union(int x, int y){
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY){
                return;
            }

            if (rootX > rootY ){
                parent[rootX] = rootY;
            }else {
                parent[rootY] = rootX;
            }
        }

        /**
         * 路径压缩
         * @param x  根结点的id
         * @return
         */
        public int find(int x){
            if (x != parent[x]){
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

    }

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        int[][] isConnected1 = new int[][]{{1,1,0},{1,1,0},{0,0,1}};
        System.out.println(solution1.findCircleNum(isConnected1));;
        int[][] isConnected2 = new int[][]{{1,0,0},{0,1,0},{0,0,1}};
        System.out.println(solution1.findCircleNum(isConnected2));;
        int[][] isConnected3 = new int[][]{{1,0,0,1},{0,1,1,0},{0,1,1,1},{1,0,1,1}};
        System.out.println(solution1.findCircleNum(isConnected3));;
    }
}
