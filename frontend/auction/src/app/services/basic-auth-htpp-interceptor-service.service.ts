import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from "@angular/common/http";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: "root"
})
//This service checks if the session has a valid username and token string,
//then it will update the headers of all outgoing HTTP requests.
//We implement the interceptor by extending the HttpInterceptor.
export class BasicAuthHtppInterceptorServiceService implements HttpInterceptor {
  constructor() {}
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
    if (sessionStorage.getItem("email") && sessionStorage.getItem("token")) {
      req = req.clone({
        setHeaders: {
          Authorization: sessionStorage.getItem("token")
        }
      });
    }

    return next.handle(req);
  }
}
