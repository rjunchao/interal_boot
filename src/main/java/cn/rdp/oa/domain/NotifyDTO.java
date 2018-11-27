package cn.rdp.oa.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class NotifyDTO extends NotifyDO {

    private static final long serialVersionUID = 1L;

    private String isRead;

    private String before;

    private String sender;
   
}
