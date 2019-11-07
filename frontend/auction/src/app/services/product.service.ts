import { Injectable, OnDestroy } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Product } from "../models/Product";

@Injectable({
  providedIn: "root"
})
export class ProductService {
  constructor(private http: HttpClient) {}
  urlAdv = "http://localhost:8080/product/advertisement";
  urlFeatPro = "http://localhost:8080/product/featureProduct";
  urlFeatColl = "http://localhost:8080/product/featureCollection";
  urlNewArr = "http://localhost:8080/product/newArrivals";
  urlLastChan = "http://localhost:8080/product/lastChance";

  getAdvProduct(): Observable<Product> {
    return this.http.get<Product>(this.urlAdv);
  }
  getFeatPro(): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlFeatPro);
  }
  getFeatColl(): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlFeatColl);
  }
  getNewArr(page, size): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlNewArr + "?page=" + page + "&size=" + size);
  }
  getLastChan(page, size): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlLastChan + "?page=" + page + "&size=" + size);
  }
}
