import { Component, OnInit, Input } from "@angular/core";

@Component({
  selector: "app-set-products",
  templateUrl: "./set-products.component.html",
  styleUrls: ["./set-products.component.css"]
})
export class SetProductsComponent implements OnInit {
  @Input() products;
  @Input() title;
  @Input() className;
  constructor() {}

  ngOnInit() {}
}
