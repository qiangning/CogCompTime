package edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor;

import edu.illinois.cs.cogcomp.lbjava.learn.Learner;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelType;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET;

import static edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelType.getNullTempRel;

public class TempRelLabelerLBJ_ET extends TempRelLabeler {
    private Learner classifier_dist0;

    public TempRelLabelerLBJ_ET(Learner classifier_dist0) {
        this.classifier_dist0 = classifier_dist0;
    }

    @Override
    public boolean isIgnore(TemporalRelation et) {
        return !(et instanceof TemporalRelation_ET)
                || ((TemporalRelation_ET) et).getTimexNode().isDCT() // ignore E-DCT
                || Math.abs(et.getSentDiff())!=0; // ignore ET that aren't in the same sentence
    }

    @Override
    public TemporalRelType tempRelLabel(TemporalRelation et) {
        TemporalRelType ret = getNullTempRel();
        if(isIgnore(et))
            return ret;
        ret = new TemporalRelType(classifier_dist0.discreteValue(et));
        ret.setScores(temporalScores2doubles(classifier_dist0.scores(et), TemporalRelType.relTypes.getAllNames(), true));
        return ret;
    }
}
