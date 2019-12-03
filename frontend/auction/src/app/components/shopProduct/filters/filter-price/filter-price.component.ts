import { Component, OnInit, Input, Output, EventEmitter, OnChanges, ViewEncapsulation } from "@angular/core";
import { Options } from 'ng5-slider';

@Component({
  selector: "app-filter-price",
  templateUrl: "./filter-price.component.html",
  styleUrls: ["./filter-price.component.css",],
  encapsulation: ViewEncapsulation.None
})
export class FilterPriceComponent implements OnChanges {
  @Input() title;
  @Input() filter;
  @Input() lowerBound;
  @Input() upperBound;
  @Input() priceProduct;

  view: any[] = [250, 150];
  constructor() {}

  value: number;
  highValue: number;
  options: Options;

  lower;
  upper;
  
  ngOnChanges() {
    if (this.lowerBound != null && this.upperBound != null) {
    this.value = this.lowerBound;
    this.highValue = this.upperBound;
    this.lower = this.lowerBound;
    this.upper = this.upperBound;
    this.options = {
      floor: this.lowerBound,
      ceil: this.upperBound,
      step: 0.1
    };
  }
  }

  @Output() messageEvent = new EventEmitter<string>();
  message;

  sendInfoAboutPrice(event) {
    this.message = {
      lowerBound: event.value,
      upperBound: event.highValue
    };
    this.lower = event.value;
    this.upper = event.highValue;
    this.messageEvent.emit(this.message);
  }

  // options for the chart
  timeline = true;
  colorScheme = {
    domain: ["#E4E5EC"]
  };
}

