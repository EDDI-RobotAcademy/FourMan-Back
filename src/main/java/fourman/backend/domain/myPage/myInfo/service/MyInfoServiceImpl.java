package fourman.backend.domain.myPage.myInfo.service;

import fourman.backend.domain.member.entity.Address;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.entity.MemberProfile;
import fourman.backend.domain.member.repository.MemberProfileRepository;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.myPage.myInfo.service.responseForm.MyInfoResponseForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardReadResponseForm;
import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyInfoServiceImpl implements MyInfoService{

    final private MemberRepository memberRepository;
    final private MemberProfileRepository memberProfileRepository;

    @Override
    public MyInfoResponseForm myInfo(Long memberId) {
        Optional<Member> maybeMember  = memberRepository.findById(memberId);
        Optional<MemberProfile> maybeMemberProfile  = memberProfileRepository.findById(memberId);

        if (maybeMember.isEmpty() || maybeMemberProfile.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }

        Member member = maybeMember.get();
        MemberProfile memberProfile = maybeMemberProfile.get();

        MyInfoResponseForm myInfoResponseForm = new MyInfoResponseForm(
                member.getNickName(), member.getBirthdate(), member.getEmail(),
                memberProfile.getPhoneNumber(), memberProfile.getAddress()
        );

        return myInfoResponseForm;
    }
}
