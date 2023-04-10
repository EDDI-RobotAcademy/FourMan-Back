package fourman.backend.domain.noticeBoard.repository;

import fourman.backend.domain.noticeBoard.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
}
