package sn.unchk.librarymanagement.presentation.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.unchk.librarymanagement.domain.models.member.MemberRole;
import sn.unchk.librarymanagement.domain.models.member.MemberStatus;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberResponse;
import sn.unchk.librarymanagement.presentation.dto.request.MemberRequest;
import sn.unchk.librarymanagement.presentation.dto.request.MemberStatusUpdateRequest;
import sn.unchk.librarymanagement.presentation.dto.request.ModifyPasswordRequest;
import sn.unchk.librarymanagement.presentation.dto.request.NewPasswordRequest;

import java.util.List;
import java.util.UUID;

import static sn.unchk.librarymanagement.constant.GlobalConstant.MEMBER_BASE_ROUTE;

@RequestMapping(value = MEMBER_BASE_ROUTE)
public interface MemberController {
    @PostMapping
    ResponseEntity<HttpResponse> createMember(@RequestBody MemberRequest request);

    @PatchMapping("/{id}")
    ResponseEntity<HttpResponse> updateMember(@PathVariable("id") UUID memberId, @RequestBody MemberRequest request);

    @PatchMapping("/{id}/status")
    ResponseEntity<HttpResponse> updateStatus(@PathVariable("id") UUID memberId, @RequestBody MemberStatusUpdateRequest status);

    @PatchMapping("/{id}/password")
    ResponseEntity<HttpResponse> changePassword(@PathVariable("id") UUID memberId, @RequestBody NewPasswordRequest request);

    @PatchMapping("/{id}/current-password")
    ResponseEntity<HttpResponse> updateCurrentPassword(@PathVariable("id") UUID memberId, @RequestBody ModifyPasswordRequest request);

    @GetMapping
    ResponseEntity<List<MemberResponse>> getAllMembers();
    
    @GetMapping("/{id}")
    ResponseEntity<MemberResponse> getMemberInfo(@PathVariable("id") UUID memberId);

    @GetMapping("/by-role")
    ResponseEntity<List<MemberResponse>> getMembersByRole(@RequestParam("role") MemberRole role);

    @GetMapping("/by-status")
    ResponseEntity<List<MemberResponse>> getMembersByStatus(@RequestParam("status") MemberStatus status);
}
