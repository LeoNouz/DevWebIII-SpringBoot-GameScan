package br.com.gamescan.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "usuário com email %s não foi encontrado";
    private AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<AppUser> listAll() {
        return appUserRepository.findAll();
    }

    public void save(AppUser appUser) {
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        String sanitizedEncodedPassword = removeSpecialCharacters(encodedPassword);
        appUser.setPassword(encodedPassword);
        appUser.setPasswordUrl(sanitizedEncodedPassword);
        appUserRepository.save(appUser);
    }

    public String removeSpecialCharacters(String input) {
        return input.replace("/", "");
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    public AppUser get(Integer id) throws AppUserNotFoundException {
        Optional<AppUser> result = appUserRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw  new AppUserNotFoundException("Não consegui encontrar nenhum usuário com o ID " + id);
    }

    public void delete(Integer id) throws AppUserNotFoundException {
        Long count = appUserRepository.countById(id);
        if (count == null || count == 0) {
            throw  new AppUserNotFoundException("Não consegui encontrar nenhum usuário com o ID " + id);
        }
        appUserRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public Integer getUserIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof AppUser) {
            AppUser appUser = (AppUser) userDetails;
            return appUser.getId();
        } else {
            return null;
        }
    }
}