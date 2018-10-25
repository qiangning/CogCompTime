// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D7FCB3B02C0341500EFB27D080D02A2674F1341C08B8538DE2E4128D3A61A6BA96AE22EF77351A5F18A4023CDC7C9B7294A5E6D0C2AB425E20A31F26C1F4581D9DA1996B9619B212800E43DE3D594C509BD644854202C8866EED70B78D8A8ACC615F4D3E39F64DF696CAD8BBB1A522F214C4C84949A612289F8915FC527D25E94D1AEA5C96C46D4587A6FFD5BFFD57AB32FA3F11530C9B2D959E1B078103ECDDFBA65F30D740F387B58D5988285A86B9C6444CAD5D73246E8DF1E1EAE55BB377A6AF34E8E8100000

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


public class PPHead extends Classifier
{
  public PPHead()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "PPHead";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'PPHead(TemporalRelation_EE)' defined on line 72 of eeTempRelCls.lbj received '" + type + "' as input.");
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
    __id = "" + ("E1_PP_HEAD:" + e1.getPp_head());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("E2_PP_HEAD:" + e2.getPp_head());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    if (!e1.getPp_head().equals("N/A"))
    {
      if (e1.getPp_head().equals(e2.getPp_head()))
      {
        __id = "E1E2_SAME_PP_HEAD:YES";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
      else
      {
        __id = "E1E2_SAME_PP_HEAD:NO";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
    }
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'PPHead(TemporalRelation_EE)' defined on line 72 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "PPHead".hashCode(); }
  public boolean equals(Object o) { return o instanceof PPHead; }
}

