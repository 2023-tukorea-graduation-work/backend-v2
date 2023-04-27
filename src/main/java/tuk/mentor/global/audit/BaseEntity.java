package tuk.mentor.global.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Embeddable
public class BaseEntity {
    @Setter private LocalDateTime createdAt;
    @Setter private LocalDateTime updatedAt;
}