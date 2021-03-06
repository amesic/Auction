import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { BidInfo } from "../models/BidInfo";
import { Bid } from "../models/Bid";
import { Product } from '../models/Product';
import { PaginationInfo } from '../models/PaginationInfo';

const httpOptions={
  headers: new  HttpHeaders({
    'Content-Type':'application/json'
  })
}

@Injectable({
  providedIn: "root"
})
export class BidsService {
  constructor(private http: HttpClient) {}
  urlBidsInfoProduct = "/bid/bidsOfProduct";
  urlSaveNewBid = "/auth/bid/newBid";
  urlBidsFromUser = "/auth/bid/user";

  getBidsInfoOfProduct(id, page, size): Observable<BidInfo> {
    return this.http.get<BidInfo>(this.urlBidsInfoProduct 
      + "?pageNumber=" + page 
      + "&size=" + size 
      + "&id=" + id
      );
  }

  saveBidFromUser(product: Product, emailUser, value): Observable<Bid> {
    return this.http.post<any>(this.urlSaveNewBid, {
      product,
      "user": {"email": emailUser},
      value,
    }, httpOptions);
  }

  getBidsFromUser(pageNumber, size, emailUser): Observable<PaginationInfo> {
    return this.http.get<any>(this.urlBidsFromUser 
      + "?pageNumber=" + pageNumber
      + "&size=" + size
      + "&email=" + emailUser
      );
  }

}
