package com.brain.ai.contentai.ai.service;

import com.brain.ai.contentai.ai.etl.ETLService;
import com.brain.ai.contentai.ai.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DocumentService {

  private final ETLService etlService;
  private final DocumentRepository documentRepository;

  public DocumentService(ETLService etlService, DocumentRepository documentRepository) {
    this.etlService = etlService;
    this.documentRepository = documentRepository;
  }

  public void processDocument(String documentId, Resource resource) {
    if (documentRepository.existsByDocumentId(documentId)) {
      log.info("Document with ID {} already exists, skipping processing.", documentId);
      return; // Document already processed, skip
    }
    etlService.processPdf(documentId, resource);
  }
}
