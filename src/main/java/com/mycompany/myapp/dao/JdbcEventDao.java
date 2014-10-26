package com.mycompany.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

@ContextConfiguration(locations="../applicationContext.xml")

@Repository
public class JdbcEventDao implements EventDao {
	private JdbcTemplate jdbcTemplate;
/*	
	ApplicationContext context = new GenericXmlApplicationContext("com/mycompany/myapp/applicationContext.xml");;
	CalendarUserDao cal_user = context.getBean("calendarUserDao", JdbcCalendarUserDao.class);
*/
	@Autowired
	CalendarUserDao cal_user;
	// --- constructors ---
	public JdbcEventDao() { 
	}

	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<Event> userMapper = new RowMapper<Event>() {
		public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
			Event event = new Event();
			
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.setTimeInMillis(rs.getTimestamp("when").getTime());
			
			CalendarUser owner=cal_user.getUser(rs.getInt("owner"));
			CalendarUser attendee=cal_user.getUser(rs.getInt("attendee"));
			
			event.setId(rs.getInt("id"));
			event.setWhen(cal);
			event.setSummary(rs.getString("summary"));
			event.setDescription(rs.getString("description"));		
			event.setOwner(owner);
			event.setAttendee(attendee);

			return event;
			//지난번 과제에서 했던부분을 썼습니다. create빼고는 공통적으로 이 함수를 사용합니다.(Event객체를 받기때문에)
		}
	};	
	
	// --- EventService ---
	@Override
	public Event getEvent(int eventId) {
		return this.jdbcTemplate.queryForObject("select * from events where id = ?", new Object[] {eventId}, this.userMapper);
	}

	@Override
	public int createEvent(final Event event) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("insert into events(`when`, summary, description, owner, attendee) values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
				Timestamp timestamp = new Timestamp(event.getWhen().getTimeInMillis()); 
				
				ps.setTimestamp(1, timestamp);
				ps.setString(2, event.getSummary());
				ps.setString(3, event.getDescription());
				ps.setInt(4, event.getOwner().getId());
				ps.setInt(5, event.getAttendee().getId());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
		//등록한 ID값을 keyHolder가 저장하고있다가 반환해줌.
	}

	@Override
	public List<Event> findForOwner(int ownerUserId) {
		// Assignment 2
		return this.jdbcTemplate.query("select * from events where owner = ?", new Object[] {ownerUserId}, this.userMapper);
		//지난번 과제에서는 쓰지 않아서, id=? 쿼리를 썼는데, owner 검색이므로 owner = ?을 써줍니다.
	}

	@Override
	public List<Event> getEvents(){
		return this.jdbcTemplate.query("select * from events", new Object[] {}, this.userMapper);
	}	//모든 이벤트를 받아오는 단순 쿼리문. userMapper함수를 거쳐 Event형 객체로 반환됩니다.
	
	@Override
	public void deleteAll() {
		// Assignment 2
		this.jdbcTemplate.update("delete from events");
	}	
}
