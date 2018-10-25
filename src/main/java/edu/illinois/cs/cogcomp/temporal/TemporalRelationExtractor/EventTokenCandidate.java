package edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor;

import edu.illinois.cs.cogcomp.core.datastructures.Pair;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.PredicateArgumentView;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Relation;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.temporal.configurations.SignalWordSet;
import edu.illinois.cs.cogcomp.temporal.configurations.VerbIgnoreSet;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TimexTemporalNode;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.myTemporalDocument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static edu.illinois.cs.cogcomp.temporal.utils.myUtils4TextAnnotation.*;

public class EventTokenCandidate {
    /*public static HashMap<String, float[]> label_map = new HashMap<String, float[]>(){{
        put("main",new float[]{1f,0f,0f});
        put("others",new float[]{0f,1f,0f});
        put("null",new float[]{0f,0f,1f});
    }};*/
    private String label;//event axis label or not an event
    private int tokenId,sentId;
    private String pos;
    private String lemma;
    private String pp_head;

    private int window;
    private String[] pos_window;
    private String[] lemma_window;

    private HashSet<String> signals_before;
    private HashSet<String> signals_after;
    private boolean isReporting,isIntention;

    private TimexTemporalNode closestTimex_left,closestTimex_right;

    private Constituent verb_srl;
    private List<Constituent> verb_srl_same_sentence = new ArrayList<>();
    private List<Pair<String, Constituent>> verb_srl_covering = new ArrayList<>();

    private EventTokenCandidate prev_event;

    /*private List<String> synsets;
    private List<String> derivations;*/

    private myTemporalDocument doc;

