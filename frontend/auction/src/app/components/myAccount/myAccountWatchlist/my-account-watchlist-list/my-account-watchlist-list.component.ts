import { Component, OnInit, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { hdmy } from '../../../reusableFunctions/hdmy'

@Component({
  selector: 'app-my-account-watchlist-list',
  templateUrl: './my-account-watchlist-list.component.html',
  styleUrls: ['./my-account-watchlist-list.component.css']
})
export class MyAccountWatchlistListComponent implements OnInit, OnChanges {
  @Input() items;

  message;
  @Output() messageEvent = new EventEmitter<string>();

  faTimes = faTimes;

  constructor(private router: Router) { }

  ngOnInit() {
  }
  ngOnChanges() {
    this.items.forEach(product => {
    let date = new Date(Date.parse(product.endDate));
    product.timeLeft = hdmy(Math.floor((<any>date - new Date().getTime()) / 1000));
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
