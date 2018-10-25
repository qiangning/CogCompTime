// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B880000000000000005709F4B43013015CFBAC320246196F6E5A5B8717501CA709CDB4ADD96B38BD98C6649A41FBBB9D473E2EF3279466E73F6E5E53C173D192D50C587629B5E8A6D1D1E5247EBD72A6DB270955DE04A51EA72831AE7321D24C3686804854A6F4774A9B7D7DC653783E30D1FB18AF506EE51032944289C53C309CC060798CC5A7DE47F6B2CC0ADDB2DC1BE01A52F20A5D69BB838027CCB8FEAFACDB83A3AE82B50CB189A1990394CC8B1FE761661543C174B85BBE3B0F2B9CE197B0BB4FABF9AF574BD3792D850EAAA418C06F3564560ECF18CA654EF304D62AE495860F4FFD0F469E5EDDCFE1DFB8DF986A3F90F51205D32D100000

package edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET;

import edu.illinois.cs.cogcomp.core.datastructures.IntPair;
import edu.illinois.cs.cogcomp.lbjava.classify.*;
import edu.illinois.cs.cogcomp.lbjava.infer.*;
import edu.illinois.cs.cogcomp.lbjava.io.IOUtilities;
import edu.illinois.cs.cogcomp.lbjava.learn.*;
import edu.illinois.cs.cogcomp.lbjava.parse.*;
import edu.illinois.cs.cogcomp.temporal.configurations.ParamLBJ;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.EventTemporalNode;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET;
import edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TimexTemporalNode;
import java.lang.Object;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class TokenDist extends Classifier
{
  public TokenDist()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls_ET";
    name = "TokenDist";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_ET"; }
  public String getOutputType() { return "discrete%"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_ET))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'TokenDist(TemporalRelation_ET)' defined on line 30 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_ET et = (TemporalRelation_ET) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    String __id;
    String __value;

    EventTemporalNode e = et.getEventNode();
    TimexTemporalNode t = et.getTimexNode();
    __id = "" + ("Timex Len: " + t.getLength());
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    boolean e_first = et.isEventFirstInText();
    __id = "" + (e_first ? "E First" : "T First");
    __value = "true";
    __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    int dist = et.getETDistance();
    if (Math.abs(dist) <= 5)
    {
      __id = "" + ("ET Distance: " + dist);
      __value = "true";
      __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
    }
    else
    {
      if (e_first)
      {
        __id = "ET Distance: -Many";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
      else
      {
        __id = "ET Distance: Many";
        __value = "true";
        __result.addFeature(new DiscretePrimitiveStringFeature(this.containingPackage, this.name, __id, __value, valueIndexOf(__value), (short) 0));
      }
    }
    return __result;
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_ET[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'TokenDist(TemporalRelation_ET)' defined on line 30 of etTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "TokenDist".hashCode(); }
  public boolean equals(Object o) { return o instanceof TokenDist; }
}

