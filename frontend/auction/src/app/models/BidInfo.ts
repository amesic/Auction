import { Bid } from './Bid';
import { PaginationInfo } from './PaginationInfo';

export class BidInfo {
  highestBid: Bid;
  pageSize;
  pageNumber;
  totalNumberOfItems;
  items: Bid[];
}