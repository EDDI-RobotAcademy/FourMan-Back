package fourman.backend.domain.member.service;

import fourman.backend.domain.member.service.request.EmailMatchRequest;
import fourman.backend.domain.member.service.request.EmailPasswordRequest;
import fourman.backend.domain.member.service.request.MemberLoginRequest;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;
import fourman.backend.domain.member.service.response.MemberLoginResponse;

public interface MemberService {
    Boolean emailValidation(String email);
    Boolean managerCodeValidation(String managerCode);
    Boolean signUp(MemberRegisterRequest memberRegisterRequest);
    MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest);
    Boolean applyNewPassword(EmailPasswordRequest toEmailPasswordRequest);

    Boolean emailMatch(EmailMatchRequest toEmailMatchRequest);

}