// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D8FCD4B02803C1600FFA2FF60182250EE865472F014870590A398F2F012CAD26B9D52AFEE9E1C798A8CBC6C6BFD3B76BAA635A685C29E06E40D5421F1D9417DB92D9B881227B5B299581210E2D6754F0A0FE096B7119AA040F967DE9FAF20B98A64798E67D1773FD82F186A9BE67CE961A4904E2BDBB6876472DFE467BECC861E8B4B8D19C6488987EF6E9FF2F602D08858EF8FDC081974EC4B07F4CEC03C820261926E6B3B5DD06C03FF803F9F16F3B96EFF67B74C6E77C3F50154F1EA16C100000

package edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls;

import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.temporal.configurations.ParamLBJ;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.EventTemporalNode;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE;
import java.lang.Object;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class IsVerbSRL extends Classifier
{
  public IsVerbSRL()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "IsVerbSRL";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'IsVerbSRL(TemporalRelation_EE)' defined on line 130 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    EventTemporalNode e1 = ee.getSourceNode();
    EventTemporalNode e2 = ee.getTargetNode();
    boolean e1IsVerbSRL = e1.getVerb_srl() != null;
    boolean e2IsVerbSRL = e2.getVerb_srl() != null;
    __id = "" + ("E1 IsVerbSRL:" + (e1IsVerbSRL ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E2 IsVerbSRL:" + (e2IsVerbSRL ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E1_E2 IsVerbSRL" + (e1IsVerbSRL ? "Yes" : "No") + ":" + (e2IsVerbSRL ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'IsVerbSRL(TemporalRelation_EE)' defined on line 130 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "IsVerbSRL".hashCode(); }
  public boolean equals(Object o) { return o instanceof IsVerbSRL; }
}

