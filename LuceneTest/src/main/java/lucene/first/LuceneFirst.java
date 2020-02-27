package lucene.first;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneFirst {
	@Test
	public void createIndex() throws IOException {
		// 存到内存
		// Directory directory=new RAMDirectory();
		// 存到磁盘
		Directory directory = FSDirectory.open(new File("D:\\Resources\\index").toPath());
		IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());
		File dir = new File("D:\\Normal\\BaiduNetdiskDownload\\37.Lucene\\87.lucene\\lucene\\02.参考资料\\searchsource");
		File[] files = dir.listFiles();
		for (File f : files) {
			String fileName = f.getName();
			String filePath = f.getPath();
			String fileContent = FileUtils.readFileToString(f, "utf-8");
			long fileSize = FileUtils.sizeOf(f);
			Field fieldName = new TextField("name", fileName, Field.Store.YES);
			Field fieldPath = new TextField("path", filePath, Field.Store.YES);
			Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
			Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);
			Document document = new Document();
			document.add(fieldName);
			document.add(fieldPath);
			document.add(fieldContent);
			document.add(fieldSize);
			indexWriter.addDocument(document);

		}
		indexWriter.close();
	}

	@Test
	public void searchIndex() throws IOException {
		Directory directory = FSDirectory.open(new File("D:\\Resources\\index").toPath());
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Query query = new TermQuery(new Term("content", "逻"));
		TopDocs topDocs = indexSearcher.search(query, 10);
		long totalHits = topDocs.totalHits;
		System.out.println("查询总记录数:" + totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docId = scoreDoc.doc;
			Document document = indexSearcher.doc(docId);
			System.out.println(document.get("name"));
			System.out.println(document.get("path"));
			System.out.println(document.get("size"));
			System.out.println(document.get("content"));
			System.out.println("-----------------分割线-------------------------------\n\n\n");
		}
		indexReader.close();
	}

	@Test
	public void testTokenStream() throws IOException {
		Analyzer analyzer = new IKAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("", "本以为说好的UTF-16不应该都是2嘛0.0，这里需要科普一下，这个方法的介绍里面有如下这一句话");
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			System.out.println(charTermAttribute.toString());
		}
		tokenStream.close();
	}
}
