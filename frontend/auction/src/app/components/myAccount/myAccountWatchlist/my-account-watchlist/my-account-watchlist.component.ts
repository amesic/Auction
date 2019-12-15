import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { LoginService } from 'src/app/services/login.service';
import { WatchlistService } from 'src/app/services/watchlist.service';

@Component({
  selector: 'app-my-account-watchlist',
  templateUrl: './my-account-watchlist.component.html',
  styleUrls: ['./my-account-watchlist.component.css']
})
export class MyAccountWatchlistComponent implements OnInit {
  items;

  pageNumber = 0;
  size = 5;
  hide = false;

  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    if (totalNumberOfItems - pageNumber * size < 0 || totalNumberOfItems - pageNumber * size == 0) {
      return true;
    }
    return false;
  }

  constructor(private watchlistService: WatchlistService, 
    private loginService: LoginService) { }

  ngOnInit() {
    this.watchlistService.getWatchlistFromUser(this.loginService.getUserEmail(), 
    this.pageNumber, this.size).subscribe(watchlist => {
      this.items = watchlist.items;
      this.pageNumber = this.pageNumber + 1;
      this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, watchlist.totalNumberOfItems);
    });
  }

  loadMore() {
    this.watchlistService.getWatchlistFromUser(this.loginService.getUserEmail(), 
    this.pageNumber, this.size).subscribe(watchlist => {
      this.items = this.items.concat(watchlist.items);
      this.pageNumber = this.pageNumber + 1;
      this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, watchlist.totalNumberOfItems);
    });
  }

  recieveMessageOnDeleteItemFromWatchlist($event) {
    let productId = $event;
    this.watchlistService.deleteFromWatchlistOfUser(
      this.loginService.getUserEmail(), 
      productId,
      this.pageNumber,
      this.size).subscribe(watchlist => {
        this.items = watchlist.items;
        this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, watchlist.totalNumberOfItems);
      });
  }

}
