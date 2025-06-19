package com.brain.ai.contentai.ai.service;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VectorService {

  @Autowired private VectorStore vectorStore;

  public boolean isDocumentAvailable(String id) {
    return false;
  }
}
