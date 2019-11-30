import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';
import { FilterComponent } from '../filter/filter.component'

@Component({
  selector: 'app-all-filters',
  templateUrl: './all-filters.component.html',
  styleUrls: ['./all-filters.component.css']
})
export class AllFiltersComponent implements OnInit {
  @Input() allCategories;
  @Input() filterColor;
  @Input() filterSize;
  @Input() filterPrice;
  @Input() lowerBound;
  @Input() upperBound;
  @Input() priceProduct;

  constructor() { }

  messageFilterColor:string;
  messageFilterSize:string;
  messageCategory:string;
  @Output() messageEventFilterColor = new EventEmitter<string>();
  @Output() messageEventFilterSize = new EventEmitter<string>();
  @Output() messageEventCategory = new EventEmitter<string>();

  receiveMessageFromFilterColor($event) {
    this.messageFilterColor = $event;
    this.messageEventFilterColor.emit(this.messageFilterColor);
  }
  receiveMessageFromFilterSize($event) {
    this.messageFilterSize = $event;
    this.messageEventFilterSize.emit(this.messageFilterSize);
  }
  receiveMessageFromCategories($event) {
    this.messageCategory = $event;
    this.messageEventCategory.emit(this.messageCategory);
  }

  ngOnInit() {
  }

}
