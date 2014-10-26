package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.CalendarUser;

@Repository
public class JdbcCalendarUserDao implements CalendarUserDao {

	private JdbcTemplate jdbcTemplate;

	// --- constructors ---
	public JdbcCalendarUserDao() {

	}

	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<CalendarUser> userMapper = new RowMapper<CalendarUser>() {
		public CalendarUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			CalendarUser user = new CalendarUser();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			return user;
		}
		//지난번 과제에서 했던부분을 썼습니다. create빼고는 공통적으로 이 함수를 사용합니다.(CalendarUser 객체를 반환받기위해서)
	};

	// --- CalendarUserDao methods ---
	@Override
	public CalendarUser getUser(int id){
		return this.jdbcTemplate.queryForObject("select * from calendar_users where id = ?", new Object[] {id}, this.userMapper);
	}	//쿼리문 결과를 RowMapper함수를 거쳐서, CalendarUser객체 형태로 반환됩니다.

	@Override
	public CalendarUser findUserByEmail(String email) {
		return this.jdbcTemplate.queryForObject("select * from calendar_users where email = ?", new Object[] {email}, this.userMapper);
	}	

	@Override
	public List<CalendarUser> findUsersByEmail(String email) {
		String like= "%" + email + "%";
		return this.jdbcTemplate.query("select * from calendar_users where email like ?", new Object[] {like}, this.userMapper);
	}	//partial email 을 갖고 있는 사람들 List를 받습니다.

	@Override
	public int createUser(final CalendarUser userToAdd){
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("insert into calendar_users(name, password, email) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, userToAdd.getName()); 
	    		ps.setString(2, userToAdd.getPassword()); 
	    		ps.setString(3, userToAdd.getEmail()); 
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}	//등록한 ID값을 keyHolder가 저장하고있다가 반환해줌. ch.11.2.2의 addMemberAndGetKey1를 참조함.
	
	@Override
	public void deleteAll() {
		// Assignment 2
		this.jdbcTemplate.update("delete from calendar_users");
	}
}