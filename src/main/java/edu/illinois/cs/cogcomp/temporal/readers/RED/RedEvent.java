package edu.illinois.cs.cogcomp.temporal.readers.RED;

public class RedEvent{
    protected class IntPair{
        private int first, second;

        public IntPair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }
    private String polarity,contextualModality;
    private IntPair charSpan;//right exclusive
    private String text;
    private int id;
    private RedDoc doc;

    private static String nodeType = "RedEvent";

    // Constructors
    public RedEvent(RedDoc doc, int id, int charStart, int charEnd, String polarity, String contextualModality) {
        this.doc = doc;
        this.id = id;
        charSpan = new IntPair(charStart,charEnd);
        if(charStart<0||charEnd>=doc.getBodyText().length()){
            System.out.println("Invalid char spans.");
        }
        this.text = doc.getBodyText().substring(charStart,charEnd);
        this.polarity = polarity;
        this.contextualModality = contextualModality;
    }



    // other functions
    public String toString() {
        return String.format("Doc=%s, id=%d ([%d,%d)): %s, POLARITY=%s, MODALITY=%s\n",
                doc.getDocId(),id,charSpan.getFirst(),charSpan.getSecond(),text,polarity,contextualModality);
    }

    public String getPolarity() {
        return polarity;
    }

    public String getContextualModality() {
        return contextualModality;
    }

    public IntPair getCharSpan() {
        return charSpan;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public RedDoc getDoc() {
        return doc;
    }

    public static String getNodeType() {
        return nodeType;
    }
    public String Render4Crowdflower(String color){
        int charStart = charSpan.getFirst(), charEnd = charSpan.getSecond();
        StringBuilder sb = new StringBuilder();
        String left = doc.getBodyText().substring(0,charStart).replaceAll("\n"," ").replaceAll("\""," ");
        left = left.substring(left.lastIndexOf(">")+1,left.length());
        String right = doc.getBodyText().substring(charEnd).replaceAll("\n"," ").replaceAll("\""," ");
        int tmp = right.indexOf("<");
        right = right.substring(0,tmp>0?tmp:right.length());
        sb.append("<p>");
        sb.append(left);
        sb.append(String.format("<span style='color:%s;'><strong>%s</strong></span>",color,text));
        sb.append(right);
        sb.append("</p>");
        return sb.toString();
    }
}
