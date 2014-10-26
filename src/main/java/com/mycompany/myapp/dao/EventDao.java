package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.domain.Event;

public interface EventDao {

    public Event getEvent(int eventId);

    public int createEvent(Event event);

    public List<Event> findForOwner(int ownerUserId);

    public List<Event> getEvents();
    
    public void deleteAll();
}