// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000592915B62C034158FFACD516290562B7EAAF0E4A36582E4C6116F45A6B75716521942A0BD0FFBF29EA6D925BDE8CB49B9FEE9B7A7E6276A2398A1FE161B4F7598FB2F7E172FCEF4362EF109BE85C702F95AC37697AA110576416CF00FD0D47BB398784C02031B0E07B8A71565C2A48E808D6084DCCDD408FEB8206038ABFC674468D027241625B8C9A44DC8558C5128376FA01E4F9CF0D370CDB09903D5361645C30D27F8793ADEDFA05EA3152D225919665F02288691A7F68AC9A5BAD9271C9E4731235A12D2A03D47ECAE46929C4C10523EBD2323EA18916E1740B1121889A0C8BA6D1A26F58524EAB6DDF225621C8081C98DF6A72EC883164B534039639990EA433DB731735BA3FB9D7628FDCCE76593588F5288AB24A608BF4657C2F06164DA5614371D36F361AE732F22926A698B312579B296E022EFCD1A7535D665EA9CE7E7AAFDFB2B5107D6675C40F65B5535D96BEF09FF2B5734EE55FB3ACEC1A515DFF92C1F700F94A10A83B400000

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


public class PREV_EVENT_FEAT extends Classifier
{
  public PREV_EVENT_FEAT()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "PREV_EVENT_FEAT";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'PREV_EVENT_FEAT(EventTokenCandidate)' defined on line 178 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    EventTokenCandidate prev_etc = etc.getPrev_event();
    if (prev_etc != null && prev_etc.getSentId() == etc.getSentId())
    {
      __id = "" + ("PREV_EVENT LABEL:" + prev_etc.getLabel());
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      if (prev_etc.getVerb_srl() != null)
      {
        __id = "PREV_EVENT IS SRL:Yes";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        __id = "" + ("PREV_EVENT IS SRL:Yes" + ":" + prev_etc.getLabel());
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        List allSRL = etc.getVerb_srl_covering();
        int i = 0;
        for (; i < allSRL.size(); i++)
        {
          Pair srl = (Pair) allSRL.get(i);
          if (prev_etc.getVerb_srl() == (Constituent) srl.getSecond())
          {
            __id = "PREV_EVENT Covering THIS:Yes";
            __value = "true";
            __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
            __id = "" + ("PREV_EVENT Covering THIS:Yes" + ":" + prev_etc.getLabel());
            __value = "true";
            __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
          }
        }
      }
      else
      {
        __id = "PREV_EVENT IS SRL:No";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        __id = "" + ("PREV_EVENT IS SRL:No" + ":" + prev_etc.getLabel());
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
      if (prev_etc.isReporting())
      {
        __id = "PREV_EVENT Type:Reporting";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        __id = "" + ("PREV_EVENT Type:Reporting" + ":" + prev_etc.getLabel());
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
      else
      {
        if (prev_etc.isIntention())
        {
          __id = "PREV_EVENT Type:Intention";
          __value = "true";
          __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
          __id = "" + ("PREV_EVENT Type:Intention" + ":" + prev_etc.getLabel());
          __value = "true";
          __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        }
        else
        {
          __id = "PREV_EVENT Type:Other";
          __value = "true";
          __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
          __id = "" + ("PREV_EVENT Type:Other" + ":" + prev_etc.getLabel());
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
      System.err.println("Classifier 'PREV_EVENT_FEAT(EventTokenCandidate)' defined on line 178 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "PREV_EVENT_FEAT".hashCode(); }
  public boolean equals(Object o) { return o instanceof PREV_EVENT_FEAT; }
}

