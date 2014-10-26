package com.mycompany.myapp.domain;

import java.util.Calendar;

/*
 * create table `events` (
    `id` int(32) unsigned not null auto_increment,
    `when` timestamp not null,
    `summary` varchar(255) not null,
    `description` varchar(500) not null,
    `owner` int(32) unsigned not null,
    `attendee` int(32) unsigned not null,
    PRIMARY KEY (`id`),
    KEY `fk_events_owner` (`owner`),
    KEY `fk_events_attendee` (`attendee`),
    CONSTRAINT `constraints_events_owner` FOREIGN KEY (`owner`) REFERENCES `calendar_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `constraints_events_attendee` FOREIGN KEY (`attendee`) REFERENCES `calendar_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
*/
public class Event {
    private Integer id;
    private Calendar when;
    private String summary;
    private String description;
    private CalendarUser owner;
    private CalendarUser attendee;

    public Integer getId() {
        return id;
    }
    
    public Calendar getWhen() {
        return when;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }
    
    public CalendarUser getOwner() {
        return owner;
    }

    public CalendarUser getAttendee() {
        return attendee;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setWhen(Calendar when) {
        this.when = when;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(CalendarUser owner) {
        this.owner = owner;
    }

    public void setAttendee(CalendarUser attendee) {
        this.attendee = attendee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}