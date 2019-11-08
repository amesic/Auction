import { Component, OnInit } from "@angular/core";
import { CategoriesService } from "../../services/categories.service";
import { Category } from "../../models/Category";

@Component({
  selector: "app-all-categories",
  templateUrl: "./all-categories.component.html",
  styleUrls: ["./all-categories.component.css"]
})
export class AllCategoriesComponent implements OnInit {
  constructor(private categoriesService: CategoriesService) {}
  categories: Category[] = [];

  ngOnInit() {
    //null like maxNumberOfCategories bc I need all of them
    this.categoriesService.getAllCategories(null).subscribe(allCategories => {
      this.categories = allCategories;
    });
  }
}
