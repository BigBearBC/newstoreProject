package com.cy.store.base;

import com.cy.store.constants.Constant;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *   接口返回数据格式
 * @author scott
 * @email jeecgos@163.com
 * @date  2019年1月19日
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	private Integer code = Constant.SC_OK_200;
	
	/**
	 * 返回数据对象 data
	 */
	private T result;
	
	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	public Result() {
		
	}
	
	public Result<T> success(String message) {
		this.message = message;
		this.code = Constant.SC_OK_200;
		this.success = true;
		return this;
	}
	
	
	public static Result<Object> ok() {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(Constant.SC_OK_200);
		r.setMessage("成功");
		return r;
	}
	
	public static Result<Object> ok(String msg) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(Constant.SC_OK_200);
		r.setMessage(msg);
		return r;
	}

	public static Result<Object> list(List data) {
		Result<Object> r = new Result<Object>();
		if (data!=null && data.size()>0){
			r.setSuccess(true);
			r.setCode(Constant.SC_OK_200);
			r.setResult(data);
		}else{
			r.setSuccess(false);
			r.setCode(Constant.SC_INTERNAL_SERVER_ERROR_500);
			r.setMessage(Constant.NO_DATA);
		}
		return r;
	}

	public static Result<Object> ok(Object data) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setCode(Constant.SC_OK_200);
		r.setResult(data);
		return r;
	}

	public static Result<Object> ok(Object data,String mag) {
		Result<Object> r = new Result<Object>();
		r.setSuccess(true);
		r.setMessage(mag);
		r.setCode(Constant.SC_OK_200);
		r.setResult(data);
		return r;
	}
	
	public static Result<Object> error(String msg) {
		return error(Constant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}
	
	public static Result<Object> error(int code, String msg) {
		Result<Object> r = new Result<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public Result<T> error500(String message) {
		this.message = message;
		this.code = Constant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}
	/**
	 * 无权限访问返回结果
	 */
	public static Result<Object> noauth(String msg) {
		return error(Constant.SC_JEECG_NO_AUTHZ, msg);
	}
}