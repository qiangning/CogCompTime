// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000059F81BA038030168F55E8041419A0664BD2D9C1C5C1A579E429AEF411C625298D5A4FDDB170BD82E0579B38BBF8FEEFB6A535B685C682739B4B096B55238217F782D2AB31A313C0AAC232024ADF69E549D3D1532158A604848E0E6F1FD06FCAA7D53689601EE6905EF54B41AD5D11DBA25D14847E2F28C0C6217BE523F94E3329FC94309604C2B4CF7E256411503D337426718164921B241B0D57BAB7CF40C7140C7D8029A6AE8F323444C6DC5A7F700BC182254AC100000

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


public class IsIntention extends Classifier
{
  public IsIntention()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "IsIntention";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'IsIntention(TemporalRelation_EE)' defined on line 152 of eeTempRelCls.lbj received '" + type + "' as input.");
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
    boolean e1IsIntention = e1.isIntention();
    boolean e2IsIntention = e2.isIntention();
    __id = "" + ("E1 IsIntention:" + (e1IsIntention ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E2 IsIntention:" + (e2IsIntention ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E1_E2 IsIntention" + (e1IsIntention ? "Yes" : "No") + ":" + (e2IsIntention ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'IsIntention(TemporalRelation_EE)' defined on line 152 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "IsIntention".hashCode(); }
  public boolean equals(Object o) { return o instanceof IsIntention; }
}

