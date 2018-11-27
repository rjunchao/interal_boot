package cn.rdp.system.domain;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class RoleDO {
	
	private Long roleId;
	private String roleName;
	private String roleSign;
	private String remark;
	private Long userIdCreate;
	private Timestamp gmtCreate;
	private Timestamp gmtModified;
	private List<Long> menuIds;

}
