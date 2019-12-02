import { Component, OnInit, Input, ViewEncapsulation, Output, EventEmitter } from "@angular/core";
import { SliderType } from "igniteui-angular";

@Component({
  selector: "app-filter-price",
  templateUrl: "./filter-price.component.html",
  styleUrls: [
    "./filter-price.component.css",
    "../../../../../../node_modules/igniteui-angular/styles/igniteui-angular.css"
  ],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class FilterPriceComponent implements OnInit {
  @Input() title;
  @Input() filter;
  @Input() lowerBound;
  @Input() upperBound;
  @Input() priceProduct;

  sliderType = SliderType;

  view: any[] = [250, 150];
  constructor() {}

  change(valueLowerUpper) {
    this.lowerBound = valueLowerUpper.value.lower;
    this.upperBound = valueLowerUpper.value.upper;
  }
  @Output() messageEvent = new EventEmitter<string>();
  message;

  sendInfoAboutPrice() {
    this.message = {
      lowerBound: this.lowerBound,
      upperBound: this.upperBound
    };
    this.messageEvent.emit(this.message);
  }
  ngOnInit() {}
  // options for the chart
  timeline = true;
  colorScheme = {
    domain: ["#E4E5EC"]
  };
}

