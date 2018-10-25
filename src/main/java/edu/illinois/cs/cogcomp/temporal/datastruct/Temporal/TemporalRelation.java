package edu.illinois.cs.cogcomp.temporal.datastruct.Temporal;

import edu.illinois.cs.cogcomp.temporal.configurations.SignalWordSet;
import edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph.BinaryRelation;
import edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph.BinaryRelationType;
import edu.illinois.cs.cogcomp.temporal.readers.temprelAnnotationReader;
import edu.illinois.cs.cogcomp.temporal.utils.myUtils4TextAnnotation;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chuchu on 12/20/17.
 */
public class TemporalRelation extends BinaryRelation<TemporalNode>{
    private static int LabelMode;
    private myTemporalDocument doc;
    protected int sentDiff;//always positive
    /*Features that may not be initialized*/
    protected HashSet<String> signals_before,signals_between,signals_after;
    /*Constructors*/

    public TemporalRelation(TemporalNode sourceNode, TemporalNode targetNode, TemporalRelType relType, myTemporalDocument doc) {
        super(sourceNode, targetNode, relType);
        this.doc = doc;
        sentDiff = Math.abs(targetNode.getSentId()-sourceNode.getSentId());
    }

    /*Functions*/
    @Override
    public TemporalRelation inverse() {
        return new TemporalRelation(getTargetNode(),getSourceNode(),getRelType().inverse(),doc);
    }

    protected HashSet<String> getSignalsFromText(String text, SignalWordSet signalWordSet){
        HashSet<String> ret = getSignalsHelper(text,signalWordSet.temporalSignalSet.connectives_before,"temporalConnectiveSet_before");
        ret.addAll(getSignalsHelper(text,signalWordSet.temporalSignalSet.connectives_after,"temporalConnectiveSet_after"));
        ret.addAll(getSignalsHelper(text,signalWordSet.temporalSignalSet.connectives_during,"temporalConnectiveSet_during"));
        ret.addAll(getSignalsHelper(text,signalWordSet.temporalSignalSet.connectives_contrast,"temporalConnectiveSet_contrast"));
        ret.addAll(getSignalsHelper(text,signalWordSet.temporalSignalSet.connectives_adverb,"temporalConnectiveSet_adverb"));
        ret.addAll(getSignalsHelper(text,signalWordSet.modalVerbSet,"modalVerbSet"));
        ret.addAll(getSignalsHelper(text,signalWordSet.axisSignalWordSet,"axisSignalWordSet"));
        return ret;
    }

    protected HashSet<String> getSignalsFromLemma(String Lemma, SignalWordSet signalWordSet){
        HashSet<String> ret = getSignalsHelper(Lemma,signalWordSet.reportingVerbSet,"reportingVerbSet");
        ret.addAll(getSignalsHelper(Lemma,signalWordSet.intentionVerbSet,"intentionVerbSet"));
        return ret;
    }

    protected HashSet<String> getSignalsHelper(String text, Set<String> keywords, String keywordsTag){
        HashSet<String> ret = myUtils4TextAnnotation.findKeywordsInText(text, keywords,keywordsTag);
        if(!ret.contains(keywordsTag+":"+"N/A"))
            ret.add(keywordsTag+":EXISTS");
        return ret;
    }

    /*Getters and Setters*/
    @Override
    public TemporalRelType getRelType(){
        return (TemporalRelType) super.getRelType();
    }

    public boolean isNull() {
        return getRelType().isNull();
    }

    @Override
    @NotNull
    public TemporalRelation getInverse(){
        return (TemporalRelation)super.getInverse();
    }

    public myTemporalDocument getDoc() {
        return doc;
    }

    public String getLabel(){
        String label = getRelType().getReltype().getName();
        temprelAnnotationReader.Q1_Q2_temprel tuple = new temprelAnnotationReader.Q1_Q2_temprel(new TemporalRelType(label));
        switch (LabelMode){
            case 0:
                return label;
            case 1:
                return tuple.isQ1()?"q1:yes":"q1:no";
            case 2:
                return tuple.isQ2()?"q2:yes":"q2:no";
            default:
                return label;
        }
    }

    public int getSentDiff() {
        return sentDiff;
    }

    public HashSet<String> getSignals_before() {
        return signals_before;
    }

    public HashSet<String> getSignals_between() {
        return signals_between;
    }

    public HashSet<String> getSignals_after() {
        return signals_after;
    }

    @Override
    public String toString() {
        return String.format("%s-->%s: %s",getSourceNode().getUniqueId(),getTargetNode().getUniqueId(), getRelType().toString());
    }

    public static void setLabelMode(int labelMode) {
        TemporalRelation.LabelMode = labelMode;
    }
}
