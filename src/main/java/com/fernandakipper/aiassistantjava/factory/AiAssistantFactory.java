package com.fernandakipper.aiassistantjava.factory;

import java.time.Duration;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;

public class AiAssistantFactory {
    public static ChatLanguageModel createHuggingFace(String accessToken) {
        return HuggingFaceChatModel.builder()
                .accessToken(accessToken)
                .modelId("EleutherAI/gpt-neo-125m")
                .timeout(Duration.ofSeconds(300))
                .build();
    }
}
