import { Component, OnInit } from "@angular/core";
import { ProductService } from "src/app/services/product.service";
import { ActivatedRoute } from "@angular/router";
import { LoginService } from "src/app/services/login.service";
import { BidsService } from 'src/app/services/bids.service';

@Component({
  selector: "app-single-product-page",
  templateUrl: "./single-product-page.component.html",
  styleUrls: ["./single-product-page.component.css"]
})
export class SingleProductPageComponent implements OnInit {
  productInfo;
  bidsOfProduct;
  relatedProducts;
  userIsSeller = false;
  userIsLoged;
  constructor(
    private productService: ProductService,
    private router: ActivatedRoute,
    private loginService: LoginService,
    private bidService: BidsService
  ) {}

  ngOnInit() {
   this.userIsLoged = this.loginService.isUserLoggedIn();
    this.router.params.subscribe(routeParams => {
      this.productService
        .getSingleProduct(routeParams.idProduct)
        .subscribe(singleProduct => {
          this.productInfo = singleProduct;
          if (
            this.loginService.getUserEmail() != null &&
            this.productInfo.seller.email == this.loginService.getUserEmail()) {
            this.userIsSeller = true;
          }
        });
      this.bidService
        .getBidsInfoOfProduct(routeParams.idProduct)
        .subscribe(bidsOfProduct => {
          this.bidsOfProduct = bidsOfProduct;
        });
      this.productService
        .getRelatedProducts(routeParams.idSubcategory, routeParams.idProduct)
        .subscribe(relatedProducts => {
          this.relatedProducts = relatedProducts;
        });
    });
  }
}
