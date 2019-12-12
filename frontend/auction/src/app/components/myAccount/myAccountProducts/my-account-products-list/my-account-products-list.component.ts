import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { Router } from "@angular/router";

@Component({
  selector: "app-my-account-products-list",
  templateUrl: "./my-account-products-list.component.html",
  styleUrls: ["./my-account-products-list.component.css"]
})
export class MyAccountProductsListComponent implements OnInit, OnChanges {
  @Input() items;
  @Input() activeActiveProducts;

  dhms(t) {
    var years, months, days, hours;
    years = Math.floor(t / 31104000);
    t -= years * 31104000;
    months = Math.floor(t / 2592000) % 12;
    t -= months * 2592000;
    days = Math.floor(t / 86400) % 30;
    t -= days * 86400;
    hours = Math.floor(t / 3600) % 24;
    t -= hours * 3600;
    if (hours == 0) {
      hours = "";
    } else {
      hours = hours + "h";
    }
    
    if (days == 0) {
      days = "";
    } else {
      days = days + "d";
    }

    if (months == 0) {
      months = "";
    } else {
      months = months + "m";
    }

    if (years == 0) {
      years = "";
    } else {
      years = years + "y";
    }
    return [years, months, days, hours].join(" ");
  }

  constructor(private router: Router) {}

  ngOnInit() {}
  ngOnChanges() {
    if (this.activeActiveProducts) {
    this.items.forEach(product => {
    let date = new Date(Date.parse(product.endDate));
    product.timeLeft = this.dhms(Math.floor((<any>date - new Date().getTime()) / 1000));
    });
  }
  }
  sendInfoOfProduct(title, id) {
    this.router.navigate(["/shop/product/" + title + "/" + id]);
  }
}
