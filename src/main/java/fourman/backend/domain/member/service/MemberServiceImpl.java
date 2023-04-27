package fourman.backend.domain.member.service;
import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.cafeIntroduce.repository.CafeRepository;
import fourman.backend.domain.member.controller.form.FavoriteForm;
import fourman.backend.domain.member.entity.*;
import fourman.backend.domain.member.repository.*;
import fourman.backend.domain.member.service.request.EmailMatchRequest;
import fourman.backend.domain.member.service.request.EmailPasswordRequest;
import fourman.backend.domain.member.service.request.MemberLoginRequest;
import fourman.backend.domain.member.service.request.MemberRegisterRequest;
import fourman.backend.domain.member.service.response.MemberLoginResponse;
import fourman.backend.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;
    final private ManagerCodeRepository managerCodeRepository;
    final private CafeCodeRepository cafeCodeRepository;
    final private CafeRepository cafeRepository;
    final private AuthenticationRepository authenticationRepository;
    final private RedisService redisService;
    final private PointRepository pointRepository;
    final private FavoriteRepository favoriteRepository;
    final private PointInfoRepository pointInfoRepository;

    @Override
    public Boolean emailValidation(String email) {
        Optional<Member> maybeMember = memberRepository.findByEmail(email);
        if (maybeMember.isPresent()) {//이메일이 존재한다면
            return false;//false면 중복된게 있다는말
        }
        return true;
    }
    @Override
    public Boolean memberNicknameValidation(String nickName) {
        Optional<Member> maybeMemberNickname = memberRepository.findByNickName(nickName);

        if (maybeMemberNickname.isPresent()) {
            return false;
        }
        return true;
    }


    @Override
    public Boolean cafeCodeValidation(String cafeCode) {
        Optional<CafeCode> maybeCafe = cafeCodeRepository.findByCodeOfCafe(cafeCode);
        if (maybeCafe.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean signUp(MemberRegisterRequest memberRegisterRequest) {
        Member member = null;
        if(memberRegisterRequest.getAuthorityName().equals(AuthorityType.MEMBER)) {
            member = memberRegisterRequest.toMember();
            List<PointInfo> infoList = new ArrayList<>();
            String history = "회원가입 축하 포인트";
            PointInfo pointInfo = new PointInfo(history, 1000l, false);
            infoList.add(pointInfo);
            Point point = new Point(1000l, member, infoList);
            member.setPoint(point);
            pointRepository.save(point);
            pointInfoRepository.saveAll(infoList);
        }
        else if(memberRegisterRequest.getAuthorityName().equals(AuthorityType.CAFE)){
            Optional<CafeCode> cafeCode=cafeCodeRepository.findByCodeOfCafe(memberRegisterRequest.getCode());
            if(!cafeCode.isPresent()){
                log.info("카페코드가 없습니다");
                return null;
            }
            member = memberRegisterRequest.toCafeMember(cafeCode.get());


        }else  if(memberRegisterRequest.getAuthorityName().equals(AuthorityType.MANAGER)){
            Optional<ManagerCode> managerCode=managerCodeRepository.findByCodeOfManager(memberRegisterRequest.getCode());
            if(!managerCode.isPresent()){
                log.info("매니저코드가 없습니다");
                return null;
            }
            member = memberRegisterRequest.toManagerMember(managerCode.get());

        }

            memberRepository.save(member);
        final BasicAuthentication authentication = new BasicAuthentication(
                member,
                Authentication.BASIC_AUTH,//authenticationType칼럼에 값을 멤버변수 BASIC_AUTH의 값을 넣음
                memberRegisterRequest.getPassword()
        );
        authenticationRepository.save(authentication);
        return true;
    }


    @Override
    public MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest) {
        try {
        Optional<Member> maybeMember =
                memberRepository.findByEmail(memberLoginRequest.getEmail());

        System.out.println("loginRequest: " + memberLoginRequest);
        System.out.println("maybeMember.isPresent(): " + maybeMember.isPresent());

        if (maybeMember.isPresent()) {//이메일이 존재할경우
            Member member = maybeMember.get();

            System.out.println("사용자가 입력한 비번: " + memberLoginRequest.getPassword());
            System.out.println("비밀번호 일치 검사: " + member.isRightPassword(memberLoginRequest.getPassword()));
            if (!member.isRightPassword(memberLoginRequest.getPassword())) {
                System.out.println("비밀번호가 맞지 않습니다. ?");
                throw new RuntimeException("이메일 및 비밀번호 입력이 잘못되었습니다!");
                //있는 이메일인데 비번이 잘못입력됨
            }
            //비번이 제대로 입력됨(로그인성공)
            UUID userToken = UUID.randomUUID();

            // redis 처리 필요
            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), member.getId());
            //레디스에 토큰:유저ID 입력

            MemberLoginResponse memberLoginResponse=null;
            System.out.println("member.getAuthority().getAuthorityName(): "+member.getAuthority().getAuthorityName());

            if(member.getAuthority().getAuthorityName().equals(AuthorityType.CAFE)){ //카페사업자의 경우카페명 UI로 보내줌
                log.info("카페사업자 입니다!");
                if(member.getCafeCode() !=null){
                    if(member.getCafeCode().getCafe()==null){
                        memberLoginResponse = new MemberLoginResponse(userToken.toString(), member.getId(), member.getNickName(), member.getAuthority().getAuthorityName(), null ,member.getCafeCode().getCodeOfCafe(),member.getCafeCode().getCafeName(), member.getEmail());
                        log.info("카페를 아직 등록하지않음.");
                        return memberLoginResponse;
                    }
                    System.out.println("op.get().getCafe().getCafeId():"+member.getCafeCode().getCafe().getCafeId());
                    memberLoginResponse = new MemberLoginResponse(userToken.toString(),member.getId(),member.getNickName(), member.getAuthority().getAuthorityName(),member.getCafeCode().getCafe().getCafeId(),member.getCafeCode().getCodeOfCafe(),member.getCafeCode().getCafeName(), member.getEmail());
                }
                else{
                    log.info("카페코드를 못찾았습니다");
                }
            }else {
                log.info("카페사업자가 아닙니다.");
                memberLoginResponse = new MemberLoginResponse(userToken.toString(), member.getId(), member.getNickName(), member.getAuthority().getAuthorityName(), null ,member.getCafeCode().getCodeOfCafe(), null, member.getEmail());
            }
            return memberLoginResponse;
        }

            throw new RuntimeException("가입된 사용자가 아닙니다!");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException 발생!");
            e.printStackTrace();
            throw new RuntimeException("NullPointerException이 발생했습니다. 로그를 확인하세요.");
        }
    }
    @Override
    public Boolean applyNewPassword(EmailPasswordRequest emailPasswordRequest) {
        Optional<Authentication> maybeAuthentication = authenticationRepository.findByEmail(emailPasswordRequest.getEmail());
        if (!maybeAuthentication.isPresent()){ //인증정보가 존재하지 않을 경우
            System.out.println("인증정보가 존재하지않습니다.");
            return false;
        }
        BasicAuthentication authentication = (BasicAuthentication)maybeAuthentication.get();
        authentication.setPassword(emailPasswordRequest.getPassword());
        authenticationRepository.save(authentication);
        System.out.println("비밀번호 변경완료");

        return true;
    }

    @Override
    public Boolean emailMatch(EmailMatchRequest toEmailMatchRequest) {
        Optional<Member> maybeMember = memberRepository.findByEmail(toEmailMatchRequest.getEmail());
        if (!maybeMember.isPresent()){//이메일이 존재하지 않을경우
            return false;
        }

        return true;//이미존재하는 이메일
    }
    @Override
    public Boolean managerCodeValidation(String managerCode) {
        Optional<ManagerCode> maybeManager = managerCodeRepository.findByCodeOfManager(managerCode);
        if (maybeManager.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public String toggleFavorite(FavoriteForm favoriteForm) {
        Optional<Favorite> favorite = favoriteRepository.findByMemberIdAndCafeCafeId(favoriteForm.getMemberId(), favoriteForm.getCafeId());
        Optional<Member> member = memberRepository.findById(favoriteForm.getMemberId());
        Optional<Cafe> cafe = cafeRepository.findById(favoriteForm.getCafeId()) ;
        if (favoriteForm.getIsFavorite()) {
            if (!favorite.isPresent()) {
                Favorite newFavorite = new Favorite();
                newFavorite.setMember(member.get());
                newFavorite.setCafe(cafe.get());
                favoriteRepository.save(newFavorite);
                return"찜 했습니다.";
            }
            return "favorite이 존재하지않습니다.";
        } else {
            favorite.ifPresent(favoriteRepository::delete);
            return "찜 취소했습니다.";
        }
    }

    @Override
    public boolean isFavorite(Long memberId, Long cafeId) {
        return favoriteRepository.existsByMemberIdAndCafeCafeId(memberId, cafeId);
    }

    @Override
    public Member returnMemberInfo(String token) {
        Long id = redisService.getValueByKey(token);
        if(token == null || token.isEmpty()){
            log.info("토큰이 null입니다,");
            return null;
        }
        System.out.println("검색된id:"+id);
        log.info("검색된 id: {}", id);
        System.out.println("token:"+token);
        if (id == null && token !=null) {
            log.info(" 토큰이 있는데 검색이 안되니 레디스에서 토큰이 만료된 것입니다. 로그아웃 처리됩니다.");
            return null;
        }
        Optional<Member> member = memberRepository.findByMemberId(id);
        if (!member.isPresent()) {
            log.info("멤버가 존재하지 않습니다.");
            return null;
        }
        return member.get();
    }


}