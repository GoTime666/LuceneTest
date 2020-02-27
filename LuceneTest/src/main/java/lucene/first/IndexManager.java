package lucene.first;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.TextField;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexManager {
	
	private IndexWriter indexWriter;
	
	@Before
	public void init() throws IOException {
		indexWriter=new IndexWriter(FSDirectory.open(new File("D:\\Resources\\index").toPath()), new IndexWriterConfig(new IKAnalyzer()));
		
	}
	
	@Test
	public void addDocument() throws Exception{
		//IndexWriter indexWriter=new IndexWriter(FSDirectory.open(new File("D:\\Resources\\index").toPath()), new IndexWriterConfig(new IKAnalyzer()));
		Document document=new Document();
		document.add(new TextField("name","新添加的文件",Field.Store.YES));
		document.add(new TextField("content","新添加的文件内容",Field.Store.NO));
		document.add(new StoredField("path","D:\\Resources\\helo"));
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	
	//删除全部文档
	@Test
	public void deleteAllDocument() throws IOException {
		indexWriter.deleteAll();
		indexWriter.close();
	}
	
	//查询删除文档
	@Test
	public void deleteDocumentByQuery() throws IOException {
		indexWriter.deleteDocuments(new Term("name", "apache"));
		indexWriter.close();
	}
	
	//更新文档(先删除后添加)
	@Test
	public void updateDocument() throws IOException {
		Document document=new Document();
		document.add(new TextField("name", "更新后的文档",Field.Store.YES));
		document.add(new TextField("name", "更新后的文档2",Field.Store.YES));
		document.add(new TextField("name", "更新后的文档3",Field.Store.YES));
		indexWriter.updateDocument(new Term("name", "spring"), document);
		indexWriter.close();
	}
}
