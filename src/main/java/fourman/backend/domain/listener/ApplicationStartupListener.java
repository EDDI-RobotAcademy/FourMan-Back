package fourman.backend.domain.listener;

import fourman.backend.domain.member.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private AdminService adminService;//

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        adminService.ensureAdminExists();
    }
}