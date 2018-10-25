package edu.illinois.cs.cogcomp.temporal.datastruct.Temporal;

import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
import edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph.AugmentedNode;

import java.util.HashSet;

/**
 * Created by chuchu on 12/20/17.
 */
public class TemporalNode extends AugmentedNode{
    protected int sentId = -1;
    protected TextAnnotation ta;
    protected HashSet<TemporalNode> equivalentNodes = new HashSet<>();//valid only when equivalentNodeHead=self
    protected TemporalNode equivalentNodeHead;

    public TemporalNode(int nodeId, String nodeType, String text, int sentId) {
        super(nodeId, nodeType, text);
        this.sentId = sentId;
        equivalentNodeHead = this;
    }

    public TemporalNode(TemporalNode other){
        // Note TextAnnotation is not deeply copied
        // this will also lose info about equivalentNodes
        this(other.nodeId,other.nodeType,other.text,other.sentId);
        ta = other.ta;
    }

    public int getSentId() {
        return sentId;
    }

    public TextAnnotation getTa() {
        return ta;
    }

    public void addEquivalentNode(TemporalNode node){
        equivalentNodes.add(node);
    }

    public void setEquivalentNodeHead(TemporalNode equivalentNodeHead) {
        this.equivalentNodeHead = equivalentNodeHead;
    }

    public TemporalNode getEquivalentNodeHead() {
        return equivalentNodeHead;
    }

    public static void setEquivalentNodes(TemporalNode n1, TemporalNode n2){
        if(n1.compare(n2)>0){// make sure n1 < n2
            TemporalNode n = n1;
            n1 = n2;
            n2 = n;
        }
        while(!n1.getEquivalentNodeHead().isEqual(n1))
            n1 = n1.getEquivalentNodeHead();
        n1.addEquivalentNode(n2);
        n2.setEquivalentNodeHead(n1);
    }

    public static void main(String[] args) {
        TemporalNode n1 = new TemporalNode(1,"test","",0);
        TemporalNode n2 = new TemporalNode(2,"test","",0);
        TemporalNode n3 = new TemporalNode(3,"test","",0);
        TemporalNode n4 = new TemporalNode(4,"test","",0);
        setEquivalentNodes(n1,n2);
        setEquivalentNodes(n1,n3);
        setEquivalentNodes(n2,n4);
    }
}
