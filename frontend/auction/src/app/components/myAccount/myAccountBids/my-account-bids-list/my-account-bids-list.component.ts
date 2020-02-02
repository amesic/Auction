import { Component, OnInit, Input } from '@angular/core';
import { hdmy } from '../../../reusableFunctions/hdmy';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-account-bids-list',
  templateUrl: './my-account-bids-list.component.html',
  styleUrls: ['./my-account-bids-list.component.css']
})
export class MyAccountBidsListComponent implements OnInit {
  @Input() items;

  constructor(private router: Router) { }

  ngOnInit() {
      this.items.forEach(product => {
      let date = new Date(Date.parse(product.endDate));
      product.timeLeft = hdmy(Math.floor((<any>date - new Date().getTime()) / 1000));
      });
  }

  sendInfoOfProduct(title, id) {
    this.router.navigate(["/shop/product/" + title + "/" + id]);
  }

}
