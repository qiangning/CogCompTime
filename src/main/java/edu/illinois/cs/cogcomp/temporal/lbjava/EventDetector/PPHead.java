// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B49CC2E4E2A4D2945580800F84D4C41D07D2B4DCB2909CFCE4DC37E4CCB49CC49440A45A6942B6A28D8EA245B2005569615E18404F2D35B420A02E3304A743DA51A610C4EEA71584000000

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


public class PPHead extends Classifier
{
  public PPHead()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "PPHead";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete"; }


  public FeatureVector classify(Object __example)
  {
    return new FeatureVector(featureValue(__example));
  }

  public Feature featureValue(Object __example)
  {
    String result = discreteValue(__example);
    return new DiscretePrimitiveStringFeature(containingPackage, name, "", result, valueIndexOf(result), (short) allowableValues().length);
  }

  public String discreteValue(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'PPHead(EventTokenCandidate)' defined on line 35 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    return "" + (etc.getPp_head());
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof EventTokenCandidate[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'PPHead(EventTokenCandidate)' defined on line 35 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "PPHead".hashCode(); }
  public boolean equals(Object o) { return o instanceof PPHead; }
}

