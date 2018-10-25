// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000055E813B02C030158FFAC3202438858EADA2E491C523492283945BF8218A98427AB88FFDD4501AE477CD7FDDDDB1C5AB64A075834A697F015C9F1B8E87BCD6F35BC9A7171CF9D470A86C673871CC39E5E768D0301CA0BBCCBC192F583F0B0DD0E212C4CED76EB83E3BA559E61FD2B998E311ACC1D8DE6994A65853A8FFDFD34D98941A682B149EC552E38D0EDF130E2F20BCC000000

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


public class IsReporting extends Classifier
{
  public IsReporting()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "IsReporting";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'IsReporting(TemporalRelation_ET)' defined on line 130 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_ET et = (TemporalRelation_ET) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    EventTemporalNode e1 = et.getEventNode();
    boolean e1IsReporting = e1.isReporting();
    __id = "" + ("EVENT IsReporting:" + (e1IsReporting ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'IsReporting(TemporalRelation_ET)' defined on line 130 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "IsReporting".hashCode(); }
  public boolean equals(Object o) { return o instanceof IsReporting; }
}

