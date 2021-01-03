/*
 * Created by maomao (c)
 * 2021. 1. 3.
 * at Xi'an jiaotong university.
 */

package maomao.leetcode.T86;

import maomao.leetcode.ListNode;

public class Solution1 {
    public ListNode partition(ListNode head, int x) {
        ListNode ansListHead = new ListNode(0);
        ListNode ansListTail = ansListHead;
        ListNode newHead = new ListNode(x-1);
        newHead.next = head;
        ListNode newTail = newHead;
        while (newTail.next != null){
            if (newTail.next.val >= x){
                ListNode temp = new ListNode(newTail.next.val);
                newTail.next = newTail.next.next;
                ansListTail.next = temp;
                ansListTail = temp;
            }else{
                newTail = newTail.next;
            }
        }
        newTail.next = ansListHead.next;
        return newHead.next;
    }
}
