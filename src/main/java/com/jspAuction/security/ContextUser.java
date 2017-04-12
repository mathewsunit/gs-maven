package com.jspAuction.security;

import com.jspAuction.domain.User;
import com.google.common.collect.ImmutableSet;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by sunit on 3/20/17.
 */
public class ContextUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public ContextUser(User user) {
        super(user.getUserName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                ImmutableSet.of(new SimpleGrantedAuthority("create")));

        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
