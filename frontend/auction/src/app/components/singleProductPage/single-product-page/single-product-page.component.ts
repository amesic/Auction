import { Component, OnInit } from "@angular/core";
import { ProductService } from "src/app/services/product.service";
import { ActivatedRoute, Router, NavigationEnd } from "@angular/router";
import { LoginService } from "src/app/services/login.service";
import { BidsService } from "src/app/services/bids.service";

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
  usersProduct;
  timeLeft;

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
    if (hours == 1) {
      hours = hours + " hour";
    } else if (hours == 0) {
      hours = "";
    } else {
      hours = hours + " hours";
    }

    if (days == 1) {
      days = days + " day";
    } else if (days == 0) {
      days = "";
    } else {
      days = days + " days";
    }

    if (months == 1) {
      months = months + " month";
    } else if (months == 0) {
      months = "";
    } else {
      months = months + " months";
    }
    
    if (years == 1) {
      years = years + " year";
    } else if (years == 0) {
      years = "";
    } else {
      years = years + " years";
    }
    return [years, months, days, hours].join(" ");
  }

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private loginService: LoginService,
    private bidService: BidsService,
    private router: Router
  ) {}

  ngOnInit() {
    this.userIsLoged = this.loginService.isUserLoggedIn();
    this.activatedRoute.params.subscribe(routeParams => {
      this.productService
        .getSingleProduct(routeParams.idProduct)
        .subscribe(singleProduct => {
          this.productInfo = singleProduct;
          let date = new Date(Date.parse(this.productInfo.endDate));
          this.timeLeft = this.dhms(
            Math.floor((<any>date - new Date().getTime()) / 1000)
          );
        });
      this.bidService.getBidsInfoOfProduct(routeParams.idProduct).subscribe(
        bidsOfProduct => {
          this.bidsOfProduct = bidsOfProduct;
          this.userIsSeller = true;
          this.usersProduct = "Your item!";
        },
        err => {
          this.bidsOfProduct = [];
          this.userIsSeller = false;
        }
      );
      this.productService
        .getRelatedProducts(routeParams.idProduct)
        .subscribe(relatedProducts => {
          this.relatedProducts = relatedProducts;
        });
    });
    this.router.events.subscribe(evt => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }
      window.scrollTo(0, 0);
    });
  }
}
