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
    this.router.navigate(['/shop/category/' + categoryId + "/" + subcategoryId]);
  }

}
