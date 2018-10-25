// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000056D81CA02C030144F7568280D026E30A5D39454F2E14B7790A3AD04BD84675C284FFDD4B873F6BCCCB97B73F27D845E20BD638054B6FD178D1D9A4E53BB7688EAD31B57AE34F79AA20960BE51E38DB396EC45CD7241B9458D705FF526A4C1491D96880F9E848EC31BEF79E44CBB1FD219F419D6C9C19386E6C4AF22CE588CEF4B54685ECA3BDFCC2A086C71B4C881FB08BF31D783D000000

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


public class ClosestTimexFeats extends Classifier
{
  public ClosestTimexFeats()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "ClosestTimexFeats";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'ClosestTimexFeats(TemporalRelation_EE)' defined on line 121 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    HashSet feats = ee.getClosestTimexFeats();
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
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'ClosestTimexFeats(TemporalRelation_EE)' defined on line 121 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "ClosestTimexFeats".hashCode(); }
  public boolean equals(Object o) { return o instanceof ClosestTimexFeats; }
}

