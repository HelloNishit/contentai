package com.brain.ai.contentai.ai.service;

import static com.brain.ai.contentai.ai.etl.ETLService.CONTENTID;

import com.brain.ai.contentai.ai.advisor.ReReadingAdvisor;
import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class AIService {

  private final DocumentService documentService;
  private final ChatClient chatClient;
  private final VectorStore vectorStore;
  private final ChatClient.Builder chatClientBuilder;
  private final ChatMemory chatMemory;

  public AIService(
      DocumentService documentService,
      ChatClient.Builder chatClientBuilder,
      ChatMemory chatMemory,
      VectorStore vectorStore) {
    this.chatClientBuilder = chatClientBuilder;
    this.documentService = documentService;
    this.vectorStore = vectorStore;
    this.chatMemory = chatMemory;
    QuestionAnswerAdvisor questionAnswerAdvisor =
        QuestionAnswerAdvisor.builder(vectorStore)
            .searchRequest(SearchRequest.builder().build())
            .build();
    this.chatClient =
        chatClientBuilder
            .defaultAdvisors(
                new ReReadingAdvisor(),
                PromptChatMemoryAdvisor.builder(chatMemory).build(),
                questionAnswerAdvisor)
            .build();
  }

  public String askAboutDocument(String conversationId, List<String> documentIds, String question) {

    ChatClient.CallResponseSpec responseSpec =
        chatClient
            .prompt("You are a precise and fact-based assistant.")
            .user(question)
            .advisors(
                advisorSpec -> {
                  advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId);
                  addQuestionAnswerFilter(advisorSpec, documentIds);
                })
            .call();
    return responseSpec.content();
  }

  private void addQuestionAnswerFilter(
      ChatClient.AdvisorSpec advisorSpec, List<String> documentIds) {
      if(CollectionUtils.isEmpty(documentIds)) {
          return; // No document IDs provided, no filter needed
      }
    StringBuilder filterExpression = new StringBuilder();
    for (int i = 0; i < documentIds.size(); i++) {
      if (i > 0) {
        filterExpression.append(" || ");
      }
      filterExpression.append(CONTENTID).append(" == '").append(documentIds.get(i)).append("'");
    }
    if (!filterExpression.isEmpty()) {
      advisorSpec.param(QuestionAnswerAdvisor.FILTER_EXPRESSION, filterExpression);
    }
  }

  public String askAboutDocument(
      String conversationId, String question, String documentId, Resource file) {
    documentService.processDocument(documentId, file);
    return askAboutDocument(conversationId, List.of(documentId), question);
  }

//  public String askAboutDocument(String conversationId, String documentId, String question) {
//
//    ChatClient.CallResponseSpec responseSpec =
//        chatClient
//            .prompt()
//            .user(question)
//            .advisors(
//                advisorSpec -> {
//                  advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId);
//                  advisorSpec.param(
//                      QuestionAnswerAdvisor.FILTER_EXPRESSION,
//                      CONTENTID + " == '" + documentId + "'");
//                })
//            .call();
//    return responseSpec.content();
//  }

  //  @PostConstruct
  public void init() {
    Resource temp =
        new FileSystemResource("D:\\Nishit\\project\\contentai\\src\\main\\resources\\yogi.pdf");

    documentService.processDocument("1", temp);

    ChatClient.CallResponseSpec responseSpec =
        chatClient
            .prompt()
            .user(
                """
                            I need you to determine what personally identifiable information exists in this PDF form in MEMORY section.
                            I want you to provide the information in JSON format, including keys for the type and value.
                            """)
            .advisors(
                advisorSpec -> {
                  advisorSpec.param(ChatMemory.CONVERSATION_ID, "1");
                })
            .call();

    System.out.println(responseSpec.content());

    responseSpec =
        chatClient
            .prompt()
            .user(
                """
                                        are you seeing any other PI information if yes convert to the json as last time?
                                        """)
            .advisors(
                advisorSpec -> {
                  advisorSpec.param(ChatMemory.CONVERSATION_ID, "1");
                })
            .call();

    System.out.println(responseSpec.content());

    responseSpec =
        chatClient
            .prompt()
            .user(
                """
                                        What all skills are mentioned in the PDF?
                                        """)
            .advisors(
                advisorSpec -> {
                  advisorSpec.param(ChatMemory.CONVERSATION_ID, "1");
                })
            .call();

    System.out.println(responseSpec.content());

    //    Resource temp1 =
    //        new FileSystemResource(
    //            "D:\\Nishit\\project\\contentai\\src\\main\\resources\\Nishit Charania - Self
    // Assessment Form - 2025.pdf");
    //
    //    documentService.processDocument("2", temp1);

    //    ChatClient.CallResponseSpec responseSpec1 =
    //        chatClient
    //            .prompt()
    //            .user(
    //                "In which in file mention about Nishit. Give me the document name and
    // contentAI-id")
    //            .advisors(
    //                advisorSpec -> {
    //                  advisorSpec.param(ChatMemory.CONVERSATION_ID, "2");
    //                })
    //            .call();
    //    System.out.println(responseSpec1.content());

  }
}
