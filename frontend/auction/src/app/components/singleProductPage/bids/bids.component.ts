import { Component, OnInit, Input } from '@angular/core';
import { BidsService } from 'src/app/services/bids.service';

@Component({
  selector: 'app-bids',
  templateUrl: './bids.component.html',
  styleUrls: ['./bids.component.css']
})
export class BidsComponent implements OnInit {
@Input() bids = [];
@Input() userIsSeller;
@Input() highestBid;
@Input() idProduct;
@Input() numberOfBids;
@Input() hide;

pageNumber = 0;
size = 5;
  constructor(private bidsService: BidsService) { }

  ngOnInit() {
  }
  checkIfThereIsNoItemsLeft(pageNumber, size, totalNumberOfItems) {
    if (
      pageNumber * size + totalNumberOfItems - size == totalNumberOfItems ||
      pageNumber * size + totalNumberOfItems - size < 0 ||
      pageNumber * size + totalNumberOfItems - size == 0
    ) {
      return true;
    }
    return false;
  }

  onClick() {
    this.pageNumber = this.pageNumber + 1;
    this.bidsService.getBidsInfoOfProduct(this.idProduct, this.pageNumber, this.size).subscribe(
      bidInfo => {
        this.bids = this.bids.concat(bidInfo.items); 
      });
      if(this.checkIfThereIsNoItemsLeft(this.pageNumber, this.size, this.numberOfBids)) {
        this.hide = true;
      }
  }

}
