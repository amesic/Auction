import { Component, OnInit, Input } from '@angular/core';
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  @Input() addressInfo;

  faChevronDown = faChevronDown;
  faChevronRight = faChevronRight;

  constructor() { }

  ngOnInit() {
  }

}
