import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from "@angular/core";

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
    if (valueFromSearch == "") {
      if (this.router.url.indexOf("category") != -1) {
        this.router.navigate(['/shop/' + this.router.url.substr(this.router.url.indexOf("category"), this.router.url.length)]);
      } else {
        this.router.navigate(['/shop']);
      }
    } else {
    if ((this.router.url == "/shop" || this.router.url == "/home" || this.router.url.substr(0, 13) == "/shop/search/") && this.router.url.indexOf("category") == -1) {
      this.router.navigate(['/shop/search/' + valueFromSearch]);
    }
    else if (this.router.url.indexOf("category") != -1) {
      this.router.navigate(['/shop/search/' + valueFromSearch + '/' + this.router.url.substr(this.router.url.indexOf("category"), this.router.url.length)]);
    }
  }
}

  
}
