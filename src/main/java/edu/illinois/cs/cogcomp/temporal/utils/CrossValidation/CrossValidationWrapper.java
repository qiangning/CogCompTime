package edu.illinois.cs.cogcomp.temporal.utils.CrossValidation;

import edu.illinois.cs.cogcomp.core.datastructures.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class CrossValidationWrapper<LearningStruct> {
    public class PerformanceRecorder{
        public int n_param, n_fold;
        public double[][] param_fold_performance;
        public List<double[]> paramsAll = new ArrayList<>();

        public PerformanceRecorder(int n_param, int n_fold) {
            this.n_param = n_param;
            this.n_fold = n_fold;
            param_fold_performance = new double[n_param][n_fold];
        }

        public void add_paramId_fold(int paramId, int fold, double performance){
            param_fold_performance[paramId][fold] = performance;
        }
        public void add_param(double[] params){
            paramsAll.add(params);
        }
        public double[] aggregate(){
            double[] agg = new double[n_param];
            for(int k=0;k<n_param;k++) {
                double tmp = 0;
                for (int i = 0; i < n_fold; i++) {
                    tmp+=param_fold_performance[k][i];
                }
                agg[k] = tmp/n_fold;
            }
            return agg;
        }

        public Pair<Integer,Double> bestParamIdPerformance(){
            double[] agg = aggregate();
            double best = agg[0];
            int bestId = 0;
            for(int i=1;i<agg.length;i++){
                double p = agg[i];
                if(p>best){
                    best = p;
                    bestId = i;
                }
            }
            return new Pair<>(bestId,best);
        }
        public void printAll(){
            System.out.println("------------------");
            System.out.println("---parameters\t|\t---folds--->");
            for(int i=0;i<n_param;i++){
                if(paramsAll!=null&&paramsAll.get(i)!=null){
                    for(double p:paramsAll.get(i)){
                        System.out.printf("%8.4f\t",p);
                    }
                }
                System.out.printf("|\t");
                for(int j=0;j<n_fold;j++){
                    System.out.printf("%8.4f\t",param_fold_performance[i][j]);
                }
                System.out.println();
            }
            System.out.println("------------------");
            Pair<Integer,Double> best = bestParamIdPerformance();
            System.out.printf("The best param is the %d-th param array, with performance %.4f\n",
                    best.getFirst(),best.getSecond());
            System.out.println("------------------");
        }
    }
    public CrossValidationWrapper(int seed, int totalFold) {//Cross validation
        TotalFold = totalFold;
        this.mode = 0;
        this.seed = seed;
    }

    public CrossValidationWrapper(int seed) {//Train+Dev
        TotalFold = 1;
        this.mode = 1;
        this.seed = seed;
    }

    public double[][] params2tune;
    //    public myLearner<LearningExample> mylearner;
    public CrossValidationSplit cv_splitter;
    public PerformanceRecorder performanceRecorder;
    public int TotalFold = 5;
    public int seed = 0;
    public int mode = 0;//0-->Cross validation. 1-->Train+Dev+Test
    //    public List<Pair<Double,double[]>> performance2param = new ArrayList<>();
    protected List<LearningStruct> trainingStructs = new ArrayList<>(), devStructs = new ArrayList<>();//devStructs don't exist when mode==1
    private List<LearningStruct> trainingSplits = new ArrayList<>(), devSplits = new ArrayList<>();

    public abstract void load();//load training/dev/test Structs
    public abstract void learn(List<LearningStruct> slist, double[] param, int seed);
    public abstract double evaluate(List<LearningStruct> slist, int verbose);
    public abstract void setParams2tune();

    private void split(int fold,double[] param){
        cleanTrainDevExamples();
        if(cv_splitter==null){
            cv_splitter = new CrossValidationSplit(TotalFold,trainingStructs.size(),seed);
        }
        List<Integer> trainidx = cv_splitter.TrainTestSplit(fold).getFirst();
        List<Integer> devidx = cv_splitter.TrainTestSplit(fold).getSecond();
        for(int i:trainidx)
            trainingSplits.add(trainingStructs.get(i));
        for(int j:devidx)
            devSplits.add(trainingStructs.get(j));
    }
    private void cleanTrainDevExamples(){
        trainingSplits = new ArrayList<>();
        devSplits = new ArrayList<>();
    }

    public void myParamTuner(){
        setParams2tune();
        int n_param = params2tune.length;
        performanceRecorder = new PerformanceRecorder(n_param,TotalFold);
        System.out.println("Parameter tuning...");
        for(int n=0;n<n_param;n++){
            System.out.printf("%d\t",n);
            double[] param = params2tune[n];
            seed++;
            if(mode==0) {
                performanceRecorder.add_param(param);
                for (int f = 0; f < TotalFold; f++) {
                    split(f+1, param);
                    learn(trainingSplits, param,seed);//it's learn()'s job to shuffle
                    double performance = evaluate(devSplits,0);
                    performanceRecorder.add_paramId_fold(n, f, performance);
                }
            }
            else if(mode==1){
                trainingSplits = trainingStructs;
                devSplits = devStructs;
                learn(trainingSplits,param,seed);
                double performance = evaluate(devSplits,0);
                performanceRecorder.add_param(param);
                performanceRecorder.add_paramId_fold(n, 0, performance);
            }
        }
        System.out.println();
        performanceRecorder.printAll();
    }
    private double[] getBestParamFromRecorder(){
        Pair<Integer,Double> best = performanceRecorder.bestParamIdPerformance();
        return params2tune[best.getFirst()];
    }
    public void retrainUsingBest(){
        // get best param
        double[] bestparam = getBestParamFromRecorder();
        retrainUsingGiven(bestparam);
    }
    public void retrainUsingGiven(double[] givenparam){
        System.out.printf("Current seed=%d\n",seed);
        // gen training examples
        trainingSplits = new ArrayList<>();
        trainingSplits.addAll(trainingStructs);
        //trainingSplits = trainingStructs;
        if(mode==1)
            trainingSplits.addAll(devStructs);
        // retrain
        learn(trainingSplits,givenparam,seed++);
        // training performance
        double performance_train = evaluate(trainingSplits,1);
        System.out.println("------------------");
        System.out.printf("After training, seed=%d\n",seed);
        System.out.println("Retrain using best/given param:");
        for(double d:givenparam)
            System.out.printf("%8.4f\t",d);
        System.out.println();
        System.out.printf("Performance: %.4f\n",performance_train);
        System.out.println("------------------");
    }
}
