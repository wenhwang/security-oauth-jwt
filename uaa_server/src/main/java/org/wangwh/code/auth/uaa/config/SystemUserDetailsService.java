package org.wangwh.code.auth.uaa.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SystemUserDetailsService  implements UserDetailsService {

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			return User.withUsername("wangwh")
						.password("111")
						.roles("user")
						.authorities("read","writer")
						.build();
		}
	}