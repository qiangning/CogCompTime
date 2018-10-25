package edu.illinois.cs.cogcomp.temporal.readers.RED;

import org.jetbrains.annotations.Nullable;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RedDataset {
    private List<RedDoc> dataset;

    public RedDataset(){
        dataset = new ArrayList<>();
    }
    public void addDoc(RedDoc doc){
        dataset.add(doc);
    }
    public int getNum_doc(){
        return dataset.size();
    }
    public int getNum_event(){
        int n = 0;
        for(RedDoc doc:dataset){
            n+=doc.getEvents().size();
        }
        return n;
    }

    public List<RedDoc> getDataset() {
        return dataset;
    }
    @Nullable
    public RedDoc getDocFromId(String docid){
        for(RedDoc doc:dataset){
            if(doc.getDocId().equals(docid))
                return doc;
        }
        return null;
    }
    public void Render4Crowdflower(String outputfile,String color){
        StringBuilder sb = new StringBuilder();
        sb.append("\"docid\",\"eventid\",\"verb\",\"bodytext\"\n");
        for(RedDoc doc:dataset){
            sb.append(doc.Render4Crowdflower(color));
        }
        try (PrintStream out = new PrintStream(new FileOutputStream(outputfile))) {
            out.print(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
