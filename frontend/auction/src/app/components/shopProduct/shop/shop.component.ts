import { Component, OnInit } from "@angular/core";
import { CategoriesService } from "src/app/services/categories.service";
import { FilterService } from "src/app/services/filter.service";
import { Filter } from 'src/app/models/Filter';
import { Product } from 'src/app/models/Product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: "app-shop",
  templateUrl: "./shop.component.html",
  styleUrls: ["./shop.component.css"]
})
export class ShopComponent implements OnInit {
  allCategories;
  filterColor: Filter;
  filterSize: Filter;
  products: Product[];

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
    this.productService.getFeatureCollection().subscribe(products => {
      this.products = products;
    })
  }
}
