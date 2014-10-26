package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.domain.CalendarUser;

public interface CalendarUserDao {
    public CalendarUser getUser(int id);

    public CalendarUser findUserByEmail(String email);

    public List<CalendarUser> findUsersByEmail(String partialEmail);

    public int createUser(CalendarUser user);
    
    public void deleteAll();
}
