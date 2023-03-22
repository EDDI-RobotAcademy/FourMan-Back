package fourman.backend.domain.member.service;

import fourman.backend.domain.member.service.request.EmailMatchRequest;
import fourman.backend.domain.member.service.request.EmailPasswordRequest;
import fourman.backend.domain.member.service.request.MemberLoginRequest;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;

public interface MemberService {
    Boolean emailValidation(String email);
    Boolean signUp(MemberRegisterRequest memberRegisterRequest);
    String signIn(MemberLoginRequest memberLoginRequest);
    Boolean applyNewPassword(EmailPasswordRequest toEmailPasswordRequest);

    Boolean emailMatch(EmailMatchRequest toEmailMatchRequest);

}