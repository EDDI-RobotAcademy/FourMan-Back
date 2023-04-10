package fourman.backend.domain.myPage.myInfo.service;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.myPage.myInfo.service.responseForm.MyInfoResponseForm;

import java.util.List;

public interface MyInfoService {

    MyInfoResponseForm myInfo(Long memberId);
}
