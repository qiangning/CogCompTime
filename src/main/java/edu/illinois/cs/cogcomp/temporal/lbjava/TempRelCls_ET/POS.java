// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000058091CA62C040168F55E7205848059E95DE1378E5CA4D58E14461966A9E04CDD0BB3A6B41FDDBBB157D41B2D3ECCC73FDCFC4DCEFDC190DD16EFCB8C51D6A3BE6DDEB05BB616B6475A042506A7F8F645B3232762666B6201E13C874D094F3B8DBCB89041F68E3F718290CE76760D391F48CA2D1E6F833490A88CCDAFCB0C812E6B49815A5D7574D3474F48AEA9AA4545D50D18FA446737B712E8D43BC5184776DBED3B9897F4BB1BCADEE36ED4C9C0839B6E8D8083C8E162877BE0978E06A719F8A523D8C748E695611E1B3C738E7D7A95F939352E4B7B4E55A8C783A9F4752AE5EF1DBAB1A79FB5F78F1084D801F4A1200000

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


public class POS extends Classifier
{
  public POS()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "POS";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'POS(TemporalRelation_ET)' defined on line 49 of etTempRelCls.lbj received '" + type + "' as input.");
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
    __id = "" + ("E_POS:" + e.getPos());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("T_Type:" + t.getType());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("ET_POS_Type:" + e.getPos() + ":" + t.getType());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    String[] e_pos_win = e.getPos_window();
    String[] t_pos_win = t.getPos_window();
    int i = 0;
    for (; i < e_pos_win.length; i++)
    {
      __id = "" + ("E_POS_WIN:" + i + ":" + e_pos_win[i]);
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    for (i = 0; i < t_pos_win.length; i++)
    {
      __id = "" + ("T_POS_WIN:" + i + ":" + t_pos_win[i]);
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
      System.err.println("Classifier 'POS(TemporalRelation_ET)' defined on line 49 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "POS".hashCode(); }
  public boolean equals(Object o) { return o instanceof POS; }
}

