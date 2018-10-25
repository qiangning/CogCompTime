package edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph;

import edu.illinois.cs.cogcomp.temporal.utils.ListSampler;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by chuchu on 12/20/17.
 */
public class AugmentedGraph<Node extends AugmentedNode, Relation extends BinaryRelation<Node>> implements Serializable {
    private static final long serialVersionUID = 5805879840460083874L;
    protected HashMap<String,Node> nodeMap;
    protected List<Relation> relations, relations_directed;
    protected HashMap<String,List<Relation>> nodeInRelationMap, nodeOutRelationMap;

    // TO-DO: graph closure using transitivity triplets defined in BinaryRelation

    /*Constructors*/

    public AugmentedGraph() {
        this.nodeMap = new HashMap<>();
        this.relations = new ArrayList<>();
        this.relations_directed = new ArrayList<>();
        nodeInRelationMap = new HashMap<>();
        nodeOutRelationMap = new HashMap<>();
    }

    public AugmentedGraph(List<Node> nodeMap, List<Relation> relations) {
        this.nodeMap = new HashMap<>();
        for(Node node:nodeMap) {
            int ret = addNodeNoDup(node);
            if(ret!=1){
                System.out.printf("[WARNING]: node %s is invalid or exists duplicates in nodeMap input to AugmentedGraph\n",node.getUniqueId());
                System.out.printf("[ERROR CODE]: %d\n",ret);
                System.out.println(node.toString());
            }
        }

        this.relations = new ArrayList<>();
        this.relations_directed = new ArrayList<>();
        for(Relation rel : relations){
            int ret = addRelNoDup(rel);
            if(ret!=1){
                System.out.println("[WARNING]: add relation failed.");
                System.out.printf("[ERROR CODE]: %d\n",ret);
                System.out.println(rel.toString());
            }
        }
    }

    /*Functions*/
    public int addNodeNoDup(Node newnode){
        if(newnode==null){// add null
            return -1;
        }
        if(nodeMap.containsKey(newnode.getUniqueId())){//duplicate
            return 0;
        }
        nodeMap.put(newnode.getUniqueId(),newnode); //success
        return 1;
    }

    public int addRelNoDup(Relation newrel){
        if(newrel==null||newrel.isNull()){//invalid newrel
            return -1;
        }
        Node sourceNode = getNode(newrel.getSourceNode().getUniqueId());
        Node targetNode = getNode(newrel.getTargetNode().getUniqueId());
        if(sourceNode==null||targetNode==null){// newrel contains nodes that don't exist in nodeMap.
            return -2;
        }
        BinaryRelation<Node> rel = getRelBetweenNodes(sourceNode.getUniqueId(),targetNode.getUniqueId());
        if(rel!=null&&!rel.isNull()){// newrel already exists in graph
            return 0;
        }
        // success
        addNewRelation(newrel);
        return 1;
    }

    public boolean isDirectedRelation(Relation rel){
        return rel.getSourceNode().getUniqueId().compareTo(rel.getTargetNode().getUniqueId())<0;
    }

    public boolean dropNode(String nodeUniqueId){
        // nodeMap
        if(!nodeMap.containsKey(nodeUniqueId))
            return false;
        nodeMap.remove(nodeUniqueId);
        // relations
        List<Relation> newRelations = new ArrayList<>();
        List<Relation> newRelations_directed = new ArrayList<>();
        for(Relation rel:relations){
            if(rel.getSourceNode().getUniqueId().equals(nodeUniqueId)
                    ||rel.getTargetNode().getUniqueId().equals(nodeUniqueId))
                continue;
            newRelations.add(rel);
            if(isDirectedRelation(rel))
                newRelations_directed.add(rel);
        }
        relations = newRelations;
        relations_directed = newRelations_directed;
        // nodeOutRelationMap and nodeInRelationMap
        nodeInRelationMap.remove(nodeUniqueId);
        nodeOutRelationMap.remove(nodeUniqueId);
        return true;
    }

    public void dropAllNodes(){
        nodeMap = new HashMap<>();
        dropAllRelations();
    }

