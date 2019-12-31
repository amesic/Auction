import { Component, OnInit } from "@angular/core";
import { UserService } from "src/app/services/user.service";
import { LoginService } from "src/app/services/login.service";
import { ActivatedRoute } from '@angular/router';
import { ProductService } from 'src/app/services/product.service';
import { BidsService } from 'src/app/services/bids.service';

@Component({
  selector: "app-payment",
  templateUrl: "./payment.component.html",
  styleUrls: ["./payment.component.css"]
})
export class PaymentComponent implements OnInit {
  cardInfo;
  userInfo;
  product;

  highestBid;

  showMessageError = false;

  constructor(
    private userService: UserService,
    private loginService: LoginService,
    private activatedRoute: ActivatedRoute,
    private productService: ProductService,
    private bidService: BidsService
  ) {}

  ngOnInit() {
    this.activatedRoute.params.subscribe(routeParams => {
    this.userService
      .getUserInfo(this.loginService.getUserEmail())
      .subscribe(user => {
        this.userInfo = user;
      });
    this.userService.getCardInfo(this.loginService.getUserEmail()).subscribe(
      card => {
        this.cardInfo = card;
      },
      err => console.log(err.error)
    );
    this.productService.getSingleProduct(routeParams.idProduct).subscribe(product => {
      this.product = product;
    });
    this.bidService.getBidsInfoOfProduct(routeParams.idProduct, 0, 5)
      .subscribe(bidInfo => { }, 
        err => {
          this.highestBid = err.error.items[0];
        });
  });
  }

  pay() {
    if (this.cardInfo == null || this.userInfo.address == null) {
      this.showMessageError = true;
      window.scroll(0,0);
    } else {
      this.showMessageError = false;
    }
  }
}
