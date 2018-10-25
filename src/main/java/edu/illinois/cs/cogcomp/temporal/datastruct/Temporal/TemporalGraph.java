package edu.illinois.cs.cogcomp.temporal.datastruct.Temporal;

import edu.illinois.cs.cogcomp.core.io.IOUtils;
import edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph.AugmentedGraph;
import edu.illinois.cs.cogcomp.temporal.utils.GraphVisualizer.GraphJavaScript;
import edu.illinois.cs.cogcomp.temporal.utils.myLogFormatter;
import org.jetbrains.annotations.Nullable;
import org.jgrapht.alg.TransitiveClosure;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TemporalGraph extends AugmentedGraph<TemporalNode,TemporalRelation>{
    /*Variables*/
    protected myTemporalDocument doc;
    /*Constructors*/
    public TemporalGraph(myTemporalDocument doc) {
        super();
        this.doc = doc;
    }

    public TemporalGraph(List<TemporalNode> nodeMap, List<TemporalRelation> relations) {
        super(nodeMap, relations);
    }

    public TemporalGraph(TemporalGraph other){
        super();
        doc = null;
        for(String str:other.nodeMap.keySet()) {
            TemporalNode node = other.nodeMap.get(str);
            if(node instanceof EventTemporalNode)
                nodeMap.put(str, new EventTemporalNode((EventTemporalNode)node,doc));
            else if(node instanceof TimexTemporalNode)
                nodeMap.put(str, new TimexTemporalNode((TimexTemporalNode)node));
            else{
                System.out.println("[WARNING] unexpected type of nodes (Event/Timex).");
                nodeMap.put(str, new TemporalNode(node));
            }
        }
        for(TemporalRelation rel:other.getRelations()){
            if(rel instanceof TemporalRelation_EE)
                addRelNoDup(new TemporalRelation_EE((EventTemporalNode)getNode(rel.getSourceNode().getUniqueId()),
                        (EventTemporalNode)getNode(rel.getTargetNode().getUniqueId()),
                        new TemporalRelType(rel.getRelType()),
                        doc));
            else if(rel instanceof TemporalRelation_ET){
                addRelNoDup(new TemporalRelation_ET((EventTemporalNode)getNode(rel.getSourceNode().getUniqueId()),
                        (TimexTemporalNode)getNode(rel.getTargetNode().getUniqueId()),
                        new TemporalRelType(rel.getRelType()),
                        doc));
            }
            else if(rel instanceof TemporalRelation_TT){
                addRelNoDup(new TemporalRelation_TT((TimexTemporalNode)getNode(rel.getSourceNode().getUniqueId()),
                        (TimexTemporalNode)getNode(rel.getTargetNode().getUniqueId()),
                        new TemporalRelType(rel.getRelType()),
                        doc));
            }
            else{
                System.out.println("[WARNING] unexpected type of temporal relations (EE/ET/TT).");
            }
        }
    }

    /*Functions*/

    private void groupEqualNodes(){
        for(TemporalRelation rel:relations_directed){
            if(rel.getRelType().getReltype()==TemporalRelType.relTypes.EQUAL){
                TemporalNode.setEquivalentNodes(rel.getSourceNode(),rel.getTargetNode());
            }
        }
    }

    private void addRelations4EqualGroupNodes(){
        for(TemporalNode node:nodeMap.values()){
            if(node.equivalentNodes.size()>0){
                String v1 = node.getUniqueId();
                for(TemporalNode node2 : node.equivalentNodes){
                    String v2 = node2.getUniqueId();
                    setRelBetweenNodes(v1,v2,new TemporalRelType(TemporalRelType.relTypes.EQUAL));
                }
            }
        }
    }

    private DirectedAcyclicGraph<String, DefaultEdge> constructJGRAPHT(){
        DirectedAcyclicGraph<String, DefaultEdge> directedGraph = new DirectedAcyclicGraph<>(DefaultEdge.class);
        for(String nodeId:nodeMap.keySet()){
            directedGraph.addVertex(nodeId);
        }
        for(TemporalRelation rel:relations_directed){
            TemporalNode n1 = rel.getSourceNode().getEquivalentNodeHead();
            TemporalNode n2 = rel.getTargetNode().getEquivalentNodeHead();
            String v1 = n1.getUniqueId();
            String v2 = n2.getUniqueId();
            if(rel.getRelType().getReltype()==TemporalRelType.relTypes.BEFORE){
                directedGraph.addEdge(v1,v2);
            }
            else if(rel.getRelType().getReltype()==TemporalRelType.relTypes.AFTER){
                try {
                    directedGraph.addEdge(v2, v1);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return directedGraph;
    }

    public List<EventTemporalNode> convert2chain(){
        List<EventTemporalNode> ret = new ArrayList<>();
        // todo do ee first
        List<TemporalRelation_ET> allET = getAllETRelations(0);
        dropAllETRelations();
        dropAllTTRelations();

        // group equal nodes
        groupEqualNodes();

        // construct graph
        DirectedAcyclicGraph<String, DefaultEdge> directedGraph = constructJGRAPHT();

        // closure
        TransitiveClosure.INSTANCE.closeDirectedAcyclicGraph(directedGraph);

        // convert to a chain
        for(TemporalNode node:nodeMap.values()){
            if(node instanceof EventTemporalNode)
                ret.add((EventTemporalNode)node);
        }
        ret.sort(new Comparator<EventTemporalNode>() {
            @Override
            public int compare(EventTemporalNode e1, EventTemporalNode e2) {
                if(directedGraph.getEdge(e1.getUniqueId(),e2.getUniqueId())!=null)
                    return -1;
                if(directedGraph.getEdge(e2.getUniqueId(),e1.getUniqueId())!=null)
                    return 1;
                if(e1.getTokenId()<e2.getTokenId())
                    return -1;
                if(e1.getTokenId()>e2.getTokenId())
                    return 1;
                return 0;
            }
        });
        // add back ET edges
        for(TemporalRelation_ET et:allET){
            if(et.getRelType().getReltype()== TemporalRelType.relTypes.EQUAL)
                addRelNoDup(et);
        }
        return ret;
    }

    public void reduction(){
        // todo do ee first
        List<TemporalRelation_ET> allET = getAllETRelations(0);
        dropAllETRelations();
        dropAllTTRelations();

        // group equal nodes
        groupEqualNodes();

        // construct graph
        DirectedAcyclicGraph<String, DefaultEdge> directedGraph = constructJGRAPHT();

        // reduction
        TransitiveReduction.INSTANCE.reduce(directedGraph);

        // remove redundant edges
        List<TemporalRelation> reducedRelations = new ArrayList<>();
        for(DefaultEdge e:directedGraph.edgeSet()){
            String v1 = directedGraph.getEdgeSource(e);
            String v2 = directedGraph.getEdgeTarget(e);
            TemporalRelation rel = getRelBetweenNodes(v1,v2);
            reducedRelations.add(rel);
        }
        dropAllEERelations();
        for(TemporalRelation rel:reducedRelations)
            addRelNoDup(rel);

        // add back equal edges
        addRelations4EqualGroupNodes();

        // add back ET edges
        for(TemporalRelation_ET et:allET){
            if(et.getRelType().getReltype()== TemporalRelType.relTypes.EQUAL)
                addRelNoDup(et);
        }
    }

    // todo graph satuartion

    public void graphVisualization(String htmlDir){
        IOUtils.mkdir(htmlDir);
        String fname = htmlDir+ File.separator+doc.getDocid()+".html";
        GraphJavaScript graphJavaScript = new GraphJavaScript(fname);
        for(String nodeid:nodeMap.keySet()){
            TemporalNode node = nodeMap.get(nodeid);
            if(degreeOf(node.getUniqueId())<=0)
                continue;
            int colorId = node instanceof EventTemporalNode? 1:2;
            graphJavaScript.addVertex(nodeid,node.getText(),colorId);
        }
        graphJavaScript.sortVertexes();
        for(TemporalRelation rel:relations_directed){
            String id1 = rel.getSourceNode().getUniqueId();
            String id2 = rel.getTargetNode().getUniqueId();
            TemporalRelType.relTypes reltype = rel.getRelType().getReltype();
            int len = rel.getSentDiff()+1;
            if(rel instanceof TemporalRelation_EE)
                len *= 2;
            int colorId = rel instanceof TemporalRelation_EE? 1:2;
            String markerEnd = rel instanceof TemporalRelation_EE?"arrowhead":"";
            switch (reltype.getName().toLowerCase()){
                case "before":
                    graphJavaScript.addEdge(id1,id2,"",len,colorId,markerEnd);
                    break;
                case "after":
                    graphJavaScript.addEdge(id2,id1,"",len,colorId,markerEnd);
                    break;
                case "equal":
                    graphJavaScript.addEdge(id1,id2,"",len,colorId,markerEnd);
                    break;
            }
        }
        graphJavaScript.createJS();
    }

    public void chainVisualization(String txtDir){
        IOUtils.mkdir(txtDir);
        String fname = txtDir+ File.separator+doc.getDocid()+".txt";
        List<EventTemporalNode> events = convert2chain();
        try{
            PrintStream ps = new PrintStream(new File(fname));
            ps.println("|");
            int cnt = 0;
            for(EventTemporalNode e:events) {
                ps.print(e.interpret());
                ps.println("|");
                cnt++;
            }
            ps.println("V\n\nTime Axis");
            if(cnt==0)
                ps.println("(No main-axis events were found in the given text)");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dropAllEERelations(){
        List<TemporalRelation_EE> allEE = getAllEERelations(-1);
        for(TemporalRelation_EE ee:allEE){
            dropRelation(ee.getSourceNode().getUniqueId(),ee.getTargetNode().getUniqueId());
        }
    }

    public void dropAllETRelations(){
        List<TemporalRelation_ET> allET = getAllETRelations(-2);
        for(TemporalRelation_ET et:allET){
            dropRelation(et.getSourceNode().getUniqueId(),et.getTargetNode().getUniqueId());
        }
    }

    public void dropAllTTRelations(){
        List<TemporalRelation_TT> allTT = getAllTTRelations(-1);
        for(TemporalRelation_TT tt:allTT){
            dropRelation(tt.getSourceNode().getUniqueId(),tt.getTargetNode().getUniqueId());
        }
    }

    /*Getters and Setters*/
    @Nullable
    public EventTemporalNode getEventNode(String uniqueId){
        TemporalNode node = getNode(uniqueId);
        if(node instanceof EventTemporalNode)
            return (EventTemporalNode) node;
        return null;
    }

    @Nullable
    public TimexTemporalNode getTimexNode(String uniqueId){
        TemporalNode node = getNode(uniqueId);
        if(node instanceof TimexTemporalNode)
            return (TimexTemporalNode) node;
        return null;
    }

    @Override
    public TemporalRelation getRelBetweenNodes(String uniqueId1, String uniqueId2){
        return super.getRelBetweenNodes(uniqueId1,uniqueId2);
    }

    @Nullable
    public TemporalRelation_EE getEERelBetweenEvents(String uniqueId1, String uniqueId2){
        if(getEventNode(uniqueId1)==null||getEventNode(uniqueId2)==null) return null;
        TemporalRelation rel = getRelBetweenNodes(uniqueId1,uniqueId2);
        if(rel==null||rel.isNull())
            return null;
        if(rel instanceof TemporalRelation_EE)
            return (TemporalRelation_EE)rel;
        System.out.println("[WARNING] Getting EE Relation Unexpectedly Failed. Null is returned.");
        return null;
    }

    @Nullable
    public TemporalRelation_ET getETRelBetweenEventTimex(String uniqueId1, String uniqueId2){
        TemporalNode n1 = getNode(uniqueId1);
        TemporalNode n2 = getNode(uniqueId2);
        if(n1 instanceof EventTemporalNode && n2 instanceof EventTemporalNode) return null;
        if(n1 instanceof TimexTemporalNode && n2 instanceof TimexTemporalNode) return null;
        TemporalRelation rel = getRelBetweenNodes(uniqueId1,uniqueId2);
        if(rel==null||rel.isNull())
            return null;
        if(rel instanceof TemporalRelation_ET)
            return (TemporalRelation_ET)rel;
        System.out.println("[WARNING] Getting ET Relation Unexpectedly Failed. Null is returned.");
        return null;
    }

    public boolean setRelBetweenNodes(String uniqueId1, String uniqueId2, TemporalRelType relType){
        if(relType==null||relType.isNull()) {
            dropRelation(uniqueId1, uniqueId2);
            return true;
        }
        TemporalRelation temporalRelation = getRelBetweenNodes(uniqueId1,uniqueId2);
        if(temporalRelation==null) {
            TemporalNode n1 = getNode(uniqueId1);
            TemporalNode n2 = getNode(uniqueId2);
            if(n1==null||n2==null)
                return false;
            if(n1 instanceof EventTemporalNode && n2 instanceof EventTemporalNode)
                temporalRelation = new TemporalRelation_EE((EventTemporalNode)n1,(EventTemporalNode)n2,relType,doc);
            else if(n1 instanceof TimexTemporalNode && n2 instanceof TimexTemporalNode)
                temporalRelation = new TemporalRelation_TT((TimexTemporalNode)n1,(TimexTemporalNode)n2,relType,doc);
            else
                temporalRelation = new TemporalRelation_ET(n1,n2,relType,doc);
            addRelNoDup(temporalRelation);
        }
        else
            temporalRelation.setRelType(relType);
        return true;
    }

    public List<TemporalRelation_EE> getAllEERelations(int sentDiff){
        // All EE
        // - source should be before target
        // - only sentDiff can be kept (sentDiff<0 means this option is inactive)
        List<TemporalRelation> allRelations = getRelations();
        List<TemporalRelation_EE> allEERelations = new ArrayList<>();
        for(TemporalRelation rel:allRelations){
            if(rel.getSourceNode() instanceof EventTemporalNode
                    && rel.getTargetNode() instanceof EventTemporalNode){
                TemporalRelation_EE ee_rel = (TemporalRelation_EE) rel;
                if(ee_rel.getSourceNode().getTokenId()<ee_rel.getTargetNode().getTokenId()&&
                        (sentDiff<0||sentDiff==ee_rel.getSentDiff()))
                    allEERelations.add(ee_rel);
            }
        }
        return allEERelations;
    }

    public List<TemporalRelation_TT> getAllTTRelations(int sentDiff){
        // All TT
        // - source should be before target
        // - only sentDiff can be kept (sentDiff<0 means this option is inactive)
        List<TemporalRelation> allRelations = getRelations();
        List<TemporalRelation_TT> allTTRelations = new ArrayList<>();
        for(TemporalRelation rel:allRelations){
            if(rel.getSourceNode() instanceof TimexTemporalNode
                    && rel.getTargetNode() instanceof TimexTemporalNode){
                TemporalRelation_TT tt_rel = (TemporalRelation_TT) rel;
                if(tt_rel.isSourceFirstInText()&&
                        (sentDiff<0||sentDiff==tt_rel.getSentDiff()))
                    allTTRelations.add(tt_rel);
            }
        }
        return allTTRelations;
    }

    public List<TemporalRelation_ET> getAllETRelations(int sentDiff){
        // All ET
        // - E should be before T in pair
        // - only sentDiff can be kept (sentDiff=-1 means E-DCT;sentDiff=-2 means everything)
        List<TemporalRelation> allRelations = getRelations();
        List<TemporalRelation_ET> allETRelations = new ArrayList<>();
        for(TemporalRelation rel:allRelations){
            if(rel.getSourceNode() instanceof EventTemporalNode
                    && rel.getTargetNode() instanceof TimexTemporalNode){
                TemporalRelation_ET et_rel = (TemporalRelation_ET) rel;
                if(sentDiff==-2
                        ||sentDiff==-1&&et_rel.getTimexNode().isDCT()
                        ||sentDiff==et_rel.getSentDiff()){
                    allETRelations.add(et_rel);
                }
            }
        }
        return allETRelations;
    }
}
