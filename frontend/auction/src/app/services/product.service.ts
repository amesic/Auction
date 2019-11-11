import { Injectable, OnDestroy } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Product } from "../models/Product";
import { PaginationInfo } from "../models/PaginationInfo";

@Injectable({
  providedIn: "root"
})
export class ProductService {
  constructor(private http: HttpClient) {}
  urlAdvertisement = "http://localhost:8080/product/advertisement";
  urlFeatureProducts = "http://localhost:8080/product/featureProduct";
  urlFeatureCollection = "http://localhost:8080/product/featureCollection";
  urlNewArrivals = "http://localhost:8080/product/newArrivals";
  urlLastChance = "http://localhost:8080/product/lastChance";
  urlSingleProduct = "http://localhost:8080/product/singleProduct";
  urlRelatedProducts = "http://localhost:8080/product/relatedProducts";

  getAdvertisementProduct(): Observable<Product> {
    return this.http.get<Product>(this.urlAdvertisement);
  }
  getFeatureProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlFeatureProducts);
  }
  getFeatureCollection(): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlFeatureCollection);
  }
  getNewArrivals(pageNumber, size): Observable<PaginationInfo> {
    return this.http.get<PaginationInfo>(
      this.urlNewArrivals + "?page=" + pageNumber + "&size=" + size
    );
  }
  getLastChance(pageNumber, size): Observable<PaginationInfo> {
    return this.http.get<PaginationInfo>(
      this.urlLastChance + "?page=" + pageNumber + "&size=" + size
    );
  }
  getSingleProduct(id): Observable<Product> {
    return this.http.get<Product>(this.urlSingleProduct + "?id=" + id);
  }
  getRelatedProducts(idSubcategory, idProduct): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlRelatedProducts + 
      "?idSubcategory=" + idSubcategory + "&idProduct=" + idProduct);
  }
}
