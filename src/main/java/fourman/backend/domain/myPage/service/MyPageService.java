package fourman.backend.domain.myPage.service;

import fourman.backend.domain.myPage.controller.requestForm.AddPointRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.CafeInfoModifyRequestForm;
import fourman.backend.domain.myPage.controller.requestForm.MyInfoModifyRequestForm;
import fourman.backend.domain.myPage.service.responseForm.*;

import java.util.List;

public interface MyPageService {

    MyInfoResponse myInfo(Long memberId);

    MyInfoSideBarResponse myInfoSideBar(Long memberId);

    MyInfoModifyResponse myInfoModify(Long memberId, MyInfoModifyRequestForm modifyRequest);

    void withdrawal(Long memberId);

    List<MemberInfoResponse> memberInfoList();

    List<CafeInfoResponse> cafeInfoList();

    CafeInfoResponse myCafeInfo(Long cafeId);

    void cafeInfoModify(Long cafeId, CafeInfoModifyRequestForm modifyRequest);

    Boolean addPoint(Long memberId, AddPointRequestForm pointRequestForm);

    List<PointDetailsResponse> pointDetailsList();
}
