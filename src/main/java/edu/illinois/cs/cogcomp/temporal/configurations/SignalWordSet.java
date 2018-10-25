package edu.illinois.cs.cogcomp.temporal.configurations;


import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SignalWordSet {
    public class temporalSignals{
        public Set<String> connectives_before = new HashSet<>();
        public Set<String> connectives_after = new HashSet<>();
        public Set<String> connectives_during = new HashSet<>();
        public Set<String> connectives_contrast = new HashSet<>();
        public Set<String> connectives_adverb = new HashSet<>();
        private HashSet<String> allConnectives = new HashSet<>();
        public HashSet<String> getAllConnectives(){
            if(allConnectives.size()==0){
                allConnectives.addAll(connectives_before);
                allConnectives.addAll(connectives_after);
                allConnectives.addAll(connectives_during);
                allConnectives.addAll(connectives_contrast);
                allConnectives.addAll(connectives_adverb);
            }
            return allConnectives;
        }
    }
    public temporalSignals temporalSignalSet = new temporalSignals();
    public Set<String> modalVerbSet = new HashSet<>(),axisSignalWordSet = new HashSet<>(),reportingVerbSet = new HashSet<>(),intentionVerbSet = new HashSet<>();
    public static SignalWordSet instance;
    private static Set<String> parseRmProperty(ResourceManager rm, String propName){
        String tmp = rm.getString(propName);
        return new HashSet<String>(Arrays.asList(tmp.split(",")));
    }
    public static SignalWordSet getInstance(){
        if(instance!=null)
            return instance;
        try {
            return getInstance(new ResourceManager("config/SignalWordSet.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
            return new SignalWordSet();
        }
    }
    public static SignalWordSet getInstance(ResourceManager rm){
        if(instance!=null)
            return instance;
        instance = new SignalWordSet();
        instance.temporalSignalSet.connectives_before = parseRmProperty(rm,"temporalConnectiveSet_before");
        instance.temporalSignalSet.connectives_after = parseRmProperty(rm,"temporalConnectiveSet_after");
        instance.temporalSignalSet.connectives_during = parseRmProperty(rm,"temporalConnectiveSet_during");
        instance.temporalSignalSet.connectives_contrast = parseRmProperty(rm,"temporalConnectiveSet_contrast");
        instance.temporalSignalSet.connectives_adverb = parseRmProperty(rm,"temporalAdverbSet");
        instance.modalVerbSet = parseRmProperty(rm,"modalVerbSet");
        instance.axisSignalWordSet = parseRmProperty(rm,"axisSignalWordSet");
        instance.reportingVerbSet = parseRmProperty(rm,"reportingVerbSet");
        instance.intentionVerbSet = parseRmProperty(rm,"intentionVerbSet");
        return instance;
    }
}
