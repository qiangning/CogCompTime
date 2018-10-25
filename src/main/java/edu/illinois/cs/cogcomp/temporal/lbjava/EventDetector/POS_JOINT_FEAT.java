// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B880000000000000005909F4B43C04015CFBAC0101213454D36DA780DEA8A4FF40B1AA05AC21A91BE0ADDD09D5DC14AFDDDD94A9625272F6BB3FEDCBFDBDDC8CC6B04B87101F2692F971F43F44E388821F5CF0A2B98EF4453E4556495A6110DE63081D00E71696B025B38C5B18B7E1F5DE0D6CAD8F1CD532D71EE7F96B2E49FAD193FCF303DCA3BC9B4E10B3C0A2380E17F99A89DC2A1A701654A01CBAEC574F01C61FD8766F83E7411D4A7EF32BA9502561A425D2BF59456AB4643B44D47C5F683546959A76FCC3EC873ED5710E3935FADD63C8838DD12C0307F94DD66F226529BB8FC4E10050CDE4A39AFD6F49FB6ADC9A4F1A521724807353FE61E2B338E177E2ED29A9B4E8AEC8FF7EE1EF00447256C532200000

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


public class POS_JOINT_FEAT extends Classifier
{
  public POS_JOINT_FEAT()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "POS_JOINT_FEAT";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'POS_JOINT_FEAT(EventTokenCandidate)' defined on line 53 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    String pos = etc.getPos();
    String lemma = etc.getLemma();
    String pphead = etc.getPp_head();
    __id = "" + ("POS_LEMMA:" + pos + ":" + lemma);
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    __id = "" + ("POS_PPHEAD:" + pos + ":" + pphead);
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    int win = etc.getWindow();
    int i;
    String[] pos_window = etc.getPos_window();
    for (i = 0; i < win; i++)
    {
      __id = "" + ("POS_PREV_" + (win - i) + "_POS:" + pos + ":" + pos_window[i]);
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    for (i = win + 1; i < 2 * win + 1; i++)
    {
      __id = "" + ("POS_NEXT_" + (i - win) + "_POS:" + pos + ":" + pos_window[i]);
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof EventTokenCandidate[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'POS_JOINT_FEAT(EventTokenCandidate)' defined on line 53 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "POS_JOINT_FEAT".hashCode(); }
  public boolean equals(Object o) { return o instanceof POS_JOINT_FEAT; }
}

