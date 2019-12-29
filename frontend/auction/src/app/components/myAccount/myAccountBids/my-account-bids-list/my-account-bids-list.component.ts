import { Component, OnInit, Input, OnChanges} from "@angular/core";
import { hdmy } from "../../../reusableFunctions/hdmy";
import { Router } from "@angular/router";
import { faCheckCircle } from "@fortawesome/free-solid-svg-icons";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-my-account-bids-list",
  templateUrl: "./my-account-bids-list.component.html",
  styleUrls: ["./my-account-bids-list.component.css"]
})
export class MyAccountBidsListComponent implements OnInit, OnChanges{
  @Input() items;
  @Input() timeToCount;

  statusOfCounter;
  expired;
  close = false;

  faCheckCircle = faCheckCircle;
  faTimes = faTimes;

  constructor(private router: Router) {}

  ngOnInit() {}

  ngOnChanges() {
    this.items.forEach(product => {
      let date = new Date(Date.parse(product.endDate));
      product.timeLeft = hdmy(
        Math.floor((<any>date - new Date().getTime()) / 1000)
      );
      product.timeToCount = Math.floor((<any>date - new Date().getTime()) / 1000);
      if(product.timeToCount <= 3600) {
       product.hideCounter = false;
      } else {
        product.hideCounter = true;
      }
      if(product.timeToCount < 0 && product.highestBid == product.valueOfBid) {
        this.expired = true;
      } else {
        if(this.expired == undefined) {
        this.expired = false;
        }
      }
    });
  }

  sendInfoOfProduct(title, id) {
    this.router.navigate(["/shop/product/" + title + "/" + id]);
  }

  handleEvent(event, product) {
    if(event.action == "notify") {
      this.items.forEach(item => {
        if(item.id == product.id) {
          product.hideCounter = false;
        }
      });
    } else if(event.action == "done") {
      this.expired = true;
      this.items.forEach(item => {
        if(item.id == product.id) {
          product.hideCounter = true;
          product.statusOfCounter = "done";
        }
      });
    }
  }
  closeMessage() {
    this.close = true;
  }
}
