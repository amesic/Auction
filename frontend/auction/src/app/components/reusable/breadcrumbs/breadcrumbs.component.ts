import { Component, OnInit } from "@angular/core";
import { Input } from "@angular/core"; //koristimo komponentu Input

@Component({
  selector: "app-breadcrumbs",
  templateUrl: "./breadcrumbs.component.html",
  styleUrls: ["./breadcrumbs.component.css"]
})
export class BreadcrumbsComponent implements OnInit {
  @Input() title1: string;
  @Input() title2: string;
  @Input() title3: string;
  routerLink;
  constructor() {}

  ngOnInit() {
    if (this.title2 == "SHOP") {
      this.routerLink = ['/shop'];
    }
    if (this.title2 != undefined) {
      this.title2 = this.title2 + "/";
    }
  }
}
