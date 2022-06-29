package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.example.util.SessionUtil;
import com.example.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
//DB使ったテストのアノテーション
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどえお使えるように指定
})
@AutoConfigureMockMvc
class UserConfirmControllerTest {
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	@DisplayName("メール登録画面の表示")
	public void indexTest() throws Exception {
		 mockMvc.perform(get("/regist")).andExpect(view().name("regist_url")).andReturn();
	}
	
	@Test
	@DisplayName("入力値チェックに引っかかる時メール登録画面に戻る")
	public void submitTest1() throws Exception {
		 mockMvc.perform(post("/regist/submit")
                 .param("mailAddress", "")
         ).andExpect(view().name("regist_url")).andReturn();
	}
	@Test
	@DisplayName("24時間以内に発行済みのURLがある時メール登録画面に遷移する")
	@DatabaseSetup(value="classpath:submit1.xlsx")
	@Transactional
	public void submitTest2() throws Exception {
		mockMvc.perform(post("/regist/submit")
				.param("mailAddress", "aaa@aaa"))
		.andExpect(view().name("redirect:/regist")).andReturn();
				
	}
	@Test
	@DisplayName("24時間以内に発行済みのURLが無く既に会員登録されている場合送信完了画面に遷移する")
	@DatabaseSetup("classpath:submit2.xlsx")
	@Transactional
	public void submitTest3() throws Exception {
		mockMvc.perform(post("/regist/submit")
				.param("mailAddress", "qqq@qqq"))
		.andExpect(view().name("submitConfirm")).andReturn();
				
	}
	@Test
	@DisplayName("24時間以内に発行済みのURLが無く会員登録もされていない場合送信完了画面に遷移する")
	@DatabaseSetup("classpath:submit3.xlsx")
	@Transactional
	public void submitTest4() throws Exception {
		mockMvc.perform(post("/regist/submit")
				.param("mailAddress", "bbb@bbb"))
		.andExpect(view().name("submitConfirm")).andReturn();
				
	}
	@Test //insertされるURLがわからない
	@DisplayName("regist_urlに有効なURLが無かった場合、データベースにinsertされる")
	@DatabaseSetup("classpath:submit4_set.xlsx")
	@ExpectedDatabase(value = "classpath:submit4_get.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Transactional
	public void submitTest5() throws Exception {
		mockMvc.perform(post("/regist/submit")
				.param("mailAddress", "bbb@bbb"))
		.andExpect(view().name("submitConfirm")).andReturn();
				
	}
	@Test
	@DisplayName("発行されたURLkeyが一致しない場合エラー画面に遷移する")
	@Transactional
	public void userRegistTest1() throws Exception {
		MockHttpSession urlsession = SessionUtil.createUrlSession();
		mockMvc.perform(post("/regist/userRegist"))
				//.session(urlsession))
		.andExpect(view().name("jaka")).andReturn();
				
	}
	
	@Test
	@DisplayName("発行されたURLを24時間以内にアクセスし、登録がまだ済んでいない場合ユーザー登録画面に遷移する")
//	@DatabaseSetup("classpath:userRegist2.xlsx")
	@Transactional
	public void userRegistTest2() throws Exception {
		mockMvc.perform(post("/regist/userRegist")
				.param("key","zaqwsx"))
		.andExpect(view().name("userRegist")).andReturn();
				
	}
	@Test
	@DisplayName("発行されたURLに24時間経った後にアクセスした場合、エラー画面が表示される")
//	@DatabaseSetup("classpath:userRegist3.xlsx")
	@Transactional
	public void userRegistTest3() throws Exception {
		mockMvc.perform(post("/regist/userRegist")
				.param("key","zaqwsx"))
		.andExpect(view().name("jaka")).andReturn();
				
	}
	@Test 
	@DisplayName("パスワードが一致していない時ユーザー登録画面に戻る")
	@Transactional
	public void complete1() throws Exception {
	//	HttpSession urlSession = SessionUtil.createUrlSession();
		MvcResult mvcReslt = mockMvc.perform(post("/regist/complete")
				.param("name", "あああ")
				.param("ruby", "aaa")
				.param("zipCode", "111-1111")
				.param("address", "TOKYO")
				.param("telephone", "111-1111-1111")
				.param("password", "kikokiko")
				.param("password1", "toyotoyo"))
		.andExpect(view().name("userRegist")).andExpect(model().hasErrors())
		.andExpect(model().attributeHasErrors("userRegistForm")).andReturn();
		
		BindingResult bindingResult = (BindingResult)mvcReslt.getModelAndView().getModel().get(BindingResult.MODEL_KEY_PREFIX + "userRegistForm");
		String message = bindingResult.getFieldError().getDefaultMessage();
		assertEquals("パスワードが一致していません", message);
				
	}
	@Test 
	@DisplayName("入力値チェックが引っかからなかった時登録完了画面に遷移する")
	@Transactional
	public void complete2() throws Exception {
		MockHttpSession urlSession = SessionUtil.createUrlSession();
		mockMvc.perform(post("/regist/complete")
				.session(urlSession)
				.param("name", "あああ")
				.param("ruby", "aaa")
				.param("zipCode", "111-1111")
				.param("address", "TOKYO")
				.param("telephone", "111-1111-1111")
				.param("password", "kikokiko")
				.param("password1", "kikokiko")
				)
		.andExpect(view().name("registConfirm")).andReturn();
				
	}
	@Test 
	@DisplayName("入力値チェックが引っかからなかった時、データベースのuserテーブルにinsertされる")
	@DatabaseSetup("classpath:complete3_set.xlsx")
	@ExpectedDatabase(value = "classpath:complete3_get.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Transactional
	public void complete3() throws Exception {
		MockHttpSession urlSession = SessionUtil.createUrlSession();
		mockMvc.perform(post("/regist/complete")
				.session(urlSession)
				.param("name", "あああ")
				.param("ruby", "aaa")
				.param("zipCode", "111-1111")
				.param("address", "TOKYO")
				.param("telephone", "111-1111-1111")
				.param("password", "kikokiko")
				.param("password1", "kikokiko")
				)
		.andExpect(view().name("registConfirm")).andReturn();
				
	}
	@Test 
	@DisplayName("入力値チェックが引っかからなかった時、データベースのuserテーブルにupdateされる")
	@DatabaseSetup("classpath:complete4_set.xlsx")
	@ExpectedDatabase(value = "classpath:complete4_get.xlsx", assertionMode = DatabaseAssertionMode.NON_STRICT)
	@Transactional
	public void complete4() throws Exception {
		MockHttpSession urlSession = SessionUtil.createUrlSession();
		mockMvc.perform(post("/regist/complete")
				.session(urlSession)
				.param("name", "あああ")
				.param("ruby", "aaa")
				.param("zipCode", "111-1111")
				.param("address", "TOKYO")
				.param("telephone", "111-1111-1111")
				.param("password", "kikokiko")
				.param("password1", "kikokiko")
				)
		.andExpect(view().name("registConfirm")).andReturn();
				
	}


}
