package sn.unchk.librarymanagement.domain;

import org.junit.jupiter.api.Test;
import sn.unchk.librarymanagement.domain.exceptions.MalformedFieldException;
import sn.unchk.librarymanagement.domain.models.member.*;

import static org.junit.jupiter.api.Assertions.*;

public class MemberTests {
    @Test
    public void shouldCreateAdminWhenGivenValidInformationProvided() {
        Admin admin = Admin.builder()
                .firstname("John")
                .lastname("Doe")
                .username("john")
                .password("passer]123")
                .email("john.doe@example.com")
                .phoneNumber("0123456789")
                .address("123 Main St, Anytown, CA 12345")
                .build();

        Admin newAdmin = Admin.create(admin);

        assertEquals(admin.getFirstname(), newAdmin.getFirstname());
        assertEquals(admin.getLastname(), newAdmin.getLastname());
        assertNotNull(admin.getUsername(), newAdmin.getUsername());
        assertNotNull(admin.getPassword(), newAdmin.getPassword());
        assertEquals(admin.getEmail(), newAdmin.getEmail());
        assertEquals(admin.getPhoneNumber(), newAdmin.getPhoneNumber());
        assertEquals(admin.getAddress(), newAdmin.getAddress());
        assertEquals(MemberRole.ADMIN, newAdmin.getRole());
        assertEquals(MemberStatus.PENDING, newAdmin.getStatus());
    }

    @Test
    public void shouldNotCreateAdminWhenFieldsAreNotValid() {
        Admin admin = Admin.builder()
                .firstname(null)
                .lastname(null)
                .username(null)
                .email(null)
                .address(null)
                .build();

        assertThrows(MalformedFieldException.class, () -> Admin.create(admin));
    }

    @Test
    public void shouldCreateReaderWhenGivenValidInformationProvided() {
        Reader reader = Reader.builder()
                .firstname("John")
                .lastname("Doe")
                .username("john")
                .password("passer@123")
                .email("john.doe@example.com")
                .phoneNumber("0123456789")
                .address("123 Main St, Anytown, CA 12345")
                .password("123456")
                .build();

        Reader newReader = Reader.create(reader);

        assertEquals(reader.getFirstname(), newReader.getFirstname());
        assertEquals(reader.getLastname(), newReader.getLastname());
        assertNotNull(reader.getUsername(), newReader.getUsername());
        assertNotNull(reader.getPassword(), newReader.getPassword());
        assertEquals(reader.getEmail(), newReader.getEmail());
        assertEquals(reader.getPhoneNumber(), newReader.getPhoneNumber());
        assertEquals(reader.getAddress(), newReader.getAddress());
        assertEquals(MemberRole.READER, newReader.getRole());
        assertEquals(MemberStatus.PENDING, newReader.getStatus());
    }

    @Test
    public void shouldNotReaderAdminWhenFieldsAreNotValid() {
        Reader reader = Reader.builder()
                .firstname(null)
                .lastname(null)
                .username(null)
                .email(null)
                .address(null)
                .build();

        assertThrows(MalformedFieldException.class, () -> Reader.create(reader));
    }

    @Test
    public void shouldUpdateMemberFieldsWhenValidInformationProvided() {
        Member member = Reader.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .phoneNumber("0123456789")
                .address("124 Main St, Anytown")
                .build();

        Admin newAdminInfo = Admin.builder()
                .firstname("Jane")
                .lastname("Corpo")
                .email("jane.doe@example.com")
                .phoneNumber("9876543210")
                .address("123 Test Street")
                .build();

        member.update(newAdminInfo);

        assertEquals(newAdminInfo.getFirstname(), member.getFirstname());
        assertEquals(newAdminInfo.getLastname(), member.getLastname());
        assertEquals(newAdminInfo.getEmail(), member.getEmail());
        assertEquals(newAdminInfo.getPhoneNumber(), member.getPhoneNumber());
        assertEquals(newAdminInfo.getAddress(), member.getAddress());
    }

    @Test
    public void shouldChangeMemberStatusWhenTargetStatusIsProvided() {
        Admin admin = Admin.builder()
                .status(MemberStatus.ACTIVE)
                .build();

        admin.changeStatus(MemberStatus.INACTIVE);

        assertFalse(admin.isActive());
    }

    @Test
    public void shouldChangeMemberPasswordWhenNewPasswordIsProvided() {
        Admin admin = Admin.builder()
                .password("newpassword@123")
                .build();

        admin.changePassword("oldpassword@123");

        assertEquals("oldpassword@123", admin.getPassword());
    }
}