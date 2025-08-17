package sn.unchk.librarymanagement.repository;

import org.springframework.data.jpa.repository.Query;
import sn.unchk.librarymanagement.domain.models.member.Member;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.domain.models.member.MemberStatus;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends BaseRepository<Member> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, UUID id);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdIsNot(String username, UUID id);

    Optional<Member> findByUsername(String username);

    @Query("SELECT m FROM Member m WHERE m.username = :username")
    Optional<MemberInfo> findMemberInfo(String username);

    List<Member> findAllByStatus(MemberStatus status);

    List<Member> findAllByRole(MemberRole role);
}
