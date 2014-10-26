package com.mycompany.myapp.domain;

/*
 * create table `calendar_users` (
    `id` int(32) unsigned not null auto_increment,
    `email` varchar(255) not null unique,
    `password` varchar(255) not null,
    `name` varchar(255) not null,
    PRIMARY KEY (`id`)    
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
*/

public class CalendarUser {
    private Integer id;
    private String email;
    private String password;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
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
        CalendarUser other = (CalendarUser) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}