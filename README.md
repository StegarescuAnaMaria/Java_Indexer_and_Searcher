This project is a simulation of a search engine which outputs the path of the documents based on the search string query input. The program has been adapted on Romanian text and deals with diacritics to non-diacritics search and vice-versa.

The search engine is a combination of 2 components - the Indexer and Searcher. The Indexer creates an Inverted Index of the received documents. The Searcher uses the Inverted Index in order to find the documents related to the input query.

An Inverted Index is an index data structure storing a mapping from content, such as words or numbers, to its locations in a document or a set of documents.

# Versions:
-Java: Java(TM) SE Development Kit 19.0.1 (64-bit)

-Lucene: lucene-9.4.1

-Tika: 2.5.0

# Required folders:
-"Index": an empty folder where the IndexWriter object will be saved

-"documents": a folder of .doc(x), .txt. and .pdf documents used for search

-"preprocessed_documents": an initially empty folder where the preprocessed documents will be saved into (after transforming into lowercase, diacritics elimination, tokenization, stop words elimination and stemming)

# Classes:
-LuceneConstants: a class file with static variables for Indexer and Searcher.

-FileLoader: includes the "load" method, which uses the tika module for loading the documents' and return them as strings.

-StringProcessor: 
1. The "diacriticReplacer" method replaces different forms of the same diacritical marks to their default marks.
2. The "tokenizerStemmer" method eliminates the romanian stop words from the stop word list given by RomanianAnalyzer, stems the obtained strings, eliminates diacritics and returns a List<String> object.

-Indexer:
1. The "Indexer" constructor creates an IndexWriter object.
2. The "getDocument" returns a Document type object based on a File object (document content, name and path).
3. The "createIndex" and "indexFile" methods are indexing the files from the "documents" folder using the IndexWriter object. The StringProcessor class output for each file is written in the "preprocessed_documents" folder as .txt files.

-Searcher:
1. The "Searcher" constructor creates IndexSearcher and QueryParser objects.
2. The "search" method transforms the string-type query to a Query object and returns a TopDocs object corresponding with the documents containing the query in a descending order of their correlation value to the query obtained with IndexSearcher.
3. The "getDocument" method receives a ScoreDoc type object corresponding to the top document with the highest correlation and returns said document found with IndexSearcher.

-LuceneTester:
1. The "main" function obtains the user input query.
2. The "queryProcessor" function follows the same processing steps for the query as for the documents, using the methods from the StringProcessor class.
3. The "createIndex" method starts the indexing process of the Indexer class.
4. the "search" method starts the searching process of the Searcher class. The output printed is the number of documents in TopDocs, the duration of the indexing process  and the path of the documents.
