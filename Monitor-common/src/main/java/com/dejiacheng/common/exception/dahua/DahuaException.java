package com.dejiacheng.common.exception.dahua;


import com.dejiacheng.common.utils.StringUtils;

/**
 * @author pcy
 * 大华平台错误码异常处理
 * @version 创建时间:2019年9月24日-下午8:31:39
 */
public class DahuaException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 错误码*/
	private String code;
	
	/**
	 * 错误码对应参数*/
	private String args;

	/**
     * 错误消息
     */
    private String defaultMessage = "其他错误！！！";
    
    public DahuaException(String code)
    {
    	this(code, null, null);
    }
    
    public DahuaException(String code, String args, String defaultMessage) {
    	this.code = code;
    	this.args = args;
    	this.defaultMessage = defaultMessage;
    }
    
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
				String message = null;
		        if (!StringUtils.isEmpty(code))
		        {
		        	message = "平台对接错误："+this.args;
		        }
		        if (message == null)
		        {
		            message = this.defaultMessage;
		        }
		        return message;
	}
}

