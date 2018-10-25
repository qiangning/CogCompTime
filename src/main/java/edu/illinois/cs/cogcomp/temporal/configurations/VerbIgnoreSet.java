package edu.illinois.cs.cogcomp.temporal.configurations;

import edu.illinois.cs.cogcomp.core.utilities.configuration.ResourceManager;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class VerbIgnoreSet {
    public Set<String> verbIgnoreSet = new HashSet<>(), srlVerbIgnoreSet = new HashSet<>();
    public static VerbIgnoreSet instance;
    private static Set<String> parseRmProperty(ResourceManager rm, String propName){
        String tmp = rm.getString(propName);
        return new HashSet<String>(Arrays.asList(tmp.split(",")));
    }
    public static VerbIgnoreSet getInstance(){
        if(instance!=null)
            return instance;
        try {
            return getInstance(new ResourceManager("config/VerbsIgnore.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
            return new VerbIgnoreSet();
        }
    }
    public static VerbIgnoreSet getInstance(ResourceManager rm){
        if(instance!=null)
            return instance;
        instance = new VerbIgnoreSet();
        instance.verbIgnoreSet = parseRmProperty(rm,"VERBS_IGNORE");
        instance.srlVerbIgnoreSet = parseRmProperty(rm,"SRL_VERBS_IGNORE");
        return instance;
    }
}
