package edu.illinois.cs.cogcomp.temporal.readers;

import com.opencsv.CSVReader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class myCSVReader {
    private String dir = "";
    private String fname = "";
    private String[] header;
    private List<String[]> content = new ArrayList<>();
    private HashMap<String,Integer> col2idx = new HashMap<>();

    public myCSVReader(String dir, String fname) {
        this.dir = dir;
        this.fname = fname;
        try {
            CSVReader csvReader = new CSVReader(new FileReader(dir+ File.separator+fname));
            content = csvReader.readAll();
            if(content==null||content.isEmpty()){
                System.out.println("File "+dir+" is empty.");
                return;
            }
            extractHeader();
        }
        catch (Exception e){
            System.out.println("Failed loading file "+dir);
            e.printStackTrace();
        }
    }
    private void extractHeader(){
        header = content.get(0);
        for(int i=0;i<header.length;i++){
            String h = header[i].toLowerCase();
            col2idx.put(h,i);
        }
        content.remove(0);
    }
    public int getContentLines(){
        return content.size();
    }
    private int tag2idx(String tag){
        tag = tag.toLowerCase();
        if(col2idx.containsKey(tag))
            return col2idx.get(tag);
        int match = 0, idx = -1;
        for(String col:col2idx.keySet()){
            if(col.contains(tag)) {
                match++;
                idx = col2idx.get(col);
            }
        }
        return match==1?idx:-1;
    }
    @NotNull
    public String getLineTag(int line, String tag){
        tag = tag.toLowerCase();
        int idx = tag2idx(tag);
        if(idx==-1){
            System.out.println("Tag "+tag+" doesn't exist.");
            return "";
        }
        if(line<0||line>=getContentLines()){
            System.out.println("Line "+line+" doesn't exist.");
            return "";
        }
        return content.get(line)[idx];
    }

    public String getDir() {
        return dir;
    }

    public String getFname() {
        return fname;
    }

    public String[] getHeader() {
        return header;
    }

    public List<String[]> getContent() {
        return content;
    }

    public HashMap<String, Integer> getCol2idx() {
        return col2idx;
    }
}
