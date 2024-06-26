package com.fernandakipper.aiassistantjava.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernandakipper.aiassistantjava.dto.MessageDTO;
import com.fernandakipper.aiassistantjava.factory.AiAssistantFactory;
import com.fernandakipper.aiassistantjava.factory.ContentRetrieverFactory;
import com.fernandakipper.aiassistantjava.factory.DocumentAssistantFactory;
import com.fernandakipper.aiassistantjava.factory.EmbeddingFactory;

import dev.langchain4j.model.chat.ChatLanguageModel;

@RestController
@RequestMapping("/api/chat")
public class AiAssistantController {
    @Value("${langchain.huggingFace.accessToken}")
    private String token;

    @PostMapping()
    public ResponseEntity chat(@RequestBody MessageDTO messageDTO) {
        /*
        * String response = chatLanguageModel.generate(messageDTO.message());
        * return ResponseEntity.ok().body(response);
        */
        ChatLanguageModel chatModel = AiAssistantFactory.createLocalChatModel();

        var embeddingModel = EmbeddingFactory.createEmbeddingModel();
        var embeddingStore = EmbeddingFactory.createEmbeddingStore();
        var fileContentRetriever = ContentRetrieverFactory.createFileContentRetriever(
                embeddingModel,
                embeddingStore,
                "movies.txt");

        var documentAssistant = new DocumentAssistantFactory(chatModel, fileContentRetriever);
        
        String response = documentAssistant.chat(messageDTO.message());
        return ResponseEntity.ok().body(response);
    }

}
