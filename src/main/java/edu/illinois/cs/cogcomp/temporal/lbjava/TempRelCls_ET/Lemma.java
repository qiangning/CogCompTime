// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000056ECDCA0280441500E75938018225F0065B8613B3D6113BF142F01283A23E53A88EDD3D91CE786B7EC7C9B7BAA783B3A0758C96D691B6ADEB3756372635A4DD5B64960521CE6D8704D59DAC22E8D55401BF9AEDC582EBB93B839D247D697BF182F68EBB50E0C6702225627554178C224A0EC82C33902388B19F17A3043AFED3FF57A88C781EEC44EB61E9F200ED39AADAE000000

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


public class Lemma extends Classifier
{
  public Lemma()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "Lemma";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'Lemma(TemporalRelation_ET)' defined on line 68 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_ET et = (TemporalRelation_ET) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    EventTemporalNode e = et.getEventNode();
    TimexTemporalNode t = et.getTimexNode();
    __id = "" + ("E_LEMMA:" + e.getLemma());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("ET_LEMMA_Type:" + e.getLemma() + ":" + t.getType());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'Lemma(TemporalRelation_ET)' defined on line 68 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "Lemma".hashCode(); }
  public boolean equals(Object o) { return o instanceof Lemma; }
}

