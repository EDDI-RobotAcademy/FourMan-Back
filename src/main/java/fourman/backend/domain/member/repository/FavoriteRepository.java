package fourman.backend.domain.member.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByMemberIdAndCafeCafeId(Long memberId, Long cafeId);
    boolean existsByMemberIdAndCafeCafeId(Long memberId, Long cafeId);

    void deleteByCafeCafeId(Long cafeId);

    void deleteByMemberId(Long memberId);

    List<Favorite> findByCafe(Cafe cafe);
}
