package andrejewa.websocketchat.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "sender")
@EntityListeners(AuditingEntityListener.class)
public class ChatSender implements Serializable {

    @Id
    @SequenceGenerator(
            name = "sender_seq",
            sequenceName = "sender_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sender_seq"
    )
    @Column(name = "sender_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @CreatedDate
    @Column(name = "created_on", updatable = false)
    private Date createdOn;
}

