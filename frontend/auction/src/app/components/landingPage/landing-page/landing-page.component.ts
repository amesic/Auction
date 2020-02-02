import { Component, OnInit } from "@angular/core";
import { ProductService } from "../../../services/product.service";
import { Product } from "../../../models/Product";

@Component({
  selector: "app-landing-page",
  templateUrl: "./landing-page.component.html",
  styleUrls: ["./landing-page.component.css"]
})
export class LandingPageComponent implements OnInit {
  advertisementProduct: Product; //from database one product for advertisement
  featureProducts: Product[] = []; //from database feature products
  featureCollection: Product[] = []; //from database feature collection

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getAdvertisementProduct().subscribe(advPro => {
      this.advertisementProduct = advPro;
    });
    this.productService.getFeatureProducts().subscribe(featPro => {
      this.featureProducts = featPro;
    });
    this.productService.getFeatureCollection().subscribe(featColl => {
      this.featureCollection = featColl;
    });
  }
}
