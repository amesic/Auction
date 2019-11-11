import { Component, OnInit, Input } from "@angular/core";
import { ProductService } from 'src/app/services/product.service';
import {Router} from '@angular/router';

@Component({
  selector: "app-set-products",
  templateUrl: "./set-products.component.html",
  styleUrls: ["./set-products.component.css"]
})
export class SetProductsComponent implements OnInit {
  @Input() products;
  @Input() title;
  @Input() className;
  constructor(private productService: ProductService, private router: Router) {}

  ngOnInit() {}
  sendInfoOfProduct(product){
    this.router.navigate(['/shop/product/' + product.title + "/" + product.id + "/" + product.subcategory.id]);
  }
}
