import { Component, OnInit, OnDestroy, HostListener, OnChanges } from "@angular/core";
import { ProductService } from "src/app/services/product.service";
import { ActivatedRoute, Router, NavigationStart, NavigationEnd } from "@angular/router";
import { LoginService } from "src/app/services/login.service";
import { BidsService } from "src/app/services/bids.service";
import { WebSocketService } from 'src/app/services/web-socket.service';
import { faTimes } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-single-product-page",
  templateUrl: "./single-product-page.component.html",
  styleUrls: ["./single-product-page.component.css"]
})
export class SingleProductPageComponent implements OnInit, OnDestroy{
  productInfo;
  bidsOfProduct;
  numberOfBids;
  highestBid;
  relatedProducts;
  userIsSeller = false;
  userIsLoged;
  usersProduct;
  timeLeft;
  hide;

  pageNumber = 0;
  size = 5;

  stompClient = null;
  sessionId;
  showNotification = false;
  faTimes = faTimes;

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

  numberOfViewers = 0;

  constructor(
    private productService: ProductService,
    private activatedRoute: ActivatedRoute,
    private loginService: LoginService,
    private bidService: BidsService,
    private router: Router,
    private webSocketService: WebSocketService
  ) {

  }
  ngOnInit() {
    this.userIsLoged = this.loginService.isUserLoggedIn();
    this.showNotification = false;
    this.activatedRoute.params.subscribe(routeParams => {
     if(this.stompClient != null) {
      let object = {
        "email": this.loginService.getUserEmail(),
        "productId" : this.productInfo.id,
        "sessionId": this.sessionId
      }
       this.stompClient.send("/app/send/message/disconnect" , {}, JSON.stringify(object));
       this.webSocketService._disconnect();
     }
      if (this.loginService.isUserLoggedIn()) {
        // Open connection with server socket
        this.stompClient = this.webSocketService.connect();
        this.stompClient.connect({}, frame => {
          let urlarray = this.stompClient.ws._transport.url.split('/');
          this.sessionId = urlarray[urlarray.length-2];
        // Subscribe to notification queue/notify
        this.stompClient.subscribe('/user/queue/notify', notifications => {
          // Update notifications attribute with the recent messsage sent from the server
          this.numberOfViewers = JSON.parse(notifications.body);
        });
        // Subscribe to notification queue/highestBid
        this.stompClient.subscribe('/user/queue/highestBid', notifications => {
          // Update notifications attribute with the recent messsage sent from the server
          this.highestBid = JSON.parse(notifications.body).highestBid;
          this.numberOfBids = JSON.parse(notifications.body).totalNumberOfItems;
          this.showNotification = true;
        });
            let object = {
              "email": this.loginService.getUserEmail(),
              "productId" : routeParams.idProduct,
              "sessionId": this.sessionId
            }
            this.stompClient.send("/app/send/message/connect" , {}, JSON.stringify(object));
        });
      }
      this.productService
        .getSingleProduct(routeParams.idProduct).subscribe(singleProduct => {
          this.productInfo = singleProduct;
          let date = new Date(Date.parse(this.productInfo.endDate));
          this.timeLeft = this.dhms(
            Math.floor((<any>date - new Date().getTime()) / 1000)
          );
        });
      this.productService.getNumberViewers(routeParams.idProduct).subscribe(number => {
        this.numberOfViewers = number;
      });
      this.bidService.getBidsInfoOfProduct(routeParams.idProduct, this.pageNumber, this.size).subscribe(
          bidInfo => {
            if (bidInfo == null) {
              this.bidsOfProduct = [];
              this.numberOfBids = null;
              this.highestBid = null;
              this.hide = true;
            } else {
              this.bidsOfProduct = bidInfo.items;
              this.numberOfBids = bidInfo.totalNumberOfItems;
              this.highestBid = bidInfo.items[0];
              this.pageNumber++;
              if (this.numberOfBids - this.pageNumber * this.size < 0 ||
                this.numberOfBids - this.pageNumber * this.size == 0) {
                this.hide = true;
              }
              this.hide = false;
            }
            this.userIsSeller = true;
            this.usersProduct = "This is your item.";
          },
          err => {
            this.bidsOfProduct = [];
            if (err.error != null) {
              this.numberOfBids = err.error.totalNumberOfItems;
              this.highestBid = err.error.highestBid;
            } else {
              this.numberOfBids = null;
              this.highestBid = null;
            }
            this.userIsSeller = false;
            this.hide = true;
            this.usersProduct = null;
          }
        );
      this.productService.getRelatedProducts(routeParams.idProduct).subscribe(relatedProducts => {
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
  ngOnDestroy() {
    if (this.loginService.isUserLoggedIn()) {
      let object = {
        "email": this.loginService.getUserEmail(),
        "productId" : this.productInfo.id,
        "sessionId": this.sessionId
      }
      this.stompClient.send("/app/send/message/disconnect" , {}, JSON.stringify(object));
    this.webSocketService._disconnect();
    }
  }
  @HostListener("window:beforeunload", ["$event"]) 
  unloadHandler(event: Event) {
    if (this.loginService.isUserLoggedIn()) {
      let object = {
        "email": this.loginService.getUserEmail(),
        "productId" : this.productInfo.id,
        "sessionId": this.sessionId
      }
      this.stompClient.send("/app/send/message/disconnect" , {}, JSON.stringify(object));
    this.webSocketService._disconnect();
    }
}

closeNotification() {
  this.showNotification = false;
}

recieveMessageFromSingleProduct(show) {
  this.showNotification = show;
}
  
}
