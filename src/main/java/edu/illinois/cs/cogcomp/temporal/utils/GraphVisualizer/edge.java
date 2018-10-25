package edu.illinois.cs.cogcomp.temporal.utils.GraphVisualizer;

public class edge {
    private int source;
    private int target;
    private String label;
    private int length;
    private int colorId;
    private String markerEnd;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public edge(int source, int target, String label, int length, int colorId, String markerEnd) {
        this.source = source;
        this.target = target;
        this.label = label;
        this.length = length;
        this.colorId = colorId;
        this.markerEnd = markerEnd;
    }

    public String toString4d3(){
        return String.format("source: %d, target: %d, label: \"%s\", len: %d, color: %d, markerend: \"%s\"",source,target,label,length, colorId,markerEnd);
    }
}
