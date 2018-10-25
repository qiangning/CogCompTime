package edu.illinois.cs.cogcomp.temporal.readers;

import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;
import edu.illinois.cs.cogcomp.temporal.configurations.temporalConfigurator;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelType;
import edu.illinois.cs.cogcomp.temporal.utils.IO.myIOUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class temprelAnnotationReader {
    public static class Q1_Q2_temprel{
        private boolean q1, q2;
        private double q1_yes_conf, q2_yes_conf;
        private TemporalRelType relType;

        public Q1_Q2_temprel(boolean q1, boolean q2) {
            this(q1,q2,1,1);
        }

        public Q1_Q2_temprel(boolean q1, boolean q2, double q1_conf, double q2_conf){
            this.q1 = q1;
            this.q2 = q2;
            q1_yes_conf = q1?q1_conf:(1-q1_conf);
            q2_yes_conf = q2?q2_conf:(1-q2_conf);
            if(this.q1&&this.q2)
                relType = new TemporalRelType(TemporalRelType.relTypes.VAGUE);
            else if(this.q1&&!this.q2)
                relType = new TemporalRelType(TemporalRelType.relTypes.BEFORE);
            else if(!this.q1&&this.q2)
                relType = new TemporalRelType(TemporalRelType.relTypes.AFTER);
            else
                relType = new TemporalRelType(TemporalRelType.relTypes.EQUAL);
            double[] relType_conf = relType.getScores();
            relType_conf[TemporalRelType.relTypes.BEFORE.getIndex()] = q1_yes_conf*(1-q2_yes_conf);
            relType_conf[TemporalRelType.relTypes.AFTER.getIndex()] = q2_yes_conf*(1-q1_yes_conf);
            relType_conf[TemporalRelType.relTypes.EQUAL.getIndex()] = (1-q1_yes_conf)*(1-q2_yes_conf);
            relType_conf[TemporalRelType.relTypes.VAGUE.getIndex()] = q1_yes_conf*q2_yes_conf;
            relType_conf[TemporalRelType.relTypes.NULL.getIndex()] = 0;
            relType.setScores(relType_conf);
        }

        public boolean isQ1() {
            return q1;
        }

        public boolean isQ2() {
            return q2;
        }

        public TemporalRelType getRelType() {
            return relType;
        }

        @Override
        public String toString() {
            return "Q1_Q2_temprel{" +
                    "q1=" + q1 +
                    ", q2=" + q2 +
                    ", q1_yes_conf=" + q1_yes_conf +
                    ", q2_yes_conf=" + q2_yes_conf +
                    ", relType=" + relType +
                    '}';
        }

        public Q1_Q2_temprel(TemporalRelType relType) {
            this.relType = relType;
            q1_yes_conf = -1d;// todo: should re-calculate from relType.scores
            q2_yes_conf = -1d;
            switch (relType.toString().toLowerCase()){
                case "vague":
                    q1 = true;
                    q2 = true;
                    break;
                case "before":
                    q1 = true;
                    q2 = false;
                    break;
                case "after":
                    q1 = false;
                    q2 = true;
                    break;
                case "equal":
                    q1 = false;
                    q2 = false;
                    break;
                default:
                    System.out.println("Unexpected relType in Q1_Q2_temprel");
                    System.exit(-1);
            }

        }
    }

    public static class CrowdFlowerEntry{
        private int eventid1, eventid2;// eiid
        private Q1_Q2_temprel rel;

        public int getEventid1() {
            return eventid1;
        }

        public int getEventid2() {
            return eventid2;
        }

        public Q1_Q2_temprel getRel() {
            return rel;
        }

        public CrowdFlowerEntry(int eventid1, int eventid2, Q1_Q2_temprel rel) {
            this.eventid1 = eventid1;
            this.eventid2 = eventid2;
            this.rel = rel;
        }

        @Override
        public String toString() {
            return "CrowdFlowerEntry{" +
                    "eventid1=" + eventid1 +
                    ", eventid2=" + eventid2 +
                    ", rel=" + rel +
                    '}';
        }
    }

    public static HashMap<String,List<CrowdFlowerEntry>> readTemprelFromCrowdFlower(String fileList){
        // docid-->CrowdFlowerEntry
        HashMap<String,List<CrowdFlowerEntry>> relMap = new HashMap<>();
        String[] files = fileList.split(",");
        int cnt = 0;
        for(String file:files){
            String tmpDir = myIOUtils.getParentDir(file);
            String tmpFile = myIOUtils.getFileOrDirName(file);
            myCSVReader cf_reader = new myCSVReader(tmpDir,tmpFile);
            for(int i=0;i<cf_reader.getContentLines();i++){
                try {
                    String docid = cf_reader.getLineTag(i, "docid");
                    int eventid1 = Integer.valueOf(cf_reader.getLineTag(i, "eventid1"));
                    int eventid2 = Integer.valueOf(cf_reader.getLineTag(i, "eventid2"));
                    boolean q1 = cf_reader.getLineTag(i,"q1").equals("yes");
                    boolean q2 = cf_reader.getLineTag(i,"q2").equals("yes");
                    double q1_conf = Double.valueOf(cf_reader.getLineTag(i,"q1_conf"));
                    double q2_conf = Double.valueOf(cf_reader.getLineTag(i,"q2_conf"));
                    Q1_Q2_temprel temprel = new Q1_Q2_temprel(q1,q2,q1_conf,q2_conf);
                    CrowdFlowerEntry entry = new CrowdFlowerEntry(eventid1,eventid2,temprel);
                    if(!relMap.containsKey(docid))
                        relMap.put(docid,new ArrayList<>());
                    relMap.get(docid).add(entry);
                }
                catch (Exception e){
                    cnt++;
                }
            }
        }
        System.out.printf("[WARNING:temprelAnnotationReader] Exception happened for %d rows (usually it's caused by test questions from crowdflower and can be safely neglected).\n",cnt);
        return relMap;
    }
}
