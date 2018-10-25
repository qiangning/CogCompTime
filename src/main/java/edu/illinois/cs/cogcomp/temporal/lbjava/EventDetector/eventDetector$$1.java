// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D3C814A02C034144FA2F7151412E2CDBB1DA11325AD2D686791263F921C621942887C7359ADDDCCB97C82D160F8117308F64B1F292E01D9FC2B3C69EC08BB72ADC5A55A59C8808178D1C17F076D23074F6EFE318ABA6904186C8C49BEB1A45F3628592A765E23DADA5D3A5935FECBA0402F9C50C019B638F9B2AC890578EF1D635C2D6EF9B75CA4E2EA4F4C908E0D0EBC9F8ADE84085854E4E534B314B3ABA8F5048E06DE7ED000000

package edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector;

import edu.illinois.cs.cogcomp.core.datastructures.IntPair;
import edu.illinois.cs.cogcomp.core.datastructures.Pair;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent;
import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate;
import edu.illinois.cs.cogcomp.temporal.configurations.ParamLBJ;
import edu.illinois.cs.cogcomp.temporal.configurations.SignalWordSet;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TimexTemporalNode;
import java.lang.Object;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class eventDetector$$1 extends Classifier
{
  private static final BiasTermDetector __BiasTermDetector = new BiasTermDetector();
  private static final POS __POS = new POS();
  private static final Lemma __Lemma = new Lemma();
  private static final PPHead __PPHead = new PPHead();
  private static final POS_IN_WIN __POS_IN_WIN = new POS_IN_WIN();
  private static final Lemma_IN_WIN __Lemma_IN_WIN = new Lemma_IN_WIN();
  private static final SignalWords __SignalWords = new SignalWords();
  private static final ClosestTimexFeats __ClosestTimexFeats = new ClosestTimexFeats();
  private static final VerbSRLFeats __VerbSRLFeats = new VerbSRLFeats();
  private static final POS_JOINT_FEAT __POS_JOINT_FEAT = new POS_JOINT_FEAT();
  private static final isReporting __isReporting = new isReporting();
  private static final IsVerbSRL __IsVerbSRL = new IsVerbSRL();
  private static final PREV_EVENT_FEAT __PREV_EVENT_FEAT = new PREV_EVENT_FEAT();

  public eventDetector$$1()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "eventDetector$$1";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'eventDetector$$1(EventTokenCandidate)' defined on line 229 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    FeatureVector __result;
    __result = new FeatureVector();
    __result.addFeature(__BiasTermDetector.featureValue(__example));
    __result.addFeature(__POS.featureValue(__example));
    __result.addFeature(__Lemma.featureValue(__example));
    __result.addFeature(__PPHead.featureValue(__example));
    __result.addFeatures(__POS_IN_WIN.classify(__example));
    __result.addFeatures(__Lemma_IN_WIN.classify(__example));
    __result.addFeatures(__SignalWords.classify(__example));
    __result.addFeatures(__ClosestTimexFeats.classify(__example));
    __result.addFeatures(__VerbSRLFeats.classify(__example));
    __result.addFeatures(__POS_JOINT_FEAT.classify(__example));
    __result.addFeature(__isReporting.featureValue(__example));
    __result.addFeature(__IsVerbSRL.featureValue(__example));
    __result.addFeatures(__PREV_EVENT_FEAT.classify(__example));
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof EventTokenCandidate[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'eventDetector$$1(EventTokenCandidate)' defined on line 229 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "eventDetector$$1".hashCode(); }
  public boolean equals(Object o) { return o instanceof eventDetector$$1; }

  public java.util.LinkedList getCompositeChildren()
  {
    java.util.LinkedList result = new java.util.LinkedList();
    result.add(__BiasTermDetector);
    result.add(__POS);
    result.add(__Lemma);
    result.add(__PPHead);
    result.add(__POS_IN_WIN);
    result.add(__Lemma_IN_WIN);
    result.add(__SignalWords);
    result.add(__ClosestTimexFeats);
    result.add(__VerbSRLFeats);
    result.add(__POS_JOINT_FEAT);
    result.add(__isReporting);
    result.add(__IsVerbSRL);
    result.add(__PREV_EVENT_FEAT);
    return result;
  }
}

