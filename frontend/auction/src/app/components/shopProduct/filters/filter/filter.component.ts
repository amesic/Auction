import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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
  message;

  @Output() messageEvent = new EventEmitter<string>();

  ngOnInit() {
  }
  sendFilterId(id) {
    this.message = id;
    this.messageEvent.emit(this.message);
  }

}
