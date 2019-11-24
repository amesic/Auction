import { Component, OnInit, Input } from '@angular/core';
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faGavel } from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-shop-product',
  templateUrl: './shop-product.component.html',
  styleUrls: ['./shop-product.component.css']
})
export class ShopProductComponent implements OnInit {
  @Input() image;
  @Input() title;
  @Input() description;
  @Input() startPrice;
  @Input() className;

  faHeart = faHeart;
  faGavel = faGavel;

  constructor() { }

  ngOnInit() {
  }

}
