package fourman.backend.domain.myPage.service;

import fourman.backend.domain.myPage.controller.requestForm.MyInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.responseForm.MemberInfoResponseForm;
import fourman.backend.domain.myPage.service.responseForm.MyInfoModifyResponseForm;
import fourman.backend.domain.myPage.service.responseForm.MyInfoResponseForm;

import java.util.List;

public interface MyPageService {

    MyInfoResponseForm myInfo(Long memberId);

    MyInfoModifyResponseForm myInfoModify(Long memberId, MyInfoModifyRequestForm modifyRequest);

    void withdrawal(Long memberId);

    List<MemberInfoResponseForm> memberList();
}
