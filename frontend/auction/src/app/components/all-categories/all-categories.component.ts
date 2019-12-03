import { Component, OnInit } from "@angular/core";
import { CategoriesService } from "../../services/categories.service";
import { Category } from "../../models/Category";
import { MessageService } from 'src/app/services/message.service';
import { Router } from '@angular/router';

@Component({
  selector: "app-all-categories",
  templateUrl: "./all-categories.component.html",
  styleUrls: ["./all-categories.component.css"]
})
export class AllCategoriesComponent implements OnInit {
  constructor(private categoriesService: CategoriesService, private router: Router) {}
  categories: Category[] = [];

  ngOnInit() {
    //null like maxNumberOfCategories because I need all of them
    this.categoriesService.getAllCategories(null).subscribe(allCategories => {
      this.categories = allCategories;
    });
  }
  sendInfoAboutSubcategory(categoryId, subcategoryId) {
    this.router.navigate(['/shop/category/' + categoryId + "/" + subcategoryId])
  } 
}
