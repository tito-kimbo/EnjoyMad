package es.ucm.fdi.integration.data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class that represents a client session.
 * 
 * @version 08.05.2018
 */
public class SessionPOJO extends DataPOJO implements Serializable {
    
    private LocalDateTime creationTime;
    private LocalDateTime lastAccessedTime;

    public SessionPOJO(String id, LocalDateTime creationTime) {
        super(id);
        this.creationTime = creationTime;
        lastAccessedTime = null;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(LocalDateTime newTime) {
        lastAccessedTime = newTime;
    }
}
