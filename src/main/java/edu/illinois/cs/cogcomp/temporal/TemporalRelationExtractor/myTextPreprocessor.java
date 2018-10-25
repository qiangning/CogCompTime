package edu.illinois.cs.cogcomp.temporal.TemporalRelationExtractor;

import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.*;
import edu.illinois.cs.cogcomp.nlp.tokenizer.StatefulTokenizer;
import edu.illinois.cs.cogcomp.nlp.utility.TokenizerTextAnnotationBuilder;
import edu.illinois.cs.cogcomp.pipeline.main.PipelineFactory;

import java.util.List;

public class myTextPreprocessor {
    private AnnotatorService annotator;
    private TokenizerTextAnnotationBuilder tab;

    public myTextPreprocessor() throws Exception{
        annotator = PipelineFactory.buildPipeline(
                ViewNames.POS,
                ViewNames.SHALLOW_PARSE,
                ViewNames.PARSE_STANFORD,
                ViewNames.NER_CONLL,
                ViewNames.LEMMA,
                ViewNames.SRL_VERB
        );
        tab = new TokenizerTextAnnotationBuilder(new StatefulTokenizer());
    }

    public TextAnnotation extractTextAnnotation(String text)  throws Exception{
        TextAnnotation ta = tab.createTextAnnotation(text);
        TextAnnotation[] sentTa = new TextAnnotation[ta.sentences().size()];
        for(int sentenceId=0;sentenceId<ta.sentences().size();sentenceId++){// without this, empty views of ta will be added
            sentTa[sentenceId]= TextAnnotationUtilities.getSubTextAnnotation(ta, sentenceId);
            annotator.annotateTextAnnotation(sentTa[sentenceId], true);
        }
        for (int sentenceId = 0; sentenceId < ta.sentences().size(); ++sentenceId) {
            int start = ta.getSentence(sentenceId).getStartSpan();
            int end = ta.getSentence(sentenceId).getEndSpan();
            myTextAnnotationUtilities.copyViewsFromTo(sentTa[sentenceId],ta, start, end, start);
        }
        return ta;
    }

    public static void main(String[] args) throws Exception{
        myTextPreprocessor exc = new myTextPreprocessor();
        String text = "I failed to do it.";
        TextAnnotation ta = exc.extractTextAnnotation(text);
        TreeView dep = (TreeView) ta.getView(ViewNames.DEPENDENCY);
        String[] toks = ta.getTokens();
        for(int i=0;i<toks.length;i++){
            List<Constituent> clist = dep.getConstituentsCoveringToken(i);
            for(Constituent c:clist){
                List<Relation> rels = c.getOutgoingRelations();
                for(Relation r:rels){
                    System.out.println(r);
                    System.out.println(r.getRelationName());
                }
                rels = c.getIncomingRelations();
                for(Relation r:rels){
                    int tokid = r.getTarget().getStartSpan();
                    System.out.println(r.getSource());
                    System.out.println(r.getTarget());
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}
