package andrejewa.websocketchat.services;

import andrejewa.websocketchat.entities.ChatSender;
import andrejewa.websocketchat.repositories.ChatSenderRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatSenderService {

    private final ChatSenderRepo chatSenderRepo;

    public Long getSenderId(String name)
    {
        var sender = getOrCreateSender(name);
        return sender == null ? null : sender.getId();
    }

    private ChatSender getOrCreateSender(String name)
    {
        if (name == null || name.isBlank()) return null;
        var nameNorm = StringUtils.normalizeSpace(name);
        var optional = chatSenderRepo.findByNameIgnoreCase(nameNorm);
        return optional.orElseGet(() -> chatSenderRepo.saveAndFlush(ChatSender.builder().name(nameNorm).build()));
    }
}

