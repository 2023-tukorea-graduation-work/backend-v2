package tuk.mentor.global.audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {
    @PrePersist
    public void setCreatedAt(Auditable auditable) {
        BaseEntity baseTime = auditable.getBaseEntity();

        if (baseTime == null) {
            baseTime = new BaseEntity();
        }
        baseTime.setCreatedAt(LocalDateTime.now());
        auditable.setBaseEntity(baseTime);
    }

    @PreUpdate
    public void setUpdatedAt(Auditable auditable) {
        BaseEntity baseTime = auditable.getBaseEntity();
        baseTime.setUpdatedAt(LocalDateTime.now());
    }
}
