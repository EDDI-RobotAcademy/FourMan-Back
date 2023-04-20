package fourman.backend.domain.myPage.service;

import fourman.backend.domain.myPage.controller.requestForm.CafeInfoModifyRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.MyInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.responseForm.*;

import java.util.List;

public interface MyPageService {

    MyInfoResponseForm myInfo(Long memberId);

    MyInfoSideBarResponseForm myInfoSideBar(Long memberId);

    MyInfoModifyResponseForm myInfoModify(Long memberId, MyInfoModifyRequestForm modifyRequest);

    void withdrawal(Long memberId);

    List<MemberInfoResponseForm> memberInfoList();

    List<CafeInfoResponseForm> cafeInfoList();

    CafeInfoResponseForm myCafeInfo(Long cafeId);

    void cafeInfoModify(Long cafeId, CafeInfoModifyRequestForm modifyRequest);
}
