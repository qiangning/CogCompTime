// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D8F81CA028050154F75E23B252A0479614B887BB8616E6222E16A79014D72E39042AF7F42232338CDECCD37FCC4A99D4A663C3E90B13756D95979D78B572261599A3EC346E17399925B2502D5CA6E8B1C2B4B4882FED8049F18066078C50D3D9CB6CA9E36D0930DA0208CEC88BBCFD432A6B2E3B264D0AB9E6532C892ECDE04BEF704EDF5D6FCF05C38E8F61BEE2DDF9C3DE9DE24239F0CBF300E806DFB6B8100000

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


public class CoveringSRL extends Classifier
{
  public CoveringSRL()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "CoveringSRL";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete[]"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'CoveringSRL(TemporalRelation_EE)' defined on line 163 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    int __featureIndex = 0;
    String __value;

    __value = "" + ("E1 Covering E2:" + (ee.e1_covering_e2 ? "Yes" : "No"));
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    __value = "" + ("E1 Covering E2 Type:" + ee.e1_covering_e2_type);
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    __value = "" + ("E1 Covered by E2:" + (ee.e2_covering_e1 ? "Yes" : "No"));
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    __value = "" + ("E1 Covered by E2 Type:" + ee.e2_covering_e1_type);
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    __value = "" + ("E1_E2 Covering" + (ee.e1_covering_e2 ? "Yes" : "No") + ":" + (ee.e2_covering_e1 ? "Yes" : "No"));
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));

    for (int __i = 0; __i < __result.featuresSize(); ++__i)
      __result.getFeature(__i).setArrayLength(__featureIndex);

    return __result;
  }

  public String[] discreteValueArray(Object __example)
  {
    return classify(__example).discreteValueArray();
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'CoveringSRL(TemporalRelation_EE)' defined on line 163 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "CoveringSRL".hashCode(); }
  public boolean equals(Object o) { return o instanceof CoveringSRL; }
}

