package inf_retrieval;

import java.util.*;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.Scanner;

public class LuceneTester {

    String indexDir = "./Index/";
    String dataDir = "./documents/";
    Indexer indexer;
    Searcher searcher;

    public static void main(String[] args) throws IOException {
        LuceneTester tester;
        tester = new LuceneTester();
        try {
            tester.createIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String query = " ";
        while (!query.equals("quit")){
            System.out.println("Enter query or 'quit' for quitting the program");
            query = scanner.nextLine();
            query = tester.queryProcessor(query);
            if (!query.equals("quit"))
                try {
                    tester.search(query);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
        }
    }
    private String queryProcessor(String query) throws IOException {
        String replaced = StringProcessor.diacriticReplacer(query);
        List<String> tokens = StringProcessor.tokenizerStemmer(replaced);

        String new_query = "";
        for (int i = 0; i < tokens.size()-1; i++)
        {
            new_query = new_query + tokens.get(i) + " ";
        }
        if(tokens.size()!=0)
            new_query = new_query + tokens.get(tokens.size() - 1);

        return new_query;
    }

    private void createIndex() throws IOException {
        indexer = new Indexer(indexDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex(dataDir);
        long endTime = System.currentTimeMillis();
        indexer.close();
        System.out.println(numIndexed+" File indexed, time taken: "
                +(endTime-startTime)+" ms");
    }

    private void search(String searchQuery) throws IOException, ParseException {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime));
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: "
                    + doc.get(LuceneConstants.FILE_PATH));
        }
    }
}