package lucene.first;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

public class SearchIndex {
	private IndexReader indexReader;
	private IndexSearcher indexSearcher;

	@Before
	public void init() throws IOException {
		indexReader = DirectoryReader.open(FSDirectory.open(new File("D:\\Resources\\index").toPath()));
		indexSearcher = new IndexSearcher(indexReader);
	}

	@Test
	public void testRangeQuery() throws IOException {
		Query query = LongPoint.newRangeQuery("size", 0l, 100l);
		printResult(query);
	}
	
	private void printResult(Query query) throws IOException{
		TopDocs topDocs = indexSearcher.search(query, 10);
		System.out.println("总记录数: " + topDocs.totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docId = scoreDoc.doc;
			Document document = indexSearcher.doc(docId);
			System.out.println("\nname:\n" + document.get("name"));
			System.out.println("\npath:\n" + document.get("path"));
			System.out.println("\nsize:\n" + document.get("size"));
			// System.out.println("\ncontent:\n"+document.get("content"));
			System.out.println("-----------------分割线-------------------------------\n\n\n");
		}
		indexReader.close();
	}
	
}
