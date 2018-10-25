// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000052E8BCA02C034144F75EE2A2824C588B5756DA8241C2D41D5AC59E021CC3A4E648AF7F62ABB93766613ECCC817140C00735F0B5B59D655D669503446B9517231CFDB962F8654BB53DED0BC088EAC422CA22D0F9E064292A12CB0EFF1BBB8ECD0B38EF87D822AB3187CCECC3D3BDB5883A82AA34C9ED2A317A20638042D06C16E32E7AEC2754C78EEBD21B74EB49C8F7A2A61EC1B2AF26DC43158EB000000

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


public class eeTempRelCls3$$1 extends Classifier
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
  private static final Lemma __Lemma = new Lemma();

  public eeTempRelCls3$$1()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "eeTempRelCls3$$1";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "mixed%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'eeTempRelCls3$$1(TemporalRelation_EE)' defined on line 194 of eeTempRelCls.lbj received '" + type + "' as input.");
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
    __result.addFeatures(__Lemma.classify(__example));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'eeTempRelCls3$$1(TemporalRelation_EE)' defined on line 194 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "eeTempRelCls3$$1".hashCode(); }
  public boolean equals(Object o) { return o instanceof eeTempRelCls3$$1; }

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
    result.add(__Lemma);
    return result;
  }
}

