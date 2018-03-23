package com.lgc.ctps.sgpa.config.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.lgc.ctps.sgpa.config.security.utils.CipherUtils;

/**
 * @author H199653
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String simpleToken) {
		String key = null;

		// Quando utilizo o refresh token, o simple token é uma chave petro.
		// Isto porque no momento em que solicito o primeiro access token, eu devolvo um USer para o spring como chave.
		// return new User(key, simpleToken, getPermissions(person)); <- O username é a chave e ão o simpleToken...
		if (simpleToken.length() == 4) {
			key = simpleToken;
		} else {
			key = CipherUtils.getUserId(simpleToken).toUpperCase();
		}

//		Optional<Person> personOptional = personRepository.findByPermissions_Person_Key(key);
//		Person person = personOptional
//			.orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.003", null, LocaleContextHolder.getLocale())));

		return new User(key, simpleToken, getPermissions(key));
	}

	private Collection<? extends GrantedAuthority> getPermissions(String key) {
		return null;
//		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//		person.getPermissions()
//			.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getRole()
//				.getDescrption()
//				.toUpperCase())));
//		return authorities;

//		Set<GrantedAuthority> authorities
//	      = new HashSet<>();
//	    for (Role role: roles) {
//	        authorities.add(new SimpleGrantedAuthority(role.getName()));
//	        role.getPrivileges().stream()
//	         .map(p -> new SimpleGrantedAuthority(p.getName()))
//	         .forEach(authorities::add);
//	    }
//
//	    return authorities;
	}

}
