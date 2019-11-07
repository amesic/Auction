import { Component, OnInit } from "@angular/core";
import { Product } from "../../models/Product";
import { ProductService } from "../../services/product.service";
import { Input } from "@angular/core";

@Component({
  selector: "app-product",
  templateUrl: "./product.component.html",
  styleUrls: ["./product.component.css"]
})
export class ProductComponent implements OnInit {
  @Input() title;
  @Input() startPrice;
  @Input() description;
  @Input() imageUrls: Array<String>;
  @Input() firstImage;

  constructor() {}

  ngOnInit() {}
}
