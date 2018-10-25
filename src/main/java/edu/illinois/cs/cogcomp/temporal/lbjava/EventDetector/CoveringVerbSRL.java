// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D82914B43C040158FFAC0101679061FC96D38480A182949424D369D623D238567BCEE4A705AFFDD9D8A45450B7BC6EDBFEDB995670A0DB746CB18CDD90D39DDFA1DF6BD669AA831A5EECDB0ADCD8D18603C808CDB685CD2CB1C29203800BDD40F78D4FF9C2CD74BCC6F8CBEF9292D3702BC042E9BB93CEC97052F7061FB356618E5172F7A9A69EA5912F02601255CF6DF702259A8409697A93E8E17886759B3B1898749D54382142A3B5CED9D1496DA3E9E18589DE8C8A298815F2B962798AD1A1698A421997670AA5ADB5378767E7861932E5A4181BDBC0C33F8747E9FBEE05469A1634638A81CA3EE235A5A2179535CAAE6AB2BA7490E330E120A8CFF538CD0AC624ECE53D0565D5155756D5D7F68B8C577F454339476808654D42FA7D54323218C308C2184F3CD910BF11723EB370F12E6BF2200000

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


public class CoveringVerbSRL extends Classifier
{
  public CoveringVerbSRL()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "CoveringVerbSRL";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'CoveringVerbSRL(EventTokenCandidate)' defined on line 158 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    List verb_srl_covering = etc.getVerb_srl_covering();
    int i = 0;
    for (; i < verb_srl_covering.size(); i++)
    {
      Pair srl = (Pair) verb_srl_covering.get(i);
      String pred = ((Constituent) (srl.getSecond())).getAttribute("predicate");
      String feat = "";
      if (SignalWordSet.getInstance().reportingVerbSet.contains(pred))
      {
        feat += ":REPORTING";
      }
      else
      {
        if (SignalWordSet.getInstance().intentionVerbSet.contains(pred))
        {
          feat += ":INTENTION";
        }
        else
        {
          feat += ":OTHER";
        }
      }
      __id = "" + ("COVERING SRL:" + feat);
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
      System.err.println("Classifier 'CoveringVerbSRL(EventTokenCandidate)' defined on line 158 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "CoveringVerbSRL".hashCode(); }
  public boolean equals(Object o) { return o instanceof CoveringVerbSRL; }
}

