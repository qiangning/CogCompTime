package edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by chuchu on 12/19/17.
 */
public abstract class BinaryRelation<Node extends AugmentedNode> implements Serializable{
    private static final long serialVersionUID = 1238168453574063459L;
    private Node sourceNode, targetNode;
    private BinaryRelationType relType;
    private BinaryRelation<Node> relation_inverse;//assume all binary relations are two-way, i.e., if A has relation to B, there must be an inverse relation such that B has "inverse relation" to A.

    /*Constructors*/

    public BinaryRelation() {
    }

    public BinaryRelation(Node sourceNode, Node targetNode, BinaryRelationType relType) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.relType = relType;
    }

    /*Functions*/
    public abstract BinaryRelation<Node> inverse();
    public void reverse(){
        Node tmp = sourceNode;
        sourceNode = targetNode;
        targetNode = tmp;
        relType.reverse();
    }
    public abstract boolean isNull();

    @NotNull
    public BinaryRelation<Node> getInverse(){
        if(relation_inverse ==null) {
            relation_inverse = inverse();
            relation_inverse.setRelation_inverse(this);
        }
        return relation_inverse;
    }

    private boolean sameNodePair(BinaryRelation<Node> other){
        if(other==null) return false;
        if(sourceNode.isEqual(other.getSourceNode())&&targetNode.isEqual(other.getTargetNode())) return true;
        if(sourceNode.isEqual(other.getTargetNode())&&targetNode.isEqual(other.getSourceNode())) return true;
        return false;
    }
    public boolean isEqual(BinaryRelation<Node> other){
        return sameNodePair(other)&&isConsistent(other);
    }
    public boolean isConsistent(BinaryRelation<Node> other) {// return true if no conflicts
        if(!sameNodePair(other)) return true;
        if(sourceNode.isEqual(other.getSourceNode())&&relType.isEqual(other.getRelType())) return true;
        return relType.isEqual(other.getRelType().inverse());
    }

    /*Getters and Setters*/

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Node targetNode) {
        this.targetNode = targetNode;
    }

    public BinaryRelationType getRelType() {
        return relType;
    }

    public void setRelType(BinaryRelationType relType) {
        this.relType = relType;
    }

    public BinaryRelation<Node> getRelation_inverse() {
        return relation_inverse;
    }

    public void setRelation_inverse(BinaryRelation<Node> relation_inverse) {
        this.relation_inverse = relation_inverse;
    }
}
