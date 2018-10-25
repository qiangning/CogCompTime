package edu.illinois.cs.cogcomp.core.datastructures.textannotation;

import edu.illinois.cs.cogcomp.core.datastructures.HasAttributes;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class myTextAnnotationUtilities {
    static private Logger logger = LoggerFactory.getLogger(myTextAnnotationUtilities.class);
    public static void copyViewsFromTo(TextAnnotation ta, TextAnnotation newTA, int sourceStartTokenIndex,
                                       int sourceEndTokenIndex, int offset) {
        for (String vuName : ta.getAvailableViews()) {
            if (ViewNames.TOKENS.equals(vuName) || ViewNames.SENTENCE.equals(vuName))
                continue;
            copyViewFromTo(vuName, ta, newTA, sourceStartTokenIndex, sourceEndTokenIndex, offset);
        }
    }

    public static void copyViewFromTo(String vuName, TextAnnotation ta, TextAnnotation newTA, int sourceStartTokenIndex, int sourceEndTokenIndex, int offset) {
        View vu = ta.getView(vuName);

        if(vu == null) {
            // either the view is not contained, or the view contained is null
            logger.warn("The view `" + vuName + "` for sentence `" + ta.text + "` is empty . . . ");
            return;
        }

        View newVu = null;
        if (newTA.hasView(vuName))
            newVu = newTA.getView(vuName);
        else {
            if (vu instanceof TokenLabelView) {
                newVu = new TokenLabelView(vu.getViewName(), vu.getViewGenerator(), newTA, vu.getScore());
            } else if (vu instanceof SpanLabelView) {
                newVu = new SpanLabelView(vu.getViewName(), vu.getViewGenerator(), newTA, vu.getScore());
            } else if (vu instanceof CoreferenceView) {
                newVu = new CoreferenceView(vu.getViewName(), vu.getViewGenerator(), newTA, vu.getScore());
            } else if (vu instanceof PredicateArgumentView) {
                newVu = new PredicateArgumentView(vu.getViewName(), vu.getViewGenerator(), newTA, vu.getScore());
            } else if (vu instanceof TreeView) {
                newVu = new TreeView(vu.getViewName(), vu.getViewGenerator(), newTA, vu.getScore());
            } else {
                newVu = new View(vu.getViewName(), vu.getViewGenerator(), newTA, vu.getScore());
            }
        }

        Map<Constituent, Constituent> consMap = new HashMap<>();
        List<Constituent> constituentsToCopy = null;

        if (ta.size() <= newTA.size())
            constituentsToCopy = vu.getConstituents();
        else
            constituentsToCopy = vu.getConstituentsCoveringSpan(sourceStartTokenIndex, sourceEndTokenIndex + 1);

        for (Constituent c : constituentsToCopy) {
            // replacing the constituents with a new ones, with token ids shifted
            Constituent newC = copyConstituentWithNewTokenOffsets(newTA, c, offset);
            consMap.put(c, newC);
            newVu.addConstituent(newC);
        }
        for (Relation r : vu.getRelations()) {
            //don't include relations that cross into irrelevant span
            if (!consMap.containsKey(r.getSource()) || !consMap.containsKey(r.getTarget()))
                continue;
            // replacing the relations with a new ones, with their constituents replaced with the shifted ones.
            Relation newR = copyRelation(r, consMap);
            newVu.addRelation(newR);
        }

        newTA.addView(vuName, newVu);

        if (vu instanceof TreeView) {
            ((TreeView) newVu).makeTrees();
        }
        else if (vu instanceof PredicateArgumentView) {
            //((PredicateArgumentView) vu).findPredicates();
            //((PredicateArgumentView) vu).getPredicates();
            myFindPredicates((PredicateArgumentView) newVu);
        }
    }
    public static Constituent copyConstituentWithNewTokenOffsets(TextAnnotation newTA, Constituent c, int offset) {
        int newStart = c.getStartSpan() + offset;
        int newEnd = c.getEndSpan() + offset;

        assert(newStart >= 0 && newStart <= newTA.size());
        assert(newEnd >= 0 && newEnd <= newTA.size());

        Constituent newCon = null;
        if (null != c.getLabelsToScores())
            newCon = new Constituent(c.getLabelsToScores(), c.getViewName(), newTA, newStart, newEnd);
        else
            newCon = new Constituent(c.getLabel(), c.getConstituentScore(), c.getViewName(), newTA, newStart, newEnd);

        copyAttributesFromTo(c, newCon);

        return newCon;
    }
    public static void copyAttributesFromTo(HasAttributes origObj, HasAttributes newObj) {
        for(String key : origObj.getAttributeKeys())
            newObj.addAttribute(key, origObj.getAttribute(key));
    }
    public static Relation copyRelation(Relation r, Map<Constituent, Constituent> consMap) {
        Relation newRel = null;

        if ( null == r.getLabelsToScores() )
            newRel = new Relation(r.getRelationName(), consMap.get(r.getSource()), consMap.get(r.getTarget()), r.getScore());
        else
            newRel = new Relation(r.getLabelsToScores(), consMap.get(r.getSource()), consMap.get(r.getTarget()));

        copyAttributesFromTo(r, newRel);

        return newRel;
    }
    public static void myFindPredicates(PredicateArgumentView vu) {
        List<Constituent> predicates = vu.getPredicates();
        predicates.clear();
        // The hypothesis is that all nodes with no incoming edges are predicates.
        for (Constituent c : vu.getConstituents()) {
            if (c.getIncomingRelations().size() == 0)
                predicates.add(c);
        }
    }
}
