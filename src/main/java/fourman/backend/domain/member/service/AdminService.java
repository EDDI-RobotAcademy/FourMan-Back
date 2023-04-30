package fourman.backend.domain.member.service;

import fourman.backend.domain.member.entity.ManagerCode;
import fourman.backend.domain.member.repository.ManagerCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor

public class AdminService {
    private final ManagerCodeRepository managerCodeRepository;

    public void ensureAdminExists() {
        Optional<ManagerCode> MaybeManagerCode = managerCodeRepository.findByCodeOfManager("manager1");

        if (!MaybeManagerCode.isPresent()) {
            ManagerCode managerCode= new ManagerCode();
            managerCode.setCodeOfManager("manager1");
            managerCodeRepository.save(managerCode);
            System.out.println("매니저코드가 존재하지 않아서 등록합니다.");
        }
        System.out.println("매니저코드가 존재합니다");
    }
}
