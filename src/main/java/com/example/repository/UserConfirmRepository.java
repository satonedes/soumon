package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.RegistUrl;
import com.example.domain.Users;




@Repository
public class UserConfirmRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	 private static final RowMapper<RegistUrl> REGISTURL_ID_ROW_MAPPER=(rs,i)->{
	        RegistUrl regist = new RegistUrl();
	        regist.setId(rs.getInt("id"));
	        return regist;
	    };
	 private static final RowMapper<Users> USERS_ID_ROW_MAPPER=(rs,i)->{
	        Users users = new Users();
	        users.setId(rs.getInt("id"));
	        return users;
	    };
	    
	    @Transactional
	    public void insert(RegistUrl regist) {
	    	String sql = "insert into regist_url(unique_url, mail_address) values(:uniqueUrl, :mailAddress);";
	    	SqlParameterSource param = new MapSqlParameterSource().addValue("uniqueUrl", regist.getUniqueUrl()).addValue("mailAddress", regist.getMailAddress()).addValue("registDate", regist.getRegistDate());
	    	template.update(sql, param);
	    }
	    
	    
/*	    public User findByEmail(String email) {
			String sql = "SELECT id,name,email,password,zipcode,address,telephone,password from users WHERE email=:email;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
			List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
			
			if(userList.size() == 0) {
				return null;
			}
			return userList.get(0);
		}*/
	    
	    
	    @Autowired
	    PasswordEncoder passwordEncoder;
	    @Transactional
	    public void insertUsers(Users users) {
	    	users.setPassword(passwordEncoder.encode(users.getPassword()));
	    	String sql = "insert into users(name, ruby, mail_address, zip_code, address, telephone, password) values(:name, :ruby, :mailAddress, :zipCode, :address, :telephone, :password)";
	    	SqlParameterSource param = new MapSqlParameterSource().addValue("name", users.getName()).addValue("ruby", users.getRuby()).addValue("mailAddress", users.getMailAddress()).addValue("zipCode", users.getZipCode()).addValue("address", users.getAddress()).addValue("telephone", users.getTelephone()).addValue("password", users.getPassword()).addValue("registDate", users.getRegistDate()).addValue("delFlg", users.getDelFlg());
	    	template.update(sql, param);
	    }

	    public List<Users> findByUser(String mailAddress) {
			String sql = "SELECT id, name, ruby, mail_address, zip_code, address, telephone, password, regist_date, regist_user, update_date, update_user, del_flg from users WHERE mail_address=:mailAddress;";
			SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
			List<Users> usersList = template.query(sql, param, USERS_ID_ROW_MAPPER);
			
			if(usersList.size() == 0) {
				return null;
			}
			return usersList;
		}
	    public List<RegistUrl> findByEmail(String mailAddress){
	    	String sql = "select id, unique_url, mail_address, regist_date, del_flg from regist_url where mail_address=:mailAddress and regist_date > (select now()+cast('-1 days' as INTERVAL))";
	    	SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
	    	List<RegistUrl> urlList = template.query(sql, param, REGISTURL_ID_ROW_MAPPER);
	    	if(urlList.size() == 0) {
	    		return null;
	    	}
	    	return urlList;
	    }
//	    public void updateUser(String mailAddress) {
//	    	String updateSql = "update users set del_flg=1 where mail_address=:mailAddress;";
//	    	SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
//	    	template.update(updateSql, param);
//	    }
	    public void updateUrl(String mailAddress) {
	    	String updateSql = "update regist_url set del_flg=1 where mail_address=:mailAddress;";
	    	SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
	    	template.update(updateSql, param);
	    }
	    
}
