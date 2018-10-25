package edu.illinois.cs.cogcomp.temporal.readers.RED;

import java.util.ArrayList;
import java.util.List;

public class RedDoc {
    private String bodyText;
    private String docId;
    private List<RedEvent> events = new ArrayList<>();

    public RedDoc(String bodyText, String docId) {
        this.bodyText = bodyText;
        this.docId = docId;
    }

    public void addEvent(RedEvent event){
        events.add(event);
    }

    public List<RedEvent> getEvents() {
        return events;
    }

    public String getBodyText() {
        return bodyText;
    }

    public String getDocId() {
        return docId;
    }
    public RedEvent getEventFromId(int eventid){
        for(RedEvent e:events){
            if(e.getId()==eventid)
                return e;
        }
        return null;
    }
    public String Render4Crowdflower(String color){
        StringBuilder sb = new StringBuilder();
        for(RedEvent e:events){
            sb.append(String.format("\"%s\",\"%d\",\"%s\",\"%s\"\n",docId,e.getId(),e.getText(),e.Render4Crowdflower("red")));
        }
        return sb.toString();
    }
}
