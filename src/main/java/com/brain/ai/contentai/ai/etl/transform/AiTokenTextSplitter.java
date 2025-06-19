package com.brain.ai.contentai.ai.etl.transform;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

@Component
public class AiTokenTextSplitter {

    public List<Document> splitDocuments(List<Document> documents) {
        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(documents);
    }

    public List<Document> splitCustomized(List<Document> documents) {
//        TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
        TokenTextSplitter splitter = new TokenTextSplitter(1200, 400, 4, 12000, true);
        return splitter.apply(documents);
    }
}