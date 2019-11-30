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
  filterPrice;
  products;
  lowerBound;
  upperBound;
  priceProduct;

  hide;
  pageNumber = 0;
  size = 9;

  //from children
  filterColorId = null;
  filterSizeId = null;
  subcategoryId = null;
  listOfFiltersClicked = null;
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
    this.filterService.getFilterItemsByName("color", this.subcategoryId, this.listOfFiltersClicked).subscribe(items => {
      this.filterColor = items;
    });
    this.filterService.getFilterItemsByName("size", this.subcategoryId, this.listOfFiltersClicked).subscribe(items => {
      this.filterSize = items;
    });
    this.filterService.getFilterPriceInfo().subscribe(infoPriceProduct => {
      this.filterPrice = infoPriceProduct;
      this.lowerBound = this.filterPrice.priceNumber[0].name;
      this.upperBound = this.filterPrice.priceNumber[this.filterPrice.priceNumber.length - 1].name;
      this.priceProduct = this.filterPrice.priceNumber;
    })
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
      if (this.filterColorId != null) {
      this.listOfFiltersClicked = new Array();
      this.listOfFiltersClicked = [this.filterColorId];
      } else {
        this.listOfFiltersClicked = null;
      }
      this.filterService.getFilterItemsByName("size", this.subcategoryId, this.listOfFiltersClicked).subscribe(items => {
        this.filterSize = items;
      });
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.subcategoryId,
          this.pageNumber,
          this.size).subscribe(products => {
          if (products != null) {
          this.products = products;
          this.messageIfThereIsNoProducts = null;
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
          this.messageIfThereIsNoProducts = "Shop is empty!";
        }
        });
        window.scrollTo(0, 0);
    }
  }
  receiveMessageFromFilterSize($event) {
    if ($event != this.filterSizeId) {
      this.filterSizeId = $event;
      this.pageNumber = 0;
      this.size = 9;
      if (this.filterSizeId != null) {
        this.listOfFiltersClicked = new Array();
        this.listOfFiltersClicked = [this.filterSizeId];
        } else {
          this.listOfFiltersClicked = null;
        }
        this.filterService.getFilterItemsByName("color", this.subcategoryId, this.listOfFiltersClicked).subscribe(items => {
          this.filterColor = items;
        });
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.subcategoryId,
          this.pageNumber,
          this.size).subscribe(products => {
          if (products != null){
          this.products = products;
          this.messageIfThereIsNoProducts = null;
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
          this.messageIfThereIsNoProducts = "Shop is empty!";
        }
        });
        window.scrollTo(0, 0);
    }
  }
  receiveMessageFromCategories($event) {
    if ($event != this.subcategoryId) {
      this.subcategoryId = $event;
      this.pageNumber = 0;
      this.size = 9;
      this.filterService.getFilterItemsByName("color", this.subcategoryId, this.listOfFiltersClicked).subscribe(items => {
        this.filterColor = items;
      });
      this.filterService.getFilterItemsByName("size", this.subcategoryId, this.listOfFiltersClicked).subscribe(items => {
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
        } else {
          this.products = new PaginationInfo;
          this.products.items = [];
          this.hide = true;
          this.messageIfThereIsNoProducts = "Shop is empty!";
        }

      });
      window.scrollTo(0, 0);
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
          this.messageIfThereIsNoProducts = null;
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
          this.messageIfThereIsNoProducts = "Shop is empty!";
        }
      });
      window.scrollTo(0, 0);
    }
  }
}
