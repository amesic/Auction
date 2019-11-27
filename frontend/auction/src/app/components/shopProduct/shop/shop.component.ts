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
  products;

  hide;
  pageNumber = 0;
  size = 9;

  //from children
  filterColorId = null;
  filterSizeId = null;
  subcategoryId = null;
  typeOfSorting = "default";
  messageIfThereIsNoProducts = null;

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
    this.filterService.getFilterItemsByName("color", this.subcategoryId).subscribe(items => {
      this.filterColor = items;
    });
    this.filterService.getFilterItemsByName("size", this.subcategoryId).subscribe(items => {
      this.filterSize = items;
    });
    this.productService.getSortedProducts(
        this.typeOfSorting,
        this.filterColorId,
        this.filterSizeId,
        this.subcategoryId,
        this.pageNumber,
        this.size).subscribe(products => {
        this.products = products;
        this.pageNumber++;
        if (this.products.totalNumberOfItems - this.pageNumber * this.size < 0 ||
          this.products.totalNumberOfItems - this.pageNumber * this.size == 0) {
          this.hide = true;
        } else {
        this.hide = false;
        }
      });
  }

  receiveMessageFromFilterColor($event) {
    if ($event != this.filterColorId) {
      this.filterColorId = $event;
      this.pageNumber = 0;
      this.size = 9;
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.subcategoryId,
          this.pageNumber,
          this.size).subscribe(products => {
          if (products != null) {
          this.products = products;
          this.pageNumber++;
          if (this.products.totalNumberOfItems - this.pageNumber * this.size < 0 ||
            this.products.totalNumberOfItems - this.pageNumber * this.size == 0) {
            this.hide = true;
          } else {
          this.hide = false;
          }
        } else {
          this.products = new PaginationInfo;
          this.products.items = [];
          this.hide = true;
          this.messageIfThereIsNoProducts = "For this filter there is no items yet!";
        }
        });
    }
  }
  receiveMessageFromFilterSize($event) {
    if ($event != this.filterSizeId) {
      this.filterSizeId = $event;
      this.pageNumber = 0;
      this.size = 9;
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.subcategoryId,
          this.pageNumber,
          this.size).subscribe(products => {
          if (products != null){
          this.products = products;
          this.pageNumber++;
          if (this.products.totalNumberOfItems - this.pageNumber * this.size <0 ||
            this.products.totalNumberOfItems - this.pageNumber * this.size == 0) {
            this.hide = true;
          } else {
          this.hide = false;
          }
        } else {
          this.products = new PaginationInfo;
          this.products.items = [];
          this.hide = true;
          this.messageIfThereIsNoProducts = "For this filter there is no items yet!";
        }
        });
    }
  }
  receiveMessageFromCategories($event) {
    if ($event != this.subcategoryId) {
      this.subcategoryId = $event;
      this.pageNumber = 0;
      this.size = 9;
      this.filterService.getFilterItemsByName("color", this.subcategoryId).subscribe(items => {
        this.filterColor = items;
      });
      this.filterService.getFilterItemsByName("size", this.subcategoryId).subscribe(items => {
        this.filterSize = items;
      });
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.subcategoryId,
          this.pageNumber,
          this.size).subscribe(products => {
          if(products != null) {
          this.products = products;
          this.messageIfThereIsNoProducts = null;
          this.pageNumber++;
          if (this.products.totalNumberOfItems - this.pageNumber * this.size < 0 ||
            this.products.totalNumberOfItems - this.pageNumber * this.size == 0) {
            this.hide = true;
          } else {
          this.hide = false;
          }
        }
        else {
          this.products = new PaginationInfo;
          this.products.items = [];
          this.hide = true;
          this.messageIfThereIsNoProducts = "This category has no items yet!";
        }

      });
    }
  }
  recieveMessageFromShopProducts($event) {
    if ($event != this.typeOfSorting) {
      this.typeOfSorting = $event;
      this.pageNumber = 0;
      this.size = 9;
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.subcategoryId,
          this.pageNumber,
          this.size).subscribe(products => {
          if(products != null) {
          this.products = products;
          this.pageNumber++;
          if (this.products.totalNumberOfItems - this.pageNumber * this.size < 0 ||
            this.products.totalNumberOfItems - this.pageNumber * this.size == 0) {
            this.hide = true;
          } else {
            this.hide = false;
          }
        } else {
          this.products = new PaginationInfo;
          this.products.items = [];
          this.hide = true;
          this.messageIfThereIsNoProducts = "There are no items for sorting yet!";
        }
      });
    }
  }
}
