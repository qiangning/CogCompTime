// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000059F81BA038034154F75E118282251CC86B5A39347170BE2D9425DB880639294CE25AFFED838D641786797909C1ECDBFA9EC4DA161B1A39920775AD672BD0A4CDCD554F50A716B352BAC232024ADF69E949D302DE444EAA10121A38BFF8B58D3BA147D81F538077B682FF0A5A0DE6E48E559AE124A379754646398B3FA99F42F509C794A1843026952EF27923A882897CC198D506815A4C275C2477A5D30EB20EB220EFF8029A6EE8F1A3444CEF94A7DB1049647FD1AC100000

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


public class IsReporting extends Classifier
{
  public IsReporting()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "IsReporting";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'IsReporting(TemporalRelation_EE)' defined on line 141 of eeTempRelCls.lbj received '" + type + "' as input.");
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
    boolean e1IsReporting = e1.isReporting();
    boolean e2IsReporting = e2.isReporting();
    __id = "" + ("E1 IsReporting:" + (e1IsReporting ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E2 IsReporting:" + (e2IsReporting ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E1_E2 IsReporting" + (e1IsReporting ? "Yes" : "No") + ":" + (e2IsReporting ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'IsReporting(TemporalRelation_EE)' defined on line 141 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "IsReporting".hashCode(); }
  public boolean equals(Object o) { return o instanceof IsReporting; }
}

