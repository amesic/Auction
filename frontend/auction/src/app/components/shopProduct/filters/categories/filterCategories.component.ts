import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { faMinus } from "@fortawesome/free-solid-svg-icons";
import { Router } from '@angular/router';

@Component({
  selector: 'app-filter-categories',
  templateUrl: './filterCategories.component.html',
  styleUrls: ['./filterCategories.component.css']
})
export class FilterCategoriesComponent implements OnInit {
 @Input() allCategories;

 faPlus = faPlus;
 faMinus = faMinus;
 message;

  @Output() messageEvent = new EventEmitter<string>();
  constructor(private router: Router) { }

  ngOnInit() {
  }
  sendSubcategoryId(id) {
    this.message = id;
    this.messageEvent.emit(this.message);
  }

}
