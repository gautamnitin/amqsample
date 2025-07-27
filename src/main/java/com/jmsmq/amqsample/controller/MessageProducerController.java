package com.jmsmq.amqsample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmsmq.amqsample.model.Trade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/send")
@Tag(name = "Trade API", description = "Operations related to trade processing")
public class MessageProducerController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Queue queue; // Injected from your config (can also be created manually)

    @Operation(
            summary = "Send trade message",
            description = "Accepts a trade message",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully processed"),
                    @ApiResponse(responseCode = "400", description = "Invalid input format")
            }
    )
    @PostMapping("/trade")
    public ResponseEntity<String> sendMessage(@Parameter(description = "JSON as trade message")
                                              @RequestBody Trade message) throws JsonProcessingException {
        jmsTemplate.convertAndSend(queue, objectMapper.writeValueAsString(message));
        return ResponseEntity.ok("Sent message: " + message);
    }

    @PostMapping("/trades")
    public ResponseEntity<String> sendMessages(@Parameter(description = "List of trade message")
                                              @RequestBody List<Trade> messages) {
        messages.forEach(message -> {
            try {
                jmsTemplate.convertAndSend(queue, objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return ResponseEntity.ok("Sent all the messages: " + messages);
    }
}
