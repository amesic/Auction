import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-account-products-list',
  templateUrl: './my-account-products-list.component.html',
  styleUrls: ['./my-account-products-list.component.css']
})
export class MyAccountProductsListComponent implements OnInit {
  @Input() items;

  constructor(private router: Router) { }

  ngOnInit() {
  }
  sendInfoOfProduct(title, id){
    this.router.navigate(['/shop/product/' + title + "/" + id]);
  }

}
