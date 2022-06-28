package com.example.repository;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.RegistUrl;

@Repository
public class RegistUrlRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	

	 private static final RowMapper<RegistUrl> REGISTURL_URL_ROW_MAPPER=(rs,i)->{
	        RegistUrl regist = new RegistUrl();
	        regist.setId(rs.getInt("id"));
	        regist.setUniqueUrl(rs.getString("unique_url"));
	        regist.setMailAddress(rs.getString("mail_address"));
	        regist.setRegistDate(rs.getTimestamp("regist_date").toLocalDateTime());
	        regist.setDelFlg(rs.getInt("del_flg"));
	        return regist;
	    };
	
	public RegistUrl findByKey(String key) {
		
		String sql = """
				select
					id
					, unique_url
					, mail_address
					, regist_date
					, del_flg
				from regist_url
				WHERE
					unique_url = :key
					and
					del_flg = 0;
				""";
		
		SqlParameterSource params = new MapSqlParameterSource().addValue("key", key);
		
		try {
			return template.queryForObject(sql,params,REGISTURL_URL_ROW_MAPPER);
		} catch (DataAccessException e) {
			return null;
		}
		
		
	}

}
