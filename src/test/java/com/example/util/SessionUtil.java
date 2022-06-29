package com.example.util;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.mock.web.MockHttpSession;

import com.example.domain.RegistUrl;

public class SessionUtil {
	//sessionを使用するときはブラウザを遷移する場合にパラメーターの情報を継続させたい時

	public static MockHttpSession createRegistUrl() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		LocalDateTime now = LocalDateTime.now();
		RegistUrl regist = new RegistUrl();
		regist.setId(1);
		regist.setMailAddress("aaa@aaa");
		regist.setUniqueUrl("zaqwsx");
		regist.setRegistDate(now);
		regist.setDelFlg(0);
//		
//		sessionMap.put("key", regist.getUniqueUrl());
//		sessionMap.put("regist", regist);
		return createMockHttpSession(sessionMap);
//		モックセッションに格納するメソッド呼び出し　ユーザ情報テスト用のものをmapにしてmapをテスト用セッションに格納したあとセッションを返している
	}

	private static MockHttpSession createMockHttpSession(Map<String, Object> sessions) {
		MockHttpSession mockHttpSession = new MockHttpSession();
		for (Map.Entry<String, Object> session : sessions.entrySet()) {
			mockHttpSession.setAttribute(session.getKey(), session.getValue());
//			mapのキー(ユーザーID)とバリュー(ユーザーそのもの）をセッションに格納
		}
		return mockHttpSession;
	}


	public static MockHttpSession createUrlSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("key", "zaqwsx");
		return createMockHttpSession(sessionMap);

	}
	
	//パスワードリセットで使用
	public static MockHttpSession createUniqueUrlSession() {
		Map<String, Object> sessionMap = new LinkedHashMap<String, Object>();
		sessionMap.put("mailAddress", "aaa@aaa");
		sessionMap.put("key", "zaqwsx");
		return createMockHttpSession(sessionMap);
		
		
	}

	
	
	
	


}
