package com.brain.ai.contentai.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AIStoreConfig {

//  @Bean
//  public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
//    return PgVectorStore.builder(jdbcTemplate, embeddingModel)
//            .dimensions(1536)                    // Optional: defaults to model dimensions or 1536
//            .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE)       // Optional: defaults to COSINE_DISTANCE
//            .indexType(PgVectorStore.PgIndexType.HNSW)                     // Optional: defaults to HNSW
//            .initializeSchema(true)              // Optional: defaults to false
//            .schemaName("public")                // Optional: defaults to "public"
//            .vectorTableName("vector_store")     // Optional: defaults to "vector_store"
//            .maxDocumentBatchSize(10000)         // Optional: defaults to 10000
//            .build();
//  }
  
}
