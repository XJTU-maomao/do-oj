/*
 * Created by maomao (c)
 * 2021. 1. 2.
 * at Xi'an jiaotong university.
 */

package maomao.optimaze_algorithm.AFAS_PACK;

import java.util.Date;

class AFAS {
    private int fishNum; //鱼群数目
    private int tryTimes; //尝试次数
    private int dim; //维度
    private int step; //人工鱼移动步长
    private double delta; //拥挤度因子
    private int visual; //视野范围
    //人工鱼群、范围内最佳鱼，遍历时的下一条鱼
    private Fish[] fish;
    private Fish bestfish;
    private Fish[] nextfish;
    private int[] choosed;

    public AFAS(){
    }

    public AFAS(int fishNum, int tryTimes, int dim, int step, double delta, int visual) {
        this.fishNum = fishNum;
        this.tryTimes = tryTimes;
        this.dim = dim;
        this.step = step;
        this.delta = delta;
        this.visual = visual;
        fish = new Fish[fishNum];
        nextfish = new Fish[3];
        choosed = new int[fishNum];
        init();
    }

    public void doAFAS(int num){
        long startTime = new Date().getTime();
        int count = 1;
        while (count <= num){
            for (int i = 0; i < fishNum; i++) {
                prey(i);
                swarm(i);
                follow(i);
                bulletin(i);
                System.out.println("第"+count+"遍第"+i+"条鱼结束");
            }
            System.out.println(count+"当前最优值："+bestfish.fit);
            for (int i = 0; i < dim; i++) {
                System.out.print("位置"+(i+1)+":  "+bestfish.x[i]);
            }
            System.out.println();
            count++;
            System.out.println("step:"+step+"    visaul:"+visual);
        }
        System.out.println("最优值："+bestfish.fit);
        for (int i = 0; i < dim; i++) {
            System.out.print("位置"+(i+1)+":  "+bestfish.x[i]);
        }
        System.out.println();
        long endTime = new Date().getTime();
        System.out.println("本程序运行计时： "+(endTime-startTime)+" 毫秒。");
    }

    /**
     * 评价行为
     * @param i
     */
    private void bulletin(int i){
        Fish maxfish = nextfish[0];
        for (int j = 0; j < 3; j++) {
            if (nextfish[j].fit > maxfish.fit && nextfish[j].x[0] != 0 && nextfish[j].x[1] != 0){
                maxfish = nextfish[j];
            }
        }
        if (maxfish.fit < fish[i].fit){
            return;
        }
        fish[i] = maxfish;
        if (maxfish.fit > bestfish.fit){
            bestfish = maxfish;
        }
    }

    /**
     * 追尾行为
     * 人工鱼探索周围邻居鱼的最优位置，
     * 当最优位置的目标函数值大于当前位置的目标函数值并且不是很拥挤，则当前位置向最优邻居鱼移动一步，
     * 否则执行觅食行为。
     * @param i
     */
    private void  follow(int i){
        nextfish[2] = new Fish(dim, visual);
        Fish maxfish = fish[i];
        //获得视野范围内的鱼群
        Fish[] scope = getScopefish(i);
        int key = i;
        if (scope != null){
            for (int j = 0; j < scope.length; j++) {
                //最大适应度的鱼
                if (scope[j].fit > maxfish.fit){
                    maxfish = scope[j];
                    key = j;
                }
            }
            //如果最小适应度的鱼也比自己大，就去觅食
            if (maxfish.fit <= fish[i].fit){
                prey(i);
            }else {
                Fish[] newScope = getScopefish(key);
                if (newScope != null){
                    //检查拥挤度，能不能插入，不能插入就去觅食
                    if (newScope.length*maxfish.fit < delta*fish[i].fit){
                        double dis = fish[i].distance(maxfish);
                        //如果能够插入，就往minfish的位置移动
                        for (int k = 0; k < dim; k++) {
                            nextfish[2].x[k] = (int) (fish[i].x[k]+(maxfish.x[k]-fish[i].x[k])*step*Math.random()/dis);
                        }
                        //更新适应度
                        nextfish[2].fit = nextfish[2].newFunction(nextfish[2].x);
                    } else prey(i);
                } else prey(i);
            }
        }
        else prey(i);
    }

