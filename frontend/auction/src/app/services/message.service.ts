import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private messageSourceSearch = new Subject<any>();
  currentMessageSearch = this.messageSourceSearch.asObservable();
  private messageSourceSubcategory = new Subject<any>();
  currentMessageSubcategory = this.messageSourceSubcategory.asObservable();

  constructor(public router :Router) {}

    changeMessageSearch(message) {
      this.messageSourceSearch.next(message);
      if (this.router.url == "/home") {
        this.router.navigate(['/shop']);
      }
    }
    changeMessageSubcategory(message) {
      this.messageSourceSubcategory.next(message);
      if (this.router.url == "/home" || this.router.url == "/home/allCategories") {
        this.router.navigate(['/shop']);
      }
    }

}
