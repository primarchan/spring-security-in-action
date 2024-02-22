package org.example.ssiach11ex1s1.service;

import lombok.RequiredArgsConstructor;
import org.example.ssiach11ex1s1.entity.Otp;
import org.example.ssiach11ex1s1.entity.User;
import org.example.ssiach11ex1s1.repository.OtpRepository;
import org.example.ssiach11ex1s1.repository.UserRepository;
import org.example.ssiach11ex1s1.util.GenerateCodeUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(User user) {
        Optional<User> o = userRepository.findUserByUsername(user.getUsername());

        if (o.isPresent()) {
            User u = o.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad Credentials.");
            }
        } else {
            throw new BadCredentialsException("Bad Credentials.");
        }
    }

    private void renewOtp(User u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(u.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());

        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            if (otpToValidate.getCode().equals(otp.getCode())) {  // 데이터베이스에 OTP 가 있고, 비지니스 논리 서버에서 받은 OTP 와 일치하면 ture 반환
                return true;
            }
        }


        return false;
    }

}
