// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000058E8DCA028050158F55E0EA4313AA56ADA2C58B13949A024E22935C01C534F29B80FDDB9B5095BA673C9FBF6A6EEE4D291A3698C639BA4255BF42573EB3963543752DBEA47DC57568046E4E12A00F00B638E9536565B9E58CCE957DD4FEA71EBC2E019B9695F5463F6D47AAF79B36A3BF866B17E6A58BC2EEC4AD88CE0BC1EBFE908A32DD11C9C6B1FE4930F1EA5A6006F4E6749CFBB4BAE8C832791268172D6CD7CCFD3CB0C4EB48F104A1F1A8730856E524F7003C3109A3E74BA63100000

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


public class POS_IN_WIN extends Classifier
{
  public POS_IN_WIN()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "POS_IN_WIN";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete[]"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'POS_IN_WIN(EventTokenCandidate)' defined on line 40 of eventDetector.lbj received '" + type + "' as input.");
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
    String[] pos_window = etc.getPos_window();
    for (i = 0; i < win; i++)
    {
      __value = "" + ("PREV_" + (win - i) + "_POS:" + pos_window[i]);
      __result.addFeature(new DiscreteArrayStringFeature(this.containingPackage, this.name, "", __value, valueIndexOf(__value), (short) 0, __featureIndex++, 0));
    }
    for (i = win + 1; i < 2 * win + 1; i++)
    {
      __value = "" + ("NEXT_" + (i - win) + "_POS:" + pos_window[i]);
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
      System.err.println("Classifier 'POS_IN_WIN(EventTokenCandidate)' defined on line 40 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "POS_IN_WIN".hashCode(); }
  public boolean equals(Object o) { return o instanceof POS_IN_WIN; }
}

