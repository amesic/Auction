import { Component, OnInit, Input } from "@angular/core";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { Router } from "@angular/router";
import { WatchlistService } from 'src/app/services/watchlist.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: "app-shop-product",
  templateUrl: "./shop-product.component.html",
  styleUrls: ["./shop-product.component.css"]
})
export class ShopProductComponent implements OnInit {
  @Input() product;
  @Input() className;

  faHeart = faHeart;
  faGavel = faGavel;

  constructor(
    private router: Router, 
    private watchlistService: WatchlistService,
    private loginService: LoginService) {}

  ngOnInit() {}
  sendInfoOfProduct(product) {
    this.router.navigate(["/shop/product/" + product.title + "/" + product.id]);
  }
  saveToWatchlist() {
    this.watchlistService.saveItemFromUserToWatchlist(this.product.id, this.loginService.getUserEmail())
    .subscribe(savedItem => {
     
    },
    err => {
    })
  }
}
