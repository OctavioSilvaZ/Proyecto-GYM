package com.gym.proyecto.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.services.PersonalService;

public class UserInfoService implements UserDetailsService {

    @Autowired
    private PersonalService personalService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonalModel userDetails = this.personalService.buscarPorCorreo(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new UserInfoDetails(userDetails);
    }

}
