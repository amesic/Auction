import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-my-account-products',
  templateUrl: './my-account-products.component.html',
  styleUrls: ['./my-account-products.component.css']
})
export class MyAccountProductsComponent implements OnInit {
  items;
  activeActiveProducts = true;

  pageNumber = 0;
  size = 5;

  constructor(private productService: ProductService, 
    private loginService: LoginService) { }

  ngOnInit() {
    this.productService.getActiveProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(active => {
      this.items = active;
    });
  }
  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    if (totalNumberOfItems - pageNumber * size < 0 || totalNumberOfItems - pageNumber * size == 0) {
      return true;
    }
    return false;
  }
  activeProducts() {
    this.pageNumber = 0;
    this.size = 5;
    this.activeActiveProducts = true;
    this.productService.getActiveProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(active => {
      this.items = active;
      console.log(active);
    });
  }
  soldProducts() {
    this.pageNumber = 0;
    this.size = 5;
    this.activeActiveProducts = false;
    this.productService.getSoldProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(sold => {
      this.items = sold;
      console.log(sold);
    });

  }

}
