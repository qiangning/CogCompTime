package edu.illinois.cs.cogcomp.temporal.datastruct.Temporal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qning2 on 11/15/16.
 */
public class TLINK implements java.io.Serializable{
    //[BEFORE, AFTER, INCLUDES, IS_INCLUDED, DURING, DURING_INV, SIMULTANEOUS, IAFTER, IBEFORE, IDENTITY, BEGINS, ENDS, BEGUN_BY, ENDED_BY, OVERLAP, BEFORE-OR-OVERLAP, OVERLAP-OR-AFTER, VAGUE, NONE]
    public enum TlinkType {
        BEFORE ("","before"),
        AFTER ("after","after"),
        EQUAL ("=","equal"),
        INCLUDES ("icd","includes"),
        IS_INCLUDED ("icd'd","included"),
        CAUSES ("c", "causes"),
        CAUSED_BY ("c'd", "caused"),
        RELATED ("r", "related"),
        UNDEF ("undef");
        private final String name;
        private final String fullname;
        public static TlinkType[] tvalues(){
            TlinkType[] res = new TlinkType[6];
            for(TlinkType tt:values()){
                if(tt.getTValueIdx()<0)
                    continue;
                res[tt.getTValueIdx()] = tt;
            }
            //TlinkType[] res = new TlinkType[]{BEFORE,AFTER,EQUAL,INCLUDES,IS_INCLUDED,UNDEF};
            return res;
        }
        TlinkType(String s) {
            name = s;
            fullname = s;
        }
        TlinkType(String name,String fullname){
            this.name = name;
            this.fullname = fullname;
        }
        public String toString() {
            return this.name;
        }
        public String toStringfull(){return this.fullname;}
        public TlinkType reverse(){
            switch(this){
                case BEFORE:
                    return AFTER;
                case AFTER:
                    return BEFORE;
                case EQUAL:
                    return EQUAL;
                case INCLUDES:
                    return IS_INCLUDED;
                case IS_INCLUDED:
                    return INCLUDES;
                case CAUSED_BY:
                    return CAUSES;
                case CAUSES:
                    return CAUSED_BY;
                case RELATED:
                    return RELATED;
                case UNDEF:
                    return UNDEF;
                default:
                    System.out.println("Undefined TlinkType.");
                    System.exit(-1);
            }
            return UNDEF;
        }
        public static TlinkType reverse(String fullname){
            return str2TlinkType(fullname).reverse();
        }
        public static TlinkType str2TlinkType(String fullname){
            switch(fullname){
                case "before":
                    return BEFORE;
                case "after":
                    return AFTER;
                case "equal":
                    return EQUAL;
                case "includes":
                    return INCLUDES;
                case "included":
                    return IS_INCLUDED;
                case "undef":
                case "":
                    return UNDEF;
                default:
                    System.out.println("Undefined TlinkType.");
                    System.exit(-1);
            }
            return UNDEF;
        }
        public int getTValueIdx(){
            switch(this){
                case BEFORE:
                    return 0;
                case AFTER:
                    return 1;
                case EQUAL:
                    return 2;
                case INCLUDES:
                    return 3;
                case IS_INCLUDED:
                    return 4;
                case UNDEF:
                    return 5;
                default:
                    return -1;
            }
        }
    }
    private static final long serialVersionUID = 5225315703669795655L;
    private int lid;
    private String relType;
    private String sourceType;
    private String targetType;
    private int sourceId;
    private int targetId;
    private TlinkType reducedRelType;
    public static String[] ignore_tlink = new String[]{TlinkType.UNDEF.toStringfull()};

    public TLINK(int lid, String relType, String sourceType, String targetType, int sourceId, int targetId) {
        this.lid = lid;
        this.relType = relType;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.reducedRelType = getReducedRelType(this.relType);
    }
    public TLINK(int lid, String relType, String sourceType, String targetType, int sourceId, int targetId, TlinkType reducedRel) {
        this.lid = lid;
        this.relType = relType;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.reducedRelType = reducedRel;
    }
    public TLINK deepCopy(){
        return new TLINK(lid,relType,sourceType,targetType,sourceId,targetId,reducedRelType);
    }
    public TLINK converse(){
        return new TLINK(lid, invRelType(relType), targetType, sourceType, targetId, sourceId,reducedRelType.reverse());
    }

