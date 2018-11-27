package cn.rdp.activiti.domain;

import java.util.Date;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import lombok.Data;

/**
 *
 */
@Data
public class ActivitiDO {
    private String taskId; 		// 任务编号
    private String taskName; 	// 任务名称
    private String taskDefKey; 	// 任务定义Key（任务环节标识）

    private String procInsId; 	// 流程实例ID
    private String procDefId; 	// 流程定义ID
    private String procDefKey; 	// 流程定义Key（流程定义标识）

    private String businessTable;	// 业务绑定Table
    private String businessId;		// 业务绑定ID

    private String title; 		// 任务标题

    private String status; 		// 任务状态（todo/claim/finish）

    private String procExecUrl; 	// 流程执行（办理）RUL
    private String comment; 	// 任务意见
    private String flag; 		// 意见状态

    private Task task; 			// 任务对象
    private ProcessDefinition procDef; 	// 流程定义对象
    private ProcessInstance procIns;	// 流程实例对象
    private HistoricTaskInstance histTask; // 历史任务
    private HistoricActivityInstance histIns;	//历史活动任务

    private String assignee; // 任务执行人编号
    private String assigneeName; // 任务执行人名称

    private Variable vars; 		// 流程变量
    private Variable taskVars; 	// 流程任务变量

    private Date beginDate;	// 开始查询日期
    private Date endDate;	// 结束查询日期

    private List<ActivitiDO> list; // 任务列表
}
