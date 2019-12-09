import { Component, OnInit} from "@angular/core";
import { CategoriesService } from "src/app/services/categories.service";
import { FilterService } from "src/app/services/filter.service";
import { Filter } from "src/app/models/Filter";
import { ProductService } from "src/app/services/product.service";
import { PaginationInfo } from "src/app/models/PaginationInfo";
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: "app-shop",
  templateUrl: "./shop.component.html",
  styleUrls: ["./shop.component.css"]
})
export class ShopComponent implements OnInit{
  allCategories;
  filterColor: Filter;
  filterSize: Filter;
  filterPrice;
  products;
  lowerBound;
  upperBound;
  priceProduct;
  activeSubcategoryId;
  activeCategoryId;

  hide;
  pageNumber;
  size;

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

  valueFromUserSearch = null;

  callgetFilterPriceInfo(subcategoryId, listOfFiltersClicked, valueFromUserSearch) {
    this.filterService.getFilterPriceInfo(subcategoryId, listOfFiltersClicked, valueFromUserSearch)
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
  }
  callgetSortedProducts(typeOfSorting, filterColorId, filterSizeId, lowerBoundPrice, upperBoundPrice, 
    subcategoryId, valueFromUserSearch, pageNumber, size) {
      this.productService.getSortedProducts(
        typeOfSorting, filterColorId, filterSizeId, lowerBoundPrice, upperBoundPrice,
        subcategoryId, valueFromUserSearch, pageNumber, size).subscribe(products => {
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

  constructor(
    private categoriesService: CategoriesService,
    private filterService: FilterService,
    private productService: ProductService,
    private activatedRoute : ActivatedRoute,
    private router: Router) {}

  ngOnInit() {
    this.activatedRoute.params.subscribe(routeParams => {
      if (routeParams.searchValue == undefined) {
        this.valueFromUserSearch = null;
      } else {
        this.valueFromUserSearch = routeParams.searchValue;
      }
      if (routeParams.subcategoryId == undefined) {
        this.subcategoryId = null;
      } else {
        this.subcategoryId = routeParams.subcategoryId;
        this.activeSubcategoryId = this.subcategoryId;
        this.activeCategoryId = routeParams.categoryId;
      }
      this.pageNumber = 0;
      this.size = 9;
    this.categoriesService.getCategoriesAndNumberOfProducts().subscribe(categories => {
        this.allCategories = categories;
      });
    this.filterService.getFilterItemsByName("color", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
        this.filterColor = items;
      });
    this.filterService.getFilterItemsByName("size", this.subcategoryId, null, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
        this.filterSize = items;
      });
    this.callgetFilterPriceInfo(this.subcategoryId, null, this.valueFromUserSearch);
    this.callgetSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId,this.lowerBoundPrice,
        this.upperBoundPrice, this.subcategoryId, this.valueFromUserSearch, this.pageNumber, this.size);
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
    this.callgetSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId, this.lowerBoundPrice,
        this.upperBoundPrice, this.subcategoryId, this.valueFromUserSearch, this.pageNumber, this.size);
      this.callgetFilterPriceInfo(this.subcategoryId, this.listOfFiltersClicked, this.valueFromUserSearch);
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
      this.filterService.getFilterItemsByName("color", this.subcategoryId, this.filterSizeId, this.valueFromUserSearch, this.lowerBoundPrice, this.upperBoundPrice).subscribe(items => {
          this.filterColor = items;
        });
      this.callgetSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId, this.lowerBoundPrice,
        this.upperBoundPrice, this.subcategoryId, this.valueFromUserSearch, this.pageNumber, this.size);
      this.callgetFilterPriceInfo(this.subcategoryId, this.listOfFiltersClicked, this.valueFromUserSearch);
      window.scrollTo(0, 0);
    }
  }
  recieveMessageFromShopProducts($event) {
    if ($event != this.typeOfSorting) {
      this.typeOfSorting = $event;
      this.pageNumber = 0;
      this.size = 9;
      this.callgetSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId, this.lowerBoundPrice,
        this.upperBoundPrice, this.subcategoryId, this.valueFromUserSearch, this.pageNumber, this.size);
      window.scrollTo(0, 0);
    }
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
      this.callgetSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId, this.lowerBoundPrice,
        this.upperBoundPrice, this.subcategoryId, this.valueFromUserSearch, this.pageNumber, this.size);
      window.scrollTo(0, 0);
    }
  }
}
