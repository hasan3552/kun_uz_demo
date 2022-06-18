package com.company.service;

import com.company.dto.VerificationDTO;
import com.company.dto.profile.AuthDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.RegistrationDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.SmsEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.repository.ProfileRepository;
import com.company.repository.SmsRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        entity.setPassword(dto.getPassword());
        entity.setPhone(dto.getPhone());

        entity.setRole(ProfileRole.USER);
        entity.setStatus(ProfileStatus.NOT_ACTIVE);
        profileRepository.save(entity);

        smsService.sendRegistrationSms(dto.getPhone());

        // name; surname; email; password;

//        ProfileDTO responseDTO = new ProfileDTO();
//        responseDTO.setName(dto.getName());
//        responseDTO.setSurname(dto.getSurname());
//        responseDTO.setEmail(dto.getEmail());
//        responseDTO.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return "Message sending";
    }

    public String verification(VerificationDTO dto) {
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(dto.getPhone());

        if (optional.isEmpty()) {
            return "Phone Not Found";
        }

        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        if (!sms.getCode().equals(dto.getCode())) {

            sms.setRequestCount(sms.getRequestCount()+1);
            smsRepository.save(sms);

            return "Code Invalid";
        }
        if (validDate.isBefore(LocalDateTime.now())) {

            return "Time is out";
        }

        if (sms.getRequestCount()>4) {

            return "exceeded the limit";

        }
        profileRepository.updateStatusByPhone(dto.getPhone(), ProfileStatus.ACTIVE);
        return "Verification Done";
    }

}
