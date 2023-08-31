package ntq.uet.server.common.base;

import ntq.uet.server.common.core.util.JSON;

import java.io.Serial;
import java.io.Serializable;

public class BaseObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    public BaseObject() {
    }

    public String toString() {
        return JSON.toJSONString(this);
    }

}
