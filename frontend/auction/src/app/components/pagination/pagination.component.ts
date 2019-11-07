import { Component, OnInit } from '@angular/core';
import { Product } from "../../models/Product";
import { ProductService } from "../../services/product.service";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {
  page;
  size = 8;
  productsNew: Array<Product>;
  disabled = false;

  page1;
  productsLast: Array<Product>;
  disabled1 = false;

  constructor(private productService: ProductService) { }

  ngOnInit() {
    this.page = 0;
    this.page1 = 0;
    this.productService.getNewArr(this.page, this.size).subscribe(newArr => {
      this.productsNew = newArr;
    });
    this.productService.getLastChan(this.page1, this.size).subscribe(lastChan => {
      this.productsLast = lastChan;
    });
  }
  onClick(){
    this.page += 1;
    this.productService.getNewArr(this.page, this.size).subscribe(newArr => {
      if(newArr != null) {
      this.productsNew = this.productsNew.concat(newArr);
      }
      else {
      this.disabled = true;
      }
    })
  }
  onClick1(){
    this.page1 += 1;
    this.productService.getLastChan(this.page1, this.size).subscribe(lastChan => {
      if(lastChan != null) {
      this.productsLast = this.productsLast.concat(lastChan);
      }
      else {
      this.disabled1 = true;
      }
    })
  }


}
