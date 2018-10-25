package edu.illinois.cs.cogcomp.temporal.utils.CoDL;

import edu.illinois.cs.cogcomp.lbjava.classify.ScoreSet;
import edu.illinois.cs.cogcomp.lbjava.learn.Learner;
import edu.illinois.cs.cogcomp.lbjava.learn.Softmax;

import java.util.ArrayList;
import java.util.List;

public class MultiClassifiers<LearningObj> implements ScoringFunc<LearningObj> {
    private double lambda;
    private boolean norm_before_merge = true;
    public List<Learner> classifiers;
    public MultiClassifiers(Learner cls, double lambda, boolean norm_before_merge){
        classifiers = new ArrayList<>();
        classifiers.add(cls);
        this.lambda = lambda;
        this.norm_before_merge = norm_before_merge;
    }
    public MultiClassifiers(double lambda, boolean norm_before_merge){
        classifiers = new ArrayList<>();
        this.lambda = lambda;
        this.norm_before_merge = norm_before_merge;
    }

    public void addClassifier(Learner classifier){
        System.out.printf("Adding classifier %s\n",classifier.getModelLocation());
        classifiers.add(classifier);
    }
    public void dropClassifier(){
        int n = classifiers.size();
        if(classifiers.get(n-1)!=null)
            System.out.printf("Dropping classifier %s\n", classifiers.get(n-1).getModelLocation());
        classifiers.remove(n-1);
    }

    public void printStats(){
        int n = classifiers.size();
        System.out.printf("#Classifiers=%d\n",n);
        for(int i=0;i<n;i++){
            System.out.printf("Classifier No. %2d: %s\n",i,classifiers.get(i).getModelLocation());
        }
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public ScoreSet scores(LearningObj obj){
        ScoreSet[] scores = new ScoreSet[classifiers.size()];
        scores[0] = classifiers.get(0).scores(obj);
        ScoreSet finalScores = scores[0];
        for(int i=1;i<classifiers.size();i++){
            scores[i] = classifiers.get(i).scores(obj);
            finalScores = mergeScores(finalScores,scores[i],lambda);
        }
        return finalScores;
    }
    private ScoreSet unNormalizeScores(ScoreSet scoreSet){
        ScoreSet unnorm = new ScoreSet();
        Object[] values = scoreSet.values().toArray();
        unnorm.put((String) values[0],0);
        for(int i=1;i<values.length;i++){
            String p = (String) values[i];
            String p0 = (String) values[0];
            double val = Math.log(1.0*scoreSet.get((String)p)/scoreSet.get((String)p0));
            unnorm.put((String) values[i],val);
        }
        return unnorm;
    }
    public ScoreSet mergeScores(ScoreSet s1, ScoreSet s2, double l){
        ScoreSet scores = new ScoreSet();
        Softmax sm = new Softmax();
        if(norm_before_merge){
            s1 = sm.normalize(s1);
            s2 = sm.normalize(s2);
        }
        for(Object v:s1.values()){
            if(s2.values().contains((String)v)) {
                double avg_score = s1.get((String) v) * (1 - l) + s2.get((String) v) * l;
                scores.put((String) v, avg_score);
            }
            else{
                //System.out.println("Inconsistent keys in two scoresets.");
                scores.put((String)v, s1.get((String)v));
            }
        }
        if(norm_before_merge){
            scores = unNormalizeScores(scores);
        }
        return scores;
    }

    @Override
    public String discreteValue(LearningObj obj) {
        ScoreSet scores = scores(obj);
        double max_score = -Double.MAX_VALUE;
        String pred = "[WRONG]";
        for(Object v:scores.values()){
            double score_v = scores.get((String) v);
            if(score_v>max_score){
                max_score = score_v;
                pred = (String) v;
            }
        }
        return pred;
    }
}
