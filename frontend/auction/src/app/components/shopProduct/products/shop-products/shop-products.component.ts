import { Component, OnInit, Input } from "@angular/core";
import { faTh } from "@fortawesome/free-solid-svg-icons";
import { faList } from "@fortawesome/free-solid-svg-icons";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { PaginationInfo } from "src/app/models/PaginationInfo";
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: "app-shop-products",
  templateUrl: "./shop-products.component.html",
  styleUrls: ["./shop-products.component.css"]
})
export class ShopProductsComponent implements OnInit {
  @Input() products;
  @Input() hide;

  faTh = faTh;
  faList = faList;
  faChevronDown = faChevronDown;

  activeGrid = true;
  activeList = false;
  className = "grid";

  pageNumber = 0;
  size = 9;

  constructor(private productService: ProductService) {}

  ngOnInit() {}

  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    if (totalNumberOfItems - pageNumber * size < 0 || totalNumberOfItems - pageNumber * size == 0) {
      return true;
    }
    return false;
  }

  onClick() {
    this.pageNumber = this.pageNumber + 1;
    this.productService.getSortedProducts(null, this.pageNumber, this.size).subscribe(
      products => {
        this.products.items = this.products.items.concat(products.items); 
        if(this.checkIfThereIsNoItemsLeft(this.pageNumber + 1, this.size, products.totalNumberOfItems)) {
          this.hide = true;
        }
      });
  }

  grid() {
    this.activeGrid = true;
    this.activeList = false;
    this.className = "grid";
  }
  list() {
    this.activeGrid = false;
    this.activeList = true;
    this.className = "list";
  }
}
