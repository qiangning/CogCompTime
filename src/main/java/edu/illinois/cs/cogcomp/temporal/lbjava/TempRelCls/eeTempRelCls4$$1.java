// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000052E8D4B02C030144FFA4E05141AE140F8E9CA51501C2940D3ACA47821CC749C649AFFED4AEDEDCB9958576624FB010868B152C63697B555B956981229DCA82990EF1D6B974B21BB5B8DB126D88EAC422C6D241C7A381E45B0D1E50FF7CEEAA2734E0AEBE51A88EE40AE3B333872B7F01B7EA54312E8F665982590630383963E03D113BA33FD01F9A4E5A0A44E79291F349BBF0266A9B9F1080ABEA6E4C000000

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


public class eeTempRelCls4$$1 extends Classifier
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
  private static final CoveringSRL __CoveringSRL = new CoveringSRL();

  public eeTempRelCls4$$1()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "eeTempRelCls4$$1";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "mixed%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'eeTempRelCls4$$1(TemporalRelation_EE)' defined on line 201 of eeTempRelCls.lbj received '" + type + "' as input.");
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
    __result.addFeatures(__CoveringSRL.classify(__example));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'eeTempRelCls4$$1(TemporalRelation_EE)' defined on line 201 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "eeTempRelCls4$$1".hashCode(); }
  public boolean equals(Object o) { return o instanceof eeTempRelCls4$$1; }

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
    result.add(__CoveringSRL);
    return result;
  }
}

