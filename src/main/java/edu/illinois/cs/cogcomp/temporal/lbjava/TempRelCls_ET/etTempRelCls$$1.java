// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000052D8D4B02C04C044FFACE1A2824D3877F46554140B4BB8E1565B3490EE784214CF9F6AA790F666E1460297034133705F84FAE01B982555BA9F49A07886D405A29F6B3F6EC2CDA79E63414C383D4A0326DEA746DD2986DEC79722FF1BD37F67AD302C0660D8934CB61E14A67D4C22015F490F9D328A55749B08FED777A90B38D765AC3A9A5ED063AFD2F5FE3F1CD8DA000000

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


public class etTempRelCls$$1 extends Classifier
{
  private static final BiasTermTemprel __BiasTermTemprel = new BiasTermTemprel();
  private static final SentDist __SentDist = new SentDist();
  private static final TokenDist __TokenDist = new TokenDist();
  private static final POS __POS = new POS();
  private static final PPHead __PPHead = new PPHead();
  private static final SignalWords __SignalWords = new SignalWords();
  private static final ClosestTimexFeats __ClosestTimexFeats = new ClosestTimexFeats();
  private static final IsVerbSRL __IsVerbSRL = new IsVerbSRL();
  private static final IsReporting __IsReporting = new IsReporting();
  private static final CoveringSRL __CoveringSRL = new CoveringSRL();

  public etTempRelCls$$1()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "etTempRelCls$$1";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'etTempRelCls$$1(TemporalRelation_ET)' defined on line 158 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    FeatureVector __result;
    __result = new FeatureVector();
    __result.addFeature(__BiasTermTemprel.featureValue(__example));
    __result.addFeature(__SentDist.featureValue(__example));
    __result.addFeatures(__TokenDist.classify(__example));
    __result.addFeatures(__POS.classify(__example));
    __result.addFeatures(__PPHead.classify(__example));
    __result.addFeatures(__SignalWords.classify(__example));
    __result.addFeatures(__ClosestTimexFeats.classify(__example));
    __result.addFeatures(__IsVerbSRL.classify(__example));
    __result.addFeatures(__IsReporting.classify(__example));
    __result.addFeatures(__CoveringSRL.classify(__example));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'etTempRelCls$$1(TemporalRelation_ET)' defined on line 158 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "etTempRelCls$$1".hashCode(); }
  public boolean equals(Object o) { return o instanceof etTempRelCls$$1; }

  public java.util.LinkedList getCompositeChildren()
  {
    java.util.LinkedList result = new java.util.LinkedList();
    result.add(__BiasTermTemprel);
    result.add(__SentDist);
    result.add(__TokenDist);
    result.add(__POS);
    result.add(__PPHead);
    result.add(__SignalWords);
    result.add(__ClosestTimexFeats);
    result.add(__IsVerbSRL);
    result.add(__IsReporting);
    result.add(__CoveringSRL);
    return result;
  }
}

