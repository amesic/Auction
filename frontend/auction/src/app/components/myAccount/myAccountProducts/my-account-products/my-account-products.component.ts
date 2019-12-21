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
  hide = false;

  constructor(private productService: ProductService, 
    private loginService: LoginService) { }

  ngOnInit() {
    this.productService.getActiveProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(active => {
      this.items = active.items;
      this.pageNumber = this.pageNumber + 1;
      this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, active.totalNumberOfItems);
    });
  }
  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    return totalNumberOfItems - pageNumber * size <= 0;
  }
  activeProducts() {
    this.pageNumber = 0;
    this.activeActiveProducts = true;
    this.productService.getActiveProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(active => {
      this.items = active.items;
      this.pageNumber = this.pageNumber + 1;
      this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, active.totalNumberOfItems);
    });
  }
  soldProducts() {
    this.pageNumber = 0;
    this.activeActiveProducts = false;
    this.productService.getSoldProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(sold => {
      this.items = sold.items;
      this.pageNumber = this.pageNumber + 1;
      this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, sold.totalNumberOfItems);
    });
  }
  loadMore() {
    if (this.activeActiveProducts) {
      this.productService.getActiveProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(active => {
        this.items = this.items.concat(active.items);
        this.pageNumber = this.pageNumber + 1;
        this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, active.totalNumberOfItems);
      });
    } else {
      this.productService.getSoldProductsByUser(this.loginService.getUserEmail(), this.pageNumber, this.size).subscribe(sold => {
        this.items = this.items.concat(sold.items);
        this.pageNumber = this.pageNumber + 1;
        this.hide = this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, sold.totalNumberOfItems);
      });
    }

  }

}
