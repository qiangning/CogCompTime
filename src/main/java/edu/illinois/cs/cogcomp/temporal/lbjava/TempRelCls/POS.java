// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000058099CA02C030168F5568280D25C263747936F0E545CA0E144241B36D04D44298A701FDDD98AD544B8790466EBF716271667A1D26F0613F4DF51E1F4A4765E21BCCCA05297290026003EE3CD029B0A4B51133593206C031AD7450AD45D95FE0DD4DF064FB05653ABAC43DB51A605A140F298935181A70129BA3A61AC8F10C01CAE336341B6826DD45CCBD06BCE240FA35C9A5D2461B9D2908F9491E7512D55EA4DEEF9BABABACD09CAD42BF62584B028673811CE5960F96203E6504452AC2CE186C16810D98F3E81C7D3D9D3BDA86A77DACD88D6DDCFEF23F770D3318DFB406D590CE772CDF1AADF96D392200000

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


public class POS extends Classifier
{
  public POS()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "POS";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'POS(TemporalRelation_EE)' defined on line 33 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    EventTemporalNode e1 = ee.getSourceNode();
    EventTemporalNode e2 = ee.getTargetNode();
    __id = "" + ("E1_POS:" + e1.getPos());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E2_POS:" + e2.getPos());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E1_E2_POS:" + e1.getPos() + ":" + e2.getPos());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    String[] e1_pos_win = e1.getPos_window();
    String[] e2_pos_win = e2.getPos_window();
    int i = 0;
    for (; i < e1_pos_win.length; i++)
    {
      __id = "" + ("E1_POS_WIN:" + i + ":" + e1_pos_win[i]);
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    for (i = 0; i < e2_pos_win.length; i++)
    {
      __id = "" + ("E2_POS_WIN:" + i + ":" + e2_pos_win[i]);
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
      System.err.println("Classifier 'POS(TemporalRelation_EE)' defined on line 33 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "POS".hashCode(); }
  public boolean equals(Object o) { return o instanceof POS; }
}

