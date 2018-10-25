// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000056D81CA02C030144F7568280D026E30A5D3982A71F0ADBB40D1D60AD642BB2614AFFE6A5CB5F6BCCCB97B77F27B845E20BD628054B2FD2BFD3D9A4E51BD7588EA933B17AE3477DD550AA1CA758F2E0E4AEB051F8115C62516F94D9B4C4983A23A3D011E3D190D9626DFF3D198F4DEB122F132B5B3931B7DCD894F548D9019DC4B54685E4A3BDD4C2A086C73B4C081E7043E7E5AF3D000000

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


public class ClosestTimexFeats extends Classifier
{
  public ClosestTimexFeats()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "ClosestTimexFeats";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'ClosestTimexFeats(TemporalRelation_ET)' defined on line 114 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_ET et = (TemporalRelation_ET) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    HashSet feats = et.getClosestTimexFeats();
    Iterator iter = feats.iterator();
    while (iter.hasNext())
    {
      __id = "" + ("ClosestTimexFeats:" + iter.next());
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
      System.err.println("Classifier 'ClosestTimexFeats(TemporalRelation_ET)' defined on line 114 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "ClosestTimexFeats".hashCode(); }
  public boolean equals(Object o) { return o instanceof ClosestTimexFeats; }
}

