package com.brain.ai.contentai.ai.etl.transform;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.model.transformer.KeywordMetadataEnricher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AiKeywordEnricher {

    @Qualifier("openAiChatModel")
    private final ChatModel chatModel;

    public AiKeywordEnricher(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public List<Document> enrichDocuments(List<Document> documents) {
        KeywordMetadataEnricher enricher = new KeywordMetadataEnricher(this.chatModel, 5);
        return enricher.apply(documents);
    }
}