package com.brain.ai.contentai.ai.repository;

import com.brain.ai.contentai.ai.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
  boolean existsByDocumentId(String documentId);
}
