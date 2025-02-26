package de.reebow.smarthire.matchings;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {

    private final ChatClient chatClient;

    MatchingService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public List<JobOfferSuggestion> machJobOffers(String userCV) {
        return chatClient.prompt()
                .user(userCV)
                .call()
                .entity(new ParameterizedTypeReference<List<JobOfferSuggestion>>() {
                });
    }
}
