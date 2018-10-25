// Modifying this comment will cause the next execution of LBJava to overwrite this file.
// F1B8800000000000000058F8BBE028040154F756A40D88A53A591A4B15B3368C0CE08423EE0EE3C6C8FFE2175944260BCB77EC4E4EA124E3E906326A5F67FE0D9D8E047965C02FE881D532A3FC2302A8165338B3821F503183178C0BEEAF4ACCB0AA4C01C4342CAC199F48B16DEBFB0D5D77F4FE0D8E29DB22B38CA250B4AD21C23958289C7906E14BFB8802D110A7DDF764D813163C800161F008D2AB372C2574FA63E41E1F405A553BDC87100000

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


public class CorpusStats extends Classifier
{
  public CorpusStats()
  {
    containingPackage = "edu.illinois.cs.cogcomp.temporal.lbjava.TempRelCls";
    name = "CorpusStats";
  }

  public String getInputType() { return "edu.illinois.cs.cogcomp.temporal.datastruct.Temporal.TemporalRelation_EE"; }
  public String getOutputType() { return "real[]"; }

  public FeatureVector classify(Object __example)
  {
    if (!(__example instanceof TemporalRelation_EE))
    {
      String type = __example == null ? "null" : __example.getClass().getName();
      System.err.println("Classifier 'CorpusStats(TemporalRelation_EE)' defined on line 109 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    TemporalRelation_EE ee = (TemporalRelation_EE) __example;

    FeatureVector __result;
    __result = new FeatureVector();
    int __featureIndex = 0;
    double __value;

    double total = ee.c_before + ee.c_after + ee.c_vague + ee.c_equal + ee.c_includes + ee.c_included;
    __value = 1.0d * ee.c_before / total;
    __result.addFeature(new RealArrayStringFeature(this.containingPackage, this.name, "", __value, __featureIndex++, 0));
    __value = 1.0d * ee.c_after / total;
    __result.addFeature(new RealArrayStringFeature(this.containingPackage, this.name, "", __value, __featureIndex++, 0));
    __value = 1.0d * ee.c_includes / total;
    __result.addFeature(new RealArrayStringFeature(this.containingPackage, this.name, "", __value, __featureIndex++, 0));
    __value = 1.0d * ee.c_included / total;
    __result.addFeature(new RealArrayStringFeature(this.containingPackage, this.name, "", __value, __featureIndex++, 0));
    __value = 1.0d * ee.c_equal / total;
    __result.addFeature(new RealArrayStringFeature(this.containingPackage, this.name, "", __value, __featureIndex++, 0));
    __value = 1.0d * ee.c_vague / total;
    __result.addFeature(new RealArrayStringFeature(this.containingPackage, this.name, "", __value, __featureIndex++, 0));
    __value = Math.log(total);
    __result.addFeature(new RealArrayStringFeature(this.containingPackage, this.name, "", __value, __featureIndex++, 0));

    for (int __i = 0; __i < __result.featuresSize(); ++__i)
      __result.getFeature(__i).setArrayLength(__featureIndex);

    return __result;
  }

  public double[] realValueArray(Object __example)
  {
    return classify(__example).realValueArray();
  }

  public FeatureVector[] classify(Object[] examples)
  {
    if (!(examples instanceof TemporalRelation_EE[]))
    {
      String type = examples == null ? "null" : examples.getClass().getName();
      System.err.println("Classifier 'CorpusStats(TemporalRelation_EE)' defined on line 109 of eeTempRelCls.lbj received '" + type + "' as input.");
      new Exception().printStackTrace();
      System.exit(1);
    }

    return super.classify(examples);
  }

  public int hashCode() { return "CorpusStats".hashCode(); }
  public boolean equals(Object o) { return o instanceof CorpusStats; }
}

