import { Component, OnInit, Input, ViewEncapsulation } from "@angular/core";
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

  public priceRange: PriceRange = new PriceRange(200, 800);

  sliderType = SliderType;

  view: any[] = [250, 150];
  constructor() {}

  send(valueLowerUpper) {
    this.lowerBound = valueLowerUpper.lower;
    this.upperBound = valueLowerUpper.upper;
    console.log("prestalo");
  }
  tu() {
    console.log("prestaloooo");

  }

  ngOnInit() {}
  // options for the chart
  timeline = true;
  colorScheme = {
    domain: ["#E4E5EC"]
  };
}
class PriceRange {
  constructor(
    public lower: number,
    public upper: number,
  ) {
  }
}
