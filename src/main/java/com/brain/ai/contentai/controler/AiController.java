package com.brain.ai.contentai.controler;

import com.brain.ai.contentai.ai.service.AIService;
import java.io.IOException;
import java.util.List;
import org.apache.tika.Tika;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/ai")
public class AiController {

  private final AIService aiService;
  Tika tika = new Tika();

  public AiController(AIService aiService) {
    this.aiService = aiService;
  }

  @GetMapping("/documents")
  public String askForAllDocument(
      @RequestParam("conversationId") String conversationId,
      @RequestParam("question") String question) {
    return aiService.askAboutDocument(conversationId, null, question);
  }

  @PostMapping()
  public String ask(
      @RequestParam("conversationId") String conversationId,
      @RequestParam("documentId") String documentId,
      @RequestParam("file") MultipartFile file,
      @RequestParam("question") String question) {

    try {
      String mimeType = tika.detect(file.getInputStream());
      if (!mimeType.equals("application/pdf")) {
        throw new IllegalArgumentException("Only PDF files are supported");
      }
      String response =
          aiService.askAboutDocument(conversationId, question, documentId, file.getResource());
      System.out.println(response);
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @PutMapping()
  public String ask(
      @RequestParam("conversationId") String conversationId,
      @RequestParam("documentId") String documentId,
      @RequestParam("question") String question) {
    return aiService.askAboutDocument(conversationId, List.of(documentId), question);
  }
}
