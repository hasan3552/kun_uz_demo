package com.company.service;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.VerificationDTO;
import com.company.dto.profile.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.SmsEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.repository.SmsRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsRepository smsRepository;

    public ProfileDTO login(AuthDTO authDTO) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(authDTO.getEmail());
        if (optional.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        ProfileEntity profile = optional.get();
        if (!profile.getPassword().equals(authDTO.getPassword())) {
            throw new BadRequestException("User not found");
        }

        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new BadRequestException("No ruxsat");
        }

        ProfileDTO dto = new ProfileDTO();
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setJwt(JwtUtil.encode(profile.getId(), profile.getRole()));

        return dto;
    }

    public String registration(RegistrationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(encode(dto.getPassword()));
        entity.setPhone(dto.getPhone());

        entity.setRole(ProfileRole.USER);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(entity);

        //       smsService.sendRegistrationSms(dto.getPhone());

        emailService.sendRegistrationEmail(entity.getEmail(), entity);
        // name; surname; email; password;

//        ProfileDTO responseDTO = new ProfileDTO();
//        responseDTO.setName(dto.getName());
//        responseDTO.setSurname(dto.getSurname());
//        responseDTO.setEmail(dto.getEmail());
//        responseDTO.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return "Message sending";
    }

    public String encode(String password) {

        BCryptPasswordEncoder b = new BCryptPasswordEncoder();

        return b.encode(password);
    }

    public String verification(VerificationDTO dto) {
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(dto.getPhone());

        if (optional.isEmpty()) {
            return "Phone Not Found";
        }

        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        if (!sms.getCode().equals(dto.getCode())) {

            sms.setRequestCount(sms.getRequestCount() + 1);
            smsRepository.save(sms);

            return "Code Invalid";
        }
        if (validDate.isBefore(LocalDateTime.now())) {

            return "Time is out";
        }

        if (sms.getRequestCount() > 4) {

            return "exceeded the limit";

        }
        profileRepository.updateStatusByPhone(dto.getPhone(), ProfileStatus.ACTIVE);
        return "Verification Done";
    }

    public ResponseInfoDTO resendSms(String phone) {
        Long count = smsService.getSmsCount(phone);
        if (count >= 4) {
            return new ResponseInfoDTO(-1, "Limit dan o'tib getgan");
        }

        smsService.sendRegistrationSms(phone);
        return new ResponseInfoDTO(1);
    }

    public ResponseInfoDTO emailVerification(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseInfoDTO(-1, "<h1>User Not Found</h1>");
        }

        ProfileEntity profile = optional.get();
        if (!emailService.verificationTime(profile.getEmail())) {
            return new ResponseInfoDTO(-1, "<h1>Time is out</h1>");

        }

        profile.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(profile);
        return new ResponseInfoDTO(1, "<h1 style='align-text:center'>Success. Tabriklaymiz.</h1>");
    }

    public ResponseInfoDTO resendEmail(Integer id) {

        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("User not fount");
        }
        ProfileEntity profile = optional.get();

        Long count = emailService.countVerivifationSending(profile.getEmail());
        if (count >= 4) {
            return new ResponseInfoDTO(-1, "limit");
        }

        emailService.sendRegistrationEmail(profile.getEmail(), profile);
        return new ResponseInfoDTO(1, "success");
    }
}
