package maomao.leetcode.T605;

class Solution1 {
    private static boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n > flowerbed.length/2 + 1){
            return false;
        }
        if (n <= 0 || flowerbed.length == 1 && flowerbed[0] == 0 && n <= 1){
            return true;
        }
        if (flowerbed[0] == 0 && flowerbed[1] == 0) {
            flowerbed[0] = 1;
            n--;
        }
        if (flowerbed[flowerbed.length - 1] == 0 && flowerbed.length > 1 && flowerbed[flowerbed.length - 2] == 0) {
            flowerbed[flowerbed.length-1] = 1;
            n--;
        }
        for (int i = 1; i < flowerbed.length-1; i++) {
            if (flowerbed[i] == 1){
                i += 1;
            }else {
                if (flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                    flowerbed[i] = 1;
                    n--;
                }
            }
            if (n <= 0){
                return true;
            }
        }
        if (n <= 0){
            return true;
        }
        return false;
    }

    //test
    public static void main(String[] args) {
        int[] flowerbed = new int[]{1,0,0,0,1};
        System.out.println(canPlaceFlowers(flowerbed, 2));
    }
}
