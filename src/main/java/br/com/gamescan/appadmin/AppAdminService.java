package br.com.gamescan.appadmin;

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
public class AppAdminService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "usuário com email %s não foi encontrado";
    private final AppAdminRepository appAdminRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<AppAdmin> listAll() {
        return appAdminRepository.findAll();
    }

    public void save(AppAdmin appAdmin) {
        String encodedPassword = bCryptPasswordEncoder.encode(appAdmin.getPassword());
        String sanitizedEncodedPassword = removeSpecialCharacters(encodedPassword);
        appAdmin.setPassword(encodedPassword);
        appAdmin.setPasswordUrl(sanitizedEncodedPassword);
        appAdminRepository.save(appAdmin);
    }

    public String removeSpecialCharacters(String input) {
        return input.replace("/", "");
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    public AppAdmin get(Integer id) throws AppAdminNotFoundException {
        Optional<AppAdmin> result = appAdminRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw  new AppAdminNotFoundException("Não consegui encontrar nenhum usuário com o ID " + id);
    }

    public void delete(Integer id) throws AppAdminNotFoundException {
        Long count = appAdminRepository.countById(id);
        if (count == null || count == 0) {
            throw  new AppAdminNotFoundException("Não consegui encontrar nenhum usuário com o ID " + id);
        }
        appAdminRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appAdminRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public Integer getAdminIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof AppAdmin) {
            AppAdmin appAdmin = (AppAdmin) userDetails;
            return appAdmin.getId();
        } else {
            return null;
        }
    }
}