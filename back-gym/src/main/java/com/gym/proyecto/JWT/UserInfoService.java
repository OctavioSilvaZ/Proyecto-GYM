package com.gym.proyecto.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gym.proyecto.models.PersonalModel;
import com.gym.proyecto.models.RolPersonalModel;
import com.gym.proyecto.services.PersonalService;
import com.gym.proyecto.services.RolPersonalService;

public class UserInfoService implements UserDetailsService {

    @Autowired
    private PersonalService personalService;

    @Autowired
    private RolPersonalService rolPersonalService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonalModel userDetails = this.personalService.buscarPorCorreo(username);
        RolPersonalModel rol = this.rolPersonalService.buscarPersonalId(userDetails.getId());

        if (userDetails == null || rol == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new UserInfoDetails(userDetails, rol);
    }

}
