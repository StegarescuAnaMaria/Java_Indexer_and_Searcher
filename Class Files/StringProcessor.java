package inf_retrieval;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.RomanianStemmer;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;

public class StringProcessor {

    public static String diacriticReplacer(String text){
        String replaced = text.replace("ș","ş");
        replaced = replaced.replace("Ș", "Ş");
        replaced = replaced.replace("ț", "ţ");
        replaced = replaced.replace("Ț", "Ţ");

        return replaced;
    }


    public static List<String> tokenizerStemmer(String text) throws IOException {
        Analyzer analyzer = new RomanianAnalyzer();
        TokenStream result = analyzer.tokenStream(null, text);
        result = new StopFilter(result, RomanianAnalyzer.getDefaultStopSet());
        result = new SnowballFilter(result, new RomanianStemmer());
        CharTermAttribute resultAttr = result.addAttribute(CharTermAttribute.class);
        result.reset();

        List<String> tokens = new ArrayList<>();
        String norm;
        String word;
        while (result.incrementToken()) {
            word = resultAttr.toString();
            norm = Normalizer.normalize(word, Normalizer.Form.NFKD);
            norm = norm.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            tokens.add(norm);
        }
        return tokens;
    }
}
