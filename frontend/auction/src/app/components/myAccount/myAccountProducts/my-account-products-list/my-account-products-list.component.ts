import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { Router } from "@angular/router";
import { hdmy } from '../../../reusableFunctions/hdmy';

@Component({
  selector: "app-my-account-products-list",
  templateUrl: "./my-account-products-list.component.html",
  styleUrls: ["./my-account-products-list.component.css"]
})
export class MyAccountProductsListComponent implements OnInit, OnChanges {
  @Input() items;
  @Input() activeActiveProducts;

  constructor(private router: Router) {}

  ngOnInit() {}
  ngOnChanges() {
    if (this.activeActiveProducts) {
    this.items.forEach(product => {
    let date = new Date(Date.parse(product.endDate));
    product.timeLeft = hdmy(Math.floor((<any>date - new Date().getTime()) / 1000));
    });
  }
  }
  sendInfoOfProduct(title, id) {
    this.router.navigate(["/shop/product/" + title + "/" + id]);
  }
}
