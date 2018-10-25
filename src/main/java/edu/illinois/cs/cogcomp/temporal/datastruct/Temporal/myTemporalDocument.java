package edu.illinois.cs.cogcomp.temporal.datastruct.Temporal;

import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.core.datastructures.IntPair;
import edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventAxisPerceptronTrainer;
import edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate;
import edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.myTextPreprocessor;
import edu.illinois.cs.cogcomp.temporal.configurations.VerbIgnoreSet;
import edu.illinois.cs.cogcomp.temporal.configurations.temporalConfigurator;
import edu.illinois.cs.cogcomp.temporal.readers.temprelAnnotationReader;
import edu.illinois.cs.cogcomp.temporal.utils.PrecisionRecallManager;
import edu.illinois.cs.cogcomp.temporal.utils.myLogFormatter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph.AugmentedNode.getUniqueId;
import static edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelType.getNullTempRel;
import static edu.illinois.cs.cogcomp.temporal.readers.axisAnnotationReader.*;
import static edu.illinois.cs.cogcomp.temporal.readers.temprelAnnotationReader.readTemprelFromCrowdFlower;

public class myTemporalDocument implements Serializable {
    private static final long serialVersionUID = -1304837964767492246L;
    public final static String EventNodeType = "E";
    public final static String TimexNodeType = "T";
    private List<EventTemporalNode> eventList = new ArrayList<>();
    private List<TimexTemporalNode> timexList = new ArrayList<>();
    private TimexTemporalNode dct;// a shallow copy of one of those in timexList
    private TextAnnotation ta;
    private TemporalGraph graph;
    private String docid;
    private HashMap<Integer,EventTemporalNode> map_tokenId2event = new HashMap<>();
    private HashMap<String,TimexTemporalNode> map_tokenSpan2timex = new HashMap<>();

    /*Constructors*/
    public myTemporalDocument() {
    }

    public myTemporalDocument(String bodytext, String docid) throws Exception{
        this.docid = docid;
        myTextPreprocessor myTextPreprocessor = new myTextPreprocessor();
        ta = myTextPreprocessor.extractTextAnnotation(bodytext);
        graph = new TemporalGraph(this);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        dct = new TimexTemporalNode(0,TimexNodeType,dateFormat.format(date),timexList.size(),new IntPair(-1,-1),-1,true,"DATE","",dateFormat.format(date),ta);
        addTimex(dct);
    }

