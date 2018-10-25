// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000057FC14B0280381600EFB2F1E9632214F89570F0BB9A14A7988091AF6128AB2D9D52CFFEDCA4158ABCE0B7F9FEDDEBCB863B60A17A33508AA29C2145735D8C2F0825AE245D9A014007ADEA9E942E18A5F822259380E0DEC4E6F51A365D539168B56CDBF54DD98622B137E84B54DD28C21E4A188034F7361DAC4FE0EE3F5A91177E4CD5092E24C613563EED9C2B56B07C9B9536A70D4F5CE782EB5A7411B5E15F482D4AF7554BF73AEF5E34B5C05A3100000

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


public class Lemma extends Classifier
{
  public Lemma()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "Lemma";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete[]"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'Lemma(TemporalRelation_EE)' defined on line 52 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    int __featureIndex = 0;
    String __value;

    EventTemporalNode e1 = ee.getSourceNode();
    EventTemporalNode e2 = ee.getTargetNode();
    __value = "" + ("E1_LEMMA:" + e1.getLemma());
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    __value = "" + ("E2_LEMMA:" + e2.getLemma());
    __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    if (e1.getLemma().equals(e2.getLemma()))
    {
      __value = "E1E2_SAME_LEMMA:YES";
      __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    }
    else
    {
      __value = "E1E2_SAME_LEMMA:NO";
      __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    }

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
      System.err.println("Classifier 'Lemma(TemporalRelation_EE)' defined on line 52 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "Lemma".hashCode(); }
  public boolean equals(Object o) { return o instanceof Lemma; }
}

