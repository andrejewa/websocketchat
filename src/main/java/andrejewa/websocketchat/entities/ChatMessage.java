package andrejewa.websocketchat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Table(name = "message")
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage implements Serializable {

    @Id
    @SequenceGenerator(
            name = "message_seq",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_seq"
    )
    @Column(name = "message_id")
    private Long id;

    private String content;

    @Column(name = "sender_id")
    private Long senderId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id",
            insertable = false,
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(
                    value = CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY (sender_id) REFERENCES sender(sender_id) ON DELETE CASCADE"))
    private ChatSender sender;

    @CreatedDate
    @Column(name = "created_on", updatable = false)
    private Date createdOn;
}

