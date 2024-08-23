package andrejewa.websocketchat.repositories;

import andrejewa.websocketchat.entities.ChatMessage;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByOrderByCreatedOnDesc(Limit limit);
}
