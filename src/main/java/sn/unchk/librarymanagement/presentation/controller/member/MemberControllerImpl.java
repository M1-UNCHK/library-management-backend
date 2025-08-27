package sn.unchk.librarymanagement.presentation.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sn.unchk.librarymanagement.domain.models.member.Member;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.domain.models.member.MemberStatus;
import sn.unchk.librarymanagement.domain.validation.Update;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberResponse;
import sn.unchk.librarymanagement.presentation.dto.request.MemberRequest;
import sn.unchk.librarymanagement.presentation.dto.request.MemberStatusUpdateRequest;
import sn.unchk.librarymanagement.presentation.dto.request.ModifyPasswordRequest;
import sn.unchk.librarymanagement.presentation.dto.request.NewPasswordRequest;
import sn.unchk.librarymanagement.presentation.validation.RequestValidator;
import sn.unchk.librarymanagement.service.member.MemberService;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.*;

@RestController
public class MemberControllerImpl implements MemberController {
    private static final String ENTITY = "Member";
    private final MemberService memberService;
    private final RequestValidator validator;

    public MemberControllerImpl(MemberService memberService, RequestValidator validator) {
        this.memberService = memberService;
        this.validator = validator;
    }

    @Override
    public ResponseEntity<HttpResponse> createMember(MemberRequest request) {
        validator.assertValidity(request, Update.class);

        Member member = request.role().equals(MemberRole.ADMIN)
                ? memberService.createAdmin(request)
                : memberService.createReader(request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(CREATED_MESSAGE, ENTITY)));
    }

    @Override
    public ResponseEntity<HttpResponse> updateMember(UUID memberId, MemberRequest request) {
        validator.assertValidity(request, Update.class);

        Member member = request.role().equals(MemberRole.ADMIN)
                ? memberService.updateAdmin(memberId, request)
                : memberService.updateReader(memberId, request);

        return ResponseEntity.ok().body(HttpResponse.success(String.format(UPDATED_MESSAGE, ENTITY)));
    }

    @Override
    public ResponseEntity<HttpResponse> updateStatus(UUID memberId, MemberStatusUpdateRequest request) {
        memberService.changeStatus(memberId, request.status());
        return ResponseEntity.ok().body(HttpResponse.success(String.format(UPDATED_STATUS_MESSAGE, ENTITY)));
    }

    @Override
    public ResponseEntity<HttpResponse> changePassword(UUID memberId, NewPasswordRequest request) {
        validator.assertValidity(request, Update.class);

        memberService.defineNewPassword(memberId, request);
        return ResponseEntity.ok().body(HttpResponse.success(UPDATED_PASSWORD_MESSAGE));
    }

    @Override
    public ResponseEntity<HttpResponse> updateCurrentPassword(UUID memberId, ModifyPasswordRequest request) {
        validator.assertValidity(request, Update.class);

        memberService.modifyPassword(memberId, request);
        return ResponseEntity.ok().body(HttpResponse.success(UPDATED_PASSWORD_MESSAGE));
    }


    @Override
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok().body(memberService.retrieveAllMembers());
    }

    @Override
    public ResponseEntity<MemberResponse> getMemberInfo(UUID memberId) {
        return ResponseEntity.ok().body(memberService.retrieveMemberInfoDetails(memberId));
    }

    @Override
    public ResponseEntity<List<MemberResponse>> getMembersByRole(MemberRole role) {
        return ResponseEntity.ok().body(memberService.retrieveMemberByRole(role));
    }

    @Override
    public ResponseEntity<List<MemberResponse>> getMembersByStatus(MemberStatus status) {
        return ResponseEntity.ok().body(memberService.retrieveMemberByStatus(status));
    }
}
