// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000052E8BCA02C034144F752B8A0A0171EED59A51501C2D40D5AC59E05289749CDB24DFB731DDDC9333B890E66C0B05085489A78FDB7E6A9DC2B25AC4EB8121792E3AD6BC665A6BB65B374C61934D94687DAC02AC1C1B86563DB01FF1BBBA92D05089F4438A2AB318682ECD819CFD35E185BAD7AC3DB9D809450F981C26D50CC74CF4D99F68CF43DF5A6CE15E298B83A65F5759474286B000000

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


public class eeTempRelCls$$1 extends Classifier
{
  private static final BiasTermTemprel __BiasTermTemprel = new BiasTermTemprel();
  private static final SentDist __SentDist = new SentDist();
  private static final TokenDist __TokenDist = new TokenDist();
  private static final POS __POS = new POS();
  private static final SameSynSet __SameSynSet = new SameSynSet();
  private static final PPHead __PPHead = new PPHead();
  private static final SignalWords __SignalWords = new SignalWords();
  private static final CorpusStats __CorpusStats = new CorpusStats();
  private static final ClosestTimexFeats __ClosestTimexFeats = new ClosestTimexFeats();
  private static final IsVerbSRL __IsVerbSRL = new IsVerbSRL();
  private static final IsReporting __IsReporting = new IsReporting();

  public eeTempRelCls$$1()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "eeTempRelCls$$1";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "mixed%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'eeTempRelCls$$1(TemporalRelation_EE)' defined on line 180 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    FeatureVector __result;
    __result = new FeatureVector();
    __result.addFeature(__BiasTermTemprel.featureValue(__example));
    __result.addFeature(__SentDist.featureValue(__example));
    __result.addFeature(__TokenDist.featureValue(__example));
    __result.addFeatures(__POS.classify(__example));
    __result.addFeature(__SameSynSet.featureValue(__example));
    __result.addFeatures(__PPHead.classify(__example));
    __result.addFeatures(__SignalWords.classify(__example));
    __result.addFeatures(__CorpusStats.classify(__example));
    __result.addFeatures(__ClosestTimexFeats.classify(__example));
    __result.addFeatures(__IsVerbSRL.classify(__example));
    __result.addFeatures(__IsReporting.classify(__example));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'eeTempRelCls$$1(TemporalRelation_EE)' defined on line 180 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "eeTempRelCls$$1".hashCode(); }
  public boolean equals(Object o) { return o instanceof eeTempRelCls$$1; }

  public java.util.LinkedList getCompositeChildren()
  {
    java.util.LinkedList result = new java.util.LinkedList();
    result.add(__BiasTermTemprel);
    result.add(__SentDist);
    result.add(__TokenDist);
    result.add(__POS);
    result.add(__SameSynSet);
    result.add(__PPHead);
    result.add(__SignalWords);
    result.add(__CorpusStats);
    result.add(__ClosestTimexFeats);
    result.add(__IsVerbSRL);
    result.add(__IsReporting);
    return result;
  }
}

