/*
 * Created by maomao (c)
 * 2021. 1. 2.
 * at Xi'an jiaotong university.
 */

package maomao.optimaze_algorithm.AFAS_PACK;

class Fish {
    private final int dim;    //每条鱼的维度
    int[] x;    //每条鱼的具体多维坐标
    double fit;  //鱼的适应值，浓度

    Fish(int dim, int visual){
        this.dim = dim;
        x = new int[dim];
        for (int i = 0; i < dim; i++) {
            x[i] = (int) Math.floor(256*Math.random());
        }
        fit = 0;
    }

    double distance(Fish f){
        double a = 0;
        for (int i = 0; i < dim; i++) {
            if (this.x[i] - f.x[i] == 0){
                a = 0.00001;
            }else {
                a += (this.x[i] - f.x[i]) * (this.x[i] - f.x[i]);
            }
        }
        return Math.sqrt(a);
    }

    double newFunction(int[] w){
        return -(w[0]*w[0] - 160*w[0]+640+w[1]*w[1]-260*w[1]+16900);
    }

}
