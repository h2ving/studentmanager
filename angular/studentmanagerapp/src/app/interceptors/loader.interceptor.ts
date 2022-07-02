import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoaderService } from '../services/loader.service';

@Injectable()
export class LoaderInterceptor implements HttpInterceptor {
  private requests: HttpRequest<any>[] = [];

  constructor(private loaderService: LoaderService) { }

  // Keep track of the number of HTTP calls in progress
  removeRequest(req: HttpRequest<any>) {
    const i = this.requests.indexOf(req);

    if (i >= 0) {
      this.requests.splice(i, 1);
    }

    this.loaderService.isLoading.next(this.requests.length > 0);
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    this.requests.push(request);
    this.loaderService.isLoading.next(true);

    return new Observable((observer) => {
      const subscription = next.handle(request)
        .subscribe({
          next: (event) => {
            if (event instanceof HttpResponse) {
              this.removeRequest(request);
              observer.next(event);
            }
          },
          error: (error) => {
            this.removeRequest(request);
            observer.error(error);
          },
          complete: () => {
            this.removeRequest(request);
            observer.complete();
          }
        })

      return () => {
        this.removeRequest(request);
        subscription.unsubscribe();
      }
    })
  }
}
