package com.erinc.service;

import com.erinc.dto.request.DoLoginRequestDto;
import com.erinc.dto.request.RegisterRequestDto;
import com.erinc.exception.AuthServiceException;
import com.erinc.exception.EErrorType;
import com.erinc.mapper.IAuthMapper;
import com.erinc.repository.IAuthRepository;
import com.erinc.repository.entity.Auth;
import com.erinc.utility.ServiceManager;
import com.erinc.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository repository;
    private final TokenManager tokenManager;

    public AuthService(IAuthRepository repository, TokenManager tokenManager){
        super(repository);
        this.repository=repository;
        this.tokenManager=tokenManager;
    }

    public Auth register(RegisterRequestDto dto){
        if(repository.isUsername(dto.getUsername()))
            throw new AuthServiceException(EErrorType.REGISTER_ERROR_USERNAME);
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        /**
         * Repo-> repository.save(auth); => Bu, kaydettiği entity'yi döner..
         * Serv->   save(auth);          => Bu da, kaydettiği entity'yi döner.
         * direkt-> auth, bir şekilde kayıt edilen entity'nin bilgileri işlenir ve bunu döner.
         */
        //return repository.save(auth);
        return save(auth);
    }

    public Optional<Auth> findOptionalByUsernameAndPassword(String username, String password){
        return repository.findOptionalByUsernameAndPassword(username,password);
    }

    /**
     * Kullanıcı doğrulayacak ve kullanıcının sistem içinde hareket edebilmesi için ona özel bir kimlik verecek.
     * Tokan -> JWT token.
     */
    public String doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(auth.isEmpty())
            throw new AuthServiceException(EErrorType.LOGIN_ERROR_USERNAME_PASSWORD);
        return tokenManager.createToken(auth.get().getId());
    }

    public List<Auth> findAll(String token){
        Long id = null;
        try {
            id = tokenManager.getIdByToken(token);
        }catch (Exception e){
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);
        }
        if(findById(id).isEmpty())
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);
        return findAll();
    }


}
