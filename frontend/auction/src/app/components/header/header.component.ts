import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";

import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faInstagram } from "@fortawesome/free-brands-svg-icons";
import { faTwitter } from "@fortawesome/free-brands-svg-icons";
import { faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import { faSearch } from "@fortawesome/free-solid-svg-icons";

import { LoginService } from "../../services/login.service";
import { MessageService } from 'src/app/services/message.service';
import { Router } from '@angular/router';

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {
  faFacebook = faFacebook;
  faInstagram = faInstagram;
  faTwitter = faTwitter;
  faGooglePlus = faGooglePlus;
  faSearch = faSearch;

  @Input() userName = "";
  @Input() userLoged;

  constructor(public loginService: LoginService, public router: Router) {}

  ngOnInit() {}
  sendSearchValue(valueFromSearch) {
    this.router.navigate(['/shop/search/' + valueFromSearch]);
  }
  
}
