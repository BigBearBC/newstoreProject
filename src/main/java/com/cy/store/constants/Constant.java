package com.cy.store.constants;

public interface Constant {

	/**
	 * 正常状态
	 */
	Integer STATUS_NORMAL = 0;

	/**
	 * 禁用状态
	 */
	Integer STATUS_DISABLE = -1;

	/**
	 * 删除标志
	 */
	Integer DEL_FLAG_1 = 1;

	/**
	 * 未删除
	 */
	Integer DEL_FLAG_0 = 0;

	/**
	 * 系统日志类型： 登录
	 */
	int LOG_TYPE_1 = 1;
	
	/**
	 * 系统日志类型： 操作
	 */
	int LOG_TYPE_2 = 2;

	/**
	 * 操作日志类型： 查询
	 */
	int OPERATE_TYPE_1 = 1;
	
	/**
	 * 操作日志类型： 添加
	 */
	int OPERATE_TYPE_2 = 2;
	
	/**
	 * 操作日志类型： 更新
	 */
	int OPERATE_TYPE_3 = 3;
	
	/**
	 * 操作日志类型： 删除
	 */
	int OPERATE_TYPE_4 = 4;
	
	/**
	 * 操作日志类型： 倒入
	 */
	int OPERATE_TYPE_5 = 5;
	
	/**
	 * 操作日志类型： 导出
	 */
	int OPERATE_TYPE_6 = 6;
	
	
	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    Integer SC_OK_200 = 200;
    
    /**访问权限认证未通过 510*/
    Integer SC_JEECG_NO_AUTHZ=510;

    String RIGHT = "√";

    String EMPTY = "";

	String NO_PREM = "无相应数据权限!";

    String NO_DATA = "未查询到相关数据!";

	String NO_PARAM = "输入参数错误!";

	String STATE="可用";

	String PAUSE = "暂停";

	String GREEN = "green";

	String REG_IDCARD = "/^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$/";

	String REG_PHONE = "/^1[3456789]\\d{9}$/";

	String D_MSG_CODE = "123456789";

	String D_MSG_CODE_CHAR = "abcdefghijklmnopqrstuvwxyz";

	String DELETE_NOT_EXIST = "该实体项已在数据库删除，请刷新页面重试!";



    String INTERNAL_SERVER_ERROR="服务器内部错误";
    String INDEX_OUT_ERROR="下标越界";
    String NO_FILE_ERROE="文件未找到";
    String NOT_SUMBER_ERROR="字符串转换为数字异常";
    String SQL_ERROR="数据库异常";
    String IO_ERROR="IO流异常";
    String TYPE_COERCION_ERROR="类型强制转换错误";
    String ARITHMETIC_ERROR="算术错误";



}
