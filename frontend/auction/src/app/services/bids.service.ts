import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';
import { BidInfo } from '../models/BidInfo';
import { Bid } from '../models/Bid';

@Injectable({
  providedIn: 'root'
})
export class BidsService {
  constructor(private http: HttpClient) { }
  urlBidsInfoProduct = "http://localhost:8080/bid/bidsOfProduct";
  urlSaveNewBid = "http://localhost:8080/bid/newBid";

  getBidsInfoOfProduct(id): Observable<BidInfo> {
    return this.http.get<BidInfo>(this.urlBidsInfoProduct + "?id=" + id);
  }
  saveBidFromUser(idProduct, emailUser, value, highestValue): Observable<Bid> {
    return this.http.post<any>(this.urlSaveNewBid, {idProduct, emailUser, value, highestValue});
  }
}
