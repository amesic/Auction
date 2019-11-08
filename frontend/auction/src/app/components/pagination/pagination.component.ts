import { Component, OnInit, Input } from "@angular/core";
import { Product } from "../../models/Product";
import { ProductService } from "../../services/product.service";

@Component({
  selector: "app-pagination",
  templateUrl: "./pagination.component.html",
  styleUrls: ["./pagination.component.css"]
})
export class PaginationComponent implements OnInit {
  pageNumber;
  size = 8;
  products: Product[] = [];
  hide = false;
  @Input() className;

  activeLinkNewArrivals = true;
  activeLinkLastChance = false;

  event = "New Arrivals";

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.pageNumber = 0;
    this.getNewArrivals(this.pageNumber, this.size);
  }
  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    if (
      pageNumber * size + totalNumberOfItems - size == totalNumberOfItems ||
      pageNumber * size + totalNumberOfItems - size < 0
    ) {
      return true;
    }
    return false;
  }
  getNewArrivals(pageNumber, size) {
    this.productService
      .getNewArrivals(pageNumber, size)
      .subscribe(paginationInfo => {
        this.products = this.products.concat(paginationInfo.items);
        if (
          this.checkIfThereIsNoItemsLeft(
            pageNumber,
            size,
            paginationInfo.totalNumberOfItems
          )
        ) {
          this.hide = true;
        }
      });
  }
  getLastChance(pageNumber, size) {
    this.productService
      .getLastChance(pageNumber, size)
      .subscribe(paginationInfo => {
        this.products = this.products.concat(paginationInfo.items);
        if (
          this.checkIfThereIsNoItemsLeft(
            pageNumber,
            size,
            paginationInfo.totalNumberOfItems
          )
        ) {
          this.hide = true;
        }
      });
  }
  onClick() {
    this.pageNumber += 1;
    if (this.event == "New Arrivals") {
      this.getNewArrivals(this.pageNumber, this.size);
    } else {
      this.getLastChance(this.pageNumber, this.size);
    }
  }
  clickLink(event) {
    this.event = event.target.innerHTML;
    if (event.target.innerHTML == "New Arrivals") {
      this.pageNumber = 0;
      this.products = [];
      this.hide = false;
      this.getNewArrivals(this.pageNumber, this.size);
      this.activeLinkNewArrivals = true;
      this.activeLinkLastChance = false;
    } else {
      this.pageNumber = 0;
      this.products = [];
      this.hide = false;
      this.getLastChance(this.pageNumber, this.size);
      this.activeLinkNewArrivals = false;
      this.activeLinkLastChance = true;
    }
  }
}
