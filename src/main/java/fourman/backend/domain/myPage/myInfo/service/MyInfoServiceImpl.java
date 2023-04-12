package fourman.backend.domain.myPage.myInfo.service;

import fourman.backend.domain.member.entity.Address;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.entity.MemberProfile;
import fourman.backend.domain.member.repository.MemberProfileRepository;
import fourman.backend.domain.member.repository.MemberRepository;
import fourman.backend.domain.member.service.response.MemberLoginResponse;
import fourman.backend.domain.myPage.myInfo.controller.requestForm.MemberInfoModifyRequestForm;
import fourman.backend.domain.myPage.myInfo.service.responseForm.MyInfoResponseForm;
import fourman.backend.domain.reviewBoard.controller.responseForm.ReviewBoardReadResponseForm;
import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import fourman.backend.domain.reviewBoard.repository.ReviewBoardRepository;
import fourman.backend.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyInfoServiceImpl implements MyInfoService{

    final private MemberRepository memberRepository;
    final private MemberProfileRepository memberProfileRepository;

    final private RedisService redisService;

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

    @Override
    public MemberLoginResponse memberInfoModify(Long memberId, MemberInfoModifyRequestForm modifyRequest) {
        // 기존 사용자 정보 조회
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findById(memberId);

        if (maybeMember.isEmpty()) {
            log.info("읽을 수가 없드아!");
            return null;
        }

        // 멤버 프로필 수정
        MemberProfile memberProfile = maybeMemberProfile.get();

        Address address = new Address();
        address.setCity(modifyRequest.getCity());
        address.setStreet(modifyRequest.getStreet());
        address.setAddressDetail(modifyRequest.getAddressDetail());
        address.setZipcode(modifyRequest.getZipcode());

        memberProfile.setAddress(address);
        memberProfile.setPhoneNumber(modifyRequest.getPhoneNumber());

        // 멤버 정보 수정
        Member member = maybeMember.get();
        member.setNickName(modifyRequest.getNickName());
        member.setBirthdate(modifyRequest.getBirthdate());


        // 사용자 정보 저장
        memberRepository.save(member);
        memberProfileRepository.save(memberProfile);

        UUID userToken = UUID.randomUUID();

        redisService.deleteByKey(userToken.toString());
        redisService.setKeyAndValue(userToken.toString(), member.getId());

        MemberLoginResponse memberLoginResponse = new MemberLoginResponse(userToken.toString(), member.getId(), member.getNickName(), member.getAuthority().getAuthorityName(), member.getCode(), null, member.getEmail());

        return memberLoginResponse;
    }
}
