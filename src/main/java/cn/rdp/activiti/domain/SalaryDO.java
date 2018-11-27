package cn.rdp.activiti.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * 审批流程测试表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-11-25 13:28:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SalaryDO extends  TaskDO implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	//编号
	private String id;
	//流程实例ID
	private String procInsId;
	//变动用户
	private String userId;
	//归属部门
	private String officeId;
	//岗位
	private String post;
	//性别
	private String age;
	//学历
	private String edu;
	//调整原因
	private String content;
	//现行标准 薪酬档级
	private String olda;
	//现行标准 月工资额
	private String oldb;
	//现行标准 年薪总额
	private String oldc;
	//调整后标准 薪酬档级
	private String newa;
	//调整后标准 月工资额
	private String newb;
	//调整后标准 年薪总额
	private String newc;
	//月增资
	private String addNum;
	//执行时间
	private String exeDate;
	//人力资源部门意见
	private String hrText;
	//分管领导意见
	private String leadText;
	//集团主要领导意见
	private String mainLeadText;
	//创建者
	private String createBy;
	//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;
	//更新者
	private String updateBy;
	//更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;
	//备注信息
	private String remarks;
	//删除标记
	private String delFlag;
}