    public EventTokenCandidate(myTemporalDocument doc,int tokenId, String label, int window, EventTokenCandidate prev_event) {
        this.doc = doc;
        this.tokenId = tokenId;
        this.label = label;
        this.window = window;
        this.prev_event = prev_event;
        if(window<0){
            System.out.println("[WARNING] Window cannot be negative; reset to 0.");
            window = 0;
        }
        TextAnnotation ta = doc.getTextAnnotation();
        if(!isTokenIdValid(ta,tokenId)) return;
        sentId = ta.getSentenceId(tokenId);
        // POS
        this.pos = retrievePOSAtTokenId(ta,tokenId);
        // Lemma
        this.lemma = retrieveLemmaAtTokenId(ta,tokenId);
        // PP Head
        this.pp_head = retrievePPHeadOfTokenId(ta,tokenId);
        // POS in a window
        pos_window = retrievePOSWindow(ta,tokenId,window);
        // Lemma in a window
        lemma_window = retrieveLemmaWindow(ta,tokenId,window);
        // Signal words before and after tokenId within the same sentence
        signals_before = new HashSet<>();
        signals_after = new HashSet<>();
        int start = startTokInSent(doc.getTextAnnotation(),sentId);
        int end = endTokInSent(ta,sentId);
        String text_before = getSurfaceTextInBetween(ta,start,tokenId-1);
        String text_after = getSurfaceTextInBetween(ta,tokenId+1,end);
        String lemma_before = getLemmaTextInBetween(ta,start,tokenId-1);
        String lemma_after = getLemmaTextInBetween(ta,tokenId+1,end);
        signals_before = findKeywordsInText(text_before, SignalWordSet.getInstance().temporalSignalSet.getAllConnectives(),"TemporalConnective");
        signals_after = findKeywordsInText(text_after, SignalWordSet.getInstance().temporalSignalSet.getAllConnectives(),"TemporalConnective");
        signals_before.addAll(findKeywordsInText(text_before, SignalWordSet.getInstance().modalVerbSet,"modalVerbSet"));
        signals_after.addAll(findKeywordsInText(text_after, SignalWordSet.getInstance().modalVerbSet,"modalVerbSet"));
        signals_before.addAll(findKeywordsInText(text_before, SignalWordSet.getInstance().axisSignalWordSet,"axisSignalWordSet"));
        signals_after.addAll(findKeywordsInText(text_after, SignalWordSet.getInstance().axisSignalWordSet,"axisSignalWordSet"));
        signals_before.addAll(findKeywordsInText(lemma_before, SignalWordSet.getInstance().reportingVerbSet,"reportingVerbSet"));
        signals_after.addAll(findKeywordsInText(lemma_after, SignalWordSet.getInstance().reportingVerbSet,"reportingVerbSet"));
        signals_before.addAll(findKeywordsInText(lemma_before, SignalWordSet.getInstance().intentionVerbSet,"intentionVerbSet"));
        signals_after.addAll(findKeywordsInText(lemma_after, SignalWordSet.getInstance().intentionVerbSet,"intentionVerbSet"));
        isReporting = SignalWordSet.getInstance().reportingVerbSet.contains(lemma);
        isIntention = SignalWordSet.getInstance().intentionVerbSet.contains(lemma);

        // closest Timex
        int i=0;
        List<TimexTemporalNode> allTimexes = doc.getTimexList();
        while(i<allTimexes.size() && allTimexes.get(i).getTokenSpan().getSecond()<tokenId){
            closestTimex_left = allTimexes.get(i);
            i++;
        }
        if(i<allTimexes.size())
            closestTimex_right = allTimexes.get(i);

        // Verb SRL from the same sentence
        List<Constituent> allPredicates = new ArrayList<>();
        if(ta.hasView(ViewNames.SRL_VERB))
            allPredicates = ((PredicateArgumentView)doc.getTextAnnotation().getView(ViewNames.SRL_VERB)).getPredicates();
        for(Constituent c:allPredicates){
            if(sentId==c.getSentenceId()&& !VerbIgnoreSet.getInstance().srlVerbIgnoreSet.contains(c.getAttribute("predicate"))) {
                verb_srl_same_sentence.add(c);
                if(c.getStartSpan()==tokenId)
                    verb_srl = c;
                List<Relation> tmp = c.getOutgoingRelations();
                for(Relation r:tmp){
                    if(r.getTarget().doesConstituentCover(tokenId))
                        verb_srl_covering.add(new Pair<>(r.getRelationName(),c));
                }
            }
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /*public float[] getLabelArray(){return label_map.get(label);}*/

    public int getTokenId() {
        return tokenId;
    }

    public int getSentId() {
        return sentId;
    }

    public String getPos() {
        return pos;
    }

    public String getLemma() {
        return lemma;
    }

    public String getPp_head() {
        return pp_head;
    }

    public int getWindow() {
        return window;
    }

    public String[] getPos_window() {
        return pos_window;
    }

    public String[] getLemma_window() {
        return lemma_window;
    }

    public HashSet<String> getSignals_before() {
        return signals_before;
    }

    public HashSet<String> getSignals_after() {
        return signals_after;
    }

    public boolean isReporting() {
        return isReporting;
    }

    public boolean isIntention() {
        return isIntention;
    }

    public TimexTemporalNode getClosestTimex_left() {
        return closestTimex_left;
    }

    public TimexTemporalNode getClosestTimex_right() {
        return closestTimex_right;
    }

    public List<Constituent> getVerb_srl_same_sentence() {
        return verb_srl_same_sentence;
    }

    public Constituent getVerb_srl() {
        return verb_srl;
    }

    public List<Pair<String,Constituent>> getVerb_srl_covering() {
        return verb_srl_covering;
    }

    public EventTokenCandidate getPrev_event() {
        return prev_event;
    }

    public void setPrev_event(EventTokenCandidate prev_event) {
        this.prev_event = prev_event;
    }

    public myTemporalDocument getDoc() {
        return doc;
    }

    public String htmlVisualize(){
        StringBuilder sb = new StringBuilder();
        TextAnnotation ta = doc.getTextAnnotation();
        int startTokId = startTokInSent(ta,sentId);
        int endTokId = endTokInSent(ta,sentId);
        sb.append("<p>");
        for(int i=startTokId;i<=endTokId;i++){
            if(i!=tokenId)
                sb.append(ta.getToken(i));
            else
                sb.append(String.format("<span style='color:red;'><strong>%s (AXIS=%s)</strong></span>",ta.getToken(i),getLabel()));
            sb.append(" ");
        }
        sb.append("</p>\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "EventTokenCandidate{" +
                "label='" + label + '\'' +
                ", lemma='" + lemma + '\'' +
                ", sentId=" + sentId +
                ", tokenId=" + tokenId +
                ", pos='" + pos + '\'' +
                ", pp_head='" + pp_head + '\'' +
                ", prev_event='" + (prev_event!=null?"Exists":"None") + '\'' +
                ", closestTimex_left=" + closestTimex_left +
                ", closestTimex_right=" + closestTimex_right +
                '}';
    }
}
