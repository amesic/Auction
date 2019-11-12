import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { BidInfo } from "../models/BidInfo";
import { Bid } from "../models/Bid";

@Injectable({
  providedIn: "root"
})
export class BidsService {
  constructor(private http: HttpClient) {}
  urlBidsInfoProduct = "/bid/bidsOfProduct";
  urlSaveNewBid = "/bid/newBid";
  urlGetBidsFromUserOfProduct = "/bid/bidUserOfProduct";

  getBidsInfoOfProduct(id): Observable<BidInfo> {
    return this.http.get<BidInfo>(this.urlBidsInfoProduct + "?id=" + id);
  }
  saveBidFromUser(idProduct, emailUser, value, highestValue): Observable<Bid> {
    return this.http.post<any>(this.urlSaveNewBid, {
      idProduct,
      emailUser,
      value,
      highestValue
    });
  }
  getBidUserOfProduct(emailUser, idProduct): Observable<Bid> {
    return this.http.get<Bid>(
      this.urlGetBidsFromUserOfProduct +
        "?emailUser=" +
        emailUser +
        "&idProduct=" +
        idProduct
    );
  }
}
