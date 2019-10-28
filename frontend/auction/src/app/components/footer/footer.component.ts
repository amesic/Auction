import { Component, OnInit } from '@angular/core';

import { faFacebook } from '@fortawesome/free-brands-svg-icons';
import { faInstagram } from '@fortawesome/free-brands-svg-icons';
import { faTwitter} from '@fortawesome/free-brands-svg-icons';
import { faGooglePlus} from '@fortawesome/free-brands-svg-icons';
import { faSearch} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  faFacebook = faFacebook;
  faInstagram = faInstagram;
  faTwitter = faTwitter;
  faGooglePlus=faGooglePlus;
  faSearch=faSearch;

  constructor() { }

  ngOnInit() {
  }

}
