import { Injectable } from "@angular/core";
import { HttpClient} from "@angular/common/http";
import { Observable } from "rxjs";
import { BidInfo } from "../models/BidInfo";
import { Bid } from "../models/Bid";
import { Product } from '../models/Product';

@Injectable({
  providedIn: "root"
})
export class BidsService {
  constructor(private http: HttpClient) {}
  urlBidsInfoProduct = "/bid/bidsOfProduct";
  urlSaveNewBid = "/auth/bid/newBid";

  getBidsInfoOfProduct(id): Observable<BidInfo> {
    return this.http.get<BidInfo>(this.urlBidsInfoProduct + "?id=" + id);
  }
  saveBidFromUser(product: Product, emailUser, value): Observable<Bid> {
    return this.http.post<any>(this.urlSaveNewBid, {
      product,
      "user": {"email": emailUser},
      value,
    });
  }
}
