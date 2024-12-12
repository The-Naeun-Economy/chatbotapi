package repick.chatbotapi.Dto;

import repick.chatbotapi.domain.ChatBotMessage;

public record ChatBotMessageDto(
        Long id,
        String reqeust,
        String Response
) {
    public static ChatBotMessageDto from(ChatBotMessage chatBotMessage){
        return new ChatBotMessageDto(
                chatBotMessage.getId(),
                chatBotMessage.getRequest(),
                chatBotMessage.getResponse()
        );
    }
}