    public boolean dropRelation(String uniqueId1, String uniqueId2){
        Relation rel2drop = getRelBetweenNodes(uniqueId1,uniqueId2);
        try {
            Relation rel2drop_inverse = (Relation) rel2drop.getInverse();
            relations.remove(rel2drop);
            relations.remove(rel2drop_inverse);
            if(isDirectedRelation(rel2drop))
                relations_directed.remove(rel2drop);
            else
                relations_directed.remove(rel2drop_inverse);
            nodeOutRelationMap.get(uniqueId1).remove(rel2drop);
            nodeOutRelationMap.get(uniqueId2).remove(rel2drop_inverse);
            nodeInRelationMap.get(uniqueId1).remove(rel2drop_inverse);
            nodeInRelationMap.get(uniqueId2).remove(rel2drop);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void dropAllRelations(){
        relations = new ArrayList<>();
        relations_directed = new ArrayList<>();
        nodeInRelationMap = new HashMap<>();
        nodeOutRelationMap = new HashMap<>();
    }

    private void addNewRelation(Relation rel){
        Relation rel_inverse = (Relation) rel.getInverse();
        relations.add(rel);
        relations.add(rel_inverse);
        if(isDirectedRelation(rel))
            relations_directed.add(rel);
        else
            relations_directed.add(rel_inverse);
        String uniqueId1 = rel.getSourceNode().getUniqueId();
        String uniqueId2 = rel.getTargetNode().getUniqueId();
        addRelationToMap(uniqueId1,rel,nodeOutRelationMap);
        addRelationToMap(uniqueId2,rel,nodeInRelationMap);
        addRelationToMap(uniqueId1,rel_inverse,nodeInRelationMap);
        addRelationToMap(uniqueId2,rel_inverse,nodeOutRelationMap);
    }

    private void addRelationToMap(String uniqueId, Relation rel, HashMap<String,List<Relation>> map){
        if(!map.containsKey(uniqueId))
            map.put(uniqueId,new ArrayList<>());
        map.get(uniqueId).add(rel);
    }

    public void downSamplingRelations(double sr, int seed){
        ListSampler<Relation> listSampler = new ListSampler<>(element -> false);
        List<Relation> relations2remove = listSampler.ListSampling(relations_directed,1-sr,new Random(seed));
        for(Relation rel:relations2remove){
            dropRelation(rel.getSourceNode().getUniqueId(),rel.getTargetNode().getUniqueId());
        }
    }

    /*Getters and Setters*/

    @Nullable
    public Node getNode(String uniqueId){
        return nodeMap.getOrDefault(uniqueId,null);
    }
    @Nullable
    public Relation getRelBetweenNodes(String uniqueId1, String uniqueId2){
        List<Relation> id1_outmap = nodeOutRelationMap.getOrDefault(uniqueId1, new ArrayList<>());
        for(Relation rel:id1_outmap){
            if(rel.getTargetNode().getUniqueId().equals(uniqueId2)){
                return rel;
            }
        }
        return null;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public int inDegreeOf(String uniqueid){
        if(nodeInRelationMap.containsKey(uniqueid))
            return nodeInRelationMap.get(uniqueid).size();
        return -1;
    }

    public int outDegreeOf(String uniqueid){
        if(nodeOutRelationMap.containsKey(uniqueid))
            return nodeOutRelationMap.get(uniqueid).size();
        return -1;
    }

    public int degreeOf(String uniqueid){
        return inDegreeOf(uniqueid)+outDegreeOf(uniqueid);
    }

    public HashMap<String, List<Relation>> getNodeInRelationMap() {
        return nodeInRelationMap;
    }

    public HashMap<String, List<Relation>> getNodeOutRelationMap() {
        return nodeOutRelationMap;
    }

    public List<Node> getNodesToThis(String uniqueId){
        List<Node> ret = new ArrayList<>();
        List<Relation> inRelations = nodeInRelationMap.getOrDefault(uniqueId, new ArrayList<>());
        for(Relation rel:inRelations){
            ret.add(rel.getSourceNode());
        }
        return ret;
    }
    public List<Node> getNodesFromThis(String uniqueId){
        List<Node> ret = new ArrayList<>();
        List<Relation> outRelations = nodeOutRelationMap.getOrDefault(uniqueId, new ArrayList<>());
        for(Relation rel:outRelations){
            ret.add(rel.getTargetNode());
        }
        return ret;
    }
}
