import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Filter } from 'src/app/models/Filter';
import { faTimes } from "@fortawesome/free-solid-svg-icons";

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

  faTimes = faTimes;
  showIcon = false;

  @Output() messageEvent = new EventEmitter<string>();

  ngOnInit() {
  }
  sendFilterId(id) {
    if (id == null) {
      this.showIcon = false;
    }
    else {
      this.showIcon = true;
    }
    this.message = id;
    this.messageEvent.emit(this.message);
  }

}
