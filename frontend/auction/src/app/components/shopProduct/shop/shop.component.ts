import { Component, OnInit } from "@angular/core";
import { CategoriesService } from "src/app/services/categories.service";
import { FilterService } from "src/app/services/filter.service";
import { Filter } from "src/app/models/Filter";
import { Product } from "src/app/models/Product";
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
      .getSortedProducts(null, this.pageNumber, this.size)
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
}
