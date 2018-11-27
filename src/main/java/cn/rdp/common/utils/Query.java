package cn.rdp.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	// 
	private int offset;
	// 每页条数
	private int limit;
	
	//easy ui查詢
	

	public Query(Map<String, Object> params) {
		
		this.putAll(params);
		// 分页参数
		Object offsetStr = params.get("offset");
		if(offsetStr != null) {
			this.offset = Integer.parseInt(offsetStr.toString());
		}else {
			//處理 easy ui查詢參數，頁碼
			Object pageStr = params.get("page");
			if(pageStr != null) {
				//需要计算
				this.offset = (Integer.parseInt(pageStr.toString()) - 1) * Integer.parseInt(params.get("rows").toString());;
				
			}else {
				this.offset = 0;
			}
		}
		Object limitStr = params.get("limit");
		if(limitStr != null) {
			this.limit = Integer.parseInt(limitStr.toString());
		}else {
			//處理 easy ui查詢參數，页面大小
			Object rowsStr = params.get("rows");
			if(rowsStr != null) {
				this.limit = Integer.parseInt(rowsStr.toString());
			}else {
				this.limit = 10;
			}
		}
		this.put("offset", offset);
		this.put("page", offset / limit + 1);//页码
		this.put("limit", limit);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.put("offset", offset);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
