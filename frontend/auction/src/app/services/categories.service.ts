import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Category } from "../models/Category";

@Injectable({
  providedIn: "root"
})
export class CategoriesService {
  url = "http://localhost:8080/category/all";
  constructor(private http: HttpClient) {}

  getAllCategories(numberOfCategories): Observable<Category[]> {
    if (numberOfCategories != null) {
      return this.http.get<Category[]>(this.url + "?numberOfCategories=" + numberOfCategories);
    }
    return this.http.get<Category[]>(this.url);
  }
}
