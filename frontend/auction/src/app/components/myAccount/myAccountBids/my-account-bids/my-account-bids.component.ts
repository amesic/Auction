import { Component, OnInit } from "@angular/core";
import { BidsService } from "src/app/services/bids.service";
import { LoginService } from "src/app/services/login.service";
import { WebSocketService } from 'src/app/services/web-socket.service';

@Component({
  selector: "app-my-account-bids",
  templateUrl: "./my-account-bids.component.html",
  styleUrls: ["./my-account-bids.component.css"]
})
export class MyAccountBidsComponent implements OnInit {
  items;

  pageNumber = 0;
  size = 5;
  hide = false;

  stompClient;
  sessionId;

  constructor(
    private bidService: BidsService,
    private loginService: LoginService,
    private webSocketService: WebSocketService
  ) {}

  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    return totalNumberOfItems - pageNumber * size <= 0;
  }

  ngOnInit() {
    this.bidService.getBidsFromUser(this.pageNumber, this.size, this.loginService.getUserEmail())
      .subscribe(bidsFromUser => {
          this.items = bidsFromUser.items;
          this.pageNumber = this.pageNumber + 1;
          this.hide = this.checkIfThereIsNoItemsLeft(
            this.pageNumber,
            this.size,
            bidsFromUser.totalNumberOfItems
          );
        },
        err => {
          console.log(err);
        }
      );
}

  loadMore() {
    this.bidService
      .getBidsFromUser(
        this.pageNumber,
        this.size,
        this.loginService.getUserEmail()
      )
      .subscribe(
        bidsFromUser => {
          this.items = this.items.concat(bidsFromUser.items);
          this.pageNumber = this.pageNumber + 1;
          this.hide = this.checkIfThereIsNoItemsLeft(
            this.pageNumber,
            this.size,
            bidsFromUser.totalNumberOfItems
          );
        },
        err => {
          console.log(err);
        }
      );
  }
}
