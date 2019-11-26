import { Component, OnInit, ViewChild } from "@angular/core";
import { CategoriesService } from "src/app/services/categories.service";
import { FilterService } from "src/app/services/filter.service";
import { Filter } from "src/app/models/Filter";
import { ProductService } from "src/app/services/product.service";
import { PaginationInfo } from "src/app/models/PaginationInfo";

@Component({
  selector: "app-shop",
  templateUrl: "./shop.component.html",
  styleUrls: ["./shop.component.css"]
})
export class ShopComponent implements OnInit {
  allCategories;
  filterColor: Filter;
  filterSize: Filter;
  products: PaginationInfo;

  hide;
  pageNumber = 0;
  size = 9;

  //from children
  filterColorId;
  filterSizeId;
  categoryId;
  typeOfSorting;

  constructor(
    private categoriesService: CategoriesService,
    private filterService: FilterService,
    private productService: ProductService
  ) {}

  ngOnInit() {
    this.categoriesService
      .getCategoriesAndNumberOfProducts()
      .subscribe(categories => {
        this.allCategories = categories;
      });
    this.filterService.getFilterItemsByName("color").subscribe(items => {
      this.filterColor = items;
    });
    this.filterService.getFilterItemsByName("size").subscribe(items => {
      this.filterSize = items;
    });
    this.productService
      .getSortedProducts("default", null, null, this.pageNumber, this.size)
      .subscribe(products => {
        this.products = products;
        this.pageNumber++;
        if (this.products.totalNumberOfItems - this.pageNumber * this.size < 0 ||
          this.products.totalNumberOfItems - this.pageNumber * this.size == 0) {
          this.hide = true;
        }
        this.hide = false;
      });
  }

  receiveMessageFromFilterColor($event) {
    if ($event != this.filterColorId) {
    this.filterColorId = $event;
    }
  }
  receiveMessageFromFilterSize($event) {
    if ($event != this.filterSizeId) {
    this.filterSizeId = $event;
    }
  }
  receiveMessageFromCategories($event) {
    if ($event != this.categoryId) {
    this.categoryId = $event;
    }
  }
  recieveMessageFromShopProducts($event) {
    if ($event != this.typeOfSorting) {
    this.typeOfSorting = $event;
    }
  }
}
