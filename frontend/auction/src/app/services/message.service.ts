import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private messageSource = new Subject<any>();
  currentMessage = this.messageSource.asObservable();

    changeMessage(message) {
      this.messageSource.next(message);
    }

}
