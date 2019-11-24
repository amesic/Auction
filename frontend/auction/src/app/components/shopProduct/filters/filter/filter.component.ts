import { Component, OnInit, Input } from '@angular/core';
import { Filter } from 'src/app/models/Filter';

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {
  @Input() title;
  @Input() filter: Filter;

  constructor() { }

  ngOnInit() {
  }

}
