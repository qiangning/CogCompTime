package edu.illinois.cs.cogcomp.temporal.readers;

import edu.illinois.cs.cogcomp.temporal.utils.IO.myIOUtils;

import java.util.HashMap;

public class axisAnnotationReader {
    public static String LABEL_NOT_ON_ANY_AXIS = "null";
    public static String LABEL_ON_MAIN_AXIS = "main";
    public static String LABEL_ON_ORTHOGONAL_AXIS = "orthogonal";
    public static String LABEL_ON_NEGATION_AXIS = "negation";
    public static String LABEL_ON_HYPO_AXIS = "hypothesis";
    public static String LABEL_ON_OTHER_AXIS = "others";
    public static String axis_label_conversion(String label){
        switch (label){
            case "yes"://herited from older version of anchorability
            case "yes_its_anchorable":
                return LABEL_ON_MAIN_AXIS;
            case "no_its_intentionwishopinion":
                //return LABEL_ON_ORTHOGONAL_AXIS;
            case "no_its_negation":
                //return LABEL_ON_NEGATION_AXIS;
            case "no_its_hypotheticalcondition":
                //return LABEL_ON_HYPO_AXIS;
            case "no_its_recurrent":
            case "no_its_static":
            case "no_its_abstractnonspecific":
                return LABEL_ON_OTHER_AXIS;
            default:
                return LABEL_NOT_ON_ANY_AXIS;
        }
    }
    public static HashMap<String,HashMap<Integer,String>> readAxisMapFromCrowdFlower(String fileList){
        // docid-->eventid (index in doc)-->axis_label
        HashMap<String,HashMap<Integer,String>> axisMap = new HashMap<>();
        String[] files = fileList.split(",");
        for(String file:files){
            String tmpDir = myIOUtils.getParentDir(file);
            String tmpFile = myIOUtils.getFileOrDirName(file);
            myCSVReader cf_reader = new myCSVReader(tmpDir,tmpFile);
            for(int i=0;i<cf_reader.getContentLines();i++){
                try {
                    String docid = cf_reader.getLineTag(i, "docid");
                    int eventid = Integer.valueOf(cf_reader.getLineTag(i, "eventid"));
                    String anchorability = cf_reader.getLineTag(i, "can_the_verb_span_stylecolorblueverb_span_be_anchored_in_time");
                    if(cf_reader.getLineTag(i,"_golden").equals("false")&&cf_reader.getLineTag(i,"_unit_state").equals("golden"))
                        continue;
                    if(!axisMap.containsKey(docid))
                        axisMap.put(docid,new HashMap<>());
                    axisMap.get(docid).put(eventid,anchorability);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return axisMap;
    }
}
