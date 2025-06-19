package com.brain.ai.contentai.ai.etl.extract;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class AiTikaDocumentReader {

  List<Document> loadText(Resource resource) {
    TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
    return tikaDocumentReader.read();
  }
}
