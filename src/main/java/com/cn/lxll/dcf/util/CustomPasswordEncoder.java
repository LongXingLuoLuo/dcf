package com.cn.lxll.dcf.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Project dcf
 * Created on 2024/6/13 上午11:33
 *
 * @author Lxll
 */
@Log4j2
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("rawPassword: {}, encodedPassword: {}", rawPassword, encodedPassword);
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return  new BCryptPasswordEncoder().upgradeEncoding(encodedPassword);
    }
}
