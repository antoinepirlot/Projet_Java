import "bootstrap/dist/css/bootstrap.min.css";
import "./stylesheets/style.css"; // If you prefer to style your app with vanilla CSS rather than with Bootstrap
import Navbar from "./Components/Navbar/Navbar";
import {Router} from "./Components/Router/Router";
import {checkToken} from "./utils/session";

checkToken().then();

Navbar().then();

Router(); // The router will automatically load the root page
