package edu.illinois.cs.cogcomp.temporal.utils.GraphVisualizer;

public class vertex {
    private String uniqueid;
    private String text;
    private int colorId;

    public vertex(String uniqueid, String text, int colorId) {
        this.uniqueid = uniqueid;
        this.text = text;
        this.colorId = colorId;
    }

    public boolean equals(vertex v2){
        return uniqueid.equals(v2.uniqueid);
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public String getText() {
        return text;
    }

    public String toString4d3(){
        return String.format("name:\"%s\", color: %d", toString(),colorId);
    }

    public String toString(){
        return uniqueid+":"+text;
    }
}
