import { Component, OnInit, ViewChild } from "@angular/core";
import { CategoriesService } from "src/app/services/categories.service";
import { FilterService } from "src/app/services/filter.service";
import { Filter } from "src/app/models/Filter";
import { ProductService } from "src/app/services/product.service";
import { PaginationInfo } from "src/app/models/PaginationInfo";
import { MessageService } from "src/app/services/message.service";
import { Router, ActivatedRoute } from '@angular/router';

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
  filterClicked = null;
  subcategoryId = null;
  listOfFiltersClicked = new Array();
  typeOfSorting = "default";
  messageIfThereIsNoProducts = null;
  lowerBoundPrice = null;
  upperBoundPrice = null;

  valueFromUserSearch: any = null;

  constructor(
    private categoriesService: CategoriesService,
    private filterService: FilterService,
    private productService: ProductService,
    private messageService: MessageService,
    private router : ActivatedRoute) {}

  ngOnInit() {
    this.messageService.currentMessageSearch.subscribe(searchValue => {
     this.recieveMessageFromSearchInput(searchValue);
      });
      this.messageService.currentMessageSubcategory.subscribe(subcategoryValue => {
        this.receiveMessageFromCategories(subcategoryValue);
      });
    this.categoriesService.getCategoriesAndNumberOfProducts().subscribe(categories => {
        this.allCategories = categories;
      });
    this.filterService.getFilterItemsByName("color", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
        this.filterColor = items;
      });
    this.filterService.getFilterItemsByName("size", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
        this.filterSize = items;
      });
    this.filterService.getFilterPriceInfo(this.subcategoryId, null, this.valueFromUserSearch).subscribe(infoPriceProduct => {
      this.filterPrice = infoPriceProduct;
      
      this.lowerBound = this.filterPrice.priceNumber[0].name;
      this.upperBound = this.filterPrice.priceNumber[
      this.filterPrice.priceNumber.length - 1].name;
      this.priceProduct = this.filterPrice.priceNumber;
    });
    this.productService.getSortedProducts(
        this.typeOfSorting,
        this.filterColorId,
        this.filterSizeId,
        this.lowerBoundPrice,
        this.upperBoundPrice,
        this.subcategoryId,
        this.valueFromUserSearch,
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
      this.listOfFiltersClicked = this.listOfFiltersClicked.filter(item => item !== this.filterColorId);
      this.filterColorId = $event;
      this.pageNumber = 0;
      this.size = 9;

      if (this.filterColorId != null) {
        this.listOfFiltersClicked.push(this.filterColorId);
      } 
      if (this.filterColorId == null) {
        this.lowerBoundPrice = null;
        this.upperBoundPrice = null;
      }
    this.filterService.getFilterItemsByName("size", this.subcategoryId, this.filterColorId, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
        this.filterSize = items;
      });
    this.productService.getSortedProducts(
        this.typeOfSorting,
        this.filterColorId,
        this.filterSizeId,
        this.lowerBoundPrice,
        this.upperBoundPrice,
        this.subcategoryId,
        this.valueFromUserSearch,
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
          this.products = new PaginationInfo();
          this.products.items = [];
          this.hide = true;
          this.messageIfThereIsNoProducts = "Shop is empty!";
        }
      });
      this.filterService.getFilterPriceInfo(this.subcategoryId, this.listOfFiltersClicked, this.valueFromUserSearch)
      .subscribe(infoPriceProduct => {
        this.filterPrice = infoPriceProduct;
        if (this.filterPrice.priceNumber.length != 0) {
        this.lowerBound = this.filterPrice.priceNumber[0].name;
        this.upperBound = this.filterPrice.priceNumber[this.filterPrice.priceNumber.length - 1].name;
        } else {
        this.lowerBound = null;
        this.upperBound = null;
        }
        this.priceProduct = this.filterPrice.priceNumber;
      });
      
      window.scrollTo(0, 0);
    }
  }
  receiveMessageFromFilterSize($event) {
    if ($event != this.filterSizeId) {
      this.listOfFiltersClicked = this.listOfFiltersClicked.filter(item => item !== this.filterSizeId);
      this.filterSizeId = $event;
      this.pageNumber = 0;
      this.size = 9;

      if (this.filterSizeId != null) {
        this.listOfFiltersClicked.push(this.filterSizeId);
      }
      if (this.filterSizeId == null) {
        this.lowerBoundPrice = null;
        this.upperBoundPrice = null;
      }
      this.filterService.getFilterPriceInfo(this.subcategoryId, this.listOfFiltersClicked, this.valueFromUserSearch)
      .subscribe(infoPriceProduct => {
        this.filterPrice = infoPriceProduct;
        if (this.filterPrice.priceNumber.length != 0) {
        this.lowerBound = this.filterPrice.priceNumber[0].name;
        this.upperBound = this.filterPrice.priceNumber[this.filterPrice.priceNumber.length - 1].name;
        } else {
        this.lowerBound = null;
        this.upperBound = null;
        }
        this.priceProduct = this.filterPrice.priceNumber;
      });
      this.filterService.getFilterItemsByName("color", this.subcategoryId, this.filterSizeId, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
          this.filterColor = items;
        });
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.lowerBoundPrice,
          this.upperBoundPrice,
          this.subcategoryId,
          this.valueFromUserSearch,
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
            this.products = new PaginationInfo();
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
      if (this.subcategoryId == null) {
        this.lowerBoundPrice = null;
        this.upperBoundPrice = null;
      }
      this.filterService.getFilterItemsByName("color", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
          this.filterColor = items;
        });
      this.filterService.getFilterItemsByName("size", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
          this.filterSize = items;
        });
      this.filterService.getFilterPriceInfo(this.subcategoryId, this.listOfFiltersClicked, this.valueFromUserSearch)
      .subscribe(infoPriceProduct => {
        this.filterPrice = infoPriceProduct;
        if (this.filterPrice.priceNumber.length != 0) {
        this.lowerBound = this.filterPrice.priceNumber[0].name;
        this.upperBound = this.filterPrice.priceNumber[this.filterPrice.priceNumber.length - 1].name;
        } else {
        this.lowerBound = null;
        this.upperBound = null;
        }
        this.priceProduct = this.filterPrice.priceNumber;
      });
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.lowerBoundPrice,
          this.upperBoundPrice,
          this.subcategoryId,
          this.valueFromUserSearch,
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
            this.products = new PaginationInfo();
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
          this.lowerBoundPrice,
          this.upperBoundPrice,
          this.subcategoryId,
          this.valueFromUserSearch,
          this.pageNumber,
          this.size).subscribe(products => {
          if (products != null) {
            this.products = products;
            this.messageIfThereIsNoProducts = null;
            this.pageNumber++;
            if (this.products.totalNumberOfItems - this.pageNumber * this.size < 0 ||
              this.products.totalNumberOfItems - this.pageNumber * this.size ==0) {
              this.hide = true;
            } else {
              this.hide = false;
            }
          } else {
            this.products = new PaginationInfo();
            this.products.items = [];
            this.hide = true;
            this.messageIfThereIsNoProducts = "Shop is empty!";
          }
        });
      window.scrollTo(0, 0);
    }
  }
  recieveMessageFromSearchInput(searchValue) {
    this.valueFromUserSearch = searchValue;
    this.pageNumber = 0;
    this.size = 9;
    if (this.valueFromUserSearch == "") {
      this.lowerBoundPrice = null;
      this.upperBoundPrice = null;
    }
    this.filterService.getFilterItemsByName("color", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
      this.filterColor = items;
    });
    this.filterService.getFilterItemsByName("size", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
      this.filterSize = items;
    });
    this.filterService.getFilterPriceInfo(this.subcategoryId, this.listOfFiltersClicked, this.valueFromUserSearch)
      .subscribe(infoPriceProduct => {
        this.filterPrice = infoPriceProduct;
        if (this.filterPrice.priceNumber.length != 0) {
        this.lowerBound = this.filterPrice.priceNumber[0].name;
        this.upperBound = this.filterPrice.priceNumber[this.filterPrice.priceNumber.length - 1].name;
        } else {
        this.lowerBound = null;
        this.upperBound = null;
        }
        this.priceProduct = this.filterPrice.priceNumber;
      });
    this.productService.getSortedProducts(
        this.typeOfSorting,
        this.filterColorId,
        this.filterSizeId,
        this.lowerBoundPrice,
        this.upperBoundPrice,
        this.subcategoryId,
        this.valueFromUserSearch,
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
          this.products = new PaginationInfo();
          this.products.items = [];
          this.hide = true;
          this.messageIfThereIsNoProducts = "Shop is empty!";
        }
      });
  }
  receiveMessageFromFilterPrice($event) {
    if ($event.lowerBound != this.lowerBoundPrice || $event.upperBound != this.upperBoundPrice) {
      this.lowerBoundPrice = $event.lowerBound;
      this.upperBoundPrice = $event.upperBound;
      this.pageNumber = 0;
      this.size = 9;
      this.filterService.getFilterItemsByName("color", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
        this.filterColor = items;
      });
      this.filterService.getFilterItemsByName("size", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
        this.filterSize = items;
      });
      this.productService.getSortedProducts(
          this.typeOfSorting,
          this.filterColorId,
          this.filterSizeId,
          this.lowerBoundPrice,
          this.upperBoundPrice,
          this.subcategoryId,
          this.valueFromUserSearch,
          this.pageNumber,
          this.size).subscribe(products => {
          if (products != null) {
            this.products = products;
            this.messageIfThereIsNoProducts = null;
            this.pageNumber++;
            if (this.products.totalNumberOfItems - this.pageNumber * this.size < 0 ||
              this.products.totalNumberOfItems - this.pageNumber * this.size ==0) {
              this.hide = true;
            } else {
              this.hide = false;
            }
          } else {
            this.products = new PaginationInfo();
            this.products.items = [];
            this.hide = true;
            this.messageIfThereIsNoProducts = "Shop is empty!";
          }
        });
      window.scrollTo(0, 0);
    }
    
  }
}
