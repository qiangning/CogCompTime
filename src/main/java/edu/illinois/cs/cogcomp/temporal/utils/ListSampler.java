package edu.illinois.cs.cogcomp.temporal.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListSampler<T> {
    public interface ListElementLabeler<T>{
        boolean labeler(T element);// those elements with "True" labels won't be affected by samplingRate
    }

    ListElementLabeler<T> listElementLabeler;

    public ListSampler(ListElementLabeler<T> listElementLabeler) {
        this.listElementLabeler = listElementLabeler;
    }

    public double autoSelectSamplingRate(List<T> list){
        int cnt1 = 0, cnt2 = 0;
        for(T element:list){
            if(listElementLabeler.labeler(element))
                cnt1++;
            else
                cnt2++;
        }
        return 1.0*cnt1/cnt2;
    }
    public List<T> ListSampling(List<T> list, Random rng){
        double samplingRate = autoSelectSamplingRate(list);
        return ListSampling(list,samplingRate,rng);
    }
    public List<T> ListSampling(List<T> list, double samplingRate, Random rng){
        List<T> newlist = new ArrayList<>();
        for(T element:list){
            if(listElementLabeler.labeler(element))
                newlist.add(element);
            else{
                if(samplingRate==1)
                    newlist.add(element);
                else if(samplingRate<1){
                    if(rng.nextDouble() <= samplingRate)
                        newlist.add(element);
                }
                else{
                    double tmp = samplingRate;
                    for (; tmp > 1; tmp--) {
                        newlist.add(element);
                    }
                    if (rng.nextDouble() <= tmp)
                        newlist.add(element);
                }
            }
        }
        return newlist;
    }
}
