package edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor;

import edu.illinois.cs.cogcomp.lbjava.learn.Learner;

import static edu.illinois.cs.cogcomp.temporal.readers.axisAnnotationReader.LABEL_NOT_ON_ANY_AXIS;

public class EventAxisLabelerLBJ implements EventAxisLabeler {
    public Learner classifier;

    public EventAxisLabelerLBJ(Learner classifier) {
        this.classifier = classifier;
    }

    @Override
    public String axisLabel(EventTokenCandidate etc) {
        return classifier!=null?
                classifier.discreteValue(etc):LABEL_NOT_ON_ANY_AXIS;
    }
}
