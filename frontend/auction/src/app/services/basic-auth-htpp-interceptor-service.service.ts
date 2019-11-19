import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpResponse,
  HttpErrorResponse
} from "@angular/common/http";
import { Observable } from 'rxjs';
import { map, catchError, retry } from 'rxjs/operators';

@Injectable()
//This service checks if the session has a valid username and token string,
//then it will update the headers of all outgoing HTTP requests.
//We implement the interceptor by extending the HttpInterceptor.
export class BasicAuthHtppInterceptorServiceService implements HttpInterceptor {
  constructor() {}
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    
        const request = req.clone({
        url: "http://localhost:8080" + req.url,
        headers: req.headers.set("Authorization", sessionStorage.getItem("token") || ""),
        withCredentials: false
      });
      return next.handle(request);
    }
}
