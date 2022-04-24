// When using Bootstrap to style components, the CSS is imported in index.js
// However, the JS has still to be loaded for each Bootstrap's component that needs it.
// Here, because our JS component 'Navbar' has the same name as Navbar Bootstrap's component
// we change the name of the imported Bootstrap's 'Navbar' component

/**
 * Render the Navbar which is styled by using Bootstrap
 * Each item in the Navbar is tightly coupled with the Router configuration :
 * - the URI associated to a page shall be given in the attribute "data-uri" of the Navbar
 * - the router will show the Page associated to this URI when the user click on a nav-link
 */

import {getObject} from "../../utils/session";
import {isAdmin} from "../../utils/BackEndRequests";

const navBarHtml = `
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
      <a class="navbar-brand" data-uri="/" >Donnamis</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul id="navbarLinks" class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" data-uri="/">Acceuil</a>
          </li>
        </ul>
      </div>
      <div id="usernameNavbar">
      </div>
    </div>
  </nav>
`;

const loginLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/login">Se connecter</a>
          </li>
`;

const registerLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/register">S'inscrire</a>
          </li>
`;

const logoutLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/logout">Se déconnecter</a>
          </li>
`;

const listMemberLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/list_member">Liste des membres</a>
          </li>
`;

const searchMembersLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/search_members">Rechercher des membres</a>
          </li>
`;

const allItemsLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/all_items">Tous les objets</a>
          </li>
`;

const allOfferedItemsLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/all_offered_items">Tous les objets offerts</a>
          </li>
`;

const offerAnItemLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/offer_item">Offrir un objet</a>
          </li>
`;

const myItemsLinkHtml = `
          <li class="nav-item">
            <a class="nav-link" href="#" data-uri="/my_items">Mes offres</a>
          </li>
`;

const profilLinkHtml = `
  <a id="memberUsername" class="nav-link" href="#" data-uri="/profil"></a>`;

const myAssignedItemsLinkHtml = `
  <li class="nav-item">
    <a class="nav-link" href="#" data-uri="/assigned_items">Mes objets assignés</a>
  </li>
`

const Navbar = async () => {
  const navbarWrapper = document.querySelector("#navbarWrapper");
  navbarWrapper.innerHTML = navBarHtml;
  const memberDTO = getObject("memberDTO");
  const links = document.querySelector("#navbarLinks");
  if (memberDTO) {
    const naveBarMemberPlace = document.querySelector("#usernameNavbar");
    naveBarMemberPlace.innerHTML = profilLinkHtml;
    const memberUsername = document.querySelector("#memberUsername");
    memberUsername.innerHTML += memberDTO.username;
    if (await isAdmin()) {
      links.innerHTML += listMemberLinkHtml;
      links.innerHTML += searchMembersLinkHtml;
      links.innerHTML += allItemsLinkHtml;
      memberUsername.innerHTML = memberDTO.username + " (admin)";
    }
    links.innerHTML += allOfferedItemsLinkHtml;
    links.innerHTML += offerAnItemLinkHtml;
    links.innerHTML += myItemsLinkHtml;
    links.innerHTML += myAssignedItemsLinkHtml

    links.innerHTML += logoutLinkHtml;
  } else {
    links.innerHTML += loginLinkHtml;
    links.innerHTML += registerLinkHtml;
  }
};

export default Navbar;
