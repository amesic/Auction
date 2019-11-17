import { Component, OnInit, Input } from '@angular/core';
import { BidsService } from 'src/app/services/bids.service';

@Component({
  selector: 'app-bids',
  templateUrl: './bids.component.html',
  styleUrls: ['./bids.component.css']
})
export class BidsComponent implements OnInit {
@Input() bids;
@Input() userIsSeller;
@Input() highestBid;
  constructor(private bidsService: BidsService) { }

  ngOnInit() {
  }

}
