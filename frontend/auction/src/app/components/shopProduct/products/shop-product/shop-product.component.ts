import { Component, OnInit, Input } from "@angular/core";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { Router } from "@angular/router";

@Component({
  selector: "app-shop-product",
  templateUrl: "./shop-product.component.html",
  styleUrls: ["./shop-product.component.css"]
})
export class ShopProductComponent implements OnInit {
  @Input() product;
  @Input() className;

  faHeart = faHeart;
  faGavel = faGavel;

  constructor(private router: Router) {}

  ngOnInit() {}
  sendInfoOfProduct(product) {
    this.router.navigate(["/shop/product/" + product.title + "/" + product.id]);
  }
}
