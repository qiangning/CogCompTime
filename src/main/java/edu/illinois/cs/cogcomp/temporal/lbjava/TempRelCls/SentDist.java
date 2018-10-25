// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D6D8D3B020130144FFAC07552582ADA7E57E592281B790AB1958CDED19CA5888FFDD87822E70BDDCEB73374EC7844A40742AB2ECA6674DD0D72F17B41DB27F2BF6B50195C2728B2854197423404352F9E984DD33036B607089730D0666B85560EC9405DB6361EC77455D8B18266A74DE7953FFB6D64E34AF9FFADBC5640ACDD14E7CDCEA8D000000

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


public class SentDist extends Classifier
{
  public SentDist()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "SentDist";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
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
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'SentDist(TemporalRelation_EE)' defined on line 18 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    int sentDiff = ee.getSentDiff();
    if (sentDiff == 0)
    {
      return "SentDist:Same";
    }
    else
    {
      if (sentDiff == 1)
      {
        return "SentDist:One";
      }
      else
      {
        return "SentDist:Many";
      }
    }
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'SentDist(TemporalRelation_EE)' defined on line 18 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "SentDist".hashCode(); }
  public boolean equals(Object o) { return o instanceof SentDist; }
}

