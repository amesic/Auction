import { Component, OnInit, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { faTimes } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-my-account-watchlist-list',
  templateUrl: './my-account-watchlist-list.component.html',
  styleUrls: ['./my-account-watchlist-list.component.css']
})
export class MyAccountWatchlistListComponent implements OnInit, OnChanges {
  @Input() items;

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
    if (hours == 0) {
      hours = "";
    } else {
      hours = hours + "h";
    }
    
    if (days == 0) {
      days = "";
    } else {
      days = days + "d";
    }

    if (months == 0) {
      months = "";
    } else {
      months = months + "m";
    }

    if (years == 0) {
      years = "";
    } else {
      years = years + "y";
    }
    return [years, months, days, hours].join(" ");
  }

  message;
  @Output() messageEvent = new EventEmitter<string>();

  faTimes = faTimes;

  constructor(private router: Router) { }

  ngOnInit() {
  }
  ngOnChanges() {
    this.items.forEach(product => {
    let date = new Date(Date.parse(product.endDate));
    product.timeLeft = this.dhms(Math.floor((<any>date - new Date().getTime()) / 1000));
    product.active = <any>date >= new Date().getTime();
    });
  }

  sendInfoOfProduct(title, id) {
    this.router.navigate(["/shop/product/" + title + "/" + id]);
  }
  sendProductUser(productId) {
    this.message = productId;
    this.messageEvent.emit(this.message);
  }
}
