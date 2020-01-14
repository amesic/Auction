import { Component, OnInit, Input, OnChanges } from '@angular/core';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.css']
})
export class SpinnerComponent implements OnInit, OnChanges{
  @Input() componentUsingSpinner;

  payment = false;
  required = false;
  bids = false;

  constructor() { }

  ngOnInit() {
  }
  ngOnChanges() {
    if (this.componentUsingSpinner == "payment") {
      this.payment = true;
      this.required = false;
      this.bids = false;
    } else if (this.componentUsingSpinner == "required"){
      this.payment = false;
      this.bids = false;
      this.required = true;
    } else if (this.componentUsingSpinner == "bids") {
      this.bids = true
      this.payment = false;
      this.required = false;
    }
  }

}
