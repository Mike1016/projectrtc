package com.daka.util;

/**
 * 服务端返回前端的数据封装对象
 * @author kevin
 *
 */
public class ReturnJsonBean {
	public static final String RETURN_SUCCESS = "success".intern();
	
	public static final String RETURN_ERROR = "error".intern();
	
	private String returnCode = RETURN_SUCCESS;
	
	private String returnMessage;
	
	private Object returnData;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}
	
	public boolean isSuccess() {
		return RETURN_SUCCESS.equals(returnCode);
	}
}
