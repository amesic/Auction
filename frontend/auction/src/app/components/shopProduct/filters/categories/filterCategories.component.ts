import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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

 faPlus = faPlus;
 faMinus = faMinus;
 faTimes = faTimes;
 sentCategoryId;
 message;

  @Output() messageEvent = new EventEmitter<string>();
  constructor(private router: Router) { }

  ngOnInit() {
  }
  sendSubcategoryId(subcategoryId, categoryId) {
    this.message = subcategoryId;
    this.sentCategoryId = categoryId;
    this.messageEvent.emit(this.message);
  }

}
