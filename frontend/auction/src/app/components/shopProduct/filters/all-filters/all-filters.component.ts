import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-all-filters',
  templateUrl: './all-filters.component.html',
  styleUrls: ['./all-filters.component.css']
})
export class AllFiltersComponent implements OnInit {
  @Input() allCategories;
  @Input() filterColor;
  @Input() filterSize;

  constructor() { }

  ngOnInit() {
  }

}
