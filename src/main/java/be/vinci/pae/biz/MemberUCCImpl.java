package be.vinci.pae.biz;

import be.vinci.pae.dal.MemberDAO;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

public class MemberUCCImpl implements MemberUCC {

  @Inject
  private MemberDAO memberDAO;

  //  /**
  //   * Get all members from the database.
  //   *
  //   * @return all members
  //   */
  //  @Override
  //  public List<MemberDTO> getAll() {
  //    return memberDAO.getAll();
  //  }

  @Override
  public List<MemberDTO> getAllMembers() {
    List<MemberDTO> listMember = memberDAO.getAllMembers();
    return listMember;
  }

  /**
   * Get the member from the db, checks its state.
   *
   * @param username of the member
   * @param password of the member
   */
  @Override
  public MemberDTO login(String username, String password) {
    Member member = (Member) memberDAO.getOne(username, password);
    if (!member.checkPassword(password, member.getPassword())) {
      throw new WebApplicationException(Response.status(Status.NOT_FOUND)
          .entity("Wrong password or username")
          .type("text/plain")
          .build());
    }
    member.verifyState();
    return member;
  }

  /**
   * Get member from the db and check its state.
   *
   * @param username whose information we want
   * @return memberDTO from the username in parameter
   */
  @Override
  public MemberDTO getMember(String username, int id) {
    Member member = (Member) memberDAO.getOne(username);
    if (member == null || member.getId() != id) {
      throw new WebApplicationException(Response.status(Status.NOT_FOUND)
          .entity("ID or username incorrect")
          .type("text/plain")
          .build());
    }
    member.verifyState();
    return member;
  }


}