import { Component, OnInit } from "@angular/core";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faList } from "@fortawesome/free-solid-svg-icons";
import { faGavel } from "@fortawesome/free-solid-svg-icons";
import { faGift } from "@fortawesome/free-solid-svg-icons";
import { Router } from '@angular/router';

@Component({
  selector: "app-tabs",
  templateUrl: "./tabs.component.html",
  styleUrls: ["./tabs.component.css"]
})
export class TabsComponent implements OnInit {
  faUser = faUser;
  faList = faList;
  faGavel = faGavel;
  faGift = faGift;

  activeProfile = true;
  activeProducts = false;
  activeBids = false;
  activeWatchlist = false;

  constructor(private router: Router) {}

  ngOnInit() {}
  profile() {
    this.activeProfile = true;
    this.activeProducts = false;
    this.activeBids = false;
    this.activeWatchlist = false;
    this.router.navigate(['/my-account']);
  }
  products() {
    this.activeProfile = false;
    this.activeProducts = true;
    this.activeBids = false;
    this.activeWatchlist = false;
    this.router.navigate(['/my-account/products']);
  }
  bids() {
    this.activeProfile = false;
    this.activeProducts = false;
    this.activeBids = true;
    this.activeWatchlist = false;
    this.router.navigate(['/my-account/bids']);
  }
  watchlist() {
    this.activeProfile = false;
    this.activeProducts = false;
    this.activeBids = false;
    this.activeWatchlist = true;
    this.router.navigate(['/my-account/watchlist']);
  }
}
