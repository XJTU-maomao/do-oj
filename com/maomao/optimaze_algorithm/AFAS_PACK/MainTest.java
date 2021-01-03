/*
 * Created by maomao (c)
 * 2021. 1. 2.
 * at Xi'an jiaotong university.
 */
package maomao.optimaze_algorithm.AFAS_PACK;

/**
 * 人工鱼群算法求解二元函数最优值问题, 链接如下：
 * https://mp.weixin.qq.com/s?__biz=MzU0NzgyMjgwNg==&mid=2247485998&idx=1&sn=c155e50e3ed7ad5660227f05fd6d6a6f&chksm=fb49c797cc3e4e81e7de9cccfc6759e5d4815c9a20fa64461dc00e826e87a8a6ae08c43b6fc6&scene=21#wechat_redirect
 */
class MainTest {
    public static void main(String[] args) {
        System.out.println("begin");
        AFAS run = new AFAS(10,5,2,5,0.2,10);
        run.doAFAS(40 );//括号内为迭代次数
    }
}
