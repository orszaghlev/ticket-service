package com.deik.ticketservice;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.jline.PromptProvider;

@SpringBootApplication
@ComponentScan(basePackages = {"com.deik"})
public class TicketServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("Ticket service>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }
}