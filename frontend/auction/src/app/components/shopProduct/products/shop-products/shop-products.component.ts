import { Component, OnInit, Input } from "@angular/core";
import { faTh } from "@fortawesome/free-solid-svg-icons";
import { faList } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-shop-products",
  templateUrl: "./shop-products.component.html",
  styleUrls: ["./shop-products.component.css"]
})
export class ShopProductsComponent implements OnInit {
  @Input() products;

  faTh = faTh;
  faList = faList;
  faChevronDown = faChevronDown;

  activeGrid = true;
  activeList = false;
  className = "grid";

  constructor() {}

  ngOnInit() {}

  
  grid() {
    this.activeGrid = true;
    this.activeList = false;
    this.className = "grid";
  }
  list() {
    this.activeGrid = false;
    this.activeList = true;
    this.className = "list";
  }
}
