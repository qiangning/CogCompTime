package edu.illinois.cs.cogcomp.temporal.utils;

import edu.illinois.cs.cogcomp.core.datastructures.IQueryable;
import edu.illinois.cs.cogcomp.core.datastructures.IntPair;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.*;
import edu.illinois.cs.cogcomp.core.transformers.Predicate;
import edu.illinois.cs.cogcomp.nlp.utilities.ParseTreeProperties;
import edu.illinois.cs.cogcomp.temporal.utils.WordNet.WNSim;

import java.util.*;

public class myUtils4TextAnnotation {
    public static boolean isTokenIdValid(TextAnnotation ta, int tokenId){
        int tokenLen = ta.getTokens().length;
        return tokenId>=0&&tokenId<tokenLen;
    }

    public static String retrievePOSAtTokenId(TextAnnotation ta, int tokenId){
        String pos = "N/A";
        if(!isTokenIdValid(ta,tokenId)) return pos;
        TokenLabelView posView = (TokenLabelView) ta.getView(ViewNames.POS);
        if(posView!=null) {
            Constituent c = posView.getConstituentAtToken(tokenId);
            if (c != null)
                pos = c.getLabel();
        }
        return pos;
    }

    public static String[] retrievePOSWindow(TextAnnotation ta, int tokenId, int window){
        String[] pos_window = new String[window*2+1];
        for(int i=-window;i<=window;i++){
            pos_window[i+window] = retrievePOSAtTokenId(ta,tokenId+i);
        }
        return pos_window;
    }

    public static String[] retrievePOSWindow_Span(TextAnnotation ta, IntPair tokenSpan, int window){
        String[] pos_window = new String[window*2];
        for(int i=-window;i<0;i++){// i = -win, -win+1, ..., -2, -1
            pos_window[i+window] = retrievePOSAtTokenId(ta,tokenSpan.getFirst()+i);
            pos_window[window-i-1] = retrievePOSAtTokenId(ta,tokenSpan.getSecond()-i-1);
        }
        return pos_window;
    }

    public static String retrieveLemmaAtTokenId(TextAnnotation ta, int tokenId){
        String lemma = "N/A";
        if(!isTokenIdValid(ta,tokenId)) return lemma;
        TokenLabelView lemmaView = (TokenLabelView) ta.getView(ViewNames.LEMMA);
        if(lemmaView!=null) {
            Constituent c = lemmaView.getConstituentAtToken(tokenId);
            if (c != null)
                lemma = c.getLabel();
        }
        return lemma;
    }

    public static String[] retrieveLemmaWindow(TextAnnotation ta, int tokenId, int window){
        String[] lemma_window = new String[window*2+1];
        for(int i=-window;i<=window;i++){
            lemma_window[i+window] = retrieveLemmaAtTokenId(ta,tokenId+i);
        }
        return lemma_window;
    }

    public static String[] retrieveLemmaWindow_Span(TextAnnotation ta, IntPair tokenSpan, int window){
        String[] lemma_window = new String[window*2+1];
        for(int i=-window;i<0;i++){
            lemma_window[i+window] = retrieveLemmaAtTokenId(ta,tokenSpan.getFirst()+i);
            lemma_window[window-i-1] = retrieveLemmaAtTokenId(ta,tokenSpan.getSecond()-i-1);
        }
        return lemma_window;
    }

    public static String retrievePPHeadOfTokenId(TextAnnotation ta, int tokenId){
        String pp_head = "N/A";
        if(!isTokenIdValid(ta,tokenId)||!ta.hasView(ViewNames.PARSE_CHARNIAK)) return pp_head;
        TreeView charniakView = (TreeView) ta.getView(ViewNames.PARSE_CHARNIAK);
        Predicate<Constituent> ppQuery = new Predicate<Constituent>() {
            private static final long serialVersionUID = -8421140892037175370L;

            @Override
            public Boolean transform(Constituent arg0) {
                return ParseTreeProperties.isNonTerminalPP(arg0.getLabel());
            }
        };
        Constituent constituent = new Constituent("", "", ta,
                tokenId, tokenId+1);
        Predicate<Constituent> query = ppQuery.and(Queries
                .containsConstituent(constituent));
        IQueryable<Constituent> output = charniakView.where(query).orderBy(
                TextAnnotationUtilities.constituentLengthComparator);
        Iterator<Constituent> it = output.iterator();
        if (it.hasNext()) {
            Constituent pp = it.next();
            int spp = pp.getStartSpan();
            pp_head = ta.getToken(spp);
        }
        return pp_head;
    }

    public static List<String> retrieveSynsetUsingLemmaAndPos(WNSim wnsim, String lemma, String pos){
        if(wnsim!=null)
            return wnsim.getSynset(lemma,pos);
        return new ArrayList<>();
    }

    public static HashSet<String> findKeywordsInText(String text, Set<String> keywords, String keywordsTag){
        text = text+" ";
        HashSet<String> matches = new HashSet<>();
        boolean found_connective = false;
        for(String str:keywords){
            if(text.contains(str+" ")){
                found_connective = true;
                matches.add(keywordsTag+":"+str.toLowerCase());
            }
        }
        if(!found_connective)
            matches.add(keywordsTag+":"+"N/A");
        return matches;
    }

    public static int startTokInSent(TextAnnotation ta, int sentid){
        if(sentid>=ta.getNumberOfSentences()||sentid<0)
            return -1;// sentid out of boundary
        int n = ta.getTokens().length;
        int i;
        for(i=0;i<n;i++){
            int tmp = ta.getSentenceId(i);
            if(tmp==sentid)
                break;
        }
        return i;
    }

    public static int endTokInSent(TextAnnotation ta, int sentid){
        // boundary inclusive
        int start = startTokInSent(ta,sentid);
        if(start==-1)
            return -1;
        return start+ta.getSentence(sentid).getTokens().length-1;
    }

    public static String getSurfaceTextInBetween(TextAnnotation ta, int startTokId, int endTokId){
        // boundary inclusive
        if(startTokId>endTokId)
            return "";
        String[] tokens = ta.getTokensInSpan(startTokId,endTokId+1);
        StringBuilder sb = new StringBuilder();
        for(String t:tokens){
            sb.append(t.toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public static String getLemmaTextInBetween(TextAnnotation ta, int startTokId, int endTokId){
        // boundary inclusive
        if(startTokId>endTokId)
            return "";
        StringBuilder sb = new StringBuilder();
        for(int i=startTokId;i<=endTokId;i++){
            String t = retrieveLemmaAtTokenId(ta,i);
            sb.append(t.toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

}
