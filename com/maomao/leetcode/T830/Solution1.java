/*
 * Created by maomao (c)
 * 2021. 1. 5.
 * at Xi'an jiaotong university.
 */

package maomao.leetcode.T830;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution1 {
    public List<List<Integer>> largeGroupPositions(String s) {
        char[] chars = s.toCharArray();
        List<List<Integer>> ans = new ArrayList<>();
        if (chars.length <= 2){
            return ans;
        }
        int curChar = chars[0];
        int curLen = 1;
        int curIndex = 1;
        int curStartIndex = 0;
        int curEndIndex = -1;
        while (curIndex < chars.length){
            if (chars[curIndex] == curChar){
                curLen++;
                curEndIndex = curIndex;
            }else {
                if (curLen > 2){
                    ans.add(new ArrayList<Integer>(Arrays.asList(curStartIndex, curEndIndex)));
                }
                curLen = 1;
                curStartIndex = curIndex;
                curChar = chars[curIndex];
            }
            curIndex++;
        }
        if (curLen > 2){
            curEndIndex = chars.length-1;
            ans.add(new ArrayList<Integer>(Arrays.asList(curStartIndex, curEndIndex)));
        }
        return ans;
    }
}
