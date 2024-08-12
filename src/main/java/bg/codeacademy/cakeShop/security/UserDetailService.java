package bg.codeacademy.cakeShop.security;

import bg.codeacademy.cakeShop.error_handling.exception.UserNotFoundException;
import bg.codeacademy.cakeShop.model.PersonalData;
import bg.codeacademy.cakeShop.repository.PersonalDataRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    private final PersonalDataRepository personalDataRepository;

    public UserDetailService(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        PersonalData data = personalDataRepository.findPersonalDataByUserName(username);
        if (data == null) {
            throw new UserNotFoundException("Username not found : " + username);
        }
        return new AuthenticatedUser(data);
    }
}
