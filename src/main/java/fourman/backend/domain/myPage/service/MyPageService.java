package fourman.backend.domain.myPage.service;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.service.response.MemberLoginResponse;
import fourman.backend.domain.myPage.controller.requestForm.MemberInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.responseForm.MemberInfoResponseForm;
import fourman.backend.domain.myPage.service.responseForm.MyInfoResponseForm;

import java.util.List;

public interface MyPageService {

    MyInfoResponseForm myInfo(Long memberId);

    MemberLoginResponse memberInfoModify(Long memberId, MemberInfoModifyRequestForm modifyRequest);

    void withdrawal(Long memberId);

    List<MemberInfoResponseForm> memberList();
}
