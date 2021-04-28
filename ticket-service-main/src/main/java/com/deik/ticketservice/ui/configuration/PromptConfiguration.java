package com.deik.ticketservice.ui.configuration;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class PromptConfiguration implements PromptProvider {

    private static final String PROMPT_STRING = "Ticket service>";

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(PROMPT_STRING, AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

}
