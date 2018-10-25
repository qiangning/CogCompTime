package edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor;

import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TLINK;
import edu.illinois.cs.cogcomp.temporal.utils.IO.mySerialization;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class TempLangMdl {
    public static TempLangMdl instance = null;
    public HashMap<String,HashMap<String,HashMap<TLINK.TlinkType,Integer>>> tempLangMdl = null;
    public HashMap<String,String> cluster = null;
    public TempLangMdl(String path){
        try {
            if(path.endsWith(".ser")) {
                mySerialization myser = new mySerialization(true);
                tempLangMdl = (HashMap<String, HashMap<String, HashMap<TLINK.TlinkType, Integer>>>) myser.deserialize(path);
            }
            else
                readLM(path);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public TempLangMdl(String cluster_path, String lm_path){
        try {
            readCluster(cluster_path);//set up "cluster"
            if(lm_path.endsWith(".ser")) {
                mySerialization myser = new mySerialization(true);
                tempLangMdl = (HashMap<String, HashMap<String, HashMap<TLINK.TlinkType, Integer>>>) myser.deserialize(lm_path);
            }
            else
                readLM(lm_path);
            clusteringLM();//update "tempLangMdl"
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static TempLangMdl getInstance(String path) {
        if (instance == null) {
            instance = new TempLangMdl(path);
        }
        return instance;
    }
    public static TempLangMdl getInstance(String cluster_path, String lm_path) {
        if (instance == null) {
            instance = new TempLangMdl(cluster_path,lm_path);
        }
        return instance;
    }
    public void readCluster(String fullpath) throws Exception{
        int clusterID = 0;
        Scanner in = new Scanner(new FileReader(fullpath));
        cluster = new HashMap<>();
        while(in.hasNextLine()){
            String line = in.nextLine().trim();
            String[] words = line.split(" ");
            for(String w:words){
                cluster.put(w,String.valueOf(clusterID));
            }
            clusterID++;
        }
        in.close();
    }
    public void readLM(String fullpath) throws Exception{
        Scanner in = new Scanner(new FileReader(fullpath));
        int nline = 0;
        tempLangMdl = new HashMap<>();
        while(in.hasNextLine()){
            nline++;
            String line = in.nextLine().trim();
            String[] words = line.split("\t");
            TLINK.TlinkType tt = TLINK.TlinkType.str2TlinkType(words[2]);
            int cnt = Integer.valueOf(words[3]);
            String c1 = words[0];
            String c2 = words[1];
            if(!tempLangMdl.keySet().contains(c1))
                tempLangMdl.put(c1,new HashMap<>());
            if(!tempLangMdl.get(c1).keySet().contains(c2))
                tempLangMdl.get(c1).put(c2,new HashMap<>());
            if(!tempLangMdl.get(c1).get(c2).keySet().contains(tt)) {
                tempLangMdl.get(c1).get(c2).put(tt, cnt);
            }
            else {
                int prev = tempLangMdl.get(c1).get(c2).get(tt);
                tempLangMdl.get(c1).get(c2).put(tt, prev + 1);
            }
        }
        in.close();
    }
    public void clusteringLM(){
        if(cluster == null)
            return;
        HashMap<String,HashMap<String,HashMap<TLINK.TlinkType,Integer>>> tempLangMdl_new = new HashMap<>();
        for(String v1:tempLangMdl.keySet()){
            String c1 = cluster.getOrDefault(v1.replaceAll("\\.0[1-9]",""),"-1");
            if(!tempLangMdl_new.containsKey(c1))
                tempLangMdl_new.put(c1,new HashMap<>());
            for(String v2:tempLangMdl.get(v1).keySet()){
                String c2 = cluster.getOrDefault(v2.replaceAll("\\.0[1-9]",""),"-1");
                if(!tempLangMdl_new.get(c1).containsKey(c2))
                    tempLangMdl_new.get(c1).put(c2,new HashMap<>());
                for(TLINK.TlinkType tt : tempLangMdl.get(v1).get(v2).keySet()){
                    int curr = tempLangMdl_new.get(c1).get(c2).getOrDefault(tt,0);
                    tempLangMdl_new.get(c1).get(c2).put(tt,curr+tempLangMdl.get(v1).get(v2).get(tt));
                }
            }
        }
        tempLangMdl = tempLangMdl_new;
    }
    public static void main(String[] args) throws Exception{
        //serializeAllClusters(myConfigurator.SERIAL_DIR_SHARED.value+"/clustering");
        /*TempLangMdl mdl = new TempLangMdl(myConfigurator.SERIAL_DIR_SHARED.value+"/clustering/cluster_k=100_of_glove.6B.300d.txt.plk.gz",
                myConfigurator.SERIAL_DIR_SHARED.value+"/temporalLM/980000.ser");
        System.out.println();*/
        //serializeLM(myConfigurator.SERIAL_DIR_SHARED.value+"/temporalLM_2Sent/sent2results_sorted.txt","sent2results.ser");
        //serializeLM(myConfigurator.SERIAL_DIR_SHARED.value+"/aws_results_sorted","sent1results.ser");
        //countLM();
    }
}
