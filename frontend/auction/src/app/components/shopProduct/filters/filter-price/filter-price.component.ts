import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { SliderType } from "igniteui-angular";

@Component({
  selector: 'app-filter-price',
  templateUrl: './filter-price.component.html',
  styleUrls: ['./filter-price.component.css', 
  '../../../../../../node_modules/igniteui-angular/styles/igniteui-angular.css'],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class FilterPriceComponent implements OnInit {
  @Input() title;
  @Input() filter;
  @Input() lowerBound;
  @Input() upperBound;

  sliderType = SliderType;
  constructor() { }
  send(valueLowerUpper) {
    this.lowerBound = valueLowerUpper.lower;
    this.upperBound = valueLowerUpper.upper;
  }

  ngOnInit() {}


}
