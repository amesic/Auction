import { Bid } from './Bid';

export class BidInfo {
  bidsOfProduct: Array<Bid>;
  highestBid: Bid;
  numberOfBids: number;
}