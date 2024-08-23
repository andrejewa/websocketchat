package andrejewa.websocketchat.repositories;

import andrejewa.websocketchat.entities.ChatSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatSenderRepo extends JpaRepository<ChatSender, Long> {

}
