package edu.illinois.cs.cogcomp.temporal.utils.CrossValidation;

import edu.illinois.cs.cogcomp.core.datastructures.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CrossValidationSplit {
    public int TotalFold;
    public int SampleSize;
    public int seed;
    public List<Integer> allIdx = new ArrayList<>();
    public CrossValidationSplit(int TotalFold, int SampleSize, int seed){
        this.TotalFold = TotalFold;
        this.SampleSize = SampleSize;
        this.seed = seed;
        for(int i=0;i<SampleSize;i++){
            allIdx.add(i);
        }
        Collections.shuffle(allIdx, new Random(seed));
    }
    public List<Integer> getFold(int fold){
        if(fold < 1 || fold > TotalFold)
            return null;
        int left = (int)(1.0*(fold-1)*SampleSize/TotalFold);
        int right = (int)(1.0*fold*SampleSize/TotalFold);
        left = left<0? 0 : left;
        right = right>SampleSize? SampleSize:right;
        return allIdx.subList(left,right);
    }
    public Pair<List<Integer>,List<Integer>> TrainTestSplit(int fold){
        if(fold < 1 || fold > TotalFold)
            return null;
        List<Integer> test = getFold(fold);
        List<Integer> train = new ArrayList<>();
        for(int i = 1; i <= TotalFold; i++){
            if(i == fold)
                continue;
            train.addAll(getFold(i));
        }
        return new Pair<>(train,test);

    }
    public static void main(String[] args) {
        int TotalFold = 3;
        int SampleSize = 10;
        CrossValidationSplit cv = new CrossValidationSplit(TotalFold,SampleSize,1);
        System.out.println(cv.allIdx);
        for(int i = 1;i<=TotalFold;i++) {
            System.out.println("******Fold: "+i+"******\nTrain:");
            System.out.println(cv.TrainTestSplit(i).getFirst()+"\nTest:");
            System.out.println(cv.TrainTestSplit(i).getSecond());
        }
    }
}

