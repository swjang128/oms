package com.oms.config;

/**
 * Rest API 결과로 보내줄 응답 메시지에 대한 정의
 * @author capias J
 *
 */
public class ResponseCode {
	/* Status에 대한 정적 클래스 */
	public static class Status {
		public static final int OK = 200;				// READ | UPDATE | DELETE
		public static final int CREATED = 201;			// CREATE		
		public static final int BAD_REQUEST = 400;
		public static final int UNAUTHORIZED = 401;
		public static final int FORBIDDEN = 403;
		public static final int NOT_FOUND = 404;
		public static final int INTERNAL_SERVER_ERROR = 500;
		public static final int SERVICE_UNAVAILABLE = 503;
		public static final int DB_ERROR = 600;
		
		//  Custom Status
		/* Product (2xxx) */
		public static final int PRODUCT_NOT_FOUND = 2404; 
		/* Account (1xxx) */
		public static final int ACCOUNT_EXPIRED = 1003;
		public static final int ACCOUNT_NOT_FOUND = 1004;
		public static final int ACCOUNT_OLD_PASSWORD_NOT_MATCH = 1005;
		public static final int ACCOUNT_DUPLICATE = 1009;
		public static final int ACCOUNT_BLOCKED = 1023;
	}
	
	/* Message에 대한 정적 클래스 */
	public static class Message {
		// HttpStatusCode (Success)		
		public static final String OK = "ok";				// READ | UPDATE | DELETE
		public static final String CREATED = "created";			// CREATE		
		// HttpStatusCode (Failed)
		public static final String BAD_REQUEST = "Bad Request";
		public static final String UNAUTHORIZED = "Unauthorized";
		public static final String FORBIDDEN = "Forbidden";
		public static final String NOT_FOUND = "Not Found";
		public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
		public static final String SERVICE_UNAVAILABLE = "Service Unavailable";
		public static final String DB_ERROR = "DB Error";

		// Custom Exception Message (500 에러에 주로 엮여서 사용)
		/* Product */
		public static final String PRODUCT_NOT_FOUND = "존재하지 않는 상품입니다";
		/* Account */
		public static final String ACCOUNT_EXPIRED = "계정이 만료되었습니다. 비밀번호 변경 또는 초기화를 해주세요";
		public static final String ACCOUNT_NOT_FOUND = "존재하지 않는 계정입니다";
		public static final String ACCOUNT_OLD_PASSWORD_NOT_MATCH = "기존 비밀번호가 일치하지 않습니다";
		public static final String ACCOUNT_DUPLICATE = "이미 존재하는 계정입니다";
		public static final String ACCOUNT_BLOCKED = "계정이 잠겨있습니다. 비밀번호 초기화 후 이용해주세요";
		
	}
	
}

