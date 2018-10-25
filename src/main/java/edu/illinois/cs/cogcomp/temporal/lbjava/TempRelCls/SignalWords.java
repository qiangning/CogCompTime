// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000059F8BCA02C030154F7560148611BF10E361A011DD824B507921DED6B18A984232A02EFBBD64732D2267518B9376EEC46ACD9C28134A4451A565B73633714A83F5C85955C8A42B23AF02401012DC6C4F0A5B475620627E50778322736143FA988A0072F51701E4B340EB10AB3C0F9779223768D690E3D60FD4DFA463694DB1BFE5D225F9FE60DB59AA2050D441592DD6177E02C0BE3BC14B30D069265BB85C460432F3C22DE10A901BDB26A4FC6758F5AB7674AB7126BFF798F34B755C2659A88F741C3F50D7DFA519F100000

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


public class SignalWords extends Classifier
{
  public SignalWords()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "SignalWords";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'SignalWords(TemporalRelation_EE)' defined on line 87 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    HashSet signals_before = ee.getSignals_before();
    HashSet signals_between = ee.getSignals_between();
    HashSet signals_after = ee.getSignals_after();
    Iterator iter = signals_before.iterator();
    while (iter.hasNext())
    {
      __id = "" + ("BEFORE:" + iter.next());
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    iter = signals_between.iterator();
    while (iter.hasNext())
    {
      __id = "" + ("BETWEEN:" + iter.next());
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    iter = signals_after.iterator();
    while (iter.hasNext())
    {
      __id = "" + ("AFTER:" + iter.next());
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'SignalWords(TemporalRelation_EE)' defined on line 87 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "SignalWords".hashCode(); }
  public boolean equals(Object o) { return o instanceof SignalWords; }
}

