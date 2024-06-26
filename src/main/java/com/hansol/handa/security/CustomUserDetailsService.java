package com.hansol.handa.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hansol.handa.domain.UserVO;
import com.hansol.handa.mapper.UserMapper;
import com.hansol.handa.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	
	@Setter(onMethod_ = { @Autowired })
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        
		log.warn("Load User By UserName : " + userName);
        UserVO vo = userMapper.read(userName);
        log.warn("queried by user mapper: " + vo);
        
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(vo.getAuth()));


        log.warn("add authlist queried by user mapper: " + vo);
        
		return new CustomUser(vo, authorities);
	}
	
	

}
