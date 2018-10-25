// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B88000000000000000D91915B62C034158FFAC5B03948151CA8F2ADE35570381B785BFE3A4B7BE2C2D4B4317834CFFEB42557573B55A4ED27FC93FD5EE9C89C4B6458F0012F252A4513B2077F48982946DF58245C5E72A8031191BC215808A25A0EBB0B70B2C81B8AACA31EFA56680A6A01811C4638AAD96FEC137548E2FA95CBE0B4DC63F16D3C2702A38741088D27E03E13C84D472C4EA2C8905AA7F098242283D673CB860E26DB3625930B0055F6179DDA370E15FAE66388FBB2424FCE860D66019E34C376A741C97BD3D75FEA01525028152A841D9B80E36C8D3FAD867F4B489F98AA4401AD072D25899BF03BE5695FDA85E9BF3BF52D1009B69FE78227E77227ED6463FCE5FCBBC2ECBB55CDB99ADFE96EA11ADAECBEAAECB185D539DD3DDD119D175371EFD1AB7ABB6067BBCB10CCEC2FEF23FA4B778F1F270E25491400000

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


public class ClosestTimexFeats extends Classifier
{
  public ClosestTimexFeats()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.EventDetector";
    name = "ClosestTimexFeats";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor.EventTokenCandidate"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof EventTokenCandidate))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'ClosestTimexFeats(EventTokenCandidate)' defined on line 111 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    EventTokenCandidate etc = (EventTokenCandidate) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    TimexTemporalNode t1 = etc.getClosestTimex_left();
    TimexTemporalNode t2 = etc.getClosestTimex_right();
    if (t1 != null && !t1.isDCT())
    {
      __id = "ClosestTimex Left:Exist";
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      __id = "" + ("ClosestTimex Left:" + t1.getType());
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      if (t1.getSentId() == etc.getSentId())
      {
        __id = "ClosestTimex Left:Same Sentence";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        if (etc.getTokenId() - t1.getTokenSpan().getSecond() < 3)
        {
          __id = "ClosestTimex Left:TokenDiff<3";
          __value = "true";
          __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        }
        else
        {
          if (etc.getTokenId() - t1.getTokenSpan().getSecond() < 5)
          {
            __id = "ClosestTimex Left:TokenDiff<5";
            __value = "true";
            __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
          }
        }
      }
    }
    if (t2 != null && !t2.isDCT())
    {
      __id = "ClosestTimex Right:Exist";
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      __id = "" + ("ClosestTimex Right:" + t2.getType());
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      if (t2.getSentId() == etc.getSentId())
      {
        __id = "ClosestTimex Right:Same Sentence";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        if (t2.getTokenSpan().getSecond() - etc.getTokenId() < 3)
        {
          __id = "ClosestTimex Right:TokenDiff<3";
          __value = "true";
          __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
        }
        else
        {
          if (t2.getTokenSpan().getSecond() - etc.getTokenId() < 5)
          {
            __id = "ClosestTimex Right:TokenDiff<5";
            __value = "true";
            __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
          }
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
      System.err.println("Classifier 'ClosestTimexFeats(EventTokenCandidate)' defined on line 111 of eventDetector.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "ClosestTimexFeats".hashCode(); }
  public boolean equals(Object o) { return o instanceof ClosestTimexFeats; }
}

