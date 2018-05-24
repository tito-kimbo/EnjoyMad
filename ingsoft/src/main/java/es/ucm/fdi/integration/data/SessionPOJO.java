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
    
    public boolean equals(SessionPOJO s) {
    	boolean isCr = creationTime.equals(s.creationTime), isLast;
    	
    	if(lastAccessedTime == null)
    		if(s.lastAccessedTime == null)
    			isLast = true;
    		else
    			isLast = false;
    	else
    		if(s.lastAccessedTime == null)
    			isLast = false;
    		else 
    			isLast = lastAccessedTime.equals(s.lastAccessedTime);
    	
    	return getID().equals(s.getID())
    			&& isCr
    			&& isLast;
    }
}
