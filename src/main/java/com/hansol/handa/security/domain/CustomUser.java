package com.hansol.handa.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.hansol.handa.domain.AuthVO;
import com.hansol.handa.domain.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUser extends User {
	
	private static final long serialVersionUID = 1L;

    private UserVO user;

    public CustomUser(String username, String password, 
        Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
    }
    
    @Override
    public Collection<GrantedAuthority> getAuthorities() {

    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    	
    	return authorities;
    }
    

    public CustomUser(UserVO vo) {
    	
    	super(vo.getMember_id(), vo.getPassword(), 
    			vo.getAuthList()
    			.stream()
    			.map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
    			.collect(Collectors.toList()));
    	
        log.info("CustomUSer - " + vo.toString());

        this.user = vo;
    }

}
