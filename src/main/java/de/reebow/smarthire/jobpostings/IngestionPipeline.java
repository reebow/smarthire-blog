package de.reebow.smarthire.jobpostings;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
class IngestionPipeline {

    private final VectorStore vectorStore;

    @Value("classpath:jobs/backend.txt")
    Resource backendJobDescription;
    @Value("classpath:jobs/fullstack.txt")
    Resource fullstackJobDescription;
    @Value("classpath:jobs/marketing.txt")
    Resource marketingJobDescription;

    IngestionPipeline(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void run() {
        var backendDocument = getDocument(backendJobDescription, "1");
        var frontendDocument = getDocument(fullstackJobDescription, "2");
        var marketingDocument = getDocument(marketingJobDescription, "3");
        List<Document> documents = new ArrayList<>(backendDocument);
        documents.addAll(frontendDocument);
        documents.addAll(marketingDocument);
        vectorStore.add(new TokenTextSplitter().apply(documents));
    }

    private List<Document> getDocument(Resource textFile, String id) {
        var textReader = new TextReader(textFile);
        textReader.getCustomMetadata().put("jobId", id);
        textReader.setCharset(Charset.defaultCharset());
        return textReader.get();
    }
}
