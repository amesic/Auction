import { Component, OnInit, Input } from "@angular/core";
import { ImageProduct } from "../../models/ImageProduct";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { LoginService } from 'src/app/services/login.service';
import { BidsService } from 'src/app/services/bids.service';
import { Bid } from 'src/app/models/Bid';

@Component({
  selector: "app-single-product",
  templateUrl: "./single-product.component.html",
  styleUrls: ["./single-product.component.css"]
})
export class SingleProductComponent implements OnInit {
  @Input() images: ImageProduct[] = [];
  @Input() title;
  @Input() description;
  @Input() startPrice; 
  @Input() highestBid;
  @Input() numberOfBids;
  @Input() days;
  @Input() months;
  @Input() years;
  @Input() clickedImage;
  @Input() userIsLoged;
  @Input() idProduct;

  faChevronRight = faChevronRight;
  faHeart = faHeart;
  valueFromUser;
  errorMess = null;

  constructor(private loginService: LoginService, private bidService: BidsService) {}

  ngOnInit() {
  }
  clickOnImage(event) {
    this.clickedImage = event.target.src;
  }
  saveValue(value) {
    this.valueFromUser = value;
  }

  saveNewBid() {
    if(this.valueFromUser != null && this.valueFromUser != "") {
      this.errorMess = null;
      let highestValue;
      if(this.highestBid != null) {
        highestValue = this.highestBid.value;
      }
      else {
        highestValue = this.startPrice;
      }
      this.bidService.saveBidFromUser(this.idProduct, this.loginService.getUserEmail(), 
      this.valueFromUser, highestValue).subscribe(
        bid => {
          this.highestBid = bid;
      },
      err => {
        this.errorMess = "Please enter greater number than " + highestValue;
    });
  }
    else {
      this.errorMess = "Please enter valid number!";
    }
  }
}
