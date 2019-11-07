import { Component, OnInit } from "@angular/core";
import { ProductService } from "../../services/product.service";
import { Product } from "../../models/Product";

@Component({
  selector: "app-landing-page",
  templateUrl: "./landing-page.component.html",
  styleUrls: ["./landing-page.component.css"]
})
export class LandingPageComponent implements OnInit {
  advertisementProduct: Product; //from database one product for advertisement
  featureProducts: Array<Product>; //from database feature products
  featureCollection: Array<Product>; //from database feature collection

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getAdvProduct().subscribe(advPro => {
      this.advertisementProduct = advPro;
    });
    this.productService.getFeatPro().subscribe(featPro => {
      this.featureProducts = featPro;
    });
    this.productService.getFeatColl().subscribe(featColl => {
      this.featureCollection = featColl;
    });
  }
}
