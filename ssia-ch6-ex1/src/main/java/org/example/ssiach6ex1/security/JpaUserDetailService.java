package org.example.ssiach6ex1.security;

import lombok.RequiredArgsConstructor;
import org.example.ssiach6ex1.domain.User;
import org.example.ssiach6ex1.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Supplier<UsernameNotFoundException> exceptionSupplier = () -> new UsernameNotFoundException("Problem during authentication!");

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Problem during authentication!"));

        return new CustomUserDetails(user);
    }

}
