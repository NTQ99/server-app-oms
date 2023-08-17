package ntq.uet.server.common.base;

import ntq.uet.server.common.core.util.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serial;
import java.io.Serializable;

public class BaseObject implements Serializable {
    protected static Logger log = LogManager.getLogger(BaseObject.class);
    @Serial
    private static final long serialVersionUID = 1L;
    public BaseObject() {
    }

    public String toString() {
        return JSON.toJSONString(this);
    }

}
