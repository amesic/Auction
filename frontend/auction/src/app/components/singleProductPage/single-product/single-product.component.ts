import { Component, OnInit, Input } from "@angular/core";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { LoginService } from 'src/app/services/login.service';
import { BidsService } from 'src/app/services/bids.service';

@Component({
  selector: "app-single-product",
  templateUrl: "./single-product.component.html",
  styleUrls: ["./single-product.component.css"]
})
export class SingleProductComponent implements OnInit {
  @Input() productInfo;
  @Input() bidsOfProduct;
  @Input() userIsLoged;
  @Input() userIsSeller;
  @Input() clickedImage;
  @Input() highestBid;
  @Input() numberOfBids;
  @Input() usersProduct;
  @Input() timeLeft;
  
  messStatusAboutBids;
  faChevronRight = faChevronRight;
  faHeart = faHeart;
  valueFromUser;
  errorMess = null;

  constructor(private loginService: LoginService, 
    private bidService: BidsService) {}

  ngOnInit() {
    if(this.userIsLoged != true) {
      this.errorMess = "Please login for bidding!"
    }
}
  clickOnImage(event) {
    this.clickedImage = event.target.src;
  }
  saveValue(value) {
    this.valueFromUser = value;
  }
  saveNewBid() {
    if(this.valueFromUser != null && this.valueFromUser.match("^[1-9][0-9]*$")) {
      this.bidService.saveBidFromUser(this.productInfo, this.loginService.getUserEmail(), 
      this.valueFromUser).subscribe(
        bid => {
          this.errorMess = null;
          this.highestBid = bid;
          this.numberOfBids = this.numberOfBids + 1;
          this.messStatusAboutBids = "Congrats! You are the highest bider!"
          window.scrollTo(0, 0);
      },
      err => {
        this.messStatusAboutBids = null;
        if(this.highestBid != null) {
        this.errorMess = "Please enter greater number than " + this.highestBid.value + "!";
        } else {
          this.errorMess = "Please enter greater number than " + this.productInfo.startPrice + "!";
        }
        window.scrollTo(0, 0);
    });
  }
    else {
      this.messStatusAboutBids = null;
      this.errorMess = "Please enter valid number!";
      window.scrollTo(0, 0);
    }
  }
}
