This project is a simulation of a search engine which outputs the path of the documents based on the search string query input. The program has been adapted on romanian text and deals with diacritics to non-diacritics search and vice-versa.

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
-LuceneConstants: a class file with static variables for Indexer and Searcher ()
