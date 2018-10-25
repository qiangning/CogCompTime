// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000057EC14B6380441400EFB238010D344ADE136D04829E92018A5A7905465D989D673B69DD7A0124FFB76D04F0505ED1766E3E53AD7DE82C50E38EAA2ED67FA452E3EDE04B2FEDD73DEB82BD8E6490149A31C3F2175CEBAF26D278F4C03E6C91F8C7C43D6923A25A776A4FAECC2D70586B66C94A2DD6C93579839C0AF888FF699A1AD65E4853E12902F45497E87C13B089722A0F32A8083A808FACB6BDA1648514C5FCC0FB0A90DA959E9665A6ABEBA94962BD7EED88EF9FFDFBFDD00E855E78815100000

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


public class VerbSRLFeats extends Classifier
{
  public VerbSRLFeats()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "VerbSRLFeats";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'VerbSRLFeats(EventTokenCandidate)' defined on line 140 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    Object[] verbSrls = etc.getVerb_srl_same_sentence().toArray();
    if (verbSrls.length > 0)
    {
      if (verbSrls.length == 1)
      {
        __id = "SRL Same Sent:single";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
      else
      {
        if (verbSrls.length == 2)
        {
          __id = "SRL Same Sent:double";
          __value = "true";
          __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        }
        else
        {
          __id = "SRL Same Sent:multiple";
          __value = "true";
          __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        }
      }
    }
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof EventTokenCandidate[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'VerbSRLFeats(EventTokenCandidate)' defined on line 140 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "VerbSRLFeats".hashCode(); }
  public boolean equals(Object o) { return o instanceof VerbSRLFeats; }
}

