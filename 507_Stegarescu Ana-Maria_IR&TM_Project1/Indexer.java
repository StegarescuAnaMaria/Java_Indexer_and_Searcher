package inf_retrieval;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;

public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        RomanianAnalyzer analyzer = new RomanianAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(indexDirectory, iwc);
    }

    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();

        TextField contentField = new TextField(LuceneConstants.CONTENTS, new FileReader(file));
        TextField fileNameField = new TextField(LuceneConstants.FILE_NAME,
                file.getName(),TextField.Store.YES);
        TextField filePathField = new TextField(LuceneConstants.FILE_PATH,
                file.getCanonicalPath(),TextField.Store.YES);

        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        return document;
    }

    private void indexFile(File file) throws IOException {
        System.out.println("Indexing "+file.getCanonicalPath());

        String text = FileLoader.load(file);

        String replaced = StringProcessor.diacriticReplacer(text);
        List<String> tokens = StringProcessor.tokenizerStemmer(replaced);

        String fileName = "./preprocessed_documents/"+file.getName()+".txt";


        File file_proc = new File(fileName);
        FileWriter fw = new FileWriter(file_proc);

        for(String w:tokens)
            fw.write(w+" ");

        Document document = getDocument(file_proc);
        writer.addDocument(document);
    }

    public int createIndex(String dataDirPath) throws IOException {
        File[] files = new File(dataDirPath).listFiles();
        for (File file: files){
            if(!file.isDirectory()
                    && !file.isHidden()
                    && file.exists()
                    && file.canRead()
            ){
                indexFile(file);
            }
        }
        return writer.getDocStats().numDocs;
    }
}