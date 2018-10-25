// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000058D81CA028050154F75E2EA4313AA566DA2C580261929148888E42345F40D74E22CF7FED3D586BA673CDB77EC929BD2A12947B4110DBE5976E78952E786A7F62123EAF142E09B829BCC52184616167E0E306121D1B0CE576BCA8462C2ACAB33D2778A8D5442B161592AE370A6734F3DE3895AAFBEE57303955FB2540CE43C5D16BD69295B42A52817A3B779CC08D035B9D10B5AE632B3D13ADAE47EE9B17AE2AF90AAF1C6CA744F60B8950F3A80DBB6C3A285904D8EFA2AFF2F132C8AF04100000

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


public class Lemma_IN_WIN extends Classifier
{
  public Lemma_IN_WIN()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "Lemma_IN_WIN";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete[]"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'Lemma_IN_WIN(EventTokenCandidate)' defined on line 74 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    int __featureIndex = 0;
    String __value;

    int win = etc.getWindow();
    int i;
    String[] lemma_window = etc.getLemma_window();
    for (i = 0; i < win; i++)
    {
      __value = "" + ("PREV_" + (win - i) + "_POS:" + lemma_window[i]);
      __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    }
    for (i = win + 1; i < 2 * win + 1; i++)
    {
      __value = "" + ("NEXT_" + (i - win) + "_POS:" + lemma_window[i]);
      __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    }

    for (int __i = 0; __i < __result.featuresSize(); ++__i)
      __result.getFeature(__i).setArrayLength(__featureIndex);

    return __result;
  }

  public String[] discreteValueArray(Object __example)
  {
    return classify(__example).discreteValueArray();
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof EventTokenCandidate[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'Lemma_IN_WIN(EventTokenCandidate)' defined on line 74 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "Lemma_IN_WIN".hashCode(); }
  public boolean equals(Object o) { return o instanceof Lemma_IN_WIN; }
}

