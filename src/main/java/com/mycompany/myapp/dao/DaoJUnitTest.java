package com.mycompany.myapp.dao;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.mycompany.myapp.domain.CalendarUser;
import com.mycompany.myapp.domain.Event;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../applicationContext.xml")

public class DaoJUnitTest {
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private JdbcCalendarUserDao calendarUserDao;	
	
	@Autowired
	private JdbcEventDao eventDao;
	
	private CalendarUser[] calendarUsers = null;
	private Event[] events = null;
	
	private int userID1, userID2, userID3;
	private int eventID1, eventID2, eventID3;
	//매 테스트 수행시마다 수행되는 부분
	@Before
	public void setUp() {
		calendarUsers = new CalendarUser[3];
		events = new Event[3];
		
		this.calendarUserDao.deleteAll();
		this.eventDao.deleteAll();
		
		calendarUsers[0]=new CalendarUser();
		calendarUsers[0].setName("User1");
		calendarUsers[0].setPassword("user1");
		calendarUsers[0].setEmail("user1@example.com");
		
		calendarUsers[1]=new CalendarUser();
		calendarUsers[1].setName("Admin");
		calendarUsers[1].setPassword("user1");
		calendarUsers[1].setEmail("admin1@example.com");
		
		calendarUsers[2]=new CalendarUser();
		calendarUsers[2].setName("User1");
		calendarUsers[2].setPassword("user2");
		calendarUsers[2].setEmail("user2@example.com");
		
		userID1=calendarUserDao.createUser(calendarUsers[0]);
		userID2=calendarUserDao.createUser(calendarUsers[1]);
		userID3=calendarUserDao.createUser(calendarUsers[2]);
		calendarUsers[0].setId(userID1);
		calendarUsers[1].setId(userID2);
		calendarUsers[2].setId(userID3);
		//calendarUsers에 디폴트 CalendarUser3명의 값을 집어넣고 DB에 등록 후, id값을 반환하여 저장함.
		events[0]=new Event();
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(2013, 10, 04, 20, 30, 00);
		events[0].setWhen(cal);
		events[0].setSummary("Birthday Party");
		events[0].setDescription("This is going to be a great birthday");
		events[0].setOwner(calendarUserDao.getUser(userID1));
		events[0].setAttendee(calendarUserDao.getUser(userID3));
		
		events[1]=new Event();
		cal = Calendar.getInstance();
		cal.clear();
		cal.set(2013, 12, 23, 13, 00, 00);
		events[1].setWhen(cal);
		events[1].setSummary("Conference Call");
		events[1].setDescription("Call with the client");
		events[1].setOwner(calendarUserDao.getUser(userID3));
		events[1].setAttendee(calendarUserDao.getUser(userID1));
		
		events[2]=new Event();
		cal = Calendar.getInstance();
		cal.clear();
		cal.set(2014, 01, 23, 11, 30, 00);
		events[2].setWhen(cal);
		events[2].setSummary("Lunch");
		events[2].setDescription("Eating lunch together");
		events[2].setOwner(calendarUserDao.getUser(userID2));
		events[2].setAttendee(calendarUserDao.getUser(userID3));
		
		eventID1=eventDao.createEvent(events[0]);
		eventID2=eventDao.createEvent(events[1]);
		eventID3=eventDao.createEvent(events[2]);
		events[0].setId(eventID1);
		events[1].setId(eventID2);
		events[2].setId(eventID3);
		//events에 디폴트 event3개의 값을 집어넣고 DB에 등록 후, id값을 반환하여 저장함.
		/* [참고]
		insert into calendar_users(`id`,`email`,`password`,`name`) values (1,'user1@example.com','user1','User1');
		insert into calendar_users(`id`,`email`,`password`,`name`) values (2,'admin1@example.com','admin1','Admin');
		insert into calendar_users(`id`,`email`,`password`,`name`) values (3,'user2@example.com','user2','User1');

		insert into events (`id`,`when`,`summary`,`description`,`owner`,`attendee`) values (100,'2013-10-04 20:30:00','Birthday Party','This is going to be a great birthday',1,2);
		insert into events (`id`,`when`,`summary`,`description`,`owner`,`attendee`) values (101,'2013-12-23 13:00:00','Conference Call','Call with the client',3,1);
		insert into events (`id`,`when`,`summary`,`description`,`owner`,`attendee`) values (102,'2014-01-23 11:30:00','Lunch','Eating lunch together',2,3);
		*/
		
		// 1. SQL 코드에 존재하는 3개의 CalendarUser와 Event 각각을 Fixture로서 인스턴스 변수 calendarUsers와 events에 등록하고 DB에도 저장한다. 
		// [주의 1] 모든 id 값은 입력하지 않고 DB에서 자동으로 생성되게 만든다. 
		// [주의 2] Calendar를 생성할 때에는 DB에서 자동으로 생성된 id 값을 받아 내어서 Events를 만들 때 owner와 attendee 값으로 활용한다.
		
	}
	
