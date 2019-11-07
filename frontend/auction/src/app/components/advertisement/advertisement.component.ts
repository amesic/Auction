import { Component, OnInit, Input } from "@angular/core";
import { Product } from "../../models/Product";
import { faChevronRight } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: "app-advertisement",
  templateUrl: "./advertisement.component.html",
  styleUrls: ["./advertisement.component.css"]
})
export class AdvertisementComponent implements OnInit {
  @Input() product: Product;
  faChevronRight = faChevronRight;

  constructor() {}

  ngOnInit() {}
}
