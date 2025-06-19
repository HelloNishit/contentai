package com.brain.ai.contentai.ai.etl;

import static com.brain.ai.contentai.ai.etl.ETLService.CONTENTID;

import com.brain.ai.contentai.ai.entity.DocumentEntity;
import com.brain.ai.contentai.ai.repository.DocumentRepository;
import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VectorDocumentLoader {

  @Autowired private VectorStore vectorStore;

  @Autowired private DocumentRepository documentRepository;

  public void load(List<Document> documents) {
    this.vectorStore
        .andThen(
            vectorStore -> {
              documents.forEach(
                  document -> {
                    DocumentEntity documentEntity = new DocumentEntity();
                    documentEntity.setVectorId(document.getId());
                    documentEntity.setDocumentId((String) document.getMetadata().get(CONTENTID));
                    documentRepository.save(documentEntity);
                  });
            })
        .accept(documents);
    //        this.vectorStore.add(documents);
  }
}
