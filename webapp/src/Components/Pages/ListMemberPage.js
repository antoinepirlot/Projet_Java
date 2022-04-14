/**
 * Render the ListMemberPage
 */
import {
  confirmAdmin,
  confirmMember,
  denyMember,
  getDeniedMembers,
  getRegisteredMembers,
  isAdmin
} from "../../utils/BackEndRequests";
import {Redirect} from "../Router/Router";

const tableHtmlConfirmedMembers = `
  <div>
    <h1 class="display-6">Membres en attente d'acceptation</h1>
    <br>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">Nom</th>
          <th scope="col">Prénom</th>
          <th scope="col">Administrateur</th>
          <th scope="col">Confirmation</th>
          <th scope="col">Refus</th>
          <th scope="col"> Message du refus</th>
        </tr>
      </thead>
      <tbody id="tbody_registered_members">
      </tbody>
    </table>
  </div>
  <br>
  <br>
`;

const tableHtmlDeniedMembers = `
  <div>
    <h1 class="display-6">Membres refusés</h1>
    <br>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">Nom</th>
          <th scope="col">Prénom</th>
          <th scope="col">Administrateur</th>
          <th scope="col">Confirmation</th>
        </tr>
      </thead>
      <tbody id="tbody_denied_members">
      </tbody>
    </table>
  </div>
`;

const ListMemberPage = async () => {
  if (!await isAdmin()) {
    Redirect("/");
    return;
  }
  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = tableHtmlConfirmedMembers;
  pageDiv.innerHTML += tableHtmlDeniedMembers;
  await viewRegisteredMembers(await getRegisteredMembers());
  await viewDeniedMembers(await getDeniedMembers());
};

async function viewRegisteredMembers(members) {
  const tbody = document.querySelector("#tbody_registered_members");
  tbody.innerHTML = "";
  //For Each Member
  members.forEach((member) => {
    tbody.innerHTML += `
      <tr id="RegisteredLine">
        <td>${member.firstName}</td>
        <td>${member.lastName}</td>
        <td><input class="form-check-input" type="checkbox" value="" id="RegisteredIsAdmin"></td>
        <td><button type="submit" class="btn btn-primary" id="RegisteredConfirmButton" value=${member.id}>Confirmer</button></td>
        <td><button type="submit" class="btn btn-danger" id="RegisteredRefuseButton" value=${member.id}>Refuser</button></td>
        <td><div class="form-floating mb-3"><input type="text" class="form-control" id="floatingInput"><label for="floatingInput">Message</label></div></td>
      </tr>    
    `;

    // Is Admin Button
    const isAdminButton = document.querySelector("#RegisteredIsAdmin");

    // Confirm Button
    const confirmButtons = document.querySelectorAll(
        "#RegisteredConfirmButton");
    confirmButtons.forEach(confirmButton => {
      confirmButton.addEventListener("click", async function () {
        //Confirm the registration (Click on the button)
        if (isAdminButton.checked) {
          await confirmAdmin(confirmButton.value);
        } else {
          await confirmMember(confirmButton.value);
        }
        Redirect("/list_member");
      });
    });

    //Deny Button
    const denyButtons = document.querySelectorAll("#RegisteredRefuseButton");
    denyButtons.forEach(denyButton => {
      denyButton.addEventListener("click", async function () {
        //Confirm the registration (Click on the button)
        await denyMember(denyButton.value);
        Redirect("/list_member");
      });
    });
    const line = document.querySelector("#RegisteredLine");
    line.dataset.memberId = member.id;
  });
}

async function viewDeniedMembers(members) {
  const tbody = document.querySelector("#tbody_denied_members");
  tbody.innerHTML = "";
  //For Each Member
  members.forEach((member) => {
    tbody.innerHTML += `
      <tr id="DeniedLine">
        <td>${member.firstName}</td>
        <td>${member.lastName}</td>
        <td><input class="form-check-input" type="checkbox" value="" id="DeniedIsAdmin"></td>
        <td><button type="submit" class="btn btn-primary" id="DeniedConfirmButton">Confirmer</button></td>
      </tr>    
    `;

    // Is Admin Button
    const isAdminButton = document.querySelector("#DeniedIsAdmin");

    // Confirm Button
    const confirmButton = document.querySelector("#DeniedConfirmButton");
    confirmButton.addEventListener("click", async function () {

      //Confirm the registration (Click on the button)
      if (isAdminButton.checked) {
        await confirmAdmin(member.id);
      } else {
        await confirmMember(member.id);
      }
      Redirect("/list_member");
      //Redirect vers la meme page soit ListMemberPage
    });

    const line = document.querySelector("#DeniedLine");
    line.dataset.memberId = member.id;
  });
}

export default ListMemberPage;