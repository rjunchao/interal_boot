package cn.rdp.activiti.domain;

import java.util.Map;

import lombok.Data;

/**
 * @author bootdo 1992lcg@163.com
 */
@Data
public class TaskDO {
    private  String taskId;
    private String taskComment;
    private String taskPass;
    private Map<String,Object> vars;

}
