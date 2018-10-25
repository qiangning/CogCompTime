package edu.illinois.cs.cogcomp.temporal.utils;

import java.util.Arrays;

public class myLogFormatter {
    public static int gap = 5;
    public static int level = 0;
    private static char[] tagsLevel = new char[]{'#','*','+','-','.'};
    private static char tagAtLevel(){
        if(level<0)
            return tagsLevel[0];
        if(level>=tagsLevel.length)
            return tagsLevel[tagsLevel.length-1];
        return tagsLevel[level];
    }
    public static String fullBlockLog(String label){
        char tag = tagAtLevel();
        label = label.replaceAll("\n"," ");//make sure it's oneline
        int len = label.length();
        StringBuilder sb = new StringBuilder();
        sb.append(lineOfTag(tag,len+gap*2));
        sb.append("\n");
        sb.append(middleBlockLog(label));
        sb.append(lineOfTag(tag,len+gap*2));
        sb.append("\n");
        return sb.toString();
    }
    public static String startBlockLog(String label){
        char tag = tagAtLevel();
        label = label.replaceAll("\n"," ");//make sure it's oneline
        int len = label.length();
        StringBuilder sb = new StringBuilder();
        sb.append(lineOfTag(tag,len+gap*2));
        sb.append("\n");
        sb.append(middleBlockLog(label));
        level++;
        if(level>=tagsLevel.length)
            level = tagsLevel.length-1;
        return sb.toString();
    }
    public static String endBlockLog(String label){
        level--;
        if(level<0)
            level = 0;
        char tag = tagAtLevel();
        label = label.replaceAll("\n"," ");//make sure it's oneline
        int len = label.length();
        StringBuilder sb = new StringBuilder();
        sb.append(middleBlockLog(label));
        sb.append(lineOfTag(tag,len+gap*2));
        sb.append("\n");
        return sb.toString();
    }
    public static String middleBlockLog(String label){
        char tag = tagAtLevel();
        label = label.replaceAll("\n"," ");//make sure it's oneline
        int len = label.length();
        StringBuilder sb = new StringBuilder();
        sb.append(tag);
        sb.append(lineOfTag(' ',len+gap*2-2));
        sb.append(tag+"\n"+tag);
        sb.append(lineOfTag(' ',gap-1));
        sb.append(label);
        sb.append(lineOfTag(' ',gap-1));
        sb.append(tag+"\n"+tag);
        sb.append(lineOfTag(' ',len+gap*2-2));
        sb.append(tag+"\n");
        return sb.toString();
    }
    public static String lineOfTag(char tag, int len){
        char[] line = new char[len];
        Arrays.fill(line,tag);
        return String.valueOf(line);
    }

    public static void main(String[] args) {
        System.out.println(startBlockLog("testtesttest"));
        System.out.println(startBlockLog("testtesttest"));
        System.out.println(endBlockLog("testtesttest"));
        System.out.println(endBlockLog("testtesttest"));
    }
}
