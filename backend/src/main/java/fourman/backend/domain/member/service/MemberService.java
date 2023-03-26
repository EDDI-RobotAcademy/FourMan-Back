package fourman.backend.domain.member.service;

import fourman.backend.domain.member.entity.CafeCode;
import fourman.backend.domain.member.service.request.EmailMatchRequest;
import fourman.backend.domain.member.service.request.EmailPasswordRequest;
import fourman.backend.domain.member.service.request.MemberLoginRequest;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;
import fourman.backend.domain.member.service.response.MemberLoginResponse;

import java.util.Optional;

public interface MemberService {
    Boolean emailValidation(String email);
    Boolean managerCodeValidation(String managerCode);
    Boolean cafeCodeValidation(String cafeCode);
    Boolean signUp(MemberRegisterRequest memberRegisterRequest);
    MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest);
    Boolean applyNewPassword(EmailPasswordRequest toEmailPasswordRequest);
    Boolean emailMatch(EmailMatchRequest toEmailMatchRequest);
    Boolean memberNicknameValidation(String nickname);

}