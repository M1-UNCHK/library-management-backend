package sn.unchk.librarymanagement.presentation.dto.reponse;


import sn.unchk.librarymanagement.domain.models.member.MemberRole;

public interface MemberInfo {
    String getUsername();
    String getEmail();
    MemberRole getRole();
}
