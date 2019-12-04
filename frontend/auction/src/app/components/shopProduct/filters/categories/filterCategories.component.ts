import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { faMinus } from "@fortawesome/free-solid-svg-icons";
import { Router } from '@angular/router';
import { faTimes } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-filter-categories',
  templateUrl: './filterCategories.component.html',
  styleUrls: ['./filterCategories.component.css']
})
export class FilterCategoriesComponent implements OnInit {
 @Input() allCategories;
 @Input() activeSubcategoryId;
 @Input() activeCategoryId;

 faPlus = faPlus;
 faMinus = faMinus;
 faTimes = faTimes;

  constructor(private router: Router) { }

  ngOnInit() {}

  sendSubcategoryId(subcategoryId, categoryId) {
    if (subcategoryId == "" && categoryId == "") {
      if (this.router.url.substr(0, 13) == "/shop/search/") {
        this.router.navigate([this.router.url.substr(0, this.router.url.indexOf("category"))]);
      } else {
        this.router.navigate(['/shop']);
      }
    } else {
    if (this.router.url == "/shop" || this.router.url.substr(0, 15) == "/shop/category/") {
      this.router.navigate(['/shop/category/' + categoryId + "/" + subcategoryId]);
    }
    if (this.router.url.substr(0, 13) == "/shop/search/") {
      if (this.router.url.indexOf("category") == -1) {
      this.router.navigate([this.router.url + '/category/' + categoryId + "/" + subcategoryId]);
      } else {
        this.router.navigate([this.router.url.substr(0, this.router.url.indexOf("category")) + '/category/' + categoryId + "/" + subcategoryId]);
      }
    }
  }
}

}
