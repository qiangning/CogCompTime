// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000059E8BCA02C030154F75682809026F30A5D58A4B8E641AD28B49867AD60B4209C8F009EFBBD44735B028BA18B37EEC9925AD3B142C904E2B652AD38635A569CD05151AF28A6D24592B414808476E03F91C31632C63932185FD0B7C3165AD02C2C1216D849F0276C3E1534454866C50F1B3E7BDF414AD08C737343582F3B678EDB19D22037158D80BB3C7013ECBFF2D2A2B801CA2947F952150035F7C245E108808CC51368EEB51EF78FBC0BC4B842BF128EE507273F0C46100000

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


public class SignalWords extends Classifier
{
  public SignalWords()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "SignalWords";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'SignalWords(EventTokenCandidate)' defined on line 87 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    HashSet signals_before = etc.getSignals_before();
    HashSet signals_after = etc.getSignals_after();
    Iterator iter = signals_before.iterator();
    while (iter.hasNext())
    {
      __id = "" + ("BEFORE:" + iter.next());
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    iter = signals_after.iterator();
    while (iter.hasNext())
    {
      __id = "" + ("AFTER:" + iter.next());
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
      System.err.println("Classifier 'SignalWords(EventTokenCandidate)' defined on line 87 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "SignalWords".hashCode(); }
  public boolean equals(Object o) { return o instanceof SignalWords; }
}

