import { Component, OnInit } from "@angular/core";
import { Input } from "@angular/core";

@Component({
  selector: "app-product",
  templateUrl: "./product.component.html",
  styleUrls: ["./product.component.css"]
})
export class ProductComponent implements OnInit {
  @Input() title;
  @Input() startPrice;
  @Input() firstImage;

  constructor() {}

  ngOnInit() {}
}
