import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Category } from "../models/Category";
import { CategoryInfo } from '../models/CategoryInfo';

@Injectable({
  providedIn: "root"
})
export class CategoriesService {
  urlNumberOfCategories = "/category/all";
  urlNumberOfProductsByCategory = "/category/numberOfProductsBySubcategory";

  constructor(private http: HttpClient) {}

  getAllCategories(numberOfCategories): Observable<Category[]> {
    if (numberOfCategories != null) {
      return this.http.get<Category[]>(this.urlNumberOfCategories + "?numberOfCategories=" + numberOfCategories);
    }
    return this.http.get<Category[]>(this.urlNumberOfCategories);
  }
  getCategoriesAndNumberOfProducts(): Observable<CategoryInfo[]> {
    return this.http.get<CategoryInfo[]>(this.urlNumberOfProductsByCategory);
  }
}
