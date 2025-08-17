package sn.unchk.librarymanagement.service.member;

import sn.unchk.librarymanagement.domain.models.member.Member;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.domain.models.member.MemberStatus;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberInfo;
import sn.unchk.librarymanagement.presentation.dto.request.MemberRequest;
import sn.unchk.librarymanagement.presentation.dto.request.ModifyPasswordRequest;
import sn.unchk.librarymanagement.presentation.dto.request.NewPasswordRequest;

import java.util.List;
import java.util.UUID;

public interface MemberService {
    Member createAdmin(MemberRequest request);

    Member createReader(MemberRequest request);

    Member updateAdmin(UUID memberId, MemberRequest request);
    Member updateReader(UUID memberId, MemberRequest request);
    void defineNewPassword(UUID memberId, NewPasswordRequest request);

    void modifyPassword(UUID memberId, ModifyPasswordRequest request);

    void changeStatus(UUID memberId, MemberStatus status);

    List<MemberResponse> retrieveAllMembers();

    List<MemberResponse> retrieveMemberByStatus(MemberStatus status);

    List<MemberResponse> retrieveMemberByRole(MemberRole role);
    MemberResponse retrieveMemberInfoDetails(UUID memberId);

    MemberInfo retrieveMemberInfo(String username);
}
