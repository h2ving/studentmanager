import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpContextToken
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const BYPASS_ACCESS_TOKEN = new HttpContextToken(() => false);

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const { accessToken, refreshToken } = this.authService.getTokens;
    let authReq: HttpRequest<any> = req;

    if (req.context.get(BYPASS_ACCESS_TOKEN) === true) {
      authReq = req.clone({
        setHeaders: {
          'Authorization': 'Bearer ' + refreshToken
        }
      });
    } else {
      authReq = authReq.clone({
        setHeaders: {
          'Authorization': 'Bearer ' + accessToken
        }
      })
    }

    // ?: What to do in case of 403?
    // Just logout -> That requires in server all responses to be handled

    // error instanceof HttpErrorResponse && !authReq.url.includes('auth/signin') && error.status === 403

    return next.handle(authReq);
  }
}
