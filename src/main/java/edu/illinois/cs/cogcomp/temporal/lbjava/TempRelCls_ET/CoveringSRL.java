// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B49CC2E4E2A4D294D8E85507ECF2B4D2ACCCB4F0E02F1D8094DCD28CF2A4CC90A4DC94C29CCCFCB877D01584D21D450B1D558A6582E4DCB2E4550527D0375FB018BE25801F4F57D80B25250D650D84D21DB4D8F468A44C79828DB282546A61B2928592829F5EB296A53E3314124A2B0251C6C0A9921F520490B658A5002E5DB9482B000000

package edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET;

import edu.illinois.cs.cogcomp.core.datastructures.IntPair;
import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.temporal.configurations.ParamLBJ;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.EventTemporalNode;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TimexTemporalNode;
import java.lang.Object;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class CoveringSRL extends Classifier
{
  public CoveringSRL()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "CoveringSRL";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete[]"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'CoveringSRL(TemporalRelation_ET)' defined on line 144 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_ET et = (TemporalRelation_ET) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    int __featureIndex = 0;
    String __value;

    __value = "" + ("EVENT Covering TIMEX:" + (et.e_covering_t ? "Yes" : "No"));
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    __value = "" + ("EVENT Covering TIMEX Type:" + et.e_covering_t_type);
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
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'CoveringSRL(TemporalRelation_ET)' defined on line 144 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "CoveringSRL".hashCode(); }
  public boolean equals(Object o) { return o instanceof CoveringSRL; }
}

