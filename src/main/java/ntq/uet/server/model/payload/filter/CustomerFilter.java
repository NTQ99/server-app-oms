package ntq.uet.server.model.payload.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ntq.uet.server.common.base.BaseObject;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerFilter extends BaseObject {
    private String id;
    private String customerCode;
    private String customerPhone;
}
