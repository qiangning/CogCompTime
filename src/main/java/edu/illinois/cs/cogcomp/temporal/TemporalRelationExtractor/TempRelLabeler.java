package edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor;

import edu.illinois.cs.cogcomp.lbjava.classify.ScoreSet;
import edu.illinois.cs.cogcomp.lbjava.learn.Softmax;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelType;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation;

public abstract class TempRelLabeler {
    // Do two things: 1) return a TemporalRelType label. 2) assign scores to TemporalRelType
    public abstract TemporalRelType tempRelLabel(TemporalRelation ee);

    public abstract boolean isIgnore(TemporalRelation ee);

    protected double[] temporalScores2doubles(ScoreSet scores, String[] allLabels, boolean norm){
        if(norm) {
            Softmax sm = new Softmax();
            scores = sm.normalize(scores);
        }
        int n = allLabels.length;
        double[] s = new double[n];
        for(int i=0;i<n;i++){
            try {
                s[i] = scores.get(allLabels[i]);
            }
            catch (Exception e){
                s[i] = 0;
            }
        }
        return s;
    }
}
