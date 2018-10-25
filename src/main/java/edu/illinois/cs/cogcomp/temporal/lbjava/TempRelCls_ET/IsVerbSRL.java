// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D4E8BCA02C034144F756C080922A0DD6B617595802D54D250755A5B3841262294AE64CF77B11C7CA68B7FC1689164F767C0C526FEB1ABEF85D146DCBEDCABE4754DD581DA9655D06840BD53E10577A90F13A4B301C415CCC73716873C8F4994E8EDA5DCECCCCFB5D15C43A81FE6DB3D231C2A08994BEC1E96C312453AACAF7B62318514EF77CE02E44F20914496542376073137C3F50997C659DCC000000

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


public class IsVerbSRL extends Classifier
{
  public IsVerbSRL()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "IsVerbSRL";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'IsVerbSRL(TemporalRelation_ET)' defined on line 123 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_ET et = (TemporalRelation_ET) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    EventTemporalNode e1 = et.getEventNode();
    boolean e1IsVerbSRL = e1.getVerb_srl() != null;
    __id = "" + ("EVENT IsVerbSRL:" + (e1IsVerbSRL ? "Yes" : "No"));
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'IsVerbSRL(TemporalRelation_ET)' defined on line 123 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "IsVerbSRL".hashCode(); }
  public boolean equals(Object o) { return o instanceof IsVerbSRL; }
}

