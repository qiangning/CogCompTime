package edu.illinois.cs.cogcomp.temporal.readers.RED;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RedReader_Basic {
    private String source_dir, ann_dir;
    private RedDataset redDataset;

    public RedReader_Basic(String source_dir, String ann_dir) {
        this.source_dir = source_dir;
        this.ann_dir = ann_dir;
        redDataset = new RedDataset();
    }

    public void readSource(){
        File folder = new File(source_dir);
        File[] listoffiles = folder.listFiles();
        if(listoffiles==null)
            return;
        for(int i=0;i<listoffiles.length;i++){
            File file = listoffiles[i];
            if(!file.isFile())
                continue;
            // only read all the lines
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                String line;
                while((line = br.readLine())!=null){
                    sb.append(line);
                    sb.append("\n");
                }
                br.close();
            }
            catch (Exception e){e.printStackTrace();}
            redDataset.addDoc(new RedDoc(sb.toString(),file.getName()));
        }
    }
    public void readAnnotation(){
        for(int i = 0; i< redDataset.getNum_doc(); i++){
            RedDoc doc = redDataset.getDataset().get(i);
            try{
                BufferedReader br = new BufferedReader(new FileReader(ann_dir+File.separator+doc.getDocId()+".RED-Relation.gold.completed.xml"));
                String line;
                int charStart,charEnd, id=0;
                String polarity,contextualModality;
                while((line = br.readLine())!=null){
                    if(line.contains("<span>")){
                        String span_str = readContent(line,"span");
                        String[] pair_str = span_str.split(",");
                        charStart = Integer.valueOf(pair_str[0]);
                        charEnd = Integer.valueOf(pair_str[1]);

                        line = moveToNextMarker(br,"<type>");
                        if(line==null) continue;
                        String type = readContent(line,"type");
                        if(!type.equals("EVENT")){
                            moveToNextMarker(br,"</entity>");
                            continue;
                        }
                        line = moveToNextMarker(br,"<Polarity>");
                        if(line==null) continue;
                        polarity = readContent(line,"Polarity");
                        line = moveToNextMarker(br,"<ContextualModality>");
                        if(line==null) continue;
                        contextualModality = readContent(line,"ContextualModality");
                        doc.addEvent(new RedEvent(doc,id++,charStart,charEnd,polarity,contextualModality));
                    }
                }
                br.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private String moveToNextMarker(BufferedReader br, String marker){
        String line = null;
        try {
            while ((line = br.readLine()) != null && !line.contains(marker));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Return null");
        }
        return line;
    }
    private String readContent(String text,String propname){
        int start = text.indexOf(String.format("<%s>",propname))+propname.length()+2;
        int end = text.indexOf(String.format("</%s>",propname));
        return text.substring(start,end);
    }
    public void read(){
        readSource();
        readAnnotation();
    }


    public RedDataset getRedDataset() {
        return redDataset;
    }

    public int num_Events(){
        int n = 0;
        for(RedDoc doc: redDataset.getDataset()){
            n+=doc.getEvents().size();
        }
        return n;
    }
    public static void render4crowdflower(int mod){
        String tag,source_dir,ann_dir,outputfile;
        if(mod==0){
            tag = "deft";
            source_dir = "/home/qning2/Servers/root/shared/corpora/corporaWeb/deft/event/red/data/source/deft";
            ann_dir = "/home/qning2/Servers/root/shared/corpora/corporaWeb/deft/event/red/data/annotation/deft";
            outputfile = String.format("data/RED-filtering/Crowdflower/%s.csv",tag);
        }
        else{
            tag = "deft-sample";
            source_dir = "/home/qning2/Research/illinois-temporal/data/RED-filtering/deft-sample";
            ann_dir = "/home/qning2/Servers/root/shared/corpora/corporaWeb/deft/event/red/data/annotation/deft";
            outputfile = String.format("data/RED-filtering/Crowdflower/%s.csv",tag);
        }
        RedReader_Basic reader = new RedReader_Basic(source_dir,ann_dir);
        reader.read();
        reader.getRedDataset().Render4Crowdflower(outputfile,"red");
        System.out.println(reader.num_Events());
    }
    public static void main(String[] args) throws Exception{
        render4crowdflower(1);
    }
}
