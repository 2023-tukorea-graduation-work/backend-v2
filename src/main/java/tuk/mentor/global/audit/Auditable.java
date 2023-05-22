package tuk.mentor.global.audit;

public interface Auditable {
    BaseEntity getBaseEntity();
    void setBaseEntity(BaseEntity baseEntity);
}
