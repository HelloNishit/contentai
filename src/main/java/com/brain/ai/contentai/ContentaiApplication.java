package com.brain.ai.contentai;

import org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiEmbeddingAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    exclude = {OllamaChatAutoConfiguration.class, OpenAiEmbeddingAutoConfiguration.class})
public class ContentaiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContentaiApplication.class, args);
  }
}
