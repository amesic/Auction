import { Component, OnInit, Input, OnChanges, EventEmitter, Output} from "@angular/core";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faCheckCircle } from "@fortawesome/free-solid-svg-icons";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { LoginService } from 'src/app/services/login.service';
import { BidsService } from 'src/app/services/bids.service';
import { WatchlistService } from 'src/app/services/watchlist.service';
import { Router } from '@angular/router';

@Component({
  selector: "app-single-product",
  templateUrl: "./single-product.component.html",
  styleUrls: ["./single-product.component.css"]
})
export class SingleProductComponent implements OnInit, OnChanges {
  @Input() productInfo;
  @Input() bidsOfProduct;
  @Input() userIsLoged;
  @Input() userIsSeller;
  @Input() clickedImage;
  @Input() highestBid;
  @Input() numberOfBids;
  @Input() usersProduct;
  @Input() timeLeft;
  @Input() numberOfViewers;
  @Input() stompClient;
  @Input() sessionId;
  @Input() ratingSeller;
  @Input() paid;
 
  messStatusAboutBids;
  messStatusAboutWatchList;
  faChevronRight = faChevronRight;
  faCheckCircle = faCheckCircle;
  faStar = faStar;
  faTimes = faTimes;
  faHeart = faHeart;
  valueFromUser;
  errorMess = null;

  close = false;

  @Output() messageEvent = new EventEmitter<boolean>();


  constructor(
    private loginService: LoginService, 
    private bidService: BidsService,
    private watchlistService: WatchlistService,
    private router: Router) {} 
  
  ngOnChanges() {
    if (this.messStatusAboutBids != null) {
      this.messStatusAboutBids = null;
    }
    if (this.messStatusAboutWatchList != null) {
      this.messStatusAboutWatchList = null;
    }
    if (this.errorMess != null) {
      this.errorMess = null;
    }
    console.log(this.paid);
   }
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
          this.messageEvent.emit(false);
          let object = {
            "email": this.loginService.getUserEmail(),
            "productId" : this.productInfo.id,
            "sessionId": this.sessionId
          }
          this.stompClient.send("/app/send/message/highestBid" , {}, JSON.stringify(object));
          this.errorMess = null;
          this.messStatusAboutWatchList = null;
          this.highestBid = bid;
          this.numberOfBids = this.numberOfBids + 1;
          this.messStatusAboutBids = "Congrats! You are the highest bider!"
          window.scrollTo(0, 0);
      },
      err => {
        this.messStatusAboutBids = null;
        this.messStatusAboutWatchList = null;
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
  saveToWatchlist() {
    this.watchlistService.saveItemFromUserToWatchlist(this.productInfo.id, this.loginService.getUserEmail())
    .subscribe(savedItem => {
      this.messStatusAboutWatchList = "Item added to your watchlist!";
      this.messStatusAboutBids = null;
      this.errorMess = null;
      window.scrollTo(0, 0);
    },
    err => {
      this.errorMess = "Item already added to your watchlist!";
      this.messStatusAboutWatchList = null;
      this.messStatusAboutBids = null;
      window.scrollTo(0, 0);
    })
  }
  closeMessage() {
    this.close = true;
  }

  pay() {
    this.router.navigate(["/payment/" + this.productInfo.id]);
  }

}
