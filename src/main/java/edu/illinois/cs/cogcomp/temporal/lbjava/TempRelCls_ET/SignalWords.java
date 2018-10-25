// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000059F8BCA02C030154F7560148611BF10E361A092AB9A0D61AB498A7D6305319464414AFFE6B1DD885447518B9376EECC149BDB50368499A25BCAB036F0E28C17A3B1B2BE415B46564F654E40E096636AB3DA4ABA230393F28BDEE07436143F6988A4076F61701E4B740EB20AB7C0F97F922F8C0BF128F4B3C7DDEB29D8525F4CED75B84DBEBB34FA59AA14147154594790E6C14816B76938670A1C254CB945C460432F3C22DE10A901BDB06A4DC7658F5AFFCE8CB01229FDB4CF1AF755C22EC54AF5A0A970087BA47DF9F100000

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


public class SignalWords extends Classifier
{
  public SignalWords()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "SignalWords";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'SignalWords(TemporalRelation_ET)' defined on line 92 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_ET et = (TemporalRelation_ET) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    HashSet signals_before = et.getSignals_before();
    HashSet signals_between = et.getSignals_between();
    HashSet signals_after = et.getSignals_after();
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
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'SignalWords(TemporalRelation_ET)' defined on line 92 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "SignalWords".hashCode(); }
  public boolean equals(Object o) { return o instanceof SignalWords; }
}

