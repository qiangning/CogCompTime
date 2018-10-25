package edu.illinois.cs.cogcomp.temporal.utils.CoDL;

import edu.illinois.cs.cogcomp.lbjava.learn.Learner;
import edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.TempRelLabeler;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelType;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE;
import edu.illinois.cs.cogcomp.temporal.utils.CoDL.MultiClassifiers;
import org.jetbrains.annotations.NotNull;

import static edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelType.getNullTempRel;

public class TempRelLabelerMultiLBJ extends TempRelLabeler{
    private MultiClassifiers<TemporalRelation_EE> multiClassifiers;

    public TempRelLabelerMultiLBJ(MultiClassifiers<TemporalRelation_EE> multiClassifiers) {
        this.multiClassifiers = multiClassifiers;
    }

    public TempRelLabelerMultiLBJ(Learner cls, double lambda, boolean norm_before_merge) {
        super();
        multiClassifiers = new MultiClassifiers<>(cls, lambda, norm_before_merge);
    }

    public TempRelLabelerMultiLBJ(double lambda, boolean norm_before_merge) {
        super();
        multiClassifiers = new MultiClassifiers<>(lambda, norm_before_merge);
    }

    public void addClassifier(Learner classifier){
        multiClassifiers.addClassifier(classifier);
    }

    public void dropClassifier(){
        multiClassifiers.dropClassifier();
    }

    public MultiClassifiers<TemporalRelation_EE> getMultiClassifiers() {
        return multiClassifiers;
    }

    @Override
    public boolean isIgnore(TemporalRelation ee) {
        return ! (ee instanceof TemporalRelation_EE)
                ||Math.abs(ee.getSentDiff()) > 1;
    }

    @Override
    @NotNull
    public TemporalRelType tempRelLabel(TemporalRelation ee) {
        TemporalRelType ret = getNullTempRel();
        if(isIgnore(ee))
            return ret;
        if (ee.getSentDiff() == 0 || ee.getSentDiff() == 1) {
            ret = new TemporalRelType(multiClassifiers.discreteValue((TemporalRelation_EE)ee));
            ret.setScores(temporalScores2doubles(multiClassifiers.scores((TemporalRelation_EE)ee), TemporalRelType.relTypes.getAllNames(), true));
        }
        return ret;
    }
}
