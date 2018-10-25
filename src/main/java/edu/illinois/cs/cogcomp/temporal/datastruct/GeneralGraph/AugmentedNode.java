package edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph;

import java.io.Serializable;

public abstract class AugmentedNode implements Serializable {
    private static final long serialVersionUID = 5662840974130314900L;
    protected int nodeId;
    protected String nodeType;
    protected String text;

    /*Constructors*/

    public AugmentedNode() {
    }

    public AugmentedNode(AugmentedNode other){
        nodeId = other.nodeId;
        nodeType = other.nodeType;
        text = other.text;
    }

    public AugmentedNode(int nodeId, String nodeType, String text) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.text = text;
    }

    /*Functions*/
    public boolean isEqual(AugmentedNode other){
        return this.equals(other)||
                other!=null&&getUniqueId().equals(other.getUniqueId());
    }

    public int compare(AugmentedNode other){
        return getUniqueId().compareTo(other.getUniqueId());
    }

    /*Getters and Setters*/

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUniqueId(){return getUniqueId(nodeType,nodeId);}

    public static String getUniqueId(String nodeType, int nodeId){
        return nodeType+nodeId;
    }
}