    public myTemporalDocument(String bodytext, String docid, String dct_yyyy_mm_dd) throws Exception{
        this.docid = docid;
        myTextPreprocessor myTextPreprocessor = new myTextPreprocessor();
        ta = myTextPreprocessor.extractTextAnnotation(bodytext);
        graph = new TemporalGraph(this);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dct_yyyy_mm_dd);
        dct = new TimexTemporalNode(0,TimexNodeType,dateFormat.format(date),timexList.size(),new IntPair(-1,-1),-1,true,"DATE","",dateFormat.format(date),ta);
        addTimex(dct);
    }

    public myTemporalDocument(myTemporalDocument other){
        graph = new TemporalGraph(this);
        for(EventTemporalNode e:other.eventList) {
            EventTemporalNode newevent = new EventTemporalNode(e, this);
            addEvent(newevent);
        }
        for(TimexTemporalNode t:other.timexList) {
            TimexTemporalNode newtimex = new TimexTemporalNode(t);
            if(newtimex.isDCT())
                dct = newtimex;
            addTimex(newtimex);
        }
        ta = other.ta;
        docid = other.docid;
        for(TemporalRelation rel:other.graph.getRelations()){
            if(rel instanceof TemporalRelation_EE)
                graph.addRelNoDup(new TemporalRelation_EE((EventTemporalNode)graph.getNode(rel.getSourceNode().getUniqueId()),
                        (EventTemporalNode)graph.getNode(rel.getTargetNode().getUniqueId()),
                        new TemporalRelType(rel.getRelType()),
                        this));
            else if(rel instanceof TemporalRelation_ET){
                graph.addRelNoDup(new TemporalRelation_ET(graph.getNode(rel.getSourceNode().getUniqueId()),
                        graph.getNode(rel.getTargetNode().getUniqueId()),
                        new TemporalRelType(rel.getRelType()),
                        this));
            }
            else if(rel instanceof TemporalRelation_TT){
                graph.addRelNoDup(new TemporalRelation_TT((TimexTemporalNode)graph.getNode(rel.getSourceNode().getUniqueId()),
                        (TimexTemporalNode)graph.getNode(rel.getTargetNode().getUniqueId()),
                        new TemporalRelType(rel.getRelType()),
                        this));
            }
            else{
                System.out.println("[WARNING] unexpected type of temporal relations (EE/ET/TT).");
            }
        }
    }

    /*Functions*/

    public void keepAnchorableEvents(HashMap<Integer,String> axisMap){
        // axisMap: (index in doc, CrowdFlower axis name)
        List<EventTemporalNode> newEventList = new ArrayList<>();
        for(EventTemporalNode e:eventList){
            if(!axisMap.containsKey(eventList.indexOf(e))
                    ||!axisMap.get(eventList.indexOf(e)).contains("yes")) {
                graph.dropNode(getUniqueId(EventNodeType,e.getEiid()));
            }
            else{
                newEventList.add(e);
            }
        }
        eventList = newEventList;
    }

    public void loadRelationsFromMap(List<temprelAnnotationReader.CrowdFlowerEntry> relMap, int verbose){
        // currently, relMap is EE only
        for(temprelAnnotationReader.CrowdFlowerEntry entry:relMap){
            int eiid1 = entry.getEventid1();
            int eiid2 = entry.getEventid2();
            TemporalRelType rel = entry.getRel().getRelType();
            EventTemporalNode sourceNode = (EventTemporalNode) graph.getNode(getUniqueId(EventNodeType,eiid1));
            EventTemporalNode targetNode = (EventTemporalNode) graph.getNode(getUniqueId(EventNodeType,eiid2));
            if(sourceNode==null||targetNode==null){
                if(verbose>0)
                    System.out.printf("[WARNING] null node in graph %s: %s\n", docid,entry.toString());
                continue;
            }
            TemporalRelation_EE tmpRel = new TemporalRelation_EE(sourceNode, targetNode, rel, this);
            graph.addRelNoDup(tmpRel);
        }
        // fill missing EE relations by "vague" (although in my current collection scheme, there should never be missing EE relations)
        for(EventTemporalNode e1:eventList){
            for(EventTemporalNode e2:eventList){
                if(e1.getTokenId() < e2.getTokenId() && Math.abs(e1.getSentId() - e2.getSentId()) <= 1){
                    if(graph.getEERelBetweenEvents(e1.getUniqueId(), e2.getUniqueId()) == null){
                        TemporalRelation_EE tmpRel = new TemporalRelation_EE(e1, e2, new TemporalRelType(TemporalRelType.relTypes.VAGUE), this);
                        graph.addRelNoDup(tmpRel);
                        System.out.println("[WARNING] unexpected missing ee relations "+docid+":"+tmpRel);
                        System.out.println("This is a sanity check and can be safely ignored.");
                    }
                }
            }
        }
    }

    public List<EventTokenCandidate> generateAllEventTokenCandidates(int window, HashMap<Integer,String> labelMap){
        // labelMap: (tokenId, converted axis name)
        String[] tokens = ta.getTokens();
        List<EventTokenCandidate> allCandidates = new ArrayList<>();
        EventTokenCandidate prev_event = null;
        for(int i=0;i<tokens.length;i++){
            String label = labelMap.getOrDefault(i,LABEL_NOT_ON_ANY_AXIS);
            EventTokenCandidate etc = new EventTokenCandidate(this,i,label,window,prev_event);
            if(!label.equals(LABEL_NOT_ON_ANY_AXIS))
                prev_event = etc;
            if(etc.getPos().startsWith("V")&&!VerbIgnoreSet.getInstance().verbIgnoreSet.contains(etc.getLemma()))
                allCandidates.add(etc);
        }
        return allCandidates;
    }

    public void addEvent(EventTemporalNode e){
        eventList.add(e);
        graph.addNodeNoDup(e);
    }

    public void addTimex(TimexTemporalNode t){
        timexList.add(t);
        graph.addNodeNoDup(t);
    }

    public void dropAllEventsAndTimexes(){
        dropAllEventNodes();
        dropAllTimexNodes();
    }

    public void dropAllEventNodes(){
        for(EventTemporalNode e:eventList){
            graph.dropNode(e.getUniqueId());
        }
        eventList = new ArrayList<>();
    }

    public void dropAllTimexNodes(){
        for(TimexTemporalNode t:timexList)
            graph.dropNode(t.getUniqueId());
        timexList = new ArrayList<>();
    }

    public void dropAllRelations(){
        graph.dropAllRelations();
    }

    public void sortAllEvents(){
        eventList.sort(new Comparator<EventTemporalNode>() {
            @Override
            public int compare(EventTemporalNode e1, EventTemporalNode e2) {
                if(e1.getTokenId()<e2.getTokenId())
                    return -1;
                else if(e1.getTokenId()>e2.getTokenId())
                    return 1;
                return 0;
            }
        });
    }

    public void sortAllTimexes(){
        timexList.sort(new Comparator<TimexTemporalNode>() {
            @Override
            public int compare(TimexTemporalNode t1, TimexTemporalNode t2) {
                if(t1.getTokenSpan().getFirst()<t2.getTokenSpan().getFirst())
                    return -1;
                else if(t1.getTokenSpan().getFirst()>t2.getTokenSpan().getFirst())
                    return 1;
                return 0;
            }
        });
    }

    public void extractAllFeats(int win){
        for(EventTemporalNode e:eventList)
            e.extractAllFeats(win);
        for(TimexTemporalNode t:timexList)
            t.extractAllFeats(win);
        for(TemporalRelation_EE tmp:graph.getAllEERelations(-1))
            tmp.extractAllFeats();
        for(TemporalRelation_ET tmp:graph.getAllETRelations(-2))
            tmp.extractAllFeats();
    }

    public void addTTRelationsBasedOnNormVals(){
        for(int i=0;i<timexList.size();i++){
            TimexTemporalNode t1 = timexList.get(i);
            for(int j=i+1;j<timexList.size();j++){
                TimexTemporalNode t2 = timexList.get(j);
                TemporalRelType relType = t1.compareTo(t2,dct);
                if(relType.getReltype()== TemporalRelType.relTypes.VAGUE
                        ||relType.getReltype()== TemporalRelType.relTypes.EQUAL)// todo now only deals with before/after relations
                    continue;
                TemporalRelation_TT tt = new TemporalRelation_TT(t1,t2,relType,this);
                getGraph().addRelNoDup(tt);
            }
        }
    }

    public void addEERelationsBasedOnETAndTT(boolean long_dist){
        // todo only add distance<=1; already fixed in loopIssue branch
        for(TemporalRelation_TT rel:getGraph().getAllTTRelations(-1)){
            if(!rel.isNull() && rel.getRelType().getReltype() != TemporalRelType.relTypes.VAGUE){
                TimexTemporalNode t1 = rel.getSourceNode();
                TimexTemporalNode t2 = rel.getTargetNode();
                List<EventTemporalNode> t1_events = new ArrayList<>();
                List<EventTemporalNode> t2_events = new ArrayList<>();
                for(TemporalNode tmp:getGraph().getNodesToThis(t1.getUniqueId())){
                    if(tmp instanceof EventTemporalNode){
                        TemporalRelation_ET et1 = getGraph().getETRelBetweenEventTimex(t1.getUniqueId(),tmp.getUniqueId());
                        if(et1!=null&&et1.getRelType().getReltype()== TemporalRelType.relTypes.EQUAL)
                            t1_events.add((EventTemporalNode) tmp);
                    }
                }
                for(TemporalNode tmp:getGraph().getNodesToThis(t2.getUniqueId())){
                    if(tmp instanceof EventTemporalNode){
                        TemporalRelation_ET et2 = getGraph().getETRelBetweenEventTimex(t2.getUniqueId(),tmp.getUniqueId());
                        if(et2!=null&&et2.getRelType().getReltype()== TemporalRelType.relTypes.EQUAL)
                            t2_events.add((EventTemporalNode) tmp);
                    }
                }
                for(EventTemporalNode e1 : t1_events){
                    for(EventTemporalNode e2: t2_events){
                        if(e1.isEqual(e2))
                            continue;
                        TemporalRelation_EE newEE = new TemporalRelation_EE(e1,e2,rel.getRelType(),this);
                        if(!long_dist&&Math.abs(newEE.getSentDiff()) > 1) continue;
                        getGraph().addRelNoDup(newEE);
                    }
                }
            }
        }
    }

    /*Evaluators*/

    public static void NaiveEvaluator(List<myTemporalDocument> doc_gold_list, List<myTemporalDocument> doc_pred_list, int verbose){
        System.out.println(myLogFormatter.startBlockLog("TEMPORAL DOCUMENTS NAIVE EVALUATOR"));

        System.out.println(myLogFormatter.startBlockLog("EVALUATING EVENT DETECTION"));
        NaiveEvaluator_EventDetection(doc_gold_list,doc_pred_list,verbose);
        System.out.println(myLogFormatter.endBlockLog("EVALUATING EVENT DETECTION"));

        System.out.println(myLogFormatter.startBlockLog("EVALUATING TEMPREL"));

        System.out.println(myLogFormatter.fullBlockLog("EVALUATING EVENT TEMPREL CLASSIFICATION (MODE=0)"));
        NaiveEvaluator_TempRelClassification(doc_gold_list,doc_pred_list,0,verbose);
        System.out.println(myLogFormatter.fullBlockLog("EVALUATING EVENT TEMPREL CLASSIFICATION (MODE=1)"));
        NaiveEvaluator_TempRelClassification(doc_gold_list,doc_pred_list,1,verbose);
        System.out.println(myLogFormatter.fullBlockLog("EVALUATING EVENT TEMPREL CLASSIFICATION (MODE=2)"));
        NaiveEvaluator_TempRelClassification(doc_gold_list,doc_pred_list,2,verbose);

        System.out.println(myLogFormatter.endBlockLog("TEMPORAL DOCUMENTS NAIVE EVALUATOR"));


    }

    public static void NaiveEvaluator_EventDetection(List<myTemporalDocument> doc_gold_list, List<myTemporalDocument> doc_pred_list, int verbose){
        PrecisionRecallManager eventDetectorEvaluator = new PrecisionRecallManager();
        PrecisionRecallManager eventDetectorEvaluatorDetail;
        if(doc_gold_list.size()!=doc_pred_list.size()){
            System.out.println("[WARNING] doc_gold_list and doc_pred_list don't match.");
            return;
        }
        for(int k=0;k<doc_gold_list.size();k++) {
            eventDetectorEvaluatorDetail = new PrecisionRecallManager();
            myTemporalDocument doc_gold = doc_gold_list.get(k);
            myTemporalDocument doc_pred = doc_pred_list.get(k);
            // check
            if(!doc_gold.getDocid().equals(doc_pred.getDocid())){
                System.out.println("[WARNING] doc_gold_list and doc_pred_list don't match.");
                return;
            }

            int tokenSize = doc_gold.getTextAnnotation().getTokens().length;
            for (int i = 0; i < tokenSize; i++) {
                EventTemporalNode e_gold = doc_gold.getEventFromTokenId(i);
                EventTemporalNode e_pred = doc_pred.getEventFromTokenId(i);
                String goldLabel = e_gold == null ? LABEL_NOT_ON_ANY_AXIS : LABEL_ON_MAIN_AXIS;
                String predLabel = e_pred == null ? LABEL_NOT_ON_ANY_AXIS : LABEL_ON_MAIN_AXIS;
                eventDetectorEvaluator.addPredGoldLabels(predLabel, goldLabel);
                eventDetectorEvaluatorDetail.addPredGoldLabels(predLabel, goldLabel);
            }
            if(verbose>1) {
                System.out.printf("--------#%d Doc: %s--------\n",k,doc_gold.getDocid());
                eventDetectorEvaluatorDetail.printPrecisionRecall(EventAxisPerceptronTrainer.AXIS_LABEL_TO_IGNORE);
                if (verbose > 2) {
                    System.out.println("----------CONFUSION MATRIX----------");
                    eventDetectorEvaluatorDetail.printConfusionMatrix();
                }
            }
        }
        System.out.printf("########Evaluation of %d documents########\n",doc_gold_list.size());
        eventDetectorEvaluator.printPrecisionRecall(EventAxisPerceptronTrainer.AXIS_LABEL_TO_IGNORE);
        if (verbose > 0) {
            System.out.println("----------CONFUSION MATRIX----------");
            eventDetectorEvaluator.printConfusionMatrix();
        }
    }

    public static void NaiveEvaluator_TempRelClassification(List<myTemporalDocument> doc_gold_list, List<myTemporalDocument> doc_pred_list, int mode, int verbose){
        String[] EE_IGNORE = new String[]{TemporalRelType.relTypes.VAGUE.getName(),TemporalRelType.relTypes.NULL.getName()};
        String[] ET_IGNORE = new String[]{TemporalRelType.relTypes.NULL.getName()};
        // mode: 0--default, 1--ignore gold is null, 2--1+relax vague
        PrecisionRecallManager eeTempRelClsEvaluator = new PrecisionRecallManager();
        PrecisionRecallManager eeTempRelClsEvaluatorDetail;
        PrecisionRecallManager etTempRelClsEvaluator = new PrecisionRecallManager();
        PrecisionRecallManager etTempRelClsEvaluatorDetail;
        if(doc_gold_list.size()!=doc_pred_list.size()){
            System.out.println("[WARNING] doc_gold_list and doc_pred_list don't match.");
            return;
        }
        for(int k=0;k<doc_gold_list.size();k++) {
            eeTempRelClsEvaluatorDetail = new PrecisionRecallManager();
            etTempRelClsEvaluatorDetail = new PrecisionRecallManager();
            myTemporalDocument doc_gold = doc_gold_list.get(k);
            myTemporalDocument doc_pred = doc_pred_list.get(k);
            // check
            if(!doc_gold.getDocid().equals(doc_pred.getDocid())){
                System.out.println("[WARNING] doc_gold_list and doc_pred_list don't match.");
                return;
            }

            // Get all EE pairs set
            HashSet<String> allEEPairs_str = new HashSet<>();// format: event_tokenId1 + ":" + event_tokenId2
            List<TemporalRelation_EE> allEE = doc_gold.getGraph().getAllEERelations(-1);
            allEE.addAll(doc_pred.getGraph().getAllEERelations(-1));
            for(TemporalRelation_EE ee:allEE){
                int tokId1 = ee.getSourceNode().getTokenId();
                int tokId2 = ee.getTargetNode().getTokenId();
                if(tokId1>tokId2) continue;
                allEEPairs_str.add(tokId1+":"+tokId2);
            }

            // Get all ET pairs set
            HashSet<String> allETPairs_str = new HashSet<>();// format: event_tokenId + ":" + timex_tokenSpan
            List<TemporalRelation_ET> allET = doc_gold.getGraph().getAllETRelations(0);
            allET.addAll(doc_pred.getGraph().getAllETRelations(0));
            for(TemporalRelation_ET et:allET){
                if(!et.isEventFirstInPair())
                    continue;
                int tokId = et.getEventNode().getTokenId();
                IntPair tokSpan = et.getTimexNode().getTokenSpan();
                allETPairs_str.add(tokId+":"+tokSpan.toString());
            }

            // Evaluate all EE pairs
            for(String ee_str:allEEPairs_str){
                String[] tmp = ee_str.split(":");
                int tokId1 = Integer.valueOf(tmp[0]);
                int tokId2 = Integer.valueOf(tmp[1]);
                TemporalRelType rel_gold = doc_gold.getEERelFromTokenIds(tokId1,tokId2);
                TemporalRelType rel_pred = doc_pred.getEERelFromTokenIds(tokId1,tokId2);
                if(mode>0&&rel_gold.isNull())
                    continue;
                if(mode==2){
                    if(rel_gold.getReltype() == TemporalRelType.relTypes.VAGUE
                            &&rel_pred.getReltype() != TemporalRelType.relTypes.VAGUE)
                        rel_gold = rel_pred;
                }
                eeTempRelClsEvaluator.addPredGoldLabels(rel_pred.getReltype().getName(), rel_gold.getReltype().getName());
                eeTempRelClsEvaluatorDetail.addPredGoldLabels(rel_pred.getReltype().getName(), rel_gold.getReltype().getName());
            }

            // Evaluate all ET pairs
            for(String et_str:allETPairs_str){
                String[] tmp = et_str.split(":");
                int tokId = Integer.valueOf(tmp[0]);
                String tokSpan = tmp[1];
                TemporalRelType rel_gold = doc_gold.getETRelFromTokenIdAndSpan(tokId,tokSpan);
                TemporalRelType rel_pred = doc_pred.getETRelFromTokenIdAndSpan(tokId,tokSpan);
                if(rel_gold.isNull()&&rel_pred.getReltype()== TemporalRelType.relTypes.VAGUE)
                    rel_gold = rel_pred;

                if(mode>0&&rel_gold.isNull())
                    continue;
                if(mode==2){
                    if(rel_gold.getReltype() == TemporalRelType.relTypes.VAGUE
                            &&rel_pred.getReltype() != TemporalRelType.relTypes.VAGUE)
                        rel_gold = rel_pred;
                }
                etTempRelClsEvaluator.addPredGoldLabels(rel_pred.getReltype().getName(), rel_gold.getReltype().getName());
                etTempRelClsEvaluatorDetail.addPredGoldLabels(rel_pred.getReltype().getName(), rel_gold.getReltype().getName());
            }
            if(verbose>1) {
                System.out.printf("--------#%d Doc: %s--------\n",k,doc_gold.getDocid());
                eeTempRelClsEvaluatorDetail.printPrecisionRecall(EE_IGNORE);
                etTempRelClsEvaluatorDetail.printPrecisionRecall(ET_IGNORE);
                if (verbose > 2) {
                    System.out.println("----------CONFUSION MATRIX----------");
                    eeTempRelClsEvaluatorDetail.printConfusionMatrix();
                    etTempRelClsEvaluatorDetail.printConfusionMatrix();
                }
            }
        }

        System.out.println(myLogFormatter.fullBlockLog(String.format("Evaluation of %d documents",doc_gold_list.size())));
        System.out.println(myLogFormatter.startBlockLog("Event-Event TempRels"));
        eeTempRelClsEvaluator.printPrecisionRecall(EE_IGNORE);
        if (verbose > 0) {
            System.out.println("----------CONFUSION MATRIX----------");
            eeTempRelClsEvaluator.printConfusionMatrix();
        }
        System.out.println(myLogFormatter.endBlockLog("Event-Event TempRels"));
        System.out.println(myLogFormatter.startBlockLog("Event-Timex TempRels"));
        etTempRelClsEvaluator.printPrecisionRecall(ET_IGNORE);
        if (verbose > 0) {
            System.out.println("----------CONFUSION MATRIX----------");
            etTempRelClsEvaluator.printConfusionMatrix();
        }
        System.out.println(myLogFormatter.endBlockLog("Event-Timex TempRels"));
    }

    public static void AwarenessEvaluator(List<myTemporalDocument> doc_gold_list, List<myTemporalDocument> doc_pred_list, int verbose){
        System.out.println(myLogFormatter.startBlockLog("EE Temporal Awareness"));

        System.out.println(myLogFormatter.fullBlockLog("EVALUATING EVENT TEMPREL CLASSIFICATION (MODE=0)"));
        AwarenessEvaluator_EETempRelClassification(doc_gold_list,doc_pred_list,0);
        System.out.println(myLogFormatter.fullBlockLog("EVALUATING EVENT TEMPREL CLASSIFICATION (MODE=1)"));
        AwarenessEvaluator_EETempRelClassification(doc_gold_list,doc_pred_list,1);
        System.out.println(myLogFormatter.fullBlockLog("EVALUATING EVENT TEMPREL CLASSIFICATION (MODE=2)"));
        AwarenessEvaluator_EETempRelClassification(doc_gold_list,doc_pred_list,2);

        System.out.println(myLogFormatter.endBlockLog("EE Temporal Awareness"));
    }

    public static double AwarenessEvaluator_EETempRelClassification(List<myTemporalDocument> doc_gold_list, List<myTemporalDocument> doc_pred_list, int mode){
        if(doc_gold_list.size()!=doc_pred_list.size()){
            System.out.println("[WARNING] doc_gold_list and doc_pred_list don't match.");
            return 0d;
        }
        int prec_corr = 0, prec_all = 0, recall_corr = 0, recall_all = 0;
        for(int k=0;k<doc_gold_list.size();k++) {
            myTemporalDocument doc_gold = doc_gold_list.get(k);
            myTemporalDocument doc_pred = doc_pred_list.get(k);
            myTemporalDocument doc_gold_reduced = new myTemporalDocument(doc_gold);
            doc_gold_reduced.getGraph().reduction();
            myTemporalDocument doc_pred_reduced = new myTemporalDocument(doc_pred);
            doc_pred_reduced.getGraph().reduction();
            // check
            if(!doc_gold.getDocid().equals(doc_pred.getDocid())){
                System.out.println("[WARNING] doc_gold_list and doc_pred_list don't match.");
                return 0d;
            }

            List<TemporalRelation_EE> allEE4prec = doc_pred_reduced.getGraph().getAllEERelations(-1);
            for(TemporalRelation_EE ee:allEE4prec){
                int tokId1 = ee.getSourceNode().getTokenId();
                int tokId2 = ee.getTargetNode().getTokenId();
                if(tokId1>tokId2) continue;
                TemporalRelType rel_gold = doc_gold.getEERelFromTokenIds(tokId1,tokId2);
                if(mode>0&&rel_gold.isNull())
                    continue;
                if(mode==2){
                    if(rel_gold.getReltype() == TemporalRelType.relTypes.VAGUE
                            &&ee.getRelType().getReltype() != TemporalRelType.relTypes.VAGUE)
                        rel_gold = ee.getRelType();
                }
                if(rel_gold.getReltype()==ee.getRelType().getReltype())
                    prec_corr++;
                prec_all++;
            }

            List<TemporalRelation_EE> allEE4recall = doc_gold_reduced.getGraph().getAllEERelations(-1);
            for(TemporalRelation_EE ee:allEE4recall){
                int tokId1 = ee.getSourceNode().getTokenId();
                int tokId2 = ee.getTargetNode().getTokenId();
                if(tokId1>tokId2) continue;
                TemporalRelType rel_gold = doc_pred.getEERelFromTokenIds(tokId1,tokId2);
                if(mode>0&&rel_gold.isNull())
                    continue;
                if(mode==2){
                    if(rel_gold.getReltype() == TemporalRelType.relTypes.VAGUE
                            &&ee.getRelType().getReltype() != TemporalRelType.relTypes.VAGUE)
                        rel_gold = ee.getRelType();
                }
                if(rel_gold.getReltype()==ee.getRelType().getReltype())
                    recall_corr++;
                recall_all++;
            }
        }
        System.out.printf("########Evaluation of %d documents########\n",doc_gold_list.size());
        double prec = 1.0*prec_corr/prec_all;
        double rec = 1.0*recall_corr/recall_all;
        double f1 = 2*prec*rec/(prec+rec);
        System.out.printf("Prec=%.4f, Recall=%.4f, F=%.4f\n",prec,rec,f1);
        return f1;
    }

    /*Getters and Setters*/
    public TextAnnotation getTextAnnotation() {
        return ta;
    }

    public List<EventTemporalNode> getEventList() {
        return eventList;
    }

    public List<TimexTemporalNode> getTimexList() {
        return timexList;
    }

    public String getDocid() {
        return docid;
    }

    public TemporalGraph getGraph() {
        return graph;
    }

    public void setDct(TimexTemporalNode dct) {
        this.dct = dct;
    }

    public TimexTemporalNode getDct() {
        return dct;
    }

    public EventTemporalNode getEventFromTokenId(int tokenId){
        if(map_tokenId2event==null||map_tokenId2event.size()==0) {
            map_tokenId2event = new HashMap<>();
            for(EventTemporalNode e:eventList){
                map_tokenId2event.put(e.getTokenId(),e);
            }
        }
        return map_tokenId2event.get(tokenId);
    }

    public TimexTemporalNode getTimexFromTokenSpan(String tokenSpanStr){
        if(map_tokenSpan2timex==null||map_tokenSpan2timex.size()==0) {
            map_tokenSpan2timex = new HashMap<>();
            for(TimexTemporalNode t:timexList){
                map_tokenSpan2timex.put(t.getTokenSpan().toString(),t);
            }
        }
        return map_tokenSpan2timex.get(tokenSpanStr);
    }

    public TemporalRelType getEERelFromTokenIds(int tokenId1, int tokenId2){
        EventTemporalNode e1 = getEventFromTokenId(tokenId1);
        EventTemporalNode e2 = getEventFromTokenId(tokenId2);
        if(e1==null||e2==null)
            return getNullTempRel();
        TemporalRelation ee_rel = graph.getRelBetweenNodes(e1.getUniqueId(),e2.getUniqueId());
        if(ee_rel==null)
            return getNullTempRel();
        return ee_rel.getRelType();
    }

    public TemporalRelType getETRelFromTokenIdAndSpan(int tokenId, String tokenSpanStr){
        EventTemporalNode event = getEventFromTokenId(tokenId);
        TimexTemporalNode timex = getTimexFromTokenSpan(tokenSpanStr);
        if(event==null||timex==null)
            return getNullTempRel();
        TemporalRelation et_rel = graph.getRelBetweenNodes(event.getUniqueId(),timex.getUniqueId());
        if(et_rel==null)
            return getNullTempRel();
        return et_rel.getRelType();
    }

    public static void main(String[] args) throws Exception{
        /*ResourceManager rm = new temporalConfigurator().getConfig("config/directory.properties");
        String dir = rm.getString("TBDense_Ser");
        List<TemporalDocument> docs = TempEval3Reader.deserialize(dir);
        myTemporalDocument doc = new myTemporalDocument(docs.get(0),1);

        HashMap<String,HashMap<Integer,String>> axisMap = readAxisMapFromCrowdFlower(rm.getString("CF_Axis"));
        doc.keepAnchorableEvents(axisMap.get(doc.getDocid()));
        HashMap<String,List<temprelAnnotationReader.CrowdFlowerEntry>> relMap = readTemprelFromCrowdFlower(rm.getString("CF_TempRel"));
        doc.loadRelationsFromMap(relMap.get(doc.getDocid()),1);
        System.out.println();*/
    }
}
