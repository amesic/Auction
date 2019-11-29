import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { faTh } from "@fortawesome/free-solid-svg-icons";
import { faList } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { ProductService } from "src/app/services/product.service";

@Component({
  selector: "app-shop-products",
  templateUrl: "./shop-products.component.html",
  styleUrls: ["./shop-products.component.css"]
})
export class ShopProductsComponent implements OnInit {
  @Input() products;
  @Input() hide;
  @Input() messageIfThereIsNoProducts;
  @Input() filterColorId;
  @Input() filterSizeId;
  @Input() subcategoryId;
  @Input() pageNumber;
  @Input() size;

  faTh = faTh;
  faList = faList;
  faChevronDown = faChevronDown;

  activeGrid = true;
  activeList = false;
  clickOnSortList = false;
  className = "grid";

  typeOfSorting = "default";
  sortingType = "Default Sorting";
  @Output() messageEventSorting = new EventEmitter<string>();

  constructor(private productService: ProductService) {}

  ngOnInit() {}

  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    if (totalNumberOfItems - pageNumber * size < 0 ||
      totalNumberOfItems - pageNumber * size == 0) {
      return true;
    }
    return false;
  }

  onClick() {
    this.productService
      .getSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId, this.subcategoryId, this.pageNumber, this.size)
      .subscribe(products => {
        this.products.items = this.products.items.concat(products.items);
        if (this.checkIfThereIsNoItemsLeft(
            this.pageNumber + 1,
            this.size,
            products.totalNumberOfItems)) {
          this.hide = true;
        } else {
          this.hide = false;
          this.pageNumber++;
        }
      })
  }

  grid() {
    this.activeGrid = true;
    this.activeList = false;
    this.className = "grid";
    this.pageNumber = 0;
    this.size = 9;
    this.productService.getSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId, this.subcategoryId, this.pageNumber, this.size).subscribe(products => {
      this.products = products;
      if (this.checkIfThereIsNoItemsLeft(this.pageNumber + 1, this.size, products.totalNumberOfItems)) {
        this.hide = true;
      } else {
        this.hide = false;
        this.pageNumber++;
      }
    });
  }
  list() {
    this.activeGrid = false;
    this.activeList = true;
    this.className = "list";
    this.pageNumber = 0;
    this.size = 9;
    this.productService.getSortedProducts(this.typeOfSorting, this.filterColorId, this.filterSizeId, this.subcategoryId, this.pageNumber, this.size).subscribe(products => {
      this.products = products;
      if (this.checkIfThereIsNoItemsLeft(this.pageNumber + 1, this.size, products.totalNumberOfItems)) {
        this.hide = true;
      } else {
        this.hide = false;
        this.pageNumber++;
      }
    });
  }
  sendTypeOfSorting(type, sortingType) {
    this.typeOfSorting = type;
    this.sortingType = sortingType;
    this.messageEventSorting.emit(this.typeOfSorting);
  }
}
