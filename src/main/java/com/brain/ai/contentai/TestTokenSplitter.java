package com.brain.ai.contentai;

import java.util.List;
import java.util.Map;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;

public class TestTokenSplitter {
  public static void main(String[] args) {

    Document doc1 =
        new Document(
"""
This is a long piece of text that needs to be split into smaller chunks for processing.
I am from Ahmedabad, and I want to ensure that the text is split based on token count.
This text contains multiple sentences and should be split accordingly to maintain context.
It is important to ensure that each chunk is meaningful and retains the original intent of the text.
This is an example of how the text might look like, and it should be split into smaller parts.
This is to ensure that the text is manageable and can be processed effectively.
""",
            Map.of("source", "example.txt"));
    Document doc2 =
        new Document(
            "Another document with content that will be split based on token count.",
            Map.of("source", "example2.txt"));

    TokenTextSplitter splitter = new TokenTextSplitter(10, 4, 1, 50, false);
    List<Document> splitDocuments = splitter.apply(List.of(doc1, doc2));

    for (Document doc : splitDocuments) {
      System.out.println("Chunk: " + doc.getText());
      System.out.println("Metadata: " + doc.getMetadata());
    }
  }
}
