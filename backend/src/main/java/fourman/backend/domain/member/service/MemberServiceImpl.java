package fourman.backend.domain.member.service;
import fourman.backend.domain.member.entity.Authentication;
import fourman.backend.domain.member.entity.BasicAuthentication;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.repository.AuthenticationRepository;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;
    final private AuthenticationRepository authenticationRepository;

    @Override
    public Boolean emailValidation(String email) {
        Optional<Member> maybeMember = memberRepository.findByEmail(email);
        if (maybeMember.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean signUp(MemberRegisterRequest memberRegisterRequest) {
        final Member member = memberRegisterRequest.toMember();
        memberRepository.save(member);

        final BasicAuthentication authentication = new BasicAuthentication(
                member,
                Authentication.BASIC_AUTH,//authenticationType칼럼에 값을 멤버변수 BASIC_AUTH의 값을 넣음
                memberRegisterRequest.getPassword()
        );
        authenticationRepository.save(authentication);

        return true;
    }

}