	@Test
	public void createCalendarUserAndCompare() {
		// 2. 새로운 CalendarUser 2명 등록 및 각각 id 추출하고, 추출된 id와 함께 새로운 CalendarUser 2명을 DB에서 가져와 (getUser 메소드 사용) 
		//    방금 등록된 2명의 사용자와 내용 (이메일, 이름, 패스워드)이 일치하는 지 비교		
		CalendarUser account1 = new CalendarUser();
		account1.setName("Heesung");
		account1.setPassword("0000");
		account1.setEmail("lstar216@naver.com");
		
		CalendarUser account2 = new CalendarUser();
		account2.setName("YunJo");
		account2.setPassword("abcd1234");
		account2.setEmail("younjo@nate.com");
		
		int id1 = calendarUserDao.createUser(account1);
		int id2 = calendarUserDao.createUser(account2);
		
		CalendarUser newuser1 = calendarUserDao.getUser(id1);
		CalendarUser newuser2 = calendarUserDao.getUser(id2);
		
		assertThat(account1.getEmail(), is(newuser1.getEmail()));
		assertThat(account1.getName(), is(newuser1.getName()));
		assertThat(account1.getPassword(), is(newuser1.getPassword()));
		
		assertThat(account2.getEmail(), is(newuser2.getEmail()));
		assertThat(account2.getName(), is(newuser2.getName()));
		assertThat(account2.getPassword(), is(newuser2.getPassword()));
		
	}
	
	@Test
	public void createEventUserAndCompare() {
		// 3. 새로운 Event 2개 등록 및 각각 id 추출하고, 추출된 id와 함께 새로운 Event 2개를 DB에서 가져와 (getEvent 메소드 사용) 
		//    방금 추가한 2개의 이벤트와 내용 (summary, description, owner, attendee)이 일치하는 지 비교
		// [주의 1] when은 비교하지 않아도 좋다.
		// [주의 2] owner와 attendee는 @Before에서 미리 등록해 놓은 3명의 CalendarUser 중에서 임의의 것을 골라 활용한다.
		Event event1 = new Event();
		Calendar cal = Calendar.getInstance();
		cal.set(1989, 11, 11, 03, 00, 00);
		event1.setWhen(cal);
		event1.setSummary("My Birthday");
		event1.setDescription("I Born.");
		event1.setOwner(calendarUsers[0]);
		event1.setAttendee(calendarUsers[2]);
		
		Event event2 = new Event();
		cal = Calendar.getInstance();
		cal.clear();
		cal.set(2014, 9, 19, 20, 00, 00);
		event2.setWhen(cal);
		event2.setSummary("End");
		event2.setDescription("Assignment End");
		event2.setOwner(calendarUsers[1]);
		event2.setAttendee(calendarUsers[0]);
		
		int event_id1 = eventDao.createEvent(event1);
		int event_id2 = eventDao.createEvent(event2);
		
		Event newevent1 = eventDao.getEvent(event_id1);
		Event newevent2 = eventDao.getEvent(event_id2);
		
		assertThat(event1.getSummary(), is(newevent1.getSummary()));
		assertThat(event1.getDescription(), is(newevent1.getDescription()));
		assertThat(event1.getOwner(), is(newevent1.getOwner()));
		assertThat(event1.getAttendee(), is(newevent1.getAttendee()));
		
		assertThat(event2.getSummary(), is(newevent2.getSummary()));
		assertThat(event2.getDescription(), is(newevent2.getDescription()));
		assertThat(event2.getOwner(), is(newevent2.getOwner()));
		assertThat(event2.getAttendee(), is(newevent2.getAttendee()));
		
	}
	
	@Test
	public void getAllEvent() {
		// 4. 모든 Events를 가져오는 eventDao.getEvents()가 올바로 동작하는 지 (총 3개를 가지고 오는지) 확인하는 테스트 코드 작성  
		// [주의] fixture로 등록된 3개의 이벤트들에 대한 테스트
		List<Event> all_events = eventDao.getEvents();
		for(int i=0;i<all_events.size();i++)
		 assertThat(all_events.get(i), is(events[i]));
		//getEvents로 받아온 이벤트와 @before부분에서 저장한 events이벤트와 비교
		assertThat(all_events.size(),is(3));
		//총 3개 받아 왔는지 테스트
	}
	
	@Test
	public void getEvent() {
		// 5. owner ID가 3인 Event에 대해 findForOwner가 올바로 동작하는 지 확인하는 테스트 코드 작성  
		// [주의] fixture로 등록된 3개의 이벤트들에 대해서 owner ID가 3인 것인 1개의 이벤트뿐임 
		List<Event> Get_Events = eventDao.findForOwner(userID3);
		assertThat(Get_Events.get(0).getOwner().getId(),is(userID3));		
		//하드코딩으로 3을 주니까, 실행시 ownerID가 3이 아닌 경우가 대부분이었음. 3번째 user의 ID를 Owner ID로 정함.
	}
	
	@Test
	public void getOneUserByEmail() {
		// 6. email이 'user1@example.com'인 CalendarUser가 존재함을 확인하는 테스트 코드 작성 (null인지 아닌지)
		// [주의] public CalendarUser findUserByEmail(String email)를 테스트 하는 코드
		CalendarUser cal_user = calendarUserDao.findUserByEmail("user1@example.com");
		assertTrue(cal_user != null);
	}
	
	@Test
	public void getTwoUserByEmail() {
		// 7. partialEmail이 'user'인 CalendarUser가 2명임을 확인하는 테스크 코드 작성
		// [주의] public List<CalendarUser> findUsersByEmail(String partialEmail)를 테스트 하는 코드
		List<CalendarUser> cal_users = calendarUserDao.findUsersByEmail("user");
		assertThat(cal_users.size(), is(2));
		//user가 들어간 email 소유자가 2명인지 검사. 디폴트로 입력한 데이터에서는 2명.
	}
}