    public String getRelType() {
        return relType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getTargetType() {
        return targetType;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public void setRelType(String relType) {
        this.relType = relType;
        this.reducedRelType = getReducedRelType(this.relType);
    }

    public void setReducedRelType(TlinkType reducedRelType) {
        this.reducedRelType = reducedRelType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "TLINK{" +
                "lid=" + lid +
                ", sourceType='" + sourceType + '\'' +
                ", targetType='" + targetType + '\'' +
                ", sourceId=" + sourceId +
                ", targetId=" + targetId +
                ", relType='" + reducedRelType + '\'' +
                '}';
    }

    public String toStringConcise(){
        return "("+sourceType.substring(0,1)+sourceId+","+targetType.substring(0,1)+targetId+")="
                +reducedRelType.toStringfull();
    }


    public boolean equals(TLINK tlink){
        boolean eq = this.sourceType.equals(tlink.getSourceType())
                && this.targetType.equals(tlink.getTargetType())
                && this.sourceId==tlink.getSourceId()
                && this.targetId==tlink.getTargetId();
        return eq;
    }
    public TlinkType getReducedRelType(){
        return reducedRelType;
    }
    public TlinkType getReducedRelType(String relType){
        switch(relType.toLowerCase()){
            case "before":
                return TlinkType.BEFORE;
            case "after":
                return TlinkType.AFTER;
            case "identity":
                return TlinkType.EQUAL;
            case "simultaneous":
                return TlinkType.EQUAL;
            case "includes":
                return TlinkType.INCLUDES;
            case "is_included":
                return TlinkType.IS_INCLUDED;
            case "included":
                return TlinkType.IS_INCLUDED;
            case "ended_by":
                return TlinkType.INCLUDES;
            case "ends":
                return TlinkType.IS_INCLUDED;
            case "during":
                return TlinkType.IS_INCLUDED;
            case "begun_by":
                return TlinkType.INCLUDES;
            case "begins":
                return TlinkType.IS_INCLUDED;
            case "continues":
                return TlinkType.AFTER;
            case "initiates":
                return TlinkType.IS_INCLUDED;
            case "terminates":
                return TlinkType.IS_INCLUDED;
            case "ibefore":
                return TlinkType.BEFORE;
            case "iafter":
                return TlinkType.AFTER;
            case "r":
                return TlinkType.RELATED;
            case "c":
                return TlinkType.CAUSES;
            case "c'd":
                return TlinkType.CAUSED_BY;
            default:
                return TlinkType.UNDEF;
        }
    }
    public String invRelType(String relType){
        switch(relType.toLowerCase()) {
            case "before":
                return "AFTER";
            case "after":
                return "BEFORE";
            case "identity":
                return "IDENTITY";
            case "simultaneous":
                return "SIMULTANEOUS";
            case "includes":
                return "IS_INCLUDED";
            case "is_included":
                return "INCLUDES";
            case "ended_by":
                return "ENDS";
            case "ends":
                return "ENDED_BY";
            case "during":
                return "INCLUDES";
            case "begun_by":
                return "BEGINS";
            case "begins":
                return "BEGUN_BY";
            case "continues":
                return "BEFORE";
            case "initiates":
                return "BEGUN_BY";
            case "terminates":
                return "ENDED_BY";
            case "ibefore":
                return "IAFTER";
            case "iafter":
                return "IBEFORE";
            case "c":
                return "CAUSED_BY";
            case "c'd":
                return "CAUSES";
            case "r":
                return "RELATED";
            default:
                return "INV_" + relType;
        }
    }

    public static List<TLINK> removeDuplicates(List<TLINK> tlinks){
        List<TLINK> reducedTlinks = new ArrayList<>();
        for(int i=0;i<tlinks.size();i++){
            /*Self-identity (redundant)*/
            if(tlinks.get(i).getSourceType().equals(tlinks.get(i).getTargetType())&&
                    tlinks.get(i).getSourceId()==tlinks.get(i).getTargetId())
                continue;
            /*Duplicates exist*/
            boolean found = false;
            for(int j=i+1;j<tlinks.size();j++){
                if(tlinks.get(i).equals(tlinks.get(j))) {
                    found = true;
                    break;
                }
            }
            if(!found)
                reducedTlinks.add(tlinks.get(i));
        }
        return reducedTlinks;
    }
}

