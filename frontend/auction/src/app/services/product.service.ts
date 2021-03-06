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
  urlNumberViewers = "/product/numberViewers";
  urlActiveProducts = "/auth/product/active";
  urlSoldProducts = "/auth/product/sold";

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
    lowerBound,
    upperBound,
    subcategoryId,
    searchValue,
    pageNumber,
    size
  ): Observable<PaginationInfo> {
    let url = this.urlSortedProducts + "?typeOfSort=" + typeOfSort;
    if (subcategoryId != null) {
      url += "&subcategoryId=" + subcategoryId;
    }
    if (filterColorId != null) {
      url += "&filterColorId=" + filterColorId;
    }
    if (filterSizeId != null) {
      url += "&filterSizeId=" + filterSizeId;
    }
    if (lowerBound != null && upperBound != null) {
      url += "&lowerBound=" + lowerBound +  "&upperBound=" + upperBound;
    }
    if (searchValue != null && searchValue != "") {
      url += "&searchUser=" + searchValue;
    }
    url += "&pageNumber=" + pageNumber + "&size=" + size;
    return this.http.get<PaginationInfo>(url);
  }
  getNumberViewers(id): Observable<number> {
    return this.http.get<number>(this.urlNumberViewers + "?id=" + id);
  }
  getSoldProductsByUser(email, pageNumber, size): Observable<PaginationInfo> {
    return this.http.get<PaginationInfo>(this.urlSoldProducts + "?email=" + email +
     "&pageNumber=" + pageNumber + "&size=" + size);
  }
  getActiveProductsByUser(email, pageNumber, size): Observable<PaginationInfo> {
    return this.http.get<PaginationInfo>(this.urlActiveProducts + "?email=" + email +
    "&pageNumber=" + pageNumber + "&size=" + size);
  }
}
