package io.zanozin.rmi_ai.controller;

import io.zanozin.rmi_ai.service.BaseVolumeService;
import io.zanozin.rmi_ai.service.ContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private static final String SYSTEM_PROMPT = """
            You are an AI assistant for a user that helps to interact with container data.
            You have access to tools that allows you to search and modify container data based on user's requests.
            When the user provides a request, analyze it and determine if you need to use the tool to fulfill the request.
            If you need to use the tool, call it with appropriate parameters extracted from the user's request.
            After calling the tool, use the result to formulate a complete and helpful response to the user.
            Always ensure that your responses are clear, concise, and directly address the user's needs.
            Don't describe the tool or its usage in your responses.
            Don't include code, schemas, or technical details in your replies, even in case of errors.
            Directly call the tool when needed and use the results in your replies.
            Don't do anything that is outside of the scope of container data management.
            """;

    private final ChatClient chatClient;

    private final ContainerService containerService;

    private final BaseVolumeService baseVolumeService;

    @GetMapping
    public String doChat(@RequestBody String prompt) {
        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(prompt)
                .tools(containerService, baseVolumeService)
                .call()
                .content();
    }
}
