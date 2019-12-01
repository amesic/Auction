import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Product } from "../models/Product";
import { PaginationInfo } from "../models/PaginationInfo";

@Injectable({
  providedIn: "root"
})
export class ProductService {
  constructor(private http: HttpClient) {}
  urlAdvertisement = "/product/advertisement";
  urlFeatureProducts = "/product/featureProduct";
  urlFeatureCollection = "/product/featureCollection";
  urlNewArrivals = "/product/newArrivals";
  urlLastChance = "/product/lastChance";
  urlSingleProduct = "/product/singleProduct";
  urlRelatedProducts = "/product/relatedProducts";
  urlSortedProducts = "/product/sortProducts";

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
  getRelatedProducts(id): Observable<Product[]> {
    return this.http.get<Product[]>(this.urlRelatedProducts + "?id=" + id);
  }
  getSortedProducts(
    typeOfSort,
    filterColorId,
    filterSizeId,
    subcategoryId,
    searchValue,
    pageNumber,
    size
  ): Observable<PaginationInfo> {
    let url = this.urlSortedProducts + "?typeOfSort=" + typeOfSort;
    if (filterColorId != null) {
      url += "&filterColorId=" + filterColorId;
    }
    if (filterSizeId != null) {
      url += "&filterSizeId=" + filterSizeId;
    }
    if (subcategoryId != null) {
      url += "&subcategoryId=" + subcategoryId;
    }
    if (searchValue != null) {
      url += "&searchUser=" + searchValue;
    }
    url += "&pageNumber=" + pageNumber + "&size=" + size;
    return this.http.get<PaginationInfo>(url);
  }
}
