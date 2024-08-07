package org.example.userservice.Dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SendEmailEventDto {
    private String to;
    private String from;
    private String subject;
    private String body;
}
