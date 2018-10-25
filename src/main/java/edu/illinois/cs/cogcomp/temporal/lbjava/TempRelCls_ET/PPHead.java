// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000057FCDCA0280441500E759BD040E019DEDA612430E62B12763DA609C3590EF5A36401DBB7A286906CDDDC9F8B7FC441757E216037292D348195A096197916274421A983FC4B05403C963B4A719870233DB0F3F8040AD631BD7589E2B6FDC2EB62517A87E80A9106795F0B246518890D25A6F48BB3781D280D23958EB5B58839346ACA1385532B66AD6C712B663ED363EE578945613F75E23ECB9FE4B86ADC87B678BF6AA470EEEFBD7F422063C5D7312960DF5CEF1E7D633F104CF06F34F7100000

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


public class PPHead extends Classifier
{
  public PPHead()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "PPHead";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'PPHead(TemporalRelation_ET)' defined on line 77 of etTempRelCls.lbj received '" + type + "' as input.");
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
    __id = "" + ("E_PP_HEAD:" + e.getPp_head());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("T_PP_HEAD:" + t.getPp_head());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    if (!e.getPp_head().equals("N/A"))
    {
      if (e.getPp_head().equals(t.getPp_head()))
      {
        __id = "ET_SAME_PP_HEAD:YES";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
      else
      {
        __id = "ET_SAME_PP_HEAD:NO";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
    }
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'PPHead(TemporalRelation_ET)' defined on line 77 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "PPHead".hashCode(); }
  public boolean equals(Object o) { return o instanceof PPHead; }
}

