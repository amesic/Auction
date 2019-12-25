import { Component, OnInit, Input, OnChanges, ViewChild, AfterViewInit } from "@angular/core";
import { hdmy } from "../../../reusableFunctions/hdmy";
import { Router } from "@angular/router";
import { CountdownComponent } from 'ngx-countdown';

@Component({
  selector: "app-my-account-bids-list",
  templateUrl: "./my-account-bids-list.component.html",
  styleUrls: ["./my-account-bids-list.component.css"]
})
export class MyAccountBidsListComponent implements OnInit, OnChanges, AfterViewInit {
  @Input() items;
  @Input() timeToCount;
  
  @ViewChild('cd', { static: false })
  cd: CountdownComponent;

  constructor(private router: Router) {}

  ngOnInit() {}
  ngAfterViewInit() {
    console.log(this.cd.i);

  }
  ngOnChanges() {
    this.items.forEach(product => {
      let date = new Date(Date.parse(product.endDate));
      product.timeLeft = hdmy(
        Math.floor((<any>date - new Date().getTime()) / 1000)
      );
      this.timeToCount = Math.floor((<any>date - new Date().getTime()) / 1000);
    });
  }

  sendInfoOfProduct(title, id) {
    this.router.navigate(["/shop/product/" + title + "/" + id]);
  }
}
