package edu.illinois.cs.cogcomp.temporal.datastruct.Temporal;

import edu.illinois.cs.cogcomp.temporal.datastruct.GeneralGraph.BinaryRelationType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chuchu on 12/19/17.
 */
public class TemporalRelType extends BinaryRelationType{
    public enum relTypes{
        BEFORE("BEFORE"),
        AFTER("AFTER"),
        EQUAL("EQUAL"),
        VAGUE("VAGUE"),
        NULL("NULL");
        private final String name;
        private relTypes inverse;
        private static String[] allNames;
        relTypes(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public relTypes getInverse(){
            if(inverse==null)
                inverse = this.inverse();
            return inverse;
        }
        private relTypes inverse(){
            switch(this){
                case BEFORE:
                    return AFTER;
                case AFTER:
                    return BEFORE;
                case EQUAL:
                    return EQUAL;
                case VAGUE:
                    return VAGUE;
                default:
                    return NULL;
            }
        }
        public static String[] getAllNames(){
            if(allNames==null||allNames.length == 0) {
                relTypes[] allRelTypes = values();
                allNames = new String[allRelTypes.length];
                for (int i = 0; i < allNames.length; i++)
                    allNames[i] = allRelTypes[i].getName();
            }
            return allNames;
        }
        public int getIndex(){
            relTypes[] values = relTypes.values();
            for(int i=0;i<values.length;i++){
                relTypes r = values[i];
                if(this==r)
                    return i;
            }
            return -1;
        }
        public static relTypes getRelTypesFromIndex(int id){
            if(id<0||id>=relTypes.values().length)
                return NULL;
            return relTypes.values()[id];
        }
    }

    private relTypes reltype;
    private double[] scores;
    private static TemporalRelType nullRelType;
    private static double defaultConf = 1d;

    /*Constructors*/
    public TemporalRelType(TemporalRelType other){
        reltype = other.reltype;
        scores = new double[relTypes.values().length];
        System.arraycopy(other.scores,0,scores,0,scores.length);
    }
    public TemporalRelType(relTypes reltype) {
        this.reltype = reltype;
        scores = new double[relTypes.values().length];
        Arrays.fill(scores,0);
        scores[this.reltype.getIndex()] = defaultConf;
    }

    public TemporalRelType(String reltype) {
        for(relTypes rel:relTypes.values()){
            if(rel.getName().equals(reltype)||rel.getName().toLowerCase().equals(reltype)) {
                this.reltype = rel;
                scores = new double[relTypes.values().length];
                Arrays.fill(scores,0);
                scores[this.reltype.getIndex()] = defaultConf;
                return;
            }
        }
        this.reltype = relTypes.VAGUE;
        scores = new double[relTypes.values().length];
        Arrays.fill(scores,0);
        scores[this.reltype.getIndex()] = defaultConf;
        System.out.printf("Error using TemporalRelType (%s): %s cannot be found.\n",reltype,reltype);
    }

    /*Functions*/
    public TemporalRelType inverse() {
        return new TemporalRelType(reltype.getInverse());
    }

    public String toString() {
        return getReltype().getName();
    }

    public void reverse() {
        reltype = reltype.getInverse();
    }

    public boolean isNull() {
        return reltype==null || reltype==relTypes.NULL;
        //return reltype==null;
    }

    public boolean isEqual(BinaryRelationType other) {
        return this.reltype.equals(((TemporalRelType) other).reltype);
    }

    @Override
    public List<BinaryRelationType> transitivity(BinaryRelationType rel2) {
        switch(reltype){
            case BEFORE:
                switch (((TemporalRelType)rel2).getReltype()){
                    case BEFORE:
                    case EQUAL:
                        return Arrays.asList(new BinaryRelationType[]{new TemporalRelType(relTypes.BEFORE)});
                    case AFTER:
                        return Arrays.asList(new BinaryRelationType[]{new TemporalRelType(relTypes.BEFORE),
                                new TemporalRelType(relTypes.AFTER),
                                new TemporalRelType(relTypes.EQUAL),
                                new TemporalRelType(relTypes.VAGUE)});
                    default:
                        return Arrays.asList(new BinaryRelationType[]{new TemporalRelType(relTypes.BEFORE),
                                new TemporalRelType(relTypes.VAGUE)});
                }
            case AFTER:
                switch (((TemporalRelType)rel2).getReltype()){
                    case AFTER:
                    case EQUAL:
                        return Arrays.asList(new TemporalRelType(relTypes.AFTER));
                    case BEFORE:
                        return Arrays.asList(new TemporalRelType(relTypes.BEFORE),
                                new TemporalRelType(relTypes.AFTER),
                                new TemporalRelType(relTypes.EQUAL),
                                new TemporalRelType(relTypes.VAGUE));
                    default:
                        return Arrays.asList(new TemporalRelType(relTypes.AFTER),
                                new TemporalRelType(relTypes.VAGUE));
                }
            case EQUAL:
                return Arrays.asList(new BinaryRelationType[]{new TemporalRelType(((TemporalRelType)rel2).getReltype())});
            default:
                switch (((TemporalRelType)rel2).getReltype()){
                    case BEFORE:
                        return Arrays.asList(new TemporalRelType(relTypes.BEFORE),
                                new TemporalRelType(relTypes.VAGUE));
                    case EQUAL:
                        return Arrays.asList(new BinaryRelationType[]{new TemporalRelType(relTypes.VAGUE)});
                    case AFTER:
                        return Arrays.asList(new BinaryRelationType[]{new TemporalRelType(relTypes.AFTER),
                                new TemporalRelType(relTypes.VAGUE)});
                    default:
                        return Arrays.asList(new TemporalRelType(relTypes.VAGUE));
                }
        }
    }

    /*Getters and Setters*/

    public relTypes getReltype() {
        return reltype;
    }

    public void setReltype(relTypes reltype) {
        this.reltype = reltype;
    }

    public static TemporalRelType getNullTempRel() {
        if(nullRelType==null){
            nullRelType = new TemporalRelType(relTypes.NULL);
        }
        return nullRelType;
    }

    public void setScores(double[] scores) {
        this.scores = scores;
    }

    public double[] getScores() {
        return scores;
    }

    public static void main(String[] args) throws Exception{
        String[] test = relTypes.getAllNames();
        TemporalRelType tmp = new TemporalRelType("BEFORE");
        tmp.reverse();
        System.out.println(tmp);
        TemporalRelType tmp2 = new TemporalRelType("BEFORE");
        tmp2.inverse();
        System.out.println(tmp2);
    }
}
