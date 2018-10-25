package edu.illinois.cs.cogcomp.temporal.utils.CoDL;

import edu.illinois.cs.cogcomp.lbjava.classify.ScoreSet;

public interface ScoringFunc<LearningObj> {
    ScoreSet scores(LearningObj obj);
    String discreteValue(LearningObj obj);
}
