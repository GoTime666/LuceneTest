package lucene.first;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;

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
			Field fieldName=new TextField("name", fileName, Field.Store.YES);
			Field fieldPath=new TextField("path", filePath, Field.Store.YES);
			Field fieldContent=new TextField("content", fileContent, Field.Store.YES);
			Field fieldSize=new TextField("size", fileSize+"", Field.Store.YES);
			Document document=new Document();
			document.add(fieldName);
			document.add(fieldPath);
			document.add(fieldContent);
			document.add(fieldSize);
			indexWriter.addDocument(document);
			
		}
		indexWriter.close();
	}
}
