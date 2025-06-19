package com.brain.ai.contentai.ai.etl;

import com.brain.ai.contentai.ai.etl.extract.AiPdfDocumentReader;
import com.brain.ai.contentai.ai.etl.extract.AiTikaDocumentReader;
import com.brain.ai.contentai.ai.etl.transform.AiKeywordEnricher;
import com.brain.ai.contentai.ai.etl.transform.AiTokenTextSplitter;
import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ETLService {

  public static final String CONTENTID = "contentid";
  private final AiPdfDocumentReader aiPdfDocumentReader;
  private final AiTikaDocumentReader aiTikaDocumentReader;
  private final AiKeywordEnricher aiKeywordEnricher;
  private final AiTokenTextSplitter aiTokenTextSplitter;
  private final VectorDocumentLoader vectorDocumentLoader;

  public ETLService(
      AiPdfDocumentReader aiPdfDocumentReader,
      AiTikaDocumentReader aiTikaDocumentReader,
      AiKeywordEnricher aiKeywordEnricher,
      AiTokenTextSplitter aiTokenTextSplitter,
      VectorDocumentLoader vectorDocumentLoader) {
    this.aiPdfDocumentReader = aiPdfDocumentReader;
    this.aiTikaDocumentReader = aiTikaDocumentReader;
    this.aiKeywordEnricher = aiKeywordEnricher;
    this.aiTokenTextSplitter = aiTokenTextSplitter;
    this.vectorDocumentLoader = vectorDocumentLoader;
  }

  public void processPdf(String contentId, Resource resource) {
    List<Document> documents = aiPdfDocumentReader.getDocsFromPdf(resource);
    documents = aiKeywordEnricher.enrichDocuments(documents);
    documents = aiTokenTextSplitter.splitDocuments(documents);
    documents.forEach(document -> document.getMetadata().put(CONTENTID, contentId));
    vectorDocumentLoader.load(documents);
  }
}
