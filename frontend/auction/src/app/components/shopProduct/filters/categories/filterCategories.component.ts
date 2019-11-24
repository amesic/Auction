import { Component, OnInit, Input } from '@angular/core';
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { faMinus } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-filter-categories',
  templateUrl: './filterCategories.component.html',
  styleUrls: ['./filterCategories.component.css']
})
export class FilterCategoriesComponent implements OnInit {
 @Input() allCategories;

 faPlus = faPlus;
 faMinus = faMinus;
  constructor() { }

  ngOnInit() {
  }

}
