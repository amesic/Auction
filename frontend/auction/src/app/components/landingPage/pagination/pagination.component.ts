import { Component, OnInit, Input } from "@angular/core";
import { Product } from "../../../models/Product";
import { ProductService } from "../../../services/product.service";

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
  messageIfProductsIsNull;

  @Input() className;

  activeLinkNewArrivals = true;
  activeLinkLastChance = false;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.pageNumber = 0;
    this.getNewArrivals(this.pageNumber, this.size);
  }
  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    if (totalNumberOfItems - pageNumber * size < 0 || totalNumberOfItems - pageNumber * size == 0) {
      return true;
    }
    return false;
  }
  getNewArrivals(pageNumber, size) {
    this.productService
      .getNewArrivals(pageNumber, size)
      .subscribe(paginationInfo => {
        if (paginationInfo != null) {
          this.messageIfProductsIsNull = "";
          this.products = this.products.concat(paginationInfo.items);
          if (
            this.checkIfThereIsNoItemsLeft(
              pageNumber + 1,
              size,
              paginationInfo.totalNumberOfItems
            )
          ) {
            this.hide = true;
          }
        } else {
          this.products = null;
          this.hide = true;
          this.messageIfProductsIsNull = "There are no new arrivals!";
        }
      });
  }
  getLastChance(pageNumber, size) {
    this.productService
      .getLastChance(pageNumber, size)
      .subscribe(paginationInfo => {
        if (paginationInfo != null) {
          this.messageIfProductsIsNull = "";
          this.products = this.products.concat(paginationInfo.items);
          if (
            this.checkIfThereIsNoItemsLeft(
              pageNumber + 1,
              size,
              paginationInfo.totalNumberOfItems
            )
          ) {
            this.hide = true;
          }
        } else {
          this.products = null;
          this.hide = true;
          this.messageIfProductsIsNull = "There are no last chances!";
        }
      });
  }
  onClick() {
    this.pageNumber += 1;
    if (this.activeLinkNewArrivals) {
      this.getNewArrivals(this.pageNumber, this.size);
    } else {
      this.getLastChance(this.pageNumber, this.size);
    }
  }
  newArrivals() {
    this.pageNumber = 0;
    this.products = [];
    this.hide = false;
    this.getNewArrivals(this.pageNumber, this.size);
    this.activeLinkNewArrivals = true;
    this.activeLinkLastChance = false;
  }
  lastChance() {
    this.pageNumber = 0;
    this.products = [];
    this.hide = false;
    this.getLastChance(this.pageNumber, this.size);
    this.activeLinkNewArrivals = false;
    this.activeLinkLastChance = true;
  }
}
