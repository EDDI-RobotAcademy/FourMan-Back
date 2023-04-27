package fourman.backend.domain.aop.aspect;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CheckSecurity {
    private final SecurityService securityService;
    private final MemberService memberService;
    private final HttpServletRequest request;

    @Pointcut("@annotation(fourman.backend.domain.aop.aspect.SecurityAnnotations.SecurityCheck)")
    public void checkMethodSecurity(){
    }

    @Around("checkMethodSecurity() && @annotation(securityCheck)")
    public Object checkSecurity(ProceedingJoinPoint joinPoint, SecurityAnnotations.SecurityCheck securityCheck) throws Throwable {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Basic ")) {
            token = token.substring(6);
        }
        log.info("인증에 사용될 토큰:"+token);
        Member currentMember = memberService.returnMemberInfo(token); //멤버객체를받아옴
        SecurityAnnotations.UserType requiredUserType = securityCheck.value();

        if (requiredUserType == SecurityAnnotations.UserType.MANAGER && securityService.isManager(currentMember)
                || requiredUserType == SecurityAnnotations.UserType.CAFE && securityService.isCafe(currentMember)
                || requiredUserType == SecurityAnnotations.UserType.AUTHENTICATED && securityService.isAuthenticated(currentMember)
                || requiredUserType == SecurityAnnotations.UserType.NOT_AUTHENTICATED && securityService.isNotAuthenticated(currentMember)) {
            log.info("Checking method security... Access granted");
            Object result = joinPoint.proceed();
            return result;
        } else {
            log.warn("Checking method security... Access denied.");
            throw new SecurityException("Access denied.");
        }
    }
}
