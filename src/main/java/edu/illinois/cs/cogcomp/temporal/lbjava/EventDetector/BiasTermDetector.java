// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000B49CC2E4E2A4D2945507ACC4C2E094D2AC57102729B42FB843C5BC253FA4242F3B353FC9313F2523352118A825B4295351C64751AA5108A7A4B82F41492908AD49CA51A6107365C76F84000000

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


public class BiasTermDetector extends Classifier
{
  public BiasTermDetector()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "BiasTermDetector";
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
      System.err.println("Classifier 'BiasTermDetector(EventTokenCandidate)' defined on line 218 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    return "bias";
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof EventTokenCandidate[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'BiasTermDetector(EventTokenCandidate)' defined on line 218 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "BiasTermDetector".hashCode(); }
  public boolean equals(Object o) { return o instanceof BiasTermDetector; }
}

