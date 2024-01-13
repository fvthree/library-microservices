package com.fvthree.app.auth;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Transactional
public class UserService {

    private static Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.encoder = encoder;
    }

    public User register(Map<?,?> request) {
        User createUser = User.builder()
                .username(request.get(AuthConstants.REQUEST_USERNAME).toString())
                .email(request.get(AuthConstants.REQUEST_EMAIL).toString())
                .password(encoder.encode(request.get(AuthConstants.REQUEST_PASSWORD).toString()))
                .build();
        return userRepository.save(createUser);
    }

    public JwtAccessToken login(Map<?,?> request) {
        String username = request.get(AuthConstants.REQUEST_USERNAME)!=null?
                request.get(AuthConstants.REQUEST_USERNAME).toString() : request.get(AuthConstants.REQUEST_EMAIL).toString();

        String password = encoder.encode(request.get(AuthConstants.REQUEST_PASSWORD).toString());

        logger.info("username {}, email: {}, password: {}", username, username, "");

        User user = userRepository.findByUsernameOrEmailAndPassword(username, username, password)
                .orElseThrow(()->new APIException(HttpStatus.BAD_REQUEST, "wrong username or password"));

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user.getUsername(), password);

        SecurityContextHolder.getContext().setAuthentication(userToken);

        String tokn = jwtTokenUtil.generateToken(userToken);

        return JwtAccessToken.builder().accessToken(tokn).refreshToken(tokn).build();
    }
}
