package com.brain.ai.contentai.ai.advisor;

import java.util.Map;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.Ordered;

/**
 * https://www.baeldung.com/spring-ai-advisors
 * https://docs.spring.io/spring-ai/reference/api/advisors.html
 *
 * https://github.com/spring-projects/spring-ai/blob/694bb50be5825bb2d91f3ce0291020d9aadcb780/models/spring-ai-openai/src/test/java/org/springframework/ai/openai/chat/client/ReReadingAdvisor.java#L38
 */
public class ReReadingAdvisor implements BaseAdvisor {

  private static final String DEFAULT_RE2_ADVISE_TEMPLATE =
      """
			{re2_input_query}
			Read the question again: {re2_input_query}
			""";

  private final String re2AdviseTemplate;

  private int order = Ordered.HIGHEST_PRECEDENCE;

  public ReReadingAdvisor() {
    this(DEFAULT_RE2_ADVISE_TEMPLATE);
  }

  public ReReadingAdvisor(String re2AdviseTemplate) {
    this.re2AdviseTemplate = re2AdviseTemplate;
  }

  @Override
  public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
    String augmentedUserText =
        PromptTemplate.builder()
            .template(this.re2AdviseTemplate)
            .variables(
                Map.of("re2_input_query", chatClientRequest.prompt().getUserMessage().getText()))
            .build()
            .render();

    return chatClientRequest
        .mutate()
        .prompt(chatClientRequest.prompt().augmentUserMessage(augmentedUserText))
        .build();
  }

  @Override
  public ChatClientResponse after(
      ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
    return chatClientResponse;
  }

  @Override
  public int getOrder() {
    return this.order;
  }

  public ReReadingAdvisor withOrder(int order) {
    this.order = order;
    return this;
  }
}
