import { Component, OnInit } from "@angular/core";
import { Input } from "@angular/core";
import { Router } from '@angular/router';

@Component({
  selector: "app-product",
  templateUrl: "./product.component.html",
  styleUrls: ["./product.component.css"]
})
export class ProductComponent implements OnInit {
  @Input() title;
  @Input() startPrice;
  @Input() firstImage;
  @Input() id;

  constructor(private router: Router) {}

  ngOnInit() {}

  sendInfoOfProduct(){
    this.router.navigate(['/shop/product/' + this.title + "/" + this.id]);
  }
}
