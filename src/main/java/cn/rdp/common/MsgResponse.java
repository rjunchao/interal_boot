package cn.rdp.common;
/**
* author:rjc 
*  email: rjunchao@126.com
*   date: 2018年11月14日 下午4:09:40
*   desc: 
*/
public class MsgResponse {


	/** 标识符，true:成功，false:失败*/
	private boolean flag;

	// 提示信息
	private String message;
	
	//传递对象
	private Object Obj;

	public MsgResponse() {
		super();
	}

	public MsgResponse(String message) {
		super();
		this.message = message;
	}

	public MsgResponse(boolean flag) {
		super();
		this.flag = flag;
	}

	public MsgResponse(boolean flag, String message) {
		super();
		this.flag = flag;
		this.message = message;
	}

	public MsgResponse(String message, boolean flag) {
		super();
		this.flag = flag;
		this.message = message;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return Obj;
	}

	public void setObj(Object obj) {
		Obj = obj;
	}

}
