import { Component, OnInit } from "@angular/core";
import { CategoriesService } from "../../../../services/categories.service";
import { Category } from "../../../../models/Category";
import { MessageService } from 'src/app/services/message.service';

@Component({
  selector: "app-categories",
  templateUrl: "./categories.component.html",
  styleUrls: ["./categories.component.css"]
})
export class CategoriesComponent implements OnInit {
  constructor(private categoriesService: CategoriesService, public messageService: MessageService) {}
  categories: Array<Category>;

  ngOnInit() {
    this.categoriesService.getAllCategories(9).subscribe(allCategories => {
      this.categories = allCategories;
    });
  }
  sendInfoAboutSubcategory(subcategoryId) {
    this.messageService.changeMessageSubcategory(subcategoryId);
  }  

}