    /**
     * 人工鱼i的聚群行为
     * 人工鱼探索当前邻居内的伙伴数量，并计算伙伴的中心位置，
     * 然后把新得到的中心位置的目标函数与当前位置的目标函数相比较，
     * 如果中心位置的目标函数优于当前位置的目标函数并且不是很拥挤，则当前位置向中心位置移动一步，否则执行觅食行为。
     * 鱼聚群时会遵守两条规则：一是尽量向邻近伙伴的中心移动，二是避免过分拥挤。
     * @param i
     */
    private void swarm(int i){
        nextfish[1] = new Fish(dim,visual);
        int[] center = new int[dim];
        for(int j=0;j<dim;j++){
            center[j] = 0;
        }
        //取得视野内的鱼群
        Fish[] scope = getScopefish(i);
        if(scope!=null){
            for(int j=0;j<scope.length;j++){
                for( i=0; i<dim; ++i ){
                    center[i] += scope[j].x[i];
                }
            }
            //计算中心位置
            for( i=0; i<dim; i++ ){
                center[i] /= scope.length;
            }
            //满足条件
            Fish centerfish = new Fish(dim,visual);
            centerfish.x = center;
            centerfish.fit = centerfish.newFunction(centerfish.x);
            double dis = fish[i].distance(centerfish);
            if(centerfish.fit>fish[i].fit && scope.length*centerfish.fit<delta*fish[i].fit){
                for(int j=0;j<dim;j++){
                    nextfish[1].x[j] = (int) (fish[i].x[j]+(centerfish.x[j]-fish[i].x[j])*step*Math.random()/dis);
                }
                nextfish[1].fit = nextfish[1].newFunction(nextfish[1].x);
            }else prey(i);
        }else prey(i);
    }

    /**
     * 人工鱼i的觅食行为
     * @param i
     */
    private void prey(int i){
        Fish newfish = new Fish(dim,visual);
        newfish.fit = 0;
        nextfish[0] = new Fish(dim,visual);
        //选择次数达到一定数量后，如果仍然不满足条件，则随机移动一步
        for(int k=0; k<tryTimes; k++ ) {           // 进行try_number次尝试
            //在其感知范围内随机选择另一个状态
            for (int j = 0; j < dim; j++) {
                newfish.x[j] = (int) ((2*(Math.random())-1)*visual);
            }
            newfish.fit = newfish.newFunction(newfish.x);
            //如果得到的状态的目标函数大于当前的状态，则向新选择得到的状态靠近一步
            if( newfish.fit > fish[i].fit ){
                double dis = fish[i].distance(newfish);
                for(int j=0; j<dim; j++ ){
                    nextfish[0].x[j] = (int) (fish[i].x[j]+(newfish.x[j]-fish[i].x[j])*step*Math.random()/dis);
                }
                nextfish[0].fit =nextfish[0].newFunction(nextfish[0].x);
            }else {
                //反之，重新选取新状态
                for(int j=0; j<dim; j++){
                    nextfish[0].x[j] = (int) (fish[i].x[j]+visual*(2*(Math.random())-1));
                    nextfish[0].fit = nextfish[0].newFunction(nextfish[0].x);
                }
            }
        }
    }

    /**
     * 获得鱼i视野范围内的鱼群
     * @param i
     * @return
     */
    private Fish[] getScopefish(int i) {
        int num = 0;
        //计算视野范围内的鱼群个数，并且记录下标
        for(int j=0;j<fishNum;j++){
            choosed[j] = -1;
            if(fish[i].distance(fish[j])<visual){
                choosed[j] = i;
                num++;
            }
        }
        //如果视野范围内有其它鱼，标记出来返回
        if (num != 0){
            Fish[] scope = new Fish[num];
            int k = 0;
            for(int j=0;j<fishNum;j++){
                if(choosed[j]!=-1){
                    scope[k++] = fish[choosed[j]];
                }
            }
            return scope;
        }
        return null;
    }

    /**
     * 初始化鱼群，随机生成鱼的位置
     * 并且根据位置计算其适应度
     */
    private void init() {
        for (int i = 0; i < fishNum; i++) {
            fish[i] = new Fish(dim, visual);
            fish[i].fit = fish[i].newFunction(fish[i].x);
        }
        //最优鱼群
        bestfish = new Fish(dim, visual);
        bestfish.fit = -9999999;
    }


}
