package sn.unchk.librarymanagement.service.member;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.unchk.librarymanagement.domain.exceptions.AlreadyExistsException;
import sn.unchk.librarymanagement.domain.exceptions.NotFoundException;
import sn.unchk.librarymanagement.domain.models.member.*;
import sn.unchk.librarymanagement.domain.validation.Create;
import sn.unchk.librarymanagement.domain.validation.Update;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberInfo;
import sn.unchk.librarymanagement.presentation.dto.reponse.MemberResponse;
import sn.unchk.librarymanagement.presentation.dto.request.MemberRequest;
import sn.unchk.librarymanagement.presentation.dto.request.ModifyPasswordRequest;
import sn.unchk.librarymanagement.presentation.dto.request.NewPasswordRequest;
import sn.unchk.librarymanagement.presentation.exceptions.PasswordNotMatchException;
import sn.unchk.librarymanagement.presentation.validation.RequestValidator;
import sn.unchk.librarymanagement.repository.MemberRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RequestValidator validator;
    private final PasswordEncoder passwordEncoder;


    public MemberServiceImpl(MemberRepository memberRepository, RequestValidator validator, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Member createAdmin(MemberRequest request) {
        if (memberRepository.existsByUsername(request.username()))
            throw new AlreadyExistsException("Username");

        if (memberRepository.existsByEmail(request.email()))
            throw new AlreadyExistsException("Email");

        Admin newAdmin = Admin.create( buildAdmin(request));

        validator.assertValidity(newAdmin, Create.class);

        return memberRepository.save(newAdmin);
    }
    private Admin buildAdmin(MemberRequest request) {
        return Admin.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .username(request.username())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }

    @Override
    public Member createReader(MemberRequest request) {
        if (memberRepository.existsByUsername(request.username()))
            throw new AlreadyExistsException("Username");

        if (memberRepository.existsByEmail(request.email()))
            throw new AlreadyExistsException("Email");

        Reader newReader = Reader.create(buildReader(request));

        validator.assertValidity(newReader, Create.class);

        return memberRepository.save(newReader);
    }

    private Reader buildReader(MemberRequest request) {
        return Reader.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .username(request.username())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }

    @Override
    public Member updateAdmin(UUID memberId, MemberRequest request) {
        Admin admin = (Admin) retrieveMember(memberId, request);

        Admin newAdminInfo = Admin.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .build();

        admin.update(newAdminInfo);

        validator.assertValidity(admin, Update.class);

        return memberRepository.save(admin);
    }

    private Member retrieveMember(UUID memberId, MemberRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("id", String.format("Member with id %s not found", memberId)));

        if (memberRepository.existsByUsernameAndIdIsNot(request.email(), memberId))
            throw new AlreadyExistsException("Username");

        if (memberRepository.existsByEmailAndIdIsNot(request.email(), memberId))
            throw new AlreadyExistsException("Email");

        return member;
    }

    @Override
    public Member updateReader(UUID memberId, MemberRequest request) {
        Reader reader = (Reader) retrieveMember(memberId, request);

        Reader newReaderInfo = Reader.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .build();

        reader.update(newReaderInfo);

        validator.assertValidity(reader, Update.class);

        return memberRepository.save(reader);
    }


    @Override
    public void defineNewPassword(UUID memberId, NewPasswordRequest request) {
        memberRepository.findById(memberId)
                .map(member -> {
                    member.changePassword(passwordEncoder.encode(request.password()));

                    validator.assertValidity(member, Update.class);

                    return memberRepository.save(member);
                }).orElseThrow(() -> new NotFoundException("id", String.format("Member with id %s not found", memberId)));
    }

    @Override
    public void modifyPassword(UUID memberId, ModifyPasswordRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("id", String.format("Member with id %s not found", memberId)));

        if (!passwordEncoder.matches(request.oldPassword(), member.getPassword()))
            throw new PasswordNotMatchException("Old password is incorrect.");

        member.changePassword(passwordEncoder.encode(request.newPassword()));

        validator.assertValidity(member, Update.class);

        memberRepository.save(member);
    }

    @Override
    public void changeStatus(UUID memberId, MemberStatus status) {
        memberRepository.findById(memberId)
                .map(member -> {
                    member.changeStatus(status);

                    validator.assertValidity(member, Update.class);

                    return memberRepository.save(member);
                }).orElseThrow(() -> new NotFoundException("id", String.format("Member with id %s not found", memberId)));
    }

    @Override
    public List<MemberResponse> retrieveAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberResponse::of)
                .toList();
    }


    @Override
    public List<MemberResponse> retrieveMemberByStatus(MemberStatus status) {
        return memberRepository.findAllByStatus(status)
                .stream()
                .map(MemberResponse::of)
                .toList();
    }

    @Override
    public List<MemberResponse> retrieveMemberByRole(MemberRole role) {
        return memberRepository.findAllByRole(role)
                .stream()
                .map(MemberResponse::of)
                .toList();
    }
    @Override
    public MemberResponse retrieveMemberInfoDetails(UUID memberId) {
        return memberRepository.findById(memberId)
                .map(MemberResponse::of)
                .orElseThrow(() -> new NotFoundException("id", String.format("Member with id %s not found", memberId)));
    }

    @Override
    public MemberInfo retrieveMemberInfo(String username) {
        return memberRepository.findMemberInfo(username)
                .orElseThrow(() -> new NotFoundException("username", String.format("Member with username %s not found", username)));
    }
}
