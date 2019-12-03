import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";

import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faInstagram } from "@fortawesome/free-brands-svg-icons";
import { faTwitter } from "@fortawesome/free-brands-svg-icons";
import { faGooglePlus } from "@fortawesome/free-brands-svg-icons";
import { faSearch } from "@fortawesome/free-solid-svg-icons";

import { LoginService } from "../../services/login.service";
import { MessageService } from 'src/app/services/message.service';

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
  
  message;
  @Output() messageEventSearchValue = new EventEmitter<string>();

  constructor(public loginService: LoginService, public messageService: MessageService) {}

  ngOnInit() {}
  /*sendMessage(valueFromSearch) {
    this.messageService.changeMessageSearch(valueFromSearch);
  }*/
  sendMessage(valueFromSearch) {
    this.message = valueFromSearch;
    this.messageEventSearchValue.emit(this.message);
  }
}
