package com.howard.www.core.data.transfer.vo.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository("frameworkResult")
@Scope("prototype")
public class FrameworkResult {
	public static FrameworkResult ok() {
		FrameworkResult result = new FrameworkResult();
		result.setSuccess(true);
		return result;
	}

	public static FrameworkResult ok(String msg) {
		FrameworkResult result = new FrameworkResult();
		result.setSuccess(true);
		result.setMsg(msg);
		return result;
	}

	public static FrameworkResult ok(Object data) {
		FrameworkResult result = new FrameworkResult();
		result.setSuccess(true);
		result.setData(data);
		return result;
	}

	public static FrameworkResult fail() {
		FrameworkResult result = new FrameworkResult();
		result.setSuccess(false);
		return result;
	}

	public static FrameworkResult fail(String msg) {
		FrameworkResult result = new FrameworkResult();
		result.setSuccess(false);
		result.setMsg(msg);
		return result;
	}

	// 常用操作码
	public static final int OPERATE_CODE_LOGIN = 100; // 未

	private boolean success;
	private String msg;
	private Object data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FrameworkResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public FrameworkResult() {
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private int operateCode; // 操作码

	public int getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(int operateCode) {
		this.operateCode = operateCode;
	}
}
