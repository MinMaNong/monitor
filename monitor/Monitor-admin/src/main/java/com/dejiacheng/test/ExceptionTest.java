package com.dejiacheng.test;

import com.dejiacheng.common.utils.StringUtils;

class AddException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String msg;
	private String defaultMessage = "你的值太大了";;
	public AddException(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public AddException(String code) {
		this(code, null);
		System.out.println("你进到这里来了");
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		String message = null;
        if (!StringUtils.isEmpty(code))
        {
        	message = this.defaultMessage;
        }
        if (message == null)
        {
            message = defaultMessage;
        }
        return message;
	}
	
}
 
public class ExceptionTest {
	public static void main(String[] args) {
		int num = 10;
		try {
			if (num > 8) {
				throw new AddException("2",null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
