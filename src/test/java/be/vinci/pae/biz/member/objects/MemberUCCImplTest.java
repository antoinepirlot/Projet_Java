package be.vinci.pae.biz.member.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.vinci.pae.biz.member.interfaces.MemberDTO;
import be.vinci.pae.biz.member.interfaces.MemberUCC;
import be.vinci.pae.biz.refusal.interfaces.RefusalDTO;
import be.vinci.pae.biz.refusal.objects.RefusalImpl;
import be.vinci.pae.dal.member.interfaces.MemberDAO;
import be.vinci.pae.dal.services.interfaces.DALServices;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.utils.ApplicationBinder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MemberUCCImplTest {

  private final ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());
  private final MemberDAO memberDAO = locator.getService(MemberDAO.class);
  private final DALServices dalServices = locator.getService(DALServices.class);
  private final MemberUCC memberUCC = locator.getService(MemberUCC.class);
  private final MemberDTO memberDTO = new MemberImpl();
  private final MemberDTO memberToLogIn = new MemberImpl();
  private final RefusalDTO refusalDTO = new RefusalImpl();
  private final List<MemberDTO> memberDTOList = new ArrayList<>();
  private String hashedPassword;
  private String password;
  private String wrongPassword;

  @BeforeEach
  void setUp() {
    try {
      Mockito.doNothing().when(this.dalServices).start();
      Mockito.doNothing().when(this.dalServices).commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    hashedPassword = "$2a$10$vD5FXSmaNv4DkfpFfKfDsOjaJ192x2RdWyjIWr28lj5r1X9uvB9yC";
    password = "password";
    wrongPassword = "wrongpassword";
  }

  private void configureMemberDTO(String actualState, String password) {
    this.memberDTO.setId(99);
    this.memberDTO.setActualState(actualState);
    this.memberDTO.setPassword(this.hashedPassword);
    this.memberDTO.setUsername("nico");
    this.memberToLogIn.setUsername("nico");
    this.memberToLogIn.setPassword(password);
    this.refusalDTO.setIdRefusal(1);
    this.refusalDTO.setMember(this.memberDTO);
    this.refusalDTO.setText("Refus");
    Mockito.when(this.memberDAO.getOne(this.memberToLogIn))
        .thenReturn(this.memberDTO);
  }

  private void configureMemberDTOState(String state) {
    memberDTO.setActualState(state);
    memberDTO.setId(99);
    Mockito.when(memberDAO.confirmMember(this.memberDTO)).thenReturn(true);
    Mockito.when(memberDAO.denyMember(this.refusalDTO)).thenReturn(true);
    Mockito.when(memberDAO.getOne(99)).thenReturn(memberDTO);
  }

  private void setGetAllMembersReturnedValue() {
    Mockito.when(this.memberDAO.getAllMembers()).thenReturn(this.memberDTOList);
  }

  private void setGetOneMemberReturnedValue(int idMember) {
    if (idMember >= 1) {
      Mockito.when(this.memberDAO.getOne(idMember)).thenReturn(this.memberDTO);
    } else {
      Mockito.when(this.memberDAO.getOne(idMember)).thenReturn(null);
    }
    Mockito.when(this.memberDAO.getOne(memberDTO)).thenReturn(memberDTO);
  }

  private void setModifyMemberReturnedValue() {
    Mockito.when(this.memberDAO.modifyMember(this.memberDTO)).thenReturn(this.memberDTO);
  }

  private void setErrorDALServiceStart() {
    try {
      Mockito.doThrow(new SQLException()).when(dalServices).start();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void setErrorDALServiceCommit() {
    try {
      Mockito.doThrow(new SQLException()).when(dalServices).commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @DisplayName("Test get all members working as expected")
  @Test
  void testGetAllMembersAsExpected() {
    this.setGetAllMembersReturnedValue();
    assertEquals(this.memberDTOList, this.memberUCC.getAllMembers());
  }

  @DisplayName("Test get all members working as expected")
  @Test
  void testGetAllMembersWithStartThrowingSQLException() {
    this.setErrorDALServiceStart();
    assertThrows(FatalException.class, this.memberUCC::getAllMembers);
  }

  @DisplayName("Test get all members working as expected")
  @Test
  void testGetAllMembersWithCommitThrowingSQLException() {
    this.setErrorDALServiceCommit();
    assertThrows(FatalException.class, this.memberUCC::getAllMembers);
  }

  @DisplayName("Test get one member working as expected")
  @Test
  void testGetOneMemberWorkingAsExpected() {
    this.setGetOneMemberReturnedValue(5);
    assertEquals(this.memberDTO, this.memberUCC.getOneMember(5));
  }

  @DisplayName("Test get one member with wrong id")
  @Test
  void testGetOneMemberWithWrongId() {
    this.setGetOneMemberReturnedValue(-1);
    assertNull(this.memberUCC.getOneMember(-1));
  }

  @DisplayName("Test get one member with start throwing sql exception")
  @Test
  void testGetOneMemberWithStartThrowingSQLException() {
    this.setErrorDALServiceStart();
    assertThrows(FatalException.class, () -> this.memberUCC.getOneMember(5));
  }

  @DisplayName("Test get one member commit throwing sql exception")
  @Test
  void testGetOneMemberWithCommitThrowingSQLException() {
    this.setErrorDALServiceCommit();
    assertThrows(FatalException.class, () -> this.memberUCC.getOneMember(5));
  }

  @DisplayName("Test modify member as expected")
  @Test
  void testModifyMemberAsExpected() {
    this.setModifyMemberReturnedValue();
    assertEquals(this.memberDTO, this.memberUCC.modifyMember(this.memberDTO));
  }

  @DisplayName("Test modify member with start throwing sql exception")
  @Test
  void testModifyMemberWithStartThrowingSQLException() {
    this.setErrorDALServiceStart();
    assertThrows(FatalException.class, () -> this.memberUCC.modifyMember(this.memberDTO));
  }

  @DisplayName("Test modify member with commit throwing sql exception")
  @Test
  void testModifyMemberWithCommitThrowingSQLException() {
    this.setErrorDALServiceCommit();
    assertThrows(FatalException.class, () -> this.memberUCC.modifyMember(this.memberDTO));
  }

  //Test Confirm Member

  @DisplayName("Test Confirm Member with the state registered")
  @Test
  void testConfirmMemberWithStateRegistered() {
    configureMemberDTOState("registered");
    assertTrue(memberUCC.confirmMember(this.memberDTO));
  }

  @DisplayName("Test Confirm Member with the state denied")
  @Test
  void testConfirmMemberWithStateDenied() {
    configureMemberDTOState("denied");
    assertTrue(memberUCC.confirmMember(this.memberDTO));
  }

  @DisplayName("Test Confirm Member with the state confirmed")
  @Test
  void testConfirmMemberWithStateConfirmed() {
    configureMemberDTOState("confirmed");
    assertTrue(memberUCC.confirmMember(this.memberDTO));
  }

  //Test Deny Member

  @DisplayName("Test Deny Member With the state confirmedd")
  @Test
  void testDenyMemberWithStateConfirmed() {
    configureMemberDTOState("confirmed");
    assertTrue(memberUCC.denyMember(this.refusalDTO));
  }

  @DisplayName("Test Deny Member With the state registered")
  @Test
  void testDenyMemberWithStateRegistered() {
    configureMemberDTOState("registered");
    assertTrue(this.memberUCC.denyMember(this.refusalDTO));
  }

  @DisplayName("Test Deny Member With the state denied")
  @Test
  void testDenyMemberWithStateDenied() {
    configureMemberDTOState("denied");
    assertTrue(memberUCC.denyMember(this.refusalDTO));
  }

  //Test Confirm Admin

  @DisplayName("Test Confirm Admin With the state denied")
  @Test
  void testConfirmAdminWithStateRefused() {
    configureMemberDTOState("denied");
    assertTrue(this.memberUCC.confirmMember(this.memberDTO));
  }

  @DisplayName("Test Confirm Admin With the state registered")
  @Test
  void testConfirmAdminWithStateRegistered() {
    configureMemberDTOState("registered");
    assertTrue(memberUCC.confirmMember(this.memberDTO));
  }

  @DisplayName("Test Confirm Admin With the state confirmed")
  @Test
  void testConfirmAdminWithStateConfirmed() {
    configureMemberDTOState("confirmed");
    assertTrue(this.memberUCC.confirmMember(this.memberDTO));
  }

  //Test Login

  @DisplayName("Test login with confirmed member and good password")
  @Test
  void testLoginConfirmedMemberWithGoodPassword() {
    configureMemberDTO("confirmed", password);
    assertEquals(memberDTO,
        memberUCC.login(memberToLogIn)
    );
  }

  @DisplayName("Test login with denied member and good password")
  @Test
  void testLoginDeniedMemberWithGoodPassword() {
    configureMemberDTO("denied", password);
    assertNull(memberUCC.login(memberToLogIn));
  }

  @DisplayName("Test login with registered member and good password")
  @Test
  void testLoginRegisteredMemberWithGoodPassword() {
    configureMemberDTO("registered", password);
    assertNull(memberUCC.login(memberToLogIn));
  }

  @DisplayName("Test login with confirmed member and wrong password")
  @Test
  void testLoginConfirmedMemberWithWrongPassword() {
    configureMemberDTO("confirmed", wrongPassword);
    assertNull(memberUCC.login(memberToLogIn));
  }

  @DisplayName("Test login with registered member and wrong password")
  @Test
  void testLoginRegisteredMemberWithWrongPassword() {
    configureMemberDTO("registered", wrongPassword);
    assertNull(memberUCC.login(memberToLogIn));
  }

  @DisplayName("Test login with denied member and wrong password")
  @Test
  void testLoginDeniedMemberWithWrongPassword() {
    configureMemberDTO("denied", wrongPassword);
    assertNull(memberUCC.login(memberToLogIn));
  }
